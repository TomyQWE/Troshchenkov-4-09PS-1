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

public class ObjectsActivity extends AppCompatActivity {

    private LinearLayout objectsContainer;
    private TextView tvObjectsTitle;
    private EditText etSearch;

    private ArrayList<ObjectItem> allObjects = new ArrayList<>();
    private ArrayList<ObjectItem> filteredObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objects);

        objectsContainer = findViewById(R.id.objects_container);
        tvObjectsTitle = findViewById(R.id.tv_objects_title);
        etSearch = findViewById(R.id.et_search);

        loadSampleData();

        updateFilteredObjects("");

        refreshCards();

        setupSearch();
    }


    /**
     * Загружает тестовые данные объектов для демонстрации работы приложения
     */
    private void loadSampleData() {
        allObjects.clear();
        allObjects.add(new ObjectItem("1", "Станция Тверь", "Станция",
                "г.Тверь, ул. Коминтерна, 18", "Бригада Б"));
        allObjects.add(new ObjectItem("2", "Сервисное локомотивное депо «Тверь»", "Депо",
                "г.Тверь, ул. Железнодорожная, 25", "Бригада А"));
        allObjects.add(new ObjectItem("3", "Станция Ржев-Балтийский", "Станция",
                "г.Ржев, Привокзальная площадь, 1", "Бригада В"));
        allObjects.add(new ObjectItem("4", "Путевая машина ТМ-12", "Техника",
                "Депо Тверь", "Бригада А"));
        allObjects.add(new ObjectItem("5", "Электровоз ВЛ-10", "Техника",
                "Депо Тверь", "Бригада Б"));
    }

    /**
     * Настраивает обработчики для поля поиска: отслеживает изменения текста и очистку поля
     */
    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                updateFilteredObjects(searchText);
                refreshCards();
            }
        });

    }

    /**
     * Обновляет отфильтрованный список объектов на основе поискового запроса
     * @param searchText текст для поиска
     */
    private void updateFilteredObjects(String searchText) {
        filteredObjects.clear();

        if (searchText.isEmpty()) {
            filteredObjects.addAll(allObjects);
        } else {
            String query = searchText.toLowerCase().trim();
            for (ObjectItem object : allObjects) {
                String searchString = object.title.toLowerCase() + " " +
                        object.type.toLowerCase() + " " +
                        object.address.toLowerCase() + " " +
                        object.brigade.toLowerCase();

                if (searchString.contains(query)) {
                    filteredObjects.add(object);
                }
            }
        }
    }

    /**
     * Обновляет отображение карточек объектов
     */
    private void refreshCards() {
        objectsContainer.removeAllViews();

        for (ObjectItem object : filteredObjects) {
            addCard(object);
        }

        tvObjectsTitle.setText("Все объекты (" + filteredObjects.size() + ")");
    }

    /**
     * Создает и добавляет карточку объекта в контейнер
     * @param object объект для отображения
     */
    private void addCard(final ObjectItem object) {
        LayoutInflater inflater = LayoutInflater.from(this);
        CardView cardView = (CardView) inflater.inflate(R.layout.object_card, objectsContainer, false);

        TextView tvTitle = cardView.findViewById(R.id.tv_title);
        TextView tvType = cardView.findViewById(R.id.tv_type);
        TextView tvAddress = cardView.findViewById(R.id.tv_address);
        TextView tvBrigade = cardView.findViewById(R.id.tv_brigade);

        tvTitle.setText(object.title);
        tvType.setText("Тип объекта: " + object.type);
        tvAddress.setText("Адрес: " + object.address);
        tvBrigade.setText("Ответственная бригада: " + object.brigade);

        objectsContainer.addView(cardView);
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