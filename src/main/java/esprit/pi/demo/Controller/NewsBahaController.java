package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.NewsBahaService;
import esprit.pi.demo.entities.NewsBaha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/newsbaha")
public class NewsBahaController {

    @Autowired
    private NewsBahaService newsBahaService;

    @GetMapping
    public List<NewsBaha> getAllNews() {
        return newsBahaService.getAllNews();
    }

    @PostMapping
    public NewsBaha createOrUpdateNews(@RequestBody NewsBaha news) {
        return newsBahaService.saveOrUpdateNews(news);
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable int id) {
        newsBahaService.deleteNews(id);
    }

    @GetMapping("/search")
    public List<NewsBaha> searchNews(@RequestParam String keyword) {
        return newsBahaService.searchNews(keyword);
    }
}

