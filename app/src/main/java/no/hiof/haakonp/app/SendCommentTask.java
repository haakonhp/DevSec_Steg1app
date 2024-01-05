package no.hiof.haakonp.app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SendCommentTask {
    private final String USER_AGENT = "Mozilla/5.0";
    private final String url = "http://158.39.188.201/Steg1/api/sendComment.php";

    public static void main(String[] args) throws Exception {
        SendCommentTask request = new SendCommentTask();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("auth_token", "your_auth_token");
        parameters.put("room_id", "room_id");
        parameters.put("text", "your_comment");
        parameters.put("reply_id", "reply_id");

        String response = request.sendPost(parameters);

        System.out.println("Response: " + response);
    }

    private String sendPost(Map<String, String> parameters) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "";
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            urlParameters += entry.getKey() + "=" + entry.getValue() + "&";
        }
        urlParameters = urlParameters.substring(0, urlParameters.length() - 1);

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}

