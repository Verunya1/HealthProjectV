package com.example.healthprojectv.notification;

import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.healthprojectv.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class Notification extends Fragment {
    private DatabaseReference databaseReference;
    View notification;
    EditText comment;
    private Object Menu;
    private List<String> notificationRecords;
    Button save;
    String value;

    public Notification() {
        // require a empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        notification = inflater.inflate(R.layout.fragment_notification, container, false);
        comment = notification.findViewById(R.id.comment);
        save = notification.findViewById(R.id.save);

        notificationRecords = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Comment");
        Content();
        comment.setText(value);
//        comment.setText(notificationRecords.get(0));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notificationRecord = new String(comment.getText().toString());
                notificationRecords.add(notificationRecord);
//                databaseReference.push().setValue(notificationRecord);
                databaseReference.setValue(notificationRecord);
//                Content();
            }
        });
        comment.setText(value);

        return notification;
    }

    private void Content() { //полуение  данных из базы данных
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                value = snapshot.getValue(String.class);
                comment.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });

    }
}