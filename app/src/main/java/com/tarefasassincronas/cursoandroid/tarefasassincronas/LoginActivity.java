package com.tarefasassincronas.cursoandroid.tarefasassincronas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText txtLogin;
    private EditText txtSenha;
    private ProgressBar progressBar;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressBar);
        txtLogin = findViewById(R.id.login);
        txtSenha = findViewById(R.id.senha);


    }

    public void login(View view) {
        String login = txtLogin.getText().toString();
        String senha = txtSenha.getText().toString();
        CallAPI task = new CallAPI();
        task.execute("http://appsalaozumbaia.com/public/api/postLogin?username=victor&password=123456");
        //task.execute("http://appsalaozumbaia.com/public/api/postLogin?username=" + login + "&password=" + senha);
    }

    private void makePost() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("login", "victor")
                .addFormDataPart("password", "123456")
                .build();

        Request request = new Request.Builder()
                .url("http://appsalaozumbaia.com/public/api/postLogin")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            System.out.println(response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class CallAPI extends AsyncTask<String, Integer, Boolean> {

        public CallAPI() {
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            publishProgress(0);
            String urlString = params[0]; // URL to call
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("", "")
                    .build();

            Request request = new Request.Builder()
                    .url(urlString)
                    .post(requestBody)
                    .build();

            Log.d("request", request.toString());
            Boolean responseMensage = false;
            try (Response response = client.newCall(request).execute()) {
                Log.d("response", response.toString());
                responseMensage = response.isSuccessful();
                if (responseMensage) {
                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                }
                System.out.println(response.body().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            publishProgress(1);
            return responseMensage;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.INVISIBLE);
            if (b) {
                Toast.makeText(LoginActivity.this,
                       "Sucesso no Login !", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }else {
                Toast.makeText(LoginActivity.this,
                        "Falha no Login, Tente Novamente!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
