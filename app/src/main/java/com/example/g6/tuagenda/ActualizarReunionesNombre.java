package com.example.g6.tuagenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.List;

public class ActualizarReunionesNombre extends AppCompatActivity {


    private Button consultar, cancel, save;
    TextView nombre;
    EditText fecha, hora, ubicacion, cliente, telefono,
            email, descripcion, nota, id, nombreReunion;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);

        consultar = (Button) findViewById(R.id.consultar);
        cancel = (Button) findViewById(R.id.cancel);
        save = (Button) findViewById(R.id.save);

        nombre = (TextView) findViewById(R.id.nombre);
        nombreReunion = (EditText) findViewById(R.id.nombreReunion);
        fecha = (EditText) findViewById(R.id.fecha);
        hora = (EditText) findViewById(R.id.hora);
        ubicacion = (EditText) findViewById(R.id.ubicacion);
        cliente = (EditText) findViewById(R.id.cliente);
        telefono = (EditText) findViewById(R.id.telefono);
        email = (EditText) findViewById(R.id.email);
        descripcion = (EditText) findViewById(R.id.descripcion);
        nota = (EditText) findViewById(R.id.nota);
        id = (EditText) findViewById(R.id.id);


        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CAMBIADO DE 192.168.0.18
                String consulta = "http://192.168.0.18/CursoAndroid/consultaPorNombre.php?nombre=" + nombreReunion.getText();
                ConsultarDatos(consulta);

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

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new ActualizarReunionesNombre.ActualizarDatos().execute("http://192.168.0.18/CursoAndroid/update.php?id="
                        + id.getText().toString()
                        + "&nombre=" + nombre.getText().toString()
                        + "&fecha=" + fecha.getText().toString()
                        + "&ubicacion=" + ubicacion.getText().toString()
                        + "&cliente=" + cliente.getText().toString()
                        + "&telefono=" + telefono.getText().toString()
                        + "&email=" + email.getText().toString()
                        + "&descripcion=" + descripcion.getText().toString()
                        + "&nota=" + nota.getText().toString()
                        + "&hora=" + hora.getText().toString()

                );
            }
        });


    }

    private class ActualizarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), "Se actualizaron los datos correctamente", Toast.LENGTH_LONG).show();

        }
    }

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL", "" + myurl);
        myurl = myurl.replace(" ", "%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public void ConsultarDatos(String URL) {

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
                        CargarDatos(ja);
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

    public void CargarDatos(JSONArray ja) {

        for (int i = 0; i < ja.length(); i += 10) {

            try {
                nombre.setText(ja.getString(i + 1));
                fecha.setText(ja.getString(i + 2));
                ubicacion.setText(ja.getString(i + 3));
                cliente.setText(ja.getString(i + 4));
                telefono.setText(ja.getString(i + 5));
                email.setText(ja.getString(i + 6));
                descripcion.setText(ja.getString(i + 7));
                nota.setText(ja.getString(i + 8));
                hora.setText(ja.getString(i + 9));
                id.setText(ja.getString(i));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}


