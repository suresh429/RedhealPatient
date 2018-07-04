package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Items.SpecilizationItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by USER on 11.5.17.
 */

public class SpeclizationAdapter extends ArrayAdapter<SpecilizationItem> {

private Context activity;
public List<SpecilizationItem> parkingList;
        ArrayList<SpecilizationItem> arraylist;
        SpecilizationItem book;


// constructor
public SpeclizationAdapter(Context activity, int resource, List<SpecilizationItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<SpecilizationItem>();
        arraylist.addAll(parkingList);
        }

@Override
public int getCount() {
        return parkingList.size();
        }

@Override
public SpecilizationItem getItem(int position) {
        return parkingList.get(position);
        }
@Override
public long getItemId(int position) {
        return position;
        }

@Override
public View getView(final int position, View convertView, ViewGroup parent) {
// result for item get position
final SpecilizationItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

        // inflate UI from XML file
        convertView = inflater.inflate(R.layout.specailizationlist_item, parent, false);
        // get all UI view
        holder = new ViewHolder(convertView);
        // set tag for holder
        convertView.setTag(holder);


        } else {
        // if holder created, get tag from view
        holder = (ViewHolder) convertView.getTag();
        }

        holder.specialization.setText(productItem.getSpecialization());

        try {
                Picasso.with(getContext()).load(productItem.getImagepath()).into(holder.imageview);
        }catch (Exception e){
                e.printStackTrace();
        }


        return convertView;
        }


// View Holder
private class ViewHolder {
    public TextView specialization;
    public ImageView imageview;

    public ViewHolder(View v) {
        specialization=(TextView)v.findViewById(R.id.txtSpecialization);
        //specialization.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Regular.ttf"));
        imageview =(ImageView)v.findViewById(R.id.imgSpecilities);
    }
}

   /* // Filter for search by doctor name
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        parkingList.clear();
        if (charText.length() == 0) {
            parkingList.addAll(arraylist);

        }

        else
        {
            for (SpecilizationItem sp : arraylist)
            {
                if (sp.getSpecialization().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    parkingList.add(sp);

                }
            }
        }
        notifyDataSetChanged();
    }*/



}