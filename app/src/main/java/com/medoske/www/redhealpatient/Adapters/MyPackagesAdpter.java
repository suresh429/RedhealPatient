package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.MyPackaeItem;
import com.medoske.www.redhealpatient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 6.7.17.
 */

public class MyPackagesAdpter extends ArrayAdapter<MyPackaeItem> {

    private Context activity;
    public List<MyPackaeItem> parkingList;
    ArrayList<MyPackaeItem> arraylist;
    MyPackaeItem book;


    // constructor
    public MyPackagesAdpter(Context activity, int resource, List<MyPackaeItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<MyPackaeItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public MyPackaeItem getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final MyPackaeItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        final LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.mypackages_list_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);




        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }
        holder.packageName.setText(productItem.getPackageName());
        Log.e("clinicName123",""+productItem.getPackageName());

        holder.doctorName.setText("With "+productItem.getDoctorName()+" Expires On "+productItem.getToTime());

        holder.status.setText(productItem.getStatus());

        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView packageName;
        public TextView doctorName;
        public TextView status;


        public ViewHolder(View v) {
            packageName=(TextView)v.findViewById(R.id.textPackageName);
            packageName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            doctorName=(TextView)v.findViewById(R.id.textPatientNae1);
            doctorName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            status=(TextView)v.findViewById(R.id.textStatus);
            status.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

        }


    }




}
