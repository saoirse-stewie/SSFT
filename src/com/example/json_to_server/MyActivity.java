package com.example.json_to_server;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MyActivity extends Activity implements OnChartValueSelectedListener {
    /**
     * Called when the activity is first created.
     */

    public static String mBroadcastArrayListAction = "com.example.json_to_server.arraylist";
    public static String mBroadcastArrayIntAction = "com.example.json_to_server.arrayInt";
    private IntentFilter intentfiler;
    private PreparedStatement getNumber;
    public HashMap<String,String> inputmap;
    LineChart lineChart;

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
    BarChart chart;
    Button b2;
    int[] mColors = ColorTemplate.VORDIPLOM_COLORS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

         //chart = new BarChart(this);

        lineChart = (LineChart) findViewById(R.id.chart);

        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription("");

        lineChart.setData(new LineData());
        lineChart.invalidate();

        et = (EditText) findViewById(R.id.et1);
        //t2 = (TextView) findViewById(R.id.txt1);

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

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

                sum = result2.getString("sum");

                String test = sum.replaceAll("[-+^:,]","");
                // = test.trim();
                String test2 = test.substring(1, test.length()-1);
                String test3 =  test2.substring(1, test2.length()-1);
                //String test5 = test3.substring(1,test3.length()-1);
                String test4 = test3.replaceAll("\"", " ");



                Float floatarray = null;



                //min = result2.getString("CR_HK");
                    addDataSet(test4);
               // ArrayList<Entry> entries = new ArrayList<>();

               // entries.add(new Entry(Float.parseFloat(sum), 0));
               // entries.add(new Entry(Float.parseFloat(min), 1));


                //LineDataSet dataset = new LineDataSet(entries, "Statistics");
               // dataset.setColor(Color.BLACK);
               // ArrayList<String> labels = new ArrayList<String>();
               // labels.add("January");
               // labels.add("feb");


               // LineData data = new LineData(labels, dataset);

               // lineChart.setData(data); // set the data and list of lables into chart
               // lineChart.setDescription("Statistics from combo's");
               // lineChart.invalidate();


            } catch (JSONException e) {
                e.printStackTrace();
            }
           // t2.setText(sum);
//            t2.append(min);


        }

        }
    public void showLoanPayments(View v) throws JSONException, SQLException {

    JSONObject job = new JSONObject();

        job.put("one", "CR_MP");
        job.put("two",25);
        job.put("three",46);
        job.put("four",67);
        job.put("five",89);

        String url = et.getText().toString();

        new HttpAsyncTask().execute(url, job.toString());

    }

    private void addEntry() {

        LineData data = lineChart.getData();

        if(data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            // add a new x-value first
            data.addXValue(set.getEntryCount() + "");

            // choose a random dataSet
            int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());

            data.addEntry(new Entry((float) (Math.random() * 10) + 50f, set.getEntryCount()), randomDataSetIndex);

            // let the chart know it's data has changed
            lineChart.notifyDataSetChanged();

            lineChart.setVisibleXRangeMaximum(6);
            lineChart.setVisibleYRangeMaximum(15, YAxis.AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
            lineChart.moveViewTo(data.getXValCount()-7, 50f, YAxis.AxisDependency.LEFT);
        }
    }
    private void removeLastEntry() {

        LineData data = lineChart.getData();

        if(data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);

            if (set != null) {

                Entry e = set.getEntryForXIndex(set.getEntryCount() - 1);

                data.removeEntry(e, 0);
                // or remove by index
                // mData.removeEntry(xIndex, dataSetIndex);

                lineChart.notifyDataSetChanged();
                lineChart.invalidate();
            }
        }
    }

    private void addDataSet(String sum) {

        LineData data = lineChart.getData();

        if(data != null) {

            int count = (data.getDataSetCount() + 1);

            // create 10 y-vals
            ArrayList<Entry> yVals = new ArrayList<Entry>();

            if(data.getXValCount() == 0) {
                // add 10 x-entries
                for (int i = 0; i < 10; i++) {
                    data.addXValue("" + (i+1));
                }
            }
            String test = sum.replaceAll("\"", "");
            String words[] = test.split("");


            for (int i = 0; i < words.length; i++) {
                try {
               // System.out.println(words[i] );
                yVals.add(new Entry(Float.parseFloat(words[i]), i));
                } catch (NumberFormatException e) {
                    words[i] = null; // your default value
                }

            }


            LineDataSet set = new LineDataSet(yVals, "DataSet " + count);
            set.setLineWidth(2.5f);
            set.setCircleRadius(4.5f);

            int color = mColors[count % mColors.length];

            set.setColor(color);
            set.setCircleColor(color);
            set.setHighLightColor(color);
            set.setValueTextSize(10f);
            set.setValueTextColor(color);

            data.addDataSet(set);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        }
    }

    private void removeDataSet() {

        LineData data = lineChart.getData();

        if(data != null) {

            data.removeDataSet(data.getDataSetByIndex(data.getDataSetCount() - 1));

            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "DataSet 1");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }




}
