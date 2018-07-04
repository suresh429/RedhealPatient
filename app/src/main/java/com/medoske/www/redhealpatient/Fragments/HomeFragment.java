package com.medoske.www.redhealpatient.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.medoske.www.redhealpatient.Adapters.AlternateRecyclerAdapter;
import com.medoske.www.redhealpatient.Adapters.CurrentRecycleAdapter;
import com.medoske.www.redhealpatient.Adapters.DefaultSpcilitiesRecyclerAdapter;
import com.medoske.www.redhealpatient.Adapters.TipsSeasonalAdapter;
import com.medoske.www.redhealpatient.Adapters.TopSpecialitiesRecyclerview;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.AppointmentsActivity;
import com.medoske.www.redhealpatient.DiagnosticsActivity;
import com.medoske.www.redhealpatient.FeedsActivity;
import com.medoske.www.redhealpatient.FeedsActivity1;
import com.medoske.www.redhealpatient.GPSTracker.GPSTracker;
import com.medoske.www.redhealpatient.Items.AlternateTreatmentItem;
import com.medoske.www.redhealpatient.Items.CurrentItem;
import com.medoske.www.redhealpatient.Items.SpecilizationItem;
import com.medoske.www.redhealpatient.Items.TipsSeasonItem;
import com.medoske.www.redhealpatient.Items.TopSpelitiesItem;
import com.medoske.www.redhealpatient.LoginActivity;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.SearchActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

