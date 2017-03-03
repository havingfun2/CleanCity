package net.simplifiedcoding.androidimageupload;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.simplifiedcoding.androidimageupload.adapters.CustomAdapter;
import net.simplifiedcoding.androidimageupload.models.DataModel;
import net.simplifiedcoding.androidimageupload.models.MyData;

import java.util.ArrayList;

/**
 * Created by dragoon on 18-Jan-17.
 */
public class CheckComplaint extends Activity {
    private RecyclerView recycler;
    private CustomAdapter adapter;
    ArrayList<DataModel> data;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_complaint);
        PrefManager pref = new PrefManager(this);
        pref.setEvent("0");
        recycler = (RecyclerView) findViewById(R.id.recycler);
        setupRecycler();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.com_date.size(); i++) {
            data.add(new DataModel(
                    MyData.com_date.get(i),
                    MyData.com_id.get(i),
                    MyData.com_image.get(i),
                    MyData.com_status.get(i)
            ));
        }


        adapter = new CustomAdapter(CheckComplaint.this,data);
        recycler.setAdapter(adapter);

    }
}
