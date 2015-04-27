/ README for jtrace
==========================

# to create new entity 

yo jhipster:entity author

# to create new service

yo jhipster:service bar

# to test application 
mvn clean test
grunt test

# using in Development
mvn spring-boot:run

# using in Production

mvn -Pprod spring-boot:run
mvn -Pprod package

executing the war file without an application server

java -jar jhipster-0.0.1-SNAPSHOT.war --spring.profiles.active=prod

# database update 
1. Modify your JPA entity (add a field, a relationship, etc.)
2. Compile your application (this works on the compiled Java code, so don't forget to compile!)
3. Run mvn liquibase:diff (or mvn compile liquibase:diff to compile before)
4. A new "change log" is created in your src/main/resources/config/liquibase/changelog directory
5. Review this change log and add it to your src/main/resources/config/liquibase/master.xml file, so it is applied the next time you run your application