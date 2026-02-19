package com.example.rzdproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.btn_login);

        /**
         * Устанавливает обработчик нажатия на кнопку входа
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });
    }

    /**
     * Выполняет проверку введенных учетных данных пользователя
     * Проверяет заполненность полей и соответствие логина/пароля
     * При успешной авторизации выполняет переход на экран диспетчера
     */
    private void checkCredentials() {
        String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (login.equals(UsersInfo.Login) && password.equals(UsersInfo.Password)) {
            Toast.makeText(this, "Авторизация успешна! Вы вошли как диспетчер", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MainActivity.this, DispatcherActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_LONG).show();
        }
    }
}