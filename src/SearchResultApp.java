import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.Stage;

public class SearchResultApp extends Application {
  SearchResultList model;
  SearchResultView view;

  public void start(Stage myApp) {
    model = new SearchResultList();
    view = new SearchResultView(model);

    view.getSearchField().setOnKeyReleased(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent e){view.update();}
    });

    view.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent actionEvent) {
        view.setClick(1);
        handleSearch(false);
      }
    });

    view.getBoostRadioButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent actionEvent) {
        if (view.getBoostRadioButton().isSelected()) {
          handleSearch(true);
        } else {
          handleSearch(false);
        }
      }
    });

    myApp.setTitle("Display Search Results"); // Set title of window
    myApp.setScene(new Scene(view, 600,500)); // Set size of window
    myApp.show();

    view.update();
  }
  public static void main(String[] args) {
    launch(args);
  }

  public void handleSearch(boolean boost){
    ///gets the query from the field
    String query = view.getSearchField().getText();
    model.searchResults(query, boost);
    view.update();
  }
}


