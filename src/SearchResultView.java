import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
public class SearchResultView extends Pane{
  private ListView<String> searchResultList;
  private Button searchButton;
  private RadioButton boostRadioButton;
  private TextField searchField;
  private Label titleLabel;
  private int click;


  SearchResultList model;

  public SearchResultView(SearchResultList initResult){
    model = initResult;

    // Add the labels at the top of the pane
    titleLabel = new Label("ENTER YOUR SEARCH QUERY:");
    titleLabel.relocate(30, 10);

    // Create and position the "result" TextField
    searchField = new TextField();
    searchField.relocate(30, 40);
    searchField.setPrefSize(400, 40);

    // Create and position the "Search" Button
    searchButton = new Button("SEARCH");
    searchButton.relocate(475, 20);
    searchButton.setPrefSize(90, 35);

    // Create and position the "BOOST" Button
    boostRadioButton = new RadioButton("BOOST");
    boostRadioButton.relocate(475, 70);
    boostRadioButton.setPrefSize(90, 35);

    // Create and position the ListView
    searchResultList = new ListView<String>();
    searchResultList.relocate(30, 120);
    searchResultList.setPrefSize(535, 355);

    // Add all the components to the window
    getChildren().addAll(titleLabel, searchField, searchButton, boostRadioButton, searchResultList);
    click = 0;
  }

  public void setClick(int click) {
    this.click = click ;
  }

  public Button getSearchButton(){
    return searchButton;
  }
  public RadioButton getBoostRadioButton() {
    return boostRadioButton;
  }
  public TextField getSearchField(){
    return searchField;
  }

  public void update(){
    if(searchField.getText().length() == 0){
      searchButton.setDisable(true);
      boostRadioButton.setDisable(true);
      click = 0;
    }else{
      searchButton.setDisable(false);
      String[] topResults = model.getResults();
      if (topResults[0] != null) {
        ObservableList<String> list = FXCollections.observableArrayList(topResults);
        searchResultList.setItems(list);
        if (click == 1) {
          boostRadioButton.setDisable(false);
        }
      }
    }

  }
}