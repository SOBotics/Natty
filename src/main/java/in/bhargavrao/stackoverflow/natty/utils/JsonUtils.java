package in.bhargavrao.stackoverflow.natty.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import org.jsoup.parser.Parser;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhargav.h on 10-Sep-16.
 * AKA, TunaLib - All code is courtesy of Lord Tunaki
 */
public class JsonUtils {

    public static JsonObject get(String url, String... data) throws IOException {
        return execute(url, Connection.Method.GET, data);
    }

    public static JsonObject post(String url, String... data) throws IOException {
        return execute(url, Connection.Method.POST, data);
    }

    private static JsonObject execute(String url, Connection.Method method, String... data) throws IOException {
        String key = null;
        String accessToken = null;
        List<String> filtered = new ArrayList<>(data.length);

        for (int i = 0; i + 1 < data.length; i += 2) {
            String name = data[i];
            String value = data[i + 1];
            if ("access_token".equals(name)) {
                accessToken = value;
            } else if ("key".equals(name)) {
                key = value;
            } else {
                filtered.add(name);
                filtered.add(value);
            }
        }


        Connection connection = Jsoup.connect(url)
            .data(filtered.toArray(new String[0]))
            .method(method)
            .ignoreContentType(true)
            .ignoreHttpErrors(true);

        String bearer = accessToken != null ? accessToken : key;
        if (bearer != null && !bearer.isEmpty()) {
            connection = connection.header("Authorization", "Bearer " + bearer);
        }

        Connection.Response response = connection.execute();
        String json = response.body();
        if (response.statusCode() != 200) {
            throw new IOException("HTTP " + response.statusCode() + " fetching URL " + url + ". Body is: " + response.body());
        }
        return new JsonParser().parse(json).getAsJsonObject();
    }

    public static void handleBackoff(JsonObject root) {
        if (root.has("backoff")) {
            int backoff = root.get("backoff").getAsInt();
            System.out.println("Backing off for " + backoff+ " seconds. Quota left "+root.get("quota_remaining").getAsString());
            try {
                Thread.sleep(1000 * backoff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static String escapeHtmlEncoding(String message) {
        return Parser.unescapeEntities(JsonUtils.sanitizeChatMessage(message), false).trim();
    }
    public static String sanitizeChatMessage(String message) {
        return message.replaceAll("(\\[|\\]|_|\\*|`)", "\\\\$1");
    }

}
