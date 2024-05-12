package renovlunette;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Item {
    private String description;
    private double price;
    private ArrayList<Image> images;

    public Item(String description, double price, ArrayList<Image> images, String color, String size, String qualityState, String contact, String rib) {
        this.description = description;
        this.price = price;
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
