package com.example.healthprojectv.record;

import android.widget.EditText;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class RecordAddInform {
    String addTaskTitle,addTaskDescription;
    String taskDate,taskTime,id;
    private String key;


    public RecordAddInform(){

    }

    public RecordAddInform(String addTaskTitle,String addTaskDescription,String taskDate,String taskTime,String id){
        this.addTaskTitle=addTaskTitle;
        this.addTaskDescription=addTaskDescription;
        this.taskDate=taskDate;
        this.taskTime=taskTime;
        this.id = id;

//        this.taskDate = new Date().getTime();

    }

    public String getAddTaskTitle() {
        return addTaskTitle;
    }

    public void setAddTaskTitle(String addTaskTitle) {
        this.addTaskTitle = addTaskTitle;
    }

    public String getAddTaskDescription() {
        return addTaskDescription;
    }

    public void setAddTaskDescription(String addTaskDescription) {
        this.addTaskDescription = addTaskDescription;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}



