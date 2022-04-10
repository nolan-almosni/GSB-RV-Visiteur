package fr.gsb.rv;

import static android.icu.lang.UCharacter.toUpperCase;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class SaisieRvActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String coefConfiance;
    String rap_motif;
    Integer num_praticien;
    String bilan;


    // initialisation de la date actuelle
    GregorianCalendar aujourdhui = new GregorianCalendar();
    int jour = aujourdhui.get(Calendar.DAY_OF_MONTH);
    int mois = aujourdhui.get(Calendar.MONTH)+1; // ajout de 1 car le mois commence a 0
    int annee = aujourdhui.get(Calendar.YEAR);

    String dateDeSaisie = Integer.toString(annee)+"-"+Integer.toString(mois)+"-"+Integer.toString(jour);
    String dateVisite = Integer.toString(annee)+"-"+Integer.toString(mois)+"-"+Integer.toString(jour);

    // liste des praticiens qui affichera les nom et prénom
    List<String> listePraticiens = new ArrayList<String>();

    // création d'un dictionnaire pour relier le nom et prénom du praticien a son numéro praticien
    Dictionary<String,Integer> dictionnairePraticiens = new Hashtable<String, Integer>();

    // liste des motifs qui seront afficher
    private final String [] listeMotifs = {"-- Choisir un motif --","Premier rendez-vous","Rendez-vous semestriel","Visite de contrôle","Visite après longue durée","Visite en urgence"};

    // liste des differents coef confiance
    private final String [] listeCoefConfiance = {"-- choisir un coefficient de confiance --","0","1","2","3","4","5"};

    String matricule = Session.getSession().getVisiteur().getMatricule();

    Button bDateVisite;
    Button bAjoutRv;
    Button bAnnulerRv;

    TextView tvDateVisite;
    TextView tvPraticien;
    TextView tvErrPraticien;
    TextView tvMotif;
    TextView tvErrMotif;
    TextView tvBilan;
    TextView tvErrBilan;
    TextView tvCoefConfiance;
    TextView tvErrCoefConfiance;

    EditText etBilan;

    Spinner spPraticien;
    Spinner spMotif;
    Spinner spCoefConfiance;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_rv);

        tvDateVisite = findViewById(R.id.tvDateVisite);
        etBilan = findViewById(R.id.etBilan);

        tvErrBilan = findViewById(R.id.tvErrBilan);
        tvErrPraticien = findViewById(R.id.tvErrPraticien);
        tvErrMotif = findViewById(R.id.tvErrMotif);
        tvErrCoefConfiance = findViewById(R.id.tvErrCoefConfiance);

        bDateVisite = findViewById(R.id.bDateVisite);
        bAjoutRv = findViewById(R.id.bAjoutRv);
        bAnnulerRv = findViewById(R.id.bAnnulerRv);

        listePraticiens.add("-- Sélectionner un Praticiens --");
        dictionnairePraticiens.put("-- Sélectionner un Praticiens --",-1);
        spPraticien = findViewById(R.id.spPraticien);
        spMotif = findViewById(R.id.spMotif);
        spCoefConfiance = findViewById(R.id.spCoefConfiance);

        tvDateVisite.setText(tvDateVisite.getText() + Integer.toString(jour) + "/" + Integer.toString(mois) + "/" + Integer.toString(annee));


        String url = String.format(Ip.getIp() + "/praticiens");

        Response.Listener<JSONArray> ecouteurReponsePraticiens = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        listePraticiens.add(response.getJSONObject(i).getString("pra_nom").toUpperCase() + " " + response.getJSONObject(i).getString("pra_prenom"));

                        dictionnairePraticiens.put(response.getJSONObject(i).getString("pra_nom").toUpperCase() + " " + response.getJSONObject(i).getString("pra_prenom"),response.getJSONObject(i).getInt("pra_num"));
                    }
                } catch (JSONException e) {
                    Log.e("APP-RV", "ERREUR : " + e);
                }
                creaSpPraticiens(); // création du spinner praticien
            }
        };
        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("APP-RV", "Erreur HTTP :" + error.getMessage());
            }
        };
        JsonArrayRequest requetePraticiens = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                ecouteurReponsePraticiens,
                ecouteurErreur
        );
        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requetePraticiens);


        // création du spinner Motifs
        ArrayAdapter<String> aaMotifs = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                listeMotifs
        );
        aaMotifs.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spMotif.setAdapter(aaMotifs);

        // création du spinner coefConfiance
        ArrayAdapter<String> aaCoefConfiance = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                listeCoefConfiance
        );
        aaCoefConfiance.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spCoefConfiance.setAdapter(aaCoefConfiance);



        // modification de la date de visite
        bDateVisite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(SaisieRvActivity.this, AlertDialog.THEME_HOLO_DARK,SaisieRvActivity.this,annee,mois-1,jour); // -1 pour trouver le bon mois
                dialog.getDatePicker().setMaxDate(new Date().getTime()); // empeche de selectionner une date supérieur a la date actuelle
                dialog.show();
            }
        });

        bAnnulerRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuRv = new Intent(SaisieRvActivity.this, MenuRvActivity.class);
                startActivity(menuRv);
            }
        });

        bAjoutRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bilan = etBilan.getText().toString();
                rap_motif = spMotif.getSelectedItem().toString();
                coefConfiance = spCoefConfiance.getSelectedItem().toString();
                // recupère le numéro de praticiens qui est actuellement noté dans le spinner
                num_praticien = dictionnairePraticiens.get(spPraticien.getSelectedItem().toString());

                if(num_praticien.equals(-1) || rap_motif.matches("-- Choisir un motif --") || bilan.matches("") || coefConfiance.matches("-- choisir un coefficient de confiance --")){

                    if(num_praticien.equals(-1)){
                       tvErrPraticien.setVisibility(View.VISIBLE);
                    }else{
                        tvErrPraticien.setVisibility(View.INVISIBLE);
                    }
                    if(rap_motif.matches("-- Choisir un motif --")){
                        tvErrMotif.setVisibility(View.VISIBLE);
                    }else{
                        tvErrMotif.setVisibility(View.INVISIBLE);
                    }
                    if(bilan.matches("")){
                        tvErrBilan.setVisibility(View.VISIBLE);
                    }else{
                        tvErrBilan.setVisibility(View.INVISIBLE);
                    }
                    if (coefConfiance.matches("-- choisir un coefficient de confiance --")){
                        tvErrCoefConfiance.setVisibility(View.VISIBLE);
                    }else{
                        tvErrCoefConfiance.setVisibility(View.INVISIBLE);
                    }
                }else {
                    String url = String.format(Ip.getIp() + "/rapports/%s/%s/%s/%s/%s/%s/%s", matricule, dateVisite, bilan, coefConfiance, dateDeSaisie, rap_motif, num_praticien);

                    Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.i("APP-RV", "reponse POST : " + response);
                        }
                    };
                    Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("APP-RV", "Erreur HTTP :" + error.getMessage());
                        }
                    };
                    JsonArrayRequest requete = new JsonArrayRequest(
                            Request.Method.POST,
                            url,
                            null,
                            ecouteurReponse,
                            ecouteurErreur
                    );
                    RequestQueue fileRequetes = Volley.newRequestQueue(SaisieRvActivity.this);
                    fileRequetes.add(requete);


                    Intent menuRv = new Intent(SaisieRvActivity.this, MenuRvActivity.class);
                    startActivity(menuRv);
                }
            }
        });

    }

    // permet de crée le spinner praticiens une fois que toutes les valeurs de la base on été récupérer
    public void creaSpPraticiens(){
        // création du spinner Praticiens
        ArrayAdapter<String> aaPraticiens = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                listePraticiens
        );
        aaPraticiens.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spPraticien.setAdapter(aaPraticiens);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        dateVisite = String.format("%04d-%02d-%02d",
                year,
                month+1,
                day
        );

        // initialisation de la date actuellement choisie
        jour = day;
        mois = month+1;
        annee = year;

        // affichage de la date choisie
        tvDateVisite.setText("Date de la visite : " + jour + "/" + mois + "/" + annee);

    }
}