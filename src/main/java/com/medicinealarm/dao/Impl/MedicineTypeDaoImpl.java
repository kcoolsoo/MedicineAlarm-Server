package com.medicinealarm.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.medicinealarm.dao.MedicineTypeDAO;
import com.medicinealarm.model.MedicineType;

public class MedicineTypeDaoImpl implements MedicineTypeDAO {
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insert(String medicineType) {
		
		String sql = "INSERT INTO MedicineType (type) VALUES (?)";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, medicineType);
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public MedicineType findMedicineTypeById(int id) {
		
		String sql = "SELECT * FROM MedicineType WHERE id = ?";		
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			MedicineType medicineType = null;
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				medicineType = new MedicineType(rs.getInt("id"),
									rs.getString("type"));
			}
			rs.close();
			ps.close();
			
			return medicineType;
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
	public List<String> getMedicineTypeList() {
		
		String sql = "SELECT type FROM MedicineType";
		Connection conn = null;
		List<String> medicineTypeList = new ArrayList<String>();
		
		try {
			conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				///MedicineType medicineType = new MedicineType(rs.getInt("id"), rs.getString("type"));
				medicineTypeList.add(rs.getString("type"));
			}
			rs.close();
			stmt.close();
			
			return medicineTypeList;		
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
	public int findIdByTypeName(String typeName) {
		
		String sql = "SELECT id FROM MedicineType WHERE type = ?";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, typeName);
			
			ResultSet rs = ps.executeQuery();
			int id = -1;
			if(rs.next()) {
				id = rs.getInt("id");
			}
			rs.close();
			ps.close();
			
			return id;
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
