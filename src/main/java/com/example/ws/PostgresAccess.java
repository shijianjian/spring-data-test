package com.example.ws;

import com.example.model.Columns;
import com.example.model.ComplexQuery;
import com.example.tools.CovertingTool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class PostgresAccess
{
  private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
  private static DataSource ds = (DataSource)applicationContext.getBean("dataSource");
  private static JdbcTemplate jt = new JdbcTemplate(ds);

  public Collection<HashMap<String, String>> getEmployees(String param, String target, String table) {
      Collection<HashMap<String, String>> set = new HashSet<HashMap<String, String>>();

    ComplexQuery cq =
            new ComplexQuery(
                    param,
                    target,
                    CovertingTool.ObjectArraryToStringArray(getColumns(table).toArray()),
                    table,
                    false);

      jt.query(cq.SELECT(),
              (rs, resNum) -> {
                    HashMap<String, String> res = new HashMap<String, String>();
                    for(String col : Columns.read()) {
                        String name = col.toLowerCase();
                        res.put(name, rs.getString(name));
                    }
                    return res;
              }).forEach(item -> {
                    set.add(item);
              });

    return set;
  }

  public Collection<String> getColumns(String tableName) {
    Collection<String> res = new HashSet<String>();

    jt.query(ComplexQuery.COLUMNS(tableName),
            (rs, rsNum) -> res.add(rs.getString("column_name").trim()));

    return res;
  }

  public String insertMaterial(HashMap<String, String> dataMap, String table) {
      ComplexQuery cq = new ComplexQuery(dataMap, table);
      Collection<String> res = new HashSet<String>();

      jt.update(cq.CREATE());

      return res.toString();
  }

  public String updateMaterial(HashMap<String, String> dataMap, String table) {
      ComplexQuery cq = new ComplexQuery(dataMap, table);
      Collection<String> res = new HashSet<String>();

      jt.execute(cq.UPDATE());

      return res.toString();
  }

  public Collection<HashMap<String, String>> getMaterialById(HashMap<String, String> dataMap, String id, String table) {

      Collection<HashMap<String, String>> set = new HashSet<>();

      jt.query(ComplexQuery.queryById(id, table),
              (rs, resNum) -> {
                  HashMap<String, String> res = new HashMap<String, String>();
                  for(String col : Columns.read()) {
                      String name = col.toLowerCase();
                      res.put(name, rs.getString(name));
                  }
                  return res;
              }).forEach(item -> {
                  set.add(item);
                });
      return set;
  }

  public void deleteMaterialbyId(String id , String table) {
          jt.execute(ComplexQuery.DELETE(id, table));
  }

}
