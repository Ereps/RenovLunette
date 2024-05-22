package renovlunette;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;


public class MoreItemController implements Initializable {
    @FXML
    private Button returnButton;

    private ArrayList<Item> items;
    @FXML
    private ListView<HBox> itemsViewList;
    @FXML
    private GridPane mainGridPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refresh();
        



    }

    public void refresh(){
        itemsViewList = new ListView<HBox>();
        DB db = new DB();
        items = db.getItems();
        System.out.println(items);
        for (Item item : items) {
            HBox itemBox = new HBox();
            System.out.println();
            //TO know which item is clicked
            Label id = new Label(item.getId()+"");
            id.setVisible(false);
            ImageView imageView = new ImageView(item.getImages().get(0));//item.getImages().get(0));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            Label label = new Label(item.getDescription());
            itemBox.getChildren().add(imageView);
            itemBox.getChildren().add(label);
            itemBox.getChildren().add(id);
            itemsViewList.getItems().add(itemBox);
        }
        // when an item is clicked
        itemsViewList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HBox selectedItem = itemsViewList.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Perform your action here
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/descItem.fxml"));
                        Pane root = (Pane) loader.load();
                        Scene scene = ((Node) event.getSource()).getScene();
                        Pane mainPane = (Pane) scene.getRoot();
                        mainPane.getChildren().setAll(root);
                        DescItemController descItemController = loader.getController();
                        descItemController.initData(Integer.parseInt(((Label)selectedItem.getChildren().get(2)).getText()));
                        System.out.println("Clicked on " + ((Label)selectedItem.getChildren().get(2)).getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                }
            }
        });

    // Add the itemsViewList to the parent container
    mainGridPane.add(itemsViewList,0,2);
    GridPane.setRowSpan(itemsViewList, GridPane.REMAINING);
    GridPane.setColumnSpan(itemsViewList, GridPane.REMAINING);

    }
    
    @FXML
    public void goToMenu(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Pane root = (Pane) loader.load();
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);

    }
    @FXML
    public void goToMore(ActionEvent actionEvent) throws Exception{
        refresh();
    }
}