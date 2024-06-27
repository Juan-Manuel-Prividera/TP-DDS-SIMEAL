package ar.edu.utn.frba.dds.simeal.models.entities.reporte;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Reporte {

  private List<Heladera> heladeras;
  private List<Vianda> viandas;


  public Reporte(HeladeraRepository heladeraRepository, ViandaRepository viandaRepository) {
    this.heladeras = heladeraRepository.getHeladeras();
    this.viandas = viandaRepository.getViandas();
  }

  public void generarReporte() {

    Document documento = new Document(PageSize.A4, 50, 50, 50, 50);

    String pdfPath = "E:\\Tarea Fran\\Diseño de sistemas\\Pruebas\\Generador de pdf\\Pdfs Creados\\Reportes_generados.pdf";
    String imagePath = "https://github.com/fmosqueraalfaro/DDS/blob/main/ImagenesPrueba/logoSimeal.png?raw=true";
        try {

      PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(pdfPath));

      HeaderFooterPageEvent event = new HeaderFooterPageEvent();
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
      PdfPTable table = new PdfPTable(2);
      table.setSpacingBefore(20);
      table.setSpacingAfter(20);
      addTableHeader(table);
      addRows(table);
      documento.add(table);


      // Página 2
      documento.newPage();
      Paragraph subtitulo1 = new Paragraph("Reporte cantidad de fallas generados por heladeras", parrafoFont);
      subtitulo1.setAlignment(Paragraph.ALIGN_CENTER);
      documento.add(subtitulo1);

      // Tabla de incidentes generados por heladera

      PdfPTable tabla1 = new PdfPTable(2);
      tabla1.setWidthPercentage(100);
      tabla1.setSpacingBefore(10);

      PdfPCell cell1 = new PdfPCell(new Paragraph("Heladera"));
      cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
      tabla1.addCell(cell1);

      PdfPCell cell2 = new PdfPCell(new Phrase("Cantidad de incidentes"));
      cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
      tabla1.addCell(cell2);

      for (Heladera h : heladeras) {
        tabla1.addCell(h.getNombre());
        tabla1.addCell(String.valueOf(h.getIncidentes().size()));
      }

      documento.add(tabla1);

      // Página 3
      documento.newPage();

      Paragraph subtitulo2 = new Paragraph("Reporte cantidad de viandas retiradas/colocadas por heladera", parrafoFont);
      subtitulo2.setAlignment(Element.ALIGN_CENTER);
      documento.add(subtitulo2);

      // Tabla de cantidad de viandas retiradas/colocadas por heladera

      //TODO



      // Página 4
      documento.newPage();

      Paragraph subtitulo3 = new Paragraph("Reporte cantidad de viandas por colaborador", parrafoFont);
      subtitulo3.setAlignment(Element.ALIGN_CENTER);
      documento.add(subtitulo3);

      // Tabla de viandas por colaborador
      PdfPTable tabla2 = new PdfPTable(2);
      tabla2.setWidthPercentage(100);
      tabla2.setSpacingBefore(10);

      HashMap<Colaborador, Integer> viandasPorColaborador = new HashMap<>();

      for (Vianda v : viandas) {
        Colaborador colaborador = v.getColaborador();
        if (viandasPorColaborador.containsKey(colaborador)) {
          viandasPorColaborador.put(colaborador, viandasPorColaborador.get(colaborador)+1);
        } else {
          viandasPorColaborador.put(colaborador, 1);
        }
      }

      for (Map.Entry<Colaborador, Integer> entry : viandasPorColaborador.entrySet()) {
        tabla2.addCell(entry.getKey().getNombre());
        tabla2.addCell(String.valueOf(entry.getValue()));
      }

      documento.add(tabla2);

      documento.close();


    } catch (Exception e) {
      System.err.println("Error al generar el PDF: " + e.getMessage());
      e.printStackTrace();
    }
  }
  private static void addTableHeader(PdfPTable table) {
    Stream.of("Nombre", "Apellido").forEach(columnTitle -> {
      PdfPCell header = new PdfPCell();
      header.setBackgroundColor(BaseColor.LIGHT_GRAY);
      header.setBorderWidth(2);
      header.setPhrase(new Phrase(columnTitle));
      table.addCell(header);
    });
  }

  private static void addRows(PdfPTable table) {
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
class HeaderFooterPageEvent extends PdfPageEventHelper {
  private Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);

  @Override
    public void onEndPage(PdfWriter writer, Document document) {
    // Pie de página
    ColumnText.showTextAligned(writer.getDirectContent(),
            Element.ALIGN_CENTER,
            new Phrase(String.format("Página %d", writer.getPageNumber()), footerFont),
            (document.right() - document.left()) / 2 + document.leftMargin(),
            document.bottom() - 10, 0);
  }

  @Override
    public void onStartPage(PdfWriter writer, Document document) {
    // Encabezado
    String leftImagePath = "https://github.com/fmosqueraalfaro/DDS/blob/main/ImagenesPrueba/Logo-UTNBA-encabezado.jpg?raw=true";
    String rightImagePath = "https://github.com/fmosqueraalfaro/DDS/blob/main/ImagenesPrueba/logoSimeal.png?raw=true";

    try {
      PdfPTable headerTable = new PdfPTable(3);
      headerTable.setWidths(new int[]{1, 5, 1});
      headerTable.setTotalWidth(527);
      headerTable.setLockedWidth(true);
      headerTable.getDefaultCell().setFixedHeight(50);
      headerTable.getDefaultCell().setBorder(Rectangle.BOTTOM);

      Image leftLogo = Image.getInstance(leftImagePath);
      headerTable.addCell(leftLogo);

      PdfPCell titleCell = new PdfPCell(new Phrase("SIMEAL", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
      titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      titleCell.setBorder(Rectangle.BOTTOM);
      headerTable.addCell(titleCell);

      Image rightLogo = Image.getInstance(rightImagePath);
      headerTable.addCell(rightLogo);

      PdfContentByte canvas = writer.getDirectContent();
      headerTable.writeSelectedRows(0, -1, 34, 803, canvas);

      document.add(new Paragraph(" "));
    } catch (DocumentException | IOException e) {
      throw new ExceptionConverter(e);
    }
    }
}