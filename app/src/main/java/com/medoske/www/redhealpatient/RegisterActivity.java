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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.RegisterItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;

    String dialogOtp ;
    JSONObject jsonObj;
    JSONObject c;

    String FullName, Email, mobile, Password;
    EditText etFullName,etEmail,etMobile,etPassword;
    String responseStatus,responseReason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Register");

        etFullName = (EditText) findViewById(R.id.etFullName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etPassword = (EditText) findViewById(R.id.etPassword);

        TextView txtLoginLink =(TextView)findViewById(R.id.link_login);
        txtLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        ((Button)findViewById(R.id.submitButton)).setOnClickListener(this);
    }

    public String POST(String[] params, RegisterItem register){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.REGISTER_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("fullName", new StringBody(register.getEtFullName()));
                Log.e("fullName+++",""+register.getEtFullName());
                entity.addPart("email", new StringBody(register.getEtEmail()));
                entity.addPart("mobileNumber", new StringBody(register.getEtPhone()));
                entity.addPart("password", new StringBody(register.getEtPassword()));

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
                responseReason = response1.getString("reason");


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
        checkValidation();
    }

    // Async task
    private class Asyncclass extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(RegisterActivity.this);
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

            RegisterItem register = new RegisterItem();
            register.setEtFullName(FullName);
            register.setEtEmail(Email);
            register.setEtPhone(mobile);
            register.setEtPassword(Password);
            return POST(params,register);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            progressDialog.dismiss();
            super.onPostExecute(jsonObject);



            if (responseStatus.equals("failed")){

                Toast.makeText(RegisterActivity.this, ""+responseReason, Toast.LENGTH_SHORT).show();

            }
            else {

                Intent in = new Intent(RegisterActivity.this, LoginActivity.class);
                Toast.makeText(RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                startActivity(in);
            }

/*
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_otp, null);
            final EditText mOtp = (EditText) mView.findViewById(R.id.etOtp);


            Button mLogin = (Button) mView.findViewById(R.id.btnVerify);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String givenOtp = mOtp.getText().toString();

                    if (  ( !mOtp.getText().toString().equals("")) )
                    {
                        // Pass those values to connectWithHttpGet() method
                        connectWithHttpGet(givenOtp);
                    }
                    else if ( ( !mOtp.getText().toString().equals("")) )
                    {
                        Toast.makeText(getApplicationContext(),
                                "otp field empty", Toast.LENGTH_SHORT).show();
                    }

                   *//* else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                    }*//*
                }
            });*/



        }
    }


    private void checkValidation() {
        FullName = etFullName.getText().toString();
        Email = etEmail.getText().toString();
        mobile = etMobile.getText().toString();
        Password = etPassword.getText().toString();

        boolean check;
        Pattern p;
        Matcher m;
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);
        m = p.matcher(Email);
        check = m.matches();

        // Check for both field is empty or not
        if (FullName.equals("") || Email.equals("") || mobile.equals("") || Password.equals("") ) {
            Toast.makeText(RegisterActivity.this, "Enter All Fields", Toast.LENGTH_LONG).show();
        }
        else if (FullName.isEmpty() || FullName.length()<4 ){
            etFullName.setError("Enter Minimum 4 Characters");
            return;
        }

        else if (!isValidMail(Email)){
            etEmail.setError("Enter Valid Email");
            return ;
        }
        else if (!isValidMobile(mobile)){
            etMobile.setError("Enter valid Mobile No");
            return;
        }
        else if (Password.isEmpty() || Password.length()<3 ){
            etPassword.setError("Enter Minimum 3 Characters");
            return;
        }

        else{
            new Asyncclass().execute();
        }

    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }



    private void connectWithHttpGet(final String givenOtp) {

        // Connect with a server is a time consuming process.
        //Therefore we use AsyncTask to handle it
        // From the three generic types;
        //First type relate with the argument send in execute()
        //Second type relate with onProgressUpdate method which I haven't use in this code
        //Third type relate with the return type of the doInBackground method, which also the input type of the onPostExecute method
        class HttpGetAsyncTask extends AsyncTask<String, Void, String> {

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Create a new progress dialog
                progressDialog = new ProgressDialog(RegisterActivity.this);
                //Set the progress dialog to display a horizontal progress bar
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                //Set the dialog title to 'Loading...'
                // progressDialog.setTitle("Chargement...");
                //Set the dialog message to 'Loading application View, please wait...'
                progressDialog.setMessage("Loading Please Wait...");

                //This dialog can't be canceled by pressing the back key
                progressDialog.setCancelable(true);
                //This dialog isn't indeterminate
                progressDialog.setIndeterminate(false);
                progressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                // As you can see, doInBackground has taken an Array of Strings as the argument
                //We need to specifically get the givenUsername and givenPassword
                dialogOtp = params[0];
                //paramPassword = params[1];
                System.out.println("paramUsername" + dialogOtp );

                // Create an intermediate to connect with the Internet
                HttpClient httpClient = new DefaultHttpClient();

                // Sending a GET request to the web page that we want
                // Because of we are sending a GET request, we have to pass the values through the URL
                HttpGet httpGet = new HttpGet(Apis.OTP_URL + dialogOtp);
                //HttpGet httpGet = new HttpGet(LOGIN_URL);
                Log.e("httpGet12345",""+Apis.OTP_URL + dialogOtp);


                try {
                    // execute(); executes a request using the default context.
                    // Then we assign the execution result to HttpResponse
                    HttpResponse httpResponse = httpClient.execute(httpGet);

                    // getContent() ; creates a new InputStream object of the entity.
                    // Now we need a readable source to read the byte stream that comes as the httpResponse
                    InputStream inputStream = httpResponse.getEntity().getContent();

                    // We have a byte stream. Next step is to convert it to a Character stream
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    // Then we have to wraps the existing reader (InputStreamReader) and buffer the input
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    // InputStreamReader contains a buffer of bytes read from the source stream and converts these into characters as needed.
                    //The buffer size is 8K
                    //Therefore we need a mechanism to append the separately coming chunks in to one String element
                    // We have to use a class that can handle modifiable sequence of characters for use in creating String
                    StringBuilder stringBuilder = new StringBuilder();

                    String bufferedStrChunk = null;

                    // There may be so many buffered chunks. We have to go through each and every chunk of characters
                    //and assign a each chunk to bufferedStrChunk String variable
                    //and append that value one by one to the stringBuilder
                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                    }

                    // Now we have the whole response as a String value.
                    //We return that value then the onPostExecute() can handle the content
                    System.out.println("Returninge of doInBackground :" + stringBuilder.toString());

                    // If the Username and Password match, it will return "working" as response
                    // If the Username or Password wrong, it will return "invalid" as response
                    return stringBuilder.toString();

                } catch (ClientProtocolException cpe) {
                    System.out.println("Exceptionrates caz of httpResponse :" + cpe);
                    cpe.printStackTrace();
                } catch (IOException ioe) {
                    System.out.println("Secondption generates caz of httpResponse :" + ioe);
                    ioe.printStackTrace();
                }

                return null;
            }

            // Argument comes for this method according to the return type of the doInBackground() and
            //it is the third generic type of the AsyncTask
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                progressDialog.dismiss();
                Log.e("result123", "" + result);

                try {
                    jsonObj = new JSONObject(result);
                    JSONArray loginarray = jsonObj.getJSONArray("user");

                    String resUname = "";
                    String resName = "";
                    String resRedhealId = "";
                    String resOtp = "";
                    String resMobileNumber = "";


                    // looping through All Contacts
                    //for (int i = 0; i < contacts.length(); i++) {
                    try {
                        c = loginarray.getJSONObject(0);
                        resUname = c.getString("email");
                        resName=c.getString("fullName");
                        resRedhealId=c.getString("patient_redhealId");
                        resOtp=c.getString("otp");
                        resMobileNumber=c.getString("mobileNumber");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (resOtp.equals(dialogOtp)) {

                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        Toast.makeText(RegisterActivity.this, "Otp is Verified", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid otp", Toast.LENGTH_LONG).show();
                        //Toast.makeText((Context) object.get("msg"),"",Toast.LENGTH_SHORT).show();
                    }
                    // }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        // Initialize the AsyncTask class
        HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
        // Parameter we pass in the execute() method is relate to the first generic type of the AsyncTask
        // We are passing the connectWithHttpGet() method arguments to that
        httpGetAsyncTask.execute(givenOtp);

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
