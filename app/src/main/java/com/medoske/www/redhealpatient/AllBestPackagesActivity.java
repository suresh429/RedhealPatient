package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.RecomPackageAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Fragments.ProfileFragment;
import com.medoske.www.redhealpatient.Items.BestPackageItem;
import com.medoske.www.redhealpatient.Items.RecomendPackageItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllBestPackagesActivity extends AppCompatActivity {
    ArrayList<RecomendPackageItem> packageItems = new ArrayList<RecomendPackageItem>();
    RecomPackageAdapter packageAdapter;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ListView listView;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_best_packages);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Packages");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String patientRhid = sp.getString("patientRedhealId", "1");
        String patientName= sp.getString("firstName", "1");
        String patientMobile = sp.getString("mobileno", "1");
        String latitude = sp.getString("presentLatitude", "1");
        String longitude = sp.getString("presentLongitude", "1");

        // String doctorId = getContext().getIntent().getExtras().getString("doctorId");
        URL = Apis.BEST_ALL_PACKAGES_URL+latitude+"/"+longitude;
        Log.e("bestallpackage",""+URL);

        listView =(ListView)findViewById(R.id.listPackages);

        new AllPackagesDataList().execute();
    }

    public class AllPackagesDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(AllBestPackagesActivity.this);
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

            Log.e("BEST_URL", URL);

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

                JSONArray jsonArray2 = json.optJSONArray("best_packages_list");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray2.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        String fullName1 = jsonObject2.optString("fullName").toString();
                        String doctor_redhealId1= jsonObject2.optString("doctor_redhealId").toString();
                        String packageName = jsonObject2.optString("packageName").toString();
                        Log.e("packageName123",""+packageName);
                        String packageId = jsonObject2.optString("packageId").toString();
                        String actualPrice = jsonObject2.optString("actualPrice").toString();
                        String discountPrice = jsonObject2.optString("discountPrice").toString();
                        String packageImage = jsonObject2.optString("packageImage").toString();
                        String sittings = jsonObject2.optString("sittings").toString();
                        String period = jsonObject2.optString("period").toString();
                        String fromTime = jsonObject2.optString("fromTime").toString();
                        String toTime = jsonObject2.optString("toTime").toString();
                        String description1 = jsonObject2.optString("description").toString();
                        String clinic_redhealId = jsonObject2.optString("clinic_redhealId").toString();
                        String clinicName = jsonObject2.optString("clinicName").toString();
                        String address = jsonObject2.optString("address").toString();
                        String distance = jsonObject2.optString("distance").toString();
                        String doctorImage = jsonObject2.optString("imagePath").toString();
                        String speclization = jsonObject2.optString("specialization").toString();

                        packageItems.add(new RecomendPackageItem(fullName1,doctor_redhealId1,packageName,packageId,actualPrice,discountPrice,packageImage,sittings,period,fromTime,toTime,description1,clinic_redhealId,clinicName,address,distance,doctorImage,speclization));
                    }


                    if(packageItems.size()>0)
                    {
                        packageAdapter = new RecomPackageAdapter(AllBestPackagesActivity.this, R.layout.recom_package_item, packageItems);
                        listView.setItemsCanFocus(false);
                        listView.setAdapter(packageAdapter);

                    }
                    else
                    {
                        Toast.makeText(AllBestPackagesActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                    }

                }
                else {

                    Toast.makeText(AllBestPackagesActivity.this, "no data found", Toast.LENGTH_SHORT).show();

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