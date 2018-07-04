package com.medoske.www.redhealpatient.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.PackagesListActivity;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutMeFragment extends Fragment {
    private static final int MAX_LINES =4;

    TextView txtPackages,txtEducation,txtService,txtAwards,txtMembership,viewMore;

    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();

    String URL;
    View rootView;
    RelativeLayout relativeLayout;

    public AboutMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_about_me, container, false);

        String doctorId = getActivity().getIntent().getExtras().getString("doctorId");
        URL = Apis.ABOUT_ME_URL+doctorId;

        txtPackages =(TextView)rootView.findViewById(R.id.listPackages) ;
        txtEducation =(TextView)rootView.findViewById(R.id.textViewEducation) ;
        txtService =(TextView)rootView.findViewById(R.id.textViewService) ;
        txtAwards =(TextView)rootView.findViewById(R.id.textViewAward) ;
        txtMembership =(TextView)rootView.findViewById(R.id.textViewMembership) ;
        viewMore =(TextView)rootView.findViewById(R.id.viewmore) ;

         relativeLayout =(RelativeLayout)rootView.findViewById(R.id.packageLayout);

        //listView =(ListView)rootView .findViewById(R.id.listPackages);

        new AboutMeDataList().execute();

        return rootView;
    }
    public class AboutMeDataList extends AsyncTask<String, String, JSONObject> {

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

            Log.e("ABOUT_ME_URL", URL);

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
               JSONArray jsonArray = json.optJSONArray("doctor_profile");



                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                    final String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                    final String fullName = jsonObject.optString("fullName").toString();
                    String description = jsonObject.optString("description").toString();
                    String education = jsonObject.optString("education").toString();
                    String service = jsonObject.optString("service").toString();
                    String membership = jsonObject.optString("membership").toString();
                    String awards = jsonObject.optString("awards").toString();
                    final String doctorImage = jsonObject.optString("imagePath").toString();
                    final String specialization = jsonObject.optString("specialization").toString();

                    /*txtDescription.setText(description);
                    ResizableCustomView.doResizeTextView(txtDescription, MAX_LINES, "View More", true);
                    txtDescription.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Roboto-Regular.ttf"));*/

                    txtEducation.setText(education);
                    txtEducation.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));

                    txtService.setText(service);
                    txtService.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));

                    txtMembership.setText(membership);
                    txtMembership.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));

                    txtAwards.setText(awards);
                    txtAwards.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));


                }

                JSONArray jsonArray2 = json.optJSONArray("doctor_package");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        String fullName1 = jsonObject2.optString("fullName").toString();
                        final String doctor_redhealId1= jsonObject2.optString("doctor_redhealId").toString();
                        String packageName = jsonObject2.optString("packageName").toString();
                        Log.e("packageName123",""+packageName);
                        String packageId = jsonObject2.optString("packageId").toString();
                        String actualPrice = jsonObject2.optString("actualPrice").toString();
                        String discountPrice = jsonObject2.optString("discountPrice").toString();
                        String packageImage = jsonObject2.optString("packageImage").toString();

                        String sittings = jsonObject2.optString("sittings").toString();
                        String period = jsonObject2.optString("period").toString();
                        String fromTime = jsonObject2.optString("fromTime").toString();
                        String toTime = jsonObject2.optString("toTime").toString();
                        String description1 = jsonObject2.optString("description").toString();



                        txtPackages.setText(packageName);
                        txtPackages.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));

                        if(jsonObject2.isNull("packageName") || jsonObject2.get("packageName").equals("")) {
                           relativeLayout.setVisibility(View.GONE);
                       }else {
                           relativeLayout.setVisibility(View.VISIBLE);
                       }

                        viewMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(getContext(),PackagesListActivity.class);
                                intent.putExtra("doctorId",doctor_redhealId1);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                        // packageItems.add(new PackageItem(fullName1,doctor_redhealId1,packageName,packageId,actualPrice,discountPrice,packageImage,sittings,period,fromTime,toTime,description1));
                    }

                }
                else {

                    Toast.makeText(getActivity(), "no data found", Toast.LENGTH_SHORT).show();


                }



                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}


            /*if(packageItems.size()>0)
            {
                packageAdapter = new PackageAdapter(getContext(), R.layout.package_list_item, packageItems);
                listView.setItemsCanFocus(false);
                listView.setAdapter(packageAdapter);
                ListUtils.setDynamicHeight(listView);
                *//*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        *//**//*DoctorsListItem doctorsListItem = (DoctorsListItem) listView.getItemAtPosition(position);

                        Intent intent =new Intent(DoctorsListActivity.this,DoctorsProfileActivity.class);
                        intent.putExtra("doctorName",doctorsListItem.getDoctorName());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);*//**//*

                        Toast.makeText(SelectClinicACtivity.this, "no data found", Toast.LENGTH_SHORT).show();

                    }
                });*//*

            }
            else
            {
                Toast.makeText(getActivity(), "no data found", Toast.LENGTH_SHORT).show();
                final RelativeLayout relativeLayout =(RelativeLayout)rootView.findViewById(R.id.packageLayout);
                relativeLayout.setVisibility(View.GONE);
            }*/
        }
    }


    // ListUtils for 2 or more list views in same activity
    static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

}
