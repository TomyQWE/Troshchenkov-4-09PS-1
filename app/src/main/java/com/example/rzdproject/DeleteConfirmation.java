package com.example.rzdproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DeleteConfirmation extends DialogFragment {

    private Removable removable;
    private MaintenanceRequest requestToDelete;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        removable = (Removable) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Получаем данные заявки из аргументов
        Bundle args = getArguments();
        if (args != null) {
            requestToDelete = (MaintenanceRequest) args.getSerializable("request");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Подтверждение удаления")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Вы хотите удалить \"" + requestToDelete.getObjectName() + "\"?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removable.remove(requestToDelete.getId());
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }

    /**
     * Фабричный метод для создания диалога с данными заявки
     */
    public static DeleteConfirmation newInstance(MaintenanceRequest request) {
        DeleteConfirmation dialog = new DeleteConfirmation();
        Bundle args = new Bundle();
        args.putSerializable("request", request);
        dialog.setArguments(args);
        return dialog;
    }
}