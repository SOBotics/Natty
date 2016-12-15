package in.bhargavrao.stackoverflow.natobot.entities;

import com.google.gson.JsonObject;

import javax.json.Json;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class SOUser {
    private String username;
    private int userId;
    private long reputation;
    private String userType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getReputation() {
        return reputation;
    }

    public void setReputation(long reputation) {
        this.reputation = reputation;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return getJson().toString();
    }

    public JsonObject getJson(){
        JsonObject json = new JsonObject();
        json.addProperty("username" , username);
        json.addProperty("userId" , userId);
        json.addProperty("reputation" , reputation);
        json.addProperty("userType" , userType);
        return json;
    }
}
