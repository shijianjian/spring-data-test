package com.example.model.DAO;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by shijian on 06/02/2017.
 */
public class EmployeeDAO {

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    /**
     * Created by shijian on 06/02/2017.
     */
    private JdbcTemplate template;

}
