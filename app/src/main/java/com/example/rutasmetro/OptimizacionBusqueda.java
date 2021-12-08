package com.example.rutasmetro;

import android.location.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OptimizacionBusqueda {
    public static Location busca(String direccion) {
        Location centroCiudad= new Location("");
        centroCiudad.setLatitude(40.4381311);
        centroCiudad.setLongitude(-3.8196205);
        direccion= direccion + " , Madrid";
        Location localizacion;
        try {
            localizacion= consultaLocalizacion ("calle" + direccion, centroCiudad);
            return localizacion;

        } catch (Exception e) {
            return null;
        }
    }

    private static Location consultaLocalizacion(String direccion, Location centroCiudad) throws Exception {
        Location localizacion;
        InputStream entradaDatos;
        URL url = new URL("http://maps.google.com/maps/api/geocode/json?address=" + URLEncoder.encode(direccion, "UTF-8"));
        HttpURLConnection cliente = (HttpURLConnection) url.openConnection();
        cliente.connect();
        try {
            entradaDatos = new BufferedInputStream(cliente.getInputStream());
            leerInputStream(entradaDatos);

        } finally {
            cliente.disconnect();

        }
        StringBuilder cadena = new StringBuilder();
        int caracter = 0;
        while ((entradaDatos.read() != -1)) {
            cadena.append((char) caracter);

        }
        JSONObject objetoJSON = new JSONObject(cadena.toString());
        if (!(objetoJSON.getString("status").equals("OK"))) {
            return null;
        }

        JSONArray direcciones = objetoJSON.getJSONArray(String.valueOf(cadena));
        if (direcciones == null || direcciones.length()==0) {
            return null;

        }
        localizacion = getLocation (direcciones.getJSONObject(0));
        return localizacion;

    }

    private static void leerInputStream(InputStream entradaDatos) {
    }

    private static Location getLocation(JSONObject dir) throws Exception {
        String direccion = dir.getString("Formatted_address");
        direccion = new String(direccion.getBytes("ISO-8859-1"),"UTF-8");
        Location localizacion = new Location(direccion);
        double latitud = dir.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        double longitud = dir.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
        localizacion.setLatitude(latitud);
        localizacion.setLongitude(longitud);
        return localizacion; // aqui vamos

    }

}
