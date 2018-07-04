package com.medoske.www.redhealpatient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.ivbaranov.mfb.Utils;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Fragments.ProfileFragment;
import com.medoske.www.redhealpatient.Items.EditProfileItem;
import com.medoske.www.redhealpatient.Utilities.FileUtils;
import com.medoske.www.redhealpatient.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.medoske.www.redhealpatient.Utilities.FileUtils.getPath;
import static java.security.AccessController.getContext;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private static final int SELECT_PDF = 2;
    String selectedImagePath;
    Uri selectedImageUri;
    ImageView imageView;
    Spinner spGender,spinnerBloodGroup;
    String gender,name,mobile,email,age,patientRhid,bloodGroup;
    EditText etName,etMobile,etEmail,etAge;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
         patientRhid = sp.getString("patientRedhealId", "1");

        etName=(EditText)findViewById(R.id.editTextName);
        etMobile=(EditText)findViewById(R.id.editTextMobile);
        etEmail=(EditText)findViewById(R.id.editTextEmail);
        etAge=(EditText)findViewById(R.id.editTextAge);

        spGender=(Spinner)findViewById(R.id.spinnerGender) ;

        // Spinner Drop down elements
        List<String> sittingsArray = new ArrayList<String>();
        sittingsArray.add("male");
        sittingsArray.add("female");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sittingsArray);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spGender.setAdapter(dataAdapter);
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender=parent.getItemAtPosition(position).toString();
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


        imageView =(ImageView)findViewById(R.id.imageView_Patient);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        saveButton =(Button)findViewById(R.id.buttonSubmit);
        saveButton.setOnClickListener(this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(EditProfileActivity.this);

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
        selectedImagePath = FileUtils.getPath(this, selectedImageUri);
        Log.e("path",""+selectedImagePath);
    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        name = etName.getText().toString();
        mobile = etMobile.getText().toString();
        email = etEmail.getText().toString();
        age = etAge.getText().toString();
        gender = spGender.getSelectedItem().toString();
        bloodGroup = spinnerBloodGroup.getSelectedItem().toString();

        // Check patter for email id
       // Pattern p = Pattern.compile(Utils.regEx);

        // Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (name.equals("") || mobile.equals("") || email.equals("") || age.equals("") || gender.equals("") || bloodGroup.equals("")) {
            Toast.makeText(EditProfileActivity.this, "Enter All Fields", Toast.LENGTH_LONG).show();
        }

        else{

            //init new Product object
            EditProfileItem product = new EditProfileItem(patientRhid,name,email,gender,age,mobile,bloodGroup);

            //convert product => json array
            JSONArray jProducts = new JSONArray();
            JSONObject jProduct = new JSONObject();

            try {
                jProduct.put("patient_redhealId",product.getPatientId());
                jProduct.put("fullName",product.getName());
                Log.e("name",""+product.getName());
                jProduct.put("email",product.getEmail());
                jProduct.put("gender",product.getGender());
                jProduct.put("age",product.getAge());
                jProduct.put("mobileNumber",product.getMobile());
                jProduct.put("bloodGroup",product.getBloodGroup());


                //add to json array
                jProducts.put(jProduct);
                Log.d("json api", "Json array converted from Product: " + jProducts.toString());

                String jsonData = jProduct.toString();
                Log.e("jsonData",""+jsonData);
                new DoUpdateProfile().execute(jsonData);

                Intent i7 =new Intent(EditProfileActivity.this,BottomMenuActivity.class);
                i7.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i7);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onClick(View v) {

        checkValidation();
    }

    class DoUpdateProfile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Log.e("params_test",""+params);
            String jsonData = params[0];

            Log.e("jsonData_test_doinback",""+jsonData);

            try {
                URL url = new URL(Apis.EDIT_PROFILE_URL);
                Log.e("update_package",""+url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");


                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(jsonData);
                osw.flush();
                osw.close();


                InputStream is = connection.getInputStream();
                String result = "";
                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    result += (char) byteCharacter;


                }
                Log.d("json_api", "DoUpdateProduct.doInBackground Json return: " + result);

                JSONObject response = new JSONObject(result);
                Log.e("result",""+response.getString("status"));

                /*responseStatus = response.getString("status");
                Log.e("responseStatus",""+responseStatus);*/

                is.close();


//                osw.flush();
                osw.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
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
