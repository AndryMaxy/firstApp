package com.example.andry.vocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class WordPagerActivity extends AppCompatActivity implements Callbacks, OnUpdateListener{

    private static final String TAG = "WordPagerActivity";
    private static final String EXTRA_WORD_LIST = "com.example.andry.vocabulary.word_pager_activity.word_list";
    private static final String EXTRA_PART_OF_SPEECH = "com.example.andry.vocabulary.word_pager_activity.part_of_speech";
    private static final String EXTRA_IS_REMEMBERED = "com.example.andry.vocabulary.word_pager_activity.is_remembered";
    private ViewPager mViewPager;
    private ConstraintLayout mConstraintLayout;
    private ArrayList<Word> mWordList;
    private MyFragmentAdapter mAdapter;
    private PartOfSpeech mPartOfSpeech;
    private Toolbar mToolbar;
    private boolean mIsRemembered;


    public static Intent newInstance(Context context, PartOfSpeech partOfSpeech, boolean isRemembered) {
        Intent intent = new Intent(context, WordPagerActivity.class);
        //intent.putParcelableArrayListExtra(EXTRA_WORD_LIST, words);
        intent.putExtra(EXTRA_PART_OF_SPEECH, partOfSpeech.toString());
        intent.putExtra(EXTRA_IS_REMEMBERED, isRemembered);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_pager);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mConstraintLayout = findViewById(R.id.fragment_container);

        mToolbar = findViewById(R.id.pager_toolbar);
        setSupportActionBar(mToolbar);

        mViewPager = findViewById(R.id.list_view_pager);

        mPartOfSpeech =  PartOfSpeech.valueOf((String)getIntent().getSerializableExtra(EXTRA_PART_OF_SPEECH));
        getSupportActionBar().setTitle(getResources().getString(R.string.pager_toolbar_title, mPartOfSpeech));
        mIsRemembered = (boolean) getIntent().getSerializableExtra(EXTRA_IS_REMEMBERED);
        //mWordList = getIntent().getParcelableArrayListExtra(EXTRA_WORD_LIST);
        mWordList = WordLab.getWordLab(getApplicationContext()).getWords(mPartOfSpeech, mIsRemembered);

        if (MyPreferences.getItemsCount(this) == 0) {
            mConstraintLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mConstraintLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int layoutHeight = mConstraintLayout.getHeight();
                    int toolbarHeight = mToolbar.getHeight();
                    int availableHeight = layoutHeight - toolbarHeight;
                    new MyAsyncTask().execute(availableHeight);
                }
            });
        } else {
            setAdapter(mWordList);
        }
    }

 /*   @Override
    public void updateUI() {
        for (int i = 0; i < mAdapter.getPagesCount(); i++) {
            WordListFragment fragment = (WordListFragment) mAdapter.getItem(i);
            fragment.updateUI();
        }
        mWordList = WordLab.getWordLab(this).getWords(mPartOfSpeech, mIsRemembered);
        if (mAdapter.removeLastPage()) {
            mAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(mAdapter.getPagesCount() - 1);
        }
    }*/

    @Override
    public void updateUI() {
        int curItem = mViewPager.getCurrentItem();
        mWordList = WordLab.getWordLab(this).getWords(mPartOfSpeech, mIsRemembered);
        setAdapter(mWordList);
        int setItem = curItem < mAdapter.getCount() - 1 ? curItem : mAdapter.getCount() - 1;
        for (int i = 0; i < setItem; i++) {
            mAdapter.getItem(i);
        }
        mViewPager.setCurrentItem(setItem);
    }

    private void setAdapter(ArrayList<Word> wordList){
        mAdapter = new MyFragmentAdapter(getSupportFragmentManager(), wordList);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void callSearch(ArrayList<Word> words) {
 /*       for (int i = 0; i < mAdapter.getPagesCount(); i++) {
            Log.i(TAG, "fragment0: " + mAdapter.getItem(i).getId());
        }
        for (Fragment f : getSupportFragmentManager().getFragments()) {
           // getSupportFragmentManager().beginTransaction().remove(f).commit();
            Log.i(TAG, "fragment: " + f.getId());
        }
        SearchListFragment fragment = (SearchListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list_word);
        if (fragment == null) {
            fragment = SearchListFragment.newInstance(words);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_word, fragment).commit();
            mViewPager.setAdapter(new SearchViewPagerAdapter(getSupportFragmentManager(), fragment));
        } else {
            fragment.updateUI(words);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pager, menu);

     //   Log.i(TAG, "menu activity");
        MenuItem searchItem = menu.findItem(R.id.pager_action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Word> list = new ArrayList<>();
                for (Word word : mWordList) {
                    if (word.getNativeWord().contains(s) || word.getForeignWord().contains(s)) {
                        list.add(word);
                    }
                }
                setAdapter(list);
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setAdapter(mWordList);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           case R.id.pager_action_search:
                return true;
            case android.R.id.home :
                onBackPressed();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer[]> {
        private ProgressBar mProgressBar;

        @Override
        protected void onPreExecute() {
            mProgressBar = findViewById(R.id.progress_indicator);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer[] doInBackground(Integer... integers) {
            float layoutHeight = integers[0] + 0.0f;
            Log.i(TAG, "layoutHeight: " + layoutHeight);
            float maxDividerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
            Log.i(TAG, "maxDividerHeight: " + maxDividerHeight);

            float dividerHeight = 1f;
            float curItemHeight = 106;
            int itemsCount = 2;

            Integer[] results = new Integer[] {5, 150, 5};

            while (curItemHeight > 105) {
                while (curItemHeight > 105 && dividerHeight <= maxDividerHeight) {
                    curItemHeight = (layoutHeight - (itemsCount - 1) * dividerHeight) / itemsCount;
                    if (curItemHeight % 1 == 0 && curItemHeight < 160) {
                        results = new Integer[] {itemsCount,(int) curItemHeight, (int) dividerHeight};
                    }
                    dividerHeight++;
                }
                dividerHeight = 1;
                itemsCount++;
            }

            Log.i(TAG, "bestItemsCount: " + results[0]);
            Log.i(TAG, "bestItemHeight " + results[1]);
            Log.i(TAG, "bestDividerHeight: " + results[2]);
            return results;
        }

        @Override
        protected void onPostExecute(Integer[] params) {
            mProgressBar.setVisibility(View.GONE);
            mProgressBar = null;
            MyPreferences.setItemsCount(getApplicationContext(), params[0]);
            MyPreferences.setItemHeight(getApplicationContext(), params[1]);
            MyPreferences.setDividerHeight(getApplicationContext(), params[2]);
            mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), mWordList));
        }
    }

    private class MyFragmentAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Word> mLocalWordList;
        private List<WordListFragment> mWordListFragment;
        private int mWordsOnPage;
        private int mLastIndex;
        private int mCountItems;

        MyFragmentAdapter(FragmentManager fm, ArrayList<Word> words) {
            super(fm);
            mWordsOnPage = MyPreferences.getItemsCount(WordPagerActivity.this);
            mWordListFragment = new ArrayList<>();
            mCountItems = -1;
            mLocalWordList = words;
            Log.i(TAG, "mCountItems1 = " + mCountItems);
        }

        @Override
        public Fragment getItem(int itemPosition) {
            if (mCountItems < itemPosition) {
                WordListFragment fragment = WordListFragment.newInstance(getWordList());
                mWordListFragment.add(fragment);
                //mLastIndex += mWordsOnPage;
                Log.i(TAG, "mCountItems2 = " + mCountItems);
                mCountItems++;
            }
            return mWordListFragment.get(itemPosition);
        }

        @Override
        public int getCount() {
            return (int) Math.ceil((double) mLocalWordList.size() / mWordsOnPage);
        }

        private int getPagesCount(){
            return mWordListFragment.size();
        }

        private ArrayList<Word> getWordList() {
            ArrayList<Word> mLocalWords = new ArrayList<>();
            int range = (mWordsOnPage + mLastIndex) < mLocalWordList.size() ? (mWordsOnPage + mLastIndex) : mLocalWordList.size();
/*            Log.i(TAG, "id: " + mLastIndex
                    + "rang: " + range
                    + " listSize: " + mLocalWordList.size());*/
            for (int i = mLastIndex; i < range; i++) {
                mLocalWords.add(mLocalWordList.get(i));
            }
            mLastIndex += mWordsOnPage;
            return mLocalWords;
        }

/*

        @Override
        public int getItemPosition(Object object) {
            int index = mWordListFragment.indexOf(object);
            if (index == -1)
                return POSITION_NONE;
            else
                return index;
        }

        private boolean removeLastPage() {
            Log.i(TAG, "remove");
            if (((mLocalWordList.size() * 1.0 / mWordsOnPage)) <= (getPagesCount() - 1)) {
                Log.i(TAG, "size" + mLocalWordList.size());
                int lastFragmentPosition = mWordListFragment.size() - 1;
                mAdapter.destroyItem(mViewPager, lastFragmentPosition, mWordListFragment.get(lastFragmentPosition));
                mWordListFragment.remove(lastFragmentPosition);
                mCountItems -= 1;
                return true;
            }
            return false;
        }
*/

        /*   @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.pager_toolbar_title, mPartOfSpeech.toString()) + " p" + position;
        }*/

    }
}
