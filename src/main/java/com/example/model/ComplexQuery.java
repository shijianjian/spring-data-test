package com.example.model;

import com.example.tools.CovertingTool;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * Created by shijian on 07/02/2017.
 */
public class ComplexQuery {

    private String param;
    private String[] targets;
    private String[] columns;
    private String table;
    private boolean explicitly;
    private HashMap<String, String> dataMap;

    /**
     * Construct a complex query for querying one param in multi targets(selected column)
     * @param param
     * @param targets
     * @param columns
     * @param table
     * @param explicitly
     */
    public ComplexQuery(String param, String[] targets, String[] columns, String table, boolean explicitly) {
        this.param = param.toLowerCase();
        this.targets = targets;
        this.columns = columns;
        this.table = table;
        this.explicitly = explicitly;
    }

    /**
     * Construct a complex query for querying one param in one or all (*) targets(selected column)
     * @param param
     * @param targets
     * @param columns
     * @param table
     * @param explicitly
     */
    public ComplexQuery(String param, String targets, String[] columns, String table, boolean explicitly) {
        this.param = param.toLowerCase();
        String[] array = { targets };
        this.targets = array;
        this.columns = columns;
        this.table = table;
        this.explicitly = explicitly;
    }

    public ComplexQuery(HashMap<String, String> dataMap, String table) {
        this.dataMap = dataMap;
        this.table = table;
    }

    public ComplexQuery() {

    }

    // return all the columns with ','
    private String targetColumns(String[] targets) {
        String res = "*";
        if(!CovertingTool.StringArrayToString(targets).equals("*")){
            res = CovertingTool.StringArrayToString(targets);
        }
        return res;
    }

    // return the query after WHERE
    private String conditions(String param, String[] columns){
        StringBuffer res = new StringBuffer();
            if (!explicitly)
                param = implicityParam(param);
            for (String column : columns) {
                // exclude id
                if(!column.contains("id")) {
                    if (res.toString().equals("")) {
                        res.append("");
                    } else {
                        res.append(" OR ");
                    }
                    res.append("lower(" + column + ") LIKE " + param);
                }
            }
        return res.toString();
    }

    // make query patterns like '%param%'
    private String implicityParam(String param) {
        String res = "'%" + param + "%'";
        return res;
    }

    public String SELECT() {
        String x =targetColumns(targets);
        String res = "SELECT " + x + " FROM " + table;
        if(!param.equals("")) {
            res += " WHERE " + conditions(param, columns);
        }
        return res;
    }

    public String CREATE() {
        StringBuffer col = new StringBuffer();
        StringBuffer val = new StringBuffer();
        StringBuffer res = new StringBuffer();
        res.append("INSERT INTO " + table + " ");
        col.append("(");
        val.append("(");
        for(Map.Entry<String, String> entry : dataMap.entrySet()){
            if(entry.getKey() != "id") {
                col.append("," + entry.getKey());
                String v = "\'" + entry.getValue() + "\'";
                val.append("," + v);
            }
        }
        Date date = new Date();
        long id = date.getTime();
        col.append(",id)"); col.deleteCharAt(1);
        val.append(",\'"+id+"\')"); val.deleteCharAt(1);
        res.append(col + " VALUES " + val + ";");
        return res.toString();
    }

    public String UPDATE() {
//        UPDATE Customers
//        SET ContactName='Alfred Schmidt', City='Frankfurt'
//        WHERE CustomerID=1;
        String query = "UPDATE " + table + " SET ";
        StringBuffer val = new StringBuffer();
        String id = "";
        for(Map.Entry<String, String> entry : dataMap.entrySet()){
            if(entry.getKey() != "id") {
                String v = "\'" + entry.getValue() + "\'";
                val.append("," + entry.getKey() + "=" + v);
            } else {
                id = entry.getValue().toString();
            }
        }
        val.deleteCharAt(0);
        query = query + val.toString() + " WHERE id=" + "\'" + id + "\'";
        System.out.println(query);
        return query;
    }

    public String DELETE(String id, String table) {
        return "DELETE FROM " + table + " WHERE id=" + id;
    }

    public String queryById(String id) {
        return "SELECT * FROM " + table + " WHERE id=" + "\'" + id + "\'";
    }


}
