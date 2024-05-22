package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter

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
