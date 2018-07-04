package com.medoske.www.redhealpatient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.medoske.www.redhealpatient.Adapters.BrandAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.CategoryItem;
import com.medoske.www.redhealpatient.Items.Mobile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoeryFilterActivity extends AppCompatActivity {

    private ExpandableListView mBrandsListView;
    private ArrayList<CategoryItem> brands = new ArrayList<CategoryItem>();

    private ExpandableListAdapter mBrandAdapter;

    List<String> selectedId  = new ArrayList<String>();

    Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoery_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Filter");

        btnProceed=(Button)findViewById(R.id.button_proceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedId  = new ArrayList<String>();

                Intent intent = new Intent(getApplicationContext(), DiagnosticsActivity.class);
                intent.putStringArrayListExtra("checkedContacts", (ArrayList<String>) selectedId);
                startActivity(intent);
            }
        });



        mBrandsListView = (ExpandableListView) findViewById(R.id.list_brands);

        categoeryData();


    }

    private void categoeryData() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Apis.CATEGOERY_LIST_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.e("URL",""+Apis.CATEGOERY_LIST_URL);


                try {
                    // Parsing json object response
                    // response will be a json object
                    brands.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("categories");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);


                        String categoryId = jsonObject1.optString("categoryId").toString();
                        String categoryName = jsonObject1.optString("categoryName").toString();


                        // Genre is json array
                        JSONArray genreArry = jsonObject1.getJSONArray("tests");
                        ArrayList<Mobile> mobileArrayList = new ArrayList<Mobile>();

                        for (int j = 0; j < genreArry.length(); j++) {
                           // genre.add((String) genreArry.get(j));
                            JSONObject perResult = genreArry.getJSONObject(j);
                            String testId =perResult.getString("testId");
                            String testName =perResult.getString("testName");

                            mobileArrayList.add(new Mobile(testId,testName));

                            Log.e("testName",""+testName);
                        }


                        // adding movie to movies array
                       brands.add(new CategoryItem(categoryId,categoryName,mobileArrayList));



                       // categoryItemArrayList.add(new CategoryItem( categoryId,categoryName,tests));

                    }

                    mBrandAdapter = new BrandAdapter(CategoeryFilterActivity.this, brands);
                    mBrandsListView.setAdapter(mBrandAdapter);

                    mBrandsListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                        int previousGroup = -1;

                        @Override
                        public void onGroupExpand(int groupPosition) {
                            if ((previousGroup != -1) && (groupPosition != previousGroup)) {
                               // mBrandsListView.collapseGroup(previousGroup);
                            }
                            previousGroup = groupPosition;
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CategoeryFilterActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                // hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(CategoeryFilterActivity.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                // hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(CategoeryFilterActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);

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
