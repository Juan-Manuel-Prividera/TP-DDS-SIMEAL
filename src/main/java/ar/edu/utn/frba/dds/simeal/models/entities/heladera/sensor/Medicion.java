package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipoMedicion"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MedicionMovimiento.class, name = "medicionMovimiento"),
    @JsonSubTypes.Type(value = MedicionTemperatura.class, name = "medicionTemperatura")
})
public interface Medicion {
  void procesar(Heladera heladera);
  boolean esDeTemperatura();

}
