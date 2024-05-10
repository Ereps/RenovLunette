package renovlunette;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import org.python.util.PythonInterpreter;
public class DB {
    
    private String url = "https://renovlunette-project-ereps.turso.io";
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
        /*
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("import sys\nsys.path.append('pathToModules if they are not there by default')\nimport yourModule");
        // execute a function that takes a string and returns a string
        PyObject someFunc = interpreter.get("funcName");
        PyObject result = someFunc.__call__(new PyString("Test!"));
        String realResult = (String) result.__tojava__(String.class); */
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
}
