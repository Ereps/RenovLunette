package renovlunette;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;


public class SellController implements Initializable{
    @FXML
    TextArea descriptionTextArea;
    @FXML
    ChoiceBox<String> colorCB;
    @FXML
    ChoiceBox<String> sizeCB;
    @FXML
    ChoiceBox<String> qualityStateCB;
    @FXML
    TextField priceTextField;
    @FXML
    TextField contactTextField;
    @FXML
    TextField ribTextField;
    @FXML
    Button returnButton;
    @FXML
    Button validationButton;
    @FXML
    Button img1Button;
    @FXML
    Button img2Button;
    @FXML
    Button img3Button;
    @FXML
    Button newImgButton;
    @FXML
    ImageView img1;
    @FXML
    ImageView img2;
    @FXML
    ImageView img3;
    //TODO mettre les button en non visible et les afficher juste quand une image est mise
    //TODO redimensionner les images pour qu'elles soient toutes de la même taille
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colorCB.getItems().addAll("Rouge", "Bleu", "Vert", "Jaune");
        sizeCB.getItems().addAll("S", "M", "L", "XL");
        qualityStateCB.getItems().addAll("Neuf", "Bon état", "Moyen état");
        // force the field to be numeric only
        priceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    @FXML
    public void goToMenu(ActionEvent actionEvent) throws Exception{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));

        //REFAIRE L'initialisation de la page
        Pane root = (Pane) loader.load();
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }
    @FXML
    public void validate(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Pane root = (Pane) loader.load();
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
        //TODO : vérifier la validité des infos
        String description = descriptionTextArea.getText();
        String color = colorCB.getValue();
        String size = sizeCB.getValue();
        String qualityState = qualityStateCB.getValue();
        String price = priceTextField.getText();
        String contact = contactTextField.getText();
        String rib = ribTextField.getText();
        DB db = new DB();
        Image convertedImg1 = img1.getImage();
        byte[] buf1 = imageToByteArray(convertedImg1);
        byte[] buf2 = null;
        byte[] buf3 = null;
        if(img2.getImage() == null){
            buf2 = null;
        }
        else{
            Image convertedImg2 = img2.getImage();
            buf2 = imageToByteArray(convertedImg2);
        }
        if(img3.getImage() == null){
            buf3 = null;
        }
        else{
            Image convertedImg3 = img3.getImage();
            buf3 = imageToByteArray(convertedImg3);
        }
        
        db.saveItem(buf1,buf2,buf3,description, color, size, qualityState, price, contact, rib);

    }
    @FXML
    public void selectImageFile(ActionEvent actionEvent) throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        if(selectedFile != null){

            if(img1.getImage() == null){
                img1.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
            }
            else if(img2.getImage() == null){
                img2.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
            }
            else{
                img3.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
            }
        }

    }
    @FXML
    public void deleteImage(ActionEvent actionEvent1) throws Exception{
        //TODO : delete the image
        //TODO : si on delete l'image 1, alors l'image 2 devient l'image 1 etc...
    }
    public byte[] imageToByteArray(Image image) throws IOException {
            // Convert JavaFX Image to BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            // Write BufferedImage to ByteArrayOutputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream); // "jpg" can be replaced with the appropriate format
            byteArrayOutputStream.flush();

            // Convert ByteArrayOutputStream to byte array
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            return byteArray;
        }
    
}
