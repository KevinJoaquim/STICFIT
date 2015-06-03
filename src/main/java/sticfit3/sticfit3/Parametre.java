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
<<<<<<< HEAD
       /* final Button PersoButton = (Button) findViewById(R.id.infoperso);
=======
        final Button PersoButton = (Button) findViewById(R.id.infoperso);
>>>>>>> f472e5b435f112df1746e667b45e0e18fdbe52a6
        PersoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferenceManager.instance().persistTimeSpentOnLevel(0);
<<<<<<< HEAD
                Intent intent = new Intent(Parametre.this, Perso.class);
                startActivity(intent);
            }
        });
        */
=======
                Intent intent = new Intent(Parametre.this, InfoPerso.class);
                startActivity(intent);
            }
        });

>>>>>>> f472e5b435f112df1746e667b45e0e18fdbe52a6
    }
}
