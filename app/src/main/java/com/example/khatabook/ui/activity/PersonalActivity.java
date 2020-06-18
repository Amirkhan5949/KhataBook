package com.example.khatabook.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.khatabook.R;
import com.example.khatabook.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PersonalActivity extends AppCompatActivity {

    EditText name,businessname;
    Button next;
    private DatabaseReference User;
    String khatatype ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        name=findViewById(R.id.name);
        businessname=findViewById(R.id.businessname);
        next=findViewById(R.id.next);
        khatatype=getIntent().getStringExtra("khatatype");





        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User= FirebaseDatabase.getInstance().getReference().child(Constants.User.key);


                HashMap<String,Object> map = new HashMap<>();
                map.put(Constants.User.name,name.getText().toString());
                map.put(Constants.User.business_name,businessname.getText().toString());

                User.child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.i("dsdfsfdf", "onFailure: "+e.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Intent intent=new Intent(PersonalActivity.this,ProfileActivity.class);
                        intent.putExtra("khatatype", Constants.Khatatype.Personal);
                        intent.putExtra("name",name.getText().toString());
                        intent.putExtra("businessname",businessname.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                });


            }
        });

    }
}
