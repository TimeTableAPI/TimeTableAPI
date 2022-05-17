package pt.iscte.asd.projectn3.group11.services;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public final class SwrlService {

    public static final String OWL_FILE = "src/main/resources/ADS.owl";

    public static List<String> querry(int numberOfObjectivesQuerry) {
        File owlFile = new File(OWL_FILE);
        try {
            // Loading an OWL ontology using the OWLAPI
            OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(owlFile);

            // Create SQWRL query engine using the SWRLAPI
            SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

            // Create and execute a SQWRL query using the SWRLAPI
            // Os algoritmos que funcionam bem para problemas com 2,3,4,etc. objetivps são diferentes
            String numberOfObjectives = Integer.toString(numberOfObjectivesQuerry);
            String query = "Algorithm(?alg) ^ "
                    + "minObjectivesAlgorithmIsAbleToDealWith(?alg,?min) ^ swrlb:lessThanOrEqual(?min," + numberOfObjectives + ")"
                    + "maxObjectivesAlgorithmIsAbleToDealWith(?alg,?max) ^ swrlb:greaterThanOrEqual(?max," + numberOfObjectives + ")"
                    + " -> sqwrl:select(?alg) ^ sqwrl:orderBy(?alg)";
            SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

            // Process the SQWRL result
            System.out.println("Query: \n" + query + "\n");
            System.out.println("Result: ");
            List<String> results = new LinkedList<>();
            while (result.next()) {
                System.out.println(result.getNamedIndividual("alg").getShortName().substring(1));
                results.add(result.getNamedIndividual("alg").getShortName().substring(1));
            }

            return results;

        } catch (OWLOntologyCreationException e) {
            System.err.println("Error creating OWL ontology: " + e.getMessage());
            return null;
        } catch (SWRLParseException e) {
            System.err.println("Error parsing SWRL rule or SQWRL query: " + e.getMessage());
            return null;
        } catch (SQWRLException e) {
            System.err.println("Error running SWRL rule or SQWRL query: " + e.getMessage());
            return null;
        } catch (RuntimeException e) {
            System.err.println("Error starting application: " + e.getMessage());
            return null;
        }
    }
}
