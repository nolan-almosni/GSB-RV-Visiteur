package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.gsb.rv.technique.Session;

public class MenuRvActivity extends AppCompatActivity {

    Button bConsulterRv;
    Button bSaisirRv;
    TextView tvVisiteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rv);

        bConsulterRv = findViewById(R.id.bConsulterRv);
        bSaisirRv = findViewById(R.id.bSaisirRv);
        tvVisiteur = findViewById(R.id.tvVisiteur);

        // affichage du prenom et du nom du visiteur
        tvVisiteur.setText(Session.getSession().getVisiteur().getPrenom() + " " + Session.getSession().getVisiteur().getNom());

        bConsulterRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentionRechercheRv = new Intent(MenuRvActivity.this, RechercheRvActivity.class);
                startActivity(intentionRechercheRv);
            }
        });

        bSaisirRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentionSaisieRv = new Intent(MenuRvActivity.this, SaisieRvActivity.class);
                startActivity(intentionSaisieRv);

            }
        });


    }
}