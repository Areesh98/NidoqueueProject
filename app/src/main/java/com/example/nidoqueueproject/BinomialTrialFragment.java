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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class BinomialTrialFragment extends DialogFragment {

    private OnFragmentInteractionListener listener;
    float successCounter = 0, failureCounter=0;
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.binomial_trial_fragment_layout, null);
        final TextView countSuccess = view.findViewById(R.id.binomial_success_count);
        final TextView countFailure = view.findViewById(R.id.binomial_failure_count);

        countSuccess.setText(String.valueOf(successCounter));
        countFailure.setText(String.valueOf(failureCounter));

        Button successAddButton = view.findViewById(R.id.binomial_success_add_button);
        Button successMinusButton = view.findViewById(R.id.binomial_success_minus_button);
        Button failureAddButton = view.findViewById(R.id.binomial_failure_add_button);
        Button failureMinusButton = view.findViewById(R.id.binomial_failure_minus_button);

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Test_Data");

        successAddButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                successCounter++;
                countSuccess.setText(String.valueOf(successCounter));
            }
        });

        successMinusButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                successCounter--;
                countSuccess.setText(String.valueOf(successCounter));
            }
        });

        failureAddButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                failureCounter++;
                countFailure.setText(String.valueOf(failureCounter));
            }
        });

        failureMinusButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                failureCounter--;
                countFailure.setText(String.valueOf(failureCounter));
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
                        HashMap<String, Number> data2 = new HashMap<>();

                        data.put("Successes", successCounter);
                        data2.put("Failures", failureCounter);

                        collectionReference
                                .document("New_Data")
                                .set(data);
                        collectionReference
                                .document("New_Data")
                                .set(data2, SetOptions.merge());
                    }}).create();

    }

}
