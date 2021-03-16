package com.example.nidoqueueproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CountBasedTrialFragment extends DialogFragment {

    private OnFragmentInteractionListener listener;
    float occurrenceCounter = 0;
    FirebaseFirestore db;

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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.countbased_trial_fragment_layout, null);
        final TextView countOccurrence = view.findViewById(R.id.countbased_occurrence_count);

        countOccurrence.setText(String.valueOf(occurrenceCounter));

        Button addButton = view.findViewById(R.id.countbased_add_button);
        Button minusButton = view.findViewById(R.id.countbased_minus_button);

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Test_Data");

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                occurrenceCounter++;
                countOccurrence.setText(String.valueOf(occurrenceCounter));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                occurrenceCounter--;
                countOccurrence.setText(String.valueOf(occurrenceCounter));
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Trial Data")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onOkPressed(new Trial());
                        HashMap<String, Number> data = new HashMap<>();

                        data.put("Count", occurrenceCounter);

                        collectionReference
                                .document("Countbased_Trial")
                                .set(data);
                    }}).create();

    }
}
