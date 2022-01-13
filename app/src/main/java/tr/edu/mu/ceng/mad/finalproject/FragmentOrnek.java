package tr.edu.mu.ceng.mad.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentOrnek extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentornek);
        ListView liste1 = findViewById(R.id.listView2);
        String[] hayvanlar= new String[] {"Apartment","Gym","Food","Hairdresser","Taxi","Bar","Cafe","Stationer"};
        ArrayAdapter adapter = new ArrayAdapter(FragmentOrnek.this, android.R.layout.simple_list_item_1,hayvanlar);
        liste1.setAdapter(adapter);

        liste1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentB fragb = new FragmentB();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.lay6,fragb,"fragmentb");
                transaction.commit();
            }
        });
    }
}
