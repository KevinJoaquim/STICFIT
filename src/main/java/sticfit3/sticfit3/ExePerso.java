package sticfit3.sticfit3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

        //Verification des champs repos ! si c'est entre 0 et 60 et si le champs est vide on boucle pas

        EditText minutes = (EditText) findViewById(R.id.minRepos);
        minutes.addTextChangedListener(new TextWatcher() {
            EditText minutes = (EditText) findViewById(R.id.minRepos);
            EditText secondes = (EditText) findViewById(R.id.secRepos);
            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

                    if (Integer.valueOf(s.toString()) > 60 || Integer.valueOf(s.toString()) < 0 ) {



                        TextView text = (TextView) layout.findViewById(R.id.text);

                            text.setText("La valeur doit être comprise entre 0 et 60");

                        Toast toast = new Toast(getApplicationContext());
                        toast.setView(layout);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                        minutes.setText("");

                    }else if(!secondes.getText().toString().isEmpty()) {
                        if(Integer.valueOf(secondes.getText().toString())==0 && Integer.valueOf(s.toString()) <= 0) {

                            TextView text = (TextView) layout.findViewById(R.id.text);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setView(layout);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.show();

                            text.setText("Il faut un temps de repos superieur à 0");

                            toast.setView(layout);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.show();
                            minutes.setText("");
                        }
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        //Verification des champs repos ! si c'est entre 0 et 60 et si le champs est vide on boucle pas

        EditText secondes = (EditText) findViewById(R.id.secRepos);
        secondes.addTextChangedListener(new TextWatcher() {
            EditText secondes = (EditText) findViewById(R.id.secRepos);
            EditText minutes = (EditText) findViewById(R.id.minRepos);
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

                    if (Integer.valueOf(s.toString()) > 60 || Integer.valueOf(s.toString()) < 0) {

                        TextView text = (TextView) layout.findViewById(R.id.text);

                        text.setText("La valeur doit être comprise entre 0 et 60");

                        Toast toast = new Toast(getApplicationContext());
                        toast.setView(layout);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                        secondes.setText("");

                    }else if(!minutes.getText().toString().isEmpty()) {
                            if(Integer.valueOf(minutes.getText().toString())== 0 && Integer.valueOf(s.toString()) <= 0){
                                TextView text = (TextView) layout.findViewById(R.id.text);
                                Toast toast = new Toast(getApplicationContext());
                                toast.setView(layout);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();

                                text.setText("Il faut un temps de repos superieur à 0");

                                toast.setView(layout);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();
                                secondes.setText("");
                            }

                          }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        //Verification du champs serie ! si c'est entre superieur superieur à 0

        EditText serie = (EditText) findViewById(R.id.nbSeriePerso);
        serie.addTextChangedListener(new TextWatcher() {
            EditText serie = (EditText) findViewById(R.id.nbSeriePerso);
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    if (Integer.valueOf(s.toString()) <= 0 ) {

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

                        TextView text = (TextView) layout.findViewById(R.id.text);
                        text.setText("La valeur doit être superieure à 0");

                        Toast toast = new Toast(getApplicationContext());
                        toast.setView(layout);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                        serie.setText("");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        //Verification des champs repos ! si c'est entre 0 et 60 et si le champs est vide on boucle pas

        EditText repetition = (EditText) findViewById(R.id.nbRepPerso);
        repetition.addTextChangedListener(new TextWatcher() {
            EditText secondes = (EditText) findViewById(R.id.nbRepPerso);
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    if (Integer.valueOf(s.toString()) <= 0) {

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

                        TextView text = (TextView) layout.findViewById(R.id.text);
                        text.setText("La valeur doit être superieure à 0");

                        Toast toast = new Toast(getApplicationContext());
                        toast.setView(layout);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                        secondes.setText("");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
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
        EditText minutesRepos = (EditText) findViewById(R.id.minRepos);
        EditText secondesRepos = (EditText) findViewById(R.id.secRepos);

        String nbSerie = seriePerso.getText().toString();
        String nbRep = repPerso.getText().toString();
        String minRepos = minutesRepos.getText().toString();
        String secRepos = secondesRepos.getText().toString();

        //On instancie les données pour les passées dans Exe.class

        intent.putExtra("Exercice", exo);
        intent.putExtra("nbSeriePerso", nbSerie);
        intent.putExtra("nbRepPerso",nbRep);
        intent.putExtra("minRepos", minRepos);
        intent.putExtra("secRepos",secRepos);

        Log.i("test","Exercice :" +exo);
        Log.i("test","nbSerie :" +nbSerie);
        Log.i("test","nbRep :" +nbRep);

        //On vérifie que les champs soient bien remplis avant de rediriger
        if(nbSerie.isEmpty() || nbRep.isEmpty() || minRepos.isEmpty() || secRepos.isEmpty() ) {

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
