package com.ieugene.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ieugene.customrecyclerview.TestRecyclerViewActivity;
import com.ieugene.flowlayout.TestFlowLayoutActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.item_list);
        arrayAdapter.add("Test DPRecyclerView");
        arrayAdapter.add("Test FlowLayout");
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class clazz = null;
                if (position == 0) {
                    clazz = TestRecyclerViewActivity.class;
                } else if (position == 1) {
                    clazz = TestFlowLayoutActivity.class;
                }
                if (clazz != null)
                    startActivity(new Intent(MainActivity.this, clazz));
            }
        });
    }

}