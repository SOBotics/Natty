package in.bhargavrao.stackoverflow.natty.model;

import com.google.gson.JsonObject;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class SOUser {
    private String username;
    private long userId;
    private long reputation;
    private String userType;

    public SOUser(String username, long userId, long reputation, String userType) {
        this.username = username;
        this.userId = userId;
        this.reputation = reputation;
        this.userType = userType;
    }

    public SOUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
