package com.example.rutasmetro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Arrays;

public class Buscador extends AppCompatActivity {

    Lineas [] lineas;
    Rutas ruta;
    private ProgressBar barraProgreso;
    EditText origen, destino;
    Button enviar;
    String direccionOrigen, direccionDestino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
    }

    public void onResume() {

        super.onResume();
        origen=((EditText) findViewById(R.id.origen));
        origen.setText("");
        enviar=((Button) findViewById(R.id.enviar));
        enviar.setAlpha(1);
        enviar.setEnabled(true);
        if (lineas== null) {
            Bundle miBundle = getIntent().getExtras();
            Parcelable [] datos= miBundle.getParcelableArray("LINEAS");
            lineas= Arrays.copyOf(datos, datos.length, Lineas[].class);

        }
        ruta= new Rutas();

    }
    public void leerDirecciones (View vista) {
        enviar.setAlpha(0);
        enviar.setEnabled(false);
        direccionOrigen= origen.getText().toString();
        direccionDestino= destino.getText().toString();
        InputMethodManager introduce= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        introduce.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        barraProgreso= new ProgressBar(this);
        EjecutaSegundoPlano tarea= new EjecutaSegundoPlano();
    }

    private class EjecutaSegundoPlano extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            Location puntoOrigen;
            Location puntoDestino;
            Context contexto = getApplicationContext();
            ConnectivityManager miManager= (ConnectivityManager) contexto.getSystemService(contexto.CONNECTIVITY_SERVICE);
            NetworkInfo estadoRed = miManager.getActiveNetworkInfo();

            if (estadoRed == null || !estadoRed.isConnected() || !estadoRed.isAvailable()) {
                return getString(R.string.error_conexion);
            }
            try {
                puntoOrigen= OptimizacionBusqueda //continuara el codigo en esta parte


            } catch (Exception e) {

            }
            return null;

        }
    }
}