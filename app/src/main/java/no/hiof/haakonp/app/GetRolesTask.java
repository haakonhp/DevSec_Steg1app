package no.hiof.haakonp.app;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetRolesTask {
    private static final String API_URL = "http://158.39.188.201/Steg1/api/getRoles.php";

    public static JSONArray getUserRoles(ProfileActivity profileActivity, String authToken) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String data = "auth_token=" + authToken;
        byte[] postData = data.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
        }

        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Failed to connect to API. Response code: " + responseCode);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }

        JSONArray roles = new JSONArray(response.toString());
        return roles;
    }

}

