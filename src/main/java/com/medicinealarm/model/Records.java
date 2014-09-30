package com.medicinealarm.model;

import java.sql.Time;

public class Records {
	int id;
	int patient_id;
	int type_id;
	Time time_to_take;
	String records;
	String record_date;

	public Records() {
		super();
	}

	public Records(int id, int patient_id, int type_id, Time time_to_take,
			String records, String record_date) {
		super();
		this.id = id;
		this.patient_id = patient_id;
		this.type_id = type_id;
		this.time_to_take = time_to_take;
		this.records = records;
		this.record_date = record_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public Time getTime_to_take() {
		return time_to_take;
	}

	public void setTime_to_take(Time time_to_take) {
		this.time_to_take = time_to_take;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public String getRecord_date() {
		return record_date;
	}

	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}
}
