import java.io.*;
import java.util.*;

public class SearchData {
    URL myPage;

    public SearchData() {
        myPage = new URL();
    }

    public void crawlInitialization() {
            String dirName = FileHandling.filePath;
            File dir = new File(dirName);
            FileHandling.deleteFilesDirectories(dir);
    }

    public List<String> retrieveOutgoingLinks(String url) {
        String title = URL.getURLTitle(url);
        File path = new File(FileHandling.filePath + File.separator + title);
        if (path.exists() && path.isDirectory()) {
            return FileHandling.readFileLinks(FileHandling.filePath, title, "outgoing.txt");
        }
        return null;
    }

    public List<String> retrieveIncomingLinks(String url) {
        String title = URL.getURLTitle(url);
        File path = new File(FileHandling.filePath + File.separator + title);
        if (path.exists() && path.isDirectory()) {
            return FileHandling.readFileLinks(FileHandling.filePath, title, "incoming.txt");
        }
        return null;
    }


    public double retrievePageRank(String url) {
        String title = URL.getURLTitle(url);
        File path = new File(FileHandling.filePath + File.separator + title);
        if (path.exists() && path.isDirectory()) {
            return FileHandling.readFileData(FileHandling.filePath, title, "pageRank.txt", false);
        }
        return -1;
    }

    public double retrieveIDF(String word) {
        File path = new File(FileHandling.filePath + File.separator + "idf" + File.separator + word + ".txt");
        if (path.exists()) {
            return FileHandling.readFileData(FileHandling.filePath, "idf", word + ".txt", false);
        }
        return 0;
    }

    public double retrieveTF(String url, String word) {
        String title = URL.getURLTitle(url);
        File path = new File(FileHandling.filePath + File.separator + title + File.separator + word + ".txt");
        if (path.exists()) {
            return FileHandling.readFileData(FileHandling.filePath, title, word + ".txt", false);
        }
        return 0;
    }

    public double retrieveTFIDF(String url, String word) {
        String title = URL.getURLTitle(url);
        File path = new File(FileHandling.filePath + File.separator + title + File.separator + word + ".txt");
        if (path.exists()) {
            return FileHandling.readFileData(FileHandling.filePath, title, word + ".txt", true);
        }
        return 0;
    }
}
