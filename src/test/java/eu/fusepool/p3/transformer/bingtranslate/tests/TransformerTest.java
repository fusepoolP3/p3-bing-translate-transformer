package eu.fusepool.p3.transformer.bingtranslate.tests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import eu.fusepool.p3.transformer.bingtranslate.BingTranslateTransformer;
import eu.fusepool.p3.transformer.server.TransformerServer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Bing Translate Transformer
 */
public class TransformerTest {

    final private String clientId = "FP3JavaTranslator";

    final private String clientSecret = "lFGMlv3EI7cmjPCKOpQrwbqJw0B7lTqBd3OvuJ9kzs4=";

    final private String testText = "Die NASA wurde am 29. Juli 1958 durch den „National Aeronautics and Space Act“ gegründet. Damit wurde durch Präsident Dwight D. Eisenhower auf den Rat seines Wissenschaftsberaters James Killian entschieden, dass das zivile Raumfahrtprogramm durch eine Raumfahrtorganisation durchgeführt werden soll. Vorläufer war u.a. das National Advisory Committee for Aeronautics, das der Luftwaffe unterstand. Die neue Behörde nahm am 1. Oktober 1958 ihre Arbeit auf, wobei sie die circa 8.000 Angestellten der NACA übernahm. Zum ersten Administrator der NASA wurde Thomas Keith Glennan ernannt.";

    private String baseURI;

    @Before
    public void setUp() throws Exception {
        final int port = findFreePort();
        baseURI = "http://localhost:" + port + "/";
        TransformerServer server = new TransformerServer(port, false);
        server.start(new BingTranslateTransformer());
    }

    @Test
    public void turtleOnGet() {
        Response response = RestAssured.given().header("Accept", "text/plain")
                .expect().statusCode(HttpStatus.SC_OK).header("Content-Type", "text/turtle").when()
                .get(baseURI);
    }

    @Test
    public void turtlePost() {
        Response response = RestAssured.given().header("Accept", "text/plain")
                .contentType("text/plain; charset=UTF-8")
                .content(testText)
                .expect().statusCode(HttpStatus.SC_OK).header("Content-Type", "text/plain; charset=UTF-8").when()
                .post(baseURI + "?client-id=" + clientId + "&client-secret=" + clientSecret + "&from=de&to=en");
        try {
            String translation = IOUtils.toString(response.getBody().asInputStream(), "UTF-8");
            Assert.assertFalse("Response contained no text.", StringUtils.isEmpty(translation));
        } catch (IOException ex) {
            Assert.assertFalse(ex.getMessage(), true);
        }
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
