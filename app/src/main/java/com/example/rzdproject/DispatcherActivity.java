package com.example.rzdproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.*;
public class DispatcherActivity extends AppCompatActivity {

    TextView textRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);

        setupFunctionCards();
    }

    /**
     * Обрабатывает нажатие кнопки выхода
     * Выполняет переход на главный экран и закрывает текущую активность
     * @param view элемент, вызвавший метод (кнопка выхода)
     */
    public void btn_exit(View view) {
        Intent intent = new Intent(DispatcherActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Выполняет инициализацию и настройку карточек функциональных разделов
     * Находит все карточки по ID, устанавливает отображаемую роль пользователя
     * и назначает обработчики кликов для перехода к соответствующим экранам
     */
    private void setupFunctionCards() {
        CardView cardSchedule = findViewById(R.id.card_schedule);
        CardView cardRequests = findViewById(R.id.card_requests);
        CardView cardObjects = findViewById(R.id.card_objects);
        CardView cardEmployees = findViewById(R.id.card_employees);
        CardView cardTeams = findViewById(R.id.card_teams);
        textRole = findViewById(R.id.textRole);
        textRole.setText(UsersInfo.getRole());

        if (cardSchedule != null) {
            cardSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DispatcherActivity.this, ScheduleActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (cardRequests != null) {
            cardRequests.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DispatcherActivity.this, ClaimsActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (cardObjects != null) {
            cardObjects.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DispatcherActivity.this, ObjectsActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (cardEmployees != null) {
            cardEmployees.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DispatcherActivity.this, EmployeesActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (cardTeams != null) {
            cardTeams.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DispatcherActivity.this, TeamActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }
}