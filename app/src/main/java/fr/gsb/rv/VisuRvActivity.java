package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisuRvActivity extends AppCompatActivity {

    Button bAfficherVisuEchant;

    TextView tvRapNum;
    TextView tvRapDateVisite;
    TextView tvRapBilan;
    TextView tvRapMotif;
    TextView tvPraNom;
    TextView tvPraPrenom;
    TextView tvPraCp;
    TextView tvPraVille;

    TextView tvRapNumSelect;
    TextView tvRapDateVisiteSelect;
    TextView tvRapBilanSelect;
    TextView tvRapMotifSelect;
    TextView tvPraNomSelect;
    TextView tvPraPrenomSelect;
    TextView tvPraCpSelect;
    TextView tvPraVilleSelect;
    JSONObject rapportChoisi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_rv);

        bAfficherVisuEchant = findViewById(R.id.bAfficherVisuEchant);

        tvRapNumSelect = findViewById(R.id.tvRapNumSelect);
        tvRapDateVisiteSelect = findViewById(R.id.tvRapDateVisiteSelect);
        tvRapBilanSelect = findViewById(R.id.tvRapBilanSelect);
        tvRapMotifSelect = findViewById(R.id.tvRapMotifSelect);
        tvPraNomSelect = findViewById(R.id.tvPraNomSelect);
        tvPraPrenomSelect = findViewById(R.id.tvPraPrenomSelect);
        tvPraCpSelect = findViewById(R.id.tvPraCpSelect);
        tvPraVilleSelect = findViewById(R.id.tvPraVilleSelect);

        //recuperation des extra envoyé apr l'activité précedente
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("rap_select")) {
                try {
                    rapportChoisi = new JSONObject(intent.getStringExtra("rap_select"));
                } catch (JSONException e) {
                    Log.e("APP-RV", "ERREUR : " + e);
                }

                try {
                    tvRapNumSelect.setText(rapportChoisi.getString("rap_num"));
                    tvRapDateVisiteSelect.setText(rapportChoisi.getString("rap_date_visite"));
                    tvRapBilanSelect.setText(rapportChoisi.getString("rap_bilan"));
                    tvRapMotifSelect.setText(rapportChoisi.getString("rap_motif"));
                    tvPraNomSelect.setText(rapportChoisi.getString("pra_nom"));
                    tvPraPrenomSelect.setText(rapportChoisi.getString("pra_prenom"));
                    tvPraCpSelect.setText(rapportChoisi.getString("pra_cp"));
                    tvPraVilleSelect.setText(rapportChoisi.getString("pra_ville"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        bAfficherVisuEchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent visuEchant = new Intent(VisuRvActivity.this, VisuEchantActivity.class);
                try {
                    visuEchant.putExtra("rap_num", rapportChoisi.getString("rap_num"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(visuEchant);
            }
        });

    }
}