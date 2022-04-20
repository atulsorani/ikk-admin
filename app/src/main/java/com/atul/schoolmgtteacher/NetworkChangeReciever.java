package com.atul.schoolmgtteacher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)){
                //Toast.makeText(context, "Network Connected", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
            }

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public Boolean isOnline(Context context){
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo !=null && networkInfo.isConnected());

        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }
}