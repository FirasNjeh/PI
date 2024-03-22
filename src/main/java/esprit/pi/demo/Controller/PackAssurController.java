package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.IFileService;
import esprit.pi.demo.Services.IPackAssurService;
import esprit.pi.demo.dto.ResponseFile;
import esprit.pi.demo.dto.ResponseMessage;
import esprit.pi.demo.entities.PackAssur;
import esprit.pi.demo.entities.PackAssurance;
import esprit.pi.demo.entities.PackCR;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/PackAssur")
@AllArgsConstructor

public class PackAssurController {
    private IPackAssurService service;
    private IFileService fileService;

    @PostMapping("/add")
   public PackAssur addPackAssur(@RequestBody PackAssur packAssur){
       return service.savePackAssur(packAssur);
   }
    @GetMapping("/all")
    public List<PackAssur> findAllPacksAssur(){
        return service.getPacksAssur();
    }

    @GetMapping("/{id}")
    public PackAssur findPackAssurById(@PathVariable int id)
    {
      return service.getPackAssurById(id);

    }
    @PutMapping("/update/{id}")
    public PackAssur updatePackAssur(@PathVariable int id,@RequestBody PackAssur packAssur) {
        return  service.updatePackAssur(id,packAssur);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePackAssur(@PathVariable int id)
    {
        return service.deletePackAssur(id);
    }

    @PostMapping("/{idpack}/uploadimage")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable int idpack) {
        String message = "";
        try {
            fileService.storePackFile(idpack,file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/{idpack}/images")
    public ResponseEntity<List<ResponseFile>> getListFiles(@PathVariable int idpack) {
        List<ResponseFile> files = fileService.getFilesByPack(idpack).map(dbFile -> {
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
    @GetMapping("/assuranceCounts")
    public ResponseEntity<Map<String, Integer>> getPackAssurAssuranceCounts() {
        Map<String, Integer> packAssurCounts = service.getPackAssurAssuranceCounts();
        return new ResponseEntity<>(packAssurCounts, HttpStatus.OK);
    }
}
