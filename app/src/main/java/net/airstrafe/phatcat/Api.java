package net.airstrafe.phatcat;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Api {
    private static String TAG = "phatcat api";
    private static String baseApiUrl = "http://10.0.2.2:3333";
    private static Api instance = null;
    private static String accessToken = "";
    private static OkHttpClient okHttpClient = new OkHttpClient();
    public static User user;

    private Api() {}

    public static void setAccessToken(String accessToken) {
        Api.accessToken = accessToken;
    }

    public static String authenticate(String accessToken) {
            setAccessToken(accessToken);
            Response response;
            try {
                response = Api.get("/user/me");
            } catch (IOException e) {
                return e.getLocalizedMessage() == null ? "Unknown error validating account" : e.getLocalizedMessage();
            }
            if (response.code() != 200) {
                return "Server returned status " + response.code() + " ( " + response.body() + ")";
            }
            try {
                String body = response.body().string();
                Log.w(TAG, body);
                JSONObject bodyJson = new JSONObject(body);
                user = new User(bodyJson);
            } catch (JSONException e) {
                return "Got invalid JSON from server (" + e.getLocalizedMessage() + ")";
            } catch (IOException e) {
                return "Got invalid response from server (" + e.getLocalizedMessage() + ")";
            }
            return null;
    }

    public static Response postSync(Request.Builder requestBuilder) throws IOException {
        requestBuilder.addHeader("access_token", accessToken);
        return okHttpClient.newCall(requestBuilder.build()).execute();
    }

    public static void post(Request.Builder requestBuilder, Callback callback) {
        requestBuilder.addHeader("access_token", accessToken);
        okHttpClient.newCall(requestBuilder.build()).enqueue(callback);
    }

    public static String apiUrl(String path) {
        return baseApiUrl + path + (path.contains("?") ? "&" : "?") + "access_token=" + accessToken;
    }

    public static Request.Builder builder(String path) {
        return new Request.Builder().url(baseApiUrl + (path.startsWith("/") ? path : "/" + path));
    }

    public static Response get(String path) throws IOException {
        Log.w(TAG, apiUrl(path));
//        String url = apiUrl(path);
//        Request.Builder requestBuilder = builder(apiUrl(path));
//        requestBuilder.addHeader("access_token", accessToken);
        return okHttpClient.newCall(new Request.Builder().url(apiUrl(path)).build()).execute();
    }
}
