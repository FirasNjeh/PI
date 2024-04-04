package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.IPackCRService;
import esprit.pi.demo.entities.PackCR;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/user/PackCR")
@AllArgsConstructor
public class PackCRController {

    private IPackCRService service;
    @PostMapping("/add")
    public PackCR addPackCR(@RequestBody PackCR packCR){
        return service.savePackCR(packCR);
    }

    @GetMapping("/all")
    public List<PackCR> findAllPacksCR(){
        return service.getPacksCR();
    }

    @GetMapping("/{id}")
    public PackCR findPackCRById(@PathVariable int id){
        return service.getPackCRById(id);
    }

    @PutMapping("/update/{id}")
    public PackCR updatePackCR(@PathVariable int id,@RequestBody PackCR packCR) {
        return  service.updatePackCR(id,packCR);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePackCR(@PathVariable int id){
        return service.deletePackCR(id);
    }

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImageOnServer(@RequestParam("file") MultipartFile file, @PathVariable int id) throws IOException {
        service.uploadImage(file,id);
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }



}
