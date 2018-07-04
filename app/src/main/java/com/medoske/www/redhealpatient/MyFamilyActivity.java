package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.MyFamilyAdapter;
import com.medoske.www.redhealpatient.Adapters.SelectClinicAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.MyFamilyItem;
import com.medoske.www.redhealpatient.Items.SelectClinicItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyFamilyActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ArrayList<MyFamilyItem> myFamilyItems = new ArrayList<MyFamilyItem>();
    MyFamilyAdapter myFamilyAdapter;

    ListView listView;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_family);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Family");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp =getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        URL = Apis.MY_FAMILY_LIST_URL+redHealId;

        listView =(ListView)findViewById(R.id.familyList);

       // myFamilyAdapter.notifyDataSetChanged();

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

                                        new MyFamilyData().execute();
                                    }
                                }
        );

        new MyFamilyData().execute();

    }

    @Override
    public void onRefresh() {
        new MyFamilyData().execute();
    }

    public class MyFamilyData extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(MyFamilyActivity.this);
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

            Log.e("FAMILY_LIST_URL", url);

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

                myFamilyItems.clear();

                //Get the instance of JSONArray that contains JSONObjects
               JSONArray jsonArray = json.optJSONArray("family_list");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String relation_redhealId = jsonObject.optString("redhealId").toString();
                    String email = jsonObject.optString("email").toString();
                    String fullName = jsonObject.optString("fullName").toString();
                    String relation = jsonObject.optString("relation").toString();
                    String gender = jsonObject.optString("gender").toString();
                    String bloodGroup = jsonObject.optString("bloodGroup").toString();
                    String mobileNumber = jsonObject.optString("mobileNumber").toString();
                    String age = jsonObject.optString("age").toString();
                    String imagePath = jsonObject.optString("imagePath").toString();

                    myFamilyItems.add(new MyFamilyItem(relation_redhealId,email,fullName,relation,gender,bloodGroup,mobileNumber,age,imagePath));

                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}

            if(myFamilyItems.size()>0)
            {
                myFamilyAdapter = new MyFamilyAdapter(MyFamilyActivity.this, R.layout.myfamily_item, myFamilyItems);
                listView.setItemsCanFocus(false);
                listView.setAdapter(myFamilyAdapter);


            }
            else
            {
                Toast.makeText(MyFamilyActivity.this, "no data found", Toast.LENGTH_SHORT).show();
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_family, menu);
        MenuItem searchItem = menu.findItem(R.id.addfamily);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent =new Intent(new Intent(MyFamilyActivity.this,AddFamilyActivity1.class));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return false;
            }
        });
        return true;

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
