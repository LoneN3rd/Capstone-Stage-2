package com.example.android.katsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import static android.support.constraint.Constraints.TAG;

public class CheckNetwork {

    private static final String LOG_TAG = CheckNetwork.class.getSimpleName();

    public static boolean isInternetAvailable(Context context){

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo == null)
        {
            Log.d(LOG_TAG,"no internet connection");
            return false;
        }
        else
        {
            if(networkInfo.isConnected())
            {
                Log.d(TAG," internet connection available...");
                return true;
            }
            else
            {
                Log.d(TAG," internet connection");
                return true;
            }

        }

    }

}
