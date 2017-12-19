import com.reportmill.base.*;
import com.reportmill.shape.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;

/*
 * This class generates a tabular report with a dynamically generated image provided by each table object for each
 * table row.
 */
public class RMImageTest {

/**
 * main() - This method generates some sample data in the form of MovieReview objects, loads them into a list
 *    and generates a PDF report using the template provided by the getTemplate method.
 */
public static void main(String args[])
{
    // Create some data
    MovieReview mr1 = new MovieReview("Raiders of the Lost Ark", 10);
    MovieReview mr2 = new MovieReview("ToyStory", 8);
    MovieReview mr3 = new MovieReview("Weekend at Bernie's", 2);
    MovieReview mr4 = new MovieReview("The Matrix", 9);
    MovieReview mr5 = new MovieReview("Catch Me If You Can", 7);
    List list = new ArrayList();
    list.add(mr1); list.add(mr2); list.add(mr3); list.add(mr4); list.add(mr5);
    
    // Request "headless" support since we have Java 2D calls in the MovieReview class (Java 1.4 only)
    System.setProperty("java.awt.headless", "true");
    
    // Get template, generate report and write PDF
    RMDocument template = getTemplate();
    RMDocument report = template.generateReport(list);
    report.write("RMImageTest.pdf");
    
    System.exit(0);
}

/**
 * getTemplate() - This method dynamically generates an RMTemplate. It would be much easier to just draw the
 * template in the layout app and load it, but this programatic example might be useful.
 */
public static RMDocument getTemplate()
{
    // Create template
    RMDocument template = new RMDocument(612, 792);
    
    // Create table, size it and add it to first template page
    RMTable table = new RMTable();
    table.setBounds(36, 36, 540, 720);
    template.getPage(0).addChild(table);
    
    // Create tableRow, turn off structuring and set height to 20
    RMTableRow tableRow = table.addDetails("Objects");
    tableRow.setStructured(false);
    tableRow.setHeight(20);
    
    // Create text field and add to tableRow
    RMText text = new RMText("@Row@. @title@");
    text.setBounds(0, 0, 200, 20);
    tableRow.addChild(text);
    
    // Create RMImage and set key
    RMImage image = new RMImage(null);
    image.setKey("chart");
    image.setBounds(250, 0, 250, 20);
    tableRow.addChild(image);

    return template;
}

/**
 * MovieReview class - An inner class to store movie reviews and generate a 200x20 pixel JPEG chart
 */
public static class MovieReview {
    public String title;
    public int    rating;
    public MovieReview(String aTitle, int aRating) { title = aTitle; rating = aRating; }
    public byte[] chart() {
        BufferedImage image = new BufferedImage(250, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 250, 20);
        g.setColor(Color.red);
        for(int i=0; i<rating; i++)
            g.fillRect(i*20, 2, 18, 18);
        return RMAWTUtils.getBytesJPEG(image);
    }
}

}