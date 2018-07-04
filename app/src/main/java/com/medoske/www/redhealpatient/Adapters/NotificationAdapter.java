package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.ConfirmationAppointmentActivity;
import com.medoske.www.redhealpatient.DoctorNotifyDetailsActivity;
import com.medoske.www.redhealpatient.Items.NotificationListItem;
import com.medoske.www.redhealpatient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2.6.17.
 */

public class NotificationAdapter extends ArrayAdapter<NotificationListItem> {

    private Context activity;
    public List<NotificationListItem> parkingList;
    ArrayList<NotificationListItem> arraylist;


    // constructor
    public NotificationAdapter(Context activity, int resource, List<NotificationListItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<NotificationListItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public NotificationListItem getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final NotificationListItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.notification_list_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtShortMsg.setText(productItem.getShortmessage());
        holder.txtTitle.setText(productItem.getTitle());
        holder.txtLngMsg.setText(productItem.getLngmessage());



        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productItem.getAction().equals("appcallcenterpayment")){

                    // Create Notification
                    Intent intent = new Intent(getContext(), ConfirmationAppointmentActivity.class);
                    intent.putExtra("appointmentRefno",productItem.getReference());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                }
                else if (productItem.getAction().equals("doctorslist")){

                    // Create Notification
                    Intent intent1 = new Intent(getContext(), DoctorNotifyDetailsActivity.class);
                    intent1.putExtra("Refno",productItem.getReference());
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent1);
                }
            }
        });





        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView txtTitle;
        public TextView txtShortMsg;
        public TextView txtLngMsg;
        public LinearLayout view;

        public ViewHolder(View v) {
            txtTitle =(TextView)v.findViewById(R.id.txtTitle);
            txtTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtShortMsg=(TextView)v.findViewById(R.id.txtShortMsg);
            txtShortMsg.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtLngMsg=(TextView)v.findViewById(R.id.txtLngMsg);
            txtLngMsg.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            view =(LinearLayout)v.findViewById(R.id.view);


        }
    }

}
