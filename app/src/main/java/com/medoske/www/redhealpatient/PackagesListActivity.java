package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.PackageAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.PackageItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PackagesListActivity extends AppCompatActivity {
    ArrayList<PackageItem> packageItems = new ArrayList<PackageItem>();
    PackageAdapter packageAdapter;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ListView listView;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Packages");

        String doctorId = getIntent().getExtras().getString("doctorId");
        URL = Apis.ALL_PACKAGES_URL+doctorId;

        listView =(ListView)findViewById(R.id.packagelist);

        new AboutMeDataList().execute();
    }

    public class AboutMeDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(PackagesListActivity.this);
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

            Log.e("ABOUT_ME_URL", URL);

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

                JSONArray jsonArray2 = json.optJSONArray("doctor_all_package");

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

                        packageItems.add(new PackageItem(fullName1,doctor_redhealId1,packageName,packageId,actualPrice,discountPrice,packageImage,sittings,period,fromTime,toTime,description1,clinic_redhealId,clinicName,address,distance,doctorImage,speclization));
                    }


                    if(packageItems.size()>0)
                    {
                        packageAdapter = new PackageAdapter(PackagesListActivity.this, R.layout.package_list_item, packageItems);
                        listView.setItemsCanFocus(false);
                        listView.setAdapter(packageAdapter);

                       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                *//*PackageItem packageItem = (PackageItem) listView.getItemAtPosition(position);

                                Intent intent =new Intent(PackagesListActivity.this,DoctorsProfileActivity.class);
                                intent.putExtra("doctorName",packageItem.getDescription());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                Toast.makeText(PackagesListActivity.this, "no data found", Toast.LENGTH_SHORT).show();*//*

                            }
                        });*/

                    }
                    else
                    {
                        Toast.makeText(PackagesListActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                    }

                }
                else {

                    Toast.makeText(PackagesListActivity.this, "no data found", Toast.LENGTH_SHORT).show();

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
