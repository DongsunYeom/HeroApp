package com.brilliancemobility.heroes;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.brilliancemobility.heroes.net.TestEnum;

/**
 * Created by dongsun on 13/11/16.
 */

public class MainActivity extends FragmentActivity implements OnClickListener, OnItemClickListener, OnEditorActionListener {
    private EditText searchField;
    private InputMethodManager in;
    private View mSearchButton;
    private HeroesFragment mHeroesFragment;
    private TestEnum.SortOrder selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);

        mHeroesFragment = new HeroesFragment();

        searchField = (EditText) findViewById(R.id.searchTermField);
        searchField.setOnEditorActionListener(this);

        mSearchButton = this.findViewById(R.id.searchbutton);
        mSearchButton.setOnClickListener(this);

        this.getActionBar().setHomeButtonEnabled(true);

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.END;
        getActionBar().setDisplayShowCustomEnabled(true);

        in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        selectedItem = TestEnum.SortOrder.values()[0];

        replaceFragment(R.id.fl_fragment, mHeroesFragment, "mHeroesFragment");
    }

    protected void replaceFragment(int containerViewId, Fragment fragment, String string) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, string);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.searchbutton) {
            search();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> a, View view, int position, long id) {
        search();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.relevance:
                selectedItem = TestEnum.SortOrder.values()[0];
                return true;
            case R.id.date_posted:
                selectedItem = TestEnum.SortOrder.values()[1];
                return true;
            case R.id.date_taken:
                selectedItem = TestEnum.SortOrder.values()[2];
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void search() {
        in.hideSoftInputFromWindow(searchField.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        String term = searchField.getText().toString();
        if (!TextUtils.isEmpty(term)) {
            mHeroesFragment.reset(selectedItem.toString(), term);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            search();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
    }

}
