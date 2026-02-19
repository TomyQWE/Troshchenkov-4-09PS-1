package com.example.rzdproject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;

public class EditRequestDialog extends DialogFragment {

    private Listener listener;
    private MaintenanceRequest oldRequest;
    private EditText name, team, work, date;

    /**
     * Создает новый экземпляр диалога редактирования с переданными данными заявки
     * @param request объект заявки для редактирования
     * @return настроенный диалог EditRequestDialog
     */
    public static EditRequestDialog create(MaintenanceRequest request) {
        EditRequestDialog dialog = new EditRequestDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", request);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            oldRequest = (MaintenanceRequest) args.getSerializable("data");
        }

        View window = LayoutInflater.from(getActivity())
                .inflate(R.layout.activity_edit_request_dialog, null);

        name = window.findViewById(R.id.et_object_name);
        team = window.findViewById(R.id.et_team);
        work = window.findViewById(R.id.et_work_type);
        date = window.findViewById(R.id.et_date);

        if (oldRequest != null) {
            name.setText(oldRequest.getObjectName());
            team.setText(oldRequest.getTeam());
            work.setText(oldRequest.getWorkType());
            date.setText(oldRequest.getDate());
        }

        window.findViewById(R.id.btn_cancel).setOnClickListener(v -> dismiss());

        window.findViewById(R.id.btn_save).setOnClickListener(v -> saveData());

        return new android.app.AlertDialog.Builder(getActivity())
                .setView(window)
                .create();
    }

    /**
     * Выполняет сохранение отредактированных данных заявки
     * Проверяет заполненность полей и корректность формата даты,
     * создает обновленный объект заявки и передает его через слушатель
     */
    private void saveData() {
        String newName = name.getText().toString().trim();
        String newTeam = team.getText().toString().trim();
        String newWork = work.getText().toString().trim();
        String newDate = date.getText().toString().trim();

        if (newName.isEmpty() || newTeam.isEmpty() || newWork.isEmpty() || newDate.isEmpty()) {
            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            Toast.makeText(getContext(), "Неверный формат даты", Toast.LENGTH_SHORT).show();
            return;
        }

        MaintenanceRequest newRequest = new MaintenanceRequest(
                oldRequest.getId(),
                newName,
                newTeam,
                newWork,
                newDate
        );

        newRequest.setExpanded(oldRequest.isExpanded());

        if (listener != null) {
            listener.onSave(newRequest);
        }

        dismiss();
    }

    /**
     * Устанавливает слушатель для передачи сохраненных данных в активность
     * @param listener объект слушателя, реализующий интерфейс Listener
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }
}