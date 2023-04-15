import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectTesterImp implements ProjectTester{ // MUST ONLY SUPPORT METHODS IN ProjectTester
    // CREATE INSTANCES FOR OTHER CLASSES
    SearchData data;
    Crawler crawl;
    Search mySearch;


    public ProjectTesterImp() {
        crawl = new Crawler();
        data = new SearchData();
        mySearch = new Search();
    }

    @Override
    public void initialize() {data.crawlInitialization();}
    @Override
    public void crawl(String seedURL) throws IOException {
        crawl.crawlImplementation(seedURL);
        mySearch = new Search();
    }
    @Override
    public List<String> getOutgoingLinks(String url) {
        return data.retrieveOutgoingLinks(url);
    }
    @Override
    public List<String> getIncomingLinks(String url) {
        return data.retrieveIncomingLinks(url);
    }
    @Override
    public double getPageRank(String url) {
        return data.retrievePageRank(url);
    }
    @Override
    public double getIDF(String word) {
        return data.retrieveIDF(word);
    }
    @Override
    public double getTF(String url, String word) {
        return data.retrieveTF(url, word);
    }
    @Override
    public double getTFIDF(String url, String word) {
        return data.retrieveTFIDF(url, word);
    }
    @Override
    public List<SearchResult> search(String query, boolean boost, int X) {return mySearch.runSearch(query, boost, X);}

}
