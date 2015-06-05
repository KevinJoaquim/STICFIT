package sticfit3.sticfit3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Random;

/**
 * Created by kevin on 05/06/2015.
 */
public class ModifPerso extends AppCompatActivity {
    private InfoDataSource datasource;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifperso);

        datasource = new InfoDataSource(this);
        datasource.open();

        final Button SaveAll = (Button) findViewById(R.id.valider);
        SaveAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                 InfoBDD info = null;


                EditText editpoids = (EditText) findViewById (R.id.mpoids);
                String nbpoids = editpoids.getText().toString();
                if(nbpoids.isEmpty()){
                    nbpoids="0";
                }
                String[] poids = new String[] { nbpoids };


                EditText edittaille = (EditText) findViewById (R.id.mtaille);
                String nbtaille = edittaille.getText().toString();
                if(nbtaille.isEmpty()){
                    nbtaille="0";
                }
                String[] taille = new String[] { nbtaille };


                EditText editsexe = (EditText) findViewById (R.id.msexe);
                String nbsexe = editsexe.getText().toString();
                if(nbsexe.isEmpty()){
                    nbsexe="0";
                }
                String[] sexe = new String[] { nbsexe };

                EditText editage = (EditText) findViewById (R.id.mage);
                String nbage = editage.getText().toString();
                if(nbage.isEmpty()){
                    nbage="0";
                }
                String[] age = new String[] { nbage };






                int nextInt = new Random().nextInt(1);



                info = datasource.createComment(poids[nextInt],taille[nextInt],sexe[nextInt],age[nextInt]);

                Log.i("test", " data :" + info);

                Intent intent = new Intent(ModifPerso.this, InfoPerso.class);
                startActivity(intent);



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
