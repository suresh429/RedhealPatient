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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.medoske.www.redhealpatient.R;

/**
 * Created by francesco on 13/09/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
public static final String TOKEN_BROADCAST ="myfcmtockenbroadcast";
    @Override
    public void onTokenRefresh() {
        String tkn = FirebaseInstanceId.getInstance().getToken();
        Log.d("NotTOCKEN",tkn);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
        sendRegistrationToServer(tkn);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN),tkn);
        editor.commit();
    }

    private void sendRegistrationToServer(String tkn) {
    }
}
