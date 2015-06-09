package sticfit3.sticfit3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by geoffrey on 05/06/2015.
 */
public class ExePerso extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exe_perso);



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendPerso(View view) {

        Intent intentListExercice = getIntent();
        Intent intent = new Intent(this, Exe.class);

        String exo = intentListExercice.getStringExtra("Exercice");
        EditText seriePerso = (EditText) findViewById(R.id.nbSeriePerso);
        EditText repPerso = (EditText) findViewById(R.id.nbRepPerso);

        String nbSerie = seriePerso.getText().toString();
        String nbRep = repPerso.getText().toString();

        intent.putExtra("Exercice", exo);
        intent.putExtra("nbSeriePerso", nbSerie);
        intent.putExtra("nbRepPerso",nbRep);

        Log.i("test","Exercice :" +exo);
        Log.i("test","nbSerie :" +nbSerie);
        Log.i("test","nbRep :" +nbRep);

        //On vérifie que les champs soient bien remplis avant de rediriger
        if(nbSerie.isEmpty() || nbRep.isEmpty()) {
            //On avertis qu'un des champs est vide
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Veuillez remplir tous les champs");

            Toast toast = new Toast(getApplicationContext());
            toast.setView(layout);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }else{
            //Sinon on redirige
            startActivity(intent);
        }


    }

}
