package com.example.g6.tuagenda;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ListaReunionesTodo extends AppCompatActivity {


    private Button consultar, cancel; ImageButton buttonDelete;
    ListView listaResultado;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_reuniones_2_todo);

        // fecha = (TextView) findViewById(R.id.listView);
        listaResultado = (ListView) findViewById(R.id.listViewTodo);
        consultar = (Button) findViewById(R.id.consultar);
        cancel = (Button) findViewById(R.id.cancel);

        buttonDelete=(ImageButton) findViewById(R.id.buttonDelete);


       /* buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new CargarDatos().execute("http://192.168.0.18/CursoAndroid/borrarReunion.php?id=10");
            }
        });
*/



        String consulta = "http://192.168.0.18/CursoAndroid/consultaReuniones.php";
        EnviarRecibirDatos(consulta);


    }

    public void EnviarRecibirDatos(String URL) {

        Toast.makeText(getApplicationContext(), "" + URL, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue( this);
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

            try {lista.add(new ReunionTodo(ja.getString(i + 1), ja.getString(i + 2), ja.getString(i + 3), ja.getString(i + 4)
                    , ja.getString(i + 5), ja.getString(i + 6), ja.getString(i + 7), ja.getString(i + 8), ja.getString(i + 9)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        MyListAdapterTodo adapter = new MyListAdapterTodo(this, R.layout.my_custom_list_todo, lista);

        //attaching adapter to the listview
        listaResultado.setAdapter(adapter);


    }

    public class CargarDatos extends AsyncTask<String, Void, String> {
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
