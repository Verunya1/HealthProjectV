package com.example.healthprojectv.record;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthprojectv.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordMain extends Fragment implements View.OnClickListener {

    View rootView;
    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;

    @BindView(R.id.addTask)
    TextView addTask;

   /* @BindView(R.id.addTaskTitle)
    EditText addTaskTitle;
    @BindView(R.id.addTaskDescription)
    EditText addTaskDescription;
    @BindView(R.id.taskDate)
    EditText taskDate;
    @BindView(R.id.taskTime)
    EditText taskTime;*/
 /*   @BindView(R.id.title)
   EditText addTaskTitle;
    @BindView(R.id.description)
    EditText addTaskDescription;
    @BindView(R.id.taskDate)
    EditText taskDate;
    @BindView(R.id.taskTime)
    EditText taskTime;
    на этой активности- фрагменте нет ни имени ни времени ни тд это есть во всплывающем окне
  */

    Task task;
    int mYear, mMonth, mDay;
    int mHour, mMinute;
   // setRefreshListener setRefreshListener;
    AlarmManager alarmManager;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;


    ImageView calendar;
    //private FirebaseOptions
    RecordAdapter recordAdapter;


    LinearLayoutManager layoutManager;

    private List<RecordAddInform> listRecords;

    private DatabaseReference databaseReference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_record, container, false);

        ButterKnife.bind(this, rootView);

        listRecords = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Record");

//        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Recipe").push();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        taskRecycler.setLayoutManager(layoutManager);
        recordAdapter = new RecordAdapter(this.getContext(),listRecords );
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setAdapter(recordAdapter);
        Content();//из бд



//        addTask = (TextView) rootView.findViewById(R.id.addTask);
//        calendar = (ImageView) rootView.findViewById(R.id.calendar);
//        displayAllRecord();


//        databaseReference=FirebaseDatabase.getInstance().getReference("User");


        taskRecycler = (RecyclerView) rootView.findViewById(R.id.taskRecycler);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            addTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addRecord();
                }
            });

//        Recycler();
        return rootView;
    }

    private void addRecord() {
       /* CreateRecordBottomSheetFragment createTaskBottomSheetFragment = new CreateRecordBottomSheetFragment();
        createTaskBottomSheetFragment.setTaskId(0, false, this, MainActivity.this);
        createTaskBottomSheetFragment.show(getSupportFragmentManager(), createTaskBottomSheetFragment.getTag());
*/
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = LayoutInflater.from(this.getContext());

        View myView = inflater.inflate(R.layout.add_record, null);
        myDialog.setView(myView);


        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();
        Button add = myView.findViewById(R.id.addTask2);
        add.setOnClickListener(v -> {
            Recycler();
            dialog.dismiss();
        });
/*
        taskDate.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            taskDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });*/
    }

    private void Recycler() {

//        RecordAddInform recordAddInform = new RecordAddInform("a","a","a","a","a");
        RecordAddInform recordAddInform = new RecordAddInform();
        listRecords.add(recordAddInform);
        databaseReference.push().setValue(recordAddInform);
        recordAdapter.notifyDataSetChanged();
//        Content();
//         добавить когда появится бд и поставить перед 169 строчкой
//        Delete();


    }

    private void Content() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listRecords.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    RecordAddInform recordAddInform = postSnapshot.getValue(RecordAddInform.class);

                    if(recordAddInform!=null){
                        recordAddInform.setKey(postSnapshot.getKey());
                    }
                    listRecords.add(recordAddInform);
                }
                recordAdapter.notifyDataSetChanged();
            }//

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "",error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void initRecyclerView() {
        taskRecycler = (RecyclerView) rootView.findViewById(R.id.taskRecycler);
//    taskRecycler.setLayoutManager(new LinearLayoutManager(RecordMain ));
    }

    private void displayAllRecord() {
//    ListView listView = rootView.findViewById()



    }
public void Delete(){
//        databaseReference.child(listRecords.get(viewHolder.getA));

}

    @Override
    public void onClick(View v) {

    }

}
