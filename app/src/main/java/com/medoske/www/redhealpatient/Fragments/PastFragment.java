package com.medoske.www.redhealpatient.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.CurrentAppointmentAdapter;
import com.medoske.www.redhealpatient.Adapters.PastAppointmentAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.CurrentAppointmentDetails;
import com.medoske.www.redhealpatient.Items.CurrentItem;
import com.medoske.www.redhealpatient.Items.PastItem;
import com.medoske.www.redhealpatient.PastAppointmentDetailsActivity;
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
public class PastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;
    ListView listView;
    JSONArray jsonArray;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL ;
    private List<PastItem> pastItemArrayList =new ArrayList<>();
    private PastAppointmentAdapter mAdapter;

    String appointmentRefNo;

   View rootView;
    public PastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_clinic, container, false);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getActivity().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        listView =(ListView)rootView.findViewById(R.id.pastList);

        URL = Apis.PAST_APPOINTMENTS_LIST_URL+redHealId;

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

                                        new PastAppointmentDataList().execute();
                                    }
                                }
        );


        return rootView;
    }
    @Override
    public void onRefresh() {
        new PastAppointmentDataList().execute();
    }

    public class PastAppointmentDataList extends AsyncTask<String, String, JSONObject> {

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

            Log.e("PAST_LIST_URL", url);

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

                pastItemArrayList.clear();

                //Get the instance of JSONArray that contains JSONObjects
                jsonArray = json.optJSONArray("past_appointment_list");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                     appointmentRefNo = jsonObject.optString("appointmentRefNo").toString();
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

                   /* try{
                         intColor = Integer.parseInt(color);
                    }catch(NumberFormatException ex){ // handle your exception

                    }*/
                    pastItemArrayList.add(new PastItem(doctorName,doctorImage,status,bookingDate,bookingTime,clinicName,reschedule_reson,address,doctor_redhealId,specialization,patientName,patientImage,patient_redhealId,mobileNumber,latitude,longitude,clinic_redhealId,appointmentRefNo,bookingId,paymentMode));

                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}

            if(pastItemArrayList.size()>0)
            {

                mAdapter=new PastAppointmentAdapter(getContext(),R.layout.past_list_item, pastItemArrayList);
                listView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        PastItem pastItem = (PastItem) listView.getItemAtPosition(position);

                        Intent intent =new Intent(getContext(),PastAppointmentDetailsActivity.class);

                        SharedPreferences sp = getContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("appointmentRefNo",pastItem.getAppointRefNo());// key_name is the name through which you can retrieve it later.
                        editor.commit();

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

            }
            else
            {
                Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
