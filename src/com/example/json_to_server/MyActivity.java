package com.example.json_to_server;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Set;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    public static String mBroadcastArrayListAction = "com.example.json_to_server.arraylist";
    public static String mBroadcastArrayIntAction = "com.example.json_to_server.arrayInt";
    private IntentFilter intentfiler;
    private PreparedStatement getNumber;
    public HashMap<String,String> inputmap;

    String sd = "data/data/com.example.Exam_2";
    String mydb = sd + "/mydb.db";
    String TABLE_NAME = "numbers";


    String col1 = "num1";
    String col2 = "num2";
    String col3 = "num3";
    String col4 = "num4";
    String col5 = "num5";
    private Connection connection;



    SQLiteDatabase db;
   // BroadcastReceiver mReceiver;
   BroadcastReceiver receiver;
    int num[] ;


    EditText et;


    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    Intent service;

    Button b2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        et = (EditText) findViewById(R.id.et1);


        t4 = (TextView) findViewById(R.id.ServiceText);

        t2 = (TextView) findViewById(R.id.txt1);
        t3 = (TextView) findViewById(R.id.txt2);
        t5 = (TextView) findViewById(R.id.txt3);

        b2 = (Button) findViewById(R.id.btn2);

        intentfiler = new IntentFilter();

        intentfiler.addAction(mBroadcastArrayListAction);
        intentfiler.addAction(mBroadcastArrayIntAction);


        service = new Intent(this, Number_service.class);

        receiver = new MyEmbeddedBroadcastReceiver();

        registerReceiver(receiver, intentfiler);

        String sd = "data/data/com.example.json_to_server";
        String mydb = sd + "/mydb.db";

        db = SQLiteDatabase.openDatabase(mydb, null, SQLiteDatabase.CREATE_IF_NECESSARY);

       db.execSQL("drop table " + TABLE_NAME + ";");

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(_id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col1 + " TEXT ," + col2 + " TEXT ," + col3 + " TEXT ," + col4 + " TEXT ," + col5 + " TEXT );");

    }
    public void Rand (View v){
        startService(service);
    }
    public void stopService( View v)
    {
        stopService(service);
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {



            String  jsonString = null;
            try {

                jsonString = HttpUtils.urlContentPost(urls[0],"num" ,urls[1]);
           } catch (IOException e) {
                e.printStackTrace();
            }


            return jsonString;
        }
        protected void onPostExecute(String result) {

            JSONObject result2 = new JSONObject();
            try {
                result2 = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String min = null;
            String max = null;
            String sum = null;
            try {
                min = result2.getString("min");
                max = result2.getString("max");
                sum = result2.getString("sum");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            t3.setText(min);
            t5.setText(max);
            t2.setText(sum);


        }

        }
    public void showLoanPayments(View v) throws JSONException, SQLException {

    JSONObject job = new JSONObject();

        job.put("one", num[0]);
       job.put("two",num[1]);
        job.put("three",num[2]);
        job.put("four",num[3]);
        job.put("five",num[4]);

        String url = et.getText().toString();

        new HttpAsyncTask().execute(url, job.toString());

    }

    public class MyEmbeddedBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            t4.setText(t4.getText());

            //int num
            if(intent.getAction().equals(mBroadcastArrayListAction)) {


               num = intent.getIntArrayExtra("data");
                //int test = intent.getIntExtra("data", 0);

                   t4.setText(t4.getText().toString() + " " +  num[0] );
                t4.setText(t4.getText().toString() + " " +  num[1] );
                   t4.setText(t4.getText().toString() + " " +  num[2] );
                    t4.setText(t4.getText().toString() + " " +  num[3] );
                    t4.setText(t4.getText().toString() + " " + num[4] );

                db.execSQL("insert into " + TABLE_NAME + "(" + col1 + ", " + col2 + ", " + col3 + ", " + col4 + ", " + col5 + ")"
                           + " values ('" + num[0] + "', '" + num[1] + "' , '"+num[2]+"' , '"+num[3]+"' , '"+num[4] +"')");


                }



        }
    }





}
