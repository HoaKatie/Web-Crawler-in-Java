import java.io.*;
import java.util.*;

public class Crawler {
    private CrawlCalculation makeData;
    private HashSet<String> uniqueWords;
    private HashSet<String> parsedURLs;
    private LinkedHashMap<String, HashSet<String>> incomingMapping; // INCOMING LINKS MAPPING

    public Crawler() {
        makeData = new CrawlCalculation();

        uniqueWords = new HashSet<>();
        parsedURLs = new HashSet<>();
        incomingMapping = new LinkedHashMap<>();

    }

    public void crawlImplementation(String seed) throws IOException {
        Queue<String> queue = new LinkedList<>(); // CREATE A QUEUE USING LINKED LIST
        queue.add(seed);

        int i = seed.lastIndexOf("/");
        String absolutePath =  seed.substring(0, i+1); // eg: https://people.scs.carleton.ca/~davidmckenney/tinyfruits/

        String link;
        HashMap<String, String[]> urlsWords = new HashMap<>();
        while (!queue.isEmpty()) {
            link = queue.poll();
            if (parsedURLs.contains(link)) {
                continue;
            } else {
                parsedURLs.add(link);
            }

            String pageSource = WebRequester.readURL(link);
            String pageName = URL.getPageName(pageSource);
            String[] words = URL.getWords(pageSource);
            urlsWords.put(link, words);
            HashSet<String> outgoingLinks = new HashSet<>(List.of(URL.getURLsFromRelativeURLs(pageSource, absolutePath)));

            uniqueWords.addAll(Arrays.asList(words));

            String title = URL.getURLTitle(link);
            FileHandling.createDirectoryInCrawled(FileHandling.filePath, title); // Create N-0, N-1,... subfolder in crawled folder

            HashSet<String> onePageName = new HashSet<>();
            onePageName.add(pageName);
            FileHandling.writeLinesInFile(FileHandling.filePath, title, "pageName.txt", onePageName); // WRITE TITLE OF THE PAGE (DIFFERENT FROM TITLE FROM RELATIVE LINKS

            for (String url: outgoingLinks) {
                if (incomingMapping.containsKey(url)) {
                    incomingMapping.get(url).add(link);
                } else {
                    incomingMapping.put(url, new HashSet<String>());
                    incomingMapping.get(url).add(link);
                }
                if (!parsedURLs.contains(url)) {
                    queue.add(url);
                }
            }
            FileHandling.writeLinesInFile(FileHandling.filePath, title, "outgoing.txt", outgoingLinks); // WRITE OUTGOING LINKS
        }

        FileHandling.writeLinesInFile(FileHandling.filePath, null, "html.txt", parsedURLs); // WRITE ALL HTML URLS
        FileHandling. writeLinesInFile(FileHandling.filePath, null, "uniqueWords.txt", uniqueWords); // WRITE ALL WORDS

        for (Map.Entry<String, HashSet<String>> entry : incomingMapping.entrySet()) {
            String url = entry.getKey();
            String title = URL.getURLTitle(url);
            HashSet<String> incomingLinks = entry.getValue();
            FileHandling.writeLinesInFile(FileHandling.filePath, title, "incoming.txt", incomingLinks); // WRITE INCOMING LINKS
        }

        FileHandling.createDirectoryInCrawled(FileHandling.filePath, "idf");
        makeData.createIDF(incomingMapping, uniqueWords, urlsWords);
        makeData.createTF_TFIDF(incomingMapping, uniqueWords, urlsWords);
        makeData.createPageRank(incomingMapping);
    }
}
