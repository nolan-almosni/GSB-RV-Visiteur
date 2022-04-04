package fr.gsb.rv;

import androidx.annotation.IntegerRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RechercheRvActivity extends AppCompatActivity {

    private static final String [] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aôut", "Septembre", "Octobre", "Novembre", "Décembre"};
    Button bAfficherRv;
    Spinner spMois;
    Spinner spAnnee;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_rv);

        bAfficherRv = findViewById(R.id.bAfficherRv);
        spMois = findViewById(R.id.spMois);
        spAnnee = findViewById(R.id.spAnnee);


        ArrayAdapter<String> aaMois = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                mois
        );
        aaMois.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spMois.setAdapter(aaMois);
        // recuperation du mois courant pour le selectionner automatiquement
        Integer moisActuelle = LocalDate.now().getMonthValue();
        spMois.setSelection(moisActuelle-1);

        // recuperation de l'annee actuelle et création d'une liste allant jusq'a 50 ans en arrière
        List<Integer> annees = new ArrayList<Integer>();
        Integer anneeActuelle = LocalDate.now().getYear();
        for(Integer i = anneeActuelle; i>=anneeActuelle-50; i--){
            annees.add(i);
        }
        ArrayAdapter<Integer> aaAnnee = new ArrayAdapter<Integer>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                annees
        );
        aaAnnee.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spAnnee.setAdapter(aaAnnee);

        bAfficherRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listeRv = new Intent(RechercheRvActivity.this, ListeRvActivity.class);
                listeRv.putExtra("mois", spMois.getSelectedItem().toString());
                listeRv.putExtra("annee", spAnnee.getSelectedItem().toString());
                startActivity(listeRv);
            }
        });


    }
}