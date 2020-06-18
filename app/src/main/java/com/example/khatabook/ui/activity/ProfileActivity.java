package com.example.khatabook.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;


import com.example.khatabook.R;
import com.example.khatabook.ui.activity.fregment.CustomersFragment;
import com.example.khatabook.ui.activity.fregment.ProfileFragment;
import com.example.khatabook.ui.activity.fregment.ReportsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static com.example.khatabook.R.*;
import static com.example.khatabook.R.string.close;

public class ProfileActivity extends AppCompatActivity {

   private DrawerLayout drawerLayout;
    private NavigationView navigationView;
   private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private ActionBarDrawerToggle toggle;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_profile);

        String khatatype=getIntent().getStringExtra("khatatype");
        String name=getIntent().getStringExtra("name");
        String businessname=getIntent().getStringExtra("businessname");


        drawerLayout=findViewById(id.drawer);
        navigationView=findViewById(id.navigation);
        toolbar=findViewById(id.toolbar);
        bottomNavigationView=findViewById(id.bottom);
        frameLayout=findViewById(id.frame);

        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.open,R.string.close);
        toggle.syncState();


        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case id.customers:
                        replace(new CustomersFragment());
                        break;

                    case id.reporters:
                        replace(new ReportsFragment());
                        break;

                    case id.profile:
                        replace(new ProfileFragment());
                        break;

                }

            }
        });
    }

    void replace(Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }
}
