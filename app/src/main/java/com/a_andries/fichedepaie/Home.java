package com.a_andries.fichedepaie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.edit)
    void onEditClick() {
        Intent intent = new Intent(this, NDFList.class);

        startActivity(intent);
    }

    @OnClick(R.id.add)
    void onAddClick() {
        startActivity(new Intent(this, NDFView.class));
    }

    @OnClick(R.id.share)
    void onShareClick() {
        try {
            JSONArray jsonArray = new JSONArray();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            List<NDF> items;

            if (Reservoir.contains("itemsList")) {
                Type resultType = new TypeToken<List<NDF>>() {}.getType();
                items = Reservoir.get("itemsList", resultType);
            }
            else {
                Toast.makeText(this, "Aucune note de frais", Toast.LENGTH_SHORT).show();
                return ;
            }
            for (int i = 0; i < items.size(); i++) {
                JSONObject jsonObj = new JSONObject();
                NDF current = items.get(i);

                jsonObj.put("date_of_visit", dateFormat.format(current.getDateOfVisit().getTime()));
                jsonObj.put("hour_of_visit", timeFormat.format(current.getTimeOfVisit().getTime()));
                jsonObj.put("practitian_name", current.getPracticianName());
                jsonObj.put("amount_noon", current.getAmountNoon());
                jsonObj.put("amount_evening", current.getAmountEvening());
                jsonObj.put("amount_night", current.getAmountNight());
                jsonObj.put("justificatives_count", current.getJustificativesCount());
                jsonArray.put(jsonObj);
            }
            String shareBody = "Voici les notes de frais en JSON : \n" + jsonArray.toString(2);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Notes de frais");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Partager via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
