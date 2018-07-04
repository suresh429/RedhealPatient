package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.MyFamilyItem;
import com.medoske.www.redhealpatient.MyFamilyActivity;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 7.7.17.
 */

public class MyFamilyAdapter extends ArrayAdapter<MyFamilyItem> {
    String URL;
    private Context activity;
    public List<MyFamilyItem> parkingList;
    ArrayList<MyFamilyItem> arraylist;
    MyFamilyItem productItem;

    // constructor
    public MyFamilyAdapter(Context activity, int resource, List<MyFamilyItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<MyFamilyItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public MyFamilyItem getItem(int position) {
        return parkingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // result for item get position
        productItem = parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.myfamily_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtPatientName.setText(productItem.getFullName());

        holder.txtPatientId.setText(productItem.getGender()+"  |  "+productItem.getRelation()+"\n"+productItem.getMobileNumber()+"\n"+productItem.getEmail());

       /* //Loading Image from URL
        Picasso.with(getContext()).load(productItem.getImagePath()).placeholder(R.drawable.ic_action_placeholder)   // optional
                .into(holder.patientImage);*/

        if (productItem.getImagePath().isEmpty()) {
            holder.patientImage.setImageResource(R.drawable.ic_action_placeholder);
        } else{
            Picasso.with(getContext()).load(productItem.getImagePath())  // optional
                    .into(holder.patientImage);
        }

        // To retrieve value from shared preference in another activity
        SharedPreferences sp =getContext().getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        URL =Apis.ADD_RELATION_URL+redHealId+"/";

        holder.overFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate menu
                PopupMenu popup = new PopupMenu(parent.getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_delete, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.action_delete:

                                String deleteId =  getItem(position).getRelation_redhealId();
                                Log.e("delet",""+deleteId);

                                new DoDeleteProfile().execute(deleteId);
                                arraylist.remove(position);
                                notifyDataSetChanged();


                                Intent intent =new Intent(getContext(),MyFamilyActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                getContext().startActivity(intent);

                                //Toast.makeText(getContext(), "delete"+position, Toast.LENGTH_SHORT).show();

                                return true;

                            default:
                        }


                        return false;
                    }
                });
                popup.show();
            }
        });


        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView txtPatientName;
        public TextView txtPatientId;
        public ImageView overFlow;
        public CircleImageView patientImage;

        public ViewHolder(View v) {
            txtPatientName = (TextView) v.findViewById(R.id.textViewPatientName);
            txtPatientName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtPatientId = (TextView) v.findViewById(R.id.textViewId);
            txtPatientId.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            patientImage = (CircleImageView) v.findViewById(R.id.patientImage);

            overFlow =(ImageView)v.findViewById(R.id.imageDots);

        }

    }


    class DoDeleteProfile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            //http://dev.superman-academy.com/api.php/10
            String id = params[0];

            Log.e("stringid",""+id);

            try {

                URL url = new URL(URL+id);
                Log.e("deleteurl",""+url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("DELETE");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                InputStream is = httpURLConnection.getInputStream();
                int byteCharacter;
                String result = "";
                while ((byteCharacter = is.read()) != -1) {
                    result += (char) byteCharacter;
                }
                Log.d("json api", result);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
