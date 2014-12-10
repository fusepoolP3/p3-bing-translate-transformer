package eu.fusepool.p3.transformer.bingtranslate;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import eu.fusepool.p3.transformer.HttpRequestEntity;
import eu.fusepool.p3.transformer.SyncTransformer;
import eu.fusepool.p3.transformer.TransformerException;
import eu.fusepool.p3.transformer.commons.Entity;
import eu.fusepool.p3.transformer.commons.util.WritingEntity;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Gabor
 */
public class BingTranslateTransformer implements SyncTransformer {

    final private String clienId;
    final private String clientSectret;

    public BingTranslateTransformer(String clienId, String clientSectret) {
        this.clienId = clienId;
        this.clientSectret = clientSectret;
    }

    @Override
    public Entity transform(final HttpRequestEntity entity) throws IOException {
        final String queryString = entity.getRequest().getQueryString();
        final String original = IOUtils.toString(entity.getData(), "UTF-8");

        // get query params from query string
        Map<String, String> queryParams = getQueryParams(queryString);

        // query string must not be empty
        if (queryParams.isEmpty()) {
            throw new TransformerException(HttpServletResponse.SC_BAD_REQUEST, "ERROR: Query string must not be empty!\nUsage: http://<bing_transformer>/?from=<from_language>&to=<to_language>");
        }

        long start, end;
        start = System.currentTimeMillis();

        // set client id and secret
        Translate.setClientId(clienId);
        Translate.setClientSecret(clientSectret);

        // language string to translate from
        String from = queryParams.get("from");

        // language string to translate to
        String to = queryParams.get("to");

        // language to translate from
        Language fromLanguage = Language.fromString(from);
        if (fromLanguage == null) {
            fromLanguage = Language.AUTO_DETECT;
        }

        // language to translate to
        Language toLanguage = Language.fromString(to);
        if (toLanguage == null) {
            throw new TransformerException(HttpServletResponse.SC_BAD_REQUEST, "ERROR: No language was supplied to translate to!\nUsage: http://<bing_transformer>/?from=<from_language>&to=<to_language>");
        }

        // translated text
        final String translation;

        if (original != null && !original.isEmpty()) {
            // translate original text
            try {
                System.out.print("Translating input text from " + StringUtils.capitalize(fromLanguage.name().toLowerCase()) + " to " + StringUtils.capitalize(toLanguage.name().toLowerCase()) + " (" + original.length() + ") ...");

                translation = Translate.execute(original, fromLanguage, toLanguage);

                end = System.currentTimeMillis();
                System.out.println(" done [" + Double.toString((double) (end - start) / 1000) + " sec] .");

//                node.addProperty(RDF.type, new UriRef("http://example.org/ontology#LanguageAnnotation"));
//                node.addPropertyValue(SIOC.content, translation);
//                node.addPropertyValue(new UriRef("http://example.org/ontology#textLength"), translation.length());
                return new WritingEntity() {
                    @Override
                    public MimeType getType() {
                        return entity.getType();
                    }

                    @Override
                    public void writeData(OutputStream outputStream) throws IOException {
                        IOUtils.copy((InputStream) new ByteArrayInputStream(translation.getBytes()), outputStream);
                    }
                };

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new TransformerException(HttpServletResponse.SC_BAD_REQUEST, "ERROR: No input data was supplied!");
        }
    }

    @Override
    public Set<MimeType> getSupportedInputFormats() {
        try {
            MimeType mimeType = new MimeType("text/plain");
            return Collections.singleton(mimeType);
        } catch (MimeTypeParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Set<MimeType> getSupportedOutputFormats() {
        try {
            MimeType mimeType = new MimeType("text/plain");
            return Collections.singleton(mimeType);
        } catch (MimeTypeParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean isLongRunning() {
        return false;
    }

    /**
     * Get query parameters from a query string.
     *
     * @param queryString the query string
     * @return HashMap containing the query parameters
     */
    private Map<String, String> getQueryParams(String queryString) {
        try {
            Map<String, String> temp = new HashMap<>();
            // query string should not be empty or blank
            if (StringUtils.isNotBlank(queryString)) {
                String[] params = queryString.split("&");
                String[] param;
                for (String item : params) {
                    param = item.split("=", 2);
                    temp.put(param[0], param[1]);
                }
            }
            return temp;
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new TransformerException(HttpServletResponse.SC_BAD_REQUEST, "ERROR: Failed to parse query string!\nUsage: http://<bing_transformer>/?from=<from_language>&to=<to_language>");
        }
    }
}
