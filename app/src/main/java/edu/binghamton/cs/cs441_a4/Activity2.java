package edu.binghamton.cs.cs441_a4;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Activity2 extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        double x,y,m,b;
        x = 0;
        m = Double.parseDouble(getIntent().getStringExtra("M_VALUE"));
        b = Double.parseDouble(getIntent().getStringExtra("B_VALUE"));

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<>();

        int numDataPoints = 100;
        for(int i=0; i<numDataPoints; i++) {
            x = x + 0.1;
            y = m*x + b;
            series.appendData(new DataPoint(x,y), true, 100);
        }
        graph.addSeries(series);

    }

    /*
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
    */
}
