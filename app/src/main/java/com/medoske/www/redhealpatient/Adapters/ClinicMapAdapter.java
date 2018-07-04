package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Items.SelectClinicItem;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.TimeSlotActivity;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by USER on 28.6.17.
 */

public class ClinicMapAdapter extends ArrayAdapter<SelectClinicItem> {

    private Context activity;
    public List<SelectClinicItem> parkingList;
    ArrayList<SelectClinicItem> arraylist;
    SelectClinicItem book;


    // constructor
    public ClinicMapAdapter(Context activity, int resource, List<SelectClinicItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<SelectClinicItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public SelectClinicItem getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final SelectClinicItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        final LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.clinic_map_list_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);




        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }
        holder.clinicName.setText(productItem.getClinicName());
        Log.e("clinicName123",""+productItem.getClinicName());
        holder.area.setText(productItem.getClinicAddress());
        holder.fee.setText("ConsultationFee : "+productItem.getConsultationFee());
        holder.instantFee.setText("InstantConsultationFee : "+productItem.getInstantConsultationFee());

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), TimeSlotActivity.class);
                intent.putExtra("doctorImage",productItem.getDoctorImage());
                intent.putExtra("clinicId",productItem.getClinic_redhealId());
                intent.putExtra("doctorId",productItem.getDoctor_redhealId());
                intent.putExtra("clinicName",productItem.getClinicName());
                intent.putExtra("doctorName",productItem.getDoctorName());
                intent.putExtra("speclization",productItem.getDoctorSpeclization());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);
            }
        });

        double latitude = Double.parseDouble(productItem.getLatitude());
        double longitude = Double.parseDouble(productItem.getLongitude());

       // String URL ="http://maps.google.com/maps/api/staticmap?center=48.858235,%202.294571&zoom=15&size=500x200&sensor=false";
        String URL ="http://medoske.com/rhdoctor/welcome/test?lat="+productItem.getLatitude()+"&lan="+productItem.getLongitude();
        Log.e("webUrl",""+URL);

        holder.webView.loadUrl(URL);
        holder.directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo:"+productItem.getLatitude()+","+productItem.getLongitude()));
                // i.setData(Uri.parse("geo:<lat>,<long>?q=<lat>,<long>"));
                getContext().startActivity(i);
                Toast.makeText(activity, "click", Toast.LENGTH_SHORT).show();

            }
        });

      //  Picasso.with(getContext()).load(URL).into(holder.imageView);

        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView clinicName;
        public TextView area;
        public TextView fee;
        public TextView instantFee;
        public TextView directions;
        public WebView webView;
        RelativeLayout webLayout;
        public Button select;



        public ViewHolder(View v) {
            clinicName=(TextView)v.findViewById(R.id.textViewClinicName);
            clinicName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            area=(TextView)v.findViewById(R.id.textViewAddress);
            area.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            fee=(TextView)v.findViewById(R.id.textViewFee);
            fee.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            instantFee=(TextView)v.findViewById(R.id.textViewInstantFee);
            instantFee.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            directions=(TextView)v.findViewById(R.id.textDirections);

            select=(Button)v.findViewById(R.id.button_Select);
            webView=(WebView)v.findViewById(R.id.webView);
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setFocusable(false);
            webView.setClickable(false);

           // imageView=(ImageView) v.findViewById(R.id.webView);
            webLayout=(RelativeLayout) v.findViewById(R.id.webLayout);










        }


    }




}



