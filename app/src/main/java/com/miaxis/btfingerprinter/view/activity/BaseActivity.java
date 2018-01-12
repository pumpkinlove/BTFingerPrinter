package com.miaxis.btfingerprinter.view.activity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by xu.nan on 2017/8/16.
 */

public abstract class BaseActivity extends Activity {

    protected abstract void initData();
    protected abstract void initView();

    protected void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
