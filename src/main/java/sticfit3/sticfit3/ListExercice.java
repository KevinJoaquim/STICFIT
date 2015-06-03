package sticfit3.sticfit3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

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
        Intent intent = new Intent(this, Exe.class);
        String exo;
        switch (view.getId()) {
            case R.id.pompe:
                exo = getString(R.string.button_pompe);
                intent.putExtra("Exercice", exo);
                break;
            case R.id.abdo:
                exo = getString(R.string.button_abdo);
                intent.putExtra("Exercice", exo);
                break;
        }

        startActivity(intent);
    }
}
