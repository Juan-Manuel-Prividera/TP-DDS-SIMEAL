package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OfertaDTO {
  private final Long id;
  private final Colaborador colaborador;
  private final LocalDate fechaDeRealizacion;
  private final String nombre;
  private final double puntosNecesarios;
  private final String rubroNombre;
  private final Long rubroId;
  private final String imagen;
  private final String productoNombre;
  private final String productoDes;

  public OfertaDTO(Oferta oferta) {
    this.id = oferta.getId();
    this.colaborador = oferta.getColaborador();
    this.fechaDeRealizacion = oferta.getFechaDeRealizacion();
    this.nombre = oferta.getNombre();
    this.puntosNecesarios = oferta.getPuntosNecesarios();
    this.rubroNombre = oferta.getRubro().getNombre();
    this.rubroId = oferta.getRubro().getId();
    this.imagen = oferta.getImagen();
    this.productoNombre = oferta.getProducto().getNombre();
    this.productoDes = oferta.getProducto().getDescripcion();
  }
}
