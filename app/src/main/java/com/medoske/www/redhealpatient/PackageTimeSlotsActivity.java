package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.GridAdapterMorning;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.GridItemMorning;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PackageTimeSlotsActivity extends AppCompatActivity {
    Calendar c;
    SimpleDateFormat df;
    String formattedDate,currentDate;
    TextView txtDate;

    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();

    // grid morning
    ArrayList<GridItemMorning> gridItemMorningArrayList = new ArrayList<GridItemMorning>();
    GridAdapterMorning gridAdapterMorning;

    GridView gridViewMorning;

    String URL_MORNING ;
    String patientName,patientEmail,patientMobile,doctorImge,clinicName,speclization,doctorName,clinicId,doctorId,packageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_time_slots);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Book Time Slot");


        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getApplicationContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        patientName = sp.getString("firstName", "1");
        patientEmail = sp.getString("email", "1");
        patientMobile = sp.getString("mobileno", "1");


        doctorImge = getIntent().getExtras().getString("doctorImage","defaultKey");
        clinicName = getIntent().getExtras().getString("clinicName","defaultKey");
        speclization = getIntent().getExtras().getString("speclization","defaultKey");
        doctorName = getIntent().getExtras().getString("doctorName","defaultKey");
        clinicId = getIntent().getExtras().getString("clinicId","defaultKey");
        doctorId = getIntent().getExtras().getString("doctorId","defaultKey");
        packageId = getIntent().getExtras().getString("packageId","defaultKey");


        final CircleImageView circleImageView =(CircleImageView)findViewById(R.id.profile_image);
//        Picasso.with(getApplicationContext()).load(doctorImge).placeholder(R.drawable.ic_action_placeholder).into(circleImageView);

        final TextView txtDoctorName =(TextView)findViewById(R.id.textPatientNae1);
        txtDoctorName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        txtDoctorName.setText(doctorName);

        final TextView txtSpeclization =(TextView)findViewById(R.id.textRhid1);
        txtSpeclization.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        txtSpeclization.setText(speclization + " | " +clinicName);

        // date on textview
        c = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());

        //current date
        currentDate=df.format(c.getTime());
        Log.e("currentdate",""+currentDate);


        // morning time slot url
        URL_MORNING = Apis.TIMESLOT_PACKAGE_URL+doctorId+"/"+clinicId+"/"+formattedDate;
        Log.e("morning",""+URL_MORNING);


        txtDate =(TextView)findViewById(R.id.textDateSlot);
        txtDate.setText(formattedDate);
        Log.e("txtDate",""+txtDate);

        final ImageButton pButton =(ImageButton)findViewById(R.id.PreviousButton);
        pButton.setVisibility(View.INVISIBLE);

        // forward button for date increase
        final ImageButton fButton =(ImageButton)findViewById(R.id.ForwardButton);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.add(Calendar.DATE, 1);

                formattedDate = df.format(c.getTime());



                Log.v("FORWARD DATE : ", formattedDate);
                txtDate.setText(formattedDate);
               // Toast.makeText(PackageTimeSlotsActivity.this, "fbutton"+formattedDate, Toast.LENGTH_SHORT).show();

                // morning time slot url
                URL_MORNING =Apis.TIMESLOT_PACKAGE_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                Log.e("morning>",""+URL_MORNING);


                new MorningList().execute(URL_MORNING);


                pButton.setVisibility(View.VISIBLE);
                pButton.setEnabled(true);
            }
        });

        // backward button for date decrease
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (formattedDate.equals(currentDate)){
                    //pButton.setVisibility(View.INVISIBLE);
                    pButton.setEnabled(false);

                }else {

                    c.add(Calendar.DATE, -1);
                    formattedDate = df.format(c.getTime());
                    Log.d("PREVIOUS DATE : ", formattedDate);
                    txtDate.setText(formattedDate);

                    // morning time slot url
                    URL_MORNING =Apis.TIMESLOT_PACKAGE_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                    Log.e("<morning",""+URL_MORNING);




                    new MorningList().execute(URL_MORNING);


                }

            }
        });


        gridViewMorning=(GridView)findViewById(R.id.gridMorning);




        // normal time slots list
        new MorningList().execute();




    }

    // morning Time Slot data
    public class MorningList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(PackageTimeSlotsActivity.this);
            //Set the progress dialog to display a Packag progress bar
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
            JSONObject json = jsonParser.makeHttpRequest(URL_MORNING, "GET", param);

            Log.e("URL_MORNING", URL_MORNING);

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
            try {
                gridItemMorningArrayList.clear();
                resultObject = new JSONObject(String.valueOf(json));
                System.out.println("Testing the water " + resultObject.toString());
                jsonArray = resultObject.optJSONArray("package_instant_time_slots");

                if (jsonArray != null&& jsonArray.length()>0) {     // check jsonArray is null?
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonChildNode = null;
                        try {
                            jsonChildNode = jsonArray.getJSONObject(i);
                            String slotId1 = jsonChildNode.optString("slotId").toString();
                            String bookingTime1 = jsonChildNode.optString("bookingTime").toString();
                            String bookingSession1 = jsonChildNode.optString("bookingSession").toString();
                            String bookingDate1 = jsonChildNode.optString("bookingDate").toString();
                            String appointmentType1 = jsonChildNode.optString("appointmentType").toString();
                            String status1 = jsonChildNode.optString("status").toString();

                            gridItemMorningArrayList.add(new GridItemMorning(slotId1,bookingTime1,bookingSession1,bookingDate1,appointmentType1,status1));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    if (gridItemMorningArrayList.size() > 0) {


                        gridAdapterMorning = new GridAdapterMorning(PackageTimeSlotsActivity.this, R.layout.grid_item, gridItemMorningArrayList);
                        gridViewMorning.setAdapter(gridAdapterMorning);
                        gridViewMorning.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                GridItemMorning gridItem = (GridItemMorning) parent.getItemAtPosition(position);


                                if (gridItem.getStatus().equals("unavailable")){

                                    Toast.makeText(PackageTimeSlotsActivity.this, "Slots are unavailable", Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    Intent intent =new Intent(PackageTimeSlotsActivity.this,PackagEnterDetailsActivity.class);
                                    intent.putExtra("bookingDate",gridItem.getBookingDate());
                                    intent.putExtra("bookingTime",gridItem.getBookingTime());
                                    intent.putExtra("bookingSession",gridItem.getBookingSession());
                                    intent.putExtra("patientName",patientName);
                                    intent.putExtra("patientEmail",patientEmail);
                                    intent.putExtra("patientMobile",patientMobile);
                                    intent.putExtra("doctorImage",doctorImge);
                                    intent.putExtra("clinicName",clinicName);
                                    intent.putExtra("doctorName",doctorName);
                                    intent.putExtra("speclization",speclization);
                                    intent.putExtra("clinicId",clinicId);
                                    intent.putExtra("doctorId",doctorId);
                                    intent.putExtra("packageId",packageId);
                                    intent.putExtra("slotId",gridItem.getSlotId());
                                    Log.e("slotiddd",""+gridItem.getSlotId());
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }


                            }
                        });

                    } else {
                        TextView txtMorning =(TextView)findViewById(R.id.textViewMorning);
                        txtMorning.setVisibility(View.GONE);

                        // Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
                        // searchSpeclitiesLayout.setVisibility(View.GONE);
                    }
                    gridAdapterMorning.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
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
