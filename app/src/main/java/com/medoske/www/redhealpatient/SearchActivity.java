package com.medoske.www.redhealpatient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.medoske.www.redhealpatient.Adapters.ClinicSearchAdapter;
import com.medoske.www.redhealpatient.Adapters.ClinicSearchRecyclerAdapter;
import com.medoske.www.redhealpatient.Adapters.DefaultSpcilitiesRecyclerAdapter;
import com.medoske.www.redhealpatient.Adapters.DoctorsSearchAdapter;
import com.medoske.www.redhealpatient.Adapters.DoctorsSearchRecyclerAdapter;
import com.medoske.www.redhealpatient.Adapters.SearchSpecilizationAdapter;
import com.medoske.www.redhealpatient.Adapters.SearchSpecilizationRecyclerAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Fragments.SearchFragment;
import com.medoske.www.redhealpatient.Items.ClinicSearchItem;
import com.medoske.www.redhealpatient.Items.DoctorsSearchItem;
import com.medoske.www.redhealpatient.Items.SpecailizationSearchItem;
import com.medoske.www.redhealpatient.Items.SpecilizationItem;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerViewDefault,recyclerViewDoctors,recyclerViewClinics,recyclerViewSpecilities;

    ArrayList<SpecilizationItem> specilizationItems = new ArrayList<SpecilizationItem>();
    DefaultSpcilitiesRecyclerAdapter adapter;

    ArrayList<DoctorsSearchItem> doctorsearchItem = new ArrayList<DoctorsSearchItem>();
    DoctorsSearchRecyclerAdapter doctorSearchAdapter;

    ArrayList<ClinicSearchItem> clinicSearchItem = new ArrayList<ClinicSearchItem>();
    ClinicSearchRecyclerAdapter clinicSearchAdapter;

    ArrayList<SpecailizationSearchItem> spelizationSearchItem = new ArrayList<SpecailizationSearchItem>();
    SearchSpecilizationRecyclerAdapter searchSpecilizationAdapter;

    String URL_CLINIC, URL_DOCTOR, URL_SPECIALIZATION;

    private static String TAG = SearchActivity.class.getSimpleName();
    // Progress dialog
    private ProgressDialog pDialog;

    LinearLayout searchSpeclitiesLayout, searchDoctorsLayout, searchClinicsLayout;

    String presentLatitude,presentLongitude,presentPlaceName;
    String Latitude,Longitude;

    MenuItem searchItem;
    SearchView searchView;
    Double lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Search");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);
        presentLatitude = sp.getString("presentLatitude", "1");
        presentLongitude = sp.getString("presentLongitude", "1");
        presentPlaceName = sp.getString("presentLocality", "Select Location");


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        searchView = (SearchView)findViewById(R.id.search_bar);
        loadSearchView();

        searchSpeclitiesLayout = (LinearLayout) findViewById(R.id.speclizationLayout);
        searchDoctorsLayout = (LinearLayout) findViewById(R.id.doctorsLayout);
        searchClinicsLayout = (LinearLayout) findViewById(R.id.clinicsLayout);


        // default specialities list
        recyclerViewDefault = (RecyclerView)findViewById(R.id.DefaultspeclizationList);
        recyclerViewDefault.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager1 = new LinearLayoutManager(SearchActivity.this);
        MyLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDefault.setLayoutManager(MyLayoutManager1);
        recyclerViewDefault.setNestedScrollingEnabled(false);

        // doctors list
        recyclerViewDoctors = (RecyclerView)findViewById(R.id.listDoctors);
        recyclerViewDoctors.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager2 = new LinearLayoutManager(SearchActivity.this);
        MyLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDoctors.setLayoutManager(MyLayoutManager2);
        recyclerViewDoctors.setNestedScrollingEnabled(false);

        // doctors list
        recyclerViewClinics = (RecyclerView)findViewById(R.id.listClinics);
        recyclerViewClinics.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager3 = new LinearLayoutManager(SearchActivity.this);
        MyLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewClinics.setLayoutManager(MyLayoutManager3);
        recyclerViewClinics.setNestedScrollingEnabled(false);

        // doctors list
        recyclerViewSpecilities = (RecyclerView)findViewById(R.id.listSpecilities);
        recyclerViewSpecilities.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager4 = new LinearLayoutManager(SearchActivity.this);
        MyLayoutManager4.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSpecilities.setLayoutManager(MyLayoutManager4);
        recyclerViewSpecilities.setNestedScrollingEnabled(false);

        getDefalutSpeclitiesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);//Menu Resource, Menu
        searchItem = menu.findItem(R.id.location);
        searchItem.setTitle(presentPlaceName);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                try {
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(SearchActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
                return false;
            }
        });
        return true;

    }

    // A place has been received; use requestCode to track the request.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(SearchActivity.this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());

                // places latitude
                LatLng qLoc = place.getLatLng();
                Double lat =qLoc.latitude;

                Latitude = String.valueOf(lat);
                Log.e("lat", "Place: " + Latitude);

                // places longitude
                Double lon =qLoc.longitude;
                Longitude = String.valueOf(lon);
                Log.e("long", "Place: " + Longitude);

                presentLatitude = Latitude;
                presentLongitude = Longitude;
                presentPlaceName = String.valueOf(place.getName());

                Log.e("presentPlaceName", "Place: " + presentPlaceName);

                searchItem.setTitle(presentPlaceName);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(SearchActivity.this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    // load searchView based on url
    private void loadSearchView() {
        // Do all of your work here

        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getContext(),"toasttwo",Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {

                Log.e("latitude123", "Place: " + searchQuery);
                URL_DOCTOR = Apis.DOCTORS_SEARCH_URL + searchQuery +"/"+ presentLatitude+"/"+presentLongitude;
                URL_CLINIC = Apis.CLINIC_SEARCH_URL + searchQuery + "/"+ presentLatitude+"/"+presentLongitude;
                URL_SPECIALIZATION = Apis.SPECLIZATION_SEARCH_URL + searchQuery;

                Log.e("searchclinic", "" + URL_CLINIC);
                Log.e("searchdoctor", "" + URL_DOCTOR);
                Log.e("searchspeclization", "" + URL_SPECIALIZATION);

               SearchClinicsList();
               SearchDoctorsList();
               SearchSpecalitiesList();

                recyclerViewDefault.setVisibility(View.GONE);

                if (searchQuery.equals("")) {

                    recyclerViewDefault.setVisibility(View.VISIBLE);

                }

                adapter.notifyDataSetChanged();



                return true;
            }
        });

    }


    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void getDefalutSpeclitiesList() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Apis.SPECLIZATION_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    specilizationItems.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("specialization");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                        String id = jsonObject1.optString("id").toString();
                        String specialization = jsonObject1.optString("specialization").toString();
                        String image = jsonObject1.optString("image").toString();
                        Log.e("imageviewpath", "" + image);

                        specilizationItems.add(new SpecilizationItem(id, specialization, image,presentLatitude,presentLongitude,presentPlaceName));

                    }

                    //Finally initializing our adapter
                    adapter = new DefaultSpcilitiesRecyclerAdapter(SearchActivity.this,specilizationItems);

                    //Adding adapter to recyclerview
                    recyclerViewDefault.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }


    private void SearchDoctorsList(){

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL_DOCTOR, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    doctorsearchItem.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("doctors_list");

                    String Message =response.getString("message");
                    if (Message.equals("No doctors available")){
                        searchDoctorsLayout.setVisibility(View.GONE);

                    }else if (Message.equals("Result Found")){
                        searchDoctorsLayout.setVisibility(View.VISIBLE);
                    }


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                        String doctor_redhealId = jsonObject1.optString("doctor_redhealId").toString();
                        String fullName = jsonObject1.optString("fullName").toString();
                        String doctorImage = jsonObject1.optString("doctorImage").toString();
                        String specialization = jsonObject1.optString("specialization").toString();
                        String clinic_redhealId = jsonObject1.optString("clinic_redhealId").toString();
                        String clinicName = jsonObject1.optString("clinicName").toString();
                        String clinicAddress = jsonObject1.optString("areaName").toString();
                        String distance = jsonObject1.optString("distance").toString();
                        String expereience = jsonObject1.optString("experience").toString();
                        String consultationFee = jsonObject1.optString("consultationFee").toString();
                        String premiumConsultationFee = jsonObject1.optString("premiumConsultationFee").toString();
                        String description = jsonObject1.optString("description").toString();
                        String like_status = jsonObject1.optString("like_status").toString();
                        doctorsearchItem.add(new DoctorsSearchItem(doctor_redhealId, fullName, doctorImage, specialization, clinic_redhealId, clinicName, clinicAddress, distance,expereience,consultationFee,premiumConsultationFee,description,like_status,presentLatitude,presentLongitude));


                    }

                    //Finally initializing our adapter
                    doctorSearchAdapter = new DoctorsSearchRecyclerAdapter(SearchActivity.this,doctorsearchItem);

                    //Adding adapter to recyclerview
                    recyclerViewDoctors.setAdapter(doctorSearchAdapter);

                    doctorSearchAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }

    private void SearchClinicsList(){

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL_CLINIC, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    clinicSearchItem.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("clinics_list");

                    String Message =response.getString("message");
                    if (Message.equals("No clinics available")){
                        searchClinicsLayout.setVisibility(View.GONE);

                    }else if (Message.equals("Result Found")){
                        searchClinicsLayout.setVisibility(View.VISIBLE);
                    }
                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                        String clinicImage = jsonObject1.optString("clinicImage").toString();
                        String clinic_redhealId = jsonObject1.optString("clinic_redhealId").toString();
                        String clinicName = jsonObject1.optString("clinicName").toString();
                        String address = jsonObject1.optString("address").toString();
                        String distance = jsonObject1.optString("distance").toString();

                        clinicSearchItem.add(new ClinicSearchItem(clinicImage, clinic_redhealId, clinicName, address, distance,presentLatitude,presentLongitude));

                    }

                    //Finally initializing our adapter
                    clinicSearchAdapter = new ClinicSearchRecyclerAdapter(SearchActivity.this,clinicSearchItem);

                    //Adding adapter to recyclerview
                    recyclerViewClinics.setAdapter(clinicSearchAdapter);

                    clinicSearchAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }

    private void SearchSpecalitiesList(){

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL_SPECIALIZATION, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    spelizationSearchItem.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("specializations_list");

                    String Message =response.getString("message");
                    if (Message.equals("No doctors available")){
                        searchSpeclitiesLayout.setVisibility(View.GONE);

                    }else if (Message.equals("Result Found")){
                        searchSpeclitiesLayout.setVisibility(View.VISIBLE);
                    }



                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                        String doctor_redhealId = jsonObject1.optString("doctor_redhealId").toString();
                        String status = jsonObject1.optString("status").toString();
                        String fullName = jsonObject1.optString("fullName").toString();
                        String doctorImage = jsonObject1.optString("doctorImage").toString();
                        String id = jsonObject1.optString("id").toString();
                        String specialization = jsonObject1.optString("specialization").toString();
                        spelizationSearchItem.add(new SpecailizationSearchItem(id, specialization, doctor_redhealId, status, fullName, doctorImage,presentLatitude,presentLongitude,presentPlaceName));

                    }

                    //Finally initializing our adapter
                    searchSpecilizationAdapter = new SearchSpecilizationRecyclerAdapter(SearchActivity.this,spelizationSearchItem);

                    //Adding adapter to recyclerview
                    recyclerViewSpecilities.setAdapter(searchSpecilizationAdapter);

                    searchSpecilizationAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }




    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
