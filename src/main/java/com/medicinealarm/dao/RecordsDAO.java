package com.medicinealarm.dao;

import java.util.List;

import com.medicinealarm.model.Records;

public interface RecordsDAO {

	public void insert(Records records);
	public List<Records> findRecordsByPatientIdWithPage(int id, int pageNum, String record_date);
	public List<Records> findRecordsByPatientId(int id);
	public int getTotalNumberOfRecordOfPatientId(int id, String record_date);
	
}
