package com.example.g6.tuagenda;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddMeeting extends AppCompatActivity {


    private EditText nombre,ubicacion,cliente,telefono,email,descripcion,nota;
    private TextView fecha;
    private Spinner hora;

    private Button save,cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        nombre = (EditText) findViewById(R.id.nombre);
        fecha = (TextView) findViewById(R.id.fecha);
        hora = (Spinner) findViewById(R.id.hora);
        ubicacion = (EditText) findViewById(R.id.ubicacion);
        cliente = (EditText) findViewById(R.id.cliente);
        telefono = (EditText) findViewById(R.id.telefono);
        email = (EditText) findViewById(R.id.email);
        descripcion = (EditText) findViewById(R.id.descripcion);
        nota = (EditText) findViewById(R.id.nota);


        Bundle bundle = this.getIntent().getExtras();
        int dia = 0, mes = 0, anyo = 0;
        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        anyo = bundle.getInt("anyo");
        fecha.setText(dia + "-" + mes + "-" + anyo);


        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);


        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new CargarDatos().execute("http://192.168.0.18/CursoAndroid/registro.php?nombre="
                        + nombre.getText().toString()
                        + "&fecha=" + fecha.getText().toString()
                        + "&ubicacion=" + ubicacion.getText().toString()
                        + "&cliente=" + cliente.getText().toString()
                        + "&telefono=" + telefono.getText().toString()
                        + "&email=" + email.getText().toString()
                        + "&descripcion=" + descripcion.getText().toString()
                        + "&nota=" + nota.getText().toString()
                        + "&hora=" + hora.getSelectedItem().toString()

                );
            }
        });


        //cancel.setOnClickListener(this);








/*SPINNER */
        Spinner spinner = (Spinner) findViewById(R.id.hora);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.horas_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "selected "+position, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { Toast.makeText(getApplicationContext(), "none", Toast.LENGTH_LONG).show();
            } });

    }
/*SPINNER*/
    public void btnClickedBack(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    private class CargarDatos extends AsyncTask<String, Void, String> {
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

            Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

        }
    }
    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
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




    }


