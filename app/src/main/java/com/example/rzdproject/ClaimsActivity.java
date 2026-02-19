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

public class ClaimsActivity extends AppCompatActivity {

    private LinearLayout requestsContainer;
    private TextView tvRequestsTitle, tvNewRequestsCount;
    private EditText etSearch;

    private ArrayList<RequestItem> allRequests = new ArrayList<>();
    private ArrayList<RequestItem> filteredRequests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claims);

        initViews();
        loadSampleData();
        updateFilteredRequests("");
        refreshCards();
        updateCounter();
        setupSearch();
    }

    /**
     * Инициализирует все view-элементы активности
     */
    private void initViews() {
        requestsContainer = findViewById(R.id.requests_container);
        tvRequestsTitle = findViewById(R.id.tv_requests_title);
        tvNewRequestsCount = findViewById(R.id.tv_new_requests_count);
        etSearch = findViewById(R.id.et_search);
    }

    /**
     * Загружает тестовые данные заявок
     */
    private void loadSampleData() {
        allRequests.clear();
        allRequests.add(new RequestItem("1", "Станция Ржев-Балтийский",
                "Неисправность в электричестве", "27.09.2026", "Средний"));
        allRequests.add(new RequestItem("2", "Тяговая подстанция ЭЧЭ-7 «Тверь»",
                "Неисправность в электричестве", "30.09.2026", "Высокий"));
        allRequests.add(new RequestItem("3", "Депо Тверь",
                "Плановый ремонт", "25.09.2026", "Низкий"));
        allRequests.add(new RequestItem("4", "Путевая машина ТМ-12",
                "Техническое обслуживание", "28.09.2026", "Средний"));
        allRequests.add(new RequestItem("5", "Электровоз ВЛ-10",
                "Ремонт двигателя", "01.10.2026", "Высокий"));
    }

    /**
     * Настраивает обработчик для поля поиска
     */
    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                updateFilteredRequests(searchText);
                refreshCards();
            }
        });
    }

    /**
     * Обновляет список отфильтрованных заявок
     */
    private void updateFilteredRequests(String searchText) {
        filteredRequests.clear();

        if (searchText.isEmpty()) {
            for (RequestItem request : allRequests) {
                if (request.visible) filteredRequests.add(request);
            }
        } else {
            String query = searchText.toLowerCase().trim();
            for (RequestItem request : allRequests) {
                if (request.visible && matchesSearch(request, query)) {
                    filteredRequests.add(request);
                }
            }
        }
    }

    /**
     * Проверяет, соответствует ли заявка поисковому запросу
     */
    private boolean matchesSearch(RequestItem request, String query) {
        return request.title.toLowerCase().contains(query) ||
                request.description.toLowerCase().contains(query) ||
                request.priority.toLowerCase().contains(query);
    }

    /**
     * Обновляет отображение карточек заявок
     */
    private void refreshCards() {
        requestsContainer.removeAllViews();

        for (RequestItem request : filteredRequests) {
            addCard(request);
        }

    }

    /**
     * Создает и добавляет карточку заявки
     */
    private void addCard(final RequestItem request) {
        CardView cardView = (CardView) LayoutInflater.from(this)
                .inflate(R.layout.item_request_card, requestsContainer, false);

        TextView tvTitle = cardView.findViewById(R.id.tv_title);
        TextView tvDescription = cardView.findViewById(R.id.tv_description);
        TextView tvDate = cardView.findViewById(R.id.tv_date);
        TextView tvPriority = cardView.findViewById(R.id.tv_priority);

        tvTitle.setText(request.title);
        tvDescription.setText(request.description);
        tvDate.setText(request.date);
        tvPriority.setText(request.priority);

        setupCardButtons(cardView, request);
        requestsContainer.addView(cardView);
    }

    /**
     * Настраивает обработчики для кнопок
     */
    private void setupCardButtons(CardView cardView, final RequestItem request) {
        Button btnReject = cardView.findViewById(R.id.btn_reject);
        Button btnAccept = cardView.findViewById(R.id.btn_accept);

        View.OnClickListener handleAction = v -> {
            if (v == btnReject || v == btnAccept) {
                request.visible = false;
                updateFilteredRequests(etSearch.getText().toString());
                refreshCards();
                updateCounter();
                String message = v == btnReject ? "❌ Заявка отклонена: " : "✅ Заявка принята: ";
                Toast.makeText(this, message + request.title, Toast.LENGTH_SHORT).show();
            }
        };

        btnReject.setOnClickListener(handleAction);
        btnAccept.setOnClickListener(handleAction);
    }

    /**
     * Обновляет счетчик заявок
     */
    private void updateCounter() {
        int totalVisible = 0;
        for (RequestItem request : allRequests) {
            if (request.visible) totalVisible++;
        }
        tvNewRequestsCount.setText(String.valueOf(totalVisible));
    }

    /**
     * Обрабатывает нажатие кнопки "Назад"
     */
    public void ClickGoBack(View view) {
        startActivity(new Intent(this, DispatcherActivity.class));
        finish();
    }
}