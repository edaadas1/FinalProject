package tr.edu.mu.ceng.mad.finalproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegistration extends AppCompatActivity {
    
    FirebaseFirestore db;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        EditText userName = findViewById(R.id.userName);
        EditText password = findViewById(R.id.password);

        Map<String,Object> userInformation = new HashMap<>();

        db = FirebaseFirestore.getInstance();
        
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                userInformation.put("user name",userName.getText().toString());
                userInformation.put("password",password.getText().toString());

                if(!userInformation.containsValue("")) { //boş değer girilmediği sürece database e eklensin
                    db.collection("users").add(userInformation)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserRegistration.this, "Fail to send data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if(userInformation.containsValue("")){
                    Toast.makeText(UserRegistration.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UserRegistration.this, "User informations are created.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserRegistration.this,HomePage.class);
                    startActivity(intent);
                }

            }
        });

        TextView registerNewAccount = findViewById(R.id.registerNewAccount);
        registerNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegistration.this, UserRegistrationContent.class);
                startActivity(intent);
            }
        });

    }
}