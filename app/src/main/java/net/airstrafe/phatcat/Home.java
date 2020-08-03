package net.airstrafe.phatcat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home {
    String ownerId, name;
    ArrayList<User> users = new ArrayList<>();

    Home(JSONObject json) throws JSONException {
        this.ownerId = json.getString("owner");
        this.name = json.getString("name");
        if (json.has("users")) {
            JSONArray usersJson = json.getJSONArray("users");
            for (int i = 0; i < usersJson.length(); i++) {
                users.add(new User(usersJson.getJSONObject(i)));
            }
        }
    }

    public boolean isOwner(User user) {
        return user.id.equals(ownerId);
    }
}
