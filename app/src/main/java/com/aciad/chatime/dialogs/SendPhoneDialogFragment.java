package com.aciad.chatime.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.aciad.chatime.R;

public class SendPhoneDialogFragment extends DialogFragment {
    public static String TAG = "SendPhoneDialogFragment";
    private final String phoneNumber;
    private final SendPhoneDialogCallback sendPhoneDialogCallback;

    public SendPhoneDialogFragment(String phoneNumber, SendPhoneDialogCallback sendPhoneDialogCallback) {
        this.phoneNumber = phoneNumber;
        this.sendPhoneDialogCallback = sendPhoneDialogCallback;
    }

    public interface SendPhoneDialogCallback {
        void onConfirm();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.phone_number_dialog_title))
                .setMessage(getString(R.string.phone_number_dialog_message, phoneNumber))
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    if (sendPhoneDialogCallback != null)
                        sendPhoneDialogCallback.onConfirm();
                    dialog.cancel();
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel())
                .create();
    }
}
