package esprit.pi.demo.Services;

import esprit.pi.demo.entities.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface IFileService {
    File storeSinistreFile(Integer idsinistre, MultipartFile file) throws IOException;

    File storePackFile(Integer idpack, MultipartFile file) throws IOException;

    Stream<File> getFilesBySinistre(Integer idsinistre);

    Stream<File> getFilesByPack(Integer idpack);
}
