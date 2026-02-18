package com.example.rzdproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;

public class EmployeesActivity extends AppCompatActivity {

    private LinearLayout employeesContainer;
    private TextView tvEmployeesTitle;
    private EditText etSearch;
    private ArrayList<EmployeeItem> allEmployees = new ArrayList<>();
    private ArrayList<EmployeeItem> filteredEmployees = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        employeesContainer = findViewById(R.id.employees_container);
        tvEmployeesTitle = findViewById(R.id.tv_employees_title);
        etSearch = findViewById(R.id.et_search);

        loadSampleData();
        updateFilteredEmployees("");
        refreshCards();
        setupSearch();
    }

    /**
     * Загружает тестовые данные сотрудников для демонстрации работы приложения
     */
    private void loadSampleData() {
        allEmployees.clear();
        allEmployees.add(new EmployeeItem("1", "Иванов Иван Иванович", "Инженер", "Бригада А",
                "+7 (999) 123-45-67", "i.ivanov@rzd.ru"));
        allEmployees.add(new EmployeeItem("2", "Морозова Ольга Андреевна", "Инженер", "Бригада А",
                "+7 (999) 111-22-33", "o.morozova@rzd.ru"));
        allEmployees.add(new EmployeeItem("3", "Соколова Елена Алексеевна", "Слесарь", "Бригада Б",
                "+7 (999) 444-55-66", "e.sokolova@rzd.ru"));
        allEmployees.add(new EmployeeItem("4", "Петров Петр Петрович", "Механик", "Бригада Б",
                "+7 (999) 777-88-99", "p.petrov@rzd.ru"));
        allEmployees.add(new EmployeeItem("5", "Сидорова Анна Викторовна", "Техник", "Бригада В",
                "+7 (999) 000-11-22", "a.sidorova@rzd.ru"));
    }

    /**
     * Настраивает обработчик поиска для фильтрации списка сотрудников
     */
    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                updateFilteredEmployees(searchText);
                refreshCards();
            }
        });
    }

    /**
     * Обновляет отфильтрованный список сотрудников на основе поискового запроса
     * @param searchText текст для поиска
     */
    private void updateFilteredEmployees(String searchText) {
        filteredEmployees.clear();

        if (searchText.isEmpty()) {
            filteredEmployees.addAll(allEmployees);
        } else {
            String query = searchText.toLowerCase().trim();
            for (EmployeeItem employee : allEmployees) {
                String searchString = employee.name.toLowerCase() + " " +
                        employee.position.toLowerCase() + " " +
                        employee.brigade.toLowerCase() + " " +
                        employee.phone.toLowerCase() + " " +
                        employee.email.toLowerCase();

                if (searchString.contains(query)) {
                    filteredEmployees.add(employee);
                }
            }
        }
    }

    /**
     * Обновляет отображение карточек сотрудников
     */
    private void refreshCards() {
        employeesContainer.removeAllViews();

        for (EmployeeItem employee : filteredEmployees) {
            addCard(employee);
        }

        tvEmployeesTitle.setText("Список сотрудников (" + filteredEmployees.size() + ")");
    }

    /**
     * Создает и добавляет карточку сотрудника в контейнер
     * @param employee объект сотрудника для отображения
     */
    private void addCard(final EmployeeItem employee) {
        LayoutInflater inflater = LayoutInflater.from(this);
        CardView cardView = (CardView) inflater.inflate(R.layout.employees_card, employeesContainer, false);

        TextView tvName = cardView.findViewById(R.id.tv_name);
        TextView tvPositionBrigade = cardView.findViewById(R.id.tv_position_brigade);
        TextView tvPhone = cardView.findViewById(R.id.tv_phone);
        TextView tvEmail = cardView.findViewById(R.id.tv_email);
        final LinearLayout detailsContainer = cardView.findViewById(R.id.details_container);

        tvName.setText(employee.name);
        tvPositionBrigade.setText(employee.position + " • " + employee.brigade);
        tvPhone.setText("Номер телефона: " + employee.phone);
        tvEmail.setText("Электронная почта: " + employee.email);

        // Добавляем обработчик клика на всю карточку
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailsContainer.getVisibility() == View.VISIBLE) {
                    detailsContainer.setVisibility(View.GONE);
                } else {
                    detailsContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        employeesContainer.addView(cardView);
    }

    /**
     * Обрабатывает нажатие кнопки "Назад" - возвращает пользователя на экран диспетчера
     * @param view кнопка, вызвавшая метод
     */
    public void ClickGoBack(View view) {
        startActivity(new Intent(this, DispatcherActivity.class));
        finish();
    }
}