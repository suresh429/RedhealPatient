package com.medoske.www.redhealpatient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Utilities.MySingleton;
import com.medoske.www.redhealpatient.Utilities.Session;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginActivity extends Activity {

    private static EditText email,password1;
    String paramUsername ;
    String paramPassword ;
    ProgressDialog pDialog;
    JSONObject jsonObj;
    JSONObject c;
    Button buttonLogin;
    final Context context = this;
    TextView registerlink,forgotPasswordLink,txtOr;
    String resRedhealId;
    String tkn;
    private BroadcastReceiver broadcastReceiver;

    private Session session;

    String refreshedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Typeface face = Typeface.createFromAsset(getAssets(), "fonts/OldStandard-Bold.ttf");
        txtOr=(TextView)findViewById(R.id.orText);
        txtOr.setTypeface(face);*/

        session = new Session(this);



        if(session.loggedin()){
            startActivity(new Intent(LoginActivity.this,BottomMenuActivity.class));
            finish();
        }

        // generate token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("token",""+refreshedToken);
        Toast.makeText(context, ""+refreshedToken, Toast.LENGTH_LONG).show();


           /*broadcastReceiver=new BroadcastReceiver() {
               @Override
                 public void onReceive(Context context, Intent intent) {

                   SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                    tkn =sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");


                 }
                };
              registerReceiver(broadcastReceiver,new IntentFilter(MyFirebaseInstanceIDService.TOKEN_BROADCAST));*/



        email = (EditText) findViewById(R.id.etEmail);
        password1 = (EditText) findViewById(R.id.etPassword);


        registerlink=(TextView)findViewById(R.id.textViewRegisterLink);
        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        forgotPasswordLink=(TextView)findViewById(R.id.forgotpassLink);
        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });

        buttonLogin=(Button)findViewById(R.id.button_Login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get the values given in EditText fields
                String givenUsername = email.getText().toString();
                String givenPassword = password1.getText().toString();
                System.out.println("Givennames is :" + givenUsername + " Given password is :" + givenPassword);

                if (  ( !email.getText().toString().equals("")) && ( !password1.getText().toString().equals("")) )
                {
                    // Pass those values to connectWithHttpGet() method
                    connectWithHttpGet(givenUsername, givenPassword);
                }
                else if ( ( !email.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                }
                else if ( ( !password1.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                }


            }});



    }

    //-----get the login values
    private void connectWithHttpGet(final String givenUsername, final String givenPassword) {

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
                progressDialog = new ProgressDialog(LoginActivity.this);
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
                paramUsername = params[0];
                paramPassword = params[1];
                System.out.println("paramUsername" + paramUsername + " paramPassword is :" + paramPassword);

                // Create an intermediate to connect with the Internet
                HttpClient httpClient = new DefaultHttpClient();

                // Sending a GET request to the web page that we want
                // Because of we are sending a GET request, we have to pass the values through the URL
                HttpGet httpGet = new HttpGet(Apis.LOGIN_URL + paramUsername + "/" + paramPassword);
                //HttpGet httpGet = new HttpGet(LOGIN_URL);
                Log.e("httpGet12345",""+httpGet);


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
                    JSONArray loginarray = jsonObj.getJSONArray("patient");

                    String resUname = "";
                    String resPassword = "";
                    String resName = "";
                    String resImage = "";
//                     resRedhealId = "";
                    String resBloodGroup = "";
                    String resGender = "";
                    String resMobileNumber = "";
                    String resDateOfBirth = "";
                    String resAddress = "";

                    // looping through All Contacts
                    //for (int i = 0; i < contacts.length(); i++) {
                    try {
                        c = loginarray.getJSONObject(0);
                        resUname = c.getString("email");
                        resPassword = c.getString("password");
                        resName=c.getString("fullName");
                        resRedhealId=c.getString("patientRedhealId");
                        Log.e("rhid",""+resRedhealId);
                        resBloodGroup=c.getString("bloodGroup");
                        resMobileNumber=c.getString("mobileNumber");
                        resDateOfBirth=c.getString("dateOfBirth");
                        resAddress=c.getString("address");
                        resGender=c.getString("gender");
                        resImage= Apis.IMAGE_BASEURL+"profile/"+c.getString("imagePath");


                        Log.e("c123455",""+resUname);
                        Log.e("resImage1234",""+resImage);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (resUname.equals(paramUsername)&& resPassword.equals(paramPassword)) {


                        SharedPreferences sp = getApplicationContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("firstName",resName);// key_name is the name through which you can retrieve it later.
                        editor.putString("imageProfile",resImage);
                        editor.putString("email",resUname);
                        editor.putString("patientRedhealId",resRedhealId);

                        editor.putString("bloodgroup",resBloodGroup);
                        editor.putString("mobileno",resMobileNumber);
                        editor.putString("dob",resDateOfBirth);
                        editor.putString("address",resAddress);
                        editor.putString("gender",resGender);
                        editor.commit();


                        Firebase();

                        session.setLoggedin(true);
                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), BottomMenuActivity.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(LoginActivity.this, "Login Success..", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid...", Toast.LENGTH_LONG).show();
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
        httpGetAsyncTask.execute(givenUsername, givenPassword);

    }

private void Firebase(){



    StringRequest stringRequest =new StringRequest(Request.Method.POST, Apis.PUSH_NOTIFICATION_URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
            Log.e("Reason",""+response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params = new HashMap<String, String>();
            params.put("fcm_tokenId",refreshedToken);
            params.put("redhealId",resRedhealId);

            Log.e("rhid123",""+resRedhealId);
            return checkParams(params);
        }

        private Map<String, String> checkParams(Map<String, String> map){
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
                if(pairs.getValue()==null){
                    map.put(pairs.getKey(), "");
                }
            }
            return map;
        }
    };
    MySingleton.getmInstance(LoginActivity.this).addToRequestQue(stringRequest);
}
}

