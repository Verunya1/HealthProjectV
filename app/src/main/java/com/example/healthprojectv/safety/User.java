package com.example.healthprojectv.safety;

import android.widget.EditText;

public class User {
    public String name;
    public String age;
    public String email;
    public String id;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String comment;
    public User(){

    }
    public User(String name,String age,String email){
        this.age=age;
        this.email=email;
        this.name=name;
    }



}
