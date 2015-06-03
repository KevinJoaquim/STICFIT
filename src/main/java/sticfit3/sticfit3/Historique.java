package sticfit3.sticfit3;

import android.app.ListActivity;
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

    private PompeDataSource datasource;
    PompeBDD laData = null;
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique);

        datasource = new PompeDataSource(this);
        datasource.open();


        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        final ArrayAdapter<PompeBDD> listAdapter = new ArrayAdapter<PompeBDD>(this, android.R.layout.simple_list_item_checked, datasource.getAllComments());

        final ListView histoList = (ListView) this.findViewById(R.id.listHisto);

        histoList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        histoList.setAdapter(listAdapter);

        setData(null);

        histoList.setAdapter(listAdapter);

        histoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private PompeDataSource datasource;
            PompeBDD data = null;
            ArrayAdapter<PompeBDD> listAdapter;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                checkedTextView.toggle();

                listAdapter = (ArrayAdapter<PompeBDD>) adapterView.getAdapter();
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

                PompeBDD data = null;


                if (getLaData() != null) {

                    long position = 0;
                    // for (int i = 0; i < getListAdapter().getCount(); i++)
                    //{
                    data = getLaData();
                    datasource.deleteComment(data);
                    listAdapter.remove(data);

                    setData(null);
                    //}
                    histoList.clearChoices();


                }
                listAdapter.notifyDataSetChanged();


            }

        });
    }

    public void setData(PompeBDD data) {
        this.laData = data;
    }

    public PompeBDD getLaData() {
        return laData;
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}