package pl.noskilljustfun.dwakrokistad.model;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Bartosz on 06.06.2016.
 */
public class AppSoupTokenInterceptor implements Interceptor {
    /*
     * CONFIGURATION
     */
    public static final String HOST = "api.appsoup.io";
    public static final String APP_ID = "2.kroki.stad";
    public static final String MD5_PASSWORD = toMD5("2krokistad");

    /*
     * INTERCEPTOR
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl url = chain.request().url();
        if (url.host().equals(HOST)) {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("app-id", APP_ID)
                    .addHeader("token", toMD5(MD5_PASSWORD + "+" + date()))
                    .build();
            return chain.proceed(newRequest);
        } else {
            return chain.proceed(chain.request());
        }
    }

    private static String date() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static String toMD5(String toEncode) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(toEncode.getBytes("UTF-8"));

            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("REST", "No such algorithm", e);
        } catch (UnsupportedEncodingException e) {
            Log.e("REST", "Unsupported encoding exception", e);
        }
        return "";
    }
}
