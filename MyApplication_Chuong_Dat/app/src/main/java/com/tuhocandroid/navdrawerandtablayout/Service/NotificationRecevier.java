package com.tuhocandroid.navdrawerandtablayout.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by phamhuudat on 10/3/2016.
 */

public class NotificationRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
//        if(AppConstant.STOP_SERVICE.equals(action)){
//            Toast.makeText(context, "Stop Service", Toast.LENGTH_LONG).show();
//        }

    }
}
