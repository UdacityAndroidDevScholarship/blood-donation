package com.udacity.nanodegree.blooddonation.ui.myprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udacity.nanodegree.blooddonation.R;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Harshvardhan Singh Chawda (harshvardhanpro@gmail.com) on 13/5/18.
 */

public class MyProfileActivity extends AppCompatActivity {
    private ValueEventListener mUserProfileValueEventListener;
    private DatabaseReference mUserProfileDabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        String uid=currentUser.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        TextView tv_blood_group=(TextView) findViewById(R.id.my_blood_group);
        TextView tv_dob=(TextView) findViewById(R.id.my_dob);
        TextView tv_emailid=(TextView) findViewById(R.id.my_emailid);
        TextView tv_name=(TextView) findViewById(R.id.my_name);
        TextView tv_gender=(TextView) findViewById(R.id.my_gender);
        TextView tv_location=(TextView) findViewById(R.id.my_location);
        mUserProfileDabaseReference = mDatabase.child("users").child(uid);
        mUserProfileValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterator<DataSnapshot> userDataIterator = dataSnapshot.getChildren().iterator();
                    while (userDataIterator.hasNext()) {

                        DataSnapshot nextSnapShot = userDataIterator.next();

                        if (nextSnapShot.getKey().equals("bloodGroup")) {
                            tv_blood_group.setText(nextSnapShot.getValue().toString());
                        }
                        else if (nextSnapShot.getKey().equals("dob")) {
                            tv_dob.setText(nextSnapShot.getValue().toString());
                        }
                        else if (nextSnapShot.getKey().equals("email")) {
                            tv_emailid.setText(nextSnapShot.getValue().toString());
                        }
                        else if (nextSnapShot.getKey().equals("fName")) {
                            tv_name.setText(nextSnapShot.getValue().toString());
                        }
                        else if (nextSnapShot.getKey().equals("gender")) {
                            tv_gender.setText(nextSnapShot.getValue().toString());
                        }
                        else if (nextSnapShot.getKey().equals("lName")) {
                            tv_name.setText(tv_name.getText()+" "+nextSnapShot.getValue().toString());
                        }
                        else if (nextSnapShot.getKey().equals("latitude")) {
                            tv_location.setText(nextSnapShot.getValue().toString());
                        }
                        else if (nextSnapShot.getKey().equals("longitude")) {
                            tv_location.setText(tv_location.getText()+":"+nextSnapShot.getValue().toString());
                        }

                    }

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mUserProfileDabaseReference.addValueEventListener(mUserProfileValueEventListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserProfileDabaseReference.removeEventListener(mUserProfileValueEventListener);
    }
}
