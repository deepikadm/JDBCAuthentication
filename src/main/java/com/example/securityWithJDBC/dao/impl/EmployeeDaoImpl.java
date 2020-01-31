package com.example.securityWithJDBC.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.securityWithJDBC.dao.EmployeeDao;
import com.example.securityWithJDBC.model.Employee;



@Repository
public class EmployeeDaoImpl  implements EmployeeDao{
	

	/*
	 * @Autowired DataSource dataSource;
	 */
	private JdbcTemplate jdbcTemplate;
	
	 public EmployeeDaoImpl(DataSource dataSource) {
		 jdbcTemplate = new JdbcTemplate(dataSource);
	    }
	
	/*
	 * @PostConstruct private void initialize(){ setDataSource(dataSource); }
	 */
	@Override
	public void insertEmployee(Employee emp) {
		String sql = "INSERT INTO employee " +
				"(empId, empName) VALUES (?, ?)" ;
		jdbcTemplate.update(sql, new Object[]{
				emp.getEmpId(), emp.getEmpName()
		});
	}
	
	
	/*public EmployeeDaoImpl(NamedParameterJdbcTemplate template) {  
        this.template = template;  
	}  
	NamedParameterJdbcTemplate template;  
	
	@Override
	public void insertEmployee(Employee emp) {
		 final String sql = "insert into employee(empId, empName) values(:empId,:empName)";
		        KeyHolder holder = new GeneratedKeyHolder();
		        SqlParameterSource param = new MapSqlParameterSource()
		.addValue("empId", emp.getEmpId())
		.addValue("empName", emp.getEmpName());
		        template.update(sql,param, holder);
		}*/
	
	@Override
	public void insertEmployees(final List<Employee> employees) {
		String sql = "INSERT INTO employee " + "(empId, empName) VALUES (?, ?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Employee employee = employees.get(i);
				ps.setString(1, employee.getEmpId());
				ps.setString(2, employee.getEmpName());
			}
			
			public int getBatchSize() {
				return employees.size();
			}
		});

	}
	@Override
	public List<Employee> getAllEmployees(){
		String sql = "SELECT * FROM employee";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		
		List<Employee> result = new ArrayList<Employee>();
		for(Map<String, Object> row:rows){
			Employee emp = new Employee();
			emp.setEmpId((String)row.get("empId"));
			emp.setEmpName((String)row.get("empName"));
			result.add(emp);
		}
		
		return result;
	}

	@Override
	public Employee getEmployeeById(String empId) {
		String sql = "SELECT * FROM employee WHERE empId = ?";
		return (Employee)jdbcTemplate.queryForObject(sql, new Object[]{empId}, new RowMapper<Employee>(){
			@Override
			public Employee mapRow(ResultSet rs, int rwNumber) throws SQLException {
				Employee emp = new Employee();
				emp.setEmpId(rs.getString("empId"));
				emp.setEmpName(rs.getString("empName"));
				return emp;
			}
		});
	}

	
}