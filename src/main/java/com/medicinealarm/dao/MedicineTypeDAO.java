package com.medicinealarm.dao;

import java.util.List;

import com.medicinealarm.model.MedicineType;

public interface MedicineTypeDAO {
	
	public void insert(String medicineType);
	public MedicineType findMedicineTypeById(int id);
	public int findIdByTypeName(String typeName);
	public List<String> getMedicineTypeList();
	
}
