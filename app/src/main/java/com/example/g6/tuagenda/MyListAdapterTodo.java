package com.example.g6.tuagenda;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.util.List;

/**
 * Created by Belal on 9/14/2017.
 */

//we need to extend the ArrayAdapter class as we are building an adapter
public class MyListAdapterTodo extends ArrayAdapter<ReunionTodo> {

    //the list values in the List of type hero
    List<ReunionTodo> reunionList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    //constructor initializing the values
    public MyListAdapterTodo(Context context, int resource, List<ReunionTodo> reunionList) {
        super(context, resource, reunionList);
        this.context = context;
        this.resource = resource;
        this.reunionList = reunionList;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        TextView textViewNombre = view.findViewById(R.id.textViewNombreT);
        TextView textViewCliente = view.findViewById(R.id.textViewClienteT);
        TextView textViewTelefono = view.findViewById(R.id.textViewTelefonoT);
        TextView textViewUbicacion = view.findViewById(R.id.textViewUbicacionT);
        TextView textViewDescripcion = view.findViewById(R.id.textViewDescripcionT);
        TextView textViewEmail = view.findViewById(R.id.textViewEmailT);
        TextView textViewFecha = view.findViewById(R.id.textViewFechaT);
        TextView textViewNota = view.findViewById(R.id.textViewNotaT);
        TextView textViewHora = view.findViewById(R.id.textViewHoraT);


        //getting the hero of the specified position
        ReunionTodo reunion = reunionList.get(position);

        //adding values to the list item
        textViewNombre.setText(reunion.getNombre());
        textViewCliente.setText(reunion.getCliente());
        textViewTelefono.setText(reunion.getTelefono());
        textViewUbicacion.setText(reunion.getUbicacion());
        textViewDescripcion.setText(reunion.getDescripcion());
        textViewEmail.setText(reunion.getEmail());
        textViewFecha.setText(reunion.getFecha());
        textViewNota.setText(reunion.getNota());
        textViewHora.setText(reunion.getHora());
        ImageButton delete = (ImageButton) view.findViewById(R.id.buttonDelete);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will call this method to remove the selected value from the list
                //we are passing the position which is to be removed in the method
                removeReunion(position);
            }
        });

        //finally returning the view
        return view;
    }


    //this method will remove the item from the list

    private void removeReunion(final int position) {
        //Creating an alert dialog to confirm the deletion
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this?");

        //if the response is positive in the alert
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //removing the item
                reunionList.remove(position);


                //reloading the list
                notifyDataSetChanged();
            }
        });

        //if response is negative nothing is being done
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //creating and displaying the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}