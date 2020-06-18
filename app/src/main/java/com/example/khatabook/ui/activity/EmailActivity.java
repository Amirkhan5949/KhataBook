package com.example.khatabook.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khatabook.R;
import com.example.khatabook.authutils.callback.BooleanCallback;
import com.example.khatabook.authutils.callback.Common;
import com.example.khatabook.model.User;
import com.example.khatabook.utils.Constants;
import com.example.khatabook.utils.Loader;
import com.preference.PowerPreference;

import java.util.regex.Pattern;

public class EmailActivity extends AppCompatActivity {

    private EditText email;
    private Button submit,skip;
    private Loader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        email=findViewById(R.id.email);
        submit=findViewById(R.id.submit);

        loader = new Loader(this);

        final String Fid=getIntent().getStringExtra("Fid");
        final Constants.Auth auth=(Constants.Auth)getIntent().getSerializableExtra("auth");
        final Constants.AuthType authType=(Constants.AuthType)getIntent().getSerializableExtra("authType");

        User user = PowerPreference.getDefaultFile().getObject("user",User.class,new User());
        user.setAuth(auth);
        user.setfId(Fid);
        user.setAuthType(authType);

        PowerPreference.getDefaultFile().setObject("user",user);

        User user1 = PowerPreference.getDefaultFile().getObject("user",User.class,new User());
        Log.i("adsds", "onClick: "+user1.toString());




        skip=findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EmailActivity.this,PasswordActivity.class);
                intent.putExtra("email",email+"");
                intent.putExtra("Fid",Fid+"");
                intent.putExtra("authType",Constants.AuthType.FACEBOOK);
                intent.putExtra("auth",Constants.Auth.REGISTRATION);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = EmailActivity.this.email.getText().toString().replace(" ","");

                if(email.length()==0){
                    Toast.makeText(EmailActivity.this, "Enter email address.", Toast.LENGTH_SHORT).show();
                }
                else if(!Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$").matcher(email).matches()){
                    Toast.makeText(EmailActivity.this, "Enter valid email address.", Toast.LENGTH_SHORT).show();
                }


                else {
                    Common.checkEmailRegistration(email, new BooleanCallback() {
                        @Override
                        public void callback(boolean isValid) {
                            if(isValid){
                                Intent intent=new Intent(EmailActivity.this,PasswordActivity.class);
                                intent.putExtra("email",email+"");
                                intent.putExtra("Fid",Fid+"");
                                intent.putExtra("authType", Constants.AuthType.FACEBOOK);
                                intent.putExtra("auth",Constants.Auth.REGISTRATION);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(EmailActivity.this, "You already registeered.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}

