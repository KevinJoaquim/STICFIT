package sticfit3.sticfit3;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;



/**
 * Created by kevin on 23/04/2015.
 */
public class Historique extends AppCompatActivity {

    private SeanceDataSource dataSourceSeance;
    SeanceBDD laData = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique);

        dataSourceSeance = new SeanceDataSource(this);
        dataSourceSeance.open();


        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        final ArrayAdapter<SeanceBDD> listAdapter = new ArrayAdapter<SeanceBDD>(this, android.R.layout.simple_list_item_checked, dataSourceSeance.getAllComments());

        final ListView histoList = (ListView) this.findViewById(R.id.listHisto);

        histoList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        histoList.setAdapter(listAdapter);

        setData(null);

        histoList.setAdapter(listAdapter);

        histoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private SeanceDataSource dataSourceSeance;
            SeanceBDD data = null;
            ArrayAdapter<SeanceBDD> listAdapter;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                checkedTextView.toggle();

                listAdapter = (ArrayAdapter<SeanceBDD>) adapterView.getAdapter();
                data = listAdapter.getItem(i);


                if(checkedTextView.isChecked()){
                    setData(data);
                }else{
                    setData(null);
                }

                if(histoList.getCheckedItemIds().length > 0  ){

                }

                setData(data);

            }


        });


        final Button Delete = (Button) findViewById(R.id.delete);
        Delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SeanceBDD data = null;


                if (getLaData() != null) {

                    long position = 0;
                    // for (int i = 0; i < getListAdapter().getCount(); i++)
                    //{
                    data = getLaData();
                    dataSourceSeance.deleteComment(data);
                    listAdapter.remove(data);

                    setData(null);
                    //}
                    histoList.clearChoices();


                }
                listAdapter.notifyDataSetChanged();


            }

        });

        final Button Afficher = (Button) findViewById(R.id.afficher);
        Afficher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SeanceBDD data = null;


                if (getLaData() != null) {

                    data = getLaData();
                    String idSeance = Long.toString(data.getId());
                    setData(null);
                    histoList.clearChoices();
                    Log.i("test", "idSeance :" + data.getId());
                    Intent intent = new Intent(Historique.this, DetailHisto.class);
                    intent.putExtra("idSeance",idSeance);
                    startActivity(intent);

                }
                listAdapter.notifyDataSetChanged();


            }

        });
    }

    public void setData(SeanceBDD data) {
        this.laData = data;
    }

    public SeanceBDD getLaData() {
        return laData;
    }

    @Override
    protected void onResume() {
        dataSourceSeance.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSourceSeance.close();
        super.onPause();
    }
}