import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;

public class WebCrawler {

    private static final int MAX_DEPTH = 3;
    private HashSet<String> visitedURLs;

    public WebCrawler() {
        visitedURLs = new HashSet<>();
    }

    public void crawl(String seedURL, int depth) {
        if (depth <= MAX_DEPTH) {
            System.out.println("Depth: " + depth + " - Visiting: " + seedURL);

            try {
                Document document = Jsoup.connect(seedURL).get();

                Elements links = document.select("a[href]");
                for (Element link : links) {
                    String nextURL = link.absUrl("href");

                    if (!visitedURLs.contains(nextURL)) {
                        visitedURLs.add(nextURL);
                        crawl(nextURL, depth + 1);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error fetching URL: " + seedURL);
            }
        }
    }

    public static void main(String[] args) {
        String seedURL = "https://example.com";  // Inserisci il tuo seed URL qui
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.crawl(seedURL, 1);
    }
}
