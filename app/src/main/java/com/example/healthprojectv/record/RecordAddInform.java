package com.example.healthprojectv.record;

import com.google.firebase.database.Exclude;

public class RecordAddInform {
    String addTaskTitle,addTaskDescription;
    String taskDate,taskTime,status;
    private String key;


    public RecordAddInform(){

    }

//    public boolean getStatus() {
//        return status.equals("Завершенный");
//    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RecordAddInform(String addTaskTitle, String addTaskDescription, String taskDate, String taskTime){
        this.addTaskTitle=addTaskTitle;
        this.addTaskDescription=addTaskDescription;
        this.taskDate=taskDate;
        this.taskTime=taskTime;
//        this.status = status;

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



