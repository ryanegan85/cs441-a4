package edu.binghamton.cs.cs441_a4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;

interface NetResponse {
    void netResult(Integer code, JSONArray json);
}

public class MainActivity extends AppCompatActivity implements NetResponse {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button mButton;
    private TextView mTextM;
    private TextView mTextB;
    private SeekBar mSeekValueM;
    private SeekBar mSeekValueB;
    private double mM;
    private double mB;
    String updateString;
    String computeResult;
    MainActivity handle;
    NetTask netTask;
    boolean setM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> champs = new ArrayList<>();
        champs.add("Garen");
        champs.add("Fiora");
        champs.add("Vayne");
        champs.add("Swain");
        champs.add("Leona");
        champs.add("Elise");
        champs.add("Nidalee");
        champs.add("Gnar");
        champs.add("Sejuani");
        champs.add("Volibear");
        champs.add("Ashe");
        champs.add("Kindred");
        champs.add("Varus");
        champs.add("Morgana");
        champs.add("Ahri");
        champs.add("Veigar");
        champs.add("Aurelion Sol");
        champs.add("Shyvana");

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this, champs);
        recyclerView.setAdapter(mAdapter);
        handle = this;
        setM = false;

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = Double.toString(mM);
                //System.out.println("Starting m val: " + mM);
                String request1 = "value=" + m;
                netTask = new NetTask("https://cs.binghamton.edu/~pmadden/php/double.php", request1, handle);
                netTask.execute((Void) null);
                //System.out.println("Ending m val: " + mM);

                String b = Double.toString(mB);
                String request2 = "value=" + b;
                netTask = new NetTask("https://cs.binghamton.edu/~pmadden/php/double.php", request2, handle);
                netTask.execute((Void) null);

                openActivity2();
            }
        });

        mTextM = findViewById(R.id.mValueView);
        mTextB = findViewById(R.id.bValueView);
        mSeekValueM = findViewById(R.id.seekbarM);
        mSeekValueB = findViewById(R.id.seekbarB);
        mM = 0;
        mB = 0;

        mSeekValueM.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mM = progress;
                mTextM.setText("Value of M: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekValueB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mB = progress;
                mTextB.setText("Value of B: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void openActivity2() {
        Intent i = new Intent(this, Activity2.class);
        i.putExtra("M_VALUE", Double.toString(mM));
        i.putExtra("B_VALUE", Double.toString(mB));
        startActivity(i);
    }

    public void netResult(Integer code, JSONArray json)
    {
        System.out.println("Got a result from the web");
        updateString = "";

        for (int i = 0; i < json.length(); ++i)
        {
            try {
                JSONObject item = json.getJSONObject(i);

                if (item.getString("result") != null) {
                    System.out.println("Found a match");
                    System.out.println(item.getString("result"));
                    updateString = item.getString("result");


                }
            }
            catch (JSONException e)
            {
                updateString = "JSON Error!";
            }

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!setM) {
                        setM = true;
                        mM = Double.parseDouble(updateString);
                    } else {
                        setM = false;
                        mB = Double.parseDouble(updateString);
                    }
                    //computeResult = updateString;

                }
            });
        }
    }

    public class NetTask extends AsyncTask<Void, Void, Boolean> {
        private final String urlString;
        private final String reqString;
        private NetResponse changeListener;

        NetTask(String url, String request, NetResponse responseListener) {
            urlString = url; reqString = request; changeListener = responseListener;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                System.out.println("JSON Query: " + reqString);
                // JSONObject json = readJsonFromUrl("https://graph.facebook.com/19292868552");
                // JSONObject json = readJsonFromUrl("https://cnn.com");
                // System.out.println(reqString);
                JSONArray json = readJsonFromUrl(reqString);
                System.out.println("Finished getting json.");
                if (json != null)
                    System.out.println(json.toString());

                if (changeListener != null)
                    changeListener.netResult(0, json);

                //System.out.println("Notify that JSON has come in");
                // if (noteConnector != null)
                //    noteConnector.ncnotify(0, "");

            } catch (IOException e) {
                System.out.println("IO exception");
                //System.out.println(e);
                if (changeListener != null)
                    changeListener.netResult(1, null);
            } catch (JSONException e) {
                System.out.println("JSON Didn't work");
                //System.out.println(e);
                if (changeListener != null)
                    changeListener.netResult(2, null);
            }
            return true;
        }

        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            System.out.println("Read from the URL");
            System.out.println(sb.toString());
            System.out.println("Going to try to turn it into json");
            return sb.toString();
        }

        public JSONArray readJsonFromUrl(String request) throws IOException, JSONException {
            URL nurl = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) nurl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.out.println("Network request to " + urlString + " with request " + reqString);
            OutputStream urlout = connection.getOutputStream();

            //String s = "id=3452&second=fjfjfjfj";
            urlout.write(request.getBytes());
            urlout.close();
            InputStream is = connection.getInputStream();

            System.out.println("Waiting for network stream");
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String jsonText = readAll(rd);
                System.out.println("JSON is " + jsonText);

                JSONArray jarray = new JSONArray(jsonText);


                System.out.println("Got the object");
                return jarray;
            } finally {
                is.close();
                // System.out.println("Did not get the object.");
            }


        }
    }
}
