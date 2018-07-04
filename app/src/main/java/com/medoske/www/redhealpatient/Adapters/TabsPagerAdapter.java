package com.medoske.www.redhealpatient.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.medoske.www.redhealpatient.Fragments.AboutClinicFragment;
import com.medoske.www.redhealpatient.Fragments.AboutMeFragment;
import com.medoske.www.redhealpatient.Fragments.FeedsFragment;

/**
 * Created by suresh on 11-11-2016.
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public TabsPagerAdapter(FragmentManager fm, int tabCount) {
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
                AboutClinicFragment tab1 = new AboutClinicFragment();
                return tab1;
            case 1:

                AboutMeFragment tab2 = new AboutMeFragment();
                return tab2;

            case 2:
                FeedsFragment tab3 = new FeedsFragment();
                return tab3;
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