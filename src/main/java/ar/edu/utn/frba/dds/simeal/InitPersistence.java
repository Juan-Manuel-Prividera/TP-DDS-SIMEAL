package ar.edu.utn.frba.dds.simeal;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.creacionales.MedioDeContactoFactory;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Producto;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TipoJuridico;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Opcion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.TipoPregunta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Email;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.AreaDeCobertura;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Localidad;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.TipoDeComida;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.*;
import ar.edu.utn.frba.dds.simeal.models.usuario.*;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.EnviadorDeMails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Este archivo mete en la BD algunos datos hardcodeados, como los tipos de roles, los formularios, etc...
public class InitPersistence {
    public static void main(String[] args){
        initUbicacionesRecomendadas();
        initPermisosRolesYUsuarios();
        initFormularios();
        initHeladeras();
        initOfertas();
    }

    private static void initPermisosRolesYUsuarios(){
        // Creamos los permisos
        // Homepage
        Permiso getHome = new Permiso("home", TipoMetodoHttp.GET);

        // Admin
        Permiso getMigracion = new Permiso("migracion", TipoMetodoHttp.GET);
        Permiso postMigracionUpload = new Permiso("migracion/upload", TipoMetodoHttp.POST);
        Permiso getReportes = new Permiso("reportes", TipoMetodoHttp.GET);
        Permiso getCambiarModo = new Permiso("cambiarmodo", TipoMetodoHttp.GET);

        Permiso getFormularios = new Permiso("formularios", TipoMetodoHttp.GET);
        Permiso postFormularios = new Permiso("formulario", TipoMetodoHttp.POST);
        Permiso getFormulario = new Permiso("formulario/.+", TipoMetodoHttp.GET);
        Permiso postPregunta = new Permiso("formulario/\\d+/pregunta", TipoMetodoHttp.POST);

        Permiso deletePregunta = new Permiso("/formularios/\\d+/pregunta/\\d+", TipoMetodoHttp.DELETE);
        Permiso deleteFormulario = new Permiso("/formularios/\\d+", TipoMetodoHttp.DELETE);

        // Humano
        Permiso getTarjeta = new Permiso("/tarjeta", TipoMetodoHttp.GET);
        Permiso getTarjetas = new Permiso("/tarjeta/.+",TipoMetodoHttp.GET);
        Permiso postTarjetas = new Permiso("/tarjeta/.+",TipoMetodoHttp.POST);
        Permiso getSolicitud = new Permiso("/solicitud/.+", TipoMetodoHttp.GET);
        Permiso postSolicitud = new Permiso("/solicitud/.+", TipoMetodoHttp.POST);



        Permiso getColaboraciones = new Permiso("colaboraciones", TipoMetodoHttp.GET);
        Permiso postDonarDinero = new Permiso("colaboracion/donarDinero", TipoMetodoHttp.POST);
        Permiso getDonarDinero = new Permiso("colaboracion/donarDinero", TipoMetodoHttp.GET);
        Permiso getDonarVianda = new Permiso("colaboracion/donarVianda", TipoMetodoHttp.GET);
        Permiso postDonarVianda = new Permiso("colaboracion/donarVianda", TipoMetodoHttp.POST);
        Permiso getAdherirHeladera = new Permiso("colaboracion/adherirHeladera", TipoMetodoHttp.GET);
        Permiso postAdherirHeladera = new Permiso("colaboracion/adherirHeladera", TipoMetodoHttp.POST);
        Permiso getDistribucionVianda = new Permiso("colaboracion/distribucionVianda", TipoMetodoHttp.GET);
        Permiso postDistribucionVianda = new Permiso("colaboracion/distribucionVianda", TipoMetodoHttp.POST);

        Permiso getHistorialColaboraciones = new Permiso("colaboraciones/historial", TipoMetodoHttp.GET);
        Permiso getConfiguracionColaboraciones = new Permiso("colaboraciones/configuracion", TipoMetodoHttp.GET);
        Permiso postConfiguracionColaboraciones = new Permiso("colaboraciones/configuracion", TipoMetodoHttp.POST);

        Permiso getHeladera = new Permiso("heladera", TipoMetodoHttp.GET);
        Permiso getHeladeraEspecifico = new Permiso("/heladera/(?!suscribirse/).*",
                TipoMetodoHttp.GET);
        Permiso postHeladera = new Permiso("/heladera/(?!suscribirse/).*",
                TipoMetodoHttp.POST);

        Permiso getSuscribirHeladera = new Permiso("/heladera/suscribirse/\\d+",
                TipoMetodoHttp.GET);
        Permiso postSuscribirHeladera = new Permiso("/heladera/suscribirse/\\d+",
                TipoMetodoHttp.GET);
        Permiso getSuscripciones = new Permiso("/suscripciones/\\d+", TipoMetodoHttp.GET);
        Permiso deleteSuscripciones = new Permiso("/suscripcion/\\d+", TipoMetodoHttp.DELETE);

        Permiso getRecomendacion = new Permiso("/recomendacion", TipoMetodoHttp.GET);

        Permiso getHeladeras = new Permiso("heladeras", TipoMetodoHttp.GET);
        Permiso getOfertas = new Permiso("ofertas", TipoMetodoHttp.GET);
        Permiso getOferta = new Permiso("oferta/\\d+", TipoMetodoHttp.GET);

        Permiso getTecnico = new Permiso("/tecnico", TipoMetodoHttp.GET);
        Permiso postTecnico = new Permiso("/tecnico", TipoMetodoHttp.POST);
        Permiso getRegistroTecnico = new Permiso("/registro/tecnico", TipoMetodoHttp.GET);
        Permiso deleteTecnicos = new Permiso("tecnico/\\d+", TipoMetodoHttp.DELETE);

        Permiso getEncargoAceptado = new Permiso("encargo/\\d+/aceptado", TipoMetodoHttp.GET);
        Permiso getEncargoRechazado = new Permiso("encargo/\\d+/rechazado", TipoMetodoHttp.GET);

        Permiso getTecnicoHome = new Permiso("/tecnico/home", TipoMetodoHttp.GET);
        Permiso getEncargoVisita = new Permiso("/\\d+/visita", TipoMetodoHttp.GET);
        Permiso postEncargoVisita = new Permiso("/\\d+/visita", TipoMetodoHttp.POST);

        // Crear roles
        List<Permiso> permisosHumano = List.of(
                getHome, getTarjeta, getTarjetas, postTarjetas, postDonarDinero, getColaboraciones,
                getHeladera, getHeladeraEspecifico, postHeladera, getSuscribirHeladera, postSuscribirHeladera,
                getHeladeras, getOfertas, getOferta, getSuscripciones, deleteSuscripciones,
                getSolicitud, postSolicitud, getDonarDinero, postDonarVianda, postDonarDinero, getDonarVianda,
                getDistribucionVianda, postDistribucionVianda, getHistorialColaboraciones, getConfiguracionColaboraciones, postConfiguracionColaboraciones
          );
        Rol humano = new Rol(TipoRol.HUMANO, permisosHumano);

        List<Permiso> permisosJuridico = List.of(
                getHome, getHeladera, postHeladera, postDonarDinero, getColaboraciones,
                getHeladeras, getOfertas, getOferta, getDonarDinero, postDonarDinero,
                getAdherirHeladera, postAdherirHeladera, getRecomendacion, getHistorialColaboraciones,
                getConfiguracionColaboraciones, postConfiguracionColaboraciones
        );
        Rol juridico = new Rol(TipoRol.JURIDICO, permisosJuridico);

        List<Permiso> permisosAdmin = List.of(
                getMigracion, postMigracionUpload, getReportes, getCambiarModo,
                getFormularios, getFormulario, postFormularios, postPregunta,
                deletePregunta, deleteFormulario, getTarjeta, getTarjetas, postTarjetas, getColaboraciones,
                postDonarDinero,
                getHeladeraEspecifico, getHeladera, getHeladeras, postHeladera, getSuscribirHeladera,
                getOferta, getOfertas, postSuscribirHeladera, getSuscripciones, deleteSuscripciones,
                getHome, getTecnico, postTecnico, getRegistroTecnico, deleteTecnicos, getDistribucionVianda, postDistribucionVianda
        );
        Rol admin = new Rol(TipoRol.ADMIN, permisosAdmin);

        List<Permiso> permisosTecnico = List.of(
          getEncargoAceptado, getEncargoRechazado, getTecnicoHome, getEncargoVisita,
          postEncargoVisita
        );

        Rol tecnicoRol = new Rol(TipoRol.TECNICO, permisosTecnico);


        Repositorio repo = new Repositorio();
        repo.guardar(humano);
        repo.guardar(juridico);
        repo.guardar(admin);
        repo.guardar(tecnicoRol);

        Usuario usuarioAdmin = new Usuario("admin", PasswordHasher.hashPassword("admin"), List.of(admin));
        Usuario usuarioHumano = new Usuario("humano", PasswordHasher.hashPassword("humano"), List.of(humano));
        Usuario usuarioJuridico = new Usuario("juridico", PasswordHasher.hashPassword("juridico"), List.of(juridico));
        Usuario usuarioTecnico = new Usuario("tecnico", PasswordHasher.hashPassword("tecnico"), List.of(tecnicoRol));

        Colaborador colaboradorHumano = new Colaborador(
                new Documento(TipoDocumento.DNI, "13"),
                "Friederich", "Gauss");
        colaboradorHumano.setUsuario(usuarioHumano);
        Contacto contactoHumano = new Contacto("emouesca@frba.utn.edu.ar", new Email(EnviadorDeMails.getInstancia()));
        colaboradorHumano.setContactoPreferido(contactoHumano);
        colaboradorHumano.addContacto(contactoHumano);

        repo.guardar(colaboradorHumano);
        TarjetaColaborador tarjetaColaborador = new TarjetaColaborador(colaboradorHumano, LocalDate.now());
        ServiceLocator.getRepository(TarjetaColaboradorRepository.class).guardar(tarjetaColaborador);

        List<Rubro> rubros = (List<Rubro>) ServiceLocator.getRepository(Repositorio.class).obtenerTodos(Rubro.class);
        Rubro alimentos = null;
        for(Rubro rubro : rubros){
            if(rubro.getNombre() == "Alimentos") alimentos = rubro;
        }

        Colaborador colaboradorJuridico = new Colaborador(
                "Arcos dorados",
                new Rubro("fastFood", alimentos)
        );
        LocalidadRepository localidadRepository = (LocalidadRepository) ServiceLocator.getRepository(LocalidadRepository.class);
        Localidad localidad = localidadRepository.buscarPorNombre("Almagro");
        colaboradorJuridico.setUsuario(usuarioJuridico);
        Ubicacion ubicacion0 = new Ubicacion("Av. Corrientes", 3966,Provincia.Ciudad_Autonoma_De_Buenos_Aires,1179, localidad);
        Ubicacion ubicacion1 = new Ubicacion("Av. Cordoba",3821 ,Provincia.Ciudad_Autonoma_De_Buenos_Aires,1188, localidad);
        colaboradorJuridico.setUbicaciones(List.of(ubicacion0, ubicacion1));
        Contacto contactoJuridico = new Contacto("tpauza@frba.utn.edu.ar", new Email(EnviadorDeMails.getInstancia()));
        colaboradorJuridico.setContactoPreferido(contactoJuridico);
        colaboradorHumano.addContacto(contactoJuridico);
        repo.guardar(colaboradorJuridico);

        Contacto contactoTecnico = new Contacto("tpauza@gmail.com", new Email(EnviadorDeMails.getInstancia()));
        AreaDeCobertura areaDeCobertura = new AreaDeCobertura(
          new Ubicacion("Av Medrano",947, Provincia.Buenos_Aires,1179, localidad), 10000.0);
        Tecnico tecnico = new Tecnico("Tomas", "Pauza",
          new Documento(TipoDocumento.DNI, "1234556677"),
            "2012334556599", List.of(contactoTecnico), contactoTecnico, areaDeCobertura
          );
        tecnico.setUsuario(usuarioTecnico);
        repo.guardar(tecnico);

        repo.guardar(usuarioAdmin);
        repo.guardar(usuarioHumano);
        repo.guardar(usuarioJuridico);
        repo.guardar(usuarioTecnico);

    }
    private static void initFormularios() {
        Repositorio repo = ServiceLocator.getRepository(Repositorio.class);
        // Formulario persona
        Opcion opcionSi = new Opcion("Sí");
        Opcion opcionUnPoco = new Opcion("Un poco");
        Opcion opcionSiMucho = new Opcion("Sí, mucho!");
        Pregunta teGustanLosGatos = new Pregunta("¿Te gustan los gatos?", "gatos_sino",
                TipoPregunta.CHOICE, List.of(opcionUnPoco, opcionSi, opcionSiMucho), true);
        Pregunta comoSeLlamaTuGato = new Pregunta("¿Cómo se llama(n) tu(s) gato(s)?", "gatos_nombres",
                TipoPregunta.TEXT, null, true);
        Formulario formularioHumano = new Formulario(List.of(teGustanLosGatos, comoSeLlamaTuGato), true,
                TipoRol.HUMANO, "Cuestionario de registro humano");
        repo.guardar(formularioHumano);

        // Formulario juridico
        Opcion muyEnDesacuerdo = new Opcion("Muy en desacuerdo");
        Opcion levementeEnDesacuerdo = new Opcion("Levemente en desacuerdo");
        Opcion indiferente = new Opcion("Indiferente");
        Opcion levementeDeAcuerdo = new Opcion("Levemente de acuerdo");
        Opcion muyDeAcuerdo = new Opcion("Muy de acuerdo");
        Pregunta cuanDeAcuerdo = new Pregunta("¿Está de acuerdo con la siguiente afirmación?: 'Vamos a besar gatos'", "cuan_de_acuerdo",
                TipoPregunta.CHOICE, List.of(muyEnDesacuerdo, levementeEnDesacuerdo,
                indiferente, levementeDeAcuerdo, muyDeAcuerdo), true);
        Pregunta porfavorVentajas = new Pregunta("Por favor, indique tres ventajas por las que tener un gato como mascota", "razones",
                TipoPregunta.TEXT, null, true);
        Formulario formularioJuridico = new Formulario(List.of(cuanDeAcuerdo, porfavorVentajas), true,
                TipoRol.JURIDICO, "Cuestionario de registro juridico");
        repo.guardar(formularioJuridico);
    }

    private static void initHeladeras() {
        Ubicacion ubicacion = new Ubicacion("Sarmiento", 2600, -34.57991061741367, -58.42111388432673);
        Ubicacion ubicacion1 = new Ubicacion("Medrano", 1344, -34.59859548850497, -58.42011530932118);
        Ubicacion ubicacion2 = new Ubicacion("Mozart", 2300, -34.65946430376541, -58.46797562733076);
        Ubicacion ubicacion3 = new Ubicacion("Santa Fe", 1860, -34.59266250613197, -58.39405684357533);
        Ubicacion ubicacion4 = new Ubicacion("Gorriti", 900, -34.60679445485131, -58.36548827958108);
        ModeloHeladera modeloHeladera0 = new ModeloHeladera("Super Heladera 3000",4,2,20);
        ModeloHeladera modeloHeladera1 = new ModeloHeladera("Gran Heladera 2500",4,2,15);
        ModeloHeladera modeloHeladera2 = new ModeloHeladera("Heladereitor 2000",4,2,10);
        ModeloHeladera modeloHeladera3 = new ModeloHeladera("Heladerita 1000",5,1,5);
        Repositorio modeloRepo = ServiceLocator.getRepository(ModeloHeladeraRepository.class);
        modeloRepo.guardar(modeloHeladera0);
        modeloRepo.guardar(modeloHeladera1);
        modeloRepo.guardar(modeloHeladera2);
        modeloRepo.guardar(modeloHeladera3);

        Colaborador colaborador0 = new Colaborador("UTN-BA", new Rubro("Educación"), TipoJuridico.INSTITUCION, new Contacto("uni@gmail.com", MedioDeContactoFactory.crearMedioDeContacto("email")));
        colaborador0.sumarPuntosReconocimiento(200);
        List<Rol> roles = (List<Rol>) ServiceLocator.getRepository(Repositorio.class).obtenerTodos(Rol.class);
        Rol rolJuridico = null;
        for (Rol rol : roles) {
            if (rol.getTipo().equals(TipoRol.JURIDICO)) {
                rolJuridico = rol;
                break;
            }
        }

        Usuario usuario = new Usuario("utnba",PasswordHasher.hashPassword("utnba"),List.of(rolJuridico));
        colaborador0.setUsuario(usuario);
        Repositorio repositorioColabs = ServiceLocator.getRepository(Repositorio.class);

        repositorioColabs.guardar(colaborador0);

        Heladera heladera = new Heladera("Heladera Plaza Italia",ubicacion, LocalDate.now(),null,modeloHeladera0,true,new ArrayList<>());
        Heladera heladera1 = new Heladera("Heladera Medrano",ubicacion1, LocalDate.now(),colaborador0,modeloHeladera0,true,new ArrayList<>());
        Heladera heladera2 = new Heladera("Heladera Campus",ubicacion2, LocalDate.now(),colaborador0,modeloHeladera1,true,new ArrayList<>());
        Heladera heladera3 = new Heladera("Heladera el Ateneo",ubicacion3, LocalDate.now(),null,modeloHeladera1,true,new ArrayList<>());
        Heladera heladera4 = new Heladera("Heladera Puerto Madero",ubicacion4, LocalDate.now(),null,modeloHeladera1,true,new ArrayList<>());

        Sensor sensor = new Sensor(heladera,null);
        Sensor sensor1 = new Sensor(heladera1,null);
        Sensor sensor2 = new Sensor(heladera2,null);
        Sensor sensor3 = new Sensor(heladera3,null);
        Sensor sensor4 = new Sensor(heladera4,null);

        Repositorio repositorioHeladera = ServiceLocator.getRepository(Repositorio.class);
        repositorioHeladera.guardar(heladera);
        repositorioHeladera.guardar(heladera1);
        repositorioHeladera.guardar(heladera2);
        repositorioHeladera.guardar(heladera3);
        repositorioHeladera.guardar(heladera4);

        SensorRepository sensorRepository = (SensorRepository) ServiceLocator.getRepository(SensorRepository.class);
        sensorRepository.guardar(sensor);
        sensorRepository.guardar(sensor1);
        sensorRepository.guardar(sensor2);
        sensorRepository.guardar(sensor3);
        sensorRepository.guardar(sensor4);

        // ***** Viandas ******
        Vianda vianda = new Vianda(new TipoDeComida("Saludable"), LocalDate.now().plusYears(3), LocalDate.now(), colaborador0, 12000, heladera, true);
        Vianda vianda1 = new Vianda(new TipoDeComida("Carne"), LocalDate.now().plusYears(3), LocalDate.now(), colaborador0, 5000, heladera1, true);
        Vianda vianda2 = new Vianda(new TipoDeComida("Pescado"), LocalDate.now().plusYears(3), LocalDate.now(), colaborador0, 7600, heladera2, true);
        Vianda vianda3 = new Vianda(new TipoDeComida("Vegetales"), LocalDate.now().plusYears(3), LocalDate.now(), colaborador0, 23000, heladera3, true);
        Vianda vianda4 = new Vianda(new TipoDeComida("Helado"), LocalDate.now().plusYears(3), LocalDate.now(), colaborador0, 3250, heladera4, true);
        modeloRepo.guardar(vianda);
        modeloRepo.guardar(vianda1);
        modeloRepo.guardar(vianda2);
        modeloRepo.guardar(vianda3);
        modeloRepo.guardar(vianda4);

    }

    //TODO: Agregar las imagenes
    private static void initOfertas(){
        Repositorio modeloRepo = ServiceLocator.getRepository(OfertaRepository.class);
        Repositorio repoColaborador = ServiceLocator.getRepository(ColaboracionRepository.class);
        Repositorio repoRubro = ServiceLocator.getRepository(Repositorio.class);

        Colaborador colaborador= new Colaborador(
                new Documento(TipoDocumento.DNI, "666"),
                "Mr. Ofertas", "Monopoly");

        repoColaborador.guardar(colaborador);

        Rubro rubro = new Rubro("Alimentos");
        repoRubro.guardar(rubro);
        Rubro rubro1 = new Rubro("Pollo en escabeche", rubro);
        repoRubro.guardar(rubro1);

        int quantity = 20;
        for(int i = 1; i < quantity; i++){
            ignorarEstaFuncion(i, colaborador, modeloRepo, rubro1);
        }

    }
    private static void initUbicacionesRecomendadas() {
        List<Localidad> localidades = new ArrayList<>();
        localidades.add(new Localidad("La Plata", Provincia.Buenos_Aires));
        localidades.add(new Localidad("Mar del Plata", Provincia.Buenos_Aires));
        localidades.add(new Localidad("Bahía Blanca", Provincia.Buenos_Aires));
        localidades.add(new Localidad("Tandil", Provincia.Buenos_Aires));
        localidades.add(new Localidad("San Nicolás", Provincia.Buenos_Aires));
        localidades.add(new Localidad("San Fernando", Provincia.Buenos_Aires));
        localidades.add(new Localidad("Avellaneda", Provincia.Buenos_Aires));
        localidades.add(new Localidad("Quilmes", Provincia.Buenos_Aires));
        localidades.add(new Localidad("Tigre", Provincia.Buenos_Aires));
        localidades.add(new Localidad("Adrogué", Provincia.Buenos_Aires));

        // Localidades de Ciudad Autónoma de Buenos Aires (CABA)
        localidades.add(new Localidad("Palermo", Provincia.Ciudad_Autonoma_De_Buenos_Aires));
        localidades.add(new Localidad("Recoleta", Provincia.Ciudad_Autonoma_De_Buenos_Aires));
        localidades.add(new Localidad("Belgrano", Provincia.Ciudad_Autonoma_De_Buenos_Aires));
        localidades.add(new Localidad("Almagro", Provincia.Ciudad_Autonoma_De_Buenos_Aires));
        localidades.add(new Localidad("Caballito", Provincia.Ciudad_Autonoma_De_Buenos_Aires));
        localidades.add(new Localidad("Villa Urquiza", Provincia.Ciudad_Autonoma_De_Buenos_Aires));
        localidades.add(new Localidad("Flores", Provincia.Ciudad_Autonoma_De_Buenos_Aires));
        localidades.add(new Localidad("Villa Lugano", Provincia.Ciudad_Autonoma_De_Buenos_Aires));
        localidades.add(new Localidad("Almagro", Provincia.Ciudad_Autonoma_De_Buenos_Aires));

        for (Localidad l : localidades) {
            ServiceLocator.getRepository(Repositorio.class).guardar(l);
        }

      Ubicacion u1 = new Ubicacion("Av. Acoyte", 467, Provincia.Ciudad_Autonoma_De_Buenos_Aires,1405, localidades.get(18));
      Ubicacion u3 = new Ubicacion("Otamendi", 477,Provincia.Ciudad_Autonoma_De_Buenos_Aires, 1405,localidades.get(18));
      Ubicacion u2 =  new Ubicacion("Franklin", 629,Provincia.Ciudad_Autonoma_De_Buenos_Aires, 1405, localidades.get(18));
      ServiceLocator.getRepository(Repositorio.class).guardar(u1);
      ServiceLocator.getRepository(Repositorio.class).guardar(u2);
      ServiceLocator.getRepository(Repositorio.class).guardar(u3);
    }
    private static void ignorarEstaFuncion(int day, Colaborador colaborador, Repositorio repositorio, Rubro rubro) {
        Producto producto = new Producto("nombre", "Descripción");
        repositorio.guardar(producto);

        Oferta oferta = Oferta.create(colaborador,
                "Pollo del:" + Integer.valueOf(day).toString(),
                LocalDate.of(2024, 1, day),
          day+10,
                rubro,
                null,
                producto);
        repositorio.guardar(oferta);
    }
}
