package net.shreygupta.medispencer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import static android.content.ContentValues.TAG;
import static net.shreygupta.medispencer.LoginActivity.mAuth;

public class ShowQRCode extends AppCompatActivity {

    private TextView date;
    private TextView medicines;
    private TextView symptoms;
    private String documentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qrcode);

        date = findViewById(R.id.date);
        medicines = findViewById(R.id.medicines);
        symptoms = findViewById(R.id.symptoms);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userEmail = currentUser.getEmail();

        documentName = getIntent().getStringExtra("document_name");

        DocumentReference db = FirebaseFirestore.getInstance().collection("patientData").document(userEmail).collection("prescriptions").document(documentName);

        db.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        date.setText("Prescription: " + documentName);
                        medicines.setText(document.get("medicines").toString());
                        symptoms.setText(document.get("symptoms").toString());

                        try {
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.encodeBitmap(document.get("medicines").toString(), BarcodeFormat.QR_CODE, 1000, 1000);
                            ImageView imageViewQrCode = findViewById(R.id.qrCode);
                            imageViewQrCode.setImageBitmap(bitmap);
                        } catch(Exception ignored) {}

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.patientmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            signOut();
            startActivity(new Intent(ShowQRCode.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        mAuth.signOut();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShowQRCode.this, PatientDetails.class));
    }
}