@file:Suppress(
    "DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "IMPLICIT_CAST_TO_ANY"
)

package com.example.demopracticle.utlity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.util.Base64
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object UDF {
    /**
     * To check for internet availability status
     *
     * @param context the Context to access System Service
     * @return a boolean value (TRUE or FALSE)
     */
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(
            Context
                .CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        var result = false
        try {
            result = cm.activeNetworkInfo != null && cm.activeNetworkInfo!!
                .isAvailable && cm.activeNetworkInfo!!.isConnected
        } catch (e: Exception) {

        }

        return result
    }


    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun getDateFromMilliSeconds(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)
        // Create a calendar object that will convert the date and time value in milliseconds to
        // date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun getFromatedDate(dateString: String, requestFormat: String, resultFormat: String): String {
        val sdf = SimpleDateFormat(requestFormat)
        try {
            val date: Date? = sdf.parse(dateString)
            val tz = TimeZone.getDefault()
            sdf.timeZone = tz
            val millis = date!!.time
            return getDateFromMilliSeconds(millis, resultFormat)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun getOnlyDate(dateString: String): String {
        try {
            val f = SimpleDateFormat("dd/MM/yyyy hh:mm a")
            val d = f.parse(dateString)
            val date = SimpleDateFormat("dd-MM-yyyy")
            val time = SimpleDateFormat("hh:mm:ss")
            println("Date: " + date.format(d))
            println("Time: " + time.format(d))
            return date.format(d)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }






    fun convertBitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }









    fun shakeError(): TranslateAnimation {
        val shake = TranslateAnimation(0f, 10f, 0f, 0f)
        shake.duration = 500
        shake.interpolator = CycleInterpolator(7f)
        return shake
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun getCurrentMonthandYear(): String {
        val pattern = "yyyy-MM"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = simpleDateFormat.format(Date())
        return date
    }

    fun parseInt(intValue: String): Int {
        return try {
            intValue.toInt()
        } catch (e: java.lang.Exception) {
            0
        }
    }

    @JvmStatic
    fun checkNullReturnString(value:String):String{
        if(value!=null && !value.equals("null",true)){
            return value
        }else{
            return ""
        }
    }
}

