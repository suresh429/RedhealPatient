package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.BookPackageDetailsActivity;
import com.medoske.www.redhealpatient.Items.RecomendPackageItem;
import com.medoske.www.redhealpatient.PackageTimeSlotsActivity;
import com.medoske.www.redhealpatient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 4.7.17.
 */

public class RecomPackageAdapter extends ArrayAdapter<RecomendPackageItem> {

    private Context activity;
    public List<RecomendPackageItem> parkingList;
    ArrayList<RecomendPackageItem> arraylist;
    RecomendPackageItem book;


    // constructor
    public RecomPackageAdapter(Context activity, int resource, List<RecomendPackageItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<RecomendPackageItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public RecomendPackageItem getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final RecomendPackageItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        final LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.recom_package_item, parent, false);
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

        holder.actualPrice.setText("Actual Price :"+productItem.getActualPrice());
        holder.actualPrice.setPaintFlags(holder.actualPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        holder.discountPrice.setText("Discount Price:"+productItem.getDiscountPrice());

       // holder.distance.setText(productItem.getDistance());

        holder.doctorName.setText(productItem.getFullName1()+"\n"+productItem.getClinicName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getContext(),BookPackageDetailsActivity.class);
                intent.putExtra("packageId",productItem.getPackageId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);
            }
        });

        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i8 =new Intent(getContext(),PackageTimeSlotsActivity.class);
                i8.putExtra("clinicId",productItem.getClinic_redhealId());
                i8.putExtra("doctorId",productItem.getDoctor_redhealId1());
                i8.putExtra("doctorImage",productItem.getDoctorImage());
                i8.putExtra("clinicName",productItem.getClinicName());
                i8.putExtra("doctorName",productItem.getFullName1());
                i8.putExtra("speclization",productItem.getSpeclization());
                i8.putExtra("packageId",productItem.getPackageId());
                i8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(i8);
            }
        });
        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView packageName;
        public TextView actualPrice;
        public TextView discountPrice;
        public TextView doctorName;
        public TextView distance;
        public LinearLayout linearLayout;
        public Button bookButton;

        public ViewHolder(View v) {
            packageName=(TextView)v.findViewById(R.id.txtPackageName);
            packageName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            actualPrice=(TextView)v.findViewById(R.id.txtActulPrice);
            actualPrice.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            discountPrice=(TextView)v.findViewById(R.id.txtPriceDiscount);
            discountPrice.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            doctorName=(TextView)v.findViewById(R.id.txtDoctorName);
            doctorName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            /*distance=(TextView)v.findViewById(R.id.txtDistance);
            distance.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Regular.ttf"));*/

            linearLayout=(LinearLayout)v.findViewById(R.id.layoutDetails);

            bookButton=(Button)v.findViewById(R.id.buttonBook);



        }


    }




}

