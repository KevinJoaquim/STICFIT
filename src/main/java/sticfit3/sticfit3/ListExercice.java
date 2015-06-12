package sticfit3.sticfit3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListExercice extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listexe);
    }


    public void choiceExe(View view) {

        TextView exo;
        CheckBox exPerso = (CheckBox) findViewById(R.id.exPerso);

        //si la case est coché on redirige vers exePerso
        if (exPerso.isChecked()) {

            Intent intentPerso = new Intent(this, ExePerso.class);

                switch (view.getId()) {
                    case R.id.pompe:
                        exo = (TextView) findViewById(R.id.pompe);

                        intentPerso.putExtra("Exercice", exo.getText());


                        break;
                    case R.id.biceps:
                        exo = (TextView) findViewById(R.id.biceps);
                        intentPerso.putExtra("Exercice", exo.getText());
                        break;
                }
            //On start l'acctivité
            startActivity(intentPerso);

            }else {
            //Autrement on redirige vers exe directement et on passe les parametre Perso vide pour eviter erreur
            Intent intent = new Intent(this, Exe.class);
            intent.putExtra("nbSeriePerso", "");
            intent.putExtra("nbRepPerso","");
                switch (view.getId()) {

                    case R.id.pompe:
                        exo = (TextView) findViewById(R.id.pompe);
                        intent.putExtra("Exercice", exo.getText());
                        break;
                    case R.id.biceps:
                        exo = (TextView) findViewById(R.id.biceps);
                        intent.putExtra("Exercice", exo.getText());
                        break;

                }
            //On start l'activité
            startActivity(intent);
            }


    }
}
