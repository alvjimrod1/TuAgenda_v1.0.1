package com.example.g6.tuagenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import java.util.List;

public class ListaReunionesCliente extends AppCompatActivity {


    private Button consultar, cancel, llamar, buttonDelete;
    ListView listaResultado;
    EditText cliente;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reuniones_cliente);

        // fecha = (TextView) findViewById(R.id.listView);
        listaResultado = (ListView) findViewById(R.id.listView);
        consultar = (Button) findViewById(R.id.consultar);
        cancel = (Button) findViewById(R.id.cancel);
        llamar = (Button) findViewById(R.id.llamar);
        buttonDelete=(Button)findViewById(R.id.buttonDelete);

        cliente = (EditText) findViewById(R.id.cliente);


        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CAMBIADO DE 192.168.0.18
                String consulta = "http://192.168.0.18/CursoAndroid/consultaPorCliente.php?cliente=" + cliente.getText();
                EnviarRecibirDatos(consulta);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == cancel.getId()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }
            }
        });

        llamar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:649130112"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(i);
            }
        });


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

        List<ReunionCliente> lista = new ArrayList<>();

        for (int i = 0; i < ja.length(); i += 3) {

            try {

                lista.add(new ReunionCliente(ja.getString(i), ja.getString(i + 1), ja.getString(i + 2)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        MyListAdapterCliente adapter = new MyListAdapterCliente(this, R.layout.my_custom_list_cliente, lista);

        //attaching adapter to the listview
        listaResultado.setAdapter(adapter);


    }
}
