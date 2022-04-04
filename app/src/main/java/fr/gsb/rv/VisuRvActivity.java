package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisuRvActivity extends AppCompatActivity {

    TextView tvRapNum;
    TextView tvRapDateVisite;
    TextView tvRapBilan;
    TextView tvRapMotif;

    TextView tvRapNumSelect;
    TextView tvRapDateVisiteSelect;
    TextView tvRapBilanSelect;
    TextView tvRapMotifSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_rv);

        tvRapNumSelect = findViewById(R.id.tvRapNumSelect);
        tvRapDateVisiteSelect = findViewById(R.id.tvRapDateVisiteSelect);
        tvRapBilanSelect = findViewById(R.id.tvRapBilanSelect);
        tvRapMotifSelect = findViewById(R.id.tvRapMotifSelect);

        //recuperation des extra envoyé apr l'activité précedente
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("rap_select")) {
                List<String> test = new ArrayList<String>(intent.getStringArrayListExtra("rap_select"));
                Log.i("APP-RV", "test : " + test);

                tvRapNumSelect.setText(intent.getStringExtra("rap_select"));
            }
        }

    }
}