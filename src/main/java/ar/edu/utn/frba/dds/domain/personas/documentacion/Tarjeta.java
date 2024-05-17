package ar.edu.utn.frba.dds.domain.personas.documentacion;

import ar.edu.utn.frba.dds.domain.personas.Colaborador;
import ar.edu.utn.frba.dds.domain.personas.PersonaVulnerable;

import java.time.LocalDate;
import java.util.List;

public class Tarjeta {

    private String codigo;
    private PersonaVulnerable personaVulnerable;
    private Colaborador colaborador; // El que entrego la tarjeta
    private List<LocalDate> fechasRetiros;
    private int limiteUsoDiario;

    public boolean puedeRetirar(){
        return false;
    }

    public int calcularLimiteDeUso(){
        return 0;
    }

    public void agregarRetiro(){

    }

}
