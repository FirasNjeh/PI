package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.FileRepository;
import esprit.pi.demo.Repository.PackAssuranceRepository;
import esprit.pi.demo.Repository.SinistreRepository;
import esprit.pi.demo.entities.File;
import esprit.pi.demo.entities.PackAssur;
import esprit.pi.demo.entities.Sinistre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileService implements IFileService{

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private SinistreRepository sinistreRepository;

    @Autowired
    private PackAssuranceRepository packAssuranceRepository;
    @Override
    public File storeSinistreFile(Integer idsinistre, MultipartFile file) throws IOException {
        Sinistre sinistre = sinistreRepository.findById(idsinistre).get();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File FileDB = new File(fileName, file.getContentType(), file.getBytes(), sinistre);

        return fileRepository.save(FileDB);
    }

    @Override
    public File storePackFile(Integer idpack, MultipartFile file) throws IOException {
        PackAssur packAssur = packAssuranceRepository.findById(idpack).get();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File FileDB = new File(fileName, file.getContentType(), file.getBytes(), packAssur);

        return fileRepository.save(FileDB);
    }

    @Override
    public Stream<File> getFilesBySinistre(Integer idsinistre)
    {
        Sinistre sinistre = sinistreRepository.findById(idsinistre).get();
        return fileRepository.findBySinistre(sinistre).stream();
    }

    @Override
    public Stream<File> getFilesByPack(Integer idpack)
    {
        PackAssur packAssur = packAssuranceRepository.findById(idpack).get();
        return fileRepository.findByPackAssur(packAssur).stream();
    }
}