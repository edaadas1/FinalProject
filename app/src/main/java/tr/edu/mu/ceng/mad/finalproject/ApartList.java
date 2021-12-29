package tr.edu.mu.ceng.mad.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ApartList extends AppCompatActivity {

    final List<Aparts> aparts = new ArrayList<>();
    private Object ApartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apart_list);

        aparts.add(new Aparts("Emre Apart","Kötekli, 284. Sk. No:5, 48000 Muğla Merkez/Muğla","0552 467 48 48",R.mipmap.emreapart));
        aparts.add(new Aparts("İdeal Apart","Kötekli Mah. Sıtkı Koçman Vakfı Arkası 278 Sok. No:9","0541 280 82 83 / 0252 223 83 22",R.mipmap.idealapart));
        aparts.add(new Aparts("Başaran Apart","Kötekli, 287. Sk. No:20, 48000 Menteşe/Muğla","05345659993",R.mipmap.basaran_apart));
        ListView listView = findViewById(R.id.listview);
        ApartAdapter adapter = new ApartAdapter(aparts,this);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView apartLocation = findViewById(R.id.apartLocation);
                apartLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri geoLocation = Uri.parse("geo:37.169831, 28.378014");
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(geoLocation);
                        if(intent.resolveActivity(getPackageManager())!=null){
                            startActivity(intent);
                        }
                    }
                });

                TextView apartPhoneNumber = findViewById(R.id.apartPhoneNumber);
                apartPhoneNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // I gave a permission for phone call in android manifest.
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + apartPhoneNumber.getText().toString()));
                        if(intent.resolveActivity(getPackageManager())!=null){
                            startActivity(intent);
                        }
                    }
                });

                Button btnDownload = findViewById(R.id.btn_download);
                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });
            }
        });
    }
}