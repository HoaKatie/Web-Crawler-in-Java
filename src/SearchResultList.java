import java.util.ArrayList;
import java.util.List;

public class SearchResultList {
    private List<SearchResult> searchResults;
    private String[] result;
    private final int size = 10;
    Search mySearch;


    public SearchResultList() {
        searchResults = new ArrayList<>();
        mySearch = new Search();
        result = new String[size];
    }

    public void searchResults(String query, boolean boost) {
        searchResults = mySearch.runSearch(query, boost, size);
    }

    public String[] getResults() {
        int i = 0;
        for (SearchResult s: searchResults) {
            result[i] = s.getTitle() + ": " + s.getScore();
            i++;
        }
        return result;
    }

}
