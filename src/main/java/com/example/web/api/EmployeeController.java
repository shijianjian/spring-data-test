package com.example.web.api;

import com.example.model.Columns;
import com.example.tools.CovertingTool;
import com.example.ws.PostgresAccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by shijian on 06/02/2017.
 */
@RestController
public class EmployeeController {

    private static String table = "company";
    private static PostgresAccess pa = new PostgresAccess();

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/api/employees",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<HashMap<String, String>>> getEmployees() {

        Columns.init(pa, table);

        Collection<HashMap<String, String>> materials = pa.getEmployees("","*", table);

        return new ResponseEntity<Collection<HashMap<String, String>>>(materials, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/api/columns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, String>> getColumns() {

        Collection<String> columns = pa.getColumns(table);

        HashMap<String, String> keyValuePairs = new HashMap<String, String>();

        for(String item : columns) {
            keyValuePairs.put(item, "");
        }

        return new ResponseEntity<HashMap<String, String>>(keyValuePairs, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/api/employee",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<HashMap<String, String>>> getEmployee(@RequestParam("query") String query){

        Columns.init(pa, table);

        PostgresAccess pa = new PostgresAccess();

        Collection<HashMap<String, String>> materials = pa.getEmployees(query, "*", table);

        return new ResponseEntity<Collection<HashMap<String, String>>>(materials, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/api/employee/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<HashMap<String, String>>> addEmployee(@RequestBody String addNew){

        Columns.init(pa, table);

        Collection<HashMap<String, String>> materials = new HashSet<>();


        try {
            HashMap<String, String> hashMap = CovertingTool.JSONStringToHashMap(addNew);
            PostgresAccess pa = new PostgresAccess();
            pa.insertMaterial(hashMap, table);
            Columns.init(pa, table);
            materials = pa.getEmployees("","*", table);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Collection<HashMap<String, String>>>(materials, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/api/employee/update",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<HashMap<String, String>>> updateEmployee(@RequestBody String updates) {

        Collection<HashMap<String, String>> res = new HashSet<HashMap<String, String>>();
        String id = "";
        System.out.println(updates);

        try {
            HashMap<String, String> hashMap = CovertingTool.JSONStringToHashMap(updates);
            PostgresAccess pa = new PostgresAccess();
            pa.updateMaterial(hashMap, table);

            for(Map.Entry<String, String> entry : hashMap.entrySet()){
                if(entry.getKey() == "id") {
                     id = entry.getValue().toString();
                }
            }
            res = pa.getMaterialById(hashMap, id, table);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Collection<HashMap<String, String>>>(res, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/api/employee/delete",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<HashMap<String, String>>> deleteEmployee(@RequestBody String id) {

        Collection<HashMap<String, String>> res = new HashSet<HashMap<String, String>>();

            PostgresAccess pa = new PostgresAccess();
            pa.deleteMaterialbyId(id, table);

        return new ResponseEntity<Collection<HashMap<String, String>>>(res, HttpStatus.OK);
    }

}
