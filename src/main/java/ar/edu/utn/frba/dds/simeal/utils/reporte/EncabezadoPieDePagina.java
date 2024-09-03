package ar.edu.utn.frba.dds.simeal.utils.reporte;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;

public class EncabezadoPieDePagina extends PdfPageEventHelper {
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
    // Revisar el tema de los path
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