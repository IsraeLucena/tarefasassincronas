package com.tarefasassincronas.cursoandroid.tarefasassincronas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        MyAsyncTask task = new MyAsyncTask();
        String[] urlApi = {"http://appsalaozumbaia.com/public/api/getCargos",
                "http://appsalaozumbaia.com/public/api/getServico",
                "http://appsalaozumbaia.com/public/api/getProfissional"};

        task.execute(urlApi);

    }

    /*
     * 1 -> Parâmetro a ser passado para a classe / Void
     * 2 -> Tipo de valor que será utilizado para
     * o progresso da tarefa
     * 3 -> Retorno após tarefa finalizada
     * */
    class MyAsyncTask extends AsyncTask<String, Integer, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... strings) {
            publishProgress(0);
            String[] stringResult = new  String[3];
            for (Integer i = 0; i < 3; i++) {
                String stringUrl = strings[i];
                InputStream inputStream = null;
                InputStreamReader inputStreamReader = null;
                StringBuffer buffer = null;

                try {

                    URL url = new URL(stringUrl);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                    // Recupera os dados em Bytes
                    inputStream = conexao.getInputStream();

                    //inputStreamReader lê os dados em Bytes e decodifica para caracteres
                    inputStreamReader = new InputStreamReader(inputStream);

                    //Objeto utilizado para leitura dos caracteres do InpuStreamReader
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    buffer = new StringBuffer();
                    String linha = "";

                    while ((linha = reader.readLine()) != null) {
                        buffer.append(linha);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /* Leitura do Json*/
                String objetoValor = null;

                try {
                    JSONObject jsonObject = new JSONObject(buffer.toString());
                    objetoValor = jsonObject.getString("data");
                    stringResult[i] = objetoValor;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(1);
            return stringResult;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
//            Toast.makeText(MainActivity.this,
//                    s[0], Toast.LENGTH_SHORT).show();
            progressBar.setProgress(0);
            progressBar.setVisibility(View.INVISIBLE);
            if(s.length > 0) {
                Intent i = new Intent(getApplicationContext(), ServicoActivity.class);
                i.putExtra("cargo", s[0]);
                i.putExtra("servico", s[1]);
                i.putExtra("profissional", s[2]);
                startActivity(i);
            }else{
                Toast.makeText(MainActivity.this,
                    "Não foi possível carregar os dados!", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
