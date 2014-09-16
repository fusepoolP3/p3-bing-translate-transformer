package eu.fusepoolp3.bingtranslate;

import org.wymiwyg.commons.util.arguments.ArgumentsWithHelp;
import org.wymiwyg.commons.util.arguments.CommandLine;

public interface Arguments extends ArgumentsWithHelp {

    @CommandLine(longName = "port", shortName = {"P"}, required = false,
    defaultValue = "7100",
    description = "The port on which the proxy shall listen")
    public int getPort();

    @CommandLine(longName = "client_id", shortName = {"CI"}, required = false,
    description = "The client id for the Microsoft Translator API")
    public String getClientId();

    @CommandLine(longName = "client_secret", shortName = {"CS"}, required = false,
    description = "The client secret for the Microsoft Translator API")
    public String getClientSecret();
}
