package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.NewsBahaRepository;
import esprit.pi.demo.entities.NewsBaha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsBahaService {

    @Autowired
    private NewsBahaRepository newsBahaRepository;

    public List<NewsBaha> getAllNews() {
        return newsBahaRepository.findAll();
    }

    public NewsBaha saveOrUpdateNews(NewsBaha news) {
        return newsBahaRepository.save(news);
    }

    public void deleteNews(int id) {
        newsBahaRepository.deleteById(id);
    }

    public List<NewsBaha> searchNews(String keyword) {
        return newsBahaRepository.findByTitreContainingIgnoreCase(keyword);
    }
}
