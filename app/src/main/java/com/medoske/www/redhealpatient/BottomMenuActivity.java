package com.medoske.www.redhealpatient;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Fragments.HomeFragment;
import com.medoske.www.redhealpatient.Fragments.NotificationFragment;
import com.medoske.www.redhealpatient.Fragments.ProfileFragment;
import com.medoske.www.redhealpatient.Fragments.SearchFragment;
import com.medoske.www.redhealpatient.GPSTracker.GPSTracker;

import java.lang.reflect.Field;


public class BottomMenuActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //changeFragment(new HomeFragment());
                    getSupportActionBar().setTitle("Home");
                    getSupportActionBar().hide();
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager manager1 =getSupportFragmentManager();
                    manager1.beginTransaction().replace(R.id.content_layout,homeFragment,homeFragment.getTag()).commit();
                    return true;
                /*case R.id.navigation_dashboard:
                    *//*changeFragment(new MenuFragment());*//*
                    getSupportActionBar().setTitle("Menu");
                    MenuFragment menuFragment = new MenuFragment();
                    FragmentManager manager2 =getSupportFragmentManager();
                    manager2.beginTransaction().replace(R.id.content_layout,menuFragment,menuFragment.getTag()).commit();
                    return true;*/
                /*case R.id.navigation_search:
                   // changeFragment(new SearchFragment());
                    getSupportActionBar().setTitle("Search");
                    getSupportActionBar().show();
                    SearchFragment searchFragment = new SearchFragment();
                    FragmentManager manager3 =getSupportFragmentManager();
                    manager3.beginTransaction().replace(R.id.content_layout,searchFragment,searchFragment.getTag()).commit();
                    return true;*/

                case R.id.navigation_profile:
                   // changeFragment(new ProfileFragment());
                    getSupportActionBar().setTitle("Profile");
                    getSupportActionBar().show();
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentManager manager5 =getSupportFragmentManager();
                    manager5.beginTransaction().replace(R.id.content_layout,profileFragment,profileFragment.getTag()).commit();
                    return true;

                case R.id.navigation_notifications:
                    // changeFragment(new NotificationFragment());
                    getSupportActionBar().setTitle("Notifications");
                    getSupportActionBar().show();
                    NotificationFragment notificationFragment = new NotificationFragment();
                    FragmentManager manager4 =getSupportFragmentManager();
                    manager4.beginTransaction().replace(R.id.content_layout,notificationFragment,notificationFragment.getTag()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_menu);
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("Home");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);

       // changeFragment(new HomeFragment());

        // default fragment after launch
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager1 =getSupportFragmentManager();
        manager1.beginTransaction().replace(R.id.content_layout,homeFragment,homeFragment.getTag()).commit();





    }

    static class BottomNavigationViewHelper {

        static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }
        }
    }

}
