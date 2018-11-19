package com.andrydevelops.langnote.dialogFragments.suredialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.andrydevelops.langnote.R;


public abstract class SureDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {


    protected abstract String writeMessage();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(writeMessage())
                .setPositiveButton(R.string.dialog_ok, this)
                .setNegativeButton(R.string.dialog_cancel, null)
                .create();
    }

}
