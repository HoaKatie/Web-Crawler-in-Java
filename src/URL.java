public class URL {
    private double idf;
    private double tf;
    private double tfidf;
    private double pageRank;


    public double getIdf() {
        return idf;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }

    public double getTf() {
        return tf;
    }

    public void setTf(double tf) {
        this.tf = tf;
    }

    public double getTfidf() {
        return tfidf;
    }

    public void setTfidf(double tfidf) {
        this.tfidf = tfidf;
    }

    public double getPageRank() {
        return pageRank;
    }

    public void setPageRank(double pageRank) {
        this.pageRank = pageRank;
    }

    public URL() {
        idf = 0;
        tf = 0;
        tfidf = 0;
        pageRank = 0;
    }


    public static String getURLTitle(String URL) { // EG: N-0 from the URL
        int i = URL.lastIndexOf("/");
        int j = URL.lastIndexOf(".");
        return URL.substring(i+1,j);
    }

    public static String getPageName(String content) {
        if (content == "") {
            return null;
        } else {
            String str = content.split("<title>")[1];
            String name = str.split("</title")[0];
            return name;
        }
    }

    public static String[] getWords(String content) {
        if (content == "") {
            return null;
        } else {
            String[] list = content.split("</p>");
            int i = list[0].lastIndexOf(">");
            int size = list[0].length();
            String s = list[0].substring(i+2, size-1);
            String[] words;
            if (s.contains(" ")){
                words = s.split(" ");
            } else {
                words = s.split("\n");
            }
            return words;
        }
    }

    public static String[] getURLsFromRelativeURLs(String content, String base) {
        if (content == "") {
            return null;
        } else {
            String[] tempSplit = content.split("[.]/");
            String[] relativeURLs = new String[tempSplit.length-1];
            for (int i=1; i< tempSplit.length; i++) {
                String s = tempSplit[i].split("\">")[0];
                relativeURLs[i-1] = base + s;
            }
            return relativeURLs;
        }
    }
}
