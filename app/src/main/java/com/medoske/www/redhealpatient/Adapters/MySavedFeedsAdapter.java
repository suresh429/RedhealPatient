package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.SavedFeedItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 7.7.17.
 */

public class MySavedFeedsAdapter extends ArrayAdapter<SavedFeedItem> {

    private Context activity;
    public List<SavedFeedItem> parkingList;
    ArrayList<SavedFeedItem> arraylist;
    SavedFeedItem productItem;

    // constructor
    public MySavedFeedsAdapter(Context activity, int resource, List<SavedFeedItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<SavedFeedItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public SavedFeedItem getItem(int position) {
        return parkingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        productItem = parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.saved_fee_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDoctor.setText(productItem.getDoctorName());

        holder.txtcatageory.setText(productItem.getCategoryName());
        holder.txtcatageory.setBackgroundColor(Color.parseColor(productItem.getColor()));

        holder.txtTipTitle.setText(productItem.getTipName());

        //Loading Image from URL
        Picasso.with(getContext()).load(productItem.getDoctorImage()).placeholder(R.drawable.ic_action_placeholder)   // optional
                .into(holder.doctorImage);

        //Loading Image from URL
        Picasso.with(getContext()).load(productItem.getTipImage()).placeholder(R.drawable.feeds_image)   // optional
                .into(holder.tipImage);


        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView txtDoctor;
        public TextView txtcatageory;
        public TextView txtTipTitle;

        public CircleImageView doctorImage;
        public ImageView tipImage;

        public ViewHolder(View v) {
            txtDoctor = (TextView) v.findViewById(R.id.txtDoctorName);
            txtDoctor.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtcatageory = (TextView) v.findViewById(R.id.txtcatageory);
            txtcatageory.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtTipTitle = (TextView) v.findViewById(R.id.txtTipTitle);
            txtTipTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            doctorImage = (CircleImageView) v.findViewById(R.id.doctorImage);

            tipImage = (ImageView) v.findViewById(R.id.tipImage);



        }

    }
}
