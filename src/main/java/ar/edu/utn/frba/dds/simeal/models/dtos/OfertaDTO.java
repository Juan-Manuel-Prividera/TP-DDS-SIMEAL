package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OfertaDTO {
  private Long id;
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private String nombre;
  private double puntosNecesarios;
  private String rubroNombre;
  private Long rubroId;
  private String imagen;
  private String productoNombre;
  private String productoDes;

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
