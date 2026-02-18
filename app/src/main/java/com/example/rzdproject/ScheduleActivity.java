package com.example.rzdproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity implements Listener, Removable {

    private LinearLayout cardsContainer;
    private ArrayList<MaintenanceRequest> requests = new ArrayList<>();
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        cardsContainer = findViewById(R.id.cards_container);
        searchEditText = findViewById(R.id.et_search);

        loadSampleData();
        refreshCards();
        setupSearch();
    }

    /**
     * Настраивает обработчик поиска для фильтрации списка заявок
     */
    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    /**
     * Выполняет поиск заявок по введенному запросу
     * @param query текст для поиска
     */
    private void search(String query) {
        cardsContainer.removeAllViews();

        for (MaintenanceRequest request : requests) {
            String searchText = request.getObjectName() + " " + request.getTeam() +
                    " " + request.getWorkType() + " " + request.getDate();

            if (TextUtils.isEmpty(query) || searchText.toLowerCase().contains(query.toLowerCase())) {
                addCard(request);
            }
        }
    }

    /**
     * Создает и добавляет карточку заявки в контейнер
     * @param request объект заявки для отображения
     */
    private void addCard(MaintenanceRequest request) {
        View cardView = LayoutInflater.from(this).inflate(R.layout.item_maintenance_card, cardsContainer, false);

        TextView tvTitle = cardView.findViewById(R.id.tv_title);
        TextView tvTeam = cardView.findViewById(R.id.tv_team);
        TextView tvWorkType = cardView.findViewById(R.id.tv_work_type);
        TextView tvDate = cardView.findViewById(R.id.tv_date);
        View layoutDetails = cardView.findViewById(R.id.layout_details);
        CardView btnEdit = cardView.findViewById(R.id.btn_edit);
        CardView btnDelete = cardView.findViewById(R.id.btn_delete);

        tvTitle.setText(request.getObjectName());
        tvTeam.setText(request.getTeam());
        tvWorkType.setText(request.getWorkType());
        tvDate.setText(request.getDate());

        cardView.setOnClickListener(v -> {
            boolean visible = layoutDetails.getVisibility() == View.VISIBLE;
            layoutDetails.setVisibility(visible ? View.GONE : View.VISIBLE);
            request.setExpanded(!visible);
        });

        btnEdit.setOnClickListener(v -> openEditDialog(request));

        // Открываем диалог удаления при нажатии на кнопку
        btnDelete.setOnClickListener(v -> {
            DeleteConfirmation dialog = DeleteConfirmation.newInstance(request);
            dialog.show(getSupportFragmentManager(), "delete_dialog");
        });

        if (request.isExpanded()) {
            layoutDetails.setVisibility(View.VISIBLE);
        }

        cardsContainer.addView(cardView);
    }

    /**
     * Обновляет отображение карточек с учетом текущего поискового запроса
     */
    private void refreshCards() {
        search(searchEditText.getText().toString());
    }

    /**
     * Загружает тестовые данные заявок для демонстрации работы приложения
     */
    private void loadSampleData() {
        requests.clear();
        requests.add(new MaintenanceRequest("1", "Станция Тверь", "Бригада Б", "Плановая проверка", "15.09.2026"));
        requests.add(new MaintenanceRequest("2", "Сервисное локомотивное депо «Тверь»", "Бригада А", "Техническое обслуживание", "16.09.2026"));
        requests.add(new MaintenanceRequest("3", "Путевая машина ТМ-12", "Бригада А", "Ремонт двигателя", "17.09.2026"));
        requests.add(new MaintenanceRequest("4", "Электровоз ВЛ-10", "Бригада №3", "Технический осмотр", "18.09.2026"));
    }

    /**
     * Открывает диалог редактирования заявки
     * @param request заявка для редактирования
     */
    private void openEditDialog(MaintenanceRequest request) {
        EditRequestDialog dialog = EditRequestDialog.create(request);
        dialog.setListener(this);
        dialog.show(getSupportFragmentManager(), "edit_dialog");
    }

    /**
     * Обрабатывает сохранение отредактированной заявки (из диалога)
     * @param editedRequest отредактированный объект заявки
     */
    @Override
    public void onSave(MaintenanceRequest editedRequest) {
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(editedRequest.getId())) {
                requests.set(i, editedRequest);
                refreshCards();
                Toast.makeText(this, "Данные обновлены", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    /**
     * Реализация метода интерфейса Removable
     * Удаляет заявку по ID
     * @param requestId идентификатор заявки для удаления
     */
    @Override
    public void remove(String requestId) {
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(requestId)) {
                String deletedName = requests.get(i).getObjectName();
                requests.remove(i);
                refreshCards();
                Toast.makeText(this, "Удалено: " + deletedName, Toast.LENGTH_SHORT).show();
                break;
            }
        }
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