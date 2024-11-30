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
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.EnviadorDeMails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Este archivo mete en la BD algunos datos hardcodeados, como los tipos de roles, los formularios, etc...
public class InitPersistence {
    public static void main(String[] args){
        Logger.info("Iniciando carga de bd");
        initUbicacionesRecomendadas();
        Logger.info("Ubicaciones cargadas");
        initPermisosRolesYUsuarios();
        Logger.info("Roles y permisos cargados");
        initFormularios();
        Logger.info("Formularios cargados");
        initHeladeras();
        Logger.info("Heladeras cargadas");
        initOfertas();
        Logger.info("Carga terminada :)");
    }

    private static void initPermisosRolesYUsuarios(){
        // Creamos los permisos
        // Homepage
        Permiso getHome = new Permiso("home", TipoMetodoHttp.GET);

        // Admin
        Permiso getMigracion = new Permiso("migracion", TipoMetodoHttp.GET);
        Permiso postMigracionUpload = new Permiso("migracion/upload", TipoMetodoHttp.POST);
        Permiso getReportes = new Permiso("reportes", TipoMetodoHttp.GET);
        Permiso getReporte = new Permiso("reporte", TipoMetodoHttp.GET);
        Permiso getReporteEspecifico = new Permiso("reportes/.+\\.pdf", TipoMetodoHttp.GET);
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
                TipoMetodoHttp.POST);
        Permiso getSuscripciones = new Permiso("/suscripciones/\\d+", TipoMetodoHttp.GET);
        Permiso deleteSuscripciones = new Permiso("/suscripcion/\\d+", TipoMetodoHttp.DELETE);

        Permiso getVisitasHeladera = new Permiso("/heladera/visitas/\\d+", TipoMetodoHttp.GET);
        Permiso getAlertasRecientes = new Permiso("/heladera/incidentes/\\d+", TipoMetodoHttp.GET);
        Permiso getReportarFallo = new Permiso("/heladera/reportar/\\d+", TipoMetodoHttp.GET);
        Permiso postReportarFallo = new Permiso("/heladera/reportar/\\d+", TipoMetodoHttp.POST);

        Permiso getRecomendacion = new Permiso("/recomendacion", TipoMetodoHttp.GET);

        Permiso getHeladeras = new Permiso("heladeras", TipoMetodoHttp.GET);

    // PERMISOS OFERTAS (all)
    Permiso getOfertas = new Permiso("ofertas", TipoMetodoHttp.GET);
    Permiso getOferta = new Permiso("ofertas/\\d+", TipoMetodoHttp.GET);
    Permiso getComprarOferta = new Permiso("ofertas/comprar", TipoMetodoHttp.GET);
    Permiso getOfertaCanjes = new Permiso("ofertas/misCanjes", TipoMetodoHttp.GET);

    // PERMISOS OFERTAS (humano)

    // PERMISOS OFERTAS (juridico)
    Permiso getMisOfertas = new Permiso("ofertas/misOfertas", TipoMetodoHttp.GET);
    Permiso getMiOferta = new Permiso("ofertas/misOfertas/\\d+", TipoMetodoHttp.GET);
    Permiso getPublicarOferta = new Permiso("ofertas/misOfertas/publicar", TipoMetodoHttp.GET);
    Permiso getModificarOferta = new Permiso("ofertas/misOfertas/\\d+/modificar", TipoMetodoHttp.GET);

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
                getDistribucionVianda, postDistribucionVianda, getHistorialColaboraciones, getConfiguracionColaboraciones,
                postConfiguracionColaboraciones, getAlertasRecientes, getReportarFallo, postReportarFallo,
                getComprarOferta, getOfertaCanjes
          );
        Rol humano = new Rol(TipoRol.HUMANO, permisosHumano);

       List<Permiso> permisosJuridico = List.of(
                getHome, getHeladera, postHeladera, postDonarDinero, getColaboraciones,
                getReporteEspecifico,
                getHeladeras, getOfertas, getOferta, getComprarOferta, getOfertaCanjes, getDonarDinero, postDonarDinero,
                getAdherirHeladera, postAdherirHeladera, getRecomendacion, getHistorialColaboraciones,
                getConfiguracionColaboraciones, postConfiguracionColaboraciones, getVisitasHeladera,
                getAlertasRecientes, getReportarFallo, postReportarFallo, getMisOfertas, getMiOferta, getPublicarOferta, getModificarOferta
        );
        Rol juridico = new Rol(TipoRol.JURIDICO, permisosJuridico);

        List<Permiso> permisosAdmin = List.of(
                getMigracion, postMigracionUpload, getReportes, getCambiarModo,
                getReporteEspecifico,
                getFormularios, getFormulario, postFormularios, postPregunta,
                deletePregunta, deleteFormulario, getTarjeta, getTarjetas, postTarjetas, getColaboraciones,
                postDonarDinero, getReporte,
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
        Usuario usuarioTecnico2 = new Usuario("pedro", PasswordHasher.hashPassword("pedro"), List.of(tecnicoRol));


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
                new Rubro("Comida Rapida", alimentos)
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

        Contacto contactoTecnico = new Contacto("jmprividera@gmail.com", new Email(EnviadorDeMails.getInstancia()));
        AreaDeCobertura areaDeCobertura = new AreaDeCobertura(
          new Ubicacion("Av Medrano",947, Provincia.Buenos_Aires,1179, localidad), 25d);
        Tecnico tecnico = new Tecnico("Tomas", "Pauza",
          new Documento(TipoDocumento.DNI, "1234556677"),
            "2012334556599", List.of(contactoTecnico), contactoTecnico, areaDeCobertura
          );
        tecnico.setUsuario(usuarioTecnico);
        repo.guardar(tecnico);

        Contacto contactoTecnico2 = new Contacto("tpauzasager@frba.utn.edu.ar", new Email(EnviadorDeMails.getInstancia()));
        AreaDeCobertura areaDeCobertura2 = new AreaDeCobertura(
          new Ubicacion("Av Rivadavia",5000, Provincia.Buenos_Aires,1424, localidad), 25d);
        Tecnico tecnico2 = new Tecnico("Pedro", "Pauza",
          new Documento(TipoDocumento.DNI, "1234556677"),
          "2012334556599", List.of(contactoTecnico2), contactoTecnico2, areaDeCobertura2
        );
        tecnico2.setUsuario(usuarioTecnico2);
        repo.guardar(tecnico2);

        repo.guardar(usuarioAdmin);
        repo.guardar(usuarioHumano);
        repo.guardar(usuarioJuridico);
        repo.guardar(usuarioTecnico);
        repo.guardar(usuarioTecnico2);

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
        Rubro rubro1 = new Rubro("Juguetes");
        repoRubro.guardar(rubro1);
        Rubro rubro2 = new Rubro("Otros");
        repoRubro.guardar(rubro2);
        Rubro rubro3 = new Rubro("Empresas");
        repoRubro.guardar(rubro3);


        // Day, Colaborador, Nombre, Rubro, Img, Nombre_prod, Desc_prod, costo)
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Güater", rubro, "/img/ofertas/agua.jpg", "Botella de agua",
                "Este agua fue producida por los mejores ingenieros que el continente puede ofrecer, por eso está tan cara", 9999.0));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Asado con los pibes", rubro, "/img/ofertas/asado.jpg", "Asado",
                "Un buen asado con los amigos de la vida, para disfrutar bien acompañado", 100.0));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Oveja - v1", rubro2, "/img/ofertas/oveja_blanca.jpg", "oveja_maicra",
                "Si compras dos y cultivas un poco de trigo tenes ovejas infinitas", 100.0));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Oveja - v2", rubro2, "/img/ofertas/oveja_negra.jpg", "oveja_maicra",
                "Un muy buen chiste fue desperdiciado, solo digo eso...", 100.0));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Simeal SRL", rubro3, "/img/ofertas/simeal.jpg", "Simeal SRL",
                "Adquiriendo este producto será capaz de tomar completo control de la organización Simeal SRL, la cual cuanta con un total de 3 (tres) empleados infinitamente capaces", 100000000.0));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Pizza con piña \uD83D\uDC80 \uD83D\uDC80", rubro, "/img/ofertas/pizza.jpg", "\"Pizza\"",
                "Considerado como un manjar por muchas personas", 0.0));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Titulo en Ingeniería", rubro2, "/img/ofertas/diploma.jpg", "Diploma de la UTN",
                "Con esta compra llevas: Angustia, depresión y ansiedad social", 1000.));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Oracio", rubro3, "/img/ofertas/oracio.jpg", "Oracio", "Oracio.", 237.0));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "5min de caricias a la bebe", rubro2, "/img/ofertas/maga.jpg", "5m de caricias a la bebe", "La reina (maga) se va a dejar acariciar por un total de 5 minutos.", 180.));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "La Patagonia Argentina", rubro3, "/img/ofertas/patagonia.jpg", "La Patagonia, Territorio Argentino",
                "Útil para sembrar y criar ganado", 200000.));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Programador JAVA Backend Jr.", rubro3, "/img/ofertas/programador_contento.jpg", "Programador JAVA Backend Jr.", "Programador sin experiencia buscando incursionar en el mundo laboral en una empresa desafiante que le permita ganar experiencia y enriquecer su red de contactos.", 45.));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "Misil termonuclear", rubro1, "/img/ofertas/misil.jpg", "Misil termonuclear",
                "Misil termonuclear traído directo desde China", 1000000.));
        modeloRepo.guardar(crearOferta(LocalDate.now(), colaborador, "TERRANATOR!!!!!", rubro1,"/img/ofertas/terranator.jpg", "Terrenator",
                "Turbo Wheels a diseñado el radio control más moderno…! T E R R A N A T O R! El coche más poderoso que ha existido, con tracción 4 X 4 y dos TURBO motores! Este si es todo terreno, las calles son fáciles… Méteofertas/terranator.jpg1000000.lo al lodo, parte la nieve, pasa por el agua! T E R R A N A T O R! Es el más potente que haya ex1000000.istido! O ofertas/terr1000000.anator.jpgque prefiere1000000.s un coche para niñita1000000.s?! T E R R E N A T O 1000000.R! De Fotorama."
                , 999.));
    }

    private static Oferta crearOferta(LocalDate date, Colaborador colab, String nombre, Rubro rubro, String img, String prod_name, String prod_desc, Double costo){
        Repositorio prodRepository = ServiceLocator.getRepository(Repositorio.class);
        Producto prod = new Producto(prod_name, prod_desc);
        prodRepository.guardar(prod);
        return Oferta.create(
                colab,
                nombre,
                date,
                costo,
                rubro,
                img,
                prod
        );
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

        // Localidades de Catamarca
        localidades.add(new Localidad("San Fernando del Valle de Catamarca", Provincia.Catamarca));
        localidades.add(new Localidad("Belén", Provincia.Catamarca));
        localidades.add(new Localidad("Tinogasta", Provincia.Catamarca));

        // Localidades de Chaco
        localidades.add(new Localidad("Resistencia", Provincia.Chaco));
        localidades.add(new Localidad("Presidencia Roque Sáenz Peña", Provincia.Chaco));
        localidades.add(new Localidad("Villa Ángela", Provincia.Chaco));

        // Localidades de Chubut
        localidades.add(new Localidad("Rawson", Provincia.Chubut));
        localidades.add(new Localidad("Trelew", Provincia.Chubut));
        localidades.add(new Localidad("Puerto Madryn", Provincia.Chubut));

        // Localidades de Córdoba
        localidades.add(new Localidad("Córdoba", Provincia.Cordoba));
        localidades.add(new Localidad("Villa Carlos Paz", Provincia.Cordoba));
        localidades.add(new Localidad("Río Cuarto", Provincia.Cordoba));

        // Localidades de Corrientes
        localidades.add(new Localidad("Corrientes", Provincia.Corrientes));
        localidades.add(new Localidad("Goya", Provincia.Corrientes));
        localidades.add(new Localidad("Paso de los Libres", Provincia.Corrientes));

        // Localidades de Entre Ríos
        localidades.add(new Localidad("Paraná", Provincia.Entre_Rios));
        localidades.add(new Localidad("Concordia", Provincia.Entre_Rios));
        localidades.add(new Localidad("Gualeguaychú", Provincia.Entre_Rios));

        // Localidades de Formosa
        localidades.add(new Localidad("Formosa", Provincia.Formosa));
        localidades.add(new Localidad("Clorinda", Provincia.Formosa));
        localidades.add(new Localidad("Pirané", Provincia.Formosa));

        // Localidades de Jujuy
        localidades.add(new Localidad("San Salvador de Jujuy", Provincia.Jujuy));
        localidades.add(new Localidad("Palpalá", Provincia.Jujuy));
        localidades.add(new Localidad("Humahuaca", Provincia.Jujuy));

        // Localidades de La Pampa
        localidades.add(new Localidad("Santa Rosa", Provincia.La_Pampa));
        localidades.add(new Localidad("General Pico", Provincia.La_Pampa));
        localidades.add(new Localidad("Eduardo Castex", Provincia.La_Pampa));

        // Localidades de La Rioja
        localidades.add(new Localidad("La Rioja", Provincia.La_Rioja));
        localidades.add(new Localidad("Chilecito", Provincia.La_Rioja));
        localidades.add(new Localidad("Aimogasta", Provincia.La_Rioja));

        // Localidades de Mendoza
        localidades.add(new Localidad("Mendoza", Provincia.Mendoza));
        localidades.add(new Localidad("San Rafael", Provincia.Mendoza));
        localidades.add(new Localidad("Malargüe", Provincia.Mendoza));

        // Localidades de Misiones
        localidades.add(new Localidad("Posadas", Provincia.Misiones));
        localidades.add(new Localidad("Puerto Iguazú", Provincia.Misiones));
        localidades.add(new Localidad("Eldorado", Provincia.Misiones));

        // Localidades de Neuquén
        localidades.add(new Localidad("Neuquén", Provincia.Neuquen));
        localidades.add(new Localidad("San Martín de los Andes", Provincia.Neuquen));
        localidades.add(new Localidad("Zapala", Provincia.Neuquen));

        // Localidades de Río Negro
        localidades.add(new Localidad("Viedma", Provincia.Rio_Negro));
        localidades.add(new Localidad("San Carlos de Bariloche", Provincia.Rio_Negro));
        localidades.add(new Localidad("General Roca", Provincia.Rio_Negro));

        // Localidades de Salta
        localidades.add(new Localidad("Salta", Provincia.Salta));
        localidades.add(new Localidad("San Ramón de la Nueva Orán", Provincia.Salta));
        localidades.add(new Localidad("Tartagal", Provincia.Salta));

        // Localidades de San Juan
        localidades.add(new Localidad("San Juan", Provincia.San_Juan));
        localidades.add(new Localidad("Jáchal", Provincia.San_Juan));
        localidades.add(new Localidad("Caucete", Provincia.San_Juan));

        // Localidades de San Luis
        localidades.add(new Localidad("San Luis", Provincia.San_Luis));
        localidades.add(new Localidad("Villa Mercedes", Provincia.San_Luis));
        localidades.add(new Localidad("Merlo", Provincia.San_Luis));

        // Localidades de Santa Cruz
        localidades.add(new Localidad("Río Gallegos", Provincia.Santa_Cruz));
        localidades.add(new Localidad("Caleta Olivia", Provincia.Santa_Cruz));
        localidades.add(new Localidad("El Calafate", Provincia.Santa_Cruz));

        // Localidades de Santa Fe
        localidades.add(new Localidad("Santa Fe", Provincia.Santa_Fe));
        localidades.add(new Localidad("Rosario", Provincia.Santa_Fe));
        localidades.add(new Localidad("Rafaela", Provincia.Santa_Fe));

        // Localidades de Santiago del Estero
        localidades.add(new Localidad("Santiago del Estero", Provincia.Santiago_Del_Estero));
        localidades.add(new Localidad("La Banda", Provincia.Santiago_Del_Estero));
        localidades.add(new Localidad("Termas de Río Hondo", Provincia.Santiago_Del_Estero));

        // Localidades de Tierra del Fuego
        localidades.add(new Localidad("Ushuaia", Provincia.Tierra_del_Fuego));
        localidades.add(new Localidad("Río Grande", Provincia.Tierra_del_Fuego));
        localidades.add(new Localidad("Tolhuin", Provincia.Tierra_del_Fuego));

        // Localidades de Tucumán
        localidades.add(new Localidad("San Miguel de Tucumán", Provincia.Tucuman));
        localidades.add(new Localidad("Tafí Viejo", Provincia.Tucuman));
        localidades.add(new Localidad("Concepción", Provincia.Tucuman));


        for (Localidad l : localidades) {
            ServiceLocator.getRepository(Repositorio.class).guardar(l);
        }

      Ubicacion u1 = new Ubicacion("Bogotá", 305, Provincia.Ciudad_Autonoma_De_Buenos_Aires,1424, localidades.get(14));
      Ubicacion u3 = new Ubicacion("Otamendi", 477,Provincia.Ciudad_Autonoma_De_Buenos_Aires, 1405,localidades.get(14));
      Ubicacion u2 = new Ubicacion("Leonardo Rosales", 1652,Provincia.Buenos_Aires, 1846,localidades.get(9));

      ServiceLocator.getRepository(Repositorio.class).guardar(u1);
      ServiceLocator.getRepository(Repositorio.class).guardar(u2);
      ServiceLocator.getRepository(Repositorio.class).guardar(u3);
    }
}
