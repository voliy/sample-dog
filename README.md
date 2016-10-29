# sample-dog
Sample Dog project

## Hot to run integration tests
- start application
- run Maven command: `mvn -Dtest=**/*ITCase.java test`

## Tomcat thread pool configuration
Add to *server.xml*: `<Executor name="tomcatThreadPool" namePrefix="catalina-exec-" maxThreads="5" minSpareThreads="1" maxQueueSize="5"/>`
