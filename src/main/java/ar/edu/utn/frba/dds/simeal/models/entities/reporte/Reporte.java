package ar.edu.utn.frba.dds.simeal.models.entities.reporte;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.reporte.utils.ImagenesCaratula.CreadorDeTabla;
import ar.edu.utn.frba.dds.simeal.models.entities.reporte.utils.ImagenesCaratula.EncabezadoPieDePagina;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.DistribucionDeViandasRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Reporte {

  private List<Heladera> heladeras;
  private List<Vianda> viandas;
  private List<DistribuirVianda> distribuciones;


  public Reporte(HeladeraRepository heladeraRepository, ViandaRepository viandaRepository, DistribucionDeViandasRepository distribucionDeViandasRepository) {
    this.heladeras = heladeraRepository.getHeladeras();
    this.viandas = viandaRepository.getViandas();
    this.distribuciones = distribucionDeViandasRepository.getDistribuciones();
  }


  // Clase generadora de reporte
  public void generarReporte() {

    Document documento = new Document(PageSize.A4, 50, 50, 50, 50);

    String pdfPath = "E:\\Tarea Fran\\Diseño de sistemas\\Pruebas\\Generador de pdf\\Pdfs Creados\\Reportes_generados.pdf";
    String imagePath = "https://github.com/fmosqueraalfaro/DDS/blob/main/ImagenesPrueba/logoSimeal.png?raw=true";
    try {

      PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(pdfPath));

      EncabezadoPieDePagina event = new EncabezadoPieDePagina();
      writer.setPageEvent(event);

      documento.open();

      Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
      Font parrafoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);




      Paragraph espacio = new Paragraph(" ", parrafoFont);
      espacio.setSpacingAfter(10);
      documento.add(espacio);
      Image imagen = Image.getInstance(imagePath);
      imagen.setAlignment(Image.ALIGN_CENTER);
      imagen.scaleToFit(400, 300);
      imagen.setSpacingBefore(40);
      documento.add(imagen);

      documento.add(new Chunk(new LineSeparator()));

      // Título
      Paragraph titulo = new Paragraph("Generación de reportes", tituloFont);
      titulo.setAlignment(Paragraph.ALIGN_CENTER);
      titulo.setSpacingBefore(10);
      documento.add(titulo);

      // Tabla integrantes del grupo
      String[] cabeceraIntegrantes = {"Nombre", "Apellido"};
      PdfPTable tabla = CreadorDeTabla.crearNuevaTabla(cabeceraIntegrantes);
      agregarIntegrantes(tabla);
      documento.add(tabla);


      // Página 2
      documento.newPage();
      Paragraph subtitulo1 = new Paragraph("Reporte cantidad de fallas generados por heladeras", parrafoFont);
      subtitulo1.setAlignment(Paragraph.ALIGN_CENTER);
      documento.add(subtitulo1);

      // Tabla de incidentes generados por heladera
      String[] cabeceraIncidentxHelad = {"Heladera", "Cantidad de incidentes"};

      PdfPTable tablaIncidentesxheladera = CreadorDeTabla.crearNuevaTabla(cabeceraIncidentxHelad);

      // Agrego los datos a la tabla
      for (Heladera h : heladeras) {
        tablaIncidentesxheladera.addCell(h.getNombre());
        tablaIncidentesxheladera.addCell(String.valueOf(h.getIncidentes().size()));
      }

      documento.add(tablaIncidentesxheladera);

      // Página 3
      documento.newPage();

      Paragraph subtitulo2 = new Paragraph("Reporte cantidad de viandas retiradas/colocadas por heladera", parrafoFont);
      subtitulo2.setAlignment(Element.ALIGN_CENTER);
      documento.add(subtitulo2);

      // Tabla de cantidad de viandas retiradas/colocadas por heladera
      String[] cabeceraViandasRetiradasColocadas = {"Heladera", "Viandas retiradas", "Viandas colocadas"};
      PdfPTable tablaViandasRetiradasColocadas = CreadorDeTabla.crearNuevaTabla(cabeceraViandasRetiradasColocadas);

      HashMap<String, Integer> viandasRetiradasPorHeladera = new HashMap<>();
      HashMap<String, Integer> viandasColocadasPorHeladera = new HashMap<>();

      // Obtengo las heladeras de origen, destino y la cantidad de viandas movidas
      for (DistribuirVianda distribucion : distribuciones) {
        String origen = distribucion.getOrigen().getNombre();
        String destino = distribucion.getDestino().getNombre();
        int cantidad = distribucion.getCantidadViandasMover();

        // las agrego al hashmap
        viandasRetiradasPorHeladera.put(origen, viandasRetiradasPorHeladera.getOrDefault(origen, 0) + cantidad);
        viandasColocadasPorHeladera.put(destino, viandasColocadasPorHeladera.getOrDefault(destino, 0) + cantidad);
      }

      // Agrego los datos a la tabla
      for (Heladera h : heladeras) {
        String nombreHeladera = h.getNombre();
        int retiradas = viandasRetiradasPorHeladera.getOrDefault(nombreHeladera, 0);
        int colocadas = viandasColocadasPorHeladera.getOrDefault(nombreHeladera, 0);
        tablaViandasRetiradasColocadas.addCell(nombreHeladera);
        tablaViandasRetiradasColocadas.addCell(String.valueOf(retiradas));
        tablaViandasRetiradasColocadas.addCell(String.valueOf(colocadas));
      }

      // Página 4
      documento.newPage();

      Paragraph subtitulo3 = new Paragraph("Reporte cantidad de viandas por colaborador", parrafoFont);
      subtitulo3.setAlignment(Element.ALIGN_CENTER);
      documento.add(subtitulo3);

      // Tabla de viandas por colaborador
      String[] cabeceraViandasxColab = {"Colaborador", "Cantidad de viandas"};
      PdfPTable tablaViandasxcolaborador = CreadorDeTabla.crearNuevaTabla(cabeceraViandasxColab);

      // Creo un HashMap con key colaborador
      HashMap<Colaborador, Integer> viandasPorColaborador = new HashMap<>();

      for (Vianda vianda : viandas) {
        Colaborador colaborador = vianda.getColaborador();
        if (viandasPorColaborador.containsKey(colaborador)) {
          viandasPorColaborador.put(colaborador, viandasPorColaborador.get(colaborador)+1);
        } else {
          viandasPorColaborador.put(colaborador, 1);
        }
      }

      // Agrego los datos a la tabla
      for (Map.Entry<Colaborador, Integer> entry : viandasPorColaborador.entrySet()) {
        tablaViandasxcolaborador.addCell(entry.getKey().getNombre());
        tablaViandasxcolaborador.addCell(String.valueOf(entry.getValue()));
      }

      documento.add(tablaViandasxcolaborador);
      documento.close();


    } catch (Exception e) {
      System.err.println("Error al generar el PDF: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static void agregarIntegrantes(PdfPTable table) {
    table.addCell("Tomás");
    table.addCell("Pauza Sager");
    table.addCell("Juan Manuel");
    table.addCell("Prividera");
    table.addCell("Elías Martín");
    table.addCell("Mouesca");
    table.addCell("Felipe");
    table.addCell("Russo");
    table.addCell("Francisco");
    table.addCell("Mosquera Alfaro");
  }
  }
