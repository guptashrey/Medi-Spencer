package net.shreygupta.medispencer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.shreygupta.medispencer.R;
import net.shreygupta.medispencer.ShowQRCode;
import net.shreygupta.medispencer.adapters.PrescriptionAdapter;
import net.shreygupta.medispencer.generalclass.Prescriptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.ContentValues.TAG;
import static net.shreygupta.medispencer.LoginActivity.mAuth;

public class PatientPrescriptions extends Fragment {

    private final ArrayList<Prescriptions> prescriptions = new ArrayList<>();
    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_prescriptions, container, false);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userEmail = currentUser.getEmail();
        list = v.findViewById(R.id.list);
        DocumentReference db = FirebaseFirestore.getInstance().collection("patientData").document(userEmail);

        db.collection("prescriptions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        final PrescriptionAdapter adptr = new PrescriptionAdapter(getActivity(), prescriptions);
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                //Appointments data = doc.toObject(Appointments.class);
                                Prescriptions data = new Prescriptions(doc.getId(), doc.get("medicines").toString(), doc.get("symptoms").toString());
                                prescriptions.add(data);
                                adptr.setNotifyOnChange(true);
                            }
                            Collections.sort(prescriptions, new dateSort());
                            list.setAdapter(adptr);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent obj = new Intent(getActivity(), ShowQRCode.class);
                obj.putExtra("document_name", prescriptions.get(i).getDocumentId());
                startActivity(obj);
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    class dateSort implements Comparator<Prescriptions> {
        public int compare(Prescriptions one, Prescriptions two) {
            return (two.getDocumentId().compareTo(one.getDocumentId()));
        }
    }
}