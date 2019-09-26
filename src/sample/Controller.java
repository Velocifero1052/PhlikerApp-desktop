package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
    @FXML
    private Button searchButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;


    @FXML
    private void search(ActionEvent event){
        System.out.println("search button click");
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
