package sticfit3.sticfit3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kevin on 23/04/2015.
 */
public class Parametre extends MainActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametre);

        final Button InfoButton = (Button) findViewById(R.id.info);
        InfoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferenceManager.instance().persistTimeSpentOnLevel(0);
                Intent intent = new Intent(Parametre.this, Info.class);
                startActivity(intent);
            }
        });
        final Button PersoButton = (Button) findViewById(R.id.infoperso);
        PersoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferenceManager.instance().persistTimeSpentOnLevel(0);
                Intent intent = new Intent(Parametre.this, InfoPerso.class);
                startActivity(intent);
            }
        });

    }
}
