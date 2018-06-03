package net.shreygupta.medispencer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.shreygupta.medispencer.R;
import net.shreygupta.medispencer.generalclass.Prescriptions;

import java.util.ArrayList;

public class PrescriptionAdapter extends ArrayAdapter<Prescriptions>{

    public PrescriptionAdapter(@NonNull Context context, ArrayList<Prescriptions> Prescription) {
        super(context, 0, Prescription);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Prescriptions data = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.prescription_list, parent, false);
        }

        TextView date = listItemView.findViewById(R.id.date);

        date.setText(data.getDocumentId());
        return listItemView;
    }
}