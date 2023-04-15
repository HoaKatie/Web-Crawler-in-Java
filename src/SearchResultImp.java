public class SearchResultImp implements SearchResult, Comparable<SearchResultImp>{

  private String title;
  private double score;

  public SearchResultImp(String title, double score){
    this.title = title;
    this.score = score;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public double getScore() {
    return score;
  }

  @Override
  public int compareTo(SearchResultImp result) {
    Double thisResult = (double) Math.round(this.score * 1000d);
    Double thatResult = (double) Math.round(result.getScore() * 1000d);
    int scoreComparison = thisResult.compareTo(thatResult);
    int titleComparison = this.title.compareTo(result.getTitle());

    if (scoreComparison == 0) {
      return titleComparison;
    } else if (scoreComparison > 0) {
      return -1;
    } else { // (scoreComparison < 0)
      return 1;
    }
  }

}