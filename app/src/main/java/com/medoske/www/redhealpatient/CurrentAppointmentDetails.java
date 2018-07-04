package com.medoske.www.redhealpatient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentAppointmentDetails extends AppCompatActivity {
 Button buttonDirections,buttonCancel;
    TextView textPatientName,textRhId,textMobile,textDoctorName,textSpeclization,textDateTime;
    CircleImageView patientImageView,doctorImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_appointment_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Current Appointment Details");

        String doctorName = getIntent().getExtras().getString("doctorName");
        String doctorId = getIntent().getExtras().getString("doctorId");
        String doctorImage = getIntent().getExtras().getString("doctorImage");
        String clinicName = getIntent().getExtras().getString("clinicName");
        String date = getIntent().getExtras().getString("date");
        String time = getIntent().getExtras().getString("time");
        String speclization = getIntent().getExtras().getString("speclization");
        String address = getIntent().getExtras().getString("address");
        String patientName = getIntent().getExtras().getString("patientName");
        String patientImage = getIntent().getExtras().getString("patientImage");
        String patientId = getIntent().getExtras().getString("patientId");
        String mobileNo = getIntent().getExtras().getString("mobileNo");
        String paymentMode = getIntent().getExtras().getString("paymentMode");
        final String appointmentRefId = getIntent().getExtras().getString("appointmentRefId");
        final String destinationLatitude = getIntent().getExtras().getString("latitude");
        final String destinationLongitude = getIntent().getExtras().getString("longitude");

        final  String sourceLatitude ="17.7307";
        final  String sourceLongitude ="83.3087";

        buttonDirections=(Button)findViewById(R.id.buttonDirections);
        buttonDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        buttonCancel=(Button)findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newIntent =new Intent(CurrentAppointmentDetails.this,CancelAppointmentActivity.class);
                newIntent.putExtra("appointmentRefId",appointmentRefId);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newIntent);

               /* final View checkBoxView = View.inflate(CurrentAppointmentDetails.this, R.layout.checkbox, null);
                final CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        // Save to shared preferences
                    }
                });

                checkBox.setText("Accept the Terms & Conditions.");


                AlertDialog.Builder builder =new AlertDialog.Builder(CurrentAppointmentDetails.this);
                builder.setMessage("Please confirm that you are about to be diverted to a Call center ?")
                        .setView(checkBoxView)
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getContext(), "clicked suresh", Toast.LENGTH_SHORT).show();

                                if(checkBox.isChecked()){
                                    //do some validation
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:+91720999666"));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(intent);
                                }else {

                                    Toast.makeText(CurrentAppointmentDetails.this, "Please Accept the Terms & Conditions.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        })
                        .setNegativeButton("NO",null);
                AlertDialog alert =builder.create();
                alert.show();*/
            }
        });

        textPatientName=(TextView)findViewById(R.id.textViewCenterName);
        textPatientName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        textPatientName.setText(patientName);

        textRhId=(TextView)findViewById(R.id.textSpeclization);
        textRhId.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
        textRhId.setText(patientId);

        textMobile=(TextView)findViewById(R.id.textExp);
        textMobile.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
        textMobile.setText(mobileNo);

        textDoctorName=(TextView)findViewById(R.id.textPatientNae1);
        textDoctorName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        textDoctorName.setText(doctorName);

        textSpeclization=(TextView)findViewById(R.id.textRhid1);
        textSpeclization.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
        textSpeclization.setText(speclization);

        textDateTime=(TextView)findViewById(R.id.textDateTime);
        textDateTime.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
        textDateTime.setText("on "+date+" & "+time+"\n"+"at "+address);

        doctorImageView=(CircleImageView)findViewById(R.id.patientImage1) ;
        patientImageView=(CircleImageView)findViewById(R.id.labreportImage) ;

        Picasso.with(getApplicationContext()).load(doctorImage).placeholder(R.drawable.ic_action_placeholder).into(doctorImageView);
        //Picasso.with(getApplicationContext()).load(patientImage).placeholder(R.drawable.ic_action_placeholder).into(patientImageView);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
