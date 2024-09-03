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

  public static void subirArchivoAGithub(String pdfPath) {
    try {
      // Obtener el token de github
      String token = System.getenv("GITHUB_TOKEN");
      if (token == null) {
        throw new IllegalStateException("El token de GitHub no está definido en las variables de entorno");
      }

      // Autenticación con GitHub usando el token de la variable de entorno
      GitHub github = new GitHubBuilder().withOAuthToken(token).build();

      if (!github.isCredentialValid()) {
        throw new SecurityException("El token de GitHub es inválido o ha expirado.");
      }

      // Obtener el repositorio
      GHRepository repo = github.getRepository("dds-utn/2024-tpa-ma-ma-grupo-11");

      // Path en el repositorio donde se subirá el archivo
      DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      String fechaActual = LocalDate.now().format(formato);
      String pathEnRepo = "Reportes_Generados/Reporte_generado" + fechaActual + ".pdf";

      // Leer el archivo PDF
      byte[] pdfBytes = Files.readAllBytes(Paths.get(pdfPath));
      String contenidoEncodeado = Base64.getEncoder().encodeToString(pdfBytes);


      // Crear o actualizar el archivo en el repositorio
      repo.createContent()
          .path(pathEnRepo)
          .message("Subiendo reporte generado automáticamente")
          .content(contenidoEncodeado)
          .commit();

      System.out.println("Archivo subido exitosamente a GitHub.");

    } catch (IOException e) {
      System.err.println("Error al subir el archivo a GitHub: " + e.getMessage());
      e.printStackTrace();
    } catch (SecurityException e) {
      System.err.println("Fallo de seguridad: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
