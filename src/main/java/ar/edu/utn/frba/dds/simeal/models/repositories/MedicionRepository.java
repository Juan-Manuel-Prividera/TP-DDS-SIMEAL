package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import com.itextpdf.text.pdf.PRAcroForm;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MedicionRepository implements Repository<Medicion>{
  private List<Medicion> mediciones;

  public MedicionRepository(){
    mediciones = new ArrayList<>();
  }

  public void guardar(Medicion medicion) {
    mediciones.add(medicion);
    // TODO persistir de verdad
  }

  @Override
  public void eliminar(Long id) {

  }

  @Override
  public void actualizar(Medicion medicion) {

  }

  @Override
  public List<Medicion> obtenerTodos() {
    return List.of();
  }

  public void eliminar(Medicion medicion) {
    mediciones.remove(medicion);
  }


}
