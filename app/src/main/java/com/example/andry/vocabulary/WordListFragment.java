package com.example.andry.vocabulary;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andry.vocabulary.dialogFragments.FunctionSelectorDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class WordListFragment extends Fragment{

    private static final String TAG = "WordListFragment";
    private static final String ARG_WORD_LIST = "word_list";
    private static final String ARG_IS_REMEMBERED = "is_remembered";
    private static final String ARG_POS = "pos";
    private static final String ARG_LAST_INDEX = "last_index";
    private static final String ARG_HEIGHT_PX = "height_px";
    private static final String ARG_DIVIDER_HEIGHT = "divider_Height";
    private static final int REQUEST_DELETE_WORD = 1;
    public static final String ARG_WORD = "word";

    private ListView mWordListView;
    private List<Word> mDBList;
    private List<Word> mWordList;
    private WordListAdapter mAdapter;
    private int mLastIndex;
    private PartOfSpeech mPartOfSpeech;
    private boolean mIsRemembered;

    private Callbacks mCallbacks;
    private Context mContext;
    private static int num;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
        mContext = context;
     //   Log.i(TAG, "attach" + ++num);
    }


    public static WordListFragment newInstance(ArrayList<Word> words){
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_WORD_LIST, words);
       // args.putString(ARG_POS, pos.toString());
       // args.putBoolean(ARG_IS_REMEMBERED, remembered);
       // args.putInt(ARG_LAST_INDEX, lastIndex);
      //  args.putInt(ARG_HEIGHT_PX, heightPx);
      //  args.putInt(ARG_DIVIDER_HEIGHT, dividerHeight);
        WordListFragment wlf = new WordListFragment();
        wlf.setArguments(args);
        return wlf;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        Log.i(TAG, "create" + num);
        setHasOptionsMenu(true);
        mPartOfSpeech = PartOfSpeech.valueOf(getArguments().getString(ARG_POS));
        mIsRemembered = getArguments().getBoolean(ARG_IS_REMEMBERED);
        mLastIndex = getArguments().getInt(ARG_LAST_INDEX);
        mDBList = WordLab.getWordLab(getActivity()).getWords(mPartOfSpeech, mIsRemembered);
        mWordList = getWordList();
        mDBList = getArguments().getParcelableArrayList(ARG_WORD_LIST);*/
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
                //Toast.makeText(getActivity(), "OnItemLongClickListener: pos - " + position, Toast.LENGTH_SHORT).show();
                FunctionSelectorDialogFragment dialogFragment = new FunctionSelectorDialogFragment();
                Bundle args = new Bundle();
                args.putParcelable(ARG_WORD, mWordList.get(position)); //remake word list
                dialogFragment.setArguments(args);
                dialogFragment.show(getFragmentManager(), null);
                return false;
            }
        });
        return rootView;
    }

 /*   private MenuItem mCheckItem;
    private MenuItem mMoveItem;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(TAG, "menu fragment");
        mCheckItem = menu.findItem(R.id.pager_action_check);
        mMoveItem = menu.findItem(R.id.pager_action_move);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pager_action_check:
                mCheckItem.setVisible(false);
                Toast.makeText(getActivity(), "Does not work yet", Toast.LENGTH_SHORT).show();
                mWordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mWordListView.setItemChecked(position, true);
                        Log.i(TAG, "clicked: " + position);
                    }
                });
                mMoveItem.setVisible(true);
                return true;
            case R.id.pager_action_move:
                Toast.makeText(getActivity(), "Does not work yet", Toast.LENGTH_SHORT).show();
                mWordListView.setOnItemClickListener(null);
                mMoveItem.setVisible(false);
                mCheckItem.setVisible(true);
                return true;
            default:
                return false;
        }
    }*/

/*
    public void updateUI(){
        mDBList = WordLab.getWordLab(getActivity()).getWords(mPartOfSpeech, mIsRemembered);
        mWordList = getWordList();
        Log.i(TAG, "listSize0: " + ++num + mWordList.size());
     //   mAdapter.setWords(getWordList());
        mAdapter.notifyDataSetChanged();
    }

    private void setAdapter(){
        mAdapter = new WordListAdapter(getActivity());
        mWordListView.setAdapter(mAdapter);
    }
*/

/*    private ArrayList<Word> getWordList() {
        ArrayList<Word> mLocalWords = new ArrayList<>();
        Log.i(TAG, "id: " + mLastIndex);
        int wordsOnPage = MyPreferences.getItemsCount(mContext);
        int range = (wordsOnPage + mLastIndex) < mDBList.size() ? (wordsOnPage + mLastIndex) : mDBList.size();
        Log.i(TAG, "id: " + mLastIndex
                + "rang: " + range
                + " listSize: " + mDBList.size());
        for (int i = mLastIndex; i < range; i++) {
            mLocalWords.add(mDBList.get(i));
        }
        return mLocalWords;
    }*/


    public class WordListAdapter extends ArrayAdapter<Word>  {

        //private List<Word> mWords;

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
            wordItem.setText(getString(R.string.word_item, word.getNativeWord(), word.getForeignWord()));
            dateItem.setText(getString(R.string.date_item, word.getFormattedDate()));
            return convertView;
        }

        @Override
        public int getCount() {
            return mWordList.size();
        }
    }

}
