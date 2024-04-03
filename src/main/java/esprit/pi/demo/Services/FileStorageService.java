package esprit.pi.demo.Services;

import esprit.pi.demo.Controller.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    @Autowired
    private FileStorageProperties fileStorageProperties;

    public String getUploadDir() {
        return fileStorageProperties.getUploadDir();
    }

    public void storeFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Files.copy(file.getInputStream(), uploadPath.resolve(file.getOriginalFilename()));
    }

    public File loadFile(String filename) {
        return new File(fileStorageProperties.getUploadDir() + "/" + filename);
    }

    public void deleteFile(String filename) {
        Path filePath = Paths.get(fileStorageProperties.getUploadDir()).resolve(filename).normalize();
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {

        }
    }
}
