package sticfit3.sticfit3;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by kevin on 22/04/2015.
 */
public class Exe extends Activity implements SensorEventListener {


    // initialise la bdd
    private PompeDataSource datasource;
    private SeanceDataSource dataSourceSeance;


    // proximity
    private static final String LOG_TAG = "SensorsProximity";
    float proximityValue;
    float maxRange;
    SensorManager sensorManager;
    Sensor proximity;

    //chronomètre
    private Chronometer mChronometer;
    private long timeWhenStopped=1 ;

    // variable serie, repetitio et calorie
    private int h =0;
    private int i = 1;
    private double calfinal = 0;

    SeanceBDD laSeance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exe);

        //Connect to BDD
        dataSourceSeance = new SeanceDataSource(this);
        dataSourceSeance.open();

        datasource = new PompeDataSource(this);
        datasource.open();

        Intent intent = getIntent();
        String exo = intent.getStringExtra("Exercice");
        TextView title = (TextView) findViewById(R.id.Chronometre);
        title.setText(exo);

        String[] seance = new String[] { exo };
        int nextInt = new Random().nextInt(1);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm ");
        String formattedDate = df.format(c.getTime());


        SeanceBDD seanceDetail = dataSourceSeance.createComment(seance[nextInt],formattedDate);
        setSeance(seanceDetail);


        //active le sensor du téléphone
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Instantiate the light and its max range
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        maxRange = 8;//proximity.getMaximumRange();


        //Chronomètre
        SharedPreferenceManager.instance().persistTimeSpentOnLevel(0);
        mChronometer = (Chronometer)findViewById(R.id.chronometer);

        //Boutton Pause pour Chronomètre
        Button Stop = (Button)findViewById(R.id.Pause);
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPauseCH();
            }
        });


        //Boutton reset pour Chronomètre
        Button Effacer = (Button)findViewById(R.id.Reset);
        Effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReset();
            }
        });

        // Boutton Startpour chonomètre + debut calcul kal
        Button Start = (Button)findViewById(R.id.Start);
        Start.setOnClickListener(new View.OnClickListener() {
            Intent intent = getIntent();
            String exo = intent.getStringExtra("Exercice");

            @Override
            public void onClick(View v) {
                timeWhenStopped=1;
                mChronometer.setBase(SystemClock.elapsedRealtime() + SharedPreferenceManager.instance().getTimeSpentOnLevel());
                mChronometer.start();

                CalculKal(exo);
            }
        });

        // boutton exit quitter la page sans sauvegarder
        final Button ExitButton = (Button) findViewById(R.id.Exit);
        ExitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dataSourceSeance.deleteCommentById(getSeance().getId());
                Intent intent = new Intent(Exe.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //boutton ajouter serie +1
        final Button SerieAdd = (Button) findViewById(R.id.SerieAdd);
        SerieAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                save();
                SerieAdd();

            }
        });

        // boutton restAll remettre tout a 0
        Button ResetAll = (Button)findViewById(R.id.ResetAll);
        ResetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // serie = 0
                TextView t1 = (TextView) findViewById(R.id.nbSerie);
                i=0;
                String j = Integer.toString(i);
                t1.setText(j);

                //chronomètre = 0
                mChronometer.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = 0;
                onPause();
                mChronometer.stop();

                // repetition = 0
                h = 0;
                TextView t2 = (TextView) findViewById(R.id.nbRepet);
                String k = Integer.toString(h);
                t2.setText(k);


                //calorie =0
                int u = 0;
                TextView t3 = (TextView) findViewById(R.id.kl);
                String calnull = Double.toString(u);
                t3.setText(calnull);
                onResume();
            }
        });

        //boutton sauvegarder tout (que Serie pour le moment)
        final Button SaveAll = (Button) findViewById(R.id.SaveAll);
        SaveAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("Votre séance a bien été sauvegardée");

                Toast toast = new Toast(getApplicationContext());
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(Exe.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }



    public void setSeance(SeanceBDD seance){
        this.laSeance=seance;
    }
    public SeanceBDD getSeance() {
        return laSeance;
    }

    //Fonction sauvegarder tout (que serie pour le moment)
    public void save() {
        @SuppressWarnings("unchecked")

        TextView t1 = (TextView) findViewById(R.id.nbSerie);
        String nbSerie = t1.getText().toString();
        String[] serie = new String[] { nbSerie };

        TextView t2 = (TextView) findViewById(R.id.nbRepet);
        String nbRep = t2.getText().toString();
        if(nbRep.isEmpty()){
            nbRep="0";
        }
        String[] rep = new String[] { nbRep };

        TextView t3 = (TextView) findViewById(R.id.kl);
        String nbKal = t3.getText().toString();
        if(nbKal.isEmpty()){
            nbKal="0";
        }
        String[] kal = new String[] { nbKal };

        long idSeance = laSeance.getId();
        String[] seance = new String[] { Long.toString(idSeance) };
        Log.i("tag", "idSeanceSave:" +laSeance.getId());
        int nextInt = new Random().nextInt(1);

        datasource.createComment(serie[nextInt],rep[nextInt],kal[nextInt],seance[nextInt]);

    }


    // au Depart de l'éxécution de la classe, cette fonction s'éxecutera sans qu'on l'appel (un peu comme le oncreate)
    @Override
    protected void onResume() {
        datasource.open();
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_FASTEST);

        super.onResume();

    }

    //Fonction mettre pause chonomètre
    protected void onPauseCH() {

        super.onPause();
        timeWhenStopped=0;
        long timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
        SharedPreferenceManager.instance().persistTimeSpentOnLevel(timeWhenStopped);
        mChronometer.stop();
    }


    //reset chronomètre
    public void onReset() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        timeWhenStopped = 0;
        onPause();

    }

    //Ajout serie +1
    protected void SerieAdd() {
        i++;
        TextView t1 = (TextView) findViewById(R.id.nbSerie);
        String j = Integer.toString(i);
        t1.setText(j);

        // remet a 0 repetition car on change de serie
        int nb = 0;
        h = 0;
        TextView t2 = (TextView) findViewById(R.id.nbRepet);
        String k = Integer.toString(nb);
        t2.setText(k);



    }


    //Calcul Kalorie
    protected void CalculKal(String exo) {
        if (exo.equals("Pompe")) {

            new Thread(new Runnable() {

                public void run() {
                    while (timeWhenStopped != 0) {
                        double calorie = 2.14;
                        calfinal = calorie + calfinal;
                        SystemClock.sleep(10000); // METTRE EN PAUSE LE THREAD PENDANT 1 SECONDE
                        runOnUiThread(new Runnable() { // UNE FACON DE MODIFIER LE THREAD UI (L'AFFICHAGE) DEPUIS L'INTERIEUR D'UN THREAD
                            public void run() {
                                TextView t2 = (TextView) findViewById(R.id.kl);
                                String cal = Double.toString(calfinal);
                                t2.setText(cal.substring(0, 4));
                            }
                        });
                    }
                }
            }).start();

        }
    }

    /*
   * (non-Javadoc)
   *
   * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
   *
   */
    //proximity fonction quand le sensor change
    @Override
    public void onSensorChanged(SensorEvent event) {
        // update only when your are in the right case:


        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            // the light value
            proximityValue = event.values[0];
            // do a log (for the fun, ok don't do a log...)
            if(proximityValue == 1.0){
                h++;
                TextView t1 = (TextView) findViewById(R.id.nbRepet);
                String j = Integer.toString(h);
                t1.setText(j);

            }

            Log.d(LOG_TAG, "Sensor's values (" + proximityValue + ") and maxRange : " + maxRange);
        }

    }

    // comme le onResume cette fonction stop tout les paramètre de la classe indiqué
    @Override
    protected void onPause()
    {
        // unregister every body
        datasource.close();
        sensorManager.unregisterListener(this, proximity);
        // and don't forget to pause the thread that redraw the xyAccelerationView
        super.onPause();
    }
    // fonction qui permet de changer le accuracy dans proximity
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing to do
        if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
            // do a log (for the fun, ok don't do a log...)
            String accuracyStr;
            if(SensorManager.SENSOR_STATUS_ACCURACY_HIGH==accuracy) {
                accuracyStr="SENSOR_STATUS_ACCURACY_HIGH";

            }else if(SensorManager.SENSOR_STATUS_ACCURACY_LOW==accuracy) {
                accuracyStr="SENSOR_STATUS_ACCURACY_LOW";

            }else if(SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM==accuracy) {
                accuracyStr="SENSOR_STATUS_ACCURACY_MEDIUM";

            }else{
                accuracyStr="SENSOR_STATUS_UNRELIABLE";

            }

            Log.d(LOG_TAG, "Sensor's values (" + proximityValue + ") and accuracy : " + accuracyStr);
            // then redraw

        }
    }
}