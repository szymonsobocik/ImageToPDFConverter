package pl.com.sobsoft.imageToPDFConverter;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.*;
import java.util.List;

/**
 * user: ssobocik
 */
public class PDFCreator {

    private List<File> images;
    private String whereToSave;
    private Float rotationDegrees;

    public PDFCreator(String whereToSave, List<File> images) {
        this.images = images;
        this.whereToSave = whereToSave;
    }

    public void writeDoc() throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(whereToSave));
        document.open();

        addImages(document);

        document.close();
    }

    private void addImages(Document document) throws DocumentException, IOException {
        for (File fileImage : images) {
            Image image = Image.getInstance(fileImage.getPath());

            if (rotationDegrees != null){
                image.setRotationDegrees(rotationDegrees);
            }

            document.setPageSize(new Rectangle(image.getScaledWidth(), image.getScaledHeight()));
            document.newPage();
            document.add(image);
        }

    }

    public void setRotationDegrees(Float rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
    }
}
