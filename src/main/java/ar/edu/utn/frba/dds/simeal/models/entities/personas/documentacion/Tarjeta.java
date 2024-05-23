package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion;

import ar.edu.utn.frba.dds.simeal.models.entities.Retiro;
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
    private int limiteDeUsoDiario = 4, retirosAdicionalesPorMenorACargo = 1;

    public boolean puedeRetirar(){
        return this.fechasRetiros.stream().filter(f->f.getDayOfYear() == LocalDate.now().getDayOfYear()).toList().size()
                <
               this.calcularLimiteDeUso();
    }

    public int calcularLimiteDeUso(){
        return limiteDeUsoDiario + this.personaVulnerable.cantMenoresACargo();
    }

    public void agregarRetiro(Retiro retiro){
        this.fechasRetiros.add(retiro.getFechaRetiro());
    }
}
