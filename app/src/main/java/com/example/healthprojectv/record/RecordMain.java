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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthprojectv.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordMain extends Fragment implements View.OnClickListener {

    View rootView;
    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;

    @BindView(R.id.addTask)
    TextView addTask;

//    @BindView(R.id.options)
//    ImageView options;

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


    // setRefreshListener setRefreshListener;
    AlarmManager alarmManager;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
     int mHour ;
     int mMinute;


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
        recordAdapter = new RecordAdapter(this.getContext(), listRecords);
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

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = LayoutInflater.from(this.getContext());

        View myView = inflater.inflate(R.layout.add_record, null);
        myDialog.setView(myView);


        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();
        Button add = myView.findViewById(R.id.addTask2);
        //-в всплывающем окне определяю кто за что отвечает
        EditText title = myView.findViewById(R.id.title);
        EditText description = myView.findViewById(R.id.description);
        EditText date = myView.findViewById(R.id.taskDate);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final EditText time = myView.findViewById(R.id.taskTime);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        myView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date_ = day + "." + month + "." + year;
                        date.setText(date_);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);*/

                timePickerDialog = new TimePickerDialog(getActivity(),
                        (view12, hourOfDay, minute) -> {
                            String time_=hourOfDay+":"+minute;
                            time.setText( time_);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        add.setOnClickListener(v -> {
            Recycler(title.getText().toString(),description.getText().toString(),date.getText().toString(), time.getText().toString());//- передавть сюда данные времени и тд
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

    private void Recycler(String title,String description, String date, String time) {

//        RecordAddInform recordAddInform = new RecordAddInform("a","a","a","a");
        RecordAddInform recordAddInform = new RecordAddInform(title,description,date,time);
        listRecords.add(recordAddInform);
        databaseReference.push().setValue(recordAddInform);
        recordAdapter.notifyDataSetChanged();
        Content();
//         добавить когда появится бд и поставить перед 169 строчкой
//        Delete();


    }

    private void Content() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listRecords.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    RecordAddInform recordAddInform = postSnapshot.getValue(RecordAddInform.class);

                    if (recordAddInform != null) {
                        recordAddInform.setKey(postSnapshot.getKey());
                    }
                    listRecords.add(recordAddInform);
                }
                recordAdapter.notifyDataSetChanged();
            }//

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "",error.getMessage(), Toast.LENGTH_SHORT).show();
                Snackbar.make(rootView, "Ошибка", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    public void Delete() {
//        databaseReference.child(listRecords.get(viewHolder.getA));

   /* ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mDatabaseReference.child(mListCharacter.get(viewHolder.getAdapterPosition()).getKey()).setValue(null);
            mCharacterAdapter.deleteItem(viewHolder.getAdapterPosition());
        }
    };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
    itemTouchHelper.attachToRecyclerView(mRecyclerView);*/

//        PopupMenu popupMenu = new PopupMenu(getContext(),options);
//        popupMenu.getMenuInflater().inflate(R.menu.action_with_a_record, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.menuDelete:
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.AppTheme_Dialog);
//                    alertDialogBuilder.setTitle(R.string.delete_confirmation).setMessage(R.string.sureToDelete).
//                            setPositiveButton(R.string.yes, (dialog, which) -> {
////                                databaseReference.child(listRecords.get(RecyclerView.ViewHolder .getAdapterPosition()).getKey()).setValue(null);
////                                recordAdapter.deleteItem(RecyclerView.ViewHolder.getAdapterPosition());
////                                recordAdapter.notifyItemRemoved();
//
//
//                            })
//                            .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();
//                    break;
//                case R.id.menuUpdate:
////                    CreateTaskBottomSheetFragment createTaskBottomSheetFragment = new CreateTaskBottomSheetFragment();
////                    createTaskBottomSheetFragment.setTaskId(task.getTaskId(), true, context, context);
////                    createTaskBottomSheetFragment.show(context.getSupportFragmentManager(), createTaskBottomSheetFragment.getTag());
//                    break;
//            }
//            return false;
//        });
//        popupMenu.show();


    }


    @Override
    public void onClick(View v) {

    }

}
