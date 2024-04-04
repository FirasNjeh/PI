package esprit.pi.demo.Services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BadWordsFilter {

    private static final List<String> BAD_WORDS = Arrays.asList("badword1", "badword2", "badword3");

    public String filter(String content) {
        if (content == null) {
            return null;
        }

        for (String badWord : BAD_WORDS) {
            content = content.replaceAll(badWord, "***");
        }

        return content;
    }
}
