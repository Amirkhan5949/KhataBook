package com.example.khatabook.authutils.callback;

import androidx.annotation.NonNull;

import com.example.khatabook.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Common {

    public static void checkEmailRegistration(final String email, final BooleanCallback booleanCallback){

        FirebaseDatabase.getInstance().getReference()
                .child(Constants.User.key)
                .orderByChild(Constants.User.email)
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()==0){
                            booleanCallback.callback(true);
                        }
                        else {
                            booleanCallback.callback(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public static void checkNumberRegistration(final String number, final BooleanCallback booleanCallback){

        FirebaseDatabase.getInstance().getReference()
                .child(Constants.User.key)
                .orderByChild(Constants.User.number)
                .equalTo(number)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()==0){
                            booleanCallback.callback(true);
                        }
                        else {
                            booleanCallback.callback(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
