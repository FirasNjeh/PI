package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.IFileService;
import esprit.pi.demo.Services.ISinistreAssurance;

import esprit.pi.demo.DTO.ResponseFile;
import esprit.pi.demo.DTO.ResponseMessage;
import esprit.pi.demo.entities.Sinistre;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/user/Siniste")
public class SinistreController {

    private ISinistreAssurance service;
    private IFileService fileService;

//    @PostMapping("/addSinistre")
//    public Sinistre addSinistre(@RequestBody Sinistre sinistre){
//        return service.saveSinistre(sinistre);}

    @GetMapping("/Sinsitres")
    public List<Sinistre> findAllSinistres(){
        return service.getSinsitre();}


    @GetMapping("/Sinistre/{id}")
    public Sinistre findSinistreById(@PathVariable int id) {
        return service.getSinistreById(id);}


    @PutMapping("/updateS")
    public Sinistre updateSinistre(@RequestBody int id ,Sinistre sinistre){
        return service.updateSinistre(id,sinistre);}

    @DeleteMapping("/deleteS/{id}")
    public String deleteSinistre(@PathVariable int id)
    {
        return service.deleteSinistre(id);
    }


    @PostMapping("/{idsinistre}/uploadimage")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable int idsinistre) {
        String message = "";
        try {
            fileService.storeSinistreFile(idsinistre,file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/{idsinistre}/images")
    public ResponseEntity<List<ResponseFile>> getListFiles(@PathVariable int idsinistre) {
        List<ResponseFile> files = fileService.getFilesBySinistre(idsinistre).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    @GetMapping("/byAssurance/{idassur}")
    public ResponseEntity<Set<Sinistre>> getListSinistesByAssurance(@PathVariable int idassur) {
        Set<Sinistre> sinistres = service.getListSinistesByAssurance(idassur);
        return ResponseEntity.ok(sinistres);
    }

    @PostMapping("/createWithAssurance/{idassur}")
    public ResponseEntity<Sinistre> createSinistreWithAssurance( @PathVariable int idassur, @RequestBody Sinistre sinistre) {
        Sinistre createdSinistre = service.createSinistreWithAssurance(idassur, sinistre);
        return ResponseEntity.ok(createdSinistre);
    }

    @PostMapping("/accept/{idsin}")
    public ResponseEntity<Sinistre> acceptSinistre(@PathVariable int idsin) {
        Sinistre acceptedSinistre = service.acceptSinistre(idsin);
        return new ResponseEntity<>(acceptedSinistre, HttpStatus.OK);
    }

    @PostMapping("/refuse/{idsin}")
    public ResponseEntity<Sinistre> refuseSinistre(@PathVariable int idsin) {
        Sinistre refusedSinistre = service.refuseSinistre(idsin);
        return new ResponseEntity<>(refusedSinistre, HttpStatus.OK);
    }
}
