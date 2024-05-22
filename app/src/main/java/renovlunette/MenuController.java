package renovlunette;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.Node;


public class MenuController implements Initializable {
    @FXML
    private Button sellButton;

    private ArrayList<Item> items;
    @FXML
    private ListView<HBox> itemsViewList;
    @FXML
    private GridPane mainGridPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        itemsViewList = new ListView<HBox>();
        
        DB db = new DB();
        items = db.getItems();
        System.out.println(items);
        for (Item item : items) {
            HBox itemBox = new HBox();
            ImageView imageView = new ImageView();
            Label label = new Label(item.getDescription());
            System.out.println("labellllll + " + item.getDescription());
            //TODO: set the image of the item
            imageView.setImage(null);//item.getImages().get(0));
            itemBox.getChildren().add(imageView);
            itemBox.getChildren().add(label);
            itemsViewList.getItems().add(itemBox);
        }
        // Add the itemsViewList to the parent container

        mainGridPane.add(itemsViewList,0,2);
        GridPane.setRowSpan(itemsViewList, GridPane.REMAINING);
        GridPane.setColumnSpan(itemsViewList, GridPane.REMAINING);
        */
    }
    
    @FXML
    public void goToSell(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sell.fxml"));
        Pane root = (Pane) loader.load();
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);

    }
}