package tr.edu.mu.ceng.mad.finalproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApartList extends AppCompatActivity {

    final List<Aparts> aparts = new ArrayList<>();
    private Object ApartAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final int PERMISSION_WRITE = 0;
    String url = "https://lh3.googleusercontent.com/proxy/Ug6JJy7IfcPVQfWrh3s5sOdohzTgC63_A58Xf6mdnHQAMTo7wCSO4eUa04_-8b_aaKRg2APjeQT9Skhhpr_1fwJJIoVV";
    ProgressDialog progressDialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apart_list);

        progressDialog = new ProgressDialog(this);

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

                imageView = findViewById(R.id.apartImage);
                Button btnDownload = findViewById(R.id.btn_download);
                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Downloading().execute(url);
                    }
                });

            }
        });

    }

    public class Downloading extends AsyncTask<String, Integer, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... url) {
            File mydir = new File(Environment.getExternalStorageDirectory() + "/eda_photos");
            if (!mydir.exists()) {
                mydir.mkdirs();
            }

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(url[0]);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            SimpleDateFormat dateFormat = new SimpleDateFormat("mmddyyyyhhmmss");
            String date = dateFormat.format(new Date());

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle("Downloading")
                    .setDestinationInExternalPublicDir("/eda_photos", date + ".jpg");

            manager.enqueue(request);
            return mydir.getAbsolutePath() + File.separator + date + ".jpg";
        }

        @Override
        public void onPostExecute(String s) {
            super .onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_SHORT).show();
        }
    }

    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }
}