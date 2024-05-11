package renovlunette;

import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.layout.Pane;

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
        //TODO : save the item in the database
        //TODO : vérifier la validité des infos
        String description = descriptionTextArea.getText();
        String color = colorCB.getValue();
        String size = sizeCB.getValue();
        String qualityState = qualityStateCB.getValue();
        String price = priceTextField.getText();
        String contact = contactTextField.getText();
        String rib = ribTextField.getText();
        DB db = new DB();
        db.saveItem(description, color, size, qualityState, price, contact, rib);

    }
    
}
