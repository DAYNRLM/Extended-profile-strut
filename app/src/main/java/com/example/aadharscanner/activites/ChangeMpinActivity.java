package com.example.aadharscanner.activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aadharscanner.R;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;

public class ChangeMpinActivity extends AppCompatActivity {

    private EditText id, password;
    private Button submit;
    @Nullable
    private String getId, getPassword, savedId, savedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mpin);

        id = (EditText) findViewById(R.id.user_IdTIET);
        password = (EditText) findViewById(R.id.user_PassTIET);
        submit = (Button) findViewById(R.id.loginBTN);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getId = id.getText().toString().trim();
                getPassword = password.getText().toString().trim();
                savedId = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), ChangeMpinActivity.this);
                savedPassword = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginPassword(), ChangeMpinActivity.this);
                if (getId != null && getPassword != null && getId.equalsIgnoreCase(savedId) && getPassword.equalsIgnoreCase(savedPassword)) {
                    AppUtils.getInstance().makeIntent(ChangeMpinActivity.this, MpinActivity.class, true);

                } else {
                    Toast.makeText(ChangeMpinActivity.this, getResources().getText(R.string.invalid_user), Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}