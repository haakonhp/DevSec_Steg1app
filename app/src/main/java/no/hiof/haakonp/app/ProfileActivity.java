package no.hiof.haakonp.app;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity {

    private TextView greetingText;
    private ListView subjectList;
    private final ArrayList<String> subjectArrayList = new ArrayList<>();
    private ListView roleList;
    private final ArrayList<String> roleArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        greetingText = findViewById(R.id.greetingText);
        new FetchUserInfoTask().execute();

        subjectList = findViewById(R.id.subjectList);
        new FetchSubjectsTask().execute();

        roleList = findViewById(R.id.roleList);
        new FetchRolesTask().execute();


    }
    private class FetchUserInfoTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://158.39.188.201/Steg1/api/getUserInfo.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                SharedPreferences sharedPreferences = getSharedPreferences("UUID", MODE_PRIVATE);
                String token = sharedPreferences.getString("uuid", "");
                String WriteToken = "auth_token=" + token;
                dataOutputStream.writeBytes(WriteToken);
                dataOutputStream.flush();
                dataOutputStream.close();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String firstName = jsonObject.getString("firstName");
                String lastName = jsonObject.getString("lastName");
                greetingText.setText("Hello " + firstName + " " + lastName + "!");
            } catch (JSONException e) {
                e.printStackTrace();
        }
        }
    }

    private class FetchRolesTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://158.39.188.201/Steg1/api/getRoles.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                JSONObject jsonObject = new JSONObject();
                SharedPreferences sharedPreferences = getSharedPreferences("UUID", MODE_PRIVATE);
                String token = sharedPreferences.getString("uuid", "");
                jsonObject.put("auth_token", token);
                dataOutputStream.writeBytes(jsonObject.toString());
                dataOutputStream.flush();
                dataOutputStream.close();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String role = jsonObject.getString("role");
                    roleArrayList.add(role);
                }
                Collections.sort(roleArrayList);
                ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_list_item_1, roleArrayList);
                roleList.setAdapter(roleAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class FetchSubjectsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://158.39.188.201/Steg1/api/getSubjects.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                JSONObject jsonObject = new JSONObject();
                SharedPreferences sharedPreferences = getSharedPreferences("UUID", MODE_PRIVATE);
                String token = sharedPreferences.getString("uuid", "");
                jsonObject.put("auth_token", token);
                dataOutputStream.writeBytes(jsonObject.toString());
                dataOutputStream.flush();
                dataOutputStream.close();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String subject = jsonObject.getString("subject");
                    subjectArrayList.add(subject + "\n");
                }
                Collections.sort(subjectArrayList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_list_item_1, subjectArrayList);
                subjectList.setAdapter(arrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }
}