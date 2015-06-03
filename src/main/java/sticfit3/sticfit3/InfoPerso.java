package sticfit3.sticfit3;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by kevin on 03/06/2015.
 */
public class InfoPerso extends Activity {
    private PersonneDataSource datasource;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoperso);

        datasource = new PersonneDataSource(this);
        datasource.open();


        PersonneBDD personne = null;

        TextView t1 = (TextView) findViewById(R.id.sexe);
        String nbsexe = t1.getText().toString();
        String[] sexe = new String[] { nbsexe };
        int nextInt = new Random().nextInt(1);


        TextView t2 = (TextView) findViewById(R.id.age);
        String nbage = t2.getText().toString();
        String[] age = new String[] { nbage };
        int nextInt2 = new Random().nextInt(2);


        TextView t3 = (TextView) findViewById(R.id.poids);
        String nbpoids = t3.getText().toString();
        String[] poids = new String[] { nbpoids };
        int nextInt3 = new Random().nextInt(3);


        TextView t4 = (TextView) findViewById(R.id.taille);
        String nbtaille = t4.getText().toString();
        String[] taille = new String[] { nbtaille };
        int nextInt4 = new Random().nextInt(4);


        personne = datasource.createPerso(sexe[nextInt],taille[nextInt4],age[nextInt2],poids[nextInt3]);

        Log.i("test"," data :" + personne);


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
