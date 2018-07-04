package com.medoske.www.redhealpatient.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.AboutUsActivity;
import com.medoske.www.redhealpatient.Adapters.BestPackageAdapter;
import com.medoske.www.redhealpatient.AllBestPackagesActivity;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.AppointmentsActivity;
import com.medoske.www.redhealpatient.EditProfileActivity;
import com.medoske.www.redhealpatient.FeedBackActivity;
import com.medoske.www.redhealpatient.Items.BestPackageItem;
import com.medoske.www.redhealpatient.LoginActivity;
import com.medoske.www.redhealpatient.MyDoctorsActivity;
import com.medoske.www.redhealpatient.MyFamilyActivity;
import com.medoske.www.redhealpatient.MyLabRecordsActivity;
import com.medoske.www.redhealpatient.MyPackagesActivity;
import com.medoske.www.redhealpatient.MyRecordsActivity;
import com.medoske.www.redhealpatient.MySavedFeedsActivity;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.SettingsActivity;
import com.medoske.www.redhealpatient.Utilities.JSONParser;
import com.medoske.www.redhealpatient.Utilities.Session;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Session session;
    ArrayList<BestPackageItem> packageItems = new ArrayList<BestPackageItem>();
    BestPackageAdapter packageAdapter;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ListView listView;
    String URL;

    TextView txtAppointments,txtPackages,txtSavedFeeds,txtMyFamily,txtMyDoctors,txtMyRecords,txtMyLabReports,txtEdit,txtviewAll;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  mPageNo1 = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String patientRhid = sp.getString("patientRedhealId", "1");
        String patientName= sp.getString("firstName", "1");
        String patientMobile = sp.getString("mobileno", "1");


        sp = getContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String latitude = sp.getString("presentLatitude", "1");
        String longitude = sp.getString("presentLongitude", "1");

        Toast.makeText(getContext(),""+latitude+"\n"+longitude,Toast.LENGTH_LONG).show();

        session = new Session(getContext());
        if(!session.loggedin()){
            logout();
        }

        TextView txtPatientName =(TextView)view.findViewById(R.id.textViewPatientName);
        txtPatientName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));
        txtPatientName.setText(patientName);

        TextView txtMobile=(TextView)view.findViewById(R.id.textViewMobile);
        txtMobile.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));
        txtMobile.setText(patientMobile);

        TextView txtRhid =(TextView)view.findViewById(R.id.textViewRhId);
        txtRhid.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));
        txtRhid.setText(patientRhid);




       // String doctorId = getContext().getIntent().getExtras().getString("doctorId");
        URL = Apis.BEST_PACKAGES_URL+latitude+"/"+longitude;
        Log.e("bestpackage",""+URL);

        listView =(ListView)view.findViewById(R.id.listPackages1);

        // intent to appointments
        txtAppointments=(TextView)view.findViewById(R.id.textViewAppointments);
        txtAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(),AppointmentsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i);
            }
        });

        txtPackages=(TextView)view.findViewById(R.id.textViewPackages);
        txtPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 =new Intent(getContext(),MyPackagesActivity.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i1);
            }
        });

        txtSavedFeeds=(TextView)view.findViewById(R.id.textViewSavedFeeds);
        txtSavedFeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(getContext(),MySavedFeedsActivity.class);
                i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i2);
            }
        });

        txtMyFamily=(TextView)view.findViewById(R.id.textViewMyFamily);
        txtMyFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i3 =new Intent(getContext(),MyFamilyActivity.class);
                i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i3);
            }
        });

        txtMyDoctors=(TextView)view.findViewById(R.id.textViewMyDoctors);
        txtMyDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i4 =new Intent(getContext(),MyDoctorsActivity.class);
                i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i4);
            }
        });

        txtMyRecords=(TextView)view.findViewById(R.id.textViewMyRecords) ;
        txtMyRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i5 =new Intent(getContext(),MyRecordsActivity.class);
                i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i5);

            }
        });

        txtMyLabReports=(TextView)view.findViewById(R.id.textViewMyLabRecords);
        txtMyLabReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i6 =new Intent(getContext(),MyLabRecordsActivity.class);
                i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i6);
            }
        });

        txtEdit=(TextView)view.findViewById(R.id.textEdit);
        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i7 =new Intent(getContext(),EditProfileActivity.class);
                i7.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i7);
            }
        });

        txtviewAll =(TextView)view.findViewById(R.id.viewAll);
        txtviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i8 =new Intent(getContext(),AllBestPackagesActivity.class);
                i8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i8);
            }
        });



        new AboutMeDataList().execute();

        return view;
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

            Log.e("BEST_URL", URL);

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

                JSONArray jsonArray2 = json.optJSONArray("packages_list");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray2.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        String fullName1 = jsonObject2.optString("fullName").toString();
                        String doctor_redhealId1= jsonObject2.optString("doctor_redhealId").toString();
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
                        String clinic_redhealId = jsonObject2.optString("clinic_redhealId").toString();
                        String clinicName = jsonObject2.optString("clinicName").toString();
                        String address = jsonObject2.optString("address").toString();
                        String distance = jsonObject2.optString("distance").toString();
                        String doctorImage = jsonObject2.optString("imagePath").toString();
                        String speclization = jsonObject2.optString("specialization").toString();

                        packageItems.add(new BestPackageItem(fullName1,doctor_redhealId1,packageName,packageId,actualPrice,discountPrice,packageImage,sittings,period,fromTime,toTime,description1,clinic_redhealId,clinicName,address,distance,doctorImage,speclization));
                    }


                    if(packageItems.size()>0)
                    {
                        packageAdapter = new BestPackageAdapter(getContext(), R.layout.recom_package_item, packageItems);
                        listView.setItemsCanFocus(false);
                        listView.setAdapter(packageAdapter);
                       // ListUtils.setDynamicHeight(listView);

                    }
                    else
                    {
                        Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();

                    }

                }
                else {

                    Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();

                }



                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}



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

    private void logout(){
        session.setLoggedin(false);
        getActivity().finish();
        startActivity(new Intent(getContext(),LoginActivity.class));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * react to the user tapping/selecting an options menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.navigation_settings:
                // TODO put your code here to respond to the button tap

                Intent settings =new Intent(getContext(),SettingsActivity.class);
                settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(settings);

                return true;

            case R.id.navigation_feedback:
                // TODO put your code here to respond to the button tap
                Intent feedback =new Intent(getContext(),FeedBackActivity.class);
                feedback.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(feedback);
                return true;

            case R.id.navigation_about:
                // TODO put your code here to respond to the button tap
                Intent about =new Intent(getContext(),AboutUsActivity.class);
                about.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(about);
                return true;

            case R.id.navigation_signout:
                // TODO put your code here to respond to the button tap
                /*SharedPreferences.Editor editor = getActivity().getSharedPreferences("LOGIN_PREF",getContext().MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();


                if (editor.commit()) {
                    Toast.makeText(getContext(), "Logout Successfully", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getContext(), LoginActivity.class));//open login activity on successful logout
                }*/

                logout();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
