package com.example.healthprojectv.record;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthprojectv.R;
import com.example.healthprojectv.bottomSheetFragment.CreateRecordBottomSheetFragment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final List<RecordAddInform> recordAddInforms;

    public SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.US);
    public SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-M-yyyy", Locale.US);




    public RecordAdapter(Context context, List<RecordAddInform> recordAddInforms) {
        this.inflater = LayoutInflater.from(context);
        this.recordAddInforms = recordAddInforms;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
//        View itemView = LayoutInflater.from(context).inflate(R.layout.item_record,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecordAddInform task = recordAddInforms.get(position);
        holder.title.setText(task.getAddTaskTitle());
        holder.description.setText(task.getAddTaskDescription());
        holder.time.setText(task.getTaskTime());
        //-отработать нажатие активный неактивный
//        holder.status.setText(task.getStatus() ? "Завершенный" : "Активный");
        holder.options.setOnClickListener(view -> showPopUpMenu(view, position));

        try {
            holder.date1 = inputDateFormat.parse(task.getTaskDate());
            holder.outputDateString = dateFormat.format(holder.date1);
//            DateFormat.getDateInstance().format("dd.mm.yyyy")
            String[] items1 = holder.outputDateString.split(".");
            String day1 = items1[0];
            String dd = items1[1];
            String month1 = items1[2];

            holder.day.setText("day1");
            holder.day.getText();
//            Log.d("123",holder.day.getText().toString());
            holder.date.setText(dd);
            holder.month.setText(month1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPopUpMenu(View view, int position) {
         RecordAddInform task = recordAddInforms.get(position);
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.action_with_a_record, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuDelete:
//
                    AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
                    builder.setTitle("Удаление")
                            .setMessage("Вы  уверены, что хотите удалить запись"  + "?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    recordAddInforms.remove(position);
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    builder.show();
                    break;
                case R.id.menuUpdate:


                    AlertDialog.Builder myDialog = new AlertDialog.Builder(view.getContext());
                    LayoutInflater inflater = LayoutInflater.from(view.getContext());

                    View myView = inflater.inflate(R.layout.add_record, null);
                    myDialog.setView(myView);

                    AlertDialog dialog = myDialog.create();
                    dialog.setCancelable(false);
//                    recordAddInforms.

                    dialog.show();
                    Button add = myView.findViewById(R.id.addTask2);
                    add.setOnClickListener(v -> {
                        dialog.dismiss();
                    });


                    break;
            }
            return false;
        });
        popupMenu.show();
    }

/*    public void showCompleteDialog(int taskId, int position) {
        Dialog dialog = new Dialog(context, R.style.AppTheme);
        dialog.setContentView(R.layout.dialog_completed_theme);
        Button close = dialog.findViewById(R.id.closeButton);
        close.setOnClickListener(view -> {
            deleteTaskFromId(taskId, position);
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }*/

/*

    private void deleteTaskFromId(int taskId, int position) {
        class GetSavedTasks extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                DatabaseClient.getInstance(context)
                        .getAppDatabase()
                        .dataBaseAction()
                        .deleteTaskFromId(taskId);

                return taskList;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                removeAtPosition(position);
                setRefreshListener.refresh();
            }
        }
        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }
*/


    @Override
    public int getItemCount() {
        return recordAddInforms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       /* @BindView(R.id.day)
        TextView day;*/
        /*@BindView(R.id.date)
        TextView date;*/
       /* @BindView(R.id.month)
        TextView month;*/
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.options)
        ImageView options;
        @BindView(R.id.time)
        TextView time;

        TextView day;
        TextView date;
        TextView month;

        Date date1 ;
        String outputDateString = null;
        MyViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
            day=view.findViewById(R.id.day);
            date=view.findViewById(R.id.date);
            month=view.findViewById(R.id.month);

        }
    }
//        holder.onBind(position);






    public void addItems(List<RecordAddInform> recordList){
        recordList.addAll(recordList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        if(recordAddInforms!=null & recordAddInforms.size()>0){
            recordAddInforms.remove(position);
        }
        recordAddInforms.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

 /*   public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.options)
        ImageView options;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.day)
        TextView day;

        @BindView(R.id.month)
        TextView month;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.status)
        TextView status;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
        protected void clear(){
            title.setText("");
            description.setText("");
            day.setText("");
            month.setText("");
            date.setText("");
            status.setText("");
        }
        public void onBind(int position){
//            super.onBind(position); -?

            RecordAddInform recordAddInform= recordAddInforms.get(position);

            if(recordAddInform.getAddTaskTitle()!=null){
                title.setText(recordAddInform.getAddTaskTitle());
            }

            if(recordAddInform.getAddTaskDescription()!=null){
                description.setText(recordAddInform.getAddTaskDescription());
            }

//            itemView.setOnClickListener(v -> {
//                Intent intent = new Intent(itemView.getContext(),)
//            });
//            if(recordAddInform.()!=null){
//                day.setText(recordAddInform.getAddTaskTitle());
//            }
//
//            if(recordAddInform.getAddTaskTitle()!=null){
//                month.setText(recordAddInform.getAddTaskTitle());
//            }
//
//            if(recordAddInform.getAddTaskTitle()!=null){
//                date.setText(recordAddInform.getAddTaskTitle());
//            }


        }*/



    }

