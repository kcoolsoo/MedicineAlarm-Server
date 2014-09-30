package com.medicinealarm.dao;

import java.util.List;

import com.medicinealarm.model.Medicine;

public interface MedicineDAO {
	
	public void insert(Medicine medicine);
	public Medicine findMedicineById(int id);
	public List<Medicine> findMedicineListByPatientId(int patient_id);
	public List<Integer> findMedicineListByTime(int id, String time);
	public void updateMedicine(Medicine medicine);
	public void deleteMedicine(int id);
	
}
