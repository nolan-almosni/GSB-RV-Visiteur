package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import fr.gsb.rv.technique.Session;

import fr.gsb.rv.entites.Visiteur;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "GSB MAIN ACTIVITY";
    TextView tvVisiteur;
    Button bSeConnecter;
    Button bSeDeconnecter;
    Button bAnnuler;
    EditText etMatricule;
    EditText etMdp;
    TextView tvMsgErr;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSeConnecter = findViewById(R.id.bValider);
        bAnnuler = findViewById(R.id.bAnnuler);

        etMatricule = findViewById(R.id.etMatricule);
        etMdp = findViewById(R.id.etMdp);

        tvMsgErr = findViewById(R.id.tvMsgErr);

        bSeConnecter.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            String matricule = etMatricule.getText().toString();
                                            String mdp = etMdp.getText().toString();
                                            Log.i("APP-RV", "Matricule : " + matricule);
                                            Log.i("APP-RV", "Mdp : " + mdp);
                                            try {
                                                seConnecter(matricule, mdp);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );

        bAnnuler.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            etMatricule.getText().clear();
                                            etMdp.getText().clear();
                                        }
                                    }
        );
    }

    public void seConnecter(String matricule, String mdp){
        String url = String.format("http://10.0.2.2:5000/visiteurs/%s/%s", matricule, mdp);
        Visiteur visiteur = new Visiteur();

        Response.Listener<JSONObject> ecouteurReponse = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    visiteur.setMatricule(response.getString("vis_matricule"));
                    visiteur.setMdp(response.getString("vis_mdp"));
                    visiteur.setNom(response.getString("vis_nom"));
                    visiteur.setPrenom(response.getString("vis_prenom"));
                    Session.ouvrir(visiteur);

                    Bundle paquet = new Bundle();
                    Gson gson = new Gson();
                    String SessionJson = gson.toJson(Session.getSession());
                    paquet.putString("sessionJSON", SessionJson);

                    Log.i("APP-RV", "connecter :" + visiteur);

                    Intent intentionMenuRv = new Intent(MainActivity.this, MenuRvActivity.class);
                    intentionMenuRv.putExtras(paquet);
                    startActivity(intentionMenuRv);

                } catch (JSONException e) {
                    Log.e("APP-RV", "Erreur JSON : " + e.getMessage());
                    tvMsgErr.setVisibility(View.VISIBLE);
                    etMdp.getText().clear();
                }
            }
        };


        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("APP-RV", "Erreur HTTP :" + error.getMessage());
                tvMsgErr.setVisibility(View.VISIBLE);
                etMdp.getText().clear();
            }
        };

        JsonObjectRequest requete = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                ecouteurReponse,
                ecouteurErreur
        );

        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);

    };
}