package renovlunette;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

import javafx.scene.image.Image;

import org.json.JSONArray;
public class DB {
    
    private URI uri;
    private String token = "eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MTUzNzgwOTEsImlkIjoiZGRjZTJkOGQtY2VmOC00MTdmLThhY2YtNmJhMWNjY2I1ZjhiIn0.o_qmZwR-OhPoK4Zp6XRMdG9Ueeiw_axrNEmwJ2fWD5XNeVfr8S5_J5acHsLQI0GuoiYnzYl39kNMDHZSgW57Dg";
    private Connection connection;
    //offset pour les query sur la page menu
    private static int offset = 0;
    //TODO faire un compte avec une autre table pour les utilisateurs en mode quand y'a une vente pour le rib x et bah on update la table
    public DB() {
        createItemsTable();
    }

    public void createItemsTable(){
        query("CREATE TABLE IF NOT EXISTS items (id INTEGER"+
                                                ",image BLOB,"+ 
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
            System.out.println("RESOIIBRD "+response.body());
             
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(response.statusCode() != 200){
            System.out.println("Error with the query : "+response.body());
        }
        return response.body();
    }

    public void saveItem(String description, String color, String size, String qualityState, String price, String contact, String rib) {
        query("INSERT INTO items (description, color, size, qualityState, price, contact, rib) VALUES ('"+description+"', '"+color+"', '"+size+"', '"+qualityState+"', '"+price+"', '"+contact+"', '"+rib+"')");
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
            //TODO utiliser les blob pour reprendre les images index 1 dans la db
            ArrayList<Image> images = new ArrayList<>();
            String description = getValueFromJson(row.getJSONObject(2));
            String color = getValueFromJson(row.getJSONObject(3));
            String size = getValueFromJson(row.getJSONObject(4));
            String qualityState = getValueFromJson(row.getJSONObject(5));
            double price = Double.parseDouble(getValueFromJson(row.getJSONObject(6)));
            String contact = getValueFromJson(row.getJSONObject(7));
            String rib = getValueFromJson(row.getJSONObject(8));

            items.add(new Item(description, price, images, color, size, qualityState, contact, rib));
            /*for (int j = 0; j < row.length(); j++) {
                JSONObject item = row.getJSONObject(j);
                System.out.println(item);
                int id = 
            }*/

            //TODO changer null en les images
            //items.add(new Item(item.getString("description"), item.getDouble("price"), null, item.getString("color"), item.getString("size"), item.getString("qualityState"), item.getString("contact"), item.getString("rib")));
        }
        System.out.println(items.toString());
        return items;
    }

    private String getValueFromJson(JSONObject item){
        System.out.println(item.getString("value"));
        return item.getString("value");
    }
    public void resetOffset(){
        offset = 0;
    }
}
