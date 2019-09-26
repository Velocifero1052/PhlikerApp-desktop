package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    private int currentPage = 1;
    private String searchUrlTemplate = "https://api.flickr.com/services/rest/?&method=flickr.photos.search&api_key=8c3a08d2ae655faaaa9e19146ec0985d&text={SEARCH_TEXT}&format=json&page=%d";
    @FXML
    private Button searchButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private TextField searchField;


    @FXML
    private void search(ActionEvent event){
        System.out.println("search button click");
        String text = searchField.getText();

        System.out.println(text);


    }

    @FXML
    private void prevButtonClick(ActionEvent event){
        System.out.println("prev button click");
    }

    @FXML
    private void nextButtonClick(ActionEvent event){
        System.out.println("next button click");
    }



}
