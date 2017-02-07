package com.example.model.DAO;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by shijian on 06/02/2017.
 */
public class EmployeeDAO {

    /**
     * Created by shijian on 06/02/2017.
     */
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

//    public List<Employee> getAllEmployees() {
//        return template.query("select * from employee", new ResultSetExtractor<List<Employee>>() {
//            @Override
//            public List<Employee> extractData(ResultSet rs) throws SQLException,
//                    DataAccessException {
//
//                List<Employee> list = new ArrayList<Employee>();
//                while (rs.next()) {
//                    Employee e = new Employee();
//                    e.setId(rs.getInt(1));
//                    e.setName(rs.getString(2));
//               e     e.setSalary(rs.getInt(3));
//                    list.add(e);
//                }
//                return list;
//            }
//        });
//
//    }
}
