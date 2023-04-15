import java.util.*;

public class CrawlCalculation {
    private  URL page;
    public CrawlCalculation() {
        page = new URL();
    }

    public void createIDF(LinkedHashMap<String, HashSet<String>> urlsMapping, HashSet<String> allWords, HashMap<String,String[]> urlsWords) {
        int totalCount = urlsMapping.size();
        for(String word: allWords) {
            int count = 0;
            for (String url: urlsMapping.keySet()) {
                HashSet<String> wordsInURL = new HashSet<>(Arrays.asList(urlsWords.get(url)));
                if (wordsInURL.contains(word))
                    count++;
            }
            page.setIdf(Math.log((float)totalCount/(1+count)) / Math.log(2));
            FileHandling.writeFileData(FileHandling.filePath,"idf", word + ".txt", page.getIdf(), 0, false); // WRITE IDF IN FILE
        }
    }

    public void createTF_TFIDF(LinkedHashMap<String, HashSet<String>> urlsMapping,  HashSet<String> allWords, HashMap<String,String[]> urlsWords) {
        for(String url: urlsMapping.keySet()) {
            String title = URL.getURLTitle(url);
            int totalWords = urlsWords.get(url).length;
            int count;

            for (String word: allWords) {
                page.setIdf(FileHandling.readFileData(FileHandling.filePath, "idf", word + ".txt", false));  // READ IDF
                count = Collections.frequency(List.of(urlsWords.get(url)), word); // number of occurrence of the word in the url
                page.setTf((double) count / totalWords);
                page.setTfidf(((Math.log(1+page.getTf()) / Math.log(2)) * page.getIdf()));
                FileHandling.writeFileData(FileHandling.filePath, title, word + ".txt", page.getTf(), page.getTfidf(), true); // WRITE TF and TFIDF
            }
        }
    }

    public void createPageRank(LinkedHashMap<String, HashSet<String>> urlsMapping) {
        int N = urlsMapping.size();
        String[] keySiteMapping = new String[N];
        String[][] strMatrix = new String[N][N];

        int row = 0;
        for (String i: urlsMapping.keySet()) { // CREATE NXN MATRIX
            int col = 0;
            keySiteMapping[row] = i;
            for (String j: urlsMapping.keySet()) {
                if (urlsMapping.get(i).contains(j)) {
                    strMatrix[row][col] = "1";
                } else {
                    strMatrix[row][col] = "0";
                }
                col++;
            }
            row++;
        }
        double[][] myMatrix = new double[N][N];
        for (int i=0; i<N; i++) {
            List<String> tempRow = Arrays.asList(strMatrix[i]);
            int count;
            double value;
            if (tempRow.contains("1")) {
                count = Collections.frequency(tempRow, "1");
                value = (double) 1 / count;
                for (int j = 0; j < N; j++) {
                    if (Objects.equals(strMatrix[i][j], "1")) {
                        myMatrix[i][j] = value;
                    } else {
                        myMatrix[i][j] = 0;
                    }
                }
            } else {
                value = (double) 1 / N;
                for (int j = 0; j < N; j++) {
                    myMatrix[i][j] = value;
                }
            }
        }

        for (int r=0; r<N; r++) { // MATRIX MULTIPLICATION SCALAR and ADDITION
            for (int c=0; c<N; c++) { //matrix[0].length
                myMatrix[r][c] = myMatrix[r][c] * 0.9d + 0.1d/N;
            }
        }

        double[][] prev = new double[1][N];
        for (int x=0; x<N; x++) {
            prev[0][x] = (double)1/N;
        }
        double[][] curr = MatMult.matrixMultiplication(prev, myMatrix);
        double euclidean = MatMult.euclideanDistance(prev, curr);

        while (euclidean > 0.0001d) {
            prev = curr;
            curr = MatMult.matrixMultiplication(curr, myMatrix);
            euclidean = MatMult.euclideanDistance(prev, curr);
        }

        int index = 0;
        for (String url: keySiteMapping){
            String title = URL.getURLTitle(url);
            page.setPageRank(curr[0][index]);
            FileHandling.writeFileData(FileHandling.filePath, title, "pageRank.txt", page.getPageRank(), 0, false ); // WRITE PAGERANK
            index++;
        }
    }
}
