package ar.edu.utn.frba.dds.simeal.config;

import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
  private static final Map<String , Repositorio> repositories = new HashMap<>();
  private static final Map<String, Object> controllers = new HashMap<>();
  private static final Map<String,Object> services = new HashMap<>();

  // Metodo para obtener repositorios manteniendo Singleton
  public static Repositorio getRepository(Class<? extends Repositorio> repository) {
    String repoName = repository.getName();
    // Si el repositorio esta en el Map lo devolvemos y listo
    // Si no esta en el map se crea y guarda en el map para luego retornarlo
    if (!repositories.containsKey(repoName)) {
      try {
        repositories.put(repoName, repository.getDeclaredConstructor().newInstance());
      } catch(Exception e) {
        throw new RuntimeException("No se puede instanciar el tipo de repositorio", e);
      }
    }
    return repositories.get(repoName);
  }

  // Metodo para obtener Controllers manteniendo Singleton
  public static <T> T getController(Class<T> controllerClass) {
    // Toda esta magia oscura se va a ejecutar una vez por controlador gracias a que guardamos t0do en el MAP  :)
    String controllerName = controllerClass.getName();
    // Si el controller ya esta en el map lo devolvemos
    if (!controllers.containsKey(controllerName)) {
      try {
        // Si no, obtenemos el constructor del Controller pedido
        var constructors = controllerClass.getDeclaredConstructors();
        if (constructors.length == 0) {
          throw new RuntimeException("No se encontraron constructores para " + controllerName);
        }

        // Usar el primer constructor disponible
        var constructor = constructors[0];

        // Obtenemos los tipos de parámetros del constructor
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        // Los parametros deberian ser SOLAMENTE o Controllers o Repositorios
        // Para cada tipo de parámetro, obtener la instancia
        Object[] constructorArgs = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
          // Chequeamos si es un Repositorio sino mandamos controlador
          if (Repositorio.class.isAssignableFrom(parameterTypes[i])) {
            constructorArgs[i] = getRepository((Class<? extends Repositorio>) parameterTypes[i]);
          } else {
            // Es un metodo Recursivo pero mientras no tengamos parametros infinitos t0do bien :)
            constructorArgs[i] = getController(parameterTypes[i]);
          }
        }
        // Instanciar el controlador con los parametros obtenidos
        controllers.put(controllerName, constructor.newInstance(constructorArgs));
      } catch (Exception e) {
        throw new RuntimeException("Error al crear el controlador " + controllerName, e);
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
