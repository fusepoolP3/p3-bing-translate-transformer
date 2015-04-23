package eu.fusepool.p3.transformer.bingtranslate;

import org.wymiwyg.commons.util.arguments.ArgumentsWithHelp;
import org.wymiwyg.commons.util.arguments.CommandLine;

public interface Arguments extends ArgumentsWithHelp {

    @CommandLine(longName = "port", shortName = {"P"}, required = false,
            defaultValue = "8309",
            description = "The port on which the proxy shall listen")
    public int getPort();

    @CommandLine(longName = "enableCors", shortName = {"C"}, required = false,
            description = "Enable a liberal CORS policy",
            isSwitch = true)
    public boolean enableCors();

    @CommandLine(longName = "client_id", shortName = {"CI"}, required = false,
            description = "The client id for the Microsoft Translator API")
    public String getClientId();

    @CommandLine(longName = "client_secret", shortName = {"CS"}, required = false,
            description = "The client secret for the Microsoft Translator API")
    public String getClientSecret();
}
