package in.bhargavrao.stackoverflow.natobot.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import javax.json.Json;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bhargav.h on 06-Nov-16.
 * Partly taken from Tunaki, with changes made.
 */
public class SentinelUtils {
    public static final String sentinelMainUrl = "http://sentinel.erwaysoftware.com";
    private static final String sentinelPostUrl = sentinelMainUrl+"/posts/new";
    private static final String sentinelFeedbackUrl = sentinelMainUrl+"/feedbacks/new";

    private static HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(10 * 1000);
        conn.setReadTimeout(10 * 1000);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }

    private static String getString(InputStreamReader isr) throws IOException {
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();
        return sb.toString();
    }

    public static JsonObject postCall(JsonObject json, String url){
        try {
            HttpURLConnection connection = getConnection(url);
            try (OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream())) {
                osw.write(json.toString());
                osw.flush();
            }
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println(responseCode);
                System.out.println(connection.getResponseMessage());
                return null;
            }

            try (InputStreamReader isr = new InputStreamReader(connection.getInputStream())) {
                String response = getString(isr);
                JsonObject rootResponse = new JsonParser().parse(response).getAsJsonObject();
                return rootResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long post(JsonObject json) {
        JsonObject rootResponse = postCall(json,sentinelPostUrl);
        if(rootResponse == null) return -1;
        return rootResponse.get("data").getAsJsonObject().get("post_id").getAsLong();
    }

    public static long feedback(JsonObject json){
        JsonObject rootResponse = postCall(json,sentinelFeedbackUrl);
        if(rootResponse == null) return -1;
        return rootResponse.get("data").getAsJsonObject().get("feedback_id").getAsLong();
    }
}
