package models;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Searcher {
    public String search(String searchTerm) throws IOException {

        StringBuilder foundedLinks = new StringBuilder();
        Elements results = new Elements();
        for (int i = 0; i < 2; i++) {

            String searchURL = startingURL + searchTerm + optionalQuery;
            //without proper User-Agent, we will get 403 error
            Document doc = Jsoup.connect(searchURL).userAgent(AGENT).get();
            results.addAll(doc.getElementsByClass(className));
        }
        if(results.isEmpty())
            System.out.println("empty");
        else
            System.out.println(results.size());
        int number_of_links = 0;
        for (Element result : results) {
            if(number_of_links >2)
                break;
            String hreftext = result.attr("href");
            String linkText = result.text();
            foundedLinks.append(hreftext)
                            .append(" ")
                                .append(linkText)
                                    .append("\n");
            number_of_links++;
        }
        return foundedLinks.toString();
    }
    private static final String AGENT = "Mozilla/5.0";
    private String startingURL;
    private String className;
    private String optionalQuery;

    public Searcher(String system) {
        if (system.equals("GOOGLE")) {
            this.startingURL = "https://www.google.com/search?q=";
            this.className = "TbwUpd";
            this.optionalQuery = "";
        }
        if(system.equals("YANDEX"))
        {
            //this.startingURL = "https://yandex.ru/search/?text=";
            this.startingURL = "https://yandex.ru/search/xml?user=alphadototm&key=03.375260350:385c7103cb5ebe2ae6092bc28c54695b&query=";
            this.className =  "link link_theme_normal organic__url link_cropped_no i-bem";
            //this.className = "link link_theme_outer path__item i-bem";
            this.optionalQuery = "&p=";
            //this.optionalQuery = "";
        }
        if(system.equals("HABR"))
        {
            this.startingURL = "https://habr.com/ru/search/?target_type=posts&order_by=relevance&q=";
            this.className =  "post__title_link";
            this.optionalQuery = "";
        }
    }
}
