package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.AddFamilyItem;
import com.medoske.www.redhealpatient.Items.AddFamilyOtpItem;
import com.medoske.www.redhealpatient.Items.CheckFamilyItem;
import com.medoske.www.redhealpatient.Items.CheckOtpItem;
import com.medoske.www.redhealpatient.Items.RelationItem;
import com.medoske.www.redhealpatient.Utilities.ServiceHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddFamilyActivity1 extends AppCompatActivity implements View.OnClickListener {
    String gender,relationName,age,email,mobile,bloodGroup,relationTypeId,redHealId,givenOtp;
    Spinner spinnerRelationType,spinnerGender,spinnerBloodGroup;
    EditText etRelationName,etAge,etEmail,etMobile;
    int relationCode;

    ArrayAdapter<String> relationTypeAdapter;
    ArrayList<RelationItem> relationItemArrayList=new ArrayList<RelationItem>();

    String responseStatus,responseAction,responseReason,otpStatus,otpReason,responseStatus1,responseAction1,otpStatus1,otpReason1;
    LinearLayout addLayout,checkLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family1);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Family");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        ((Button)findViewById(R.id.buttonCheck)).setOnClickListener(this);
        ((Button)findViewById(R.id.button_save)).setOnClickListener(this);
        etMobile=(EditText)findViewById(R.id.editTextMobile) ;
        etRelationName=(EditText)findViewById(R.id.editTextRelationName);
        etAge=(EditText)findViewById(R.id.editTextAge) ;
        etEmail=(EditText)findViewById(R.id.editTextEmail) ;

        addLayout=(LinearLayout)findViewById(R.id.addLayout);
        checkLayout=(LinearLayout)findViewById(R.id.checkLayout);

        // Spinner Gender
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("male");
        categories.add("female");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerGender.setAdapter(dataAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + gender, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // spinner blood group
        spinnerBloodGroup=(Spinner)findViewById(R.id.spinnerBloodGroup);
        // spinner drop down
        List<String> group =new ArrayList<>();
        group.add("A+");
        group.add("A-");
        group.add("B+");
        group.add("B-");
        group.add("AB+");
        group.add("AB-");
        group.add("O+");
        group.add("O-");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, group);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBloodGroup.setAdapter(dataAdapter1);
        spinnerBloodGroup.setPrompt("Select Blood Group");
        spinnerBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroup = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + bloodGroup, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // spinner relation Type
        spinnerRelationType=(Spinner)findViewById(R.id.spinnerrelationType );

        new GetRelationId().execute();

    }


    private class GetRelationId extends AsyncTask<Void, Void, Void> {
        ArrayList<String> list;

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Void doInBackground(Void... params) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Apis.RELATION_TYPE_URL,ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("relation_types");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            RelationItem cat = new RelationItem(catObj.getString("relationTypeId"),
                                    catObj.getString("relationType"));
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
            lables.add(relationItemArrayList.get(i).getRelationType());
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
                        relationTypeId = relationItemArrayList.get(i).getRealtionId();
                        Log.e("cityyyyid",""+relationTypeId);
                    }

                   /* URL_AREA = Apis.AREA_URL+ cityId.replace(" ", "%20").trim();
                    new GetCategories().execute(URL_AREA);*/
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public String POST1(String[] params, CheckFamilyItem checkFamilyItem){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.CHECK_FAMILY_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("patient_redhealId", new StringBody(checkFamilyItem.getPatient_redhealId()));
                Log.e("patient_redhealId",""+ checkFamilyItem.getPatient_redhealId());
                entity.addPart("relationTypeId", new StringBody(checkFamilyItem.getRelationTypeId()));
                Log.e("relationTypeId",""+ checkFamilyItem.getRelationTypeId());
                entity.addPart("mobileNumber", new StringBody(checkFamilyItem.getMobileNumber()));
                Log.e("mobileNumber",""+ checkFamilyItem.getMobileNumber());


               /* File file = new File(selectedImagePath);
                entity.addPart("imagePath", new FileBody(file));
                Log.e("imagePathPre",""+ file);
*/
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
                responseAction = response1.getString("action");
                responseReason = response1.getString("reason");
                Log.e("result",""+responseAction);

            } catch (ClientProtocolException e) {


            } catch (IOException e) {
                e.printStackTrace();

            }

            // 9. receive response as inputStream
            inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString1(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    private String convertInputStreamToString1(InputStream inputStream) throws IOException {
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

        switch(v.getId()){

            case R.id.buttonCheck: /** Start a new Activity MyCards.java */
                checkValidation();
                break;

            case R.id.button_save: /** AlerDialog when click on Exit */
                checkValidation3();
                break;
        }



    }

    private class Asyncclass1 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(AddFamilyActivity1.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Uploading ...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(true);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {

            CheckFamilyItem checkFamilyItem = new CheckFamilyItem();
            checkFamilyItem.setMobileNumber(mobile);
            checkFamilyItem.setRelationTypeId(relationTypeId);
            checkFamilyItem.setPatient_redhealId(redHealId);


            return POST1(params,checkFamilyItem);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            progressDialog.dismiss();
            super.onPostExecute(jsonObject);

           /* Intent in = new Intent(AddFamilyActivity1.this, MyFamilyActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(AddFamilyActivity1.this,"Family Added Successfully",Toast.LENGTH_SHORT).show();
            startActivity(in);*/

            if (responseStatus.equals("failed")){

                if (responseAction.equals("exist")){

                    Toast.makeText(AddFamilyActivity1.this,""+responseReason,Toast.LENGTH_LONG).show();
                }
                else if (responseAction.equals("add")){

                addLayout.setVisibility(View.VISIBLE);
                checkLayout.setVisibility(View.GONE);

                }

            }
            else if (responseStatus.equals("success")){

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddFamilyActivity1.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_otp, null);
                final EditText mOtp = (EditText) mView.findViewById(R.id.etOtp);


                Button mLogin = (Button) mView.findViewById(R.id.btnVerify);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                         givenOtp = mOtp.getText().toString();

                        if (  ( givenOtp.equals("")) )
                        {
                            Toast.makeText(getApplicationContext(),
                                    "otp field empty", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                           new OtpAsyncclass2().execute();
                        }

                    }
                });
//                dialog.dismiss();
                addLayout.setVisibility(View.GONE);
            }





        }
    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        mobile = etMobile.getText().toString().trim();

        // Check for both field is empty or not
        if ( mobile.equals("")) {
            // loginLayout.startAnimation(shakeAnimation);
            Toast.makeText(AddFamilyActivity1.this, "Enter All Fields", Toast.LENGTH_LONG).show();

        }
        else if (mobile.isEmpty() || mobile.length()<10){
            etMobile.setError("Enter valid Mobile no ");
        }

        else{
            new Asyncclass1().execute();
        }


    }


    public String POST2(String[] params, CheckOtpItem checkOtpItem){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.ADD_FAMILY_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("patient_redhealId", new StringBody(checkOtpItem.getPatient_redhealId()));
                Log.e("patient_redhealId",""+ checkOtpItem.getPatient_redhealId());
                entity.addPart("relationTypeId", new StringBody(checkOtpItem.getRelationTypeId()));
                Log.e("relationTypeId",""+ checkOtpItem.getRelationTypeId());
                entity.addPart("mobileNumber", new StringBody(checkOtpItem.getMobileNumber()));
                Log.e("mobileNumber",""+ checkOtpItem.getMobileNumber());
                entity.addPart("otp", new StringBody(checkOtpItem.getOtp()));
                Log.e("otp",""+ checkOtpItem.getOtp());


               /* File file = new File(selectedImagePath);
                entity.addPart("imagePath", new FileBody(file));
                Log.e("imagePathPre",""+ file);
*/
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

                otpStatus = response1.getString("status");
                otpReason = response1.getString("reason");
                Log.e("result",""+responseAction);

            } catch (ClientProtocolException e) {


            } catch (IOException e) {
                e.printStackTrace();

            }

            // 9. receive response as inputStream
            inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString2(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    private String convertInputStreamToString2(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class OtpAsyncclass2 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(AddFamilyActivity1.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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

            CheckOtpItem checkOtpItem = new CheckOtpItem();
            checkOtpItem.setMobileNumber(mobile);
            checkOtpItem.setRelationTypeId(relationTypeId);
            checkOtpItem.setPatient_redhealId(redHealId);
            checkOtpItem.setOtp(givenOtp);

            return POST2(params,checkOtpItem);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            progressDialog.dismiss();
            super.onPostExecute(jsonObject);

           /* Intent in = new Intent(AddFamilyActivity1.this, MyFamilyActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(AddFamilyActivity1.this,"Family Added Successfully",Toast.LENGTH_SHORT).show();
            startActivity(in);*/

            if (otpStatus.equals("failed")){

                Toast.makeText(AddFamilyActivity1.this, ""+otpReason, Toast.LENGTH_SHORT).show();

            }
            else if (responseStatus.equals("success")) {

                Intent in = new Intent(AddFamilyActivity1.this, MyFamilyActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(AddFamilyActivity1.this,""+otpReason,Toast.LENGTH_SHORT).show();
                startActivity(in);
            }
        }
    }


    public String POST3(String[] params, AddFamilyItem addFamilyItem){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.ADD_RELATION_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart(" patient_redhealId", new StringBody(addFamilyItem.getPatient_redhealId()));
                Log.e(" patient_redhealId",""+ addFamilyItem.getPatient_redhealId());
                entity.addPart("fullName", new StringBody(addFamilyItem.getFullName()));
                Log.e("fullName",""+ addFamilyItem.getFullName());
                entity.addPart("relationTypeId", new StringBody(addFamilyItem.getRelationTypeId()));
                Log.e("relation",""+ addFamilyItem.getRelationTypeId());
                entity.addPart("gender", new StringBody(addFamilyItem.getGender()));
                Log.e("gender",""+ addFamilyItem.getGender());
                entity.addPart("bloodGroup", new StringBody(addFamilyItem.getBloodGroup()));
                Log.e("bloodGroup",""+ addFamilyItem.getBloodGroup());
                entity.addPart("age", new StringBody(addFamilyItem.getAge()));
                Log.e("age",""+ addFamilyItem.getAge());
                entity.addPart("mobileNumber", new StringBody(addFamilyItem.getMobileNumber()));
                Log.e("mobileNumber",""+ addFamilyItem.getMobileNumber());
                entity.addPart("email", new StringBody(addFamilyItem.getEmail()));
                Log.e("email",""+ addFamilyItem.getEmail());

                /*File file = new File(selectedImagePath);
                entity.addPart("imagePath", new FileBody(file));
                Log.e("imagePathPre",""+ file);*/

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

                responseStatus1 = response1.getString("status");
                responseAction1 = response1.getString("action");
                Log.e("result",""+responseAction);

            } catch (ClientProtocolException e) {


            } catch (IOException e) {
                e.printStackTrace();

            }

            // 9. receive response as inputStream
            inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString3(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    private String convertInputStreamToString3(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class Asyncclass3 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(AddFamilyActivity1.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Uploading ...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(true);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {

            AddFamilyItem addFamilyItem = new AddFamilyItem();
            addFamilyItem.setAge(age);
            addFamilyItem.setBloodGroup(bloodGroup);
            addFamilyItem.setFullName(relationName);
            addFamilyItem.setGender(gender);
            addFamilyItem.setMobileNumber(mobile);
            addFamilyItem.setRelationTypeId(relationTypeId);
            addFamilyItem.setPatient_redhealId(redHealId);
            addFamilyItem.setEmail(email);

            return POST3(params,addFamilyItem);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            progressDialog.dismiss();
            super.onPostExecute(jsonObject);

            /*Intent in = new Intent(AddFamilyActivity1.this, MyFamilyActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(AddFamilyActivity1.this,"Family Added Successfully",Toast.LENGTH_SHORT).show();
            startActivity(in);*/



             if (responseStatus1.equals("success")){

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddFamilyActivity1.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_otp, null);
                final EditText mOtp = (EditText) mView.findViewById(R.id.etOtp);


                Button mLogin = (Button) mView.findViewById(R.id.btnVerify);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        givenOtp = mOtp.getText().toString();

                        if (  ( givenOtp.equals("")) )
                        {
                            Toast.makeText(getApplicationContext(),
                                    "otp field empty", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            new OtpAsyncclass4().execute();
                        }

                    }
                });
//                dialog.dismiss();

            }


        }
    }

    // Check Validation before login
    private void checkValidation3() {
        // Get email id and password
//        relationType = spinnerRelationType.getSelectedItem().toString();
        gender = spinnerGender.getSelectedItem().toString();
        bloodGroup = spinnerBloodGroup.getSelectedItem().toString();

        relationName = etRelationName.getText().toString().trim();
        age = etAge.getText().toString().trim();
        mobile = etMobile.getText().toString().trim();
        email = etEmail.getText().toString().trim();


        // Check for both field is empty or not
        if (relationName.equals("") || age.equals("") || email.equals("") || mobile.equals("")  || gender.equals("") || bloodGroup.equals("")) {
            // loginLayout.startAnimation(shakeAnimation);
            Toast.makeText(AddFamilyActivity1.this, "Enter All Fields", Toast.LENGTH_LONG).show();

        }
        else if (relationName.isEmpty() || relationName.length()<=4 ){
            etRelationName.setError("Enter Minimum 4 Characters");
            return;
        }
        else if (age.isEmpty() ){
            etAge.setError("Enter Age");
            return;
        }
        else if (mobile.isEmpty() || mobile.length()<10){
            etMobile.setError("Enter valid Mobile no ");
        }

        else{
            new Asyncclass3().execute();
        }


    }


    public String POST4(String[] params, AddFamilyOtpItem addfamilyotpitem){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.ADD_FAMILY_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart(" patient_redhealId", new StringBody(addfamilyotpitem.getPatient_redhealId()));
                Log.e(" patient_redhealId",""+ addfamilyotpitem.getPatient_redhealId());
                entity.addPart("fullName", new StringBody(addfamilyotpitem.getFullName()));
                Log.e("fullName",""+ addfamilyotpitem.getFullName());
                entity.addPart("relationTypeId", new StringBody(addfamilyotpitem.getRelationTypeId()));
                Log.e("relation",""+ addfamilyotpitem.getRelationTypeId());
                entity.addPart("gender", new StringBody(addfamilyotpitem.getGender()));
                Log.e("gender",""+ addfamilyotpitem.getGender());
                entity.addPart("bloodGroup", new StringBody(addfamilyotpitem.getBloodGroup()));
                Log.e("bloodGroup",""+ addfamilyotpitem.getBloodGroup());
                entity.addPart("age", new StringBody(addfamilyotpitem.getAge()));
                Log.e("age",""+ addfamilyotpitem.getAge());
                entity.addPart("mobileNumber", new StringBody(addfamilyotpitem.getMobileNumber()));
                Log.e("mobileNumber",""+ addfamilyotpitem.getMobileNumber());
                entity.addPart("email", new StringBody(addfamilyotpitem.getEmail()));
                Log.e("email",""+ addfamilyotpitem.getEmail());
                entity.addPart("otp", new StringBody(addfamilyotpitem.getOtp()));
                Log.e("otp",""+ addfamilyotpitem.getOtp());

                /*File file = new File(selectedImagePath);
                entity.addPart("imagePath", new FileBody(file));
                Log.e("imagePathPre",""+ file);*/

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
                JSONObject response2 = new JSONObject(String.valueOf(s));
                Log.e("result",""+response2.getString("status"));

                otpStatus1 = response2.getString("status");
                otpReason1 = response2.getString("reason");
                Log.e("result",""+responseAction);

            } catch (ClientProtocolException e) {


            } catch (IOException e) {
                e.printStackTrace();

            }

            // 9. receive response as inputStream
            inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString4(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    private String convertInputStreamToString4(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class OtpAsyncclass4 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(AddFamilyActivity1.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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

            AddFamilyOtpItem addfamilyotpitem = new AddFamilyOtpItem();
            addfamilyotpitem.setAge(age);
            addfamilyotpitem.setBloodGroup(bloodGroup);
            addfamilyotpitem.setFullName(relationName);
            addfamilyotpitem.setGender(gender);
            addfamilyotpitem.setMobileNumber(mobile);
            addfamilyotpitem.setRelationTypeId(relationTypeId);
            addfamilyotpitem.setPatient_redhealId(redHealId);
            addfamilyotpitem.setEmail(email);
            addfamilyotpitem.setOtp(givenOtp);

            return POST4(params,addfamilyotpitem);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            progressDialog.dismiss();
            super.onPostExecute(jsonObject);

            /*Intent in = new Intent(AddFamilyActivity1.this, MyFamilyActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(AddFamilyActivity1.this,"Family Added Successfully",Toast.LENGTH_SHORT).show();
            startActivity(in);*/

            if (otpStatus1.equals("failed")){

                Toast.makeText(AddFamilyActivity1.this, ""+otpReason1, Toast.LENGTH_SHORT).show();

            }
            else if (otpStatus1.equals("success")) {

                Intent in1 = new Intent(AddFamilyActivity1.this, MyFamilyActivity.class);
                in1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(AddFamilyActivity1.this,""+otpReason1,Toast.LENGTH_SHORT).show();
                startActivity(in1);
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
