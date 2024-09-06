package ar.edu.utn.frba.dds.simeal.utils.reporte;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class HerramientasPDF {

  public static PdfPTable crearNuevaTabla(String[] columnNames) throws DocumentException {
    PdfPTable table = new PdfPTable(columnNames.length);
    table.setWidthPercentage(100);
    table.setSpacingBefore(10);

    // Añadir cabeceras
    for (String columnName : columnNames) {
      PdfPCell headerCell = new PdfPCell(new Phrase(columnName, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
      headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
      table.addCell(headerCell);
    }

    return table;
  }

  public static void agregarIntegrantes(PdfPTable table) {
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
