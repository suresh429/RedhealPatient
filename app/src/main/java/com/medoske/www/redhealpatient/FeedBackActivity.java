package com.medoske.www.redhealpatient;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeedBackActivity extends AppCompatActivity {
    int k=0;
    int i=0;
LinearLayout reportLayout,suggestLayout;
    TextView textSuggest,textReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("FeedBack");

        reportLayout=(LinearLayout)findViewById(R.id.reportLayout);
        suggestLayout=(LinearLayout)findViewById(R.id.suggestLayout);

        textReport=(TextView) findViewById(R.id.reportText);
        textSuggest=(TextView) findViewById(R.id.suggestText);

        textReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k++;
                Handler handler = new Handler();
                Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        k = 0;
                    }
                };

                if (k == 1) {
                    //Single click
                    reportLayout.setVisibility(View.VISIBLE);

                } else if (k == 2) {
                    //Double click
                    k = 0;

                    reportLayout.setVisibility(View.GONE);
                }
            }
        });

        textSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                i++;
                Handler handler = new Handler();
                Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        i = 0;
                    }
                };

                if (i == 1) {
                    //Single click
                    suggestLayout.setVisibility(View.VISIBLE);

                } else if (i == 2) {
                    //Double click
                    i = 0;

                    suggestLayout.setVisibility(View.GONE);
                }
            }
        });
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
