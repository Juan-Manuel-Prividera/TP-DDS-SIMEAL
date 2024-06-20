package ar.edu.utn.frba.dds.simeal.models.entities.vianda;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vianda {
  private TipoDeComida tipoDeComida;
  private LocalDate fechaCaducidad;
  private LocalDate fechaDonacion;
  private Colaborador colaborador;
  private int calorias;
  private Heladera heladera;
  private boolean entregada;

  private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

  public Vianda(Heladera heladera) {
    this.heladera = heladera;
  }

  public void moverA(Heladera heladera) {
    retirar();
    ingresarA(heladera);
  }

  public void retirar() {
    // Esto avisa cuando se retira una vianda de una heladera
    changeSupport.firePropertyChange("retirar", this.heladera, null);
    this.heladera = null;
    // TODO: Cambiar de lugar el entregada = true si se retira para mover no esta entregada :(
    entregada = true;
  }

  private void ingresarA(Heladera heladera) {
    // Esto avisa cuando se ingresa una vianda a una heladera
    changeSupport.firePropertyChange("ingresarA", heladera, null);
    this.heladera = heladera;
  }

  public void agregarOyente(PropertyChangeListener listener) {
    changeSupport.addPropertyChangeListener(listener);
  }
}
