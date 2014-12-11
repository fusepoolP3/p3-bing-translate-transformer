package eu.fusepool.p3.transformer.bingtranslate;

import eu.fusepool.p3.transformer.server.TransformerServer;
import java.util.Map;
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

        // if client id and client secret was supplied from commandline use that
        if (!StringUtils.isEmpty(arguments.getClientId()) && !StringUtils.isEmpty(arguments.getClientSecret())) {
            clienId = arguments.getClientId();
            clientSectret = arguments.getClientSecret();
        } else {
            // otherwise try to get it from environmental varialbe
            Map<String, String> env = System.getenv();
            clienId = env.get("P3_BT_CI");
            clientSectret = env.get("P3_BT_CS");
        }

        if (StringUtils.isEmpty(clienId)) {
            throw new RuntimeException("Client ID must not be empty!");
        }

        if (StringUtils.isEmpty(clientSectret)) {
            throw new RuntimeException("Client Secret must not be empty!");
        }

        TransformerServer server = new TransformerServer(arguments.getPort(), arguments.enableCors());

        server.start(new BingTranslateTransformer(clienId, clientSectret));
        server.join();
    }
}
