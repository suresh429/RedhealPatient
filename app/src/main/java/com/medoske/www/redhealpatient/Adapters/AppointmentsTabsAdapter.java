package com.medoske.www.redhealpatient.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.medoske.www.redhealpatient.Fragments.PastFragment;
import com.medoske.www.redhealpatient.Fragments.CurrentFragment;

/**
 * Created by USER on 5.7.17.
 */

public class AppointmentsTabsAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public AppointmentsTabsAdapter(FragmentManager fm, int tabCount) {
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
                CurrentFragment tab1 = new CurrentFragment();
                return tab1;
            case 1:

                PastFragment tab2 = new PastFragment();
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
