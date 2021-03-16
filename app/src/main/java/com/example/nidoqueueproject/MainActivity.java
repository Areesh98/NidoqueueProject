package com.example.nidoqueueproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.perfmark.Link;

public class MainActivity extends AppCompatActivity implements
        BinomialTrialFragment.OnFragmentInteractionListener,
        CountBasedTrialFragment.OnFragmentInteractionListener,
        MeasurementTrialFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton addDataButton = findViewById(R.id.add_data_button);
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We can implement an if statement here to check experiment type and
                //choose the appropriate option by uncommenting for now
                //new BinomialTrialFragment().show(getSupportFragmentManager(), "ADD_TRIAL");
                //new CountBasedTrialFragment().show(getSupportFragmentManager(), "ADD_TRIAL");
                new MeasurementTrialFragment().show(getSupportFragmentManager(), "ADD_TRIAL");
            }
        });

        final FloatingActionButton removeExperimentButton = findViewById(R.id.remove_experiment_button);
        removeExperimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enter code here for deleting experiment from user's profile/list
            }
        });

        TextView dataText = findViewById(R.id.data_click_text);
        TextView qaText = findViewById(R.id.QA_click_text);

        String text = dataText.getText().toString();
        String text2 = qaText.getText().toString();

        SpannableString ss = new SpannableString(text);
        SpannableString ss2 = new SpannableString(text2);

        //Clickable for clicking on Data
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //Direct to showing data page
                //Toast text is temporary and to check if click works
                Toast.makeText(MainActivity.this, "Data", Toast.LENGTH_SHORT).show();
            }
        };

        //Clickable for clicking on QA
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //Direct to QA forum
                //Toast statement is temporary for testing purposes
                Toast.makeText(MainActivity.this, "QA", Toast.LENGTH_SHORT).show();
            }
        };

        ss.setSpan(clickableSpan1, 0, 4, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(clickableSpan2, 0, 2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        dataText.setText(ss);
        dataText.setMovementMethod(LinkMovementMethod.getInstance());

        qaText.setText(ss2);
        qaText.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public void onOkPressed(Trial newTrial) { }

}