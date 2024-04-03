package esprit.pi.demo.Controller;

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rss")
public class RssController {

    @GetMapping("/news")
    public List<SyndFeed> getAllNewsRssFeeds() {
        List<SyndFeed> allFeeds = new ArrayList<>();

        // RSS feeds URLs
        List<String> rssFeedUrls = new ArrayList<>();
        rssFeedUrls.add("https://www.cgap.org/rss/news.xml");
        rssFeedUrls.add("https://www.accion.org/rss.xml");
        rssFeedUrls.add("https://www.microfinancegateway.org/rss.xml");
        rssFeedUrls.add("https://microinsurancenetwork.org/rss.xml");
        rssFeedUrls.add("https://www.ilo.org/global/topics/microinsurance/rss.xml");
        rssFeedUrls.add("https://www.worldbank.org/en/news/all?topic_exact=Microinsurance&format=rss");

        for (String feedUrl : rssFeedUrls) {
            try {
                URL url = new URL(feedUrl);
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(url));

                allFeeds.add(feed);
            } catch (Exception e) {
                // Handle exception
                e.printStackTrace();
            }
        }

        return allFeeds;
    }
}
