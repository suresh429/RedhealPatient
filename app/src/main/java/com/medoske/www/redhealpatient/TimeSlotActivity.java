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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.medoske.www.redhealpatient.Adapters.ExpandableHeightGridView;
import com.medoske.www.redhealpatient.Adapters.GridAdapterAfterNoon;
import com.medoske.www.redhealpatient.Adapters.GridAdapterAfterNoonInstant;
import com.medoske.www.redhealpatient.Adapters.GridAdapterEvening;
import com.medoske.www.redhealpatient.Adapters.GridAdapterEveningInstant;
import com.medoske.www.redhealpatient.Adapters.GridAdapterMorning;
import com.medoske.www.redhealpatient.Adapters.GridAdapterMorningInstant;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.GridItemAfterNoon;
import com.medoske.www.redhealpatient.Items.GridItemAfterNoonInstant;
import com.medoske.www.redhealpatient.Items.GridItemEvening;
import com.medoske.www.redhealpatient.Items.GridItemEveningInstant;
import com.medoske.www.redhealpatient.Items.GridItemMorning;
import com.medoske.www.redhealpatient.Items.GridItemMorningInstant;
import com.medoske.www.redhealpatient.Utilities.JSONParser;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

public class TimeSlotActivity extends AppCompatActivity {
    String paymentmodeAfternoon,paymentmodeMorning, slot;
    int k = 0;
    int l = 0;
    int m = 0;
    Calendar c;
    SimpleDateFormat df;
    String formattedDate,currentDate;
    TextView txtDate,txtMorning,txtAfterNoon,txtEvening,txtError;

    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    //JSONArray jsonArray;

    // grid morning
    ArrayList<GridItemMorning> gridItemMorningArrayList = new ArrayList<GridItemMorning>();
    GridAdapterMorning gridAdapterMorning;


    // grid afternoon
    ArrayList<GridItemAfterNoon> gridItemAfterNoonArrayList = new ArrayList<GridItemAfterNoon>();
    GridAdapterAfterNoon gridAdapterAfternoon;

    //  grid evening
    ArrayList<GridItemEvening> gridItemEveningArrayList = new ArrayList<GridItemEvening>();
    GridAdapterEvening gridAdapterEvening;



    // grid morning Instant
    ArrayList<GridItemMorningInstant> gridItemMorningArrayListInstant = new ArrayList<GridItemMorningInstant>();
    GridAdapterMorningInstant gridAdapterMorningInstant;


    // grid afternoon Instant
    ArrayList<GridItemAfterNoonInstant> gridItemAfterNoonArrayListInstant = new ArrayList<GridItemAfterNoonInstant>();
    GridAdapterAfterNoonInstant gridAdapterAfternoonInstant;

    //  grid evening Instant
    ArrayList<GridItemEveningInstant> gridItemEveningArrayListInstant = new ArrayList<GridItemEveningInstant>();
    GridAdapterEveningInstant gridAdapterEveningInstant;



    ExpandableHeightGridView gridViewMorning, gridViewAfternoon,gridViewEvening,gridViewMorningInstant,gridViewAfternoonInstant,gridViewEveningInstant;

    String URL_MORNING ,URL_AFTERNOON,URL_EVENING,URL_MORNING_INSTANT ,URL_AFTERNOON_INSTANT,URL_EVENING_INSTANT,DOCTOR_PAYMENT_URL;

    String  ActiveStatusM, ActiveStatusA,ActiveStatusE;
    RadioButton rbNormal,rbInstant;
    String appointmentTypeStatus;

    String patientName,patientEmail,patientMobile,doctorImge,clinicName,speclization,doctorName,clinicId,doctorId,redHealId;

    LinearLayout normalLayout,instatnLayout;
    LinearLayout morningLayout,afterNoonLayout,eveningLayout,morningInstantlayout,afternoonInstantLayout,eveningInstantLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_appointments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select a time slot");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getApplicationContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
         patientName = sp.getString("firstName", "1");
         patientEmail = sp.getString("email", "1");
         patientMobile = sp.getString("mobileno", "1");
         redHealId = sp.getString("patientRedhealId", "1");


         doctorImge = getIntent().getExtras().getString("doctorImage","defaultKey");
         clinicName = getIntent().getExtras().getString("clinicName","defaultKey");
         speclization = getIntent().getExtras().getString("speclization","defaultKey");
         doctorName = getIntent().getExtras().getString("doctorName","defaultKey");
         clinicId = getIntent().getExtras().getString("clinicId","defaultKey");
         doctorId = getIntent().getExtras().getString("doctorId","defaultKey");



        final CircleImageView circleImageView =(CircleImageView)findViewById(R.id.profile_image);
        Picasso.with(getApplicationContext()).load(doctorImge).placeholder(R.drawable.ic_action_placeholder).into(circleImageView);

        final TextView txtDoctorName =(TextView)findViewById(R.id.textPatientNae1);
        txtDoctorName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        txtDoctorName.setText(doctorName);

        final TextView txtSpeclization =(TextView)findViewById(R.id.textRhid1);
        txtSpeclization.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        txtSpeclization.setText(speclization + " | " +clinicName);

        normalLayout=(LinearLayout)findViewById(R.id.normalLiniearLayout);
        instatnLayout=(LinearLayout)findViewById(R.id.instantLayout);

