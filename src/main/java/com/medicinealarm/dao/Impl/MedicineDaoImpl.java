package com.medicinealarm.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.medicinealarm.dao.MedicineDAO;
import com.medicinealarm.model.Medicine;

public class MedicineDaoImpl implements MedicineDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(Medicine medicine) {

		String sql = "INSERT INTO Medicine (patient_id, type_id, time_to_take) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, medicine.getPatient_id());
			ps.setInt(2, medicine.getType_id());
			ps.setTime(3, medicine.getTime_to_take());

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
	public List<Medicine> findMedicineListByPatientId(int patient_id) {

		String sql = "SELECT * FROM Medicine WHERE patient_id = ?";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, patient_id);
			ArrayList<Medicine> medicineList = new ArrayList<Medicine>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Medicine medicine = new Medicine(rs.getInt("id"),
						rs.getInt("patient_id"), rs.getInt("type_id"),
						rs.getTime("time_to_take"));
				medicineList.add(medicine);
			}
			rs.close();
			ps.close();

			return medicineList;
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
	public List<Integer> findMedicineListByTime(int id, String time) {

		String sql = "SELECT type_id FROM Medicine WHERE patient_id = ? AND time_to_take=?";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			ps.setTime(2, Time.valueOf(time));

			ResultSet rs = ps.executeQuery();
			List<Integer> listType = new ArrayList<Integer>();
			while (rs.next()) {
				listType.add(rs.getInt("type_id"));
			}
			rs.close();
			ps.close();

			return listType;
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
	public Medicine findMedicineById(int id) {
		String sql = "SELECT * FROM Medicine WHERE id = ?";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			Medicine medicine = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				medicine = new Medicine(rs.getInt("id"),
						rs.getInt("patient_id"), rs.getInt("type_id"),
						rs.getTime("time_to_take"));
			}
			rs.close();
			ps.close();

			return medicine;
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
	public void updateMedicine(Medicine medicine) {
		String sql = "UPDATE Medicine SET type_id = ?, time_to_take = ? WHERE id = ?";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, medicine.getType_id());
			ps.setString(2, medicine.getTime_to_take().toString());
			ps.setInt(3, medicine.getId());
			
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
	public void deleteMedicine(int id) {
		String sql = "DELETE FROM Medicine WHERE id = " + id;
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
