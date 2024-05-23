package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CalculadorDeReconocimientos {
    @Setter
    private List<Colaboracion> colaboraciones = new ArrayList<>();

    public float calcularReconocimientoTotal(Colaborador colaborador) {
        float reconocimiento = 0;
        for (Colaboracion colaboracion : colaboraciones) {
            if(colaboracion.getColaborador().equals(colaborador)) {
                reconocimiento +=  colaboracion.calcularReconocimientoParcial();
            }
        }
        return reconocimiento;
    }
}
