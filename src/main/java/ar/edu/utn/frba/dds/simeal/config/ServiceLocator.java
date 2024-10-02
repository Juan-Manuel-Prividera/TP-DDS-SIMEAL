package ar.edu.utn.frba.dds.simeal.config;

import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
  private static final Map<String , Repositorio> repositories = new HashMap<>();
  private static final Map<String, Object> controllers = new HashMap<>();
  private static final Map<String,Object> services = new HashMap<>();

  private static final Repositorio repositorio = new Repositorio();
//  // TODO: Ver de hacer como hizo el profe para no tener el bloque estatico
//
//  static {
//    repositories.put(ColaboracionRepository.class.getName(), new ColaboracionRepository());
//    repositories.put(SuscripcionesRepository.class.getName(), new SuscripcionesRepository());
//    repositories.put(ModeloHeladeraRepository.class.getName(), new ModeloHeladeraRepository());
//    repositories.put(ViandaRepository.class.getName(), new ViandaRepository());
//    repositories.put(SensorRepository.class.getName(), new SensorRepository());
//    repositories.put(TarjetaColaboradorRepository.class.getName(), new TarjetaColaboradorRepository());
//    repositories.put(OfertaRepository.class.getName(), new OfertaRepository());
//  }

  public static Repositorio getRepository(Class<? extends Repositorio> repository) {
    String repoName = repository.getName();
    if (!repositories.containsKey(repoName)) {
      try {
        repositories.put(repoName, repository.getDeclaredConstructor().newInstance());
      } catch(Exception e) {
        throw new RuntimeException("No se puede instanciar el tipo de repositorio", e);
      }
    }
    return repositories.get(repoName);
  }

  public static <T> T getController(Class<T> controllerClass) {
    String controllerName = controllerClass.getName();
    if (!controllers.containsKey(controllerName)) {
      try {
        controllers.put(controllerName, controllerClass.getDeclaredConstructor().newInstance());
      } catch (Exception e) {
        throw new RuntimeException("Error al crear el controlador con getDeclareConstructor ", e);
      }
    }
    return (T) controllers.get(controllerName);
  }

  public static <T> T getService(Class<T> serviceClass) {
    String serviceName = serviceClass.getName();
    if (!services.containsKey(serviceName)) {
      try {
        services.put(serviceName, serviceClass.getDeclaredConstructor().newInstance());
      } catch (Exception e) {
        throw new RuntimeException("Error al crear el servicio con getDeclareConstructor ", e);
      }
    }
    return (T) services.get(serviceName);
  }
}
