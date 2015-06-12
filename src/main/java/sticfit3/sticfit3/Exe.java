package sticfit3.sticfit3;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
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

    MediaPlayer player;

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

        //On récupère les variables passées dans l'intent
        Intent intent = getIntent();
        String exo = intent.getStringExtra("Exercice");
        TextView title = (TextView) findViewById(R.id.Chronometre);
        title.setText(exo);

        //On récupère les infosExe perso si l'utilisateur a choisis
        final String nbSeriePerso = intent.getStringExtra("nbSeriePerso");
        final String nbRepPerso = intent.getStringExtra("nbRepPerso");




        Log.i("test", "Exercice :" + exo);
        Log.i("test", "nbSerie :" + nbSeriePerso);
        Log.i("test", "nbRep :" + nbRepPerso);

        String[] seance = new String[]{exo};
        int nextInt = new Random().nextInt(1);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm ");
        String formattedDate = df.format(c.getTime());

        //On créer dans la  BDD une seance qui contiendra plusieurs pompe (exercices)
        SeanceBDD seanceDetail = dataSourceSeance.createComment(seance[nextInt], formattedDate);
        setSeance(seanceDetail);


        //active le sensor du téléphone
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Instantiate the light and its max range
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        maxRange = 8;//proximity.getMaximumRange();


        //Chronomètre
        SharedPreferenceManager.instance().persistTimeSpentOnLevel(0);
        mChronometer = (Chronometer) findViewById(R.id.chronometer);

        //Boutton Pause pour Chronomètre
        Button Stop = (Button) findViewById(R.id.Pause);
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPauseCH();
            }
        });


        //Boutton reset pour Chronomètre
        Button Effacer = (Button) findViewById(R.id.Reset);
        Effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReset();
            }
        });

        // Boutton Startpour chonomètre
        final Button Start = (Button) findViewById(R.id.Start);
        Start.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                timeWhenStopped = 1;
                mChronometer.setBase(SystemClock.elapsedRealtime() + SharedPreferenceManager.instance().getTimeSpentOnLevel());
                mChronometer.start();

            }
        });

        //Si en mode personnalisée on cache les boutons  du chrono, temps de repos automatique

        if(!nbRepPerso.isEmpty() && !nbSeriePerso.isEmpty()) {
            Start.setVisibility(View.GONE);
            Stop.setVisibility(View.GONE);
            Effacer.setVisibility(View.GONE);
        }

        // boutton exit quitter la page sans sauvegarder
        final Button ExitButton = (Button) findViewById(R.id.Exit);
        ExitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datasource.open();
                datasource.deleteComment(Long.toString(getSeance().getId()));
                dataSourceSeance.deleteCommentById(getSeance().getId());
                Intent intent = new Intent(Exe.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //boutton ajouter serie +1 afficher si pas exePerso
        final Button SerieAdd = (Button) findViewById(R.id.SerieAdd);

        if (nbRepPerso.isEmpty() || nbSeriePerso.isEmpty()) {
            SerieAdd.setVisibility(View.VISIBLE);
        } else {
            SerieAdd.setVisibility(View.GONE);
        }
        SerieAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                save();
                SerieAdd();

            }
        });

        // boutton restAll remettre tout a 0
        Button ResetAll = (Button) findViewById(R.id.ResetAll);

        //Je cache le reset ALL, pas très utile et désordre dans la bdd

        ResetAll.setVisibility(View.GONE);
        ResetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // serie = 0
                TextView t1 = (TextView) findViewById(R.id.nbSerie);
                i = 1;
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

        //boutton sauvegarder tout
        final Button SaveAll = (Button) findViewById(R.id.SaveAll);
        SaveAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Sauvegarde de la bdd historique
                save();

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("Votre séance a bien été sauvegardée");

                Toast toast = new Toast(getApplicationContext());
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                //redirection accueil
                Intent intent = new Intent(Exe.this, MainActivity.class);
                startActivity(intent);

            }
        });


            //Replacer le code de ce boutton dans le capteur de proximité (déja fait)
            //On cache ce bouton utiliser que pour le mode développement virtuel
           final Button Rep1 = (Button) findViewById(R.id.Rep1);

            Rep1.setVisibility(View.VISIBLE);
            Rep1.setOnClickListener(new View.OnClickListener() {

                Intent intent = getIntent();
                String exo = intent.getStringExtra("Exercice");
            @Override
            public void onClick(View v) {
                datasource.open();
                if(!nbRepPerso.isEmpty() && !nbSeriePerso.isEmpty()) {
                    //On verifie si h est superieur ou égale a nbRepPerso - 1 car sinon on répétition en trop
                    if (h >= Integer.parseInt(nbRepPerso)-1){

                        if(i==Integer.parseInt(nbSeriePerso) && h == Integer.parseInt(nbRepPerso)-1){

                            Log.i("test","seance fini");

                            h++;
                            TextView t1 = (TextView) findViewById(R.id.nbRepet);
                            String j = Integer.toString(h);
                            t1.setText(j);
                            CalculKal(exo);

                            player = MediaPlayer.create(Exe.this, R.raw.airhorn);
                            player.start();

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

                            TextView text = (TextView) layout.findViewById(R.id.text);
                            text.setText("Séance terminée ! Bravo!");

                            Toast toast = new Toast(getApplicationContext());
                            toast.setView(layout);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();

                            Rep1.setVisibility(View.GONE);


                        }else {

                            Log.i("test", "Rep = 0 et serie+1");
                            h++;
                            //On incrémente la derniere répétition en arrière plan avant de save et de remettre compteur de rep a 0
                            TextView t1 = (TextView) findViewById(R.id.nbRepet);
                            String j = Integer.toString(h);
                            t1.setText(j);
                            CalculKal(exo);

                            //On save et on incrémente une série
                            save();
                            SerieAdd();
                            player = MediaPlayer.create(Exe.this, R.raw.airhorn);
                            player.start();
                            sensorManager.unregisterListener(Exe.this, proximity);
                            Rep1.setVisibility(View.GONE);
                            Start.performClick();
                            mChronometer.setOnChronometerTickListener(
                                new Chronometer.OnChronometerTickListener(){
                                    Button Stop = (Button) findViewById(R.id.Pause);
                                    Button Effacer = (Button) findViewById(R.id.Reset);

                                    public void onChronometerTick(Chronometer chronometer){
                                        long myElapsedMillis=SystemClock.elapsedRealtime() - mChronometer.getBase();

                                        final int minRepos =  Integer.valueOf(intent.getStringExtra("minRepos"));
                                        final int secRepos = Integer.valueOf(intent.getStringExtra("secRepos"));

                                        if(myElapsedMillis>=tempsRepos(minRepos,secRepos)){
                                            //Le reset remet a 0
                                            Effacer.performClick();
                                            //Le pause garde le chrono a 0 pour eviter la boucle
                                            Stop.performClick();

                                            player = MediaPlayer.create(Exe.this, R.raw.airhorn);
                                            player.start();
                                            Rep1.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                            }


                    }else{
                        h++;
                        TextView t1 = (TextView) findViewById(R.id.nbRepet);
                        String j = Integer.toString(h);
                        t1.setText(j);
                        CalculKal(exo);
                    }
                }else {

                        h++;
                        TextView t1 = (TextView) findViewById(R.id.nbRepet);
                        String j = Integer.toString(h);
                        t1.setText(j);
                        CalculKal(exo);


                }
            }
        });


    }

    public long tempsRepos(int minutes, int secondes){
        long tempsTotal;
        minutes = minutes*60*1000;
        secondes = secondes*1000;
        tempsTotal=minutes+secondes;
        return tempsTotal;
    }


    //Pour passer les infos Séance d'une fonction a une autre je passe par ses getter setter
    public void setSeance(SeanceBDD seance){

        this.laSeance=seance;
    }

    public SeanceBDD getSeance() {

        return laSeance;
    }


    //Fonction sauvegarder tout
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

        //On save les details de la séance, a chaque série
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
        timeWhenStopped = 1;
        onPauseCH();

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
        //Calcul de calorie pour les pompes, estimation de 1 pompes = 2 secondes soit 2 x 0.14(valeur en calorie d'une pompe) = 0.28
        if (exo.equals("Pompe")) {
                        double calorie = 0.28;
                        calfinal = calorie + calfinal;
                                TextView t2 = (TextView) findViewById(R.id.kl);
                                String cal = Double.toString(calfinal);
                                t2.setText(cal.substring(0, 3));
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

        Intent intent = getIntent();
        String exo = intent.getStringExtra("Exercice");
        final String nbSeriePerso = intent.getStringExtra("nbSeriePerso");
        final String nbRepPerso = intent.getStringExtra("nbRepPerso");


        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            // the light value
            proximityValue = event.values[0];
            // do a log (for the fun, ok don't do a log...)
            if(proximityValue == 1.0){
                datasource.open();
                if(!nbRepPerso.isEmpty() && !nbSeriePerso.isEmpty()) {

                    //On verifie si le nombre de Rep est superieur ou égale à nbRepPerso - 1 car sinon une répétition en trop
                    if (h >= Integer.parseInt(nbRepPerso)-1){

                        //Condition si les séries et repétitions sont atteint au max !
                        if(i==Integer.parseInt(nbSeriePerso) && h == Integer.parseInt(nbRepPerso)-1){

                            Log.i("test","seance fini");

                            //On incrémente de 1 pour afficher le total de repetitions
                            h++;
                            TextView t1 = (TextView) findViewById(R.id.nbRepet);
                            String j = Integer.toString(h);
                            t1.setText(j);
                            CalculKal(exo);

                            //Avertisseur sonore fin de séance
                            player = MediaPlayer.create(Exe.this, R.raw.airhorn);
                            player.start();

                            //On indique que la séance est terminée
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toastsave, (ViewGroup) findViewById(R.id.toast_layout_root));

                            TextView text = (TextView) layout.findViewById(R.id.text);
                            text.setText("Séance terminée ! Bravo!");

                            Toast toast = new Toast(getApplicationContext());
                            toast.setView(layout);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();

                            //DESACTIVER LE CAPTEUR ICI ! Seance terminée
                            sensorManager.unregisterListener(Exe.this, proximity);


                        }else {
                            //Si pas tout atteint au max on continue nouvelle série, lancement chrono et desactiver capteur
                            Log.i("test", "Rep = 0 et serie+1");

                            h++;
                            //On incrémente la derniere répétition en arrière plan avant de save et de remettre compteur de rep a 0
                            TextView t1 = (TextView) findViewById(R.id.nbRepet);
                            String j = Integer.toString(h);
                            t1.setText(j);
                            CalculKal(exo);

                            //On save et on incrémente la série
                            save();
                            SerieAdd();

                            //Bip sonore fin de série
                            player = MediaPlayer.create(Exe.this, R.raw.airhorn);
                            player.start();

                            //Désactiver capteur !
                            sensorManager.unregisterListener(Exe.this, proximity);
                            Log.i("test","Capteur Désactivé");

                            //On lance le chrono
                            final Button Start = (Button) findViewById(R.id.Start);
                            Start.performClick();
                            mChronometer.setOnChronometerTickListener(
                                    new Chronometer.OnChronometerTickListener(){
                                        //On rend les boutons accessibles dans cette fonction
                                        Button Stop = (Button) findViewById(R.id.Pause);
                                        Button Effacer = (Button) findViewById(R.id.Reset);
                                        Intent intent = getIntent();
                                        public void onChronometerTick(Chronometer chronometer){

                                            //On recupère en temps réel
                                            long myElapsedMillis=SystemClock.elapsedRealtime() - mChronometer.getBase();
                                            final int minRepos =  Integer.valueOf(intent.getStringExtra("minRepos"));
                                            final int secRepos = Integer.valueOf(intent.getStringExtra("secRepos"));

                                            if(myElapsedMillis>=tempsRepos(minRepos,secRepos)){
                                                //Le reset remet a 0seconde
                                                Effacer.performClick();
                                                //Le pause garde le chrono a 0seconde pour eviter la boucle
                                                Stop.performClick();

                                                //Bip  sonore reprise de repetition
                                                player = MediaPlayer.create(Exe.this, R.raw.airhorn);
                                                player.start();

                                                //REACTIVER LE CAPTEUR
                                                sensorManager.registerListener(Exe.this, proximity, SensorManager.SENSOR_DELAY_FASTEST);
                                                Log.i("test","Capteur activé");
                                            }
                                        }
                                    });
                        }


                    }else{
                        //Ici incrémentation des repetitions tant que h(repetition) est inferieur a nbRepPerso
                        h++;
                        TextView t1 = (TextView) findViewById(R.id.nbRepet);
                        String j = Integer.toString(h);
                        t1.setText(j);
                        CalculKal(exo);
                    }
                }else {
                    //Ici c'est l'incrémentation normal losque l'utilisateur est en mode libre
                    h++;
                    TextView t1 = (TextView) findViewById(R.id.nbRepet);
                    String j = Integer.toString(h);
                    t1.setText(j);
                    CalculKal(exo);


                }

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