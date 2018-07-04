package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.SelectClinicAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.SelectClinicItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectClinicACtivity extends AppCompatActivity {
    JSONArray jsonArray;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ArrayList<SelectClinicItem> selectClinicItems = new ArrayList<SelectClinicItem>();
    SelectClinicAdapter selectClinicAdapter;

    ListView listView;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_clinic_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Book Appointment");

        String doctorImge = getIntent().getExtras().getString("doctorImage","defaultKey");
        String doctorName = getIntent().getExtras().getString("doctorName","defaultKey");
        Log.e("doctorname",""+doctorName);
        String specialization = getIntent().getExtras().getString("speclization","defaultKey");
        String doctorId = getIntent().getExtras().getString("doctorId","defaultKey");

        String latitude = getIntent().getExtras().getString("latitude","defaultKey");
        String longitude = getIntent().getExtras().getString("longitude","defaultKey");

        final CircleImageView circleImageView =(CircleImageView)findViewById(R.id.doctorImageCard);
        Picasso.with(getApplicationContext()).load(doctorImge).placeholder(R.drawable.ic_action_placeholder).into(circleImageView);

        final TextView txtDoctorName =(TextView)findViewById(R.id.txtDoctorName);
        txtDoctorName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        txtDoctorName.setText(doctorName);

        final TextView txtSpeclization =(TextView)findViewById(R.id.txtSpeclization);
        txtSpeclization.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        txtSpeclization.setText(specialization);

        URL = Apis.SELECT_CLINIC_URL+doctorId+"/"+latitude+"/"+longitude;

        listView =(ListView)findViewById(R.id.clinicList);

        new ClinicDataList().execute();

    }


    public class ClinicDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(SelectClinicACtivity.this);
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

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String clinic_redhealId = jsonObject.optString("clinic_redhealId").toString();
                    String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                    String clinicName = jsonObject.optString("clinicName").toString();
                    String primaryNumber = jsonObject.optString("primaryNumber").toString();
                    String latitude = jsonObject.optString("latitude").toString();
                    String longitude = jsonObject.optString("longitude").toString();
                    String clinicImagePath = jsonObject.optString("imagePath").toString();
                    String consultationFee = jsonObject.optString("consultationFee").toString();
                    String instantConsultationFee = jsonObject.optString("instantConsultationFee").toString();
                    String distance = jsonObject.optString("distance").toString();

                    String doctorName = jsonObject.optString("doctorName").toString();
                    String doctorSpeclization = jsonObject.optString("specialization").toString();
                    String clinicAddress = jsonObject.optString("areaName").toString();
                    String doctorImage = jsonObject.optString("doctorImage").toString();

                    selectClinicItems.add(new SelectClinicItem(clinic_redhealId,doctor_redhealId,clinicName,primaryNumber,latitude,longitude,clinicImagePath,consultationFee,instantConsultationFee,distance,doctorName,doctorImage,doctorSpeclization,clinicAddress));

                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}

            if(selectClinicItems.size()>0)
            {
                selectClinicAdapter = new SelectClinicAdapter(SelectClinicACtivity.this, R.layout.selectclinic_item, selectClinicItems);
                listView.setItemsCanFocus(false);
                listView.setAdapter(selectClinicAdapter);
                /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        *//*DoctorsListItem doctorsListItem = (DoctorsListItem) listView.getItemAtPosition(position);

                        Intent intent =new Intent(DoctorsListActivity.this,DoctorsProfileActivity.class);
                        intent.putExtra("doctorName",doctorsListItem.getDoctorName());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);*//*

                        Toast.makeText(SelectClinicACtivity.this, "no data found", Toast.LENGTH_SHORT).show();

                    }
                });*/

            }
            else
            {
                Toast.makeText(SelectClinicACtivity.this, "no data found", Toast.LENGTH_SHORT).show();
            }

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
