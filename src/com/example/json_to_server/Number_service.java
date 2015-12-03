package com.example.json_to_server;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by g00238234 on 01/12/2015.
 */
public class Number_service extends Service {

    Random rand = new Random();
    boolean isRunning = true;


    public void onCreate() {
        super.onCreate();


    }

    public void onStart(Intent intent, int startID) {
        Thread serviceThread = new Thread(new Runnable() {
            public void run() {
                Random rand = new Random();
                Intent broadcastIntent = new Intent();

                int[] test_array = new int[1000];
                int[] array = new int[1000];

                int num_holder[] = new int[1000];

                for (int i = 0; i < test_array.length & isRunning; i++) {
                    try {
                        Thread.sleep(1000);


                        num_holder[i] = rand.nextInt(1000);

                       broadcastIntent.setAction(MyActivity.mBroadcastArrayListAction);
                       broadcastIntent.putExtra("data", num_holder);
                       sendBroadcast(broadcastIntent);




                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        serviceThread.start();
    }


    public void onDestroy() {
        super.onDestroy();
        isRunning = false;



        //array[0] = num_holder;			//put random numbers in array
       // array[1] = two;					//put random numbers in array
        //array[2] = three;				//put random numbers in array
    }




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
