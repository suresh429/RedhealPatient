package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.DoctorsListAdapter;
import com.medoske.www.redhealpatient.Adapters.InstantListAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.DoctorsListItem;
import com.medoske.www.redhealpatient.Items.InstantItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorsListActivity extends AppCompatActivity {
    ListView listView;
    DoctorsListAdapter doctorsListAdapter;
    ArrayList<DoctorsListItem> doctorsListItemArrayList = new ArrayList<DoctorsListItem>();

    InstantListAdapter instantListAdapter ;
    ArrayList<InstantItem> instantItemArrayList =new ArrayList<InstantItem>();

    JSONArray jsonArray;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();

    String placeName,latitude,longitude;
    private ProgressDialog progressDialog;

    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        String data = getIntent().getExtras().getString("specilization","defaultKey");
        String specializationId = getIntent().getExtras().getString("specializationId","1");
        latitude = getIntent().getExtras().getString("latitude","1");
        longitude = getIntent().getExtras().getString("longitude","1");
        placeName = getIntent().getExtras().getString("placeName","change Location");
        getSupportActionBar().setTitle(data);

        URL =Apis.DOCTORS_LIST_URL+specializationId+"/"+latitude+"/"+longitude;
        Log.e("urllist",""+URL);

        listView=(ListView)findViewById(R.id.list_doctors);
        final FloatingActionButton button =(FloatingActionButton) findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Normal Appointment", "Instant Appointment"};
                final boolean[] states = {false, false, false};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(DoctorsListActivity.this);
                builder.setTitle("Select Appointment Type?");
                builder.setMultiChoiceItems(items, states, new DialogInterface.OnMultiChoiceClickListener(){
                    public void onClick(DialogInterface dialogInterface, int item, boolean state) {
                    }
                });
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SparseBooleanArray CheCked = ((android.support.v7.app.AlertDialog) dialog).getListView().getCheckedItemPositions();
                        if (CheCked.get(0))
                        {
                            doctorsListAdapter=new DoctorsListAdapter(DoctorsListActivity.this,R.layout.doctor_item_list,doctorsListItemArrayList);
                            listView.setAdapter(doctorsListAdapter);
                            doctorsListItemArrayList.clear();
                            doctorsListAdapter.notifyDataSetChanged();
                            new DoctorDataList().execute();
                        }
                         if (CheCked.get(1))
                        {
                            instantListAdapter= new InstantListAdapter(DoctorsListActivity.this, R.layout.instant_item_list, instantItemArrayList);
                            listView.setItemsCanFocus(false);
                            listView.setAdapter(instantListAdapter);
                            instantItemArrayList.clear();
                            instantListAdapter.notifyDataSetChanged();
                            prepareListData1();
                        }

                         if (CheCked.get(0) && CheCked.get(1)){

                            instantListAdapter= new InstantListAdapter(DoctorsListActivity.this, R.layout.instant_item_list, instantItemArrayList);
                            listView.setItemsCanFocus(false);
                            listView.setAdapter(instantListAdapter);
                            instantItemArrayList.clear();
                            instantListAdapter.notifyDataSetChanged();
                            prepareListData1();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.create().show();


            }
        });


        new DoctorDataList().execute();
    }


    public class DoctorDataList extends AsyncTask<String, String, JSONObject> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(DoctorsListActivity.this);
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

        }

        @Override
        protected JSONObject doInBackground(String... params) {

            //Getting product details from making HTTP Request
            JSONObject json = jsonParser.makeHttpRequest(URL, "GET", param);

            Log.e("DOCTORS_LIST_URL", URL);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

            progressDialog.dismiss();

            Log.e("data", String.valueOf(json));

            String data = "";
            try {

                //Get the instance of JSONArray that contains JSONObjects
                jsonArray = json.optJSONArray("specialization_doctorslist");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String specialization = jsonObject.optString("specialization").toString();
                    String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                    String doctorName = jsonObject.optString("doctorName").toString();
                    String verfication_status = jsonObject.optString("verfication_status").toString();
                    String like_status = jsonObject.optString("like_status").toString();
                    String doctorImage = jsonObject.optString("doctorImage").toString();
                    String clinicName = jsonObject.optString("clinicName").toString();
                    String areaName = jsonObject.optString("areaName").toString();
                    String areaId = jsonObject.optString("areaId").toString();
                    String consultationFee = jsonObject.optString("consultationFee").toString();
                    String experience = jsonObject.optString("experience").toString();
                    String clinic_redhealId = jsonObject.optString("clinic_redhealId").toString();
                    String distance = jsonObject.optString("distance").toString();
                    String status = jsonObject.optString("status").toString();
                    String premiumConsultationFee = jsonObject.optString("premiumConsultationFee").toString();
                    String description = jsonObject.optString("description").toString();
                    doctorsListItemArrayList.add(new DoctorsListItem(specialization,doctor_redhealId,doctorName,verfication_status,like_status,doctorImage,clinicName,areaName,areaId,consultationFee,experience,clinic_redhealId,distance,status,premiumConsultationFee,description,latitude,longitude));

                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}

            if(doctorsListItemArrayList.size()>0)
            {
                doctorsListAdapter = new DoctorsListAdapter(DoctorsListActivity.this, R.layout.doctor_item_list, doctorsListItemArrayList);
                listView.setItemsCanFocus(false);
                listView.setAdapter(doctorsListAdapter);
            }
            else
            {
                Toast.makeText(DoctorsListActivity.this, "no data found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    private void prepareListData1() {

        InstantItem instantItem = new InstantItem("Dr.Rama krishna","general Physician","Availability","Vizag","10 Years","200Rs",R.drawable.ic_action_placeholder,"400Rs");
        instantItemArrayList.add(instantItem);

        instantItem = new InstantItem("Dr.Avinash","general Physician","Availability","Vizag","10 Years","200Rs",R.drawable.ic_action_placeholder,"400Rs");
        instantItemArrayList.add(instantItem);


        instantListAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.location);
        searchItem.setTitle(placeName);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.location:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
