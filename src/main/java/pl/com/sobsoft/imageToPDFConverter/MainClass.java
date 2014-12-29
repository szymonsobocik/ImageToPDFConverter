package pl.com.sobsoft.imageToPDFConverter;

import com.google.common.base.Strings;
import com.lowagie.text.DocumentException;
import com.lowagie.toolbox.arguments.filters.PdfFilter;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * user: ssobocik
 */
public class MainClass {

    private String outputFileName;
    private Float rotation;

    public static void main(String[] args) {

        MainClass mainClass = new MainClass();
        if (args != null && args.length > 0) {
            mainClass.outputFileName = args[0];
            if (args.length > 1) {
                mainClass.rotation = new Float(args[1]);
            }
        }

        try {
            mainClass.execute();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DocumentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private JFileChooser fileChooser;

    private void execute() throws IOException, DocumentException {
        List<File> images = getImages();
        if (images == null || images.isEmpty()) {
            return;
        }

        String outputFileName = getOutputFilename();

        createPDF(images, outputFileName);
    }

    private String getOutputFilename() throws IOException {
        if (this.outputFileName == null || this.outputFileName.trim().equals("")) {
            JFileChooser fileChooser = getFileChooser();
            fileChooser.setDialogTitle("Save output file as");
            fileChooser.setFileFilter(new PdfFilter());
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                this.outputFileName = fileChooser.getSelectedFile().getCanonicalPath();
            }
        }

        return this.outputFileName;
    }

    private List<File> getImages() {
        JFileChooser fileChooser = getFileChooser();
        fileChooser.setDialogTitle("Open images to merge to PDF");
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return Arrays.asList(fileChooser.getSelectedFiles());
        }
        return null;
    }

    private void createPDF(List<File> images, String outputFileName) throws IOException, DocumentException {
        if (Strings.isNullOrEmpty(outputFileName)) {
            throw new RuntimeException("Not specified output file name");
        }

        outputFileName = makeSureItsPDF(outputFileName);

        PDFCreator pdfCreator = new PDFCreator(outputFileName, images);
        pdfCreator.setRotationDegrees(rotation);
        pdfCreator.writeDoc();
    }

    private String makeSureItsPDF(String outputFileName) {
        if (!outputFileName.toLowerCase().endsWith(".pdf")) {
            outputFileName += ".pdf";
        }
        return outputFileName;
    }

    public JFileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = createFileChooser();
        }
        return fileChooser;
    }

    private JFileChooser createFileChooser() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setMultiSelectionEnabled(true);
        return jFileChooser;
    }
}
