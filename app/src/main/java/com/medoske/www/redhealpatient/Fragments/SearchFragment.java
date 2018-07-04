package com.medoske.www.redhealpatient.Fragments;



import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;
import com.medoske.www.redhealpatient.Adapters.ClinicSearchAdapter;
import com.medoske.www.redhealpatient.Adapters.DoctorsSearchAdapter;
import com.medoske.www.redhealpatient.Adapters.ExpandableHeightListView;
import com.medoske.www.redhealpatient.Adapters.SearchSpecilizationAdapter;
import com.medoske.www.redhealpatient.Adapters.SpeclizationAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.ClinicProfileActivity;
import com.medoske.www.redhealpatient.ClinicProfileActivity1;
import com.medoske.www.redhealpatient.DoctorsListActivity;
import com.medoske.www.redhealpatient.DoctorsProfileActivity;
import com.medoske.www.redhealpatient.GPSTracker.GPSTracker;
import com.medoske.www.redhealpatient.Items.ClinicSearchItem;
import com.medoske.www.redhealpatient.Items.DoctorsSearchItem;
import com.medoske.www.redhealpatient.Items.SpecailizationSearchItem;
import com.medoske.www.redhealpatient.Items.SpecilizationItem;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    LocationManager locationManager;
    String mprovider;

    String Latitude,Longitude;


    MenuItem searchItem;
    SearchView searchView;
    Double lat;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();

    ArrayList<SpecilizationItem> specilizationItems = new ArrayList<SpecilizationItem>();
    SpeclizationAdapter adapter;

    ArrayList<DoctorsSearchItem> doctorsearchItem = new ArrayList<DoctorsSearchItem>();
    DoctorsSearchAdapter doctorSearchAdapter;

    ArrayList<ClinicSearchItem> clinicSearchItem = new ArrayList<ClinicSearchItem>();
    ClinicSearchAdapter clinicSearchAdapter;

    ArrayList<SpecailizationSearchItem> spelizationSearchItem = new ArrayList<SpecailizationSearchItem>();
    SearchSpecilizationAdapter searchSpecilizationAdapter;

    ListView defaultList, listDoctors, listClinics, listSpecialization;
   // ExpandableHeightListView defaultList;

    String URL_CLINIC, URL_DOCTOR, URL_SPECIALIZATION;

    LinearLayout searchSpeclitiesLayout, searchDoctorsLayout, searchClinicsLayout;

    GPSTracker gps;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    String presentLatitude,presentLongitude,presentPlaceName;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);


        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getActivity().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);
        presentLatitude = sp.getString("presentLatitude", "1");
        presentLongitude = sp.getString("presentLongitude", "1");
        presentPlaceName = sp.getString("presentLocality", "Select Location");



       // ExpandableHeightListView defaultList = (ExpandableHeightListView)rootView.findViewById(R.id.speclizationList);
        defaultList = (ListView) rootView.findViewById(R.id.speclizationList);
        listDoctors = (ListView) rootView.findViewById(R.id.listDoctors);
        listClinics = (ListView) rootView.findViewById(R.id.listClinics);
        listSpecialization = (ListView) rootView.findViewById(R.id.listSpecilities);

        searchSpeclitiesLayout = (LinearLayout) rootView.findViewById(R.id.speclizationLayout);
        searchDoctorsLayout = (LinearLayout) rootView.findViewById(R.id.doctorsLayout);
        searchClinicsLayout = (LinearLayout) rootView.findViewById(R.id.clinicsLayout);


        searchView = (SearchView) rootView.findViewById(R.id.search_bar);
        loadSearchView();
       // GpsLocation();

        new DefualtList1().execute();
        return rootView;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        searchItem = menu.findItem(R.id.location);
        searchItem.setTitle(presentPlaceName);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                try {
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build((Activity) getContext());
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

    // A place has been received; use requestCode to track the request.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
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

                /*((TextView) findViewById(R.id.searched_address))
                        .setText(place.getName()+",\n"+
                                place.getAddress() +"\n" + place.getPhoneNumber());
*/
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
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

                Log.e("latitude123", "Place: " + lat);
                URL_DOCTOR = Apis.DOCTORS_SEARCH_URL + searchQuery +"/"+ presentLatitude+"/"+presentLongitude;
                URL_CLINIC = Apis.CLINIC_SEARCH_URL + searchQuery + "/"+ presentLatitude+"/"+presentLongitude;
                URL_SPECIALIZATION = Apis.SPECLIZATION_SEARCH_URL + searchQuery;

                Log.e("search", "" + URL_DOCTOR);
                new SearchDoctorsList().execute(URL_DOCTOR);
                new SearchClinicsList().execute(URL_CLINIC);
                new SearchSpecalitiesList().execute(URL_SPECIALIZATION);

                defaultList.setVisibility(View.GONE);

                if (searchQuery.equals("")) {

                    defaultList.setVisibility(View.VISIBLE);

                }


                return true;
            }
        });

    }




    // default list for speialities
    public class DefualtList1 extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(getContext());
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
        protected JSONObject doInBackground(String... urls) {
            //Getting product details from making HTTP Request
            JSONObject json = jsonParser.makeHttpRequest(Apis.SPECLIZATION_URL, "GET", param);

            Log.e("SPECLIZATION_URL", Apis.SPECLIZATION_URL);

           /* // Getting JSON from URL
            JSONObject[] jsons = new JSONObject[2];
            jsons[0] = jsonParser.makeHttpRequest(Apis.SPECLIZATION_URL, "GET", param);
            jsons[1] = jsonParser.makeHttpRequest(URL, "GET", param);*/

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

            progressDialog.dismiss();


            Log.e("data", String.valueOf(json));

            String data = "";


            try {
                specilizationItems.clear();

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray1 = json.optJSONArray("specialization");


                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray1.length(); i++) {

                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                    String id = jsonObject1.optString("id").toString();
                    String specialization = jsonObject1.optString("specialization").toString();
                    String image = jsonObject1.optString("image").toString();
                    Log.e("imageviewpath",""+image);

                    specilizationItems.add(new SpecilizationItem(id, specialization,image,presentLatitude,presentLongitude,presentPlaceName));

                }


                // output.setText(data);


                if (specilizationItems.size() > 0) {
                    adapter = new SpeclizationAdapter(getContext(), R.layout.specailizationlist_item, specilizationItems);
//                    defaultList.setItemsCanFocus(false);
                    defaultList.setAdapter(adapter);
                    ListUtils.setDynamicHeight(defaultList);
                    defaultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SpecilizationItem specilizationItem = (SpecilizationItem) defaultList.getItemAtPosition(position);

                            Intent intent = new Intent(getContext(), DoctorsListActivity.class);
                            intent.putExtra("specilization", specilizationItem.getSpecialization());
                            intent.putExtra("specializationId", specilizationItem.getSpecializationId());
                            intent.putExtra("latitude",presentLatitude);
                            intent.putExtra("longitude", presentLongitude);
                            intent.putExtra("placeName", presentPlaceName);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });


                } else {
                    // Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
                    defaultList.setVisibility(View.GONE);
                }

                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }

    // search speailizations
    public class SearchSpecalitiesList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(getContext());
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
        protected JSONObject doInBackground(String... urls) {
            //Getting product details from making HTTP Request
            JSONObject json = jsonParser.makeHttpRequest(URL_SPECIALIZATION, "GET", param);

            Log.e("URL", URL_SPECIALIZATION);


            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

            progressDialog.dismiss();


            Log.e("data", String.valueOf(json));

            String data = "";

            //ArrayList<DoctorsSearchItem> doctorsearchItem = new ArrayList<DoctorsSearchItem>();
            JSONObject resultObject = null;
            JSONArray jsonArray = null;
            DoctorsSearchItem newItemObject = null;
            try {
                spelizationSearchItem.clear();
                resultObject = new JSONObject(String.valueOf(json));
                System.out.println("Testing the water " + resultObject.toString());
                jsonArray = resultObject.optJSONArray("specializations_list");

                String Message =resultObject.getString("message");
                if (Message.equals("No doctors available")){
                    searchSpeclitiesLayout.setVisibility(View.GONE);

                }else if (Message.equals("Result Found")){
                    searchSpeclitiesLayout.setVisibility(View.VISIBLE);
                }

                if (jsonArray != null&& jsonArray.length()>0) {     // check jsonArray is null?
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonChildNode = null;
                        try {
                            spelizationSearchItem.clear();
                            jsonChildNode = jsonArray.getJSONObject(i);
                            String doctor_redhealId = jsonChildNode.optString("doctor_redhealId").toString();
                            String status = jsonChildNode.optString("status").toString();
                            String fullName = jsonChildNode.optString("fullName").toString();
                            String doctorImage = jsonChildNode.optString("doctorImage").toString();
                            String id = jsonChildNode.optString("id").toString();
                            String specialization = jsonChildNode.optString("specialization").toString();
                           // spelizationSearchItem.add(new SpecailizationSearchItem(id, specialization, doctor_redhealId, status, fullName, doctorImage));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    if (spelizationSearchItem.size() > 0) {


                        searchSpecilizationAdapter = new SearchSpecilizationAdapter(getContext(), R.layout.specailizationlist_item, spelizationSearchItem);

                        listSpecialization.setItemsCanFocus(false);
                        listSpecialization.setAdapter(searchSpecilizationAdapter);

                        ListUtils.setDynamicHeight(listSpecialization);


                        listSpecialization.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                SpecailizationSearchItem specailizationSearchItem = (SpecailizationSearchItem) listSpecialization.getItemAtPosition(position);

                                Intent intent = new Intent(getContext(), DoctorsListActivity.class);
                                intent.putExtra("specilization", specailizationSearchItem.getSpecialization());
                                intent.putExtra("specializationId", specailizationSearchItem.getId());
                                intent.putExtra("latitude",presentLatitude);
                                intent.putExtra("longitude", presentLongitude);
                                intent.putExtra("placeName", presentPlaceName);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                    }
                    searchSpecilizationAdapter.notifyDataSetChanged();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    // list for search doctors
    public class SearchDoctorsList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(getContext());
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
        protected JSONObject doInBackground(String... urls) {
            //Getting product details from making HTTP Request
            JSONObject json = jsonParser.makeHttpRequest(URL_DOCTOR, "GET", param);

            Log.e("URL", URL_DOCTOR);


            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

            progressDialog.dismiss();


            Log.e("data", String.valueOf(json));

            String data = "";

            // ArrayList<DoctorsSearchItem> doctorsearchItem = new ArrayList<DoctorsSearchItem>();
            JSONObject resultObject = null;
            JSONArray jsonArray = null;
            DoctorsSearchItem newItemObject = null;
            try {
                doctorsearchItem.clear();
                resultObject = new JSONObject(String.valueOf(json));
                System.out.println("Testing the water " + resultObject.toString());
                jsonArray = resultObject.optJSONArray("doctors_list");

                String Message =resultObject.getString("message");
                if (Message.equals("No doctors available")){
                    searchDoctorsLayout.setVisibility(View.GONE);

                }else if (Message.equals("Result Found")){
                    searchDoctorsLayout.setVisibility(View.VISIBLE);
                }

                if (jsonArray != null && jsonArray.length()>0) {
                    // check jsonArray is null?
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonChildNode = null;
                        try {
                            doctorsearchItem.clear();
                            jsonChildNode = jsonArray.getJSONObject(i);
                            String doctor_redhealId = jsonChildNode.optString("doctor_redhealId").toString();
                            String fullName = jsonChildNode.optString("fullName").toString();
                            String doctorImage = jsonChildNode.optString("doctorImage").toString();
                            String specialization = jsonChildNode.optString("specialization").toString();
                            String clinic_redhealId = jsonChildNode.optString("clinic_redhealId").toString();
                            String clinicName = jsonChildNode.optString("clinicName").toString();
                            String clinicAddress = jsonChildNode.optString("areaName").toString();
                            String distance = jsonChildNode.optString("distance").toString();
                            String expereience = jsonChildNode.optString("experience").toString();
                            String consultationFee = jsonChildNode.optString("consultationFee").toString();
                            String premiumConsultationFee = jsonChildNode.optString("premiumConsultationFee").toString();
                            String description = jsonChildNode.optString("description").toString();
                            String like_status = jsonChildNode.optString("like_status").toString();
                            doctorsearchItem.add(new DoctorsSearchItem(doctor_redhealId, fullName, doctorImage, specialization, clinic_redhealId, clinicName, clinicAddress, distance,expereience,consultationFee,premiumConsultationFee,description,like_status,presentLatitude,presentLongitude));



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (doctorsearchItem.size() > 0) {
                        doctorSearchAdapter = new DoctorsSearchAdapter(getContext(), R.layout.doctorsearch_item, doctorsearchItem);
                        listDoctors.setItemsCanFocus(false);
                        listDoctors.setAdapter(doctorSearchAdapter);
                        //doctorSearchAdapter.notifyDataSetChanged();
                        ListUtils.setDynamicHeight(listDoctors);
                        listDoctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                DoctorsSearchItem doctorSearchItem = (DoctorsSearchItem) listDoctors.getItemAtPosition(position);
                                Intent intent = new Intent(getContext(), DoctorsProfileActivity.class);
                                intent.putExtra("specilization",doctorSearchItem.getSpecialization());
                                intent.putExtra("doctorImage",doctorSearchItem.getDoctorImage());
                                intent.putExtra("doctorName",doctorSearchItem.getFullName());
                                intent.putExtra("areaName",doctorSearchItem.getClinicAddress());
                                intent.putExtra("experience",doctorSearchItem.getExperience());
                                intent.putExtra("fee",doctorSearchItem.getConsultationFee());
                                intent.putExtra("doctorId",doctorSearchItem.getDoctor_redhealId());
                                intent.putExtra("description",doctorSearchItem.getDescription());
                                intent.putExtra("latitude",presentLatitude);
                                intent.putExtra("longitude", presentLongitude);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                        searchDoctorsLayout.setVisibility(View.VISIBLE);
                    } else {
                        searchDoctorsLayout.setVisibility(View.GONE);
                    }

                    doctorSearchAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    // list for clinics
    public class SearchClinicsList extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(getContext());
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
        protected JSONObject doInBackground(String... urls) {
            //Getting product details from making HTTP Request
            JSONObject json = jsonParser.makeHttpRequest(URL_CLINIC, "GET", param);
            Log.e("URL", URL_CLINIC);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            super.onPostExecute(json);
            progressDialog.dismiss();
            Log.e("data", String.valueOf(json));

            String data = "";
            // ArrayList<DoctorsSearchItem> doctorsearchItem = new ArrayList<DoctorsSearchItem>();
            JSONObject resultObject = null;
            JSONArray jsonArray = null;
            DoctorsSearchItem newItemObject = null;
            try {
                clinicSearchItem.clear();
                resultObject = new JSONObject(String.valueOf(json));
                System.out.println("Testing the water " + resultObject.toString());
                jsonArray = resultObject.optJSONArray("clinics_list");
                clinicSearchAdapter = new ClinicSearchAdapter(getContext(), R.layout.clinicsearch_item, clinicSearchItem);

                String Message =resultObject.getString("message");
                if (Message.equals("No clinics available")){
                    searchDoctorsLayout.setVisibility(View.GONE);

                }else if (Message.equals("Result Found")){
                    searchDoctorsLayout.setVisibility(View.VISIBLE);
                }

                if (jsonArray != null && jsonArray.length()>0) {     // check jsonArray is null?
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonChildNode = null;
                        try {
                            jsonChildNode = jsonArray.getJSONObject(i);
                            clinicSearchItem.clear();

                            String clinicImage = jsonChildNode.optString("clinicImage").toString();
                            String clinic_redhealId = jsonChildNode.optString("clinic_redhealId").toString();
                            String clinicName = jsonChildNode.optString("clinicName").toString();
                            String address = jsonChildNode.optString("address").toString();
                            String distance = jsonChildNode.optString("distance").toString();

                            clinicSearchItem.add(new ClinicSearchItem(clinicImage, clinic_redhealId, clinicName, address, distance,presentLatitude,presentLongitude));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    if (clinicSearchItem.size() > 0) {

                        listClinics.setItemsCanFocus(false);
                        listClinics.setAdapter(clinicSearchAdapter);
                        // clinicSearchAdapter.notifyDataSetChanged();
                        ListUtils.setDynamicHeight(listClinics);
                        listClinics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ClinicSearchItem clinicSearchItem = (ClinicSearchItem) listClinics.getItemAtPosition(position);

                                Intent intent = new Intent(getContext(), ClinicProfileActivity1.class);
                                intent.putExtra("clinicId",clinicSearchItem.getClinic_redhealId());
                                intent.putExtra("clinicName",clinicSearchItem.getClinicName());
                                intent.putExtra("latitude",presentLatitude);
                                intent.putExtra("longitude", presentLongitude);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                    } else {
                        // Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
                        searchClinicsLayout.setVisibility(View.GONE);
                    }
                    clinicSearchAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // ListUtils for 2 or more list views in same activity
    static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.location:
                //do something

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
