package sticfit3.sticfit3;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

public class ListExercice extends MainActivity {


    String[] tab ={"Pompe","Biceps"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listexe);

        final ListView listExe = (ListView)findViewById(R.id.listExe);
        ArrayAdapter arrayadp=new ArrayAdapter(this,  android.R.layout.simple_list_item_1, tab);
        listExe.setAdapter(arrayadp);

        listExe.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    choiceExe(listExe.getItemAtPosition(i));
            }


        });
    }


    public void choiceExe(Object exe) {

        String exo;
        CheckBox exPerso = (CheckBox) findViewById(R.id.exPerso);

        //si la case est coché on redirige vers exePerso
       if (exPerso.isChecked()) {

            Intent intentPerso = new Intent(this, ExePerso.class);

                switch (exe.toString()) {
                    case "Pompe":
                        exo = "Pompe";

                        intentPerso.putExtra("Exercice", exo);


                        break;
                    case "Biceps":
                        exo = "Biceps";
                        intentPerso.putExtra("Exercice", exo);
                        break;
                }
            //On start l'acctivité
            startActivity(intentPerso);

            }else {
            //Autrement on redirige vers exe directement et on passe les parametre Perso vide pour eviter erreur
            Intent intent = new Intent(this, Exe.class);
            intent.putExtra("nbSeriePerso", "");
            intent.putExtra("nbRepPerso","");
                switch (exe.toString()) {

                    case "Pompe":
                        exo = "Pompe";
                        intent.putExtra("Exercice", exo);
                        break;
                    case "Biceps":
                        exo = "Biceps";
                        intent.putExtra("Exercice", exo);
                        break;

                }
            //On start l'activité
            startActivity(intent);
            }


    }
}
