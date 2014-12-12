Bing Translate Transformer
=============================
The Bing Translate Transformer is a machine language translator that uses the Microsoft Translator via its API to translate text from one language to another. Accessing the API requires a Client ID and a Client Secret which can be obtained from Windows Azure Marketplace.

The Client ID and the Client Secret must be supplied either as environmental variable or as command line arguments. (See the Install and run section.)

###Install and run

Create the following two environmental variables (optional)

    P3_BT_CI – client ID
    P3_BT_CS – client secret

Compiled the source code and run the application with

    mvn clean install exec:java -Dexec.args="-CI <client_id> -CS <client_secret> -P 7100 -C"

`-CI` client ID (required if not set as environmental variable)

`-CS` client secret (required if not set as environmental variable)

`-P`  sets the port (optional)

###Usage

The transformer only supports `text/plain` as input type, and it produces `text/plain` as output.

    curl -X GET "http://localhost:7100/"
    <http://localhost:7101/>
          <http://vocab.fusepool.info/transformer#supportedInputFormat>
                  "text/plain"^^<http://www.w3.org/2001/XMLSchema#string> ;
          <http://vocab.fusepool.info/transformer#supportedOutputFormat>
                  "text/plain"^^<http://www.w3.org/2001/XMLSchema#string> .

To invoke the transformer with text to translate use
    
    $ curl -X POST -d "Sia a nord che a est la Toscana  circondata dagli Appenninima il territorio  prevalentemente collinare." "http://localhost:7100/?from=it&to=en"
    To the North and East the Tuscany surrounded by the Apennines but mostly hilly territory.

###How to get Cleint ID and Client Secret

To gain access to Microsoft Translator API, you need to do the following steps:
 1. Register for an account on Windows Azure Marketplace.
 2. Sign up for the Microsoft Translator API using your registered account.
 3. Register your application on Windows Azure Marketplace.
 4. Get the Client ID and Client Secret for your registered application.

For more information please visit here: http://blogs.msdn.com/b/translation/p/gettingstarted1.aspx
