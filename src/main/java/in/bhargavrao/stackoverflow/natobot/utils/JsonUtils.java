package in.bhargavrao.stackoverflow.natobot.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.jsoup.parser.Parser;
import org.slf4j.Logger;

/**
 * Created by bhargav.h on 10-Sep-16.
 * AKA, TunaLib - All code is courtesy of Lord Tunaki
 */
public class JsonUtils {
    public static JsonObject get(String url, String... data) throws IOException {
        Connection.Response response = Jsoup.connect(url).data(data).method(Connection.Method.GET).ignoreContentType(true).ignoreHttpErrors(true).execute();
        String json = response.body();
        if (response.statusCode() != 200) {
            throw new IOException("HTTP " + response.statusCode() + " fetching URL " + (url) + ". Body is: " + response.body());
        }
        JsonObject root = new JsonParser().parse(json).getAsJsonObject();
        return root;
    }
    public static void handleBackoff(Logger LOGGER, JsonObject root) {
        if (root.has("backoff")) {
            int backoff = root.get("backoff").getAsInt();
            LOGGER.warn("Backing off {} seconds", backoff);
            try {
                Thread.sleep(1000 * backoff);
            } catch (InterruptedException e) {
                LOGGER.error("Couldn't backoff for {} seconds, was interrupted!", backoff, e);
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
