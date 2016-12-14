package com.example.aluno.diariodoseriador2.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by aluno on 09/12/16.
 */
public class BaseFragment extends Fragment{
    protected static String TAG = "bdseries";

    protected void replaceFragment(int container, Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }
}
