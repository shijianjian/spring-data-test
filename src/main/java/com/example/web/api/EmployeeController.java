package com.example.web.api;

import com.example.model.Employee;
import com.example.ws.PostgresAccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by shijian on 06/02/2017.
 */
@RestController
public class EmployeeController {

    private static String table = "company";

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/api/employees",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Employee>> getEmployees() {

        PostgresAccess pa = new PostgresAccess();

        Collection<Employee> employees = pa.getEmployees("","*", table);

        return new ResponseEntity<Collection<Employee>>(employees, HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/api/columns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<String>> getColumns() {

        PostgresAccess pa = new PostgresAccess();

        Collection<String> columns = pa.getColumns(table);

        return new ResponseEntity<Collection<String>>(columns, HttpStatus.OK);

    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/api/employee",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Employee>> getEmployee(@RequestParam("query") String query){

        PostgresAccess pa = new PostgresAccess();

        Collection<Employee> employees = pa.getEmployees(query, "*", table);

        return new ResponseEntity<Collection<Employee>>(employees, HttpStatus.OK);
    }
}
