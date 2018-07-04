package com.medoske.www.redhealpatient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.medoske.www.redhealpatient.Adapters.AllPackagesAdapter;
import com.medoske.www.redhealpatient.Adapters.DiagnosticRecommendationAdapter;
import com.medoske.www.redhealpatient.Adapters.TipsSeasonalAdapter;
import com.medoske.www.redhealpatient.Adapters.TopSpecialitiesRecyclerview;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.AllPackaesItem;
import com.medoske.www.redhealpatient.Items.AlternateTreatmentItem;
import com.medoske.www.redhealpatient.Items.DianoRecomItem;
import com.medoske.www.redhealpatient.Items.TipsSeasonItem;
import com.medoske.www.redhealpatient.Items.TopSpelitiesItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticsActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView MyRecyclerViewPackages,MyRecyclerViewRecommendation;

    private List<DianoRecomItem> dianoRecomItemArrayList =new ArrayList<>();
    DiagnosticRecommendationAdapter diagnosticRecommendationAdapter;

    private List<AllPackaesItem> allPackaesItemArrayList =new ArrayList<>();
    AllPackagesAdapter allPackagesAdapter;

    ImageButton filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Diagnostics");

        Intent intent = getIntent();
        String [] stringArray = intent.getStringArrayExtra("checkedContacts");

        Log.e("Arrray",""+stringArray);


        MyRecyclerViewRecommendation = (RecyclerView)findViewById(R.id.allRecommendedList);
        MyRecyclerViewRecommendation.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager4 = new LinearLayoutManager(DiagnosticsActivity.this);
        MyLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        MyRecyclerViewRecommendation.setLayoutManager(MyLayoutManager4);
        MyRecyclerViewRecommendation.setNestedScrollingEnabled(false);

        MyRecyclerViewPackages = (RecyclerView) findViewById(R.id.allPackagesList);
        MyRecyclerViewPackages.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(DiagnosticsActivity.this);
        MyLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        MyRecyclerViewPackages.setLayoutManager(MyLayoutManager);
        MyRecyclerViewPackages.setNestedScrollingEnabled(false);
        allPackagesAdapter=new AllPackagesAdapter(DiagnosticsActivity.this,allPackaesItemArrayList);
        MyRecyclerViewPackages.setAdapter(allPackagesAdapter);

        filterButton =(ImageButton)findViewById(R.id.filterButton) ;
        filterButton.setOnClickListener(this);

        prepareRecommendData();
        prepareAllPackagesData();
    }

    private void prepareAllPackagesData() {
        AllPackaesItem movie2 = new AllPackaesItem("WHOLE BODY CHECK","98 Test Included","offer price : 2000 INR","discount Price : 4000 INR","Apollo Lab","Vizag");
        allPackaesItemArrayList.add(movie2);

        movie2 = new AllPackaesItem("HEART CHECK","120 Test Included","offer price : 3000 INR","discount Price : 4000 INR","Durga Lab","Vizag");
        allPackaesItemArrayList.add(movie2);

        movie2 = new AllPackaesItem("AGEWISE WOMEN HEALTH CHECK PACKAGES","80 Test Included","offer price : 4000 INR","discount Price : 5000 INR","Phadma Lab","Vizag");
        allPackaesItemArrayList.add(movie2);

        allPackagesAdapter.notifyDataSetChanged();
    }

    private void prepareRecommendData() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Apis.DIAGNOSTICS_RECOMMENDATION_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    // Parsing json object response
                    // response will be a json object
                    dianoRecomItemArrayList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("tests");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                        String categoryName = jsonObject1.optString("categoryName").toString();
                        String categoryId = jsonObject1.optString("categoryId").toString();
                        String testName = jsonObject1.optString("testName").toString();
                        String testId = jsonObject1.optString("testId").toString();

                        dianoRecomItemArrayList.add(new DianoRecomItem(categoryName, categoryId, testName,testId));

                    }

                    diagnosticRecommendationAdapter=new DiagnosticRecommendationAdapter(DiagnosticsActivity.this,dianoRecomItemArrayList);
                    MyRecyclerViewRecommendation.setAdapter(diagnosticRecommendationAdapter);

                    diagnosticRecommendationAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DiagnosticsActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                // hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               // VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(DiagnosticsActivity.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                // hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(DiagnosticsActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.filterButton:

                Intent moreIntent5 =new Intent(DiagnosticsActivity.this, CategoeryFilterActivity.class);
                moreIntent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(moreIntent5);

                break;
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
