package com.tindfire.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public static Date convertStringDateToDateFormate(String dateString){
        String finalString = null;
        Date date = null;
        try {
            String start_dt = dateString;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");

            date = (Date)formatter.parse(start_dt);
            SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy");
            finalString = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
    public static int getAge(Date dateOfBirth)
    {
        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();

        dob.setTime(dateOfBirth);

        if (dob.after(now))
        {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
        {
            age--;
        }

        return age;
    }

}
