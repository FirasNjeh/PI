package esprit.pi.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import esprit.pi.demo.Services.RisqueVenteService;

import java.io.IOException;

@RestController
@RequestMapping("/riqueVente")
public class RisqueVenteController {
    @Autowired
    private RisqueVenteService risqueVenteService;

    @GetMapping("/evaluerRisque")
    public ResponseEntity<byte[]> evaluerRisque(@RequestParam int qte,
                                                @RequestParam int duree,
                                                @RequestParam double prixUnitaire,
                                                @RequestParam double coutVariable,
                                                @RequestParam double coutFixe) throws IOException {

        //risqueVenteService.generateExcel(qte, duree, prixUnitaire, coutVariable, coutFixe, response);
        byte[] excelBytes = risqueVenteService.generateExcel(qte, duree, prixUnitaire, coutVariable, coutFixe);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("filename", "EvaluationRisqueVente.xls");

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}
