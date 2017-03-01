package com.tindfire.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    public static void splitePingTime(String recomondationnPingTime, TextView userPingtime) {
        try{

            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date utcDate = utcFormat.parse(recomondationnPingTime);
            Log.d("Date And Time------",""+utcDate.toString());

            String dateTimeUTC=utcDate.toString();

            String spliteTimeWithT[]=recomondationnPingTime.split("T");
//            Log.d("Android :","spliteTimeWithT[1] "+spliteTimeWithT[1]);
            Log.d("Android :","spliteTimeWithT[0] "+spliteTimeWithT[0]);
            String splTimeWithDot[]=dateTimeUTC.split(" ");
            Log.d("Android :","splTimeWithDot[0] "+splTimeWithDot[0]);
            Log.d("Android :","splTimeWithDot[1] "+splTimeWithDot[1]);
            Log.d("Android :","splTimeWithDot[2] "+splTimeWithDot[2]);
            Log.d("Android :","splTimeWithDot[3] "+splTimeWithDot[3]);
            totalTime(spliteTimeWithT[0],splTimeWithDot[3],userPingtime);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void totalTime(String datestring, String timeSTring ,TextView userPingtime) {
        String dateStart = datestring+" "+timeSTring;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateEnd = new Date();
        System.out.println(dateFormat.format(dateEnd));
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date d1 = null;
        try {
            d1 = format.parse(dateStart);

        } catch (Exception  e) {
            e.printStackTrace();
        }
        long diff = dateEnd.getTime() - d1.getTime();
//        long diffSeconds = diff / 1000 % 60;
//        long diffMinutes = diff / (60 * 1000) % 60;
//        long diffHours = diff / (60 * 60 * 1000);
//        int diffInDays = (int) ((dateEnd.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = diff / daysInMilli;
        diff = diff % daysInMilli;

        long elapsedHours = diff / hoursInMilli;
        diff = diff % hoursInMilli;

        long elapsedMinutes = diff / minutesInMilli;
        diff = diff % minutesInMilli;

        long elapsedSeconds = diff / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

        if (elapsedDays > 0) {
            System.err.println("Difference in number of days (2) : " + elapsedDays);
            userPingtime.setText(" , "+elapsedDays + " " +"days ago");
        } else if (elapsedHours > 1) {
            System.err.println(">24" +elapsedHours);
            userPingtime.setText(" , "+elapsedHours +" " +"hours ago");
        } else if (elapsedMinutes >= 1) {
            System.err.println("minutes" +elapsedMinutes);
            userPingtime.setText(" , "+elapsedMinutes +" " +"minutes ago");
        }

    }
    public static void spliteDateAndTime(String recomondationnBirthDate ,TextView userAge) {
        try{
            String getDate[]=recomondationnBirthDate.split("T");
            Log.d("Android :","getDate[0] "+getDate[0]);
            if(getDate[0].length()>0){
                Date getDateFormate= Utility.convertStringDateToDateFormate(getDate[0]);
                Log.d("Android :","getDateFormate "+getDateFormate);
                int totalAge=Utility.getAge(getDateFormate);
                Log.d("Android :","totalAge "+totalAge);
                if(totalAge!=0){
                    userAge.setText("," + totalAge);
                }else{
                    userAge.setText("");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
