package com.a_andries.fichedepaie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class NDFView extends AppCompatActivity {

    static final String NDF_EXTRA_KEY = "NDF_EXTRA";
    NDF ndf;

    @BindView(R.id.name_of_practician)
    EditText name;
    @BindView(R.id.date_visit)
    EditText dateVisit;
    @BindView(R.id.hour_of_visit_edit)
    EditText hourVisit;
    @BindView(R.id.amount_noon)
    EditText amountNoon;
    @BindView(R.id.amount_evening)
    EditText amountEvening;
    @BindView(R.id.night_price)
    EditText amountNight;
    @BindView(R.id.amount_tax_free)
    TextView total;
    @BindView(R.id.amount_of_justificatifs)
    EditText amountOfJustificatifs;
    Calendar calDateVisit = Calendar.getInstance(), calTimeVisit = Calendar.getInstance();
    TextWatcher amountTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            total.setText(String.format(Locale.getDefault(), "%f €",
                    Float.valueOf(amountNoon.getText().toString()) +
                            Float.valueOf(amountEvening.getText().toString()) +
                            Float.valueOf(amountNight.getText().toString())));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.edf_activity_title);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            ndf = (NDF) getIntent().getExtras().getSerializable(NDF_EXTRA_KEY);
            name.setText(ndf.getPracticianName());
            dateVisit.setText(dateFormat.format(ndf.getDateOfVisit().getTime()));
            hourVisit.setText(timeFormat.format(ndf.getTimeOfVisit().getTime()));
            amountNoon.setText(String.format(Locale.getDefault(), "%.2f", ndf.getAmountNoon()));
            amountEvening.setText(String.format(Locale.getDefault(), "%.2f", ndf.getAmountEvening()));
            total.setText(String.format(Locale.getDefault(), "%.2f", ndf.getTotalAmount()));
            amountOfJustificatifs.setText(Integer.toString(ndf.getJustificativesCount()));
        } catch (Exception e) {
            ndf = null;
        }
        amountNoon.addTextChangedListener(amountTW);
        amountEvening.addTextChangedListener(amountTW);
        amountNight.addTextChangedListener(amountTW);
    }

    @OnClick(R.id.hour_of_visit_edit)
    void onTimeVisitClick() {

    }

    @OnClick(R.id.date_visit)
    void onDateVisitClick() {

    }

    @OnClick(R.id.cancel)
    void onCancelClick() {
        onBackPressed();
    }

    @OnClick(R.id.validate)
    void onValidateClick() {
        final NDF newNdf = new NDF();

        newNdf.setAmountEvening(Float.parseFloat(amountEvening.getText().toString()));
        newNdf.setAmountNight(Float.parseFloat(amountNight.getText().toString()));
        newNdf.setAmountNoon(Float.parseFloat(amountNoon.getText().toString()));
        newNdf.setJustificativesCount(Integer.parseInt(amountOfJustificatifs.getText().toString()));
        newNdf.setPracticianName(name.getText().toString());
        newNdf.setDateOfVisit(calDateVisit);
        newNdf.setTimeOfVisit(calTimeVisit);
        Reservoir.put(Long.toString(System.currentTimeMillis()), );
    }
}
