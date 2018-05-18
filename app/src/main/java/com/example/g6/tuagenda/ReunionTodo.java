package com.example.g6.tuagenda;


public class ReunionTodo {


    String nombre, cliente;
    String telefono, ubicacion, fecha, hora, descripcion,email,nota;


    public ReunionTodo(String nombre, String cliente, String telefono, String ubicacion,
                       String fecha,String email, String hora, String descripcion, String nota) {

        this.nombre = nombre;
        this.cliente = cliente;
        this.telefono = telefono;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.email = email;
        this.hora = hora;
        this.descripcion = descripcion;
        this.nota = nota;

    }


    public String getNombre() {
        return nombre;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTelefono() {
        return telefono;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public String getEmail() {
        return email;
    }
    public String getFecha() {
        return fecha;
    }
    public String getHora() {

        return hora;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getNota() {
        return nota.substring(0,5);
    }
}