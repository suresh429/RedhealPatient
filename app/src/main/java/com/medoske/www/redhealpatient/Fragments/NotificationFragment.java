package com.medoske.www.redhealpatient.Fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.medoske.www.redhealpatient.Adapters.InfiniteCyclerAdapter;
import com.medoske.www.redhealpatient.Adapters.NotificationAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.DoctorNotifyDetailsActivity;
import com.medoske.www.redhealpatient.Items.NotificationDoctorItem;
import com.medoske.www.redhealpatient.Items.NotificationListItem;
import com.medoske.www.redhealpatient.Items.SpecilizationItem;
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
public class NotificationFragment extends Fragment {

    View rootView;
    ListView listView;
    NotificationAdapter notificationAdapter;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ArrayList<NotificationListItem> notificationListItemArrayList = new ArrayList<NotificationListItem>();
    JSONArray jsonArray;
    String redHealId,URL;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        URL = Apis.ALL_NOTIFICATION_URL+redHealId;
        Log.e("url",""+URL);

        listView=(ListView)rootView.findViewById(R.id.listNotifications);


        prepareListData();
        return rootView;
    }

    private void prepareListData() {

        //Showing a progress dialog
        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("response",""+response);

                //Dismissing progress dialog
                pDialog.hide();
                try {
                    JSONArray jsonArray = response.getJSONArray("appointment");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String imagetype = jsonObject.optString("imagetype").toString();
                        String imagePath = jsonObject.optString("imagePath").toString();
                        String title = jsonObject.optString("title").toString();
                        String shortmessage = jsonObject.optString("shortmessage").toString();
                        String lngmessage = jsonObject.optString("lngmessage").toString();
                        String action = jsonObject.optString("action").toString();
                        String reference = jsonObject.optString("reference").toString();

                        notificationListItemArrayList.add(new NotificationListItem(imagetype,imagePath,title,shortmessage,lngmessage,action,reference));

                    }

                    notificationAdapter=new NotificationAdapter(getActivity(),R.layout.notification_list_item,notificationListItemArrayList);
                    listView.setAdapter(notificationAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
                pDialog.hide();
            }
        });

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(jsonObjReq);

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(jsonObjReq);


}
}
