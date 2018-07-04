package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.MyPackagesAdpter;
import com.medoske.www.redhealpatient.Adapters.PackageAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.MyPackaeItem;
import com.medoske.www.redhealpatient.Items.PackageItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyPackagesActivity extends AppCompatActivity {

    ArrayList<MyPackaeItem> myPackaeItems = new ArrayList<MyPackaeItem>();
    MyPackagesAdpter packageAdapter;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ListView listView;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_packages);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Orders");

// To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        URL = Apis.MY_PACKEGES_URL+redHealId;

        listView =(ListView)findViewById(R.id.packageList);

        new MyOrdersList().execute();
    }


    public class MyOrdersList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(MyPackagesActivity.this);
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

            Log.e("MY_PACKAGE_URL", URL);

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

                JSONArray jsonArray2 = json.optJSONArray("package_list");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray2.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        String packageName = jsonObject2.optString("packageName").toString();
                        String actualPrice= jsonObject2.optString("actualPrice").toString();
                        String discountPrice = jsonObject2.optString("discountPrice").toString();
                        String fromTime = jsonObject2.optString("fromTime").toString();
                        String toTime = jsonObject2.optString("toTime").toString();
                        String packageImage = jsonObject2.optString("packageImage").toString();
                        String period = jsonObject2.optString("period").toString();
                        String description = jsonObject2.optString("description").toString();
                        String specialization = jsonObject2.optString("specialization").toString();
                        String packageId = jsonObject2.optString("packageId").toString();
                        String packageleadId = jsonObject2.optString("packageleadId").toString();
                        String doctor_redhealId = jsonObject2.optString("doctor_redhealId").toString();
                        String doctorName = jsonObject2.optString("doctorName").toString();
                        String doctorImage= jsonObject2.optString("doctorImage").toString();
                        String leadDate = jsonObject2.optString("leadDate").toString();
                        String leadTime = jsonObject2.optString("leadTime").toString();
                        String packageRefNo = jsonObject2.optString("packageRefNo").toString();
                        String paymentMode = jsonObject2.optString("paymentMode").toString();
                        String status = jsonObject2.optString("status").toString();
                        String clinicName = jsonObject2.optString("clinicName").toString();
                        String clinic_redhealId = jsonObject2.optString("clinic_redhealId").toString();
                        String address = jsonObject2.optString("address").toString();
                        String clinicImage = jsonObject2.optString("clinicImage").toString();
                        String total_sittings = jsonObject2.optString("total_sittings").toString();
                        String completed_sittings = jsonObject2.optString("completed_sittings").toString();
                        String remaing_sittings = jsonObject2.optString("remaing_sittings").toString();
                        String experience = jsonObject2.optString("experience").toString();
                        String patientName = jsonObject2.optString("patientName").toString();
                        String patientId = jsonObject2.optString("patient_redhealId").toString();
                        String patientImage = jsonObject2.optString("patientImage").toString();

                        myPackaeItems.add(new MyPackaeItem(packageName,actualPrice,discountPrice,fromTime,toTime,packageImage,period,description,specialization,packageId,
                                packageleadId,doctor_redhealId,doctorName,doctorImage,leadDate,leadTime,packageRefNo,paymentMode,status,clinicName,clinic_redhealId,address,
                                clinicImage,total_sittings,completed_sittings,remaing_sittings,experience,patientName,patientId,patientImage));
                    }


                    if(myPackaeItems.size()>0)
                    {
                        packageAdapter = new MyPackagesAdpter(MyPackagesActivity.this, R.layout.mypackages_list_item, myPackaeItems);
                        listView.setItemsCanFocus(false);
                        listView.setAdapter(packageAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                MyPackaeItem myPackaeItem = (MyPackaeItem) listView.getItemAtPosition(position);

                                Intent intent =new Intent(MyPackagesActivity.this,MyPackageDetailsActivity.class);
                                intent.putExtra("packageRefNo",myPackaeItem.getPackageRefNo());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                Toast.makeText(MyPackagesActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(MyPackagesActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                    }

                }
                else {

                    Toast.makeText(MyPackagesActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                }



                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}



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
