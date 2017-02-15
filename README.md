## springboot-seed-addressbook

---
#### Usage:
1. mvn package
2. cp context.xml ./target/classes/
3. mvn spring-boot:run
4. Example usage in browser:
        http://localhost:8080/api/employees
        http://localhost:8080/api/employee?query=California

---
#### PostgreSQL configuration
PostgreSQL commands for this particular data structure :
    Initialization: initdb -D ./data
    Start the server: postgres -D ./data
    connection : psql DATABASENAME
Use example.sql to initialise database.

---
#### Notes:
JDBC connection configuration is under ./target/context.xml.
