import com.reportmill.base.*;
import com.reportmill.shape.*;

/**
 * This class is a simple example of conditionally adding several PDFs together to get a composite document.
 *
 * Compile: prompt> javac -cp ReportMillX.jar RMPDFMerge.java
 * Run:     prompt> java -cp ReportMillX.jar:. RMPDFMerge title logo body
 *
 * The program is accompanied by three PDF files: title.pdf, logo.pdf and body.pdf. Simply specify their names on the
 * command line to include them in the composite PDF.
 */
public class RMPDFMerge {

/** Main method. */
public static void main(String args[])
{
    // Create an empty doc that is letter size (8.5" x 11").
    RMDocument doc = new RMDocument(612, 792);
    
    // Iterate over command line args to load PDF images and add them to document's first page.
    for(int i=0; i<args.length; i++)
        doc.getPage(0).addChild(new RMImage(args[i] + ".pdf"));
    
    // Write composite PDF
    doc.writePDF("RMPDFMerge.pdf");
}

}

