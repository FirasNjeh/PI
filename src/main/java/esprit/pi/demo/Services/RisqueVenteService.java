package esprit.pi.demo.Services;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class RisqueVenteService {
    public byte[] generateExcel(int qte, int duree, double prixUnitaire, double coutVariable, double coutFixe) throws IOException {

        double chiffreDAffaire = prixUnitaire * qte;
        double coutTotalVentes = coutVariable * qte;
        double beneficeBrut = chiffreDAffaire - coutTotalVentes;
        double coutsFixesCouverts = coutFixe * duree;
        double pointMort = coutsFixesCouverts / (prixUnitaire - coutVariable);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Evaluation risque de vente");

        HSSFRow row1 = sheet.createRow(0);
        HSSFRow row2 = sheet.createRow(1);
        HSSFRow row3 = sheet.createRow(2);
        HSSFRow row4 = sheet.createRow(3);
        HSSFRow row5 = sheet.createRow(4);

        row1.createCell(0).setCellValue("QTE");
        row2.createCell(0).setCellValue("PRIX UNITAIRE");
        row3.createCell(0).setCellValue("COUTS VARIABLES");
        row4.createCell(0).setCellValue("COUTS FIXES");
        row5.createCell(0).setCellValue("DUREE EN MOIS");

        row1.createCell(1).setCellValue(qte);
        row2.createCell(1).setCellValue(prixUnitaire);
        row3.createCell(1).setCellValue(coutVariable);
        row4.createCell(1).setCellValue(coutFixe);
        row5.createCell(1).setCellValue(duree);

        row1.createCell(2).setCellValue("");
        row2.createCell(2).setCellValue("");
        row3.createCell(2).setCellValue("");
        row4.createCell(2).setCellValue("");
        row5.createCell(2).setCellValue("");

        row1.createCell(3).setCellValue("CHIFFRE D'AFFAIRE");
        row2.createCell(3).setCellValue("COUT TOTAL DES VENTES");
        row3.createCell(3).setCellValue("BENEFICE BRUT");
        row4.createCell(3).setCellValue("COUTS FIXES COUVERTS");
        row5.createCell(3).setCellValue("POINT MORT");

        row1.createCell(4).setCellValue(chiffreDAffaire);
        row2.createCell(4).setCellValue(coutTotalVentes);
        row3.createCell(4).setCellValue(beneficeBrut);
        row4.createCell(4).setCellValue(coutsFixesCouverts);
        row5.createCell(4).setCellValue(pointMort);

        /*OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachement; filename=EvaluationRisqueVente.xls");
        response.flushBuffer();*/

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();
        byteArrayOutputStream.close();

        return byteArrayOutputStream.toByteArray();
    }
}
