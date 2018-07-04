package com.medoske.www.redhealpatient;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.medoske.www.redhealpatient.Adapters.ClinicPagerAdapter;
import com.medoske.www.redhealpatient.Adapters.TabsPagerAdapter;

public class ClinicProfileActivity extends AppCompatActivity{
    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_profile);

        Toolbar appbar = (Toolbar) findViewById(R.id.htab_toolbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("suresh");

        /*//Initializing the tablayout
        tabLayout = (TabLayout)findViewById(R.id.htab_tabs);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("About Clinic"));
        tabLayout.addTab(tabLayout.newTab().setText("About Doctors"));
        //tabLayout.addTab(tabLayout.newTab().setText("Feeds"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //Initializing viewPager
        viewPager = (ViewPager)findViewById(R.id.htab_viewpager);

        //Creating our pager adapter
        ClinicPagerAdapter adapter = new ClinicPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));*/




    }

   /* @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }*/


   /* public void resetActionBar()
    {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
