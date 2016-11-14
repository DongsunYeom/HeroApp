package com.brilliancemobility.heroes;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

/**
 * Created by dongsun on 13/11/16.
 */

public class Splash extends Activity {
    public static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);
        new StartupTask().executeOnExecutor(executor);
    }

    class StartupTask extends AsyncTask<Void, Void, Exception> {
        public StartupTask() {
            super();
        }

        protected Exception doInBackground(Void... args) {
            long startTime = System.currentTimeMillis();
            long minimumSplashTime = 2 * 1000;

            try {
                try {
                    long httpCacheSize = 5 * 1024 * 1024; // 10 MiB
                    File httpCacheDir = new File(getCacheDir(), "http");
                    Class.forName("android.net.http.HttpResponseCache")
                            .getMethod("install", File.class, long.class)
                            .invoke(null, httpCacheDir, httpCacheSize);
                } catch (Throwable httpResponseCacheNotAvailable) {
                    Log.d("Startup", "HTTP response cache is unavailable.");
                }


                try {
//	    			REST.getMarvel(Splash.this);
                } catch (Throwable e) {
                    Log.e("startup", "isochron", e);
                }

                try {
                    if ((System.currentTimeMillis() - startTime) < minimumSplashTime) {
                        Thread.sleep(minimumSplashTime - (System.currentTimeMillis() - startTime));
                    }
                } catch (InterruptedException ie) {

                }
            } catch (Exception e) {
                return e;
            }

            return null;
        }


        protected void onPostExecute(Exception result) {
            if (result == null) {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Log.e(getClass().getName(), "Init", result);
                String message = "Error";

                AlertDialog.Builder adb = new AlertDialog.Builder(Splash.this);
                adb.setTitle(message);
                adb.setMessage(message);
                adb.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }
                );
                adb.show();
            }
        }
    }
}
