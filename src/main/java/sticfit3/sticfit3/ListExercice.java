package sticfit3.sticfit3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

public class ListExercice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listexe);
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

    public void choiceExe(View view) {

        String exo;
        CheckBox exPerso = (CheckBox) findViewById(R.id.exPerso);

        if (exPerso.isChecked()) {

            Intent intentPerso = new Intent(this, ExePerso.class);

                switch (view.getId()) {

                    case R.id.pompe:
                        exo = getString(R.string.button_pompe);

                        intentPerso.putExtra("Exercice", exo);

                        startActivity(intentPerso);
                        break;
                    case R.id.abdo:
                        exo = getString(R.string.button_abdo);
                        intentPerso.putExtra("Exercice", exo);
                        break;
                }
            }else {

            Intent intent = new Intent(this, Exe.class);
                switch (view.getId()) {

                    case R.id.pompe:
                        exo = getString(R.string.button_pompe);
                        intent.putExtra("Exercice", exo);
                        intent.putExtra("nbSeriePerso", "");
                        intent.putExtra("nbRepPerso","");

                        startActivity(intent);
                        break;
                    case R.id.abdo:
                        exo = getString(R.string.button_abdo);
                        intent.putExtra("Exercice", exo);
                        intent.putExtra("nbSeriePerso","");
                        intent.putExtra("nbRepPerso","");
                        break;

                }
            }


    }
}
