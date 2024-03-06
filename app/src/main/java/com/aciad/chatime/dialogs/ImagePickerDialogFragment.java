package com.aciad.chatime.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class ImagePickerDialogFragment extends DialogFragment {
    public static final String TAG = "ImagePickerDialogFragment";
    private final ImagePickerDialogCallback imagePickerDialogCallback;

    public ImagePickerDialogFragment(ImagePickerDialogCallback imagePickerDialogCallback) {
        this.imagePickerDialogCallback = imagePickerDialogCallback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        String[] options = {"Take Picture", "Select From Gallery"};
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    if (imagePickerDialogCallback != null)
                        imagePickerDialogCallback.onCamera();
                    break;
                case 1:
                    if (imagePickerDialogCallback != null)
                        imagePickerDialogCallback.onGallery();
                    break;
            }
        });
        return builder.create();
    }

    public interface ImagePickerDialogCallback {
        void onCamera();
        void onGallery();
    }
}
