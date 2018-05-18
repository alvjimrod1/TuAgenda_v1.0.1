package com.example.g6.tuagenda;


public class ReunionCliente {


    String email, cliente;
    String telefono;


    public ReunionCliente(String cliente, String telefono, String email) {


        this.cliente = cliente;
        this.telefono = telefono;
        this.email = email;

    }


    public String getEmail() {
        return email;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTelefono() {
        return telefono;
    }
}