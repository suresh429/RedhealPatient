package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.DoctorsListAdapter;
import com.medoske.www.redhealpatient.Adapters.MyDoctorsListAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.DoctorsListItem;
import com.medoske.www.redhealpatient.Items.InstantItem;
import com.medoske.www.redhealpatient.Items.MyDoctorsItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyDoctorsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ListView listView;
    MyDoctorsListAdapter doctorsListAdapter;
    ArrayList<MyDoctorsItem> doctorsListItemArrayList = new ArrayList<MyDoctorsItem>();
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctors);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Doctors");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        listView=(ListView)findViewById(R.id.myDoctorsList);

        URL = Apis.MY_FAV_DOCTORS_LIST_URL+redHealId;




        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        new DoctorDataList().execute();
                                    }
                                }
        );

        new DoctorDataList().execute();

    }

    @Override
    public void onRefresh() {
        new DoctorDataList().execute();
    }

    public class DoctorDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(MyDoctorsActivity.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Set the dialog title to 'Loading...'
            // progressDialog.setTitle("Chargement...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Fetching ...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(true);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            progressDialog.show();

            // showing refresh animation before making http call
            swipeRefreshLayout.setRefreshing(true);

        }

        @Override
        protected JSONObject doInBackground(String... params) {

            // appending offset to url
            String url = URL +"/"+offSet;

            //Getting product details from making HTTP Request
            JSONObject json = jsonParser.makeHttpRequest(url, "GET", param);

            Log.e("DOCTORS_LIST_URL", URL);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

            progressDialog.dismiss();

            // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);

            Log.e("data", String.valueOf(json));

            String data = "";
            try {

                //Get the instance of JSONArray that contains JSONObjects
               JSONArray jsonArray = json.optJSONArray("myfavdoctor_list");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                    String fullName = jsonObject.optString("fullName").toString();
                    String doctorImage = jsonObject.optString("doctorImage").toString();
                    String experience = jsonObject.optString("experience").toString();
                    String specialization = jsonObject.optString("specialization").toString();
                    String description = jsonObject.optString("description").toString();
                    String verfication_status = jsonObject.optString("verfication_status").toString();
                    String like_status = jsonObject.optString("like_status").toString();

                    doctorsListItemArrayList.add(new MyDoctorsItem(doctor_redhealId,fullName,doctorImage,experience,specialization,description,verfication_status,like_status));

                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}

            if(doctorsListItemArrayList.size()>0)
            {
                doctorsListAdapter = new MyDoctorsListAdapter(MyDoctorsActivity.this, R.layout.doctors_fav_list_item, doctorsListItemArrayList);
                listView.setItemsCanFocus(false);
                listView.setAdapter(doctorsListAdapter);
            }
            else
            {
                Toast.makeText(MyDoctorsActivity.this, "no data found", Toast.LENGTH_SHORT).show();
            }

        }
    }

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
