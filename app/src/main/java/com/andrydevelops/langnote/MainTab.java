package com.andrydevelops.langnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.andry.www.R;

public abstract class MainTab extends Fragment implements View.OnClickListener {

    private static final String TAG = "MainTab";
    public static final String ARG_PART_OF_SPEECH = "part_of_speech";
    public static final String ARG_IS_REMEMBERED = "is_remembered";

    private Button mNounButton;
    private ImageButton mAddNounBt;
    private Button mVerbButton;
    private ImageButton mAddVerbBt;
    private Button mAdjectiveButton;
    private ImageButton mAddAdjectiveBt;
    private Button mAdverbButton;
    private ImageButton mAddAdverbBt;
    private Button mOtherButton;
    private ImageButton mAddOtherBt;
    private boolean mIsRemembered;

    protected abstract boolean isRemembered();
    protected abstract int getLayoutResId();
    protected abstract void manipulate(PartOfSpeech partOfSpeech);


    protected void setRemembered(){
        mIsRemembered = isRemembered();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRemembered();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResId(), container, false);

        mNounButton = rootView.findViewById(R.id.nounButton);
        mNounButton.setOnClickListener(this);

        mAddNounBt = rootView.findViewById(R.id.addNounBt);
        mAddNounBt.setOnClickListener(this);

        mVerbButton = rootView.findViewById(R.id.verb_button);
        mVerbButton.setOnClickListener(this);

        mAddVerbBt = rootView.findViewById(R.id.add_verb_bt);
        mAddVerbBt.setOnClickListener(this);

        mAdjectiveButton = rootView.findViewById(R.id.adjective_button);
        mAdjectiveButton.setOnClickListener(this);

        mAddAdjectiveBt = rootView.findViewById(R.id.add_adj_bt);
        mAddAdjectiveBt.setOnClickListener(this);

        mAdverbButton = rootView.findViewById(R.id.adverb_button);
        mAdverbButton.setOnClickListener(this);

        mAddAdverbBt = rootView.findViewById(R.id.add_adverb_bt);
        mAddAdverbBt.setOnClickListener(this);

        mOtherButton = rootView.findViewById(R.id.other_button);
        mOtherButton.setOnClickListener(this);

        mAddOtherBt = rootView.findViewById(R.id.add_other_bt);
        mAddOtherBt.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nounButton:
                startActivity(PartOfSpeech.NOUN, mIsRemembered);
                break;
            case R.id.addNounBt:
                manipulate(PartOfSpeech.NOUN);
                break;
            case R.id.verb_button:
                startActivity(PartOfSpeech.VERB, mIsRemembered);
                break;
            case R.id.add_verb_bt:
                manipulate(PartOfSpeech.VERB);
                break;
            case R.id.adjective_button:
                startActivity(PartOfSpeech.ADJECTIVE, mIsRemembered);
                break;
            case R.id.add_adj_bt:
                manipulate(PartOfSpeech.ADJECTIVE);
                break;
            case R.id.adverb_button:
                startActivity(PartOfSpeech.ADVERB, mIsRemembered);
                break;
            case R.id.add_adverb_bt:
                manipulate(PartOfSpeech.ADVERB);
                break;
            case R.id.other_button:
                startActivity(PartOfSpeech.OTHER, mIsRemembered);
                break;
            case R.id.add_other_bt:
                manipulate(PartOfSpeech.OTHER);
                break;
        }
    }

    private void startActivity(PartOfSpeech partOfSpeech, boolean isRemembered){
        if (WordLab.getWordLab(getActivity()).getWords(partOfSpeech, isRemembered).size() != 0) {
            Intent intent = WordPagerActivity.newInstance(getActivity(), partOfSpeech, isRemembered);
            startActivity(intent);
        } else Toast.makeText(getActivity(), getString(R.string.list_is_empty, PartOfSpeech.format(partOfSpeech)), Toast.LENGTH_SHORT).show();
    }


}
