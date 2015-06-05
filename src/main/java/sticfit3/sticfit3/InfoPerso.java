package sticfit3.sticfit3;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by kevin on 03/06/2015.
 */
public class InfoPerso extends AppCompatActivity {
    private InfoDataSource datasource;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoperso);

        datasource = new InfoDataSource(this);
        datasource.open();

        final ArrayAdapter<InfoBDD> listAdapter = new ArrayAdapter<InfoBDD>(this, android.R.layout.simple_list_item_1, datasource.getAllComments());


        final ListView infoListe = (ListView) this.findViewById(R.id.list_info);
        infoListe.setAdapter(listAdapter);

        final Button ajout = (Button) findViewById(R.id.ajout);
        ajout.setVisibility(View.GONE);
        TextView text = (TextView) findViewById (R.id.textView5);
        text.setVisibility(View.GONE);
        final Button modif = (Button) findViewById(R.id.modifier);
        modif.setVisibility(View.VISIBLE);

        if(listAdapter.getCount() == 0){

            ajout.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            modif.setVisibility(View.GONE);



        }

        ajout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InfoPerso.this, ModifPerso.class);
                startActivity(intent);

            }
        });


        modif.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InfoPerso.this, ModifPerso.class);
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
