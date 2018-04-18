package com.example.john.hbb.activities.scoreboard;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.john.hbb.R;
import com.example.john.hbb.core.Constants;
import com.example.john.hbb.db_operations.User;

import java.util.ArrayList;
import java.util.List;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnPxWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.EndlessOnScrollListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

/**
 * Created by john on 3/26/18.
 */

public class ScoreTableActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    //private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" },
     //       { "and", "a", "second", "test" } };
    private static final String[] TABLE_HEADERS = { "#","NAME", "Q-I", "Q-II", "Q-III" , "Q-IV", "TOTAL"};
    private List<String[]> dataL = new ArrayList<>();
    private TableView<String[]> tableView;

    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

       // tableLayout = (TableLayout) findViewById(R.id.main_table);
        tableView = (TableView<String[]>) findViewById(R.id.tableView);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));

        tableView.setHeaderBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        tableView.addOnScrollListener(new MyEndlessOnScrollListener());
        int colorEvenRows = getResources().getColor(android.R.color.white);
        int colorOddRows = getResources().getColor(android.R.color.darker_gray);


        //tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));



        TableColumnWeightModel columnModel = new TableColumnWeightModel(7);
        columnModel.setColumnWeight(0, 1);
        columnModel.setColumnWeight(1, 3);
        columnModel.setColumnWeight(2, 1);
        columnModel.setColumnWeight(3, 1);
        columnModel.setColumnWeight(4, 1);
        columnModel.setColumnWeight(5, 1);
        columnModel.setColumnWeight(6, 1);

        tableView.setColumnModel(columnModel);


        setList();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    private void setList(){
        try{
            Cursor cursor = new User(context).selectAll();
            int cc = 0;
            if (cursor.moveToFirst()){
                do {
                    cc ++;
                    String[] data = new String[7];

                    data[0] = cc+" )";
                    data[1] = cursor.getString(cursor.getColumnIndex(Constants.config.FIRST_NAME))+" "+
                            cursor.getString(cursor.getColumnIndex(Constants.config.LAST_NAME));
                    int total = 0;
                    for (int j=2; j<6; j++){
                        data[j] = ""+j;
                        total += j;
                    }
                    data[data.length-1] = total+"";
                    dataL.add(data);
                }while (cursor.moveToNext());
            }
            String [][] theArray = dataL.toArray( new String[dataL.size()][] );
            tableView.setDataAdapter(new SimpleTableDataAdapter(this, theArray));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private class MyEndlessOnScrollListener extends EndlessOnScrollListener {

        @Override
        public void onReloadingTriggered(final int firstRowItem, final int visibleRowCount, final int totalRowCount) {
            // show a loading view to the user
            // reload some data
            // add the loaded data to the adapter
            // hide the loading view
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.score_board, menu);
        return true;
    }

}
