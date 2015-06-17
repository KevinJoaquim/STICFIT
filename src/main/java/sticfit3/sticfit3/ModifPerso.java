package sticfit3.sticfit3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by kevin on 05/06/2015.
 */
public class ModifPerso extends MainActivity {
    private InfoDataSource datasource;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    InfoBDD data = null;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifperso);


        radioSexGroup=(RadioGroup)findViewById(R.id.radioSex);

        datasource = new InfoDataSource(this);
        datasource.open();
        EditText editpoids = (EditText) findViewById (R.id.mpoids);
        EditText edittaille = (EditText) findViewById (R.id.mtaille);
        EditText editage = (EditText) findViewById (R.id.mage);

        final RadioButton radio1 = (RadioButton) findViewById(R.id.radioMale);
        final RadioButton radio2 = (RadioButton) findViewById(R.id.radioFemale);



        //On verifie que la BDD est vide
        if(datasource.getAllComments().isEmpty()) {
            // Partie si vide


        }else{

            //Recuperation de tout les éléments de id 1
            data = datasource.getCommentById(1);
            //Modification du texte de l'EditTexte par le contenu de la BDD

            editpoids.setText(data.getPoids());

            edittaille.setText(data.getTaille());

            editage.setText(data.getAge());


            // le sexe est un Homme il checkera radio1 sinon radio2
            Log.i("test","sexe:"+data.getSexe());
            if(data.getSexe().equals("Homme")){
                radio1.setChecked(true);
                radio2.setChecked(false);

            }else{
                radio2.setChecked(true);
            }
        }

        //Button saveAll permet de modifier le contenu dans la bdd
        final Button SaveAll = (Button) findViewById(R.id.valider);
        SaveAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                InfoBDD info = null;

                EditText editpoids = (EditText) findViewById (R.id.mpoids);
                String nbpoids = editpoids.getText().toString();
                String[] poids = new String[] { nbpoids };


                EditText edittaille = (EditText) findViewById (R.id.mtaille);
                String nbtaille = edittaille.getText().toString();
                String[] taille = new String[] { nbtaille };


                EditText editage = (EditText) findViewById (R.id.mage);
                String nbage = editage.getText().toString();
                String[] age = new String[] { nbage };


                final RadioButton radio1 = (RadioButton)findViewById(R.id.radioMale);
                final RadioButton radio2 = (RadioButton)findViewById(R.id.radioFemale);
                //Si radio1 est checked alors on récupere l'id de celui-ci sinon on récuperer l'id de radio2
                if(radio1.isChecked()){
                    radioSexButton=(RadioButton)findViewById(R.id.radioMale);
                }else{
                    radioSexButton=(RadioButton)findViewById(R.id.radioFemale);
                }

                String nbsexe = radioSexButton.getText().toString();
                String[] sexe = new String[]{nbsexe};

                //Si les champs Poids, taille et Age ne sont pas renseigné un pop-up s'affichera
                if(nbpoids.isEmpty() || nbtaille.isEmpty()|| nbage.isEmpty()) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Veuillez remplir les champs");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(layout);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }else {

                    // nextInt est le prochain rang
                    int nextInt = new Random().nextInt(1);

                    //crée un nouvel element dans la base de donné
                    info = datasource.createComment(poids[nextInt], taille[nextInt],sexe[nextInt], age[nextInt]);

                    //Redirection vers ModifPerso
                    Intent intent = new Intent(ModifPerso.this, InfoPerso.class);
                    startActivity(intent);

                }

            }
        });



    }
    @Override
    protected void onPause()
    {
        // and don't forget to pause the thread that redraw the xyAccelerationView
        super.onPause();
    }
    @Override
    protected void onResume() {
        datasource.open();

        super.onResume();

    }
}
