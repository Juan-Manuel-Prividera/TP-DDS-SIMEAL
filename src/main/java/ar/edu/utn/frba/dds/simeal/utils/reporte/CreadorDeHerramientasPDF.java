package ar.edu.utn.frba.dds.simeal.utils.reporte;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class CreadorDeHerramientasPDF {

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
}
