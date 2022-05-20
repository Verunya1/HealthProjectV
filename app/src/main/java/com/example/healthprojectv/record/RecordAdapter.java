package com.example.healthprojectv.record;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthprojectv.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final List<RecordAddInform> recordAddInforms;



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
//        String addTaskTitle = recordAddInforms.get(position).getAddTaskTitle();
//        holder.title.setText(addTaskTitle);

//        RecordMain recordMain = recordMains.get(position);


        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return recordAddInforms.size();
    }



    public void addItems(List<RecordAddInform> recordList){
        recordList.addAll(recordList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        if(recordAddInforms!=null & recordAddInforms.size()>0){
            recordAddInforms.remove(position);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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

//        @BindView(R.id.options)
//        ImageView options;



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


        }


    }
}
