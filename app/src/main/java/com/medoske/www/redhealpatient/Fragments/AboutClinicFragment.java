package com.medoske.www.redhealpatient.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.medoske.www.redhealpatient.Adapters.ClinicMapAdapter;
import com.medoske.www.redhealpatient.Adapters.SelectClinicAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.SelectClinicItem;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.SelectClinicACtivity;
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
public class AboutClinicFragment extends Fragment {

    JSONArray jsonArray;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL;
    TextView textFee,textClinicName,textAddress,textTimings,textMore;
    WebView webViewMap;
    View rootView;

    public AboutClinicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_about_clinic, container, false);



        String doctorId = getActivity().getIntent().getExtras().getString("doctorId");


         URL = Apis.ABOUT_CLINIC_URL+doctorId+"/17.7413/83.3248";
        Log.e("URL",""+URL);

       // listView =(ListView)rootView.findViewById(R.id.mapList);

        textAddress=(TextView)rootView.findViewById(R.id.textAddress);
        textFee=(TextView)rootView.findViewById(R.id.textFee);
        textClinicName=(TextView)rootView.findViewById(R.id.textClinicName);
        textTimings=(TextView)rootView.findViewById(R.id.textTimings);
        textMore=(TextView)rootView.findViewById(R.id.textViewMore);

        webViewMap=(WebView)rootView.findViewById(R.id.webViewMap);
        webViewMap.setWebViewClient(new WebViewClient());
        webViewMap.getSettings().setJavaScriptEnabled(true);
        webViewMap.setFocusable(false);
        webViewMap.setClickable(false);

        new ClinicDataList().execute();
        return rootView;
    }


    public class ClinicDataList extends AsyncTask<String, String, JSONObject> {

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
                jsonArray = json.optJSONArray("clinics_list_doctorId");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    final String specialization = jsonObject.optString("specialization").toString();
                    final String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                    final String doctorName = jsonObject.optString("doctorName").toString();
                    final String doctorImage = jsonObject.optString("doctorImage").toString();
                    String clinic_redhealId = jsonObject.optString("clinic_redhealId").toString();
                    String clinicName = jsonObject.optString("clinicName").toString();
                    String primaryNumber = jsonObject.optString("primaryNumber").toString();
                    String latitude = jsonObject.optString("latitude").toString();
                    String longitude = jsonObject.optString("longitude").toString();
                    String imagePath = jsonObject.optString("imagePath").toString();
                    String consultationFee = jsonObject.optString("consultationFee").toString();
                    String instantConsultationFee = jsonObject.optString("instantConsultationFee").toString();
                    String clinicAddress = jsonObject.optString("clinicAddress").toString();
                    String areaName = jsonObject.optString("areaName").toString();
                    String areaId = jsonObject.optString("areaId").toString();
                    String timing1 = jsonObject.optString("timing1").toString();
                    String timing2 = jsonObject.optString("timing2").toString();
                    String timing3 = jsonObject.optString("timing3").toString();
                    String clinicImage = jsonObject.optString("clinicImage").toString();
                    String distance = jsonObject.optString("distance").toString();

                    String webUrl ="http://medoske.com/rhdoctor/welcome/test?lat="+latitude+"&lan="+longitude;
                    Log.e("weburl",""+webUrl);

                    textAddress.setText(clinicAddress);
                    textClinicName.setText(clinicName);
                    textFee.setText(consultationFee +" consultationFee");
                    textTimings.setText(timing1+"\n"+timing2+"\n"+timing3);
                    webViewMap.loadUrl(webUrl);

                    textMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent =new Intent(getContext(),SelectClinicACtivity.class);
                            intent.putExtra("doctorImage",doctorImage);
                            intent.putExtra("doctorId",doctor_redhealId);
                            intent.putExtra("doctorName",doctorName);
                            intent.putExtra("speclization",specialization);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            getContext().startActivity(intent);
                        }
                    });



                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}


        }
    }



}
