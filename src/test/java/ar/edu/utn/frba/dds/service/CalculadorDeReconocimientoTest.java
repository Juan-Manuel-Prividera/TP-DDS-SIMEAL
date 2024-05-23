package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.service.CalculadorDeReconocimientos;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class CalculadorDeReconocimientoTest {
    CalculadorDeReconocimientos calculadorDeReconocimientos;
    Colaborador colaborador;
    List<Colaboracion> colaboraciones;

    @BeforeEach
    public void init(){
        calculadorDeReconocimientos = new CalculadorDeReconocimientos(colaboraciones);
    }

}
