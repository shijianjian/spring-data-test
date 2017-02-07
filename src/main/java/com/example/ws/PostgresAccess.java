package com.example.ws;

import com.example.model.ComplexQuery;
import com.example.model.Employee;
import com.example.tools.CovertingTool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashSet;

public class PostgresAccess
{
//  public static void main(String []args) throws URISyntaxException
//  {
//
//    System.out.println("Working Directory = " +System.getProperty("user.dir"));
//
////    File contextFile = new File("context.xml");
////    if (! contextFile.exists()) {
////    	System.out.println("ERROR: " + contextFile.getAbsolutePath() + " does not exist!");
////    	System.exit(1);
////    }
////    else {
////    	System.out.println(" " + contextFile.getAbsolutePath() + " does exist!");
////    }
////    System.out.println(new File(PostgresAccess.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
//
//    // context.xml is under /target/classes
//    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
//    DataSource ds = (DataSource)applicationContext.getBean("dataSource");
//    JdbcTemplate jt = new JdbcTemplate(ds);
//
//    jt.execute("create table elephant (id int, name varchar)");
//    jt.execute("insert into elephant (id, name) values (1, 'elephant_1')");
//    jt.execute("insert into elephant (id, name) values (2, 'elephant_2')");
//
//    Object[] parameters = new Object[] {new Integer(2)};
//    Object o = jt.queryForObject("select name from elephant where id = ?", parameters, String.class);
//    System.out.println((String)o);
//  }

  private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
  private static DataSource ds = (DataSource)applicationContext.getBean("dataSource");
  private static JdbcTemplate jt = new JdbcTemplate(ds);

  public Collection<Employee> getEmployees(String param, String target, String table) {
    Collection<Employee> set = new HashSet<Employee>();

    ComplexQuery cq =
            new ComplexQuery(
                    param,
                    target,
                    CovertingTool.ObjectArraryToStringArray(getColumns(table).toArray()),
                    table,
                    false);

    jt.query(cq.toString(),
            (rs, rowNum) -> new Employee(
                    rs.getInt("id"),
                    rs.getString("name").trim(),
                    rs.getString("address").trim(),
                    rs.getInt("salary"),
                    rs.getDate("join_date"))
    ).forEach(employee -> set.add(employee));

    return set;
  }

  public Collection<String> getColumns(String tableName) {
    Collection<String> res = new HashSet<String>();

    jt.query("SELECT column_name FROM information_schema.columns WHERE table_name='" + tableName + "'",
            (rs, rsNum) -> res.add(rs.getString("column_name").trim()));

    return res;
  }
}
