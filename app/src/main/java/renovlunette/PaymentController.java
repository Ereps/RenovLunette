package renovlunette;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Scene;

public class PaymentController {
    private int id;
    @FXML
    TextField mailTextField;
    @FXML
    TextField telTextField;
    @FXML
    TextField numTextField;
    @FXML
    TextField dateTextField;
    @FXML
    TextField cvvTextField;
    @FXML
    Button returnButton;
    @FXML
    Button validationButton;
    @FXML
    Pane confirmationPane;
    @FXML
    Button ouiButton;
    @FXML
    Button nonButton;
    @FXML
    Pane effectuePane;
    @FXML
    Button okButton;
    @FXML
    GridPane mainGridPane;

    //vérifie qu'un Text Field ne contient que des chiffres
    @FXML
    private void handleNumericInput(KeyEvent event) {
        TextField sourceTextField = (TextField) event.getSource();
        String input = sourceTextField.getText();
        if (!input.matches("[0-9]*")) {
            sourceTextField.setText(input.replaceAll("[^\\d]", ""));
        }
    }
    public void setId(int id){
        this.id = id;
    }

    //on returnButton / okButton
    @FXML
    public void goToMenu(ActionEvent actionEvent) throws Exception{
        mainGridPane.setDisable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        //REFAIRE L'initialisation de la page
        Pane root = (Pane) loader.load();
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }

    //fonction pour déterminer si un champ est vide
    @FXML
    public boolean checkFields() {
        boolean isTextFieldEmpty = mailTextField.getText().isEmpty() || telTextField.getText().isEmpty() || numTextField.getText().isEmpty() || dateTextField.getText().isEmpty() || cvvTextField.getText().isEmpty();
        return (isTextFieldEmpty);
    }

        // Méthode pour afficher une alerte d'erreur
    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    //on validationButton
    @FXML 
    private void onValidationButton() {
        if (checkFields()==true){
            showErrorAlert("Champ non rempli");
            return;
        }
        mainGridPane.setDisable(true);
        confirmationPane.setVisible(true);
    }

    @FXML
    private void onNonButton() {
        confirmationPane.setVisible(false);
        mainGridPane.setDisable(false);
    }

    @FXML
    private void onOuiButton() {
        confirmationPane.setVisible(false);
        effectuePane.setVisible(true);
        DB db = new DB();
        db.query("DELETE FROM items WHERE id = "+id);

    }

}
