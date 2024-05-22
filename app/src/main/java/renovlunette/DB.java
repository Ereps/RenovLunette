package renovlunette;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import javafx.embed.swing.SwingFXUtils;


import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.image.Image;
public class DB {
    
    private URI uri;
    private String token = "eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MTUzNzgwOTEsImlkIjoiZGRjZTJkOGQtY2VmOC00MTdmLThhY2YtNmJhMWNjY2I1ZjhiIn0.o_qmZwR-OhPoK4Zp6XRMdG9Ueeiw_axrNEmwJ2fWD5XNeVfr8S5_J5acHsLQI0GuoiYnzYl39kNMDHZSgW57Dg";
    //offset pour les query sur la page menu
    private static int offset = 0;
    //TODO faire un compte avec une autre table pour les utilisateurs en mode quand y'a une vente pour le rib x et bah on update la table
    public DB() {
        createItemsTable();
    }

    public void createItemsTable(){
        query("CREATE TABLE IF NOT EXISTS items (id INTEGER"+
                                                ",image1 VARCHAR(255)"+
                                                ",image2 VARCHAR(255)"+ 
                                                ",image3 VARCHAR(255),"+ 
                                                "description VARCHAR(255),"+
                                                "color VARCHAR(255) NOT NULL,"+
                                                "size VARCHAR(255) NOT NULL,"+
                                                "qualityState VARCHAR(255) NOT NULL,"+
                                                "price VARCHAR(255) NOT NULL,"+
                                                "contact VARCHAR(255),"+
                                                "rib VARCHAR(255) NOT NULL,"+
                                                "PRIMARY KEY (id))");
    }

    /**
     * Query to json request to the server
     * @param query
     */
    public String query(String query){
        HttpResponse<String> response = null;
        try {
            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();
            ja.put(new JSONObject().put("type", "execute").put("stmt", new JSONObject().put("sql", query)));
            ja.put(new JSONObject().put("type", "close"));
            jo.put("requests", ja);
            
            String request = jo.toString();
            
            uri = new URI("https://renovlunette-project-ereps.turso.io/v2/pipeline");

            HttpRequest getRequest = HttpRequest.newBuilder().uri(uri).header("Authorization", "Bearer " + token)
                                        .POST(BodyPublishers.ofString(request)).build();
            HttpClient client = HttpClient.newHttpClient();
            response = client.send(getRequest, BodyHandlers.ofString());
             
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(response.statusCode() != 200){
            System.out.println("Error with the query : "+response.body());
        }
        return response.body();
    }

    public void saveItem(byte[] image1,byte[] image2,byte[] image3,String description, String color, String size, String qualityState, String price, String contact, String rib) {
        query("INSERT INTO items (image1,image2,image3,description, color, size, qualityState, price, contact, rib) VALUES ('"+encodeImage(image1)+"','"+encodeImage(image2)+"','"+encodeImage(image3)+"','"+description+"', '"+color+"', '"+size+"', '"+qualityState+"', '"+price+"', '"+contact+"', '"+rib+"')");
    }
    private String encodeImage(byte[] image){
        if(image == null){
            return null;
        }
        return Base64.getEncoder().encodeToString(image);
    }
    private byte[] decodeImage(String image){
        return Base64.getDecoder().decode(image);
    }

    //TODO faire un truc pour revenir en arriere avec des pages
    public ArrayList<Item> getItems(){
        String resultString = query("SELECT * FROM items LIMIT 5 OFFSET "+offset);
        offset += 5;
        JSONObject jo = new JSONObject(resultString);
        JSONArray ja = jo.getJSONArray("results");
        JSONObject response = ja.getJSONObject(0).getJSONObject("response");
        JSONObject result = response.getJSONObject("result");
        JSONArray rows = result.getJSONArray("rows");
        ArrayList<Item> items = new ArrayList<>();
        for(int i = 0; i < rows.length(); i++){
            JSONArray row = rows.getJSONArray(i);
            int id = Integer.parseInt(getValueFromJson(row.getJSONObject(0)));

            ArrayList<Image> images = new ArrayList<>();
            images.add(getImageFromByte(decodeImage(getValueFromJson(row.getJSONObject(1)))));
            images.add(getImageFromByte(decodeImage(getValueFromJson(row.getJSONObject(2)))));
            images.add(getImageFromByte(decodeImage(getValueFromJson(row.getJSONObject(3)))));
            
            String description = getValueFromJson(row.getJSONObject(4));
            String color = getValueFromJson(row.getJSONObject(5));
            String size = getValueFromJson(row.getJSONObject(6));
            String qualityState = getValueFromJson(row.getJSONObject(7));
            double price = Double.parseDouble(getValueFromJson(row.getJSONObject(8)));
            String contact = getValueFromJson(row.getJSONObject(9));
            String rib = getValueFromJson(row.getJSONObject(10));

            items.add(new Item(id,description, price, images, color, size, qualityState, contact, rib));
        }
        return items;
    }
    public Item getItem(int _id){
        String resultString = query("SELECT * FROM items WHERE id =  "+_id);
        JSONObject jo = new JSONObject(resultString);
        JSONArray ja = jo.getJSONArray("results");
        JSONObject response = ja.getJSONObject(0).getJSONObject("response");
        JSONObject result = response.getJSONObject("result");
        JSONArray rows = result.getJSONArray("rows");
            JSONArray row = rows.getJSONArray(0);
            int id = Integer.parseInt(getValueFromJson(row.getJSONObject(0)));

            ArrayList<Image> images = new ArrayList<>();
            images.add(getImageFromByte(decodeImage(getValueFromJson(row.getJSONObject(1)))));
            images.add(getImageFromByte(decodeImage(getValueFromJson(row.getJSONObject(2)))));
            images.add(getImageFromByte(decodeImage(getValueFromJson(row.getJSONObject(3)))));
            
            String description = getValueFromJson(row.getJSONObject(4));
            String color = getValueFromJson(row.getJSONObject(5));
            String size = getValueFromJson(row.getJSONObject(6));
            String qualityState = getValueFromJson(row.getJSONObject(7));
            double price = Double.parseDouble(getValueFromJson(row.getJSONObject(8)));
            String contact = getValueFromJson(row.getJSONObject(9));
            String rib = getValueFromJson(row.getJSONObject(10));

            Item item = new Item(id,description, price, images, color, size, qualityState, contact, rib);
        return item;
    }
    private Image getImageFromByte(byte[] image) {
        InputStream is = new ByteArrayInputStream(image);
        return new Image(is);
    }
                
    private String getValueFromJson(JSONObject item){
        return item.getString("value");
    }
    public void resetOffset(){
        offset = 0;
    }
}
