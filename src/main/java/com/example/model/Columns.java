package com.example.model;

import com.example.ws.PostgresAccess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by shijian on 09/02/2017.
 */
public class Columns {
    private static ArrayList<String> cols = new ArrayList<String>();

    public static void init(PostgresAccess pa, String table) {
        Collection<String> columns = pa.getColumns(table);

        for(String col : columns) {
            add(col);
        }
    }

    public static void add(String col){
        cols.add(col);
    }

    public static ArrayList<String> read() {
        return cols;
    }
}
