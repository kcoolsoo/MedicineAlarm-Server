package com.medicinealarm.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.medicinealarm.dao.PatientDAO;
import com.medicinealarm.model.Patient;

public class PatientDaoImpl implements PatientDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insert(Patient patient) {

		String sql = "INSERT INTO Patient (id, password, name, age, addr_street, addr_city, addr_state, zip, phone, authority) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'ROLE_USER')";

		Connection conn = null;

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		try {
			conn = dataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, patient.getId());
			ps.setString(2, passwordEncoder.encode(patient.getPassword()));
			//ps.setString(1, patient.getPassword());
			ps.setString(3, patient.getName());
			ps.setInt(4, patient.getAge());
			ps.setString(5, patient.getAddress_street());
			ps.setString(6, patient.getAddress_city());
			ps.setString(7, patient.getAddress_state());
			ps.setInt(8, patient.getZip());
			ps.setString(9, patient.getPhone());
		
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void updatePoints(int id, int points) {
		
		String sql = "UPDATE Patient SET points = points + ? WHERE id = ?";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, points);
			ps.setInt(2, id);
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		
	}

	public Patient findPatientById(int id) {

		String sql = "SELECT * FROM Patient WHERE id = ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			Patient patient = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				patient = new Patient(rs.getInt("id"),
						rs.getString("password"), rs.getString("name"),
						rs.getInt("age"), rs.getString("addr_street"),
						rs.getString("addr_city"), rs.getString("addr_state"),
						rs.getInt("zip"), rs.getString("phone"),
						rs.getInt("points"));
			}
			rs.close();
			ps.close();

			return patient;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public int findNextId() {
		
		String sql = "SELECT max(id) FROM Patient";
		
		Connection conn = null;
		Statement stmt = null;
		int id = -1;
		
		try {
			conn = dataSource.getConnection();			
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				id = rs.getInt("max(id)");
			}
			rs.close();
			stmt.close();
			
			return id+1;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(Patient patient) {
		String sql = "UPDATE Patient SET name = ?," +
				"age = ?, " +
				"addr_street = ?, " +
				"addr_city = ?, " +
				"addr_state = ?, " +
				"zip = ?, " +
				"phone = ? " +
				"WHERE id = ?";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, patient.getName());
			ps.setInt(2, patient.getAge());
			ps.setString(3, patient.getAddress_street());
			ps.setString(4, patient.getAddress_city());
			ps.setString(5, patient.getAddress_state());
			ps.setInt(6, patient.getZip());
			ps.setString(7, patient.getPhone());
			ps.setInt(8, patient.getId());
		
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM Patient WHERE id = " + id;
		Connection conn = null;		
		
		try {
			conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
