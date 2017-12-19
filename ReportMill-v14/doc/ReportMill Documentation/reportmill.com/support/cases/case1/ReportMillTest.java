import com.reportmill.base.*;
import com.reportmill.shape.*;
import java.io.*;
import java.util.*;

/**
 * ReportMillTest
 *
 * This is a simple test of ReportMill. It just creates a few Customer objects, loads (or creates) a template,
 * and generates a report. You should be able to compile and run this on any Java VM, just make sure the ReportMill
 * jar is in your classpath:
 *
 *    prompt> javac -cp ReportMillX.jar ReportMillTest.java
 *    prompt> java -cp .;ReportMillX.jar ReportMillTest (on Windows)
 *    prompt> java -cp .:ReportMillX.jar ReportMillTest (on Unix)
 *
 */
public class ReportMillTest {

public static void main(String args[]) throws Exception
{
    // Assemble list of customers
    List customers = new Vector();
    customers.add(new Customer("Elmer Binkman", "212.555.3325"));
    customers.add(new Customer("Susan Thomson", "805.555.6682"));
    customers.add(new Customer("Terrance Donahue", "612.555.3421"));
    customers.add(new Customer("Xavior Kelsanse", "322.555.8825"));
    
    // Generate XML dataset. Note: XML is only used to facilitate template design. Once you have a good
    // representetive XML file, never generate XML again (delete this code).
    if(!new File("Store.xml").exists()) {
        new RMXMLWriter().writeObject(customers, "Store.xml");
        System.out.println("Generated ReportMill Dataset: Store.xml");
    }
    
    // Get template, generate report and write PDF
    RMDocument template = getTemplate();
    RMDocument report = template.generateReport(customers);
    report.writePDF("Store.pdf");
    System.out.println("Generated PDF Report: Store.pdf");
}

/**
 * Customer class - encapsulates customer name & phone and a derived attribute getNamePhone().
 */
public static class Customer {
    String   _name;
    String   _phone;
    public Customer(String n, String p) { _name = n; _phone = p; }
    public String getName() { return _name; }
    public String getPhone() { return _phone; }
    public String getNamePhone() { return _name + " (" + _phone + ")"; }
    public String getName(Object aPrefix) { return aPrefix.toString() + getName(); }
}

/**
 * This method should simply load a template and return it. However, for the sake of a stand-alone example, this
 * method will programmatically generate a quick sample template if it's missing and write it to the current directory.
 * As a standard practice, don't "code" your templates - you can draw them much faster in the layout app.
 */
static RMDocument getTemplate()
{
    // If template is missing, create one (just for the purposes of this stand-alone example).
    // Note: Don't waste your time "coding" templates. Draw them with the layout app!
    if(!new File("Store.rpt").exists()) {
        RMDocument template = new RMDocument(612, 792); // 8.5" x 11"
        RMTable table = new RMTable("Objects");
        table.setBounds(36, 36, 540, 720);
        template.getPage(0).addChild(table);
        table.getRow(0).getColumn(0).setText("@getName@");
        table.getRow(0).getColumn(1).setText("@getPhone@");
        table.getRow(0).getColumn(2).setText("@getNamePhone@");
        template.write("Store.rpt");
        System.out.println("Generated template: Store.rpt");
    }
    
    // Load the new template and return it (you might store this in a static variable for efficient re-use).
    return new RMDocument("Store.rpt");
}

}