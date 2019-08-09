package com.tarefasassincronas.cursoandroid.tarefasassincronas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServicoActivity extends AppCompatActivity {
    private String cargo, servico, profissional;

    private Spinner spServico, spCargo, spProfissional;
    private Button btnSubmit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        cargo = null;
        servico = null;
        profissional = null;

        spServico = findViewById(R.id.spinner1);
        spCargo = findViewById(R.id.spinner2);
        spProfissional = findViewById(R.id.spinner3);
        btnSubmit = findViewById(R.id.btnSubmit);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                cargo = extras.getString("cargo");
                servico = extras.getString("servico");
                profissional = extras.getString("profissional");
            }

        }

        addCargoSpinner();
        addServicoSpinner();
        addProfissionalSpinner();

    }

    public void addCargoSpinner() {
        String list = cargo;
        List<String> spList = new ArrayList<String>();
        try {
            JSONArray jList = new JSONArray(list);
            for (Integer i = 0; i < jList.length(); i++) {
                JSONObject jsnobject = jList.getJSONObject(i);
                spList.add(jsnobject.get("descricao").toString());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, spList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCargo.setAdapter(dataAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addServicoSpinner() {
        String list = servico;
        List<String> spList = new ArrayList<String>();
        try {
            JSONArray jList = new JSONArray(list);
            for (Integer i = 0; i < jList.length(); i++) {
                JSONObject jsnobject = jList.getJSONObject(i);
                spList.add(jsnobject.get("").toString());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, spList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCargo.setAdapter(dataAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addProfissionalSpinner() {
        String list = profissional;
        List<String> spList = new ArrayList<String>();
        try {
            JSONArray jList = new JSONArray(list);
            for (Integer i = 0; i < jList.length(); i++) {
                JSONObject jsnobject = jList.getJSONObject(i);
                spList.add(jsnobject.get("nome").toString());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, spList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spProfissional.setAdapter(dataAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void submit(View view) {
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {

        Toast.makeText(ServicoActivity.this,
                "OnClickListener : " +
                        "\nSpinner 1 : " + (spServico.getSelectedItem()) +
                        "\nSpinner 2 : " + (spCargo.getSelectedItem()) +
                        "\nSpinner 3 : " + (spProfissional.getSelectedItem()),
                Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}
