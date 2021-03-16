package com.example.nidoqueueproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.io.Closer.create;

public class MeasurementTrialFragment extends DialogFragment {

    private OnFragmentInteractionListener listener;
    EditText measurement;
    FirebaseFirestore db;
    ArrayList<Float> measurementList = new ArrayList<>();

    public interface OnFragmentInteractionListener {
        void onOkPressed(Trial newTrial);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.measurement_trial_fragment_layout, null);

        //Make sure to validate data type from EditText in measurement
        measurement = view.findViewById(R.id.measurement_editText);
        Button addButton = view.findViewById(R.id.measurement_add_button);

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Test_Data");

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Add measurement to ArrayList
                float num = Float.valueOf(measurement.getText().toString());
                measurementList.add(num);

                measurement.setText(String.valueOf(""));
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Trial Data")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        measurementList.clear();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onOkPressed(new Trial());
                        HashMap<String, ArrayList> data = new HashMap<>();
                       // for (int j=0; j<measurementList.size(); j++) {
                            data.put("Measurement", measurementList);

                            collectionReference
                                    .document("Measurement_Trial")
                                    .set(data, SetOptions.merge());
                       // }
                    }}).create();

    }

}
