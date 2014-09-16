Bing Translate Transformer
=============================
Bing Translate is P3 transformer for language translation. The transformer uses the Microsoft Translator via its API to translate text data. Accessing the API requires a Client ID and a Client Secret.

###Install

To clone the repository to your local machine

    git clone https://github.com/fusepoolP3/p3-bing-translate-transfromer.git

Compile and run with

    mvn clean install exec:java
    
Example invocation with curl
    
    $ curl -X POST -d @file.txt "http://localhost:7102/?from=de&to=en"
    []    a       <http://example.org/ontology#LanguageAnnotation> ;
      <http://example.org/ontology#textLength>
              "16"^^<http://www.w3.org/2001/XMLSchema#int> ;
      <http://rdfs.org/sioc/ns#content>
              "Translated text."^^<http://www.w3.org/2001/XMLSchema#string> .

###How to get Cleint ID and Client Secret

To gain access to Microsoft Translator API, you need to do the following steps:
 1. Register for an account on Windows Azure Marketplace.
 2. Sign up for the Microsoft Translator API using your registered account.
 3. Register your application on Windows Azure Marketplace.
 4. Get the Client ID and Client Secret for your registered application.

For more information please visit here: http://blogs.msdn.com/b/translation/p/gettingstarted1.aspx
