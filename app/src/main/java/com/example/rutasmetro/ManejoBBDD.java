package com.example.rutasmetro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;



public class ManejoBBDD extends SQLiteOpenHelper {
    public String rutaAlmacenamiento;
    public SQLiteDatabase bbdd;


    public ManejoBBDD(@Nullable Context contexto) {
        super(contexto, "paradaMetro.db3", null, 1);
        rutaAlmacenamiento= contexto.getFilesDir().getParentFile().getPath() + "paradasMetro.db3";
    }


    public void aperturaBBDD (Context contexto) {
        try {
            SQLiteDatabase bbdd = SQLiteDatabase.openDatabase(rutaAlmacenamiento, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (Exception e) {
            copiaBBDD (contexto);
            SQLiteDatabase bbdd = SQLiteDatabase.openDatabase(rutaAlmacenamiento, null, SQLiteDatabase.OPEN_READONLY);

        }

    }

    public void copiaBBDD (Context contexto) {
        try {
            InputStream datosEntrada= contexto.getAssets().open("paradasMetro.db3");
            OutputStream datosSalida= new FileOutputStream(rutaAlmacenamiento);
            byte[] bufferBBDD= new byte[1024];
            int longitud;
            while ((longitud= datosEntrada.read(bufferBBDD))>0) {
                datosSalida.flush();
                datosSalida.close();
                datosEntrada.close();
            };
        } catch (Exception e) {

        }


    }

    public Location datosLocation (int id) {
        Location estacion;
        Cursor miCursor;
        miCursor= bbdd.rawQuery("SELECT * FROM paradas WHERE ID =" + id,null);
        miCursor.moveToFirst();
        estacion= new Location(miCursor.getString(1));
        estacion.setLatitude(Double.parseDouble(miCursor.getString(2)));
        estacion.setLongitude(Double.parseDouble(miCursor.getString(3)));
        miCursor.close();
        return estacion;
    }

    public Lineas[] dameInfoLineas(String [] nombreDeLineas) {
        Lineas[] lasLineas= new Lineas[nombreDeLineas.length];
        Cursor miCursor= null;
        for (int i = 0; i < nombreDeLineas.length; i++) {
            lasLineas [i]= new Lineas();
            lasLineas [i].nombre= nombreDeLineas[i];
            miCursor= bbdd.rawQuery("SELECT ID FROM" + nombreDeLineas[i],null);
            lasLineas[i].estaciones= new Location[miCursor.getCount()];
            int contador=0;
            miCursor.moveToFirst();

            while (!miCursor.isAfterLast()) {
                int estacion= Integer.parseInt(miCursor.getString(0));
                lasLineas[i].estaciones[contador]= datosLocation(estacion);
                contador++;
                miCursor.moveToNext();
            }

        }
        if (miCursor != null && !miCursor.isClosed())
            miCursor.close();
        return lasLineas;

    }

    public void cerrarBBDD () {
        bbdd.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
