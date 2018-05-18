package com.example.g6.tuagenda;


public class Reunion {


    String nombre, cliente;
    String telefono;


    public Reunion(String nombre, String cliente, String telefono) {

        this.nombre = nombre;
        this.cliente = cliente;
        this.telefono = telefono;

    }


    public String getNombre() {
        return nombre;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTelefono() {


        return telefono.substring(0,5);
    }
}