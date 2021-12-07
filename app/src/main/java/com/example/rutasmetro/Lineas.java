package com.example.rutasmetro;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Lineas implements Parcelable {
    public String nombre;
    public Location[] estaciones;
    public int origenRuta;
    public int finalRuta;
    public double datosParadaOrigen;
    public double datosParadaDestino;

    public Lineas() {

    }

    public Lineas(int origenRuta) {
        this.origenRuta = origenRuta;
    }

    @Override
    public void writeToParcel (Parcel parcel, int flags) {
        parcel.writeString(nombre);
        parcel.writeTypedArray(estaciones,0);
        parcel.writeInt(origenRuta);
        parcel.writeInt(finalRuta);
        parcel.writeDouble(datosParadaOrigen);
        parcel.writeDouble(datosParadaDestino);

    }

    public void distancias(Location origen, Location destino) {
        datosParadaOrigen= origen.distanceTo(estaciones [0]);
        datosParadaDestino= destino.distanceTo(estaciones [0]);
        for (int i=1; i < estaciones.length; i++) {
            if (origen.distanceTo(estaciones [i]) < datosParadaOrigen) {
                datosParadaOrigen= origen.distanceTo(estaciones[i]);
                origenRuta= i;
            }
            if (destino.distanceTo(estaciones [i]) < datosParadaDestino) {
                datosParadaDestino= destino.distanceTo(estaciones[i]);
                finalRuta= i;
            }
        }
    }
    public double sumaDisMetros() {
        return datosParadaOrigen + datosParadaDestino;
    }


    public static final Parcelable.Creator<Lineas> CREATOR
            = new Parcelable.Creator<Lineas>() {
        public Lineas createFromParcel(Parcel paquete) {
            return new Lineas(paquete);
        }

        public Lineas[] newArray(int tamagno) {
            return new Lineas[tamagno];
        }
    };



    private Lineas(Parcel parcel) {
        nombre= parcel.readString();
        estaciones= parcel.createTypedArray(Location.CREATOR);
        origenRuta= parcel.readInt();
        finalRuta= parcel.readInt();
        datosParadaOrigen= parcel.readDouble();
        datosParadaDestino= parcel.readDouble();

    }


    @Override
    public int describeContents() {
        return 0;
    }


}
