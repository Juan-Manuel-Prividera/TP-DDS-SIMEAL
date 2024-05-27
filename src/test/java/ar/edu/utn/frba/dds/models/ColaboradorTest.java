package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ColaboradorTest {
  Colaborador colaborador;
  Oferta oferta;

  @BeforeEach
  public void init(){
    colaborador = new Colaborador(new Documento(TipoDocumento.DNI,"01234567"),"Juan","Perez"
    );
    oferta = new Oferta(null, 1000);
  }

  @Test
  public void puedeCanjearTest(){
    colaborador.setPuntosDeReconocimientoGastados(100);
    Assertions.assertTrue(colaborador.puedeCanjear(oferta,2000)); // Le quedan 900 puntos al colab
  }
}
