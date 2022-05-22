package com.example.healthprojectv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.*;
public class Notification extends Fragment {
    private DatabaseReference databaseReference;
    View notification;
    EditText comment;
    public Notification(){
        // require a empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        notification=inflater.inflate(R.layout.fragment_notification, container, false);
        comment=notification.findViewById(R.id.comment);


//        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Comment");
//        databaseReference.push().setValue(comment.getText().toString());



        return notification;
    }
}