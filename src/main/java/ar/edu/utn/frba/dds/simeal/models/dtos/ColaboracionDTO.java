package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DarDeAltaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donardinero.DonarDinero;
import com.itextpdf.text.pdf.PRAcroForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColaboracionDTO {
  private String tipo;
  private String fechaRealizacion;
  private String cantidad;

  public ColaboracionDTO(ColaboracionPuntuable colaboracion) {
    if (colaboracion.getClass().equals(DonarVianda.class)) {
      this.tipo = "Donacion de vianda";
    } else if (colaboracion.getClass().equals(DonarDinero.class)) {
      this.tipo = "Donacion de dinero";
    } else if (colaboracion.getClass().equals(DistribuirVianda.class)) {
      this.tipo = "Distribución de vianda";
    } else if (colaboracion.getClass().equals(AdherirHeladera.class)) {
      this.tipo = "Adherir heladera";
    } else if (colaboracion.getClass().equals(DarDeAltaPersonaVulnerable.class)) {
      this.tipo = "Dar de alta a persona vulnerable";
    } else {
      this.tipo = null;
    }
    this.fechaRealizacion = colaboracion.getFechaDeRealizacion().toString();
    this.cantidad = colaboracion.getCantidad();
  }
}

