package com.andrydevelops.langnote;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andrydevelops.langnote.dialogFragments.FunctionSelectorDialogFragment;
import com.example.andry.www.R;

import java.util.ArrayList;
import java.util.List;

public class WordListFragment extends Fragment{

    private static final String TAG = "WordListFragment";
    private static final String ARG_WORD_LIST = "word_list";
    public static final String ARG_WORD = "word";

    private ListView mWordListView;
    private List<Word> mWordList;
    private WordListAdapter mAdapter;

    public static WordListFragment newInstance(ArrayList<Word> words){
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_WORD_LIST, words);
        WordListFragment wlf = new WordListFragment();
        wlf.setArguments(args);
        return wlf;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordList = getArguments().getParcelableArrayList(ARG_WORD_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_list_word, container, false);

        mWordListView = rootView.findViewById(R.id.list_words);
        mWordListView.setDividerHeight(MyPreferences.getDividerHeight(getActivity()));
        mAdapter = new WordListAdapter(getActivity());
        mWordListView.setAdapter(mAdapter);

        mWordListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FunctionSelectorDialogFragment dialogFragment = new FunctionSelectorDialogFragment();
                Bundle args = new Bundle();
                args.putParcelable(ARG_WORD, mWordList.get(position));
                dialogFragment.setArguments(args);
                dialogFragment.show(getFragmentManager(), null);
                return false;
            }
        });
        return rootView;
    }

    class WordListAdapter extends ArrayAdapter<Word>  {


        WordListAdapter(@NonNull Context context) {
            super(context, 0, mWordList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Word word = mWordList.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_item, parent, false);
                ViewGroup.LayoutParams params = convertView.getLayoutParams();
                params.height = MyPreferences.getItemHeight(getActivity());
                convertView.setLayoutParams(params);
            }

            TextView wordItem = convertView.findViewById(R.id.word_item);
            TextView dateItem = convertView.findViewById(R.id.date_item);

            if (word.getNativeWord2() == null)
            wordItem.setText(getString(R.string.word_item,word.getForeignWord(), word.getNativeWord()));
            else wordItem.setText(getString(R.string.word_item_2,word.getForeignWord(), word.getNativeWord(), word.getNativeWord2()));

            dateItem.setText(getString(R.string.date_item, word.getFormattedDate()));
            return convertView;
        }

        @Override
        public int getCount() {
            return mWordList.size();
        }
    }

}
