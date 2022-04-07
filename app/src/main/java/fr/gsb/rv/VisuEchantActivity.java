package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.List;

import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class VisuEchantActivity extends AppCompatActivity {

    TextView tvVisuEchant;
    ListView lvEchants;
    // recupération du matricule
    private final String matricule = Session.getSession().getVisiteur().getMatricule();
    // creation d'une liste pour afficher les echantillons offerts
    List<String> echants = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_echant);

        tvVisuEchant = findViewById(R.id.tvVisuEchant);
        lvEchants = findViewById(R.id.lvEchants);

        String numRap = null;

        //recuperation des extra envoyé apr l'activité précedente
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("rap_num")) {
                numRap = intent.getStringExtra("rap_num");
            }
        }

        String url = String.format(Ip.getIp() + "/rapports/echantillons/%s/%s", matricule, numRap);

        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        echants.add("nom médicament : " + response.getJSONObject(i).getString("med_nomcommercial") + ". quantité : " + response.getJSONObject(i).getString("off_quantite"));
                        Log.i("APP-RV", "echants : " + echants);
                    }
                } catch (JSONException e) {
                    Log.e("APP-RV", "ERREUR : " + e);
                }
                if(response.length()==0){
                    tvVisuEchant.setText("Aucun echantillon offert n'a été trouvé");
                }
                ajoutEchantLv(); //appel a la méthode pour ajouter un echantillon a la liste view
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
    }

    public void ajoutEchantLv(){
        ArrayAdapter<String> adaptateur = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                echants
        );
        lvEchants.setAdapter(adaptateur);
    }

}
