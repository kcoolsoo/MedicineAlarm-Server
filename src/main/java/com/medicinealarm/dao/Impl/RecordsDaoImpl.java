package com.medicinealarm.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.medicinealarm.dao.RecordsDAO;
import com.medicinealarm.model.Records;

public class RecordsDaoImpl implements RecordsDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insert(Records records) {
		
		String sql = "INSERT INTO Records (patient_id, type_id, time_to_take, records, record_date) VALUES (?, ?, ?, ?, CURDATE())";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, records.getPatient_id());
			ps.setInt(2, records.getType_id());
			ps.setTime(3, records.getTime_to_take());
			ps.setString(4, records.getRecords());
			
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
	public List<Records> findRecordsByPatientIdWithPage(int id, int pageNum, String record_date) {
		
		String sql = "SELECT * FROM Records WHERE patient_id = ? AND record_date = ? ORDER BY id DESC LIMIT " + (pageNum-1)*10 + ",10";
		//String sql = "SELECT * FROM Records WHERE patient_id = ? ORDER BY id DESC LIMIT " + (pageNum-1) + ",1";

		Connection conn = null;
		List<Records> records = new ArrayList<Records>();
		
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, record_date);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Records record = new Records(rs.getInt("id"),
											rs.getInt("patient_id"),
											rs.getInt("type_id"),
											rs.getTime("time_to_take"),
											rs.getString("records"),
											rs.getString("record_date"));	
				records.add(record);
			}
			rs.close();
			ps.close();
			
			return records;
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
	public int getTotalNumberOfRecordOfPatientId(int id, String record_date) {
		
		String sql = "SELECT count(*) FROM Records WHERE patient_id = ? AND record_date = ?";
		Connection conn = null;		
		int totalNumOfRecord = 0;
		
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, record_date);
			
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				totalNumOfRecord = rs.getInt(1);
			}
			
			rs.close();
			ps.close();
			
			return totalNumOfRecord;
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
	public List<Records> findRecordsByPatientId(int id) {
		String sql = "SELECT * FROM Records WHERE patient_id = ?";

		Connection conn = null;
		List<Records> records = new ArrayList<Records>();
		
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Records record = new Records(rs.getInt("id"),
											rs.getInt("patient_id"),
											rs.getInt("type_id"),
											rs.getTime("time_to_take"),
											rs.getString("records"),
											rs.getString("record_date"));	
				records.add(record);
			}
			rs.close();
			ps.close();
			
			return records;
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
