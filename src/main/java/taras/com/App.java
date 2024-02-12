package taras.com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static taras.com.utils.Properties.*;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main( String[] args ) {
        try {
            String initialResponse = getResponseFromUrl(ENDPOINT);
            LOGGER.info("Initial response received: " + initialResponse);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred when getting response from URL", e);
            e.printStackTrace();
        }
    }

    private static String getResponseFromUrl(String urlString) {

        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            LOGGER.info("Response code: " + responseCode);

            if (responseCode > 299) {
                LOGGER.log(Level.SEVERE, "Failed to get response from URL. Response code: " + responseCode);
            } else {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }
            }
            connection.disconnect();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred when getting response from from URL:", e);
            e.printStackTrace();
        }

        return response.toString();
    }
}
