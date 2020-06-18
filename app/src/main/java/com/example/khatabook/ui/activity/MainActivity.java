package com.example.khatabook.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khatabook.R;
import com.example.khatabook.authutils.callback.BooleanCallback;
import com.example.khatabook.authutils.callback.Common;
import com.example.khatabook.utils.Constants;
import com.example.khatabook.utils.Loader;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private ImageView fb;
    private EditText email, password;
    private Button login;
    private TextView signup;
    private int RC_SIGN_IN = 0;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity
                        .this, RegistrationActivity.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = MainActivity
                        .this.email.getText().toString().replace(" ", "");
                final String password = MainActivity
                        .this.password.getText().toString().replace(" ", "");

                if (email.length() == 0) {
                    Toast.makeText(MainActivity
                            .this, "Enter email address.", Toast.LENGTH_SHORT).show();
                } else if (!Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\." +
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$").matcher(email).matches()) {
                    Toast.makeText(MainActivity
                            .this, "Enter valid email address.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity
                            .this, "Enter password", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(MainActivity
                            .this, "Enter right password", Toast.LENGTH_SHORT).show();
                } else {
                    loader.show();

                    FirebaseDatabase.getInstance().getReference()
                            .child(Constants.User.key)
                            .orderByChild(Constants.User.email)
                            .equalTo(email)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() != 0) {
                                        loader.dismiss();
                                        String password1 = dataSnapshot.getChildren().iterator().next().child(Constants.User.password).getValue().toString();
                                        if (password1.equals(password)) {
                                            loader.dismiss();
                                            String number = dataSnapshot.getChildren().iterator().next().child(Constants.User.number).getValue().toString();
                                            Intent intent = new Intent(MainActivity
                                                    .this, VerificationActivity.class);
                                            intent.putExtra("number", number);
                                            intent.putExtra("authType", (Constants.AuthType.EMAIL));
                                            intent.putExtra("auth", (Constants.Auth.LOGIN));
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            loader.dismiss();
                                            Toast.makeText(MainActivity
                                                    .this, "Enter the right password", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        loader.dismiss();
                                        Toast.makeText(MainActivity
                                                .this, "Enter valid email", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(
                        MainActivity.this,
                        Arrays.asList("email", "public_profile")
                );
            }
        });

        loginfb();


        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        googleopt();


    }

    private void googleopt() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    private void init() {
        fb = findViewById(R.id.imageView2);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        loader = new Loader(this);
    }

    private void loginfb() {

        callbackManager = CallbackManager.Factory.create();

        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loader.show();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("MainActivity", response.toString());

                                // Application code
                                String email = "";
                                String id = "";

                                try {
                                    email = object.getString("email");

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                                try {
                                    id = object.getString("id");

                                    FirebaseDatabase.getInstance().getReference()
                                            .child(Constants.User.key)
                                            .orderByChild(Constants.User.fb_id)
                                            .equalTo(id)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Log.i("sdcbsd", "onDataChange: " + dataSnapshot.toString());
                                                    loader.dismiss();
                                                    if (dataSnapshot.getChildrenCount() == 1) {
                                                        String number = dataSnapshot.getChildren().iterator().next().child(Constants.User.number).getValue().toString();
                                                        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                                                        intent.putExtra("number", number);
                                                        intent.putExtra("authType", (Constants.AuthType.FACEBOOK));
                                                        intent.putExtra("auth", (Constants.Auth.LOGIN));
                                                        startActivity(intent);
                                                        finish();
                                                    } else {

                                                        Toast.makeText(MainActivity.this, "you have not registered", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }


                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                loader.dismiss();
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            final String email = account.getEmail();
            loader.show();
            Common.checkEmailRegistration(email, new BooleanCallback() {
                @Override
                public void callback(boolean isValid) {
                    if (isValid) {
                        loader.dismiss();
                        Log.i("dkvcdfklm", "not ok.. : ");
                        Toast.makeText(MainActivity.this, "User not exist.", Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseDatabase.getInstance().getReference()
                                .child(Constants.User.key)
                                .orderByChild(Constants.User.email)
                                .equalTo(email)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String number = dataSnapshot.getChildren().iterator().next().child(Constants.User.number).getValue().toString();

                                        loader.dismiss();

                                        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                                        intent.putExtra("number", number);
                                        intent.putExtra("authType", (Constants.AuthType.GOOGLE));
                                        intent.putExtra("auth", (Constants.Auth.LOGIN));

                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.i("dsjcbdskj", "onCancelled: ");
                                    }
                                });
                    }
                }
            });

        } catch (ApiException e) {


            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            Log.i("adasfsd", "handleSignInResult: " + e.toString());
        }


    }
}
