package renovlunette;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Item {
    private int id;
        private String description;
    private double price;
    private ArrayList<Image> images;
    private String color;
    private String size;
    private String qualityState;
    private String contact;
    private String rib;

    public Item(int id ,String description, double price, ArrayList<Image> images, String color, String size, String qualityState, String contact, String rib) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.images = images;
        this.color = color;
        this.size = size;
        this.qualityState = qualityState;
        this.contact = contact;
        this.rib = rib;

    }

    public int getId() {
        return id;
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
    public String getColor() {
        return color;
    }
    public String getContact() {
        return contact;
    }
    public String getQualityState() {
        return qualityState;
    }
    public String getRib() {
        return rib;
    }
    public String getSize() {
        return size;
    }
}
