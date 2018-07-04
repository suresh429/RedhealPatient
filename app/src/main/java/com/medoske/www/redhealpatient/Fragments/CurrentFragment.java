package com.medoske.www.redhealpatient.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.CurrentAppointmentAdapter;
import com.medoske.www.redhealpatient.Adapters.FeedsAdpter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.CurrentAppointmentDetails;
import com.medoske.www.redhealpatient.Items.CurrentItem;
import com.medoske.www.redhealpatient.Items.FeedsItem;
import com.medoske.www.redhealpatient.PackagesListActivity;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;
    ListView listView;
    JSONArray jsonArray;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL ;
    private List<CurrentItem> currentItemArrayList =new ArrayList<>();
    private CurrentAppointmentAdapter mAdapter;

    View rootView;
    public CurrentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_about_doctor, container, false);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getActivity().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        listView =(ListView)rootView.findViewById(R.id.upcomingList);

        URL = Apis.CURRENT_APPOINTMENTS_LIST_URL+redHealId;

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        new CurrentAppointmentDataList().execute();
                                    }
                                }
        );


        return rootView;
    }

    @Override
    public void onRefresh() {
        new CurrentAppointmentDataList().execute();
    }


    public class CurrentAppointmentDataList extends AsyncTask<String, String, JSONObject> {

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
            // showing refresh animation before making http call
            swipeRefreshLayout.setRefreshing(true);

        }

        @Override
        protected JSONObject doInBackground(String... params) {
// appending offset to url
            String url = URL +"/" +offSet;
            //Getting product details from making HTTP Request
            JSONObject json = jsonParser.makeHttpRequest(url, "GET", param);

            Log.e("CURRENT_LIST_URL", url);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);
            swipeRefreshLayout.setRefreshing(false);
            progressDialog.dismiss();

            Log.e("data", String.valueOf(json));

            String data = "";
            try {

                currentItemArrayList.clear();
                //Get the instance of JSONArray that contains JSONObjects
                jsonArray = json.optJSONArray("current_appointment_list");

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
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}

            if(currentItemArrayList.size()>0)
            {

                mAdapter=new CurrentAppointmentAdapter(getContext(),R.layout.upcomin_list_item, currentItemArrayList);
                listView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
/*
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        CurrentItem currentItem = (CurrentItem) listView.getItemAtPosition(position);


                    }
                });
*/

            }
            else
            {
                Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
