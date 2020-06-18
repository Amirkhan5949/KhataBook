package com.example.khatabook.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.khatabook.R;
import com.example.khatabook.utils.Constants;

public class DashBoardActivity extends AppCompatActivity {
    LinearLayout personallayout,businesslayout;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        personallayout=findViewById(R.id.personallayout);
        businesslayout=findViewById(R.id.businesslayout);
        next=findViewById(R.id.next);

        personallayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashBoardActivity.this,PersonalActivity.class);
                 intent.putExtra("khatatype", Constants.Khatatype.Personal);
                startActivity(intent);
                finish();

            }
        });

        businesslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashBoardActivity.this,PersonalActivity.class);
                intent.putExtra("khatatype", Constants.Khatatype.Business);
                startActivity(intent);
                finish();

            }
        });
    }
}
