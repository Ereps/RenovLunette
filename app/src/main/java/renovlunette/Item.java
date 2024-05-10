package renovlunette;

import javafx.scene.image.Image;

public class Item {
    private String description;
    private double price;
    private Image image;

    public Item(String description, double price, Image image) {
        this.description = description;
        this.price = price;
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
