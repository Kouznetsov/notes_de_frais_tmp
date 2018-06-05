package com.a_andries.fichedepaie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NDFView extends AppCompatActivity {

    static final String NDF_EXTRA_KEY = "NDF_EXTRA";
    NDF ndf;

    @BindView(R.id.name_of_practician)
    EditText name;
    @BindView(R.id.date_visit)
    Button dateVisit;
    @BindView(R.id.hour_of_visit_edit)
    Button hourVisit;
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
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    TextWatcher amountTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            total.setText(String.format(Locale.getDefault(), "%.2f €",
                    Float.valueOf(amountNoon.length() > 0 ? amountNoon.getText().toString() : "0") +
                            Float.valueOf(amountEvening.length() > 0 ? amountEvening.getText().toString() : "0") +
                            Float.valueOf(amountNight.length() > 0 ? amountNight.getText().toString() : "0")));
        }
    };

    boolean noErrors() {
        if (name.length() > 0 && !dateVisit.getText().toString().startsWith("XX") &&
                !hourVisit.getText().toString().startsWith("XX") &&
                amountNoon.length() > 0 && amountEvening.length() > 0 &&
                amountNight.length() > 0 && amountOfJustificatifs.length() > 0)
            return true;
        else {
            Toast.makeText(this, getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ndf_view);
        getSupportActionBar().setTitle(R.string.edf_activity_title);
        ButterKnife.bind(this);
        try {
            ndf = (NDF) getIntent().getExtras().getSerializable(NDF_EXTRA_KEY);
            name.setText(ndf.getPracticianName());
            dateVisit.setText(dateFormat.format(ndf.getDateOfVisit().getTime()));
            hourVisit.setText(timeFormat.format(ndf.getTimeOfVisit().getTime()));
            amountNoon.setText(String.format(Locale.getDefault(), "%.2f", ndf.getAmountNoon()));
            amountEvening.setText(String.format(Locale.getDefault(), "%.2f", ndf.getAmountEvening()));
            calDateVisit = ndf.getDateOfVisit();
            calTimeVisit = ndf.getTimeOfVisit();
            total.setText(String.format(Locale.getDefault(), "%.2f", ndf.getTotalAmount()));
            amountNight.setText(String.format(Locale.getDefault(), "%.2f", ndf.getAmountNight()));
            amountOfJustificatifs.setText(String.format(Locale.getDefault(), "%d", ndf.getJustificativesCount()));
        } catch (Exception e) {
            ndf = null;
        }
        amountNoon.addTextChangedListener(amountTW);
        amountEvening.addTextChangedListener(amountTW);
        amountNight.addTextChangedListener(amountTW);
    }

    @OnClick(R.id.hour_of_visit_edit)
    void onTimeVisitClick() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calTimeVisit.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calTimeVisit.set(Calendar.MINUTE, minute);
                hourVisit.setText(timeFormat.format(calTimeVisit.getTime()));
            }
        }, calTimeVisit.get(Calendar.HOUR_OF_DAY), calTimeVisit.get(Calendar.MINUTE), true).show();
    }

    @OnClick(R.id.date_visit)
    void onDateVisitClick() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calDateVisit.set(Calendar.YEAR, year);
                calDateVisit.set(Calendar.MONTH, month);
                calDateVisit.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateVisit.setText(dateFormat.format(calDateVisit.getTime()));
            }
        }, calDateVisit.get(Calendar.YEAR), calDateVisit.get(Calendar.MONTH), calDateVisit.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.cancel)
    void onCancelClick() {
        onBackPressed();
    }

    @OnClick(R.id.validate)
    void onValidateClick() {
        if (noErrors()) {
            final NDF newNdf = new NDF();

            newNdf.setAmountEvening(Float.parseFloat(amountEvening.getText().toString()));
            newNdf.setAmountNight(Float.parseFloat(amountNight.getText().toString()));
            newNdf.setAmountNoon(Float.parseFloat(amountNoon.getText().toString()));
            newNdf.setJustificativesCount(Integer.parseInt(amountOfJustificatifs.getText().toString()));
            newNdf.setPracticianName(name.getText().toString());
            newNdf.setDateOfVisit(calDateVisit);
            newNdf.setTimeOfVisit(calTimeVisit);
            newNdf.setKey(ndf != null && ndf.getKey() != null ? ndf.getKey() : Long.toString(System.currentTimeMillis()));
            try {
                List<NDF> list;

                if (Reservoir.contains("itemsList")) {
                    Type resultType = new TypeToken<List<NDF>>() {
                    }.getType();
                    list = Reservoir.get("itemsList", resultType);
                    Reservoir.delete("itemsList");
                } else
                    list = new ArrayList<>();
                if (ndf != null) {
                    for (int i = 0; i < list.size(); i++) {
                        NDF c = list.get(i);

                        if (c.getKey().equals(ndf.getKey())) {
                            list.remove(i);
                            break;
                        }
                    }
                }
                list.add(newNdf);
                Reservoir.put("itemsList", list);
                Toast.makeText(this, "Note de frais enregistrée", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } catch (Exception e) {
                Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
