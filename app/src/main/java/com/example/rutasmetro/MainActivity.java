package com.example.rutasmetro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barraProgreso= (ProgressBar) findViewById(R.id.barraProgreso);
        barraProgreso.setVisibility(View.VISIBLE);
        Sincroniza comienzo = new Sincroniza();
        comienzo.execute();

    }
    public final String[] LINEAS={"Linea 2","Linea 3","Linea 6"};
    Lineas [] lineas;
    private ProgressBar barraProgreso;
    private class Sincroniza extends AsyncTask<String, Integer, String> {




        @Override
        protected String doInBackground(String... strings) {
            ManejoBBDD bbdd= new ManejoBBDD(getApplicationContext());




            try {
              bbdd.aperturaBBDD(getApplicationContext());
              lineas= bbdd.dameInfoLineas(LINEAS);
              bbdd.cerrarBBDD();


            } catch (Exception e) {
                finish();

            }

            return null;
        }

        protected void onProgressUpdate(Integer... valores) {
            barraProgreso.setProgress(valores[0]);
        }

        protected void onPostExecute(String resultado) {
            comenzar();

        }
        public void comenzar(){
            Bundle miBundle= new Bundle();
            miBundle.putParcelableArray("LINEAS", lineas);


        }

    }
}

