package JenaPackage.Jena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        InputStream in = null;
        Model model = null;
        // Open the bloggers RDF graph from the filesystem

   		try {
        
	        String current = new java.io.File( "." ).getCanonicalPath();
	        System.out.println("Current dir:"+current);
	        String currentDir = System.getProperty("user.dir");
	        System.out.println("Current dir using System:" +currentDir);
        
			in = new FileInputStream(new File(".\\ontology\\bloggers.rdf"));

        // Create an empty in-memory model and populate it from the graph
        model = ModelFactory.createMemModelMaker().createFreshModel();
        model.read(in,null); // null base URI, since model URIs are absolute

			in.close();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Create a new query
        String queryString = 
        	"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
        	"SELECT ?url " +
        	"WHERE {" +
        	"      ?contributor foaf:name \"Jon Foobar\" . " +
        	"      ?contributor foaf:weblog ?url . " +
        	"      }";

        org.apache.jena.query.Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = (ResultSet) qe.execSelect();

        // Output query results	
        ResultSetFormatter.out(System.out, (org.apache.jena.query.ResultSet) results, query);

        // Important - free up resources used running the query
        qe.close();        
    }
}
