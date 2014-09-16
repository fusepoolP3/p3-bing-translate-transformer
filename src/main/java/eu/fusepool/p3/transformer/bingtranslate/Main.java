package eu.fusepool.p3.transformer.bingtranslate;

import eu.fusepool.p3.transformer.server.TransformerServer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.wymiwyg.commons.util.arguments.ArgumentHandler;

public class Main {

    public static void main(String[] args) throws Exception {
        Arguments arguments = ArgumentHandler.readArguments(Arguments.class, args);
        if (arguments != null) {
            start(arguments);
        }
    }

    private static void start(Arguments arguments) throws Exception {
        final String clienId;
        final String clientSectret;
        
        TransformerServer server = new TransformerServer(arguments.getPort());

        // if client id and client secret was supplied from commandline use that
        if (!StringUtils.isEmpty(arguments.getClientId()) && !StringUtils.isEmpty(arguments.getClientSecret())) {
            clienId = arguments.getClientId();
            clientSectret = arguments.getClientSecret();
        } else {
            // otherwise try to get it from properties file
            Properties prop = new Properties();
            InputStream input = null;

            try {

                input = new FileInputStream("config.properties");
                prop.load(input);

                // get client id and client secret from config file
                clienId = prop.getProperty("client_id");
                clientSectret = prop.getProperty("client_secret");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        if (StringUtils.isEmpty(clienId)) {
            throw new RuntimeException("Client ID must not be empty!");
        }

        if (StringUtils.isEmpty(clientSectret)) {
            throw new RuntimeException("Client Secret must not be empty!");
        }

        server.start(new BingTranslateTransformer(clienId, clientSectret));
        server.join();
    }
}
