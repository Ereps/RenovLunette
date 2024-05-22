package renovlunette;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class DescItemController implements Initializable{
    @FXML
    Text descriptionText;
    @FXML
    Text colorText;
    @FXML
    Text sizeText;
    @FXML
    Text qualityStateText;
    @FXML
    Text priceText;
    @FXML
    Text contactText;

    @FXML
    Button returnButton;
    @FXML
    Button buyButton;
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

    }
    public DescItemController(){
        
    }
    public void initData(int id){
        DB db = new DB();
        Item item = db.getItem(id);
        descriptionText.setText(item.getDescription());
        colorText.setText(item.getColor());
        sizeText.setText(item.getSize());
        qualityStateText.setText(item.getQualityState());
        priceText.setText(item.getPrice()+"");
        contactText.setText(item.getContact());
        img1.setImage(item.getImages().get(0));
        if(item.getImages().get(1) == null){
            img2.setVisible(false);
            img3.setVisible(false);
        }
        else if(item.getImages().get(2) == null){
            img2.setImage(item.getImages().get(1));
            img3.setVisible(false);
        }
        else{
            img2.setImage(item.getImages().get(1));
            img3.setImage(item.getImages().get(2));
        }
    }
    @FXML
    public void goToMenu(ActionEvent actionEvent) throws Exception{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));

        //REFAIRE L'initialisation de la page
        Pane root = (Pane) loader.load();
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
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

    @FXML
    public void buy(ActionEvent actionEvent) throws Exception{
        //TODO
    }


}
