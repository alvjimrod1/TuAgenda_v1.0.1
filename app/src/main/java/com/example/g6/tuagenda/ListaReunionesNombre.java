package com.example.g6.tuagenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ListaReunionesNombre extends AppCompatActivity implements View.OnClickListener {


    private Button consultar, cancel;
    ListView listaResultado;
    EditText nombre;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reuniones_nombre);

        // fecha = (TextView) findViewById(R.id.listView);
        listaResultado = (ListView) findViewById(R.id.listView);
        consultar = (Button) findViewById(R.id.consultar);
        cancel = (Button) findViewById(R.id.cancel);
        nombre = (EditText) findViewById(R.id.nombre);


        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CAMBIADO DE 192.168.0.18
                String consulta = "http://192.168.0.18/CursoAndroid/consultaPorNombre.php?nombre=" + nombre.getText();
                EnviarRecibirDatos(consulta);

            }


        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (v.getId() == cancel.getId()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   startActivity(intent);

                }

            }

        });

    }

    public void EnviarRecibirDatos(String URL) {

       // Toast.makeText(getApplicationContext(), "" + URL, Toast.LENGTH_SHORT).show();

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

        List<ReunionTodo> lista = new ArrayList<>();

        for (int i = 0; i < ja.length(); i += 10) {

            try {
                lista.add(new ReunionTodo(ja.getString(i + 1), ja.getString(i + 2), ja.getString(i + 3), ja.getString(i + 4)
                        , ja.getString(i + 5), ja.getString(i + 6), ja.getString(i + 7), ja.getString(i + 8), ja.getString(i + 9)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        MyListAdapterTodo adapter = new MyListAdapterTodo(this, R.layout.my_custom_list_todo, lista);

        //attaching adapter to the listview
        listaResultado.setAdapter(adapter);


    }


    public void onClick(View v) {
        if (v.getId() == cancel.getId()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finish();

        } else {
            this.finish();
            return;
        }

    }

}
