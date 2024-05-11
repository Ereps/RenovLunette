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
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONArray;
public class DB {
    
    private URI uri;
    private String token = "eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MTUzNzgwOTEsImlkIjoiZGRjZTJkOGQtY2VmOC00MTdmLThhY2YtNmJhMWNjY2I1ZjhiIn0.o_qmZwR-OhPoK4Zp6XRMdG9Ueeiw_axrNEmwJ2fWD5XNeVfr8S5_J5acHsLQI0GuoiYnzYl39kNMDHZSgW57Dg";
    private Connection connection;

    public DB() {
        createItemsTable();
 
        
    }

    
    public Connection getConnection() {
        return connection;
    }

    public void createItemsTable(){
        query("DROP TABLE items");
        query("CREATE TABLE IF NOT EXISTS items (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,image BLOB, description VARCHAR(255), color VARCHAR(255), size VARCHAR(255), qualityState VARCHAR(255), price VARCHAR(255), contact VARCHAR(255), rib VARCHAR(255))");
    }

    public void query(String query){       
        try {
            /*String request = query("SELECT * FROM items");
            */
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
            HttpResponse<String> response = client.send(getRequest, BodyHandlers.ofString());
            System.out.println("RESOIIBRD "+response.body()); 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close database connection: " + e.getMessage());
        }
    }

    public void saveItem(String description, String color, String size, String qualityState, String price, String contact, String rib) {
        query("INSERT INTO items (description, color, size, qualityState, price, contact, rib) VALUES ('"+description+"', '"+color+"', '"+size+"', '"+qualityState+"', '"+price+"', '"+contact+"', '"+rib+"')");
    }
}
