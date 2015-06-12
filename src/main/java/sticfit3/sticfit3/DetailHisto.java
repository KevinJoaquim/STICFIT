package sticfit3.sticfit3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by geoffrey on 04/06/2015.
 */
public class DetailHisto extends MainActivity{

    private PompeDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_histo);

        Intent intent = getIntent();
        String idSeance = intent.getStringExtra("idSeance");

        Log.i("test", "test" + idSeance);

        datasource = new PompeDataSource(this);
        datasource.open();

        final ArrayAdapter<PompeBDD> listAdapter = new ArrayAdapter<PompeBDD>(this, android.R.layout.simple_list_item_1, datasource.getCommentById(idSeance));

        final ListView histoList = (ListView) this.findViewById(R.id.detail);

        histoList.setAdapter(listAdapter);
        histoList.setClickable(false);
        histoList.setFocusable(false);

    }

    protected void onPause()
    {
        datasource.close();
        super.onPause();
    }

}
