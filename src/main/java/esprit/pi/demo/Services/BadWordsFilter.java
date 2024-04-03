package esprit.pi.demo.Services;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class BadWordsFilter {

    private Set<String> badWords = new HashSet<>(Arrays.asList("badword1", "badword2", "badword3"));

    public String filter(String content) {
        for (String word : badWords) {
            content = content.replaceAll("(?i)" + word, "***");
        }
        return content;
    }
}
