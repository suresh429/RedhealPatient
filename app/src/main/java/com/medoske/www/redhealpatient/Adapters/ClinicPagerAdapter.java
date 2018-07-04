package com.medoske.www.redhealpatient.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.medoske.www.redhealpatient.Fragments.CurrentFragment;
import com.medoske.www.redhealpatient.Fragments.PastFragment;

/**
 * Created by USER on 1.7.17.
 */

public class ClinicPagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public ClinicPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
               PastFragment tab1 = new PastFragment();
                return tab1;
            case 1:

                CurrentFragment tab2 = new CurrentFragment();
                return tab2;


            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}