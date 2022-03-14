package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button bValider;
    private Button bAnnuler;
    private EditText etMatricule;
    private EditText etMdp;
    private Context context;
    private TextView msgErr;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bValider = findViewById(R.id.bValider);
        bAnnuler = findViewById(R.id.bAnnuler);

        etMatricule = findViewById(R.id.etMatricule);
        etMdp = findViewById(R.id.etMdp);

        msgErr = findViewById(R.id.msgErr);

        bValider.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            if (etMatricule.getText().toString().equals("test") && etMdp.getText().toString().equals("test") ){

                                                msgErr.setText("Échec à la connexion, recommencez...");
                                                etMdp.getText().clear();

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
}