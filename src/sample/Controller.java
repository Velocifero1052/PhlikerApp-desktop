package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;
import sample.models.Photo;
import sample.models.Photos;
import sample.models.Resp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Controller {

    private Integer currentImage = 0;
    private Integer total;
    private Integer pagesTotal;
    private Integer currentPage = 1;
    private String searchUrlTemplate = "https://api.flickr.com/services/rest/?&method=flickr.photos.search&api_key=8c3a08d2ae655faaaa9e19146ec0985d&text={TEXT}&format=json&page={PAGE}";
    private ArrayList<Photo>photo = new ArrayList<Photo>(100);
    private String searching;
    @FXML
    private Button searchButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label counter;

    @FXML
    private ImageView loaderImageView;

    @FXML
    private Label title;

    @FXML
    private void search(ActionEvent event) throws Exception{

        String text = searchField.getText();
        searching = text.replace(' ', '+');


        System.out.println(text);
        currentImage = 0;
        currentPage = 1;

        String searchUrl = searchUrlTemplate.replace("{TEXT}", searching)
                .replace("{PAGE}", currentPage.toString());
        System.out.println(searchUrl);
        String res = obtainData(searchUrl);
        res = res.substring(14, res.length() - 1);

        JSONObject jsonObject = new JSONObject(res);
        Resp response = new Resp(jsonObject);
        Photos photos = response.getPhotos();
        total = Integer.parseInt(photos.getTotal());

        pagesTotal = photos.getPages();

        counter.setText(currentImage + 1 + " of " + total + " images");

        photo = photos.getData();

        Image image = new Image(createUrlForImage(photo.get(0)));
        title.setText(photo.get(0).getTitle());
        loaderImageView.setImage(image);
        prevButton.setDisable(true);

    }

    @FXML
    private void prevButtonClick(ActionEvent event){
        if(prevButton.isDisable())
            return;
        currentImage--;
        title.setText(photo.get(currentImage).getTitle());
        counter.setText(currentImage + 1 + " of " + total + " images");
        if(currentImage == 0)
            prevButton.setDisable(true);
        loaderImageView.setImage(new Image(createUrlForImage(photo.get(currentImage))));
    }

    @FXML
    private void nextButtonClick(ActionEvent event)throws IOException{


        if(currentImage + 1 == total){
            nextButton.setDisable(true);
            return;
        }

        if(currentImage + 1 == photo.size()){
            currentPage++;
            addData();
        }

        currentImage++;
        title.setText(photo.get(currentImage).getTitle());
        counter.setText(currentImage + 1 + " of " + total + " images");
        prevButton.setDisable(false);
        loaderImageView.setImage(new Image(createUrlForImage(photo.get(currentImage))));

    }

    private String createUrlForImage(Photo photo){
        String answer = "https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg";
        answer = answer.replace("{farm-id}", Integer.toString(photo.getFarm()))
                .replace("{server-id}", photo.getServer())
                .replace("{id}", photo.getId())
                .replace("{secret}", photo.getSecret());
        return answer;
    }

    private String obtainData(String urlString)throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private void addData()throws IOException{
        String searchUrl = searchUrlTemplate.replace("{TEXT}", searching)
                .replace("{PAGE}", currentPage.toString());
        System.out.println(searchUrl);
        String res = obtainData(searchUrl);
        res = res.substring(14, res.length() - 1);
        System.out.println(res);
        JSONObject jsonObject = new JSONObject(res);
        Resp response = new Resp(jsonObject);
        photo.addAll(response.getPhotos().getData());
    }

}
