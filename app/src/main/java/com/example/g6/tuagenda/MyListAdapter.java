package com.example.g6.tuagenda;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Belal on 9/14/2017.
 */

//we need to extend the ArrayAdapter class as we are building an adapter
public class MyListAdapter extends ArrayAdapter<Reunion> {

    //the list values in the List of type hero
    List<Reunion> reunionList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    //constructor initializing the values
    public MyListAdapter(Context context, int resource, List<Reunion> reunionList) {
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
        TextView textViewNombre = view.findViewById(R.id.textViewNombre);
        TextView textViewCliente = view.findViewById(R.id.textViewCliente);
        TextView textViewTelefono = view.findViewById(R.id.textViewTelefono);


        //getting the hero of the specified position
        Reunion reunion = reunionList.get(position);

        //adding values to the list item
        textViewNombre.setText(reunion.getNombre());
        textViewCliente.setText(reunion.getCliente());
        textViewTelefono.setText(reunion.getTelefono());



        //finally returning the view
        return view;
    }


}