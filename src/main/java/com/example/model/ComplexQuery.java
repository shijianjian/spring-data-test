package com.example.model;

import com.example.tools.CovertingTool;

/**
 * Created by shijian on 07/02/2017.
 */
public class ComplexQuery {

    private String param;
    private String[] targets;
    private String[] columns;
    private String table;
    private boolean explicitly;

    /**
     *  Construct a complex query for querying one param in multi columns
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

    public ComplexQuery(String param, String targets, String[] columns, String table, boolean explicitly) {
        this.param = param.toLowerCase();
        String[] array = { targets };
        this.targets = array;
        this.columns = columns;
        this.table = table;
        this.explicitly = explicitly;
    }

    // return all the columns with ','
    private String columnsConcat(String[] columns) {
        String res = CovertingTool.StringArrayToString(columns);
        return res;
    }

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
                // TODO: DELETE THIS IF STATEMENT IN THE FUTURE
                if(!column.contains("date") && !column.contains("id") && !column.contains("salary") && !column.contains("age")) {

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

    @Override
    public String toString() {
        String x =targetColumns(targets);
        String res = "SELECT " + x + " FROM " + table;
        if(!param.equals("")) {
            res += " WHERE " + conditions(param, columns);
        }
        return res;
    }

}
