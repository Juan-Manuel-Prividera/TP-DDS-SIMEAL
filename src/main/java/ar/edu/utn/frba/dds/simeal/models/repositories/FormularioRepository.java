package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;

import javax.persistence.EntityGraph;
import java.util.HashMap;
import java.util.Map;

public class FormularioRepository extends Repositorio{
  public Formulario buscarPorId(Long id) {
      try {
        beginTransaction();
        // Obtener el EntityGraph definido en la entidad Formulario
        EntityGraph<?> entityGraph = entityManager().getEntityGraph("Formulario.preguntas");

        // Usar el EntityGraph para cargar el formulario con las preguntas
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);

        Formulario formulario = entityManager().find(Formulario.class, id, properties);
        commitTransaction();

        if (formulario != null && formulario.getActivo()) {
          return formulario;
        }
        return null;
      } catch (Exception e) {
        rollbackTransaction();
        throw e;
      }
    }


}
