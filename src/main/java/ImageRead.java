import java.io.File;
import net.sourceforge.tess4j.*;
import net.sourceforge.tess4j.util.LoadLibs;

public class ImageRead {
    public static void main(String[] args) {

        File imageFile = new File("samples/eurotext.tif");
        ITesseract instance = new Tesseract();
        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}