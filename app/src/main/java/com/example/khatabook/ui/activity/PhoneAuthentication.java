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

import com.example.khatabook.authutils.callback.BooleanCallback;
import com.example.khatabook.authutils.callback.Common;
import com.example.khatabook.model.User;
import com.example.khatabook.utils.Constants;
import com.example.khatabook.utils.Loader;
import com.example.khatabook.R;
import com.preference.PowerPreference;

import com.hbb20.CountryCodePicker;

public class PhoneAuthentication extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText number;
    private Button next;
    private Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);

        countryCodePicker = findViewById(R.id.ccp);
        number = findViewById(R.id.number);
        next = findViewById(R.id.next);
        countryCodePicker.registerCarrierNumberEditText(number);
        loader = new Loader(this);

        final String Fid=getIntent().getStringExtra("Fid");
        final String email=getIntent().getStringExtra("email");

        final Constants.Auth auth=(Constants.Auth)getIntent().getSerializableExtra("auth");
        final Constants.AuthType authType=(Constants.AuthType)getIntent().getSerializableExtra("authType");

        final String password=getIntent().getStringExtra("password");

        User user = PowerPreference.getDefaultFile().getObject("user",User.class,new User());
        user.setEmail(email);
        user.setfId(Fid);
        user.setPassword(password);
        user.setAuth(auth);
        user.setAuthType(authType);
        user.setPassword(password);
        PowerPreference.getDefaultFile().setObject("user",user);

        User user1 = PowerPreference.getDefaultFile().getObject("user",User.class,new User());
        Log.i("adsds", "onClick: "+user1.toString());



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = countryCodePicker.getFullNumberWithPlus().replace(" ","");

                if(TextUtils.isEmpty(number)){
                    Toast.makeText(PhoneAuthentication.this, "Enter No ....", Toast.LENGTH_SHORT).show();
                }
                else if(PhoneAuthentication.this.number.getText().toString().replace(" ","").length()!=10){
                    Toast.makeText(PhoneAuthentication.this, "Enter Correct No ...", Toast.LENGTH_SHORT).show();
                }
                else {
                    loader.show();
                    Common.checkNumberRegistration(number, new BooleanCallback() {
                        @Override
                        public void callback(boolean isValid) {
                            loader.dismiss();
                            if(isValid){
                                Intent intent = new Intent(PhoneAuthentication.this,VerificationActivity.class);
                                intent.putExtra("number",countryCodePicker.getFullNumberWithPlus().replace(" ",""));
                                intent.putExtra("email",getIntent().getStringExtra("email"));
                                intent.putExtra("authType", (Constants.AuthType)getIntent().getSerializableExtra("authType"));
                                intent.putExtra("auth", (Constants.Auth)getIntent().getSerializableExtra("auth"));
                                intent.putExtra("password", getIntent().getStringExtra("password"));
                                intent.putExtra("Fid",Fid+"");
                                Log.i("adadad", "callback: "+auth);
                                Log.i("adadad", "callback: "+authType);

                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(PhoneAuthentication.this, "Number exist.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });



    }
}
