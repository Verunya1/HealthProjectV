package com.example.healthprojectv.safety;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthprojectv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText ageR,userName,emailR,passwordR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth=FirebaseAuth.getInstance();

        TextView registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        userName=(EditText) findViewById(R.id.userName);
        ageR=(EditText) findViewById(R.id.ageR);
        emailR=(EditText) findViewById(R.id.emailI);
        passwordR=(EditText) findViewById(R.id.passwordI);


    }

    @Override
    public void onClick(View v) {
            registerUser();
    }

    private void registerUser(){
        String email=emailR.getText().toString().trim();
        String password=passwordR.getText().toString().trim();
        String name=userName.getText().toString().trim();
        String age=ageR.getText().toString().trim();

        if(email.isEmpty()){
            emailR.setError("Укажите свою почту");
            emailR.requestFocus();
            return;
        }
        if(name.isEmpty()){
            userName.setError("Укажите имя пользователя");
            userName.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailR.setError("Пожалуйтса укажите корректную почту");
            emailR.requestFocus();
            return;
        }
        if(age.isEmpty()){
            ageR.setError("Укажите возраст");
            ageR.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordR.setError("Добавьте пароль");
            passwordR.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name,age,email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        Toast.makeText(RegisterUser.this, "Произошла ошибка. Попробуйте еще раз", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterUser.this, "Произошла ошибка. Попробуйте еще раз", Toast.LENGTH_LONG).show();

                        }

                    }
                });


    }
}