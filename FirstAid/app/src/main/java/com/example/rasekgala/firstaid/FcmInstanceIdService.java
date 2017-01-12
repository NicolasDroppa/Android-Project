package com.example.rasekgala.firstaid;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Rasekgala on 2016/11/07.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh() {

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreference = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREFF), Context.MODE_PRIVATE);

       SharedPreferences.Editor editor = sharedPreference.edit();

        editor.putString(getString(R.string.FCM_TOKEN),recent_token);
        editor.commit();

        Log.d("Not","Token ["+recent_token+"]");
    }
}
