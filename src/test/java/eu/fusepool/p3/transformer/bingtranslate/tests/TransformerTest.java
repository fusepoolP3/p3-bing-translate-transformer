package eu.fusepool.p3.transformer.bingtranslate.tests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import eu.fusepool.p3.transformer.bingtranslate.BingTranslateTransformer;
import eu.fusepool.p3.transformer.server.TransformerServer;
import java.net.ServerSocket;
import java.util.Iterator;
import org.apache.clerezza.rdf.core.BNode;
import org.apache.clerezza.rdf.core.Graph;
import org.apache.clerezza.rdf.core.Resource;
import org.apache.clerezza.rdf.core.Triple;
import org.apache.clerezza.rdf.core.UriRef;
import org.apache.clerezza.rdf.core.serializedform.Parser;
import org.apache.clerezza.rdf.ontologies.RDF;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Bing Translate Transformer
 */
public class TransformerTest {

    final private String clienId = "FP3JavaTranslator";
    
    final private String clienSecret = "lFGMlv3EI7cmjPCKOpQrwbqJw0B7lTqBd3OvuJ9kzs4=";
    
    final private String testText = "Die NASA wurde am 29. Juli 1958 durch den „National Aeronautics and Space Act“ gegründet. Damit wurde durch Präsident Dwight D. Eisenhower auf den Rat seines Wissenschaftsberaters James Killian entschieden, dass das zivile Raumfahrtprogramm durch eine Raumfahrtorganisation durchgeführt werden soll. Vorläufer war u.a. das National Advisory Committee for Aeronautics, das der Luftwaffe unterstand. Die neue Behörde nahm am 1. Oktober 1958 ihre Arbeit auf, wobei sie die circa 8.000 Angestellten der NACA übernahm. Zum ersten Administrator der NASA wurde Thomas Keith Glennan ernannt.";

    private String baseURI;
    
    @Before
    public void setUp() throws Exception {
        final int port = findFreePort();
        baseURI = "http://localhost:" + port + "/";
        TransformerServer server = new TransformerServer(port);
        server.start(new BingTranslateTransformer(clienId, clienSecret));
    }

    @Test
    public void turtleOnGet() {
        Response response = RestAssured.given().header("Accept", "text/turtle")
                .expect().statusCode(HttpStatus.SC_OK).header("Content-Type", "text/turtle").when()
                .get(baseURI);
    }

    @Test
    public void turtlePost() {
        Response response = RestAssured.given().header("Accept", "text/turtle")
                .contentType("text/plain;charset=UTF-8")
                .content(testText)
                .expect().statusCode(HttpStatus.SC_OK).header("Content-Type", "text/turtle").when()
                .post(baseURI + "?from=de&to=en");
        Graph graph = Parser.getInstance().parse(response.getBody().asInputStream(), "text/turtle");
        Iterator<Triple> typeTriples = graph.filter(null, RDF.type, new UriRef("http://example.org/ontology#LanguageAnnotation"));
        Assert.assertTrue("No type triple found", typeTriples.hasNext());
        Resource textDescription = typeTriples.next().getSubject();
        Assert.assertTrue("TextDescription resource is not a BNode", textDescription instanceof BNode);
    }

    public static int findFreePort() {
        int port = 0;
        try (ServerSocket server = new ServerSocket(0);) {
            port = server.getLocalPort();
        } catch (Exception e) {
            throw new RuntimeException("unable to find a free port");
        }
        return port;
    }
}
