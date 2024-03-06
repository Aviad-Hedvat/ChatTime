package com.aciad.chatime.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.aciad.chatime.R;

public class ErrorDialogFragment extends DialogFragment {
    public static String TAG = "ErrorDialogFragment";
    private final String errorMessage;

    public ErrorDialogFragment(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setMessage(errorMessage)
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> dialog.dismiss())
                .create();
    }
}
