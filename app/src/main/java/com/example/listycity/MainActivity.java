package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addBtn, deleteBtn, confirmBtn;
    LinearLayout addBar;
    EditText cityInput;
    int selectedIndex = -1; //Track currently selected city(-1 means no city selected)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton","Vancouver","Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka","New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        //buttons
        addBtn = findViewById(R.id.btn_add_city);
        deleteBtn = findViewById(R.id.btn_delete_city);
        confirmBtn = findViewById(R.id.btn_confirm);

        //input bar
        addBar = findViewById(R.id.add_bar);
        cityInput = findViewById(R.id.city_input);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;       //selected row index to delete
            cityList.setItemChecked(position, true);    //highlight selected city in the listView
        });

        addBtn.setOnClickListener(v -> {
            addBar.setVisibility(View.VISIBLE);     //Show add city bar
            cityInput.requestFocus();
        });

        confirmBtn.setOnClickListener(v -> {
            String name = cityInput.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "City name cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(name);
            cityAdapter.notifyDataSetChanged();     //Refresh listView after data changes

            cityInput.setText("");
            addBar.setVisibility(View.GONE);        //Hide add city bar
        });

        deleteBtn.setOnClickListener(v -> {
            if (selectedIndex < 0 || selectedIndex >= dataList.size()) {
                Toast.makeText(this, "Tap a city first to select it.", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedIndex);
            cityAdapter.notifyDataSetChanged();

            cityList.clearChoices();    //Clear selection highlight after delete
            selectedIndex = -1;
        });
    }
}
