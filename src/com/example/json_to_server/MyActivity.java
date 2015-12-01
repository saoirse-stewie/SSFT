package com.example.json_to_server;

import android.app.Activity;
import android.content.Intent;
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

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    EditText et;
    EditText et2;
    EditText et3;
    EditText et4;


    TextView t2;
    TextView t3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        et = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        et3 = (EditText)findViewById(R.id.et3);
        et4 = (EditText)findViewById(R.id.et4);

        t2= (TextView)findViewById(R.id.txt1);
        t3=(TextView)findViewById(R.id.txt2);


    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String  jsonString = null;
            try {

                jsonString = HttpUtils.urlContentPost(urls[0],"loanInputs" ,urls[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonString;
        }
        protected void onPostExecute(String result) {
            JSONObject result2= null;
            try {
                result2 = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                System.out.println("here");
                et2.setText(result2.getString("loanAmount"));
                et3.setText(result2.getString("annualInterestRateInPercent"));
                et4.setText(result2.getString("loanPeriodInMonths"));
                t2.setText(result2.getString("formattedMonthlyPayment"));
                t3.setText(result2.getString("formattedTotalPayments"));

            } catch (JSONException e) {
               e.printStackTrace();
            }

        }

        }
    public void showLoanPayments(View v) {


        String url = et.getText().toString();
        String amount = et2.getText().toString();
        String rate = et3.getText().toString();
        String period = et4.getText().toString();
        Loan_inputs li = new Loan_inputs(amount,rate,period);
        JSONObject job = new JSONObject(li.getInputmap());
        // result = new JSONObject(jsonString);
        new HttpAsyncTask().execute(url,job.toString());

    }


}
