package com.medoske.www.redhealpatient.Fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.medoske.www.redhealpatient.Adapters.FeedsAdpter;
import com.medoske.www.redhealpatient.Adapters.QuestionsAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.FeedsItem;
import com.medoske.www.redhealpatient.Items.Question;
import com.medoske.www.redhealpatient.Items.Tag;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.Utilities.JSONParser;
import com.yalantis.filter.adapter.FilterAdapter;
import com.yalantis.filter.animator.FiltersListItemAnimator;
import com.yalantis.filter.listener.FilterListener;
import com.yalantis.filter.widget.Filter;
import com.yalantis.filter.widget.FilterItem;

import org.apache.http.NameValuePair;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedsFragment extends Fragment {
    int intColor;
    JSONArray jsonArray;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL ;
    private RecyclerView mRecyclerView;

    private List<FeedsItem> feedsItemArrayList =new ArrayList<>();
    private FeedsAdpter mAdapter;
    View rootView;
    public FeedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_feeds, container, false);

        String doctorId = getActivity().getIntent().getExtras().getString("doctorId");

        URL = Apis.FEEDS_LIST_URL+doctorId;

        ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(getContext())
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(getContext(), config);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);


        new ClinicDataList().execute();

        return rootView;
    }



    public class ClinicDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(getContext());
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
            JSONObject json = jsonParser.makeHttpRequest(URL, "GET", param);

            Log.e("DOCTORS_LIST_URL", URL);

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
                jsonArray = json.optJSONArray("doctor_feeds");

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

                mAdapter = new FeedsAdpter(getContext(), feedsItemArrayList );

                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(llm);
                mRecyclerView.setAdapter( mAdapter );
               // mRecyclerView.setItemAnimator(new FiltersListItemAnimator());

            }
            else
            {
                Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
