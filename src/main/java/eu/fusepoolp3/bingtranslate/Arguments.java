package eu.fusepoolp3.bingtranslate;

import org.wymiwyg.commons.util.arguments.ArgumentsWithHelp;
import org.wymiwyg.commons.util.arguments.CommandLine;

public interface Arguments extends ArgumentsWithHelp {

    @CommandLine(longName = "port", shortName = {"P"}, required = false,
    defaultValue = "7102",
    description = "The port on which the proxy shall listen")
    public int getPort();

    @CommandLine(longName = "client_id", shortName = {"I"}, required = false,
    defaultValue = "<client_id>",
    description = "The client id for the Microsoft Translator API")
    public String getClientId();

    @CommandLine(longName = "client_secret", shortName = {"S"}, required = false,
    defaultValue = "<client_secret>",
    description = "The client secret for the Microsoft Translator API")
    public String getClientSecret();
}
