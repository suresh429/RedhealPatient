package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.medoske.www.redhealpatient.Adapters.InfiniteCyclerAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Fragments.NotificationFragment;
import com.medoske.www.redhealpatient.Items.NotificationDoctorItem;
import com.medoske.www.redhealpatient.Items.SelectClinicItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DoctorNotifyDetailsActivity extends AppCompatActivity {
List<NotificationDoctorItem> integerList =new ArrayList<>();
    HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    String redHealId;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_notify_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Doctors Details");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        String RefNo = getIntent().getExtras().getString("Refno","defaultKey");

        URL = Apis.NOTIFICATION_DOCTORS_URL+redHealId+"/"+RefNo;
        Log.e("url",""+URL);


         horizontalInfiniteCycleViewPager =(HorizontalInfiniteCycleViewPager)findViewById(R.id.cyclerView);

        initData();
    }
   /* private void initData(){
        integerList.add(R.drawable.diagnostic_image);
        integerList.add(R.drawable.doctor_image);
        integerList.add(R.drawable.feeds_image);
        integerList.add(R.drawable.locations_image);

    }*/

    private void initData() {

        //Showing a progress dialog
        final ProgressDialog pDialog = new ProgressDialog(DoctorNotifyDetailsActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("response",""+response);

                //Dismissing progress dialog
                pDialog.hide();
                try {
                    JSONArray jsonArray = response.getJSONArray("doctors_list");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String clinic_redhealId = jsonObject.optString("clinic_redhealId").toString();
                        String clinicName = jsonObject.optString("clinicName").toString();
                        String clinicPrimaryMobile = jsonObject.optString("clinicPrimaryMobile").toString();
                        String clinicAddress = jsonObject.optString("clinicAddress").toString();
                        String consultationFee = jsonObject.optString("consultationFee").toString();
                        String premiumConsultationFee = jsonObject.optString("premiumConsultationFee").toString();
                        String fromtime1 = jsonObject.optString("fromtime1").toString();
                        String totime1 = jsonObject.optString("totime1").toString();
                        String fromtime2 = jsonObject.optString("fromtime2").toString();
                        String totime2 = jsonObject.optString("totime2").toString();
                        String fromtime3 = jsonObject.optString("fromtime3").toString();
                        String totime3 = jsonObject.optString("totime3").toString();
                        String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                        String doctorName = jsonObject.optString("doctorName").toString();
                        String doctorPrimaryNumber = jsonObject.optString("doctorPrimaryNumber").toString();
                        String imagePath = jsonObject.optString("imagePath").toString();
                        String aboutDoctor = jsonObject.optString("aboutDoctor").toString();
                        String specialization = jsonObject.optString("specialization").toString();

                        integerList.add(new NotificationDoctorItem(clinic_redhealId,clinicName,clinicPrimaryMobile,clinicAddress,consultationFee,premiumConsultationFee,fromtime1,totime1,fromtime2,totime2,fromtime3,totime3,doctor_redhealId,doctorName,doctorPrimaryNumber,imagePath,aboutDoctor,specialization));

                    }

                    InfiniteCyclerAdapter adapter = new InfiniteCyclerAdapter(integerList,getBaseContext());
                    horizontalInfiniteCycleViewPager.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();

                pDialog.hide();
            }
        });

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(jsonObjReq);

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(DoctorNotifyDetailsActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
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