        morningLayout=(LinearLayout)findViewById(R.id.morningLayout);
        afterNoonLayout=(LinearLayout)findViewById(R.id.afternoonLayout);
        eveningLayout=(LinearLayout)findViewById(R.id.eveningLayout);
        morningInstantlayout=(LinearLayout)findViewById(R.id.morningInstantLayout);
        afternoonInstantLayout=(LinearLayout)findViewById(R.id.afternoonInstantLayout);
        eveningInstantLayout=(LinearLayout)findViewById(R.id.eveningInstantLayout);
        txtError=(TextView)findViewById(R.id.textViewError);
        txtError.setVisibility(View.GONE);





        // date on textview
        c = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());

        //current date
        currentDate=df.format(c.getTime());
        Log.e("currentdate",""+currentDate);


        // morning time slot url
        URL_MORNING =Apis.TIMESLOT_MORNING_URL+doctorId+"/"+clinicId+"/"+formattedDate;
        Log.e("morning",""+URL_MORNING);

        // afternoon time slot url
        URL_AFTERNOON =Apis.TIMESLOT_AFTERNOON_URL+doctorId+"/"+clinicId+"/"+formattedDate;
        Log.e("afternoon",""+URL_AFTERNOON);

        // afternoon time slot url
        URL_EVENING =Apis.TIMESLOT_EVENG_URL+doctorId+"/"+clinicId+"/"+formattedDate;
        Log.e("evening",""+URL_EVENING);

        // Instant morning time slot url
        URL_MORNING_INSTANT =Apis.TIMESLOT_MORNING_INSTANT_URL+doctorId+"/"+clinicId+"/"+formattedDate;
        Log.e("morningInstant",""+URL_MORNING_INSTANT);

        // Instant afternoon time slot url
        URL_AFTERNOON_INSTANT =Apis.TIMESLOT_AFTERNOON_INSTANT_URL+doctorId+"/"+clinicId+"/"+formattedDate;
        Log.e("afternoonInstant",""+URL_AFTERNOON_INSTANT);

        // Instant afternoon time slot url
        URL_EVENING_INSTANT =Apis.TIMESLOT_EVENG_INSTANT_URL+doctorId+"/"+clinicId+"/"+formattedDate;
        Log.e("eveningInstant",""+URL_EVENING_INSTANT);



        txtDate =(TextView)findViewById(R.id.textDateSlot);
        txtDate.setText(formattedDate);
        Log.e("txtDate",""+txtDate);


        gridViewMorning = (ExpandableHeightGridView) findViewById(R.id.gridMorning);
        // gridViewMorning=(GridView)findViewById(R.id.gridMorning);
        gridViewAfternoon=(ExpandableHeightGridView)findViewById(R.id.gridAfternoon);
        gridViewEvening=(ExpandableHeightGridView)findViewById(R.id.gridEvening);

        gridViewMorningInstant=(ExpandableHeightGridView)findViewById(R.id.gridMorningInstant);
        gridViewAfternoonInstant=(ExpandableHeightGridView)findViewById(R.id.gridAfternoonInstant);
        gridViewEveningInstant=(ExpandableHeightGridView)findViewById(R.id.gridEveningInstant);



        // normal time slots list
        new MorningList().execute();
        new AfterNoonList().execute();
        new EveningList().execute();


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
                Toast.makeText(TimeSlotActivity.this, "fbutton"+formattedDate, Toast.LENGTH_SHORT).show();

                // morning time slot url
                URL_MORNING =Apis.TIMESLOT_MORNING_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                Log.e("morning>",""+URL_MORNING);

                // afternoon time slot url
                URL_AFTERNOON =Apis.TIMESLOT_AFTERNOON_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                Log.e("afternoon>",""+URL_AFTERNOON);

                // evening time slot url
                URL_EVENING =Apis.TIMESLOT_EVENG_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                Log.e("evening>",""+URL_EVENING);


                // Instant morning time slot url
                URL_MORNING_INSTANT =Apis.TIMESLOT_MORNING_INSTANT_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                Log.e("morningInstant",""+URL_MORNING_INSTANT);

                // Instant afternoon time slot url
                URL_AFTERNOON_INSTANT =Apis.TIMESLOT_AFTERNOON_INSTANT_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                Log.e("afternoonInstant",""+URL_AFTERNOON_INSTANT);

                // Instant afternoon time slot url
                URL_EVENING_INSTANT =Apis.TIMESLOT_EVENG_INSTANT_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                Log.e("eveningInstant",""+URL_EVENING_INSTANT);


                new MorningList().execute(URL_MORNING);
                new AfterNoonList().execute(URL_AFTERNOON);
                new EveningList().execute(URL_EVENING);
                ErrorText();

                new MorningInstantList().execute(URL_MORNING_INSTANT);
                new AfterNoonInstantList().execute(URL_AFTERNOON_INSTANT);
                new EveningInstantList().execute(URL_EVENING_INSTANT);


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
                    URL_MORNING =Apis.TIMESLOT_MORNING_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                    Log.e("<morning",""+URL_MORNING);

                    // afternoon time slot url
                    URL_AFTERNOON =Apis.TIMESLOT_AFTERNOON_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                    Log.e("<afternoon",""+URL_AFTERNOON);

                    // evening time slot url
                    URL_EVENING =Apis.TIMESLOT_EVENG_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                    Log.e("<evening",""+URL_EVENING);


                    // Instant morning time slot url
                    URL_MORNING_INSTANT =Apis.TIMESLOT_MORNING_INSTANT_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                    Log.e("morningInstant",""+URL_MORNING_INSTANT);

                    // Instant afternoon time slot url
                    URL_AFTERNOON_INSTANT =Apis.TIMESLOT_AFTERNOON_INSTANT_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                    Log.e("afternoonInstant",""+URL_AFTERNOON_INSTANT);

                    // Instant afternoon time slot url
                    URL_EVENING_INSTANT =Apis.TIMESLOT_EVENG_INSTANT_URL+doctorId+"/"+clinicId+"/"+formattedDate;
                    Log.e("eveningInstant",""+URL_EVENING_INSTANT);


                    new MorningList().execute(URL_MORNING);
                    new AfterNoonList().execute(URL_AFTERNOON);
                    new EveningList().execute(URL_EVENING);
                    ErrorText();

                    new MorningInstantList().execute(URL_MORNING_INSTANT);
                    new AfterNoonInstantList().execute(URL_AFTERNOON_INSTANT);
                    new EveningInstantList().execute(URL_EVENING_INSTANT);



                }

            }
        });



        rbInstant=(RadioButton)findViewById(R.id.radioButton_instant) ;
        rbNormal=(RadioButton)findViewById(R.id.radioButton_normal) ;
        appointmentTypeStatus ="normal";
        rbNormal.setChecked(true);

        rbNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                     appointmentTypeStatus ="normal";
                     Log.e("normal",""+appointmentTypeStatus);

                    Toast.makeText(TimeSlotActivity.this, ""+appointmentTypeStatus, Toast.LENGTH_SHORT).show();

                    // normal time slots list
                    new MorningList().execute();
                    new AfterNoonList().execute();
                    new EveningList().execute();

                    normalLayout.setVisibility(View.VISIBLE);
                    instatnLayout.setVisibility(View.GONE);

                    ErrorText();

                }
            }
        });

        rbInstant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    appointmentTypeStatus ="instant";
                    Toast.makeText(TimeSlotActivity.this, ""+appointmentTypeStatus, Toast.LENGTH_SHORT).show();

                    // instant time slots list
                    new MorningInstantList().execute();
                    new AfterNoonInstantList().execute();
                    new EveningInstantList().execute();



                    normalLayout.setVisibility(View.GONE);
                    instatnLayout.setVisibility(View.VISIBLE);

                    ErrorText();
                }
            }
        });




        ErrorText();


    }



    // morning Time Slot data
    public class MorningList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(TimeSlotActivity.this);
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
                jsonArray = resultObject.optJSONArray("clinics_details_morning");

                 ActiveStatusM =resultObject.getString("status");
                Log.e("ActiveStatus :",""+ActiveStatusM);

                String message =resultObject.getString("message");
                Log.e("message :",""+message);

                if (ActiveStatusM.equals("active")){

                    morningLayout.setVisibility(View.VISIBLE);
                   // txtError.setVisibility(View.GONE);
                }else if (ActiveStatusM.equals("inactive")){
                    morningLayout.setVisibility(View.GONE);
                    //txtError.setVisibility(View.VISIBLE);
                }



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


                        gridAdapterMorning = new GridAdapterMorning(TimeSlotActivity.this, R.layout.grid_item, gridItemMorningArrayList);
                        gridViewMorning.setAdapter(gridAdapterMorning);
                        gridViewMorning.setExpanded(true);
                       // ListUtils.setDynamicHeight(gridViewMorning);
                        gridViewMorning.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                final GridItemMorning gridItem = (GridItemMorning) parent.getItemAtPosition(position);


                                if (gridItem.getStatus().equals("unavailable")){

                                    Toast.makeText(TimeSlotActivity.this, "Slots are unavailable", Toast.LENGTH_SHORT).show();
                                }
                                else {


                                    DOCTOR_PAYMENT_URL =Apis.DOCTOR_PAYMENT_MODE_URL+redHealId+"/"+doctorId+"/"+gridItem.getSlotId()+"/"+"normal";
                                    Log.e("doctorurlfjhfj",""+DOCTOR_PAYMENT_URL);
                                    //Showing a progress dialog
                                    final ProgressDialog pDialog = new ProgressDialog(TimeSlotActivity.this);
                                    pDialog.setMessage("Loading...");
                                    pDialog.show();

                                    final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                            DOCTOR_PAYMENT_URL, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.e("response",""+response);



                                            try {
                                                String status = response.getString("status");
                                                slot = response.getString("slot");
                                                Log.e("Slot",""+slot);

                                                if (slot.equals("available")){

                                                    String paymentmode = response.getString("paymentmode");
                                                    Log.e("paymentmode",""+paymentmode);

                                                    Intent intent = new Intent(TimeSlotActivity.this, EnterContactDetailsActivity.class);
                                                    intent.putExtra("bookingDate", gridItem.getBookingDate());
                                                    intent.putExtra("bookingTime", gridItem.getBookingTime());
                                                    intent.putExtra("bookingSession", gridItem.getBookingSession());
                                                    intent.putExtra("patientName", patientName);
                                                    intent.putExtra("patientEmail", patientEmail);
                                                    intent.putExtra("patientMobile", patientMobile);
                                                    intent.putExtra("doctorImage", doctorImge);
                                                    intent.putExtra("clinicName", clinicName);
                                                    intent.putExtra("doctorName", doctorName);
                                                    intent.putExtra("speclization", speclization);
                                                    intent.putExtra("clinicId", clinicId);
                                                    intent.putExtra("doctorId", doctorId);
                                                    intent.putExtra("appointmentType",appointmentTypeStatus);
                                                    intent.putExtra("slotId", gridItem.getSlotId());
                                                    Log.e("slotiddd", "" + gridItem.getSlotId());
                                                    intent.putExtra("paymentmode",paymentmode);
                                                    Log.e("paymentmode1",""+paymentmode);
                                                    //intent.putExtra("appointmentTypeStatus",appointmentTypeStatus);

                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);


                                                }else {

                                                    new MorningList().execute(URL_MORNING);
                                                    Toast.makeText(TimeSlotActivity.this, "slot not available", Toast.LENGTH_SHORT).show();

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            //Dismissing progress dialog
                                            pDialog.hide();

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
                                    RequestQueue requestQueue = Volley.newRequestQueue(TimeSlotActivity.this);

                                    //Adding request to the queue
                                    requestQueue.add(jsonObjReq);




                                }


                            }
                        });

                    }


                    gridAdapterMorning.notifyDataSetChanged();
                   // Toast.makeText(TimeSlotActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // afternoon Time Slot data

    public class AfterNoonList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(TimeSlotActivity.this);
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
            JSONObject json = jsonParser.makeHttpRequest(URL_AFTERNOON, "GET", param);

            Log.e("URL_AfterNOON", URL_AFTERNOON);

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
                gridItemAfterNoonArrayList.clear();
                resultObject = new JSONObject(String.valueOf(json));
                System.out.println("Testing the water " + resultObject.toString());
                jsonArray = resultObject.optJSONArray("clinics_details_afternoon");

                 ActiveStatusA =resultObject.getString("status");
                Log.e("ActiveStatus :",""+ActiveStatusA);

                if (ActiveStatusA.equals("active")){
                    afterNoonLayout.setVisibility(View.VISIBLE);
                    //txtError.setVisibility(View.GONE);
                }else if (ActiveStatusA.equals("inactive")){
                    afterNoonLayout.setVisibility(View.GONE);
                   // txtError.setVisibility(View.VISIBLE);
                }


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
                            gridItemAfterNoonArrayList.add(new GridItemAfterNoon(slotId1,bookingTime1,bookingSession1,bookingDate1,appointmentType1,status1));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    if (gridItemAfterNoonArrayList.size() > 0) {


                        gridAdapterAfternoon = new GridAdapterAfterNoon(TimeSlotActivity.this, R.layout.grid_item, gridItemAfterNoonArrayList);
                        gridViewAfternoon.setAdapter(gridAdapterAfternoon);
                        gridViewAfternoon.setExpanded(true);
                       // ListUtils.setDynamicHeight(gridViewAfternoon);
                        gridViewAfternoon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                final GridItemAfterNoon gridItemAfterNoon = (GridItemAfterNoon) parent.getItemAtPosition(position);


                                if (gridItemAfterNoon.getStatus().equals("unavailable")){

                                    Toast.makeText(TimeSlotActivity.this, "Slots are unavailable", Toast.LENGTH_SHORT).show();
                                }else {


                                    DOCTOR_PAYMENT_URL =Apis.DOCTOR_PAYMENT_MODE_URL+redHealId+"/"+doctorId+"/"+gridItemAfterNoon.getSlotId()+"/"+"normal";
                                    Log.e("doctorurlfjhfj",""+DOCTOR_PAYMENT_URL);
                                    //Showing a progress dialog
                                    final ProgressDialog pDialog = new ProgressDialog(TimeSlotActivity.this);
                                    pDialog.setMessage("Loading...");
                                    pDialog.show();

                                    final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                            DOCTOR_PAYMENT_URL, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.e("response",""+response);



                                            try {
                                                String status = response.getString("status");
                                                slot = response.getString("slot");
                                                 Log.e("Slot",""+slot);

                                                if (slot.equals("available")){

                                                    String paymentmode = response.getString("paymentmode");
                                                    Log.e("paymentmode",""+paymentmode);

                                                    Intent intent = new Intent(TimeSlotActivity.this, EnterContactDetailsActivity.class);
                                                    intent.putExtra("bookingDate", gridItemAfterNoon.getBookingDate());
                                                    intent.putExtra("bookingTime", gridItemAfterNoon.getBookingTime());
                                                    intent.putExtra("bookingSession", gridItemAfterNoon.getBookingSession());
                                                    intent.putExtra("patientName", patientName);
                                                    intent.putExtra("patientEmail", patientEmail);
                                                    intent.putExtra("patientMobile", patientMobile);
                                                    intent.putExtra("doctorImage", doctorImge);
                                                    intent.putExtra("clinicName", clinicName);
                                                    intent.putExtra("doctorName", doctorName);
                                                    intent.putExtra("speclization", speclization);
                                                    intent.putExtra("clinicId", clinicId);
                                                    intent.putExtra("doctorId", doctorId);
                                                    intent.putExtra("appointmentType",appointmentTypeStatus);
                                                    intent.putExtra("slotId", gridItemAfterNoon.getSlotId());
                                                    Log.e("slotiddd", "" + gridItemAfterNoon.getSlotId());
                                                    intent.putExtra("paymentmode",paymentmode);
                                                    Log.e("paymentmode1",""+paymentmode);
                                                    // intent.putExtra("appointmentTypeStatus",appointmentTypeStatus);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }else {

                                                    new AfterNoonList().execute(URL_AFTERNOON);
                                                    Toast.makeText(TimeSlotActivity.this, "slot not available", Toast.LENGTH_SHORT).show();

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            //Dismissing progress dialog
                                            pDialog.hide();

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
                                    RequestQueue requestQueue = Volley.newRequestQueue(TimeSlotActivity.this);

                                    //Adding request to the queue
                                    requestQueue.add(jsonObjReq);





                                }
                            }
                        });
                       // gridAdapterAfternoon.notifyDataSetChanged();


                    } else {
                        // Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
                        // searchSpeclitiesLayout.setVisibility(View.GONE);
                    }
                    gridAdapterAfternoon.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    // evening Time Slot data

    public class EveningList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(TimeSlotActivity.this);
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
            JSONObject json = jsonParser.makeHttpRequest(URL_EVENING, "GET", param);

            Log.e("URL_EVENING", URL_EVENING);

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
                gridItemEveningArrayList.clear();
                resultObject = new JSONObject(String.valueOf(json));
                System.out.println("Testing the water " + resultObject.toString());
                jsonArray = resultObject.optJSONArray("clinics_details_evening");

                 ActiveStatusE =resultObject.getString("status");
                Log.e("ActiveStatus :",""+ActiveStatusE);


                if (ActiveStatusE.equals("active")){

                    eveningLayout.setVisibility(View.VISIBLE);
                   // txtError.setVisibility(View.GONE);
                }else if (ActiveStatusE.equals("inactive")){
                    eveningLayout.setVisibility(View.GONE);
                    //txtError.setVisibility(View.VISIBLE);
                }

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

                            gridItemEveningArrayList.add(new GridItemEvening(slotId1,bookingTime1,bookingSession1,bookingDate1,appointmentType1,status1));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    if (gridItemEveningArrayList.size() > 0) {


                        gridAdapterEvening = new GridAdapterEvening(TimeSlotActivity.this, R.layout.grid_item, gridItemEveningArrayList);
                        gridViewEvening.setAdapter(gridAdapterEvening);
                        gridViewEvening.setExpanded(true);
                       // ListUtils.setDynamicHeight(gridViewEvening);
                        gridAdapterEvening.notifyDataSetChanged();
                        gridViewEvening.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                final GridItemEvening gridItemEvening = (GridItemEvening) parent.getItemAtPosition(position);


                                if (gridItemEvening.getStatus().equals("unavailable")){

                                    Toast.makeText(TimeSlotActivity.this, "Slots are unavailable", Toast.LENGTH_SHORT).show();
                                }else {




                                        DOCTOR_PAYMENT_URL =Apis.DOCTOR_PAYMENT_MODE_URL+redHealId+"/"+doctorId+"/"+gridItemEvening.getSlotId()+"/"+"normal";
                                        Log.e("doctorurlfjhfj",""+DOCTOR_PAYMENT_URL);
                                        //Showing a progress dialog
                                        final ProgressDialog pDialog = new ProgressDialog(TimeSlotActivity.this);
                                        pDialog.setMessage("Loading...");
                                        pDialog.show();

                                        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                                DOCTOR_PAYMENT_URL, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.e("response",""+response);



                                                try {
                                                    String status = response.getString("status");
                                                    slot = response.getString("slot");
                                                    Log.e("Slot",""+slot);

                                                    if (slot.equals("available")){

                                                        String paymentmode = response.getString("paymentmode");
                                                        Log.e("paymentmode",""+paymentmode);

                                                        Intent intent = new Intent(TimeSlotActivity.this, EnterContactDetailsActivity.class);
                                                        intent.putExtra("bookingDate", gridItemEvening.getBookingDate());
                                                        intent.putExtra("bookingTime", gridItemEvening.getBookingTime());
                                                        intent.putExtra("bookingSession", gridItemEvening.getBookingSession());
                                                        intent.putExtra("patientName", patientName);
                                                        intent.putExtra("patientEmail", patientEmail);
                                                        intent.putExtra("patientMobile", patientMobile);
                                                        intent.putExtra("doctorImage", doctorImge);
                                                        intent.putExtra("clinicName", clinicName);
                                                        intent.putExtra("doctorName", doctorName);
                                                        intent.putExtra("speclization", speclization);
                                                        intent.putExtra("clinicId", clinicId);
                                                        intent.putExtra("doctorId", doctorId);
                                                        intent.putExtra("appointmentType",appointmentTypeStatus);
                                                        intent.putExtra("slotId", gridItemEvening.getSlotId());
                                                        Log.e("slotiddd", "" + gridItemEvening.getSlotId());
                                                        intent.putExtra("paymentmode",paymentmode);
                                                        Log.e("paymentmode1",""+paymentmode);
                                                        // intent.putExtra("appointmentTypeStatus",appointmentTypeStatus);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }else {

                                                        new MorningList().execute(URL_EVENING);
                                                        Toast.makeText(TimeSlotActivity.this, "slot not available", Toast.LENGTH_SHORT).show();

                                                    }


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                //Dismissing progress dialog
                                                pDialog.hide();

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
                                        RequestQueue requestQueue = Volley.newRequestQueue(TimeSlotActivity.this);

                                        //Adding request to the queue
                                        requestQueue.add(jsonObjReq);




                                    }



                            }
                        });

                    } else {
                        // Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
                        // searchSpeclitiesLayout.setVisibility(View.GONE);
                    }
                    gridAdapterEvening.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void ErrorText(){
        Log.e("array_sizes",""+gridItemMorningArrayList.size()+"----"+gridItemAfterNoonArrayList.size()+"==="+gridItemEveningArrayList.size());


        if ((gridItemMorningArrayList.size()==0) && (gridItemAfterNoonArrayList.size()==0) && (gridItemEveningArrayList.size()==0)){

            txtError.setVisibility(View.VISIBLE);

        }else {

            txtError.setVisibility(View.GONE);
        }
    }


    // morning Time Slot data
    public class MorningInstantList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(TimeSlotActivity.this);
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
            JSONObject json = jsonParser.makeHttpRequest(URL_MORNING_INSTANT, "GET", param);

            Log.e("URL_MORNING_INSTANT", URL_MORNING_INSTANT);

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
                gridItemMorningArrayListInstant.clear();
                resultObject = new JSONObject(String.valueOf(json));
                System.out.println("Testing the water " + resultObject.toString());
                jsonArray = resultObject.optJSONArray("clinic_instant_time_slots_morning");


                String ActiveStatus =resultObject.getString("status");
                Log.e("ActiveStatus :",""+ActiveStatus);


                if (ActiveStatus.equals("active")){

                    morningInstantlayout.setVisibility(View.VISIBLE);
                   // txtError.setVisibility(View.GONE);
                }else if (ActiveStatus.equals("inactive")){
                    morningInstantlayout.setVisibility(View.GONE);
                    //txtError.setVisibility(View.VISIBLE);
                }

                if (jsonArray != null&& jsonArray.length()>0) {     // check jsonArray is null?
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonChildNode = null;
                        try {
                            jsonChildNode = jsonArray.getJSONObject(i);
                            String slotId4 = jsonChildNode.optString("slotId").toString();
                            String bookingTime4 = jsonChildNode.optString("bookingTime").toString();
                            String bookingSession4 = jsonChildNode.optString("bookingSession").toString();
                            String bookingDate4 = jsonChildNode.optString("bookingDate").toString();
                            String appointmentType4 = jsonChildNode.optString("appointmentType").toString();
                            String status4 = jsonChildNode.optString("status").toString();

                            gridItemMorningArrayListInstant.add(new GridItemMorningInstant(slotId4,bookingTime4,bookingSession4,bookingDate4,appointmentType4,status4));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    if (gridItemMorningArrayListInstant.size() > 0) {


                        gridAdapterMorningInstant = new GridAdapterMorningInstant(TimeSlotActivity.this, R.layout.grid_item, gridItemMorningArrayListInstant);
                        gridViewMorningInstant.setAdapter(gridAdapterMorningInstant);
                        gridViewMorningInstant.setExpanded(true);
                        gridViewMorningInstant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                final GridItemMorningInstant gridItemInstant = (GridItemMorningInstant) parent.getItemAtPosition(position);


                                if (gridItemInstant.getStatus().equals("unavailable")){

                                    Toast.makeText(TimeSlotActivity.this, "Slots are unavailable", Toast.LENGTH_SHORT).show();
                                }else {

                                    DOCTOR_PAYMENT_URL =Apis.DOCTOR_PAYMENT_MODE_URL+redHealId+"/"+doctorId+"/"+gridItemInstant.getSlotId()+"/"+"instant";
                                    Log.e("doctorurlfjhfj",""+DOCTOR_PAYMENT_URL);
                                    //Showing a progress dialog
                                    final ProgressDialog pDialog = new ProgressDialog(TimeSlotActivity.this);
                                    pDialog.setMessage("Loading...");
                                    pDialog.show();

                                    final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                            DOCTOR_PAYMENT_URL, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.e("response",""+response);



                                            try {
                                                String status = response.getString("status");
                                                slot = response.getString("slot");
                                                Log.e("Slot",""+slot);

                                                if (slot.equals("available")){

                                                    String paymentmode = response.getString("paymentmode");
                                                    Log.e("paymentmode",""+paymentmode);

                                                    Intent intent = new Intent(TimeSlotActivity.this, EnterContactDetailsActivity.class);
                                                    intent.putExtra("bookingDate", gridItemInstant.getBookingDate());
                                                    intent.putExtra("bookingTime", gridItemInstant.getBookingTime());
                                                    intent.putExtra("bookingSession", gridItemInstant.getBookingSession());
                                                    intent.putExtra("patientName", patientName);
                                                    intent.putExtra("patientEmail", patientEmail);
                                                    intent.putExtra("patientMobile", patientMobile);
                                                    intent.putExtra("doctorImage", doctorImge);
                                                    intent.putExtra("clinicName", clinicName);
                                                    intent.putExtra("doctorName", doctorName);
                                                    intent.putExtra("speclization", speclization);
                                                    intent.putExtra("clinicId", clinicId);
                                                    intent.putExtra("doctorId", doctorId);
                                                    intent.putExtra("appointmentType",appointmentTypeStatus);
                                                    intent.putExtra("slotId", gridItemInstant.getSlotId());
                                                    Log.e("slotiddd", "" + gridItemInstant.getSlotId());
                                                    intent.putExtra("paymentmode",paymentmode);
                                                    Log.e("paymentmode1",""+paymentmode);
                                                    // intent.putExtra("appointmentTypeStatus",appointmentTypeStatus);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }else {

                                                    new MorningList().execute(URL_MORNING_INSTANT);
                                                    Toast.makeText(TimeSlotActivity.this, "slot not available", Toast.LENGTH_SHORT).show();

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            //Dismissing progress dialog
                                            pDialog.hide();

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
                                    RequestQueue requestQueue = Volley.newRequestQueue(TimeSlotActivity.this);

                                    //Adding request to the queue
                                    requestQueue.add(jsonObjReq);




                                }




                            }
                        });

                    } else {
                        // Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
                        // searchSpeclitiesLayout.setVisibility(View.GONE);
                    }
                    gridAdapterMorningInstant.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // afternoon Time Slot data

    public class AfterNoonInstantList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(TimeSlotActivity.this);
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
            JSONObject json = jsonParser.makeHttpRequest(URL_AFTERNOON_INSTANT, "GET", param);

            Log.e("URL_AfterNOON_INSTANT", URL_AFTERNOON_INSTANT);

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
                gridItemAfterNoonArrayListInstant.clear();
                resultObject = new JSONObject(String.valueOf(json));
                System.out.println("Testing the water " + resultObject.toString());
                jsonArray = resultObject.optJSONArray("clinic_instant_time_slots_afternoon");

                String ActiveStatus =resultObject.getString("status");
                Log.e("ActiveStatus :",""+ActiveStatus);


                if (ActiveStatus.equals("active")){

                    afternoonInstantLayout.setVisibility(View.VISIBLE);
                   // txtError.setVisibility(View.GONE);
                }else if (ActiveStatus.equals("inactive")){
                    afternoonInstantLayout.setVisibility(View.GONE);
                    //txtError.setVisibility(View.VISIBLE);
                }

                if (jsonArray != null&& jsonArray.length()>0) {     // check jsonArray is null?
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonChildNode = null;
                        try {
                            jsonChildNode = jsonArray.getJSONObject(i);
                            String slotId5 = jsonChildNode.optString("slotId").toString();
                            String bookingTime5 = jsonChildNode.optString("bookingTime").toString();
                            String bookingSession5 = jsonChildNode.optString("bookingSession").toString();
                            String bookingDate5 = jsonChildNode.optString("bookingDate").toString();
                            String appointmentType5 = jsonChildNode.optString("appointmentType").toString();
                            String status5 = jsonChildNode.optString("status").toString();

                            gridItemAfterNoonArrayListInstant.add(new GridItemAfterNoonInstant(slotId5,bookingTime5,bookingSession5,bookingDate5,appointmentType5,status5));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    if (gridItemAfterNoonArrayListInstant.size() > 0) {


                        gridAdapterAfternoonInstant = new GridAdapterAfterNoonInstant(TimeSlotActivity.this, R.layout.grid_item, gridItemAfterNoonArrayListInstant);
                        gridViewAfternoonInstant.setAdapter(gridAdapterAfternoonInstant);
                        gridViewAfternoonInstant.setExpanded(true);
                        gridViewAfternoonInstant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                final GridItemAfterNoonInstant gridItemAfterNoonInstant = (GridItemAfterNoonInstant) parent.getItemAtPosition(position);

                                if (gridItemAfterNoonInstant.getStatus().equals("unavailable")){

                                    Toast.makeText(TimeSlotActivity.this, "Slots are unavailable", Toast.LENGTH_SHORT).show();
                                }else {

                                        DOCTOR_PAYMENT_URL =Apis.DOCTOR_PAYMENT_MODE_URL+redHealId+"/"+doctorId+"/"+gridItemAfterNoonInstant.getSlotId()+"/"+"instant";
                                        Log.e("doctorurlfjhfj",""+DOCTOR_PAYMENT_URL);
                                        //Showing a progress dialog
                                        final ProgressDialog pDialog = new ProgressDialog(TimeSlotActivity.this);
                                        pDialog.setMessage("Loading...");
                                        pDialog.show();

                                        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                                DOCTOR_PAYMENT_URL, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.e("response",""+response);



                                                try {
                                                    String status = response.getString("status");
                                                    slot = response.getString("slot");
                                                    Log.e("Slot",""+slot);

                                                    if (slot.equals("available")){

                                                        String paymentmode = response.getString("paymentmode");
                                                        Log.e("paymentmode",""+paymentmode);

                                                        Intent intent = new Intent(TimeSlotActivity.this, EnterContactDetailsActivity.class);
                                                        intent.putExtra("bookingDate", gridItemAfterNoonInstant.getBookingDate());
                                                        intent.putExtra("bookingTime", gridItemAfterNoonInstant.getBookingTime());
                                                        intent.putExtra("bookingSession", gridItemAfterNoonInstant.getBookingSession());
                                                        intent.putExtra("patientName", patientName);
                                                        intent.putExtra("patientEmail", patientEmail);
                                                        intent.putExtra("patientMobile", patientMobile);
                                                        intent.putExtra("doctorImage", doctorImge);
                                                        intent.putExtra("clinicName", clinicName);
                                                        intent.putExtra("doctorName", doctorName);
                                                        intent.putExtra("speclization", speclization);
                                                        intent.putExtra("clinicId", clinicId);
                                                        intent.putExtra("doctorId", doctorId);
                                                        intent.putExtra("appointmentType",appointmentTypeStatus);
                                                        intent.putExtra("slotId", gridItemAfterNoonInstant.getSlotId());
                                                        Log.e("slotiddd", "" + gridItemAfterNoonInstant.getSlotId());
                                                        intent.putExtra("paymentmode",paymentmode);
                                                        Log.e("paymentmode1",""+paymentmode);
                                                        // intent.putExtra("appointmentTypeStatus",appointmentTypeStatus);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }else {

                                                        new MorningList().execute(URL_AFTERNOON_INSTANT);
                                                        Toast.makeText(TimeSlotActivity.this, "slot not available", Toast.LENGTH_SHORT).show();

                                                    }


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                //Dismissing progress dialog
                                                pDialog.hide();

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
                                        RequestQueue requestQueue = Volley.newRequestQueue(TimeSlotActivity.this);

                                        //Adding request to the queue
                                        requestQueue.add(jsonObjReq);




                                    }


                            }
                        });

                    } else {
                        // Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
                        // searchSpeclitiesLayout.setVisibility(View.GONE);
                    }
                    gridAdapterAfternoonInstant.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    // evening Time Slot data

    public class EveningInstantList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(TimeSlotActivity.this);
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
            JSONObject json = jsonParser.makeHttpRequest(URL_EVENING_INSTANT, "GET", param);

            Log.e("URL_EVENING_INSTANT", URL_EVENING_INSTANT);

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
                gridItemEveningArrayListInstant.clear();
                resultObject = new JSONObject(String.valueOf(json));
                System.out.println("Testing the water " + resultObject.toString());
                jsonArray = resultObject.optJSONArray("clinic_instant_time_slots_evening");

                String ActiveStatus =resultObject.getString("status");
                Log.e("ActiveStatus :",""+ActiveStatus);


                if (ActiveStatus.equals("active")){

                    eveningInstantLayout.setVisibility(View.VISIBLE);
                   //txtError.setVisibility(View.GONE);

                }else if (ActiveStatus.equals("inactive")){
                    eveningInstantLayout.setVisibility(View.GONE);
                    //txtError.setVisibility(View.VISIBLE);
                }


                if (jsonArray != null&& jsonArray.length()>0) {     // check jsonArray is null?
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonChildNode = null;
                        try {
                            jsonChildNode = jsonArray.getJSONObject(i);
                            String slotId6 = jsonChildNode.optString("slotId").toString();
                            String bookingTime6 = jsonChildNode.optString("bookingTime").toString();
                            String bookingSession6 = jsonChildNode.optString("bookingSession").toString();
                            String bookingDate6 = jsonChildNode.optString("bookingDate").toString();
                            String appointmentType6 = jsonChildNode.optString("appointmentType").toString();
                            String status6 = jsonChildNode.optString("status").toString();

                            gridItemEveningArrayListInstant.add(new GridItemEveningInstant(slotId6,bookingTime6,bookingSession6,bookingDate6,appointmentType6,status6));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    if (gridItemEveningArrayListInstant.size() > 0) {


                        gridAdapterEveningInstant = new GridAdapterEveningInstant(TimeSlotActivity.this, R.layout.grid_item, gridItemEveningArrayListInstant);
                        gridViewEveningInstant.setAdapter(gridAdapterEveningInstant);
                        gridViewEveningInstant.setExpanded(true);
                        gridAdapterEveningInstant.notifyDataSetChanged();
                        gridViewEveningInstant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                final GridItemEveningInstant gridItemEveningInstant = (GridItemEveningInstant) parent.getItemAtPosition(position);



                                if (gridItemEveningInstant.getStatus().equals("unavailable")){

                                    Toast.makeText(TimeSlotActivity.this, "Slots are unavailable", Toast.LENGTH_SHORT).show();
                                }else {



                                        DOCTOR_PAYMENT_URL =Apis.DOCTOR_PAYMENT_MODE_URL+redHealId+"/"+doctorId+"/"+gridItemEveningInstant.getSlotId()+"/"+"instant";
                                        Log.e("doctorurlfjhfj",""+DOCTOR_PAYMENT_URL);
                                        //Showing a progress dialog
                                        final ProgressDialog pDialog = new ProgressDialog(TimeSlotActivity.this);
                                        pDialog.setMessage("Loading...");
                                        pDialog.show();

                                        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                                DOCTOR_PAYMENT_URL, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.e("response",""+response);



                                                try {
                                                    String status = response.getString("status");
                                                    slot = response.getString("slot");
                                                    Log.e("Slot",""+slot);

                                                    if (slot.equals("available")){

                                                        String paymentmode = response.getString("paymentmode");
                                                        Log.e("paymentmode",""+paymentmode);

                                                        Intent intent = new Intent(TimeSlotActivity.this, EnterContactDetailsActivity.class);
                                                        intent.putExtra("bookingDate", gridItemEveningInstant.getBookingDate());
                                                        intent.putExtra("bookingTime", gridItemEveningInstant.getBookingTime());
                                                        intent.putExtra("bookingSession", gridItemEveningInstant.getBookingSession());
                                                        intent.putExtra("patientName", patientName);
                                                        intent.putExtra("patientEmail", patientEmail);
                                                        intent.putExtra("patientMobile", patientMobile);
                                                        intent.putExtra("doctorImage", doctorImge);
                                                        intent.putExtra("clinicName", clinicName);
                                                        intent.putExtra("doctorName", doctorName);
                                                        intent.putExtra("speclization", speclization);
                                                        intent.putExtra("clinicId", clinicId);
                                                        intent.putExtra("doctorId", doctorId);
                                                        intent.putExtra("appointmentType",appointmentTypeStatus);
                                                        Log.e("appointmentType",""+appointmentTypeStatus);
                                                        intent.putExtra("slotId", gridItemEveningInstant.getSlotId());
                                                        Log.e("slotiddd", "" + gridItemEveningInstant.getSlotId());
                                                        intent.putExtra("paymentmode",paymentmode);
                                                        Log.e("paymentmode1",""+paymentmode);
                                                        // intent.putExtra("appointmentTypeStatus",appointmentTypeStatus);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }else {

                                                        new MorningList().execute(URL_EVENING_INSTANT);
                                                        Toast.makeText(TimeSlotActivity.this, "slot not available", Toast.LENGTH_SHORT).show();

                                                    }


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                //Dismissing progress dialog
                                                pDialog.hide();

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
                                        RequestQueue requestQueue = Volley.newRequestQueue(TimeSlotActivity.this);

                                        //Adding request to the queue
                                        requestQueue.add(jsonObjReq);




                                    }



                            }
                        });

                    } else {

                        // searchSpeclitiesLayout.setVisibility(View.GONE);
                    }
                    gridAdapterEveningInstant.notifyDataSetChanged();
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
