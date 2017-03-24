# Java Client for Hyphenate Services

The Java Client for Hyphenate Services is for use in server applications on a proxy server, aka, your developer server. Client library serves as a wrapper layer on top of raw REST APIs that allows you to deploy on your server and make APIs requests. [See Hyphenate APIs](http://docs.hyphenate.io/docs/server-overview).

## Support

This library is open source. We encourage you to contribute to make the code base better! If you encountered any bug or feature suggestion, please [submit an issue](https://github.io.hyphenateInc/hyphenate-server-client-java/issues) or email support@hyphenate.io for urgent fixes.


## Requirement

- Hyphenate API key. [learn more](https://docs.hyphenate.io/docs/getting-started) or [create one](https://console.hyphenate.io).
- Java 1.7 or later. Latest version of the [Java](http://java.sun.com/javase/downloads/index.jsp).


## Installation

You can add the library via Gradle or Maven, which we recommended, or compile it from command line. 


### Maven

**pom.xml**
 
```xml
<dependency>
    <groupId>io.hyphenate.server</groupId>
    <artifactId>hyphenate-services</artifactId>
    <version>(insert latest version)</version>
</dependency>
```


### Gradle

**build.gradle**

```xml
repositories {
    mavenCentral()
}

dependencies {
    compile 'io.hyphenate:hyphenate-services:(insert latest version)'
    ...
}
```

### Configuration 

Update Hyphenate app configurations in `config.properties`.

```java
API_ORG = hyphenatedemo
API_APP = demo
APP_CLIENT_ID = YXA68E7DkM4uEeaPwTPbScypMA
APP_CLIENT_SECRET = YXA63_RZdbtXQB9QZsizSCgMC70_4Rs
```

## Dependencies
	
 - package io.hyphenate.server.jersey is using Jersey 2.15. Java 7 or later is required.
 - package io.hyphenate.server.httpclient is using Httpclient 4.3.3. Java 1.5 or later is required.
 
 - Install JUnit library (ex. junit:junit:4.12)
 
### Get all the dependencies

We recommend using [maven](http://maven.apache.org) or [gradle](http://gradle.org) to build server components.  

### Generate keystore

[Keystore Generation with public site certificate](https://docs.hyphenate.io/docs/keystore-generation-with-public-cer)

## REST APIs Documentation 

[Usage](https://api-docs.hyphenate.io)

### Rate Limiting

By default, requests are sent at the expected rate limits for each web service, typically 30 queries per second for free users.  
Each IP address is limited to 30 requests per second. Requests beyond this limit will receive a 429 or 503 error. If you received this error, please reduce the request frequency and try again.
Please contact Hyphenate info@hyphenate.io if you need higher request rate.

## Install Java IDE

You can use IntelliJ, Eclipse, NetBeans or any Java IDE you prefer to the run the project.

### Eclipse

#### Unix or Mac
 
    $ gradle eclipse

#### Windows

Run the following command, it'll generate necessary Eclipse files, then import via Eclipse

    > gradlew.bat eclipse

### Intellij IDEA

#### Unix or Mac

- "Import Project" -> select project root folder -> select "Import project from external model" -> select "Gradle" -> select "Use auto-import" and "Create separate module per source set" -> select "Use default gradle wrapper" -> Select "1.7" for Gradle JVM -> ".idea" for project format -> click "Finish" 

- [Setup unit testing](https://docs.hyphenate.io/docs/keystore-generation-with-public-cer#section-note-here-s-the-setup-to-run-unit-testing-for-intellij-idea)

#### Windows

Run the following command and open the project from Intellij IDEA

    > gradlew.bat idea



## Build from Command line

#### Unix or Mac

Compile and package the project

```bash
$ ./gradlew jar
```

see all Gradle commands
```bash
$ ./gradlew tasks
```


#### Windows

	> gradlew.bat clean compile

#### Windows

If you're not using the tools listed above, you can manual manage the .jar package by running the following command.

    > gradlew.bat distZip

That will create a zip file, hyphenate-server-example.zip, under the folder _build/distributions_, which includes all the dependency    
 
