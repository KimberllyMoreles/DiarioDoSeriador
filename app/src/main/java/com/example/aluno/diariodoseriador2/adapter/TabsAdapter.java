package com.example.aluno.diariodoseriador2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.aluno.diariodoseriador2.R;
import com.example.aluno.diariodoseriador2.fragment.SeriesFragment;

/**
 * Created by aluno on 12/12/16.
 */
public class TabsAdapter extends FragmentPagerAdapter{

    private Context context;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        Fragment f = null;

        switch (position){
            case 0:
                f = new SeriesFragment();
                args.putString("tipo", context.getString(R.string.tabs_abc));
                break;
            case 1:
                f = new SeriesFragment();
                args.putString("tipo", context.getString(R.string.tabs_cw));
                break;
            case 2:
                f = new SeriesFragment();
                args.putString("tipo", context.getString(R.string.tabs_hbo));
                break;
            case 3:
                f = new SeriesFragment();
                args.putString("tipo", context.getString(R.string.tabs_netflix));
                break;
            case 4:
                f = new SeriesFragment();
                args.putString("tipo", context.getString(R.string.tabs_todos));
                break;
        }

        f.setArguments(args);

        return f;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return context.getString(R.string.tabs_abc);
            case 1:
                return context.getString(R.string.tabs_cw);
            case 2:
                return context.getString(R.string.tabs_hbo);
            case 3:
                return context.getString(R.string.tabs_netflix);
            case 4:
                return context.getString(R.string.tabs_todos);
        }

        return null;
    }
}
