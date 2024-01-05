package no.hiof.haakonp.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EmneActivity extends AppCompatActivity {
    private TextView subjectNumberView;
    private TextView subjectNameView;
    private TextView userNameView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emne);

        subjectNumberView = findViewById(R.id.roomIdNumber);
        subjectNameView = findViewById(R.id.roomName);
        userNameView = findViewById(R.id.username);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int subjectId = extras.getInt("subject_id");
            String subjectName = extras.getString("subject_name");
            String userName = extras.getString("user_name");

            subjectNumberView.setText(String.valueOf(subjectId));
            subjectNameView.setText(subjectName);
            userNameView.setText(userName);
        }
    }
}

