package com.udacity.nanodegree.blooddonation.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.constants.FireBaseConstants;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.injection.Injection;

import java.util.function.LongFunction;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        DatabaseReference reference = Injection.provideFireBaseDatabase().getReference().child(FireBaseConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Snapshot", " Received");

                while (dataSnapshot.getChildren().iterator().hasNext()){
                    User user = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                    Log.d("User", user.fullName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Snapshot", " Cancelled");
            }
        });
    }
}
