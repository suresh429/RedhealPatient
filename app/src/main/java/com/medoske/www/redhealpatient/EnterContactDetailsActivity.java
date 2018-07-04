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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.medoske.www.redhealpatient.Adapters.InfiniteCyclerAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.BookAppointmentItem;
import com.medoske.www.redhealpatient.Items.NotificationDoctorItem;
import com.medoske.www.redhealpatient.Items.PatientRelationItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;
import com.medoske.www.redhealpatient.Utilities.ServiceHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EnterContactDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    RadioButton rb1,rb3;
    Button btnConfirm;
    CircleImageView imageView;
    TextView textDoctorName,textClinicName,textDateTime;
    EditText editTextName,editTextEmail,editTextMobile;
    String paymentMode1,patientRhid,bookingDate,bookingTime,doctorId,clinicId,paymentMode,slotId,relationTypeId,relation_redhealId,patientRelationRhid,appointmentType,appointmentTypeStatus;
    int referenceNo;
    int relationCode;

    ArrayAdapter<String> relationTypeAdapter;
    ArrayList<PatientRelationItem> relationItemArrayList=new ArrayList<PatientRelationItem>();

    String responseStatus,responseRefNo;
    Spinner spinnerRelationType;
    LinearLayout paymentModeLayout;

    String RELATION_URL ,RELATION_DETAILS_URL,DOCTOR_PAYMENT_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packag_enter_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Check contact details");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getApplicationContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        patientRhid = sp.getString("patientRedhealId", "1");

        RELATION_URL =Apis.PATIENT_RELATION_URL+patientRhid;



       /* // auto generate no
         Random r = new Random();
         referenceNo = r.nextInt(100000 - 1) + 789456;
         Log.e("randumno",""+referenceNo);*/

        bookingDate = getIntent().getExtras().getString("bookingDate","defaultKey");
        bookingTime = getIntent().getExtras().getString("bookingTime","defaultKey");
        String bookingSession = getIntent().getExtras().getString("bookingSession","defaultKey");
        String patientName = getIntent().getExtras().getString("patientName","defaultKey");
        String patientEmail = getIntent().getExtras().getString("patientEmail","defaultKey");
        String patientMobile = getIntent().getExtras().getString("patientMobile","defaultKey");
        String doctorImage = getIntent().getExtras().getString("doctorImage","defaultKey");
        String clinicName = getIntent().getExtras().getString("clinicName","defaultKey");
        String doctorName = getIntent().getExtras().getString("doctorName","defaultKey");
        String speclization = getIntent().getExtras().getString("speclization","defaultKey");
        appointmentType = getIntent().getExtras().getString("appointmentType","defaultKey");
        Log.e("appointmentType",""+appointmentType);
        doctorId = getIntent().getExtras().getString("doctorId","defaultKey");
        clinicId = getIntent().getExtras().getString("clinicId","defaultKey");
        slotId = getIntent().getExtras().getString("slotId","defaultKey");
        paymentMode1 = getIntent().getExtras().getString("paymentmode","defaultKey");
       // appointmentTypeStatus = getIntent().getExtras().getString("appointmentTypeStatus","defaultKey");


        DOCTOR_PAYMENT_URL =Apis.DOCTOR_PAYMENT_MODE_URL+"/"+doctorId+"/"+slotId;

        //doctorPaymentMethods();


        textDateTime=(TextView)findViewById(R.id.textDateTime);
        textDateTime.setText(bookingDate+"  &  "+bookingTime);

        imageView=(CircleImageView)findViewById(R.id.circleImageView) ;
        Picasso.with(getApplicationContext()).load(doctorImage).placeholder(R.drawable.ic_action_placeholder).into(imageView);

        textDoctorName=(TextView)findViewById(R.id.textPatientNae1);
        textDoctorName.setText(doctorName);

        textClinicName=(TextView)findViewById(R.id.textClinicName);
        textClinicName.setText(speclization +" | "+clinicName);

        editTextName =(EditText)findViewById(R.id.etName);

        editTextEmail =(EditText)findViewById(R.id.etEmail);

        editTextMobile =(EditText)findViewById(R.id.etMobile);


        spinnerRelationType=(Spinner)findViewById(R.id.spinnerrelationType);

        rb1 = (RadioButton) findViewById(R.id.radioButton_card);
        rb3 = (RadioButton) findViewById(R.id.radioButton_payatClinic);

        paymentModeLayout=(LinearLayout)findViewById(R.id.paymentModeLayout);


        if (appointmentType.equals("instant") || paymentMode1.equals("online")){

            rb3.setVisibility(View.GONE);
            rb1.setVisibility(View.GONE);
            paymentModeLayout.setVisibility(View.GONE);
            paymentMode = "Online";
        }
        else {
            rb3.setVisibility(View.VISIBLE);
            rb1.setVisibility(View.VISIBLE);
            paymentModeLayout.setVisibility(View.VISIBLE);
        }


        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnConfirm.setText("Pay Now");
                    paymentMode = "Online";


                }
            }
        });

        rb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    btnConfirm.setText("Confirm");
                    paymentMode ="Offline";
                    rb3.setChecked(true);


                }
            }
        });

        btnConfirm=(Button)findViewById(R.id.button_book);
        btnConfirm.setOnClickListener(this);

        new GetRelationId().execute();

    }

    // get relation iod
    private class GetRelationId extends AsyncTask<Void, Void, Void> {
        ArrayList<String> list;

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Void doInBackground(Void... params) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(RELATION_URL,ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("patient_relation");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            PatientRelationItem cat = new PatientRelationItem(catObj.getString("redhealId"),
                                    catObj.getString("relation"),catObj.getString("relationId"));
                            relationItemArrayList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            Log.d("spinner", "date");
            populateSpinner();
        }
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();


        for (int i = 0; i < relationItemArrayList.size(); i++) {
            lables.add(relationItemArrayList.get(i).getRelationype());
        }

        // Creating adapter for spinner
        relationTypeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        relationTypeAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerRelationType.setAdapter(relationTypeAdapter);


        spinnerRelationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                // dn = adapterView.getSelectedItem().toString().trim();
                relationCode = spinnerRelationType.getSelectedItemPosition();
                Log.e("relationCode", String.valueOf(relationCode));

                for (int i = 0; i < relationItemArrayList.size(); i++) {
                    if (i == relationCode) {
                        patientRelationRhid = relationItemArrayList.get(i).getRedhealId();
                        Log.e("relationIdid",""+patientRelationRhid);

                         relationTypeId =relationItemArrayList.get(i).getRelationTypeId();
                         Log.e("typeId",""+relationTypeId);

                    }



                    RELATION_DETAILS_URL = Apis.PATIENT_RELATION_DETAILS_URL+ patientRelationRhid.replace(" ", "%20").trim();
                    new ClinicDataList().execute(RELATION_DETAILS_URL);
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public class ClinicDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(EnterContactDetailsActivity.this);
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
            JSONObject json = jsonParser.makeHttpRequest(RELATION_DETAILS_URL, "GET", param);

            Log.e("DOCTORS_LIST_URL", RELATION_DETAILS_URL);

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
                JSONArray jsonArray = json.optJSONArray("patient_details");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    relation_redhealId = jsonObject.optString("patient_redhealId").toString();
                    String patient_redhealId = jsonObject.optString("patient_redhealId").toString();
                    String fullName = jsonObject.optString("fullName").toString();
                    //relationTypeId = jsonObject.optString("relationTypeId").toString();
                    String gender = jsonObject.optString("gender").toString();
                    String bloodGroup = jsonObject.optString("bloodGroup").toString();
                    String mobileNumber = jsonObject.optString("mobileNumber").toString();
                    String email = jsonObject.optString("email").toString();
                    String age = jsonObject.optString("age").toString();
                    String imagePath = jsonObject.optString("imagePath").toString();
                    String relationType = jsonObject.optString("relationType").toString();

                    editTextName.setText(fullName);
                    editTextEmail.setText(email);
                    editTextMobile.setText(mobileNumber);


                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}


        }
    }

    public String POST(String[] params, BookAppointmentItem bookAppointmentItem){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.CONFIRM_BOOK_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("patient_redhealId", new StringBody(bookAppointmentItem.getPatient_redhealId()));
                Log.e("patient_redhealId+++",""+bookAppointmentItem.getPatient_redhealId());
                entity.addPart("clinic_redhealId", new StringBody(bookAppointmentItem.getClinic_redhealId()));
                entity.addPart("doctor_redhealId", new StringBody(bookAppointmentItem.getDoctor_redhealId()));
                entity.addPart("bookingDate", new StringBody(bookAppointmentItem.getBookingDate()));
                entity.addPart("bookingTime", new StringBody(bookAppointmentItem.getBookingTime()));
                entity.addPart("paymentMode", new StringBody(bookAppointmentItem.getPaymentMode()));
                entity.addPart("slotId", new StringBody(bookAppointmentItem.getSlotId()));
                entity.addPart("relationTypeId", new StringBody(bookAppointmentItem.getRelationTypeId()));
                entity.addPart("relation_redhealId", new StringBody(bookAppointmentItem.getRelation_redhealId()));
                entity.addPart("appointmentType", new StringBody(bookAppointmentItem.getAppointmentType()));
               // entity.addPart("status", new StringBody(bookAppointmentItem.getStatus()));

                httppost.setEntity(entity);
                response = httpclient.execute(httppost);

                Log.e("test", "SC:" + response.getStatusLine().getStatusCode());

                HttpEntity resEntity = response.getEntity();

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), "UTF-8"));
                String sResponse;
                StringBuilder s = new StringBuilder();

                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }
                Log.e("test", "Response: " + s);

                JSONObject response1 = new JSONObject(String.valueOf(s));
                Log.e("result",""+response1.getString("status"));

                responseStatus = response1.getString("status");
                responseRefNo = response1.getString("appointmentRefNo");
                Log.e("result",""+responseRefNo);

            } catch (ClientProtocolException e) {


            } catch (IOException e) {
                e.printStackTrace();

            }

            // 9. receive response as inputStream
            inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    // convert input stream to string
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    public void onClick(View v) {


        if (appointmentType.equals("instant") || paymentMode1.equals("online")){

            new Asyncclass().execute();
        }


        else if (rb1.isChecked()){

            Toast.makeText(EnterContactDetailsActivity.this, "pay at online", Toast.LENGTH_SHORT).show();

            new Asyncclass().execute();

        }

        else if (rb3.isChecked()){

            new Asyncclass().execute();

        }
        else {

            Toast.makeText(EnterContactDetailsActivity.this, "please select Payment Mode", Toast.LENGTH_SHORT).show();
        }
    }


    // Async task
    private class Asyncclass extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(EnterContactDetailsActivity.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Set the dialog title to 'Loading...'
            // progressDialog.setTitle("Chargement...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Loading ...");

            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(true);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {

            BookAppointmentItem bookAppointmentItem = new BookAppointmentItem();
            bookAppointmentItem.setClinic_redhealId(clinicId);
            bookAppointmentItem.setDoctor_redhealId(doctorId);
            bookAppointmentItem.setPatient_redhealId(patientRelationRhid);
            bookAppointmentItem.setBookingDate(bookingDate);
            bookAppointmentItem.setBookingTime(bookingTime);
            bookAppointmentItem.setPaymentMode(paymentMode);
            bookAppointmentItem.setSlotId(slotId);
            bookAppointmentItem.setRelation_redhealId(patientRhid);
            bookAppointmentItem.setRelationTypeId(relationTypeId);
            bookAppointmentItem.setAppointmentType(appointmentType);
           // bookAppointmentItem.setStatus(appointmentTypeStatus);

            return POST(params,bookAppointmentItem);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            progressDialog.dismiss();
            super.onPostExecute(jsonObject);

            if (responseStatus.equals("failed")){
                Toast.makeText(EnterContactDetailsActivity.this,"your appointment already booked! Thank You",Toast.LENGTH_LONG).show();
            }
            else {

                Intent intent =new Intent(EnterContactDetailsActivity.this,ConfirmationAppointmentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("appointmentRefno",responseRefNo);
                startActivity(intent);
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