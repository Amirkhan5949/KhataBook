package com.example.khatabook.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khatabook.R;
import com.example.khatabook.model.User;
import com.example.khatabook.utils.Constants;
import com.preference.PowerPreference;

public class PasswordActivity extends AppCompatActivity {
    private EditText password;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        submit=findViewById(R.id.submit);
        password=findViewById(R.id.password);

        final String Fid=getIntent().getStringExtra("Fid");
        final String email=getIntent().getStringExtra("email");
        final Constants.Auth auth=(Constants.Auth)getIntent().getSerializableExtra("auth");
        final Constants.AuthType authType=(Constants.AuthType)getIntent().getSerializableExtra("authType");

        User user = PowerPreference.getDefaultFile().getObject("user",User.class,new User());
        user.setEmail(email);
        user.setfId(Fid);
        user.setAuth(auth);
        user.setAuthType(authType);
        PowerPreference.getDefaultFile().setObject("user",user);
        User user1 = PowerPreference.getDefaultFile().getObject("user",User.class,new User());
        Log.i("adsds", "onClick: "+user1.toString());



        Log.i("sfdsfsfc", "onCreate: "+auth);
        Log.i("sfdsfsfc", "onCreate: "+authType);
        Log.i("sfdsfsfc", "onCreate: "+Fid);
        Log.i("sfdsfsfc", "onCreate: "+email);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(PasswordActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().replace(" ","").length()<6){
                    Toast.makeText(PasswordActivity.this, "Enter right password", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent(PasswordActivity.this, PhoneAuthentication.class);
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    intent.putExtra("authType", (Constants.AuthType)getIntent().getSerializableExtra("authType"));
                    intent.putExtra("auth", (Constants.Auth)getIntent().getSerializableExtra("auth"));
                    intent.putExtra("password",password.getText().toString().trim());
                    intent.putExtra("Fid",Fid+"");
                    startActivity(intent);

                    finish();

                }
            }
        });
    }
}
