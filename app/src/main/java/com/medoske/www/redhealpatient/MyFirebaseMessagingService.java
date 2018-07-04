package com.medoske.www.redhealpatient;

/*
 * Copyright (C) 2015, francesco Azzola
 *
 *(http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.medoske.www.redhealpatient.BottomMenuActivity;
import com.medoske.www.redhealpatient.R;

import org.json.JSONObject;

/**
 * Created by francesco on 13/09/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    PendingIntent pendingIntent;
    String remoteData,remoteReferenceNo;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
               // sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        //Log.e("Msg", "Message received ["+remoteMessage+"]");
        Log.e("Msg121313", "Message received ["+remoteMessage.getData().get("action")+"]");

        remoteData= remoteMessage.getData().get("action");
        Log.e("msgremotedata",""+remoteData);

        remoteReferenceNo =remoteMessage.getData().get("reference");
        Log.e("msgReference",""+remoteReferenceNo);

        if (remoteData.equals("appcallcenterpayment")){

            // Create Notification
            Intent intent = new Intent(this, ConfirmationAppointmentActivity.class);
            intent.putExtra("appointmentRefno",remoteReferenceNo);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 1410, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        }
        else if (remoteData.equals("doctorslist")){

            // Create Notification
            Intent intent1 = new Intent(this, DoctorNotifyDetailsActivity.class);
            intent1.putExtra("Refno",remoteReferenceNo);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 1410, intent1,
                    PendingIntent.FLAG_ONE_SHOT);
        }



        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        Log.e("title",""+remoteMessage.getNotification().getTitle());

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1410, notificationBuilder.build());
    }
}
