package com.medoske.www.redhealpatient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.AddFamilyItem;
import com.medoske.www.redhealpatient.Items.AddReportItem;
import com.medoske.www.redhealpatient.Items.RelationItem;
import com.medoske.www.redhealpatient.Utilities.ServiceHandler;
import com.medoske.www.redhealpatient.Utilities.Utility;

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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.medoske.www.redhealpatient.Utilities.FileUtils.getPath;

public class AddFamilyActivity extends AppCompatActivity implements View.OnClickListener {
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private static final int SELECT_PDF = 2;
    String selectedImagePath;
    Uri selectedImageUri;
    ImageView imageView;
    String gender,relationName,age,email,mobile,bloodGroup,relationTypeId,redHealId;
    Spinner spinnerRelationType,spinnerGender,spinnerBloodGroup;
    EditText etRelationName,etAge,etEmail,etMobile;
    int relationCode;

    ArrayAdapter<String> relationTypeAdapter;
    ArrayList<RelationItem> relationItemArrayList=new ArrayList<RelationItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Family");


        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        ((Button)findViewById(R.id.button_save)).setOnClickListener(this);
        imageView =(ImageView)findViewById(R.id.imageViewPatient);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        etRelationName=(EditText)findViewById(R.id.editTextRelationName);
        etAge=(EditText)findViewById(R.id.editTextAge) ;
        etEmail=(EditText)findViewById(R.id.editTextEmail) ;
        etMobile=(EditText)findViewById(R.id.editTextMobile) ;


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
        spinnerRelationType=(Spinner)findViewById(R.id.spinnerrelationType);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddFamilyActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(AddFamilyActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);


        }
    }



    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(thumbnail);
        Log.e("image",""+imageView);

        selectedImageUri = Uri.fromFile(destination);
        selectedImagePath=getPath(getBaseContext(),selectedImageUri);

        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        // selectedImageUri = getImageUri(getApplicationContext(), thumbnail);
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imageView.setImageBitmap(bm);
        Log.e("image123",""+imageView);

        selectedImageUri = data.getData();
        Log.e("pathuri",""+selectedImageUri);
        selectedImagePath = getPath(this, selectedImageUri);
        Log.e("path",""+selectedImagePath);
    }




    public String POST(String[] params, AddFamilyItem addFamilyItem){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.ADD_FAMILY_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("relation_redhealId", new StringBody(addFamilyItem.getPatient_redhealId()));
                Log.e("relation_redhealId",""+ addFamilyItem.getPatient_redhealId());
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

                File file = new File(selectedImagePath);
                 entity.addPart("imagePath", new FileBody(file));
                    Log.e("imagePathPre",""+ file);

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

        checkValidation();

    }

    private class Asyncclass extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(AddFamilyActivity.this);
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

            return POST(params,addFamilyItem);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            progressDialog.dismiss();
            super.onPostExecute(jsonObject);

            Intent in = new Intent(AddFamilyActivity.this, MyFamilyActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(AddFamilyActivity.this,"Family Added Successfully",Toast.LENGTH_SHORT).show();
            startActivity(in);

        }
    }

    // Check Validation before login
    private void checkValidation() {
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
            Toast.makeText(AddFamilyActivity.this, "Enter All Fields", Toast.LENGTH_LONG).show();

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
            new Asyncclass().execute();
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
