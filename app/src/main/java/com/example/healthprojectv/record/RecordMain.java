package com.example.healthprojectv.record;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthprojectv.R;
import com.example.healthprojectv.broadcastReceiver.AlarmBroadcastReceiver;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
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
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordMain extends Fragment implements View.OnClickListener {

    View rootView;
    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;

    @BindView(R.id.addTask)
    TextView addTask;

    // setRefreshListener setRefreshListener;
    AlarmManager alarmManager;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    int mHour;
    int mMinute;
    EditText title;
    EditText description;
    EditText date;
    EditText time;

    ImageView calendar;
    //private FirebaseOptions
    RecordAdapter recordAdapter;
    public static int count = 0;


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

        taskRecycler = (RecyclerView) rootView.findViewById(R.id.taskRecycler);

        addTask.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                addRecord();
            }
        });


        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addRecord() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = LayoutInflater.from(this.getContext());

        View myView = inflater.inflate(R.layout.add_record, null);
        myDialog.setView(myView);

        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();
        Button add = myView.findViewById(R.id.addTask2);
        Button close = myView.findViewById(R.id.closeButton);
        //-в всплывающем окне определяю кто за что отвечает
        title = myView.findViewById(R.id.addTaskTitle);
        description = myView.findViewById(R.id.addTaskDescription);
        date = myView.findViewById(R.id.taskDate);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        time = myView.findViewById(R.id.taskTime);


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
                            String time_ = hourOfDay + ":" + minute;
                            time.setText(time_);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        add.setOnClickListener(v -> {
            if (title.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Заполните тип напоминания", Toast.LENGTH_SHORT).show();
                return;
            }
            if (description.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Заполните описание", Toast.LENGTH_SHORT).show();
                return;
            }
            if (date.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Заполните дату", Toast.LENGTH_SHORT).show();
                return;
            }
            if (time.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Заполните время", Toast.LENGTH_SHORT).show();
                return;
            }
            Recycler(title.getText().toString(), description.getText().toString(), date.getText().toString(), time.getText().toString());//- передавть сюда данные времени и тд
//            createAnAlarm();
//            createNotification();
            dialog.dismiss();
        });

        close.setOnClickListener(v ->
        {
            dialog.dismiss();

        });


    }


    private void createNotification() {


    }

    private void Recycler(String title, String description, String date, String time) {

//        RecordAddInform recordAddInform = new RecordAddInform("a","a","a","a");
        RecordAddInform recordAddInform = new RecordAddInform(title, description, date, time);
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

//                    if (recordAddInform != null) {
//                        recordAddInform.setKey(postSnapshot.getKey());
//                    }
//                    Log.d("QQQ", recordAddInform.getTaskDate());
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


    @Override
    public void onClick(View v) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createAnAlarm() {
        try {
            String[] items1 = date.getText().toString().split("\\.");
            String dd = items1[0];
            String month = items1[1];
            String year = items1[2];

            String[] itemTime = date.getText().toString().split(":");
            String hour = itemTime[0];
            String min = itemTime[1];

            Calendar cur_cal = new GregorianCalendar();
            cur_cal.setTimeInMillis(System.currentTimeMillis());

            Calendar cal = new GregorianCalendar();
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            cal.set(Calendar.MINUTE, Integer.parseInt(min));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.DATE, Integer.parseInt(dd));

            Intent alarmIntent = new Intent(rootView.getContext(), AlarmBroadcastReceiver.class);
            alarmIntent.putExtra("TITLE", title.getText().toString());
            alarmIntent.putExtra("DESC", description.getText().toString());
            alarmIntent.putExtra("DATE", date.getText().toString());
            alarmIntent.putExtra("TIME", time.getText().toString());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(rootView.getContext(),count, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                }
                count ++;

                PendingIntent intent = PendingIntent.getBroadcast(rootView.getContext(), count, alarmIntent, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - 600000, intent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - 600000, intent);
                    } else {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - 600000, intent);
                    }
                }
                count ++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
