package com.example.healthprojectv.record;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthprojectv.R;

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
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecordAddInform task = recordAddInforms.get(position);
        holder.title.setText(task.getAddTaskTitle());
        holder.description.setText(task.getAddTaskDescription());
        holder.time.setText(task.getTaskTime());
        holder.options.setOnClickListener(view -> showPopUpMenu(view, position));
        try {

            String datw = task.getTaskDate();
            String[] items1 = datw.split("\\.");
            String day1 = items1[0];
            String dd = items1[1];
            String month1 = items1[2];


            holder.day.setText(day1);
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
                            .setMessage("Вы  уверены, что хотите удалить запись" + "?")
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
            }
            return false;
        });
        popupMenu.show();
    }




    @Override
    public int getItemCount() {
        return recordAddInforms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;

        TextView status;
        @BindView(R.id.options)
        ImageView options;
        @BindView(R.id.time)
        TextView time;

        TextView day;
        TextView date;
        TextView month;

        Date date1;
        String outputDateString = null;

        MyViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
            day = view.findViewById(R.id.day);
            date = view.findViewById(R.id.date);
            month = view.findViewById(R.id.month);

        }
    }



    public void addItems(List<RecordAddInform> recordList) {
        recordList.addAll(recordList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (recordAddInforms != null & recordAddInforms.size() > 0) {
            recordAddInforms.remove(position);
        }
        recordAddInforms.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }




}