import static com.medoske.www.redhealpatient.R.id.location;
import static com.medoske.www.redhealpatient.R.id.thing_proto;
import static com.medoske.www.redhealpatient.R.id.view;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private BannerSlider bannerSlider,bannerSlider1;
    LinearLayout doctorLayout,feedsLayout,diagnosticsLayout;
    RelativeLayout searchLayout;

    String locality;

    final String TAG = this.getClass().getName();
    GPSTracker gps;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    String presentLatitude,presentLongitude;
    View rootView;

    RecyclerView MyRecyclerView,MyRecyclerView2,MyRecyclerView3,MyRecyclerView4;

    String URL ;
    TextView txtMore,txtMore2,txtMore3,txtMore4;

    private List<CurrentItem> currentItemArrayList =new ArrayList<>();
    CurrentRecycleAdapter currentRecycleAdapter;

    private List<TopSpelitiesItem> topSpelitiesItemArrayList =new ArrayList<>();
    TopSpecialitiesRecyclerview topSpecialitiesRecyclerview;

    private List<AlternateTreatmentItem> alternateTreatmentItemArrayList =new ArrayList<>();
    AlternateRecyclerAdapter alternateRecyclerAdapter;

    private List<TipsSeasonItem> tipsSeasonItemArrayList =new ArrayList<>();
    TipsSeasonalAdapter tipsSeasonalAdapter;

    TextView txtCurrentScheduled,txtTopSpecialities,txtAlternate,txtSeasonal;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);



        SharedPreferences sp = getContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);
        sp.edit().putBoolean("isLoggedIn", true).commit();

        URL = Apis.CURRENT_APPOINTMENTS_LIST_URL+redHealId;


        // fonts loaded

        txtCurrentScheduled =(TextView)rootView.findViewById(R.id.textViewCurrentscheduled);
        txtCurrentScheduled.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

        txtTopSpecialities =(TextView)rootView.findViewById(R.id.textViewTopspeclities);
        txtTopSpecialities.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

        txtAlternate =(TextView)rootView.findViewById(R.id.textViewalternate);
        txtAlternate.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

        txtSeasonal =(TextView)rootView.findViewById(R.id.textViewSeasonal);
        txtSeasonal.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));



        MyRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        MyRecyclerView.setLayoutManager(MyLayoutManager);
        MyRecyclerView.setNestedScrollingEnabled(false);

        MyRecyclerView2 = (RecyclerView) rootView.findViewById(R.id.cardViewTopSpecilities);
        MyRecyclerView2.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager2 = new LinearLayoutManager(getActivity());
        MyLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        MyRecyclerView2.setLayoutManager(MyLayoutManager2);
        MyRecyclerView2.setNestedScrollingEnabled(false);


        MyRecyclerView3 = (RecyclerView) rootView.findViewById(R.id.cardViewAlternate);
        MyRecyclerView3.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager3 = new LinearLayoutManager(getActivity());
        MyLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        MyRecyclerView3.setLayoutManager(MyLayoutManager3);
        MyRecyclerView3.setNestedScrollingEnabled(false);
        alternateRecyclerAdapter=new AlternateRecyclerAdapter(getContext(),alternateTreatmentItemArrayList);
        MyRecyclerView3.setAdapter(alternateRecyclerAdapter);

        MyRecyclerView4 = (RecyclerView) rootView.findViewById(R.id.cardViewRecommendation);
        MyRecyclerView4.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager4 = new LinearLayoutManager(getActivity());
        MyLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        MyRecyclerView4.setLayoutManager(MyLayoutManager4);
        MyRecyclerView4.setNestedScrollingEnabled(false);
        tipsSeasonalAdapter=new TipsSeasonalAdapter(getContext(),tipsSeasonItemArrayList);
        MyRecyclerView4.setAdapter(tipsSeasonalAdapter);



        txtMore=(TextView)rootView.findViewById(R.id.viewAll) ;
        txtMore2 =(TextView)rootView.findViewById(R.id.viewAll2);
        txtMore3 =(TextView)rootView.findViewById(R.id.viewAll3);
        txtMore4 =(TextView)rootView.findViewById(R.id.viewAll4);

        searchLayout =(RelativeLayout)rootView.findViewById(R.id.searchLayout);
        feedsLayout=(LinearLayout)rootView.findViewById(R.id.feeds_layout);
        diagnosticsLayout =(LinearLayout)rootView.findViewById(R.id.diagnostics_Layout);
        doctorLayout=(LinearLayout)rootView.findViewById(R.id.doctor);

        txtMore.setOnClickListener(this);
        txtMore2.setOnClickListener(this);
        txtMore3.setOnClickListener(this);
        txtMore4.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        feedsLayout.setOnClickListener(this);
        diagnosticsLayout.setOnClickListener(this);
        doctorLayout.setOnClickListener(this);



        bannerSlider = (BannerSlider)rootView.findViewById(R.id.banner_slider1);

        //Add banners using image urls
        bannerSlider.addBanner(new RemoteBanner(Apis.IP_ADDRESS_URL+"banners/5.png"));

        bannerSlider.addBanner(new RemoteBanner(Apis.IP_ADDRESS_URL+"banners/1.png"));

        bannerSlider.addBanner(new RemoteBanner(Apis.IP_ADDRESS_URL+"banners/7.png"));

        bannerSlider.addBanner(new RemoteBanner(Apis.IP_ADDRESS_URL+"banners/6.png"));

        bannerSlider.addBanner(new RemoteBanner(Apis.IP_ADDRESS_URL+"banners/4.png"));

        bannerSlider.addBanner(new RemoteBanner(Apis.IP_ADDRESS_URL+"banners/3.png"));

        bannerSlider.addBanner(new RemoteBanner(Apis.IP_ADDRESS_URL+"banners/2.png"));

        //add banner using resource drawable
        // bannerSlider.addBanner(new DrawableBanner(R.drawable.background));
        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), "Banner with position " + String.valueOf(position) + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });


        // permissions

        try {
            if (ActivityCompat.checkSelfPermission(getContext(), mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                // execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create class object
        gps = new GPSTracker(getContext());

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            presentLatitude = String.valueOf(latitude);
            presentLongitude = String.valueOf(longitude);

            Geocoder gcd=new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses;

            try {
                addresses=gcd.getFromLocation(latitude,longitude,1);
                if(addresses.size()>0)

                {
                    //while(locTextView.getText().toString()=="Location") {
                    String cityname = addresses.get(0).getLocality().toString();
                    locality = addresses.get(0).getSubLocality().toString();
                    String locality1 = addresses.get(0).getAddressLine(0).toString();
                    //locTextView.setText(cityname);
                    // }

                    Log.e("city name",""+locality);
                    Log.e("locality1",""+locality1);
                }

            } catch (IOException e) {
                e.printStackTrace();

            }




            sp = getContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("presentLatitude",presentLatitude);// key_name is the name through which you can retrieve it later.
            editor.putString("presentLongitude",presentLongitude);
            editor.putString("presentLocality",locality);
            editor.commit();

            // \n is for new line
            Toast.makeText(getContext(), "Your Location is - \nLat: "
                    + presentLatitude + "\nLong: " + presentLongitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        getCurrentAppointmentData();
        topSpecilitiesData();
        prepareTreatmentsData();
        prepareReccommendationData();

        return rootView;
    }

    private void getCurrentAppointmentData() {


        //Showing a progress dialog
      //  final ProgressDialog loading = ProgressDialog.show(getContext(),"Loading Data", "Please wait...",true,true);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                //Dismissing progress dialog
               // loading.dismiss();
                try {
                   JSONArray jsonArray = response.optJSONArray("current_appointment_list");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String appointmentRefNo = jsonObject.optString("appointmentRefNo").toString();
                        String bookingId = jsonObject.optString("bookingId").toString();
                        String bookingDate = jsonObject.optString("bookingDate").toString();
                        String bookingTime = jsonObject.optString("bookingTime").toString();
                        String patient_redhealId = jsonObject.optString("patient_redhealId").toString();
                        String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                        String clinic_redhealId = jsonObject.optString("clinic_redhealId").toString();
                        String status = jsonObject.optString("status").toString();
                        String doctorName = jsonObject.optString("doctorName").toString();
                        String doctorImage = jsonObject.optString("doctorImage").toString();
                        String specialization = jsonObject.optString("specialization").toString();
                        String clinicName = jsonObject.optString("clinicName").toString();
                        String address = jsonObject.optString("address").toString();
                        String latitude = jsonObject.optString("latitude").toString();
                        String longitude = jsonObject.optString("longitude").toString();
                        String patientName = jsonObject.optString("patientName").toString();
                        String patientImage = jsonObject.optString("patientImage").toString();
                        String mobileNumber = jsonObject.optString("mobileNumber").toString();
                        String reschedule_reson = jsonObject.optString("reschedule_reson").toString();
                        String paymentMode = jsonObject.optString("paymentMode").toString();
                        String pcci = jsonObject.optString("pcci").toString();

                   /* try{
                         intColor = Integer.parseInt(color);
                    }catch(NumberFormatException ex){ // handle your exception

                    }*/
                        currentItemArrayList.add(new CurrentItem(doctorName,doctorImage,status,bookingDate,bookingTime,clinicName,reschedule_reson,address,doctor_redhealId,specialization,patientName,patientImage,patient_redhealId,mobileNumber,latitude,longitude,clinic_redhealId,appointmentRefNo,bookingId,paymentMode,pcci));

                    }

                    //Finally initializing our adapter
                    currentRecycleAdapter = new CurrentRecycleAdapter(getContext(),currentItemArrayList);

                    //Adding adapter to recyclerview
                    MyRecyclerView.setAdapter(currentRecycleAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
            }
        });

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(jsonObjReq);

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }

    private void topSpecilitiesData() {

       // showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Apis.TOP_SPECLIZATION_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    topSpelitiesItemArrayList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("specialization");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                        String id = jsonObject1.optString("id").toString();
                        String specialization = jsonObject1.optString("specialization").toString();
                        String image = jsonObject1.optString("image").toString();
                        Log.e("imageviewpath", "" + image);

                        topSpelitiesItemArrayList.add(new TopSpelitiesItem(id, specialization, image,presentLatitude,presentLongitude,locality));

                    }

                    topSpecialitiesRecyclerview=new TopSpecialitiesRecyclerview(getContext(),topSpelitiesItemArrayList);
                    MyRecyclerView2.setAdapter(topSpecialitiesRecyclerview);

                    topSpecialitiesRecyclerview.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
               // hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
               // hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }

    private void prepareTreatmentsData() {
        AlternateTreatmentItem movie1 = new AlternateTreatmentItem("Udana vayu",R.drawable.sample1);
        alternateTreatmentItemArrayList.add(movie1);

        movie1 = new AlternateTreatmentItem("Vyana vayu ",R.drawable.sample2);
        alternateTreatmentItemArrayList.add(movie1);

        movie1 = new AlternateTreatmentItem("Samana vayu",R.drawable.sample3);
        alternateTreatmentItemArrayList.add(movie1);

        movie1 = new AlternateTreatmentItem("Apana vayu",R.drawable.sample4);
        alternateTreatmentItemArrayList.add(movie1);

        movie1 = new AlternateTreatmentItem("Vyana vayu",R.drawable.sample3);
        alternateTreatmentItemArrayList.add(movie1);

        alternateRecyclerAdapter.notifyDataSetChanged();
    }

    private void prepareReccommendationData() {
        TipsSeasonItem movie2 = new TipsSeasonItem("Skip energy drinks when you're tired","Skin",R.drawable.feeds_image);
        tipsSeasonItemArrayList.add(movie2);

        movie2 = new TipsSeasonItem("Ditch diet soda to lose weight ","Weight Loss",R.drawable.feeds_image);
        tipsSeasonItemArrayList.add(movie2);

        movie2 = new TipsSeasonItem("To wear a smaller size, gain weight","Gain Weight",R.drawable.feeds_image);
        tipsSeasonItemArrayList.add(movie2);

        tipsSeasonalAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.viewAll: /** Start a new Activity MyCards.java */

                Intent moreIntent =new Intent(getContext(), AppointmentsActivity.class);
                moreIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(moreIntent);

                break;

            case R.id.viewAll2: /** AlerDialog when click on Exit */

            /*FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SearchFragment llf = new SearchFragment();
                ft.replace(R.id.content_layout, llf);
                ft.commit();*/

                Intent moreIntent5 =new Intent(getContext(), SearchActivity.class);
                moreIntent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(moreIntent5);

                break;

            case R.id.viewAll3: /** Start a new Activity MyCards.java */

                break;

            case R.id.viewAll4: /** AlerDialog when click on Exit */

                Intent moreIntent4 =new Intent(getContext(), FeedsActivity1.class);
                moreIntent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(moreIntent4);

                break;

            case R.id.searchLayout: /** Start a new Activity MyCards.java */

                Intent searchIntent =new Intent(getContext(), SearchActivity.class);
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(searchIntent);

                break;

            case R.id.diagnostics_Layout: /** AlerDialog when click on Exit */

                Intent diagnoIntent =new Intent(getContext(), DiagnosticsActivity.class);
                diagnoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(diagnoIntent);

                break;

            case R.id.feeds_layout: /** Start a new Activity MyCards.java */

                Intent feedsIntent =new Intent(getContext(), FeedsActivity1.class);
                feedsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(feedsIntent);

                break;

            case R.id.doctor: /** AlerDialog when click on Exit */

                checkBox();
                break;
        }
    }


    private void checkBox(){
        final View checkBoxView = View.inflate(getContext(), R.layout.checkbox, null);
        final CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Save to shared preferences
            }
        });

        checkBox.setText("Accept the Terms & Conditions.");


        AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
        builder.setMessage("Please confirm that you are about to be diverted to a Doctor on call ?")
                .setView(checkBoxView)
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getContext(), "clicked suresh", Toast.LENGTH_SHORT).show();

                        if(checkBox.isChecked()){
                            //do some validation
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:+91720999666"));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(intent);
                        }else {

                            Toast.makeText(getContext(), "Please Accept the Terms & Conditions.", Toast.LENGTH_SHORT).show();
                        }


                    }
                })
                .setNegativeButton("NO",null);
        AlertDialog alert =builder.create();
        alert.show();

    }

    private class GetLocationTask extends AsyncTask<Void, Void, Void> {
        JSONObject jsonObject;
        String address;
        Address[] addrs;

        public GetLocationTask(String a, Address[] addrs) {
            this.address = a;
            this.addrs = addrs;
        }

        @Override
        protected Void doInBackground(Void... params) {
            getLocationInfo(address);
            return null;
        }

        private void getLocationInfo(String address) {

            String query = "http://maps.google.com/maps/api/geocode/json?address=" + address.replaceAll(" ","%20")
                    + "&sensor=false";
            Address addr = null;
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(query);

            HttpResponse response;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                response = client.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {

                    HttpEntity entity = response.getEntity();
                    InputStream stream = entity.getContent();
                    int b;
                    while ((b = stream.read()) != -1) {
                        stringBuilder.append((char) b);
                    }
                    try {
                        jsonObject = new JSONObject(stringBuilder.toString());
                        addr = new Address(Locale.getDefault());
                        JSONArray addrComp = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                                .getJSONArray("address_components");
                        String locality = ((JSONArray)((JSONObject)addrComp.get(0)).get("types")).getString(0);
                        if (locality.compareTo("locality") == 0) {
                            locality = ((JSONObject)addrComp.get(0)).getString("long_name");
                            addr.setLocality(locality);
                        }
                        String adminArea = ((JSONArray)((JSONObject)addrComp.get(2)).get("types")).getString(0);
                        if (adminArea.compareTo("administrative_area_level_1") == 0) {
                            adminArea = ((JSONObject)addrComp.get(2)).getString("long_name");
                            addr.setAdminArea(adminArea);
                        }
                        String country = ((JSONArray)((JSONObject)addrComp.get(3)).get("types")).getString(0);
                        if (country.compareTo("country") == 0) {
                            country = ((JSONObject)addrComp.get(3)).getString("long_name");
                            addr.setCountryName(country);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Double lon = Double.valueOf(0);
                    Double lat = Double.valueOf(0);

                    try {

                        lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                                .getJSONObject("geometry").getJSONObject("location")
                                .getDouble("lng");

                        lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                                .getJSONObject("geometry").getJSONObject("location")
                                .getDouble("lat");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    addr.setLatitude(lat);
                    addr.setLongitude(lon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            addrs[0] = addr;

        }
    }

}
