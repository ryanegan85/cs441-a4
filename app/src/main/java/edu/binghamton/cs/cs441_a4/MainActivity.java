package edu.binghamton.cs.cs441_a4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
