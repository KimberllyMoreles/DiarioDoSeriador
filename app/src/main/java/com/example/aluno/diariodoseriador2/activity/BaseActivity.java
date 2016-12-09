package com.example.aluno.diariodoseriador2.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aluno on 09/12/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected static String TAG = "bdseries";

    protected void replaceFragment(int container, Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }
}
