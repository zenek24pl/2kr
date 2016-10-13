package pl.noskilljustfun.dwakrokistad.model;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Bartosz on 06.06.2016.
 */
public class Rest {

    public static final String SERVER = "http://api.appsoup.io";


    private static Gson gson;
    private static OkHttpClient okHttpClient;

    public static Gson getGson() {
        return gson;
    }


    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static void init() {
        gson = new Gson();

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AppSoupTokenInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static okhttp3.Call getPointsAsync( Callback callback) {
        Request request = new Request.Builder()
                .url(SERVER + "/game/points")
                .method("GET", null)
                .build();

        okhttp3.Call call = getOkHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static okhttp3.Call getNextPointAsync(long id, Callback callback) {
        Request request = new Request.Builder()
                .url(SERVER + "/game/point/"+id)
                .method("GET", null)
                .build();

        okhttp3.Call call = getOkHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }
}
