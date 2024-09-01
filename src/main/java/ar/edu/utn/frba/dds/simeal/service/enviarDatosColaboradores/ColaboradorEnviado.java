package ar.edu.utn.frba.dds.simeal.service.enviarDatosColaboradores;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;

public class ColaboradorEnviado {
    private String nombre;
    private String apellido;
    private String contacto;
    private int donaciones;
    private double puntos;

    public ColaboradorEnviado(Colaborador colaborador, int cantDonaciones, double puntos) {
        this.nombre = colaborador.getNombre();
        this.apellido = colaborador.getApellido();
        this.contacto = colaborador.getContactoPreferido().getInfoDeContacto();
        this.donaciones = cantDonaciones;
        this.puntos = puntos;
    }
}
