package net.airstrafe.phatcat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User {
    String id, name, email, pictureUrl;
    ArrayList<Home> homes = new ArrayList<>();
    boolean doneSetup;

    public User(JSONObject json) throws JSONException {
        this.id = json.getString("_id");
        this.name = json.has("name") ? json.getString("name") : "";
        this.doneSetup = json.getBoolean("doneSetup");
        this.email = json.getString("email");
        if (json.has("homes")) {
            JSONArray homesJson =  json.getJSONArray("homes");
            for (int i = 0; i < homesJson.length(); i++) {
                homes.add(new Home(homesJson.getJSONObject(i)));
            }
        }
        if (json.has("pictureUrl")) {
            pictureUrl = json.getString("pictureUrl");
        }
    }
}
