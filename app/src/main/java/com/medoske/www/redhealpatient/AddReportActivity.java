package com.medoske.www.redhealpatient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.AddReportItem;
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

public class AddReportActivity extends AppCompatActivity implements View.OnClickListener {

    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private static final int SELECT_PDF = 2;
    String selectedImagePath;
    Uri selectedImageUri;
    ImageView imageView;

    String patientId,bookingId,appointmentRefNo,spinnerItem;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Report");


         /*final String doctorName = getIntent().getExtras().getString("doctorName");
         patientId = getIntent().getExtras().getString("patientId");
         bookingId = getIntent().getExtras().getString("bookingId");
         appointmentRefNo = getIntent().getExtras().getString("appointmentRefNo");*/



        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);

        final EditText etDoctorName =(EditText)findViewById(R.id.etDoctorName);
        //etDoctorName.setText(doctorName);
        etDoctorName.setEnabled(false);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("prescription");
        categories.add("diagnostic");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerItem = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + spinnerItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ((Button)findViewById(R.id.submitButton)).setOnClickListener(this);
        imageView =(ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(AddReportActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(AddReportActivity.this);

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
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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




    public String POST(String[] params, AddReportItem addReportItem){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.ADD_REPORT_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("patient_redhealId", new StringBody(addReportItem.getPatient_redhealId()));
                entity.addPart("bookingId", new StringBody(addReportItem.getBookingId()));
                entity.addPart("appointmentRefNo", new StringBody(addReportItem.getAppointmentRefNo()));
                entity.addPart("reportName", new StringBody(addReportItem.getReportType()));

                File file = new File(selectedImagePath);

                if (addReportItem.getReportType().equals("prescription")){

                    entity.addPart("prescription_report", new FileBody(file));
                    Log.e("imagePathPre",""+ file);
                }
                else if (addReportItem.getReportType().equals("diagnostic")){

                    entity.addPart("diagnostic_report", new FileBody(file));
                    Log.e("imagePathDiag",""+ file);
                }


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

        spinnerItem=spinner.getSelectedItem().toString();

        new Asyncclass().execute();

    }

    private class Asyncclass extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(AddReportActivity.this);
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

            AddReportItem addReportItem = new AddReportItem();
            addReportItem.setAppointmentRefNo(appointmentRefNo);
            addReportItem.setBookingId(bookingId);
            addReportItem.setPatient_redhealId(patientId);
            addReportItem.setReportType(spinnerItem);

            return POST(params,addReportItem);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            progressDialog.dismiss();
            super.onPostExecute(jsonObject);

            Intent in = new Intent(AddReportActivity.this, PastAppointmentDetailsActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(AddReportActivity.this,"Report Saved Successfully",Toast.LENGTH_SHORT).show();
            startActivity(in);

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
