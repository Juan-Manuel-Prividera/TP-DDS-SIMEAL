package ar.edu.utn.frba.dds.service.cargadatos;

import ar.edu.utn.frba.dds.service.cargadorDatos.CargaDatos;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CargaDatosTest {
  CargaDatos cargaDatos;

  @BeforeEach
  public void init(){
    cargaDatos = new CargaDatos("src/main/java/ar/edu/utn/frba/dds/service/cargadorDatos/datos.csv");
  }

  @Test
  public void cargaDatos() throws IOException {
    cargaDatos.leerColaboradores();
  }


}
