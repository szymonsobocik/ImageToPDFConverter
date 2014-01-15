package pl.com.sobsoft.imageToPDFConverter;

import com.lowagie.text.DocumentException;

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

    public static void main(String[] args) {

        MainClass mainClass = new MainClass();
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
        if (images == null || images.isEmpty()){
            return;
        }
        createPDF(images);
    }

    private List<File> getImages() {
        JFileChooser fileChooser = getFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            return Arrays.asList(fileChooser.getSelectedFiles());
        }
        return null;
    }

    private void createPDF(List<File> images) throws IOException, DocumentException {
        PDFCreator pdfCreator = new PDFCreator("TestPDF.pdf", images);
        pdfCreator.writeDoc();
    }

    public JFileChooser getFileChooser() {
        if (fileChooser == null){
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
