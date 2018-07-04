package com.medoske.www.redhealpatient;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReportDetailsActivity extends AppCompatActivity {
WebView webView;
    PDFView pdfView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("pre consultation report");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        final String reportLink = getIntent().getExtras().getString("reportLink");

        String webUrl ="http://medoske.com/callcenter_api/uploads/reports/"+redHealId+"/"+reportLink;
        Log.e("weburl",""+webUrl);

         pdfView1 =(PDFView) findViewById(R.id.pdfView);
        new Retrivedata().execute(webUrl);
    }


    //retrive data for pdf
    class Retrivedata extends AsyncTask<String,Void,InputStream> {

        @Override
        protected InputStream doInBackground(String... String) {
            InputStream inputStream=null;
            try
            {
                URL url =new URL(String[0]);
                HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();


                if (urlConnection.getResponseCode()==200){

                    inputStream =new BufferedInputStream(urlConnection.getInputStream());
                }

            }
            catch (IOException e){

                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView1.fromStream(inputStream).load();
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
