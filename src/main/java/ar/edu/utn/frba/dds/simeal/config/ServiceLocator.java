package ar.edu.utn.frba.dds.simeal.config;

import ar.edu.utn.frba.dds.simeal.controllers.OfertasController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.CambioModoController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.MigracionController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.ReportesController;
import ar.edu.utn.frba.dds.simeal.models.repositories.*;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.cargadordatos.LectorCsv;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.EnviadorDeMails;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.whatsapp.EnviadorDeWpp;


import javax.media.jai.operator.AbsoluteDescriptor;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
  private static final Map<String , Repositorio> repositories = new HashMap<>();
  private static final Map<String, Object> controllers = new HashMap<>();
  private static final Map<String,Object> services = new HashMap<>();

  private static final Repositorio repositorio = new Repositorio();
  // TODO: Ver de hacer como hizo el profe para no tener el bloque estatico

  static {
    repositories.put(ColaboracionRepository.class.getName(), new ColaboracionRepository());
    repositories.put(SuscripcionesRepository.class.getName(), new SuscripcionesRepository());
    repositories.put(ModeloHeladeraRepository.class.getName(), new ModeloHeladeraRepository());
    repositories.put(ViandaRepository.class.getName(), new ViandaRepository());
    repositories.put(SensorRepository.class.getName(), new SensorRepository());
    repositories.put(TarjetaColaboradorRepository.class.getName(), new TarjetaColaboradorRepository());
  }

  public static void addRepository(String tipoRepo, Repositorio repository) {
    repositories.put(tipoRepo, repository);
  }

  public static Repositorio getRepository(Class<? extends Repositorio> repository) {
    // Devuelve el valor de la key repositoryName y sino hay devuelve el default que seria repositorio
    return repositories.getOrDefault(repository.getName(), repositorio);
  }

  public static <T> T getController(Class<T> controllerClass) {
    String controllerName = controllerClass.getName();
    if (controllerName.equals(MigracionController.class.getName())) {
      if (!controllers.containsKey(controllerName)) {
        controllers.put(controllerName, new MigracionController());
      }
    } else if (controllerName.equals(ReportesController.class.getName())) {
      if(!controllers.containsKey(controllerName)) {
        controllers.put(controllerName, new ReportesController());
      }
    } else if (controllerName.equals(CambioModoController.class.getName())) {
      if (!controllers.containsKey(controllerName)) {
        controllers.put(controllerName, new CambioModoController());
      }
    }
    if (controllerName.equals(OfertasController.class.getName())){
      if (!controllers.containsKey(controllerName)) {
        controllers.put(controllerName, new OfertasController());
      }
    }
    return (T) controllers.get(controllerName);
  }

  public static <T> T getService(Class<T> serviceClass) {
    String serviceName = serviceClass.getName();
    if(serviceName.equals(LectorCsv.class.getName())){
      if(!services.containsKey(serviceName)){
        services.put(serviceName, new LectorCsv());
      }
    } else if (serviceName.equals(EnviadorDeMails.class.getName())) {
      if (!services.containsKey(serviceName)) {
        services.put(serviceName, EnviadorDeMails.getInstancia(new ConfigReader()));
      }
    } else if (serviceName.equals(EnviadorDeWpp.class.getName())) {
      if (!services.containsKey(serviceName)) {
        services.put(serviceName, EnviadorDeWpp.getInstance());
      }
    }
    return (T) services.get(serviceName);
  }
}
