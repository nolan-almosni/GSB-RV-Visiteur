package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class ListeRvActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // création d'une liste des mois de l'année pour pouvoir les convertir en nombre
    private static final String[] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aôut", "Septembre", "Octobre", "Novembre", "Décembre"};
    TextView tvRapportVisite;
    ListView lvRapports;
    // recupération du matricule
    private final String matricule = Session.getSession().getVisiteur().getMatricule();
    // creation d'une liste pour afficher le motif et la date de chaque rapports qui existe pour la date choisie
    List<String> rapports = new ArrayList<String>();
    // creation d'un dictionaire pour envoyé le tableau JSON qui sera selectionné
    Dictionary<Integer, JSONObject> rapSelect = new Hashtable<Integer, JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);

        tvRapportVisite = findViewById(R.id.tvRapportVisite);
        lvRapports = findViewById(R.id.lvRapports);

        String moisChoisi = null;
        String anneeChoisie = null;
        Integer numMoisChoisi = null;



        //recuperation des extra envoyé apr l'activité précedente
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("mois")) {
                moisChoisi = intent.getStringExtra("mois");
                //converti le nom du moi en nombre
                numMoisChoisi = Arrays.asList(mois).indexOf(moisChoisi) + 1; // recupération du numero de mois séléctionné
            }
            if (intent.hasExtra("annee")) {
                anneeChoisie = intent.getStringExtra("annee");
            }
        }

        tvRapportVisite.setText(tvRapportVisite.getText() + matricule + " du " + numMoisChoisi + "-" + anneeChoisie);


        String url = String.format(Ip.getIp() + "/rapports/%s/%s/%s", matricule, numMoisChoisi, anneeChoisie);

        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        rapports.add("date : " + response.getJSONObject(i).getString("rap_date_visite") + ". Nom praticien : " + response.getJSONObject(i).getString("pra_nom"));
                        rapSelect.put(i,response.getJSONObject(i));
                    }
                } catch (JSONException e) {
                    Log.e("APP-RV", "ERREUR : " + e);
                }
                if(response.length()==0){
                    tvRapportVisite.setText("aucun rapport de visite n'a été trouver durant cette période pour le compte : " + matricule);
                }
                ajoutRapportsLv(); //appel a la méthode pour ajouter un rapport a la liste view
            }
        };
        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("APP-RV", "Erreur HTTP :" + error.getMessage());
            }
        };
        JsonArrayRequest requete = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                ecouteurReponse,
                ecouteurErreur
        );
        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);

        lvRapports.setOnItemClickListener(this);

    }
    public void ajoutRapportsLv(){
        ArrayAdapter<String> adaptateur = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                rapports
        );

        lvRapports.setAdapter(adaptateur);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        JSONObject rapportSelectionne = rapSelect.get(i);
        try {
            Log.i("APP-RV", "rap_num : " + rapportSelectionne.getString("rap_num"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent visuRv = new Intent(ListeRvActivity.this, VisuRvActivity.class);
        visuRv.putExtra("rap_select", rapportSelectionne.toString()); //envoie du JSON selectionne a la prochaine vue
        startActivity(visuRv);
    }
}