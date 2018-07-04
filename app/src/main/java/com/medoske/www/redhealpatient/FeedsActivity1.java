package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.medoske.www.redhealpatient.Adapters.AllFeedsAdapter;
import com.medoske.www.redhealpatient.Adapters.FeedsAdpter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Fragments.FeedsFragment;
import com.medoske.www.redhealpatient.Fragments.SearchFragment;
import com.medoske.www.redhealpatient.Items.FeedsItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedsActivity1 extends AppCompatActivity {
    JSONArray jsonArray;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL ;
    private RecyclerView mRecyclerView;

    private List<FeedsItem> feedsItemArrayList =new ArrayList<>();
    private AllFeedsAdapter mAdapter;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feeds");

        ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(FeedsActivity1.this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(FeedsActivity1.this, config);

        mRecyclerView = (RecyclerView)findViewById(R.id.list);

        searchView = (SearchView)findViewById(R.id.search_Tips);

        loadSearchView();
        new ClinicDataList().execute();
    }

    public class ClinicDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(FeedsActivity1.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Set the dialog title to 'Loading...'
            // progressDialog.setTitle("Chargement...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Fetching ...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(true);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... params) {

            //Getting product details from making HTTP Request
            JSONObject json = jsonParser.makeHttpRequest(Apis.ALL_FEEDS_URL, "GET", param);

            Log.e("FEEDS_LIST_URL", Apis.ALL_FEEDS_URL);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

            progressDialog.dismiss();

            Log.e("data", String.valueOf(json));

            String data = "";
            try {

                //Get the instance of JSONArray that contains JSONObjects
                jsonArray = json.optJSONArray("all_feeds");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String doctorName = jsonObject.optString("doctorName").toString();
                    String specialization = jsonObject.optString("specialization").toString();
                    String doctorImage = jsonObject.optString("doctorImage").toString();
                    String tip_date = jsonObject.optString("tip_date").toString();
                    String tip_time = jsonObject.optString("tip_time").toString();
                    String description = jsonObject.optString("description").toString();
                    String tipName = jsonObject.optString("tipName").toString();
                    String imagePath = jsonObject.optString("imagePath").toString();
                    String categoryName = jsonObject.optString("categoryName").toString();
                    String id = jsonObject.optString("id").toString();
                    String categoryId = jsonObject.optString("categoryId").toString();
                    String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                    String color = jsonObject.optString("color").toString();
                    String like_status = jsonObject.optString("like_status").toString();

                   /* try{
                         intColor = Integer.parseInt(color);
                    }catch(NumberFormatException ex){ // handle your exception

                    }*/


                    feedsItemArrayList.add(new FeedsItem(doctorName,specialization,doctorImage,tip_date,tip_time,description,tipName,imagePath,categoryName,id,categoryId,doctor_redhealId,color,like_status));

                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}

            if(feedsItemArrayList.size()>0)
            {

                mAdapter = new AllFeedsAdapter(FeedsActivity1.this, feedsItemArrayList );

                LinearLayoutManager llm = new LinearLayoutManager(FeedsActivity1.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(llm);
                mRecyclerView.setAdapter( mAdapter );
                // mRecyclerView.setItemAnimator(new FiltersListItemAnimator());

            }
            else
            {
                Toast.makeText(FeedsActivity1.this, "no data found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    // load searchView
    private void loadSearchView() {
        // Do all of your work here

        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getContext(),"toasttwo",Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {

                mAdapter.filter(searchQuery.toString().trim());
                mRecyclerView.invalidate();
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

     /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feeds, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        /*//**//**//*//**//**//**//**//**//**//**//*** setOnQueryTextFocusChangeListener ***
     searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
    });

     searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

    @Override
    public boolean onQueryTextSubmit(String query) {

    return false;
    }

    @Override
    public boolean onQueryTextChange(String searchQuery) {
        mAdapter.filter(searchQuery.toString().trim());
        mRecyclerView.invalidate();
    return true;
    }
    });

     MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
    // Do something when collapsed
    return true;  // Return true to collapse action view
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
    // Do something when expanded
    return true;  // Return true to expand action view
    }
    });
     return true;
     }
*/



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
