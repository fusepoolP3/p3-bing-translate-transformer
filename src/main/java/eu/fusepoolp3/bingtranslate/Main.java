package eu.fusepoolp3.bingtranslate;

import eu.fusepool.p3.transformer.server.TransformerServer;
import org.wymiwyg.commons.util.arguments.ArgumentHandler;

public class Main {

    public static void main(String[] args) throws Exception {
        Arguments arguments = ArgumentHandler.readArguments(Arguments.class, args);
        if (arguments != null) {
            start(arguments);
        }
    }

    private static void start(Arguments arguments) throws Exception {
        TransformerServer server = new TransformerServer(arguments.getPort());

        server.start(new BingTranslateTransformer(arguments.getClientId(), arguments.getClientSecret()));

        server.join();
    }
}
