package com.shybal.test.newsapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * abstract base class for the application activities
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void setUpBindings();
}
