package com.tindfire.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by vcareall on 21/4/16.
 */
public class Utility {
    public static final String TAG = Utility.class.getSimpleName();




    /**
     * Method to check Network Connectivity
     *
     * @param mContext
     * @return
     */
    public static boolean isConnectingToInternet(Context mContext) {
        Log.d(TAG, "Inside isConnectingToInternet()");
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /**
     * Method to show Message in Toast
     *
     * @param mContext
     * @param msg
     */
    public static void showMessage(Context mContext, String msg) {
        Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
