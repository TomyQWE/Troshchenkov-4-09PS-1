package com.example.rzdproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    private LinearLayout teamsContainer;
    private ArrayList<Team> teams = new ArrayList<>();
    private static final int MAX_EMPLOYEES_PER_TEAM = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        teamsContainer = findViewById(R.id.teams_container);
        loadSampleData();
        createTeamCards();
    }

    /**
     * Загружает тестовые данные бригад
     */
    private void loadSampleData() {
        teams.clear();

        ArrayList<EmployeeItem> teamAEmployees = new ArrayList<>();
        teamAEmployees.add(new EmployeeItem("Иванов Иван Иванович", "Инженер",
                "+7 (999) 123-45-67", "i.ivanov@rzd.ru"));
        teams.add(new Team("Бригада А", "Электрика", teamAEmployees));

        ArrayList<EmployeeItem> teamBEmployees = new ArrayList<>();
        teamBEmployees.add(new EmployeeItem("Соколова Елена Алексеевна", "Слесарь",
                "+7 (999) 444-55-66", "e.sokolova@rzd.ru"));
        teamBEmployees.add(new EmployeeItem("Петров Петр Петрович", "Механик",
                "+7 (999) 777-88-99", "p.petrov@rzd.ru"));
        teams.add(new Team("Бригада Б", "Электрика", teamBEmployees));

        ArrayList<EmployeeItem> teamCEmployees = new ArrayList<>();
        teamCEmployees.add(new EmployeeItem("Сидорова Анна Викторовна", "Техник",
                "+7 (999) 000-11-22", "a.sidorova@rzd.ru"));
        teamCEmployees.add(new EmployeeItem("Кузнецов Алексей Сергеевич", "Электрик",
                "+7 (999) 222-33-44", "a.kuznetsov@rzd.ru"));
        teamCEmployees.add(new EmployeeItem("Морозова Ольга Андреевна", "Инженер",
                "+7 (999) 111-22-33", "o.morozova@rzd.ru"));
        teams.add(new Team("Бригада В", "Путевое хозяйство", teamCEmployees));

        ArrayList<EmployeeItem> teamDEmployees = new ArrayList<>();
        teamDEmployees.add(new EmployeeItem("Волков Дмитрий Николаевич", "Мастер",
                "+7 (999) 333-44-55", "d.volkov@rzd.ru"));
        teamDEmployees.add(new EmployeeItem("Орлова Марина Сергеевна", "Технолог",
                "+7 (999) 555-66-77", "m.orlova@rzd.ru"));
        teamDEmployees.add(new EmployeeItem("Белов Андрей Петрович", "Электрик",
                "+7 (999) 666-77-88", "a.belov@rzd.ru"));
        teamDEmployees.add(new EmployeeItem("Крылова Ирина Викторовна", "Контролер",
                "+7 (999) 888-99-00", "i.krylova@rzd.ru"));
        teams.add(new Team("Бригада Г", "Связь и сигнализация", teamDEmployees));

        ArrayList<EmployeeItem> teamEEmployees = new ArrayList<>();
        teamEEmployees.add(new EmployeeItem("Федоров Сергей Иванович", "Бригадир",
                "+7 (999) 999-00-11", "s.fedorov@rzd.ru"));
        teamEEmployees.add(new EmployeeItem("Жукова Татьяна Александровна", "Заместитель",
                "+7 (999) 121-23-34", "t.zhukova@rzd.ru"));
        teamEEmployees.add(new EmployeeItem("Громов Михаил Владимирович", "Механик",
                "+7 (999) 232-34-45", "m.gromov@rzd.ru"));
        teamEEmployees.add(new EmployeeItem("Лебедева Наталья Олеговна", "Электрик",
                "+7 (999) 343-45-56", "n.lebedeva@rzd.ru"));
        teamEEmployees.add(new EmployeeItem("Соловьев Павел Дмитриевич", "Слесарь",
                "+7 (999) 454-56-67", "p.soloviev@rzd.ru"));
        teams.add(new Team("Бригада Д", "Ремонт подвижного состава", teamEEmployees));
    }

    /**
     * Создает карточки для всех бригад
     */
    private void createTeamCards() {
        teamsContainer.removeAllViews();
        for (Team team : teams) {
            addTeamCard(team);
        }
    }

    /**
     * Добавляет карточку бригады
     */
    private void addTeamCard(Team team) {
        LayoutInflater inflater = LayoutInflater.from(this);
        CardView teamCard = (CardView) inflater.inflate(R.layout.team_card, teamsContainer, false);

        TextView tvTeamName = teamCard.findViewById(R.id.tv_team_name);
        TextView tvTeamDepartment = teamCard.findViewById(R.id.tv_team_department);
        CardView btnToggleTeam = teamCard.findViewById(R.id.btn_toggle_team);
        TextView tvToggleText = teamCard.findViewById(R.id.tv_toggle_text);
        LinearLayout contentTeam = teamCard.findViewById(R.id.content_team);
        LinearLayout employeesContainer = teamCard.findViewById(R.id.employees_container);
        TextView tvEmployeeCount = teamCard.findViewById(R.id.tv_employee_count);

        // Заполняем данные
        tvTeamName.setText(team.name);
        tvTeamDepartment.setText(team.department);
        tvEmployeeCount.setText(String.valueOf(team.employees.size()));
        tvToggleText.setText("Подробнее"); // Всегда пишется "Подробнее"

        if (team.employees.size() == MAX_EMPLOYEES_PER_TEAM) {
            tvTeamName.setText(team.name + " (полная)");
        }

        // Добавляем сотрудников
        addEmployeesToTeam(team.employees, employeesContainer, inflater);

        // Обработчик клика по кнопке
        btnToggleTeam.setOnClickListener(v -> {
            if (contentTeam.getVisibility() == View.VISIBLE) {
                contentTeam.setVisibility(View.GONE);
            } else {
                contentTeam.setVisibility(View.VISIBLE);
            }
        });

        teamsContainer.addView(teamCard);
    }

    /**
     * Добавляет сотрудников в контейнер
     */
    private void addEmployeesToTeam(ArrayList<EmployeeItem> employees, LinearLayout container, LayoutInflater inflater) {
        container.removeAllViews();

        for (int i = 0; i < employees.size(); i++) {
            EmployeeItem employee = employees.get(i);
            View employeeItem = inflater.inflate(R.layout.team_employee_item, container, false);

            TextView tvEmployeeName = employeeItem.findViewById(R.id.tv_employee_name);
            TextView tvEmployeePosition = employeeItem.findViewById(R.id.tv_employee_position);
            TextView tvEmployeePhone = employeeItem.findViewById(R.id.tv_employee_phone);
            TextView tvEmployeeEmail = employeeItem.findViewById(R.id.tv_employee_email);
            View divider = employeeItem.findViewById(R.id.divider);

            if (i == 0) {
                divider.setVisibility(View.GONE);
            }

            tvEmployeeName.setText(employee.name);
            tvEmployeePosition.setText(employee.position);
            tvEmployeePhone.setText("Телефон: " + employee.phone);
            tvEmployeeEmail.setText("Электронная почта: " + employee.email);

            container.addView(employeeItem);
        }

        if (employees.isEmpty()) {
            TextView warning = new TextView(this);
            warning.setText("В бригаде пока нет сотрудников");
            warning.setTextColor(0xFF757575);
            warning.setTextSize(14);
            warning.setPadding(40, 20, 40, 20);
            warning.setGravity(View.TEXT_ALIGNMENT_CENTER);
            container.addView(warning);
        }

        if (employees.size() >= MAX_EMPLOYEES_PER_TEAM) {
            TextView warning = new TextView(this);
            warning.setText("⚠️ Достигнут максимум " + MAX_EMPLOYEES_PER_TEAM + " сотрудников");
            warning.setTextColor(0xFFD32F2F);
            warning.setTextSize(12);
            warning.setPadding(40, 10, 40, 10);
            warning.setGravity(View.TEXT_ALIGNMENT_CENTER);
            warning.setBackgroundColor(0x22D32F2F);
            container.addView(warning);
        }
    }

    /**
     * Обрабатывает нажатие кнопки "Назад"
     */
    public void ClickGoBack(View view) {
        startActivity(new Intent(this, DispatcherActivity.class));
        finish();
    }
}