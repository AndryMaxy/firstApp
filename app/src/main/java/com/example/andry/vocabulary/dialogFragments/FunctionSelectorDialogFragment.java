package com.example.andry.vocabulary.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.example.andry.vocabulary.R;
import com.example.andry.vocabulary.Word;
import com.example.andry.vocabulary.WordListFragment;
import com.example.andry.vocabulary.dialogFragments.suredialogs.SureDeletePair;
import com.example.andry.vocabulary.dialogFragments.suredialogs.SureMovePair;


public class FunctionSelectorDialogFragment extends DialogFragment{



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        CharSequence[] charSequence = {
                getString(R.string.edit_func),
                getString(R.string.move_func),
                getString(R.string.delete_func)
        };
        return new AlertDialog.Builder(getActivity()).setItems(charSequence, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Word word = getArguments().getParcelable(WordListFragment.ARG_WORD);
                Bundle args = new Bundle();
                args.putParcelable(WordListFragment.ARG_WORD, word);
                switch (which) {
                    case 0:
                        EditWordDialogFragment editWord= new EditWordDialogFragment();
                        editWord.setArguments(args);
                        editWord.show(getFragmentManager(), null);
                        break;
                    case 1:
                        SureMovePair movePair = new SureMovePair();
                        movePair.setArguments(args);
                        movePair.show(getFragmentManager(), null);
                        break;
                    case 2:
                        SureDeletePair deletePair = new SureDeletePair();
                        deletePair.setArguments(args);
                        deletePair.show(getFragmentManager(), null);
                        break;
                }
            }
        }).setTitle(R.string.what_to_do).create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
