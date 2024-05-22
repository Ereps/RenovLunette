package renovlunette;

import java.io.File;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


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
        Pane root = (Pane) loader.load();
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }
    @FXML
    public void validate(ActionEvent actionEvent) throws Exception{
        if (checkFields()==true) {
            showErrorAlert("Champ non rempli");
            return;
        };
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
        db.saveItem(description, color, size, qualityState, price, contact, rib);

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

    //fonction pour déterminer si un champ est vide
    @FXML
    public boolean checkFields() {
        boolean isTextFieldEmpty = priceTextField.getText().isEmpty() || contactTextField.getText().isEmpty() || ribTextField.getText().isEmpty();
        boolean isChoiceBoxeUnselected = colorCB.getValue() == null || sizeCB.getValue() == null || qualityStateCB.getValue() == null;
        return (isChoiceBoxeUnselected || isTextFieldEmpty);
    }

    // Méthode pour afficher une alerte d'erreur
    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //fonction pour lock les champs de la fenêtre
    @FXML
    public void lockAll() {
        colorCB.setDisable(true);
        sizeCB.setDisable(true);
        qualityStateCB.setDisable(true);
        priceTextField.setDisable(true);
        contactTextField.setDisable(true);
        ribTextField.setDisable(true);
        returnButton.setDisable(true);
        validationButton.setDisable(true);
    }

    //fonction pour delock les champs de la fenêtre
    @FXML
    public void unlockAll() {
        colorCB.setDisable(false);
        sizeCB.setDisable(false);
        qualityStateCB.setDisable(false);
        priceTextField.setDisable(false);
        contactTextField.setDisable(false);
        ribTextField.setDisable(false);
        returnButton.setDisable(false);
        validationButton.setDisable(false);
    }
}
