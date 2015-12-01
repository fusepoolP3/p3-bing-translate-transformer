# Bing Translate Transformer [![Build Status](https://travis-ci.org/fusepoolP3/p3-bing-translate-transformer.svg)](https://travis-ci.org/fusepoolP3/p3-bing-translate-transformer)
The Bing Translate Transformer is a machine language translator that uses the Microsoft Translator via its API to translate text from one language to another. Accessing the API requires a Client ID and a Client Secret which can be obtained from Windows Azure Marketplace (see section "How to get Client ID and Client Secret").

The Client ID and the Client Secret must be supplied each time the transformer is called as query string parameters.

## Compiling and Running

Compiled the source code and run the application with

    mvn clean install exec:java -Dexec.args="-P 8309 -C"

`-P`  sets the port (optional)

`-C`  CORS enabled (optional)

## Usage

The transformer only supports `text/plain` as input type, and it produces `text/plain` as output.

    curl -X GET "http://localhost:8309/"
    <http://localhost:8309/>
          <http://vocab.fusepool.info/transformer#supportedInputFormat>
                  "text/plain"^^<http://www.w3.org/2001/XMLSchema#string> ;
          <http://vocab.fusepool.info/transformer#supportedOutputFormat>
                  "text/plain"^^<http://www.w3.org/2001/XMLSchema#string> .

To invoke the transformer with text to translate use
    
    $ curl -X POST --data-binary "Sia a nord che a est la Toscana  circondata dagli Appenninima il territorio  prevalentemente collinare." "http://localhost:8309/?client-id=<...>&client-secret=<...>&from=it&to=en"
    To the North and East the Tuscany surrounded by the Apennines but mostly hilly territory.

## How to get Client ID and Client Secret

To gain access to Microsoft Translator API, you need to do the following steps:
 1. Register for an account on Windows Azure Marketplace.
 2. Sign up for the Microsoft Translator API using your registered account.
 3. Register your application on Windows Azure Marketplace.
 4. Get the Client ID and Client Secret for your registered application.

For more information please visit here: http://blogs.msdn.com/b/translation/p/gettingstarted1.aspx
