package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;

import java.util.List;

public class CalculadorDeReconocimientos {
    List<Colaboracion> listaColaboraciones;

    public float calcularReconocimiento(Colaborador colaborador){
        float reconocimiento = 0;
        for(Colaboracion colaboracion : listaColaboraciones){
            if(colaboracion.getColaborador().equals(colaborador)){
                reconocimiento += colaboracion.calcularReconocimientoParcial();
            }
        }
        return reconocimiento;
    }
}
