package ar.edu.utn.frba.dds.simeal;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Email;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.WhatsApp;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.TipoDeComida;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ModeloHeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prueba implements WithSimplePersistenceUnit {
  public static void main(String[] args) {
    Colaborador colaborador0 = new Colaborador(new Documento(TipoDocumento.DNI,"12345678"),"Juan","Perez", new Contacto("541198765432", new WhatsApp(null)));
    colaborador0.sumarPuntosReconocimiento(200);
    Colaborador colaborador1 = new Colaborador(new Documento(TipoDocumento.DNI,"99888777"),"Enrique", "Reinosa", new Contacto("541112345678", new WhatsApp(null)));
    colaborador1.sumarPuntosReconocimiento(10);
    Colaborador colaborador2 = new Colaborador(new Documento(TipoDocumento.DNI,"99666777"),"Pablo", "Mendez",new Contacto("541145693678", new WhatsApp(null)));
    colaborador2.sumarPuntosReconocimiento(670);
    Colaborador colaborador3 = new Colaborador(new Documento(TipoDocumento.DNI,"22666777"),"Hernan", "Puelman",new Contacto("5411123451230", new WhatsApp(null)));
    colaborador3.sumarPuntosReconocimiento(1200);
    Colaborador colaborador4 = new Colaborador(new Documento(TipoDocumento.DNI,"22666777"),"Mati", "Proieti", new Contacto("mprotip@gmail.com",new Email(null)));
    colaborador4.sumarPuntosReconocimiento(300);
    Colaborador colaborador5 = new Colaborador(new Documento(TipoDocumento.DNI,"88666777"),"Mar", "Moyano", new Contacto("marmoyano@gmail.com",new Email(null)));
    colaborador5.sumarPuntosReconocimiento(3000);

    Contacto contactoSecundario = new Contacto("ereinosa@gmail.com", new Email(null));
    colaborador1.addContacto(contactoSecundario);

    Repositorio repositorioColabs = ServiceLocator.getRepository(Repositorio.class);
    repositorioColabs.guardar(colaborador0);
    repositorioColabs.guardar(colaborador1);
    repositorioColabs.guardar(colaborador2);
    repositorioColabs.guardar(colaborador3);
    repositorioColabs.guardar(colaborador4);
    repositorioColabs.guardar(colaborador5);


    Ubicacion ubicacion = new Ubicacion(1,2);
    ModeloHeladera modeloHeladera = new ModeloHeladera("Modelo1",2,3,4);
    Repositorio modeloRepo = ServiceLocator.getRepository(ModeloHeladeraRepository.class);
    modeloRepo.guardar(modeloHeladera);

    Heladera heladera = new Heladera("Heladera de prueba",ubicacion, LocalDate.now(),colaborador0,modeloHeladera,true,new ArrayList<>());

    Repositorio repositorioHeladera = ServiceLocator.getRepository(Repositorio.class);
    repositorioHeladera.guardar(heladera);

    Vianda vianda0 = new Vianda(new TipoDeComida("Ensalada"), LocalDate.of(2024,12,20), LocalDate.now(),colaborador5,200,heladera,false);
    Vianda vianda1 = new Vianda(new TipoDeComida("Pasta"), LocalDate.of(2024,12,20), LocalDate.now(),colaborador5,250,heladera,false);
    Vianda vianda2 = new Vianda(new TipoDeComida("Tarta"), LocalDate.of(2024,12,20), LocalDate.now(),colaborador5,250,heladera,false);
    Vianda vianda3 = new Vianda(new TipoDeComida("China"), LocalDate.of(2024,12,20), LocalDate.now(),colaborador5,230,heladera,false);
    Vianda vianda4 = new Vianda(new TipoDeComida("Carne"), LocalDate.of(2024,12,20), LocalDate.now(),colaborador1,400,heladera,false);
    Vianda vianda5 = new Vianda(new TipoDeComida("Pollo"), LocalDate.of(2024,12,20), LocalDate.now(),colaborador2,400,heladera,false);
    Vianda vianda6 = new Vianda(new TipoDeComida("Cerdo"), LocalDate.of(2024,12,20), LocalDate.now(),colaborador2,400,heladera,false);
    Vianda vianda7 = new Vianda(new TipoDeComida("Cerdo"), LocalDate.of(2024,12,20), LocalDate.now(),colaborador3,400,heladera,false);

    DonarVianda donarVianda0 = new DonarVianda(colaborador5,LocalDate.now(),vianda0);
    DonarVianda donarVianda1 = new DonarVianda(colaborador5,LocalDate.now(),vianda1);
    DonarVianda donarVianda2 = new DonarVianda(colaborador5,LocalDate.now(),vianda2);
    DonarVianda donarVianda3 = new DonarVianda(colaborador5,LocalDate.now(),vianda3);
    DonarVianda donarVianda4 = new DonarVianda(colaborador1,LocalDate.now(),vianda4);
    DonarVianda donarVianda5 = new DonarVianda(colaborador2,LocalDate.now(),vianda5);
    DonarVianda donarVianda6 = new DonarVianda(colaborador2,LocalDate.now(),vianda6);
    DonarVianda donarVianda7 = new DonarVianda(colaborador3,LocalDate.now(),vianda7);

    // Persiste donaciones y viandas
    Repositorio repoColabs = ServiceLocator.getRepository(ColaboracionRepository.class);
    repoColabs.guardar(donarVianda0);
    repoColabs.guardar(donarVianda1);
    repoColabs.guardar(donarVianda2);
    repoColabs.guardar(donarVianda3);
    repoColabs.guardar(donarVianda4);
    repoColabs.guardar(donarVianda5);
    repoColabs.guardar(donarVianda6);
    repoColabs.guardar(donarVianda7);

    AdherirHeladera adherirHeladera = new AdherirHeladera(colaborador0,heladera,LocalDate.of(2020,1,3),5);
    AdherirHeladera adherirHeladera2 = new AdherirHeladera(colaborador0,heladera,LocalDate.of(2020,1,3),5);

    repoColabs.guardar(adherirHeladera);
    repoColabs.guardar(adherirHeladera2);

    // ****** Prueba de obtener datos de BD ******
    List<Colaborador> listaColabs = (List<Colaborador>) repositorioColabs.obtenerTodos(Colaborador.class);
    for(Colaborador colaborador : listaColabs){
      System.out.println(colaborador.getNombre() + " " + colaborador.getApellido());
    }


    PersonaVulnerable personaVulnerable = new PersonaVulnerable("TOmas","",LocalDate.now(),10,LocalDate.now(),null,null,0,null);
    PersonaVulnerable personaVulnerable1 = new PersonaVulnerable("Enrique", "",LocalDate.now(),100,LocalDate.now(),null,null,0,null);
    PersonaVulnerable personaVulnerable2 = new PersonaVulnerable("Pedro","" ,LocalDate.now(),10,LocalDate.now(),null,List.of(personaVulnerable1,personaVulnerable),2,null);

    ServiceLocator.getRepository(Repositorio.class).guardar(personaVulnerable);
    ServiceLocator.getRepository(Repositorio.class).guardar(personaVulnerable1);
    ServiceLocator.getRepository(Repositorio.class).guardar(personaVulnerable2);
  }
}
