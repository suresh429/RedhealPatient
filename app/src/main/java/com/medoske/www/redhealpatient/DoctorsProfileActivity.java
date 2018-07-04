package com.medoske.www.redhealpatient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.medoske.www.redhealpatient.Adapters.TabsPagerAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.CustomViews.ResizableCustomView;
import com.medoske.www.redhealpatient.Items.AddFavDoctorItem;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ss.com.bannerslider.views.BannerSlider;

public class DoctorsProfileActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private static final int MAX_LINES =2;
    private BannerSlider bannerSlider;
    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    Button bookButton;

    private TextView niceCounter;
    private int niceCounterValue = 37;

    String favoriteValue,redHealId,doctorId;
    boolean value; // default value if no value was found
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_profile);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Toolbar appbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("suresh");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);
       // String likeStatus =sp.getString("like_status","");


        final String doctorName = getIntent().getExtras().getString("doctorName","defaultKey");
        getSupportActionBar().setTitle(doctorName);
        final String doctorImage = getIntent().getExtras().getString("doctorImage","defaultKey");
        final String doctorSpecilization = getIntent().getExtras().getString("specilization","defaultKey");
        String doctorExp = getIntent().getExtras().getString("experience","defaultKey");
        String doctorArea = getIntent().getExtras().getString("areaName","defaultKey");
        doctorId = getIntent().getExtras().getString("doctorId","defaultKey");
        String description = getIntent().getExtras().getString("description","defaultKey");
        String likeStatus = getIntent().getExtras().getString("like_status","defaultKey");
        final String latitude = getIntent().getExtras().getString("latitude","defaultKey");
        final String longitude = getIntent().getExtras().getString("longitude","defaultKey");

        //nice cardview
        /*niceCounter = (TextView) findViewById(R.id.counter_value);
        niceCounter.setText(String.valueOf(niceCounterValue));*/

        MaterialFavoriteButton materialFavoriteButtonNice = (MaterialFavoriteButton) findViewById(R.id.favorite_nice);
        materialFavoriteButtonNice.setFavorite(Boolean.parseBoolean(likeStatus));
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {


                        if (favorite) {
                            niceCounterValue++;
                             favoriteValue ="1" ;

                            Toast.makeText(DoctorsProfileActivity.this,"value :"+favoriteValue,Toast.LENGTH_SHORT).show();
                        } else {
                            niceCounterValue--;
                             favoriteValue ="0" ;

                            Toast.makeText(DoctorsProfileActivity.this,"value :"+favoriteValue,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        materialFavoriteButtonNice.setOnFavoriteAnimationEndListener(
                new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {
                       // niceCounter.setText(String.valueOf(niceCounterValue));

                    new Asyncclass().execute();

                    }
                });



        bookButton=(Button)findViewById(R.id.bookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DoctorsProfileActivity.this,SelectClinicACtivity.class);
                intent.putExtra("doctorImage",doctorImage);
                intent.putExtra("doctorId",doctorId);
                intent.putExtra("doctorName",doctorName);
                intent.putExtra("speclization",doctorSpecilization);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



       /* bannerSlider = (BannerSlider)findViewById(R.id.banner_slider1);

        //Add banners using image urls
        bannerSlider.addBanner(new RemoteBanner(
                "http://englishharmony.com/wp-content/uploads/2014/05/medical-english-phrases.jpg"
        ));
        bannerSlider.addBanner(new RemoteBanner(
                "https://thumbs.dreamstime.com/z/pills-medical-equipment-5751922.jpg"
        ));
        bannerSlider.addBanner(new RemoteBanner(
                "http://amedicalequipment.com/images/banner2.jpg"
        ));
        bannerSlider.addBanner(new RemoteBanner(
                "http://www.mednovate.com/wp-content/uploads/2016/02/DRUGS-facebook.jpg"
        ));

        //add banner using resource drawable
        // bannerSlider.addBanner(new DrawableBanner(R.drawable.background));
        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(DoctorsProfileActivity.this, "Banner with position " + String.valueOf(position) + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });*/



        TextView txtDoctorName =(TextView)findViewById(R.id.txtDoctorName);
        txtDoctorName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        txtDoctorName.setText(doctorName);

        TextView txtDoctorSpecilization =(TextView)findViewById(R.id.txtSpecilization);
        txtDoctorSpecilization.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        txtDoctorSpecilization.setText(doctorSpecilization+" , "+doctorArea);

        TextView txtDoctorExp =(TextView)findViewById(R.id.txtExperience);
        txtDoctorExp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        txtDoctorExp.setText(doctorExp+"yrs exp");

        /*TextView txtDoctorFee =(TextView)findViewById(R.id.txtPrice);
        txtDoctorFee.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Regular.ttf"));
        txtDoctorFee.setText(doctorFee+" consultation fee");*/

        TextView txtDescription =(TextView)findViewById(R.id.textDescription);
        ResizableCustomView.doResizeTextView(txtDescription, MAX_LINES, "View More", true);
        txtDescription.setText(description);
        txtDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorsProfileActivity.this,PaymentActivity.class));
                Toast.makeText(DoctorsProfileActivity.this,"click",Toast.LENGTH_SHORT).show();
            }
        });



        ImageView imageInfo =(ImageView)findViewById(R.id.imageViewInfo) ;
        imageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        DoctorsProfileActivity.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("Verified Certificates");

                // Setting Dialog Message
                alertDialog.setMessage("Welcome to Redheal");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.ic_action_name);

                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });



        ImageView imageDoctor =(ImageView) findViewById(R.id.clinicImage);
        Picasso.with(getApplicationContext()).load(doctorImage).placeholder(R.drawable.ic_action_placeholder).into(imageDoctor);


        //Initializing the tablayout
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("About Clinic"));
        tabLayout.addTab(tabLayout.newTab().setText("About Me"));
        tabLayout.addTab(tabLayout.newTab().setText("Feeds"));



        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //Initializing viewPager
        viewPager = (ViewPager)findViewById(R.id.pager);

        //Creating our pager adapter
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));




    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public String POST(String[] params, AddFavDoctorItem addFavItem){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.MY_FAV_DOCTOR_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("like_status", new StringBody(addFavItem.getLikeStatus()));
                entity.addPart("patient_redhealId", new StringBody(addFavItem.getPatientId()));
                entity.addPart("doctor_redhealId", new StringBody(addFavItem.getDoctorId()));


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


    private class Asyncclass extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

          /*  //Create a new progress dialog
            progressDialog = new ProgressDialog(get);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Uploading ...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(true);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            progressDialog.show();*/
        }


        @Override
        protected String doInBackground(String... params) {

            AddFavDoctorItem addFavItem = new AddFavDoctorItem();
            addFavItem.setLikeStatus(favoriteValue);
            addFavItem.setPatientId(redHealId);
            addFavItem.setDoctorId(doctorId);

            return POST(params,addFavItem);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            // progressDialog.dismiss();
            super.onPostExecute(jsonObject);

            /*Intent in = new Intent(get.this, PastAppointmentDetailsActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(AddReportActivity.this,"Report Saved Successfully",Toast.LENGTH_SHORT).show();
            startActivity(in);*/

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
