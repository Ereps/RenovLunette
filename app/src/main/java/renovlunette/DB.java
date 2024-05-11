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
import com.google.gson.Gson;

public class DB {
    
    private URI uri;
    private String token = "eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MTUzNzgwOTEsImlkIjoiZGRjZTJkOGQtY2VmOC00MTdmLThhY2YtNmJhMWNjY2I1ZjhiIn0.o_qmZwR-OhPoK4Zp6XRMdG9Ueeiw_axrNEmwJ2fWD5XNeVfr8S5_J5acHsLQI0GuoiYnzYl39kNMDHZSgW57Dg";
    private Connection connection;

    public DB() {
        /*
        String jsonPayload = """
                {
                    "requests": [
                        { "type": "execute", "stmt": { "sql": "SELECT * FROM users" } },
                        { "type": "close" }
                    ]
        
                }
                """;

        // Create the HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Create the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // Send the HTTP request and handle the response
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();
            
            // Print the response
            System.out.println("Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        try {
            String request = "{\"requests\": ["+"{ \"type\": \"execute\", \"stmt\": { \"sql\": \"SELECT * FROM items\" } }, { \"type\": \"close\" } ]}";
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

    
    public Connection getConnection() {
        return connection;
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
        /*PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("src/main/python/renovlunette/DBC.py");
        // execute a function that takes a string and returns a string
        PyObject someFunc = interpreter.get("test");
        PyObject result = someFunc.__call__();//new PyString("Test!"));
        //String realResult = (String) result.__tojava__(String.class); */
    }
}
