import java.util.*;
import java.io.*;

public class Search {
    private ArrayList<String> allURLs;
    private ArrayList<String> uniqueWords;
    private ArrayList<String> getPageNames;
    private HashMap<String, HashMap<String, Double>> TFIDFs;
    private HashMap<String, Double> IDFs;
    private HashMap<String, Double> URLsPageRank;

    public Search() {
        File f = new File(FileHandling.filePath );
        if (f.exists()) {
            allURLs = new ArrayList<>();
            uniqueWords = new ArrayList<>();
            TFIDFs = new HashMap<>();
            IDFs = new HashMap<>();
            URLsPageRank = new HashMap<>();
            getPageNames  = new ArrayList<>();

            allURLs = (ArrayList<String>) FileHandling.readFileLinks(FileHandling.filePath, null, "html.txt"); // READ HTML
            uniqueWords = (ArrayList<String>) FileHandling.readFileLinks(FileHandling.filePath, null, "uniqueWords.txt"); // READ UNIQUE WORDS

            for (String url: allURLs) {
                String title = URL.getURLTitle(url);
                ArrayList<String> link = (ArrayList<String>) FileHandling.readFileLinks(FileHandling.filePath, title, "pageName.txt"); // READ TITLE of PAGE
                getPageNames.add(link.get(0));

                double pageRank = FileHandling.readFileData(FileHandling.filePath, title, "pageRank.txt", false);
                URLsPageRank.put(title, pageRank);

                HashMap<String, Double> wordTFIDF = new HashMap<>();
                for (String word: uniqueWords) {
                    double tfidf = FileHandling.readFileData(FileHandling.filePath, title, word + ".txt", true);
                    wordTFIDF.put(word, tfidf);
                }
                TFIDFs.put(title, wordTFIDF);
            }

            for (String word: uniqueWords) {
                double idf = FileHandling.readFileData(FileHandling.filePath, "idf", word + ".txt", false);
                IDFs.put(word, idf);
            }
        }
    }

    public List<SearchResult> runSearch(String query, boolean boost, int X) {
        double cosineSimilarity;
        List<SearchResultImp> resultObj = new ArrayList<SearchResultImp>();
        String[] wordsInQuery = query.split(" ");
        HashMap<String, Double> queryTFIDF = new HashMap<>();

        for (String queryWord: wordsInQuery) {
            if (!queryTFIDF.containsKey(queryWord)) {
                double idf = 0;
                if (uniqueWords.contains(queryWord)) {
                    idf = IDFs.get(queryWord);
                }
                double calculation = 1.0 + (double)Collections.frequency(List.of(wordsInQuery), queryWord) / wordsInQuery.length;
                double queryTfidf = (Math.log(calculation)/ Math.log(2)) * idf;
                queryTFIDF.put(queryWord, queryTfidf);
            }
        }

        for (String url: allURLs) {
            double numerator = 0, leftDenominator = 0, rightDenominator = 0, denominator;
            String title = URL.getURLTitle(url);

            for (String queryWord: queryTFIDF.keySet()) {
                double tfidf = 0;
                if (uniqueWords.contains(queryWord)) {
                    tfidf = TFIDFs.get(title).get(queryWord);
                }
                numerator += queryTFIDF.get(queryWord) * tfidf;
                leftDenominator += Math.pow(queryTFIDF.get(queryWord), 2);
                rightDenominator += Math.pow(tfidf, 2);
            }
            if (leftDenominator == 0 || rightDenominator == 0 || numerator == 0) {
                cosineSimilarity = 0.0;
            } else {
                denominator = Math.sqrt(leftDenominator) * Math.sqrt(rightDenominator);
                cosineSimilarity = numerator / denominator;
            }

            int urlIndex = allURLs.indexOf(url);
            String pageName = getPageNames.get(urlIndex);
            if (boost) {
                double pageRank = URLsPageRank.get(title);
                resultObj.add(new SearchResultImp(pageName,cosineSimilarity * pageRank));
            } else {
                resultObj.add(new SearchResultImp(pageName,cosineSimilarity));
            }
        }

        Collections.sort(resultObj);
        List<SearchResult> resultInt = new ArrayList<SearchResult>();
        int count = 0;
        for (SearchResultImp ans: resultObj) {
            if (count == X) {
                break;
            }
            resultInt.add(ans);
            count++;
        }
        return resultInt;
    }
}
