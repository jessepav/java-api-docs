import java.io.*;
import java.util.*;
import com.reportmill.base.*;
import com.reportmill.graphics.*;
import com.reportmill.shape.*;

/**
 * ReportMillTest
 *
 * This is a simple example of ReportMill.Listener and its didFillShape() callback method. If you generateReport()
 * with a ReportMill.Listener instance provided in the userInfo, any named report elements (set in the paint bucket
 * inspector of the app) will call didFillShape() giving the original shape and its resulting copy.
 *
 * This example checks the value of a PhoneText text field and sets the shape to have a red background if it's
 * area code is local (assumed to be 212 for this example).
 *
 *    prompt> javac -classpath ReportMill7.jar ReportMillTest.java
 *    prompt> java -cp .;ReportMill7.jar ReportMillTest (on Windows)
 *    prompt> java -cp .:ReportMill7.jar ReportMillTest (on Unix)
 *
 */
public class ReportMillTest {

public static void main(String args[]) throws Exception
{
    // Assemble list of customers
    List customers = new Vector();
    customers.add(new Customer("Elmer Binkman", "212.555.3325"));
    customers.add(new Customer("Susan Thomson", "805.555.6682"));
    customers.add(new Customer("Terrance Donahue", "212.555.3421"));
    customers.add(new Customer("Xavior Kelsanse", "322.555.8825"));
    customers.add(new Customer("Ellenor Houston", "212.555.7971"));
    
    // Generate XML dataset. Note: XML is only used to facilitate template design. Once you have a good
    // representetive XML file, never generate XML again (delete this code).
    if(!new File("Store.xml").exists()) {
        new RMXMLWriter().writeObject(customers, "Store.xml");
        System.out.println("Generated ReportMill Dataset: Store.xml");
    }
    
    // Get template, generate report and write PDF
    RMDocument template = getTemplate();
    RMDocument report = template.generateReport(customers, new MyListener());
    report.writePDF("Store.pdf");
    System.out.println("Generated PDF Report: Store.pdf");
}

// A ReportMill.Listener implementation to get callback when named shapes are filled.
public static class MyListener implements ReportMill.Listener {

    // Implement this method to get a hook for filled shapes. 
    public void didFillShape(ReportMill aReportMill, RMShape aShape, RMShape aCopy) {
        if("PhoneText".equals(aShape.getName())) {
            RMText phoneText = (RMText)aCopy;
            if(phoneText.getText().startsWith("212"))
                aCopy.setColor(RMColor.red);
        }
    }
}

/** Customer class - encapsulates customer name & phone and a derived attribute getNamePhone(). */
public static class Customer {
    String   _name;
    String   _phone;
    public Customer(String n, String p) { _name = n; _phone = p; }
    public String getName() { return _name; }
    public String getPhone() { return _phone; }
    public String getNamePhone() { return _name + " (" + _phone + ")"; }
}

/**
 * This method should simply load a template and return it. However, for the sake of a stand-alone example, this
 * method will programmatically generate a quick sample template if it's missing and write it to the current directory.
 * As a standard practice, don't "code" your templates - you can draw them much faster in the layout app.
 */
static RMDocument getTemplate()
{
    // Create template just for the purposes of this stand-alone example.
    // Note: Don't waste your time "coding" templates. Draw them with the layout app!
    RMDocument template = new RMDocument(612, 792); // 8.5" x 11"
    RMTable table = new RMTable("Objects");
    table.setBounds(36, 36, 540, 720);
    template.getPage(0).addChild(table);
    table.getRow(0).setNumberOfColumns(3);
    table.getRow(0).getColumn(0).setText("@getName@");
    table.getRow(0).getColumn(1).setText("@getPhone@");
    table.getRow(0).getColumn(1).setName("PhoneText");
    table.getRow(0).getColumn(2).setText("@getNamePhone@");
    return template;
}

}