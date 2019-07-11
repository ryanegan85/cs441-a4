package edu.binghamton.cs.cs441_a4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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

    }
}
