package com.example.healthprojectv.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthprojectv.notification.Notification;
import com.example.healthprojectv.R;
import com.example.healthprojectv.safety.RegisterUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView register;

    private FirebaseAuth mAuth;
    private EditText emailI, passwordI;
    private Button signIn;
    Notification notification = new Notification();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.registerUser);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.login);
        signIn.setOnClickListener(this);

        emailI = (EditText) findViewById(R.id.emailI);
        passwordI = (EditText) findViewById(R.id.passwordI);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login:
                userLogin();
                break;

        }
    }

    private void userLogin() {

        String email = emailI.getText().toString().trim();
        String password = passwordI.getText().toString().trim();

        if (email.isEmpty()) {
            emailI.setError("Введите ваш email");
            emailI.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailI.setError("Введите ваш email");
            emailI.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordI.setError("Введите ваш пароль");
            passwordI.requestFocus();
            return;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(MainActivity.this, BottomNavigationView.class));
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(MainActivity.this, BottomNavigationView.class));
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container, notification).commit();
                    } else {
                        Toast.makeText(MainActivity.this, "Не удалось войти.Проверьте вводимые данные", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
