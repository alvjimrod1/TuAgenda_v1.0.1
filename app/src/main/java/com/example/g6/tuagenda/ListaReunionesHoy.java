package com.example.g6.tuagenda;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class ListaReunionesHoy extends AppCompatActivity {


    private Button consultar, cancel;
    ListView listaResultado;
    String fecha;
    Calendar c1 = new GregorianCalendar();
    int dia = c1.get(Calendar.DATE) + 1;
    int mes = c1.get(Calendar.MONTH) + 1;
    int annio = c1.get(Calendar.YEAR);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_reuniones_2);

        // fecha = (TextView) findViewById(R.id.listView);
        listaResultado = (ListView) findViewById(R.id.listView);
        consultar = (Button) findViewById(R.id.consultar);
        cancel = (Button) findViewById(R.id.cancel);


        fecha = (annio + "-" + mes + "-" + dia);


        String consulta = "http://192.168.0.18/CursoAndroid/consultaReunionesHoy.php?fecha=" + fecha;
        EnviarRecibirDatos(consulta);


    }

    public void EnviarRecibirDatos(String URL) {

        Toast.makeText(getApplicationContext(), "" + URL, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListView(JSONArray ja) {

        List<Reunion> lista = new ArrayList<>();

        for (int i = 0; i < ja.length(); i += 3) {

            try {

                lista.add(new Reunion(ja.getString(i), ja.getString(i + 1), ja.getString(i + 2)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        MyListAdapter adapter = new MyListAdapter(this, R.layout.my_custom_list_todo, lista);

        //attaching adapter to the listview
        listaResultado.setAdapter(adapter);


    }
}
