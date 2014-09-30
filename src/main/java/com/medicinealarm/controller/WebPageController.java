package com.medicinealarm.controller;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.medicinealarm.dao.MedicineDAO;
import com.medicinealarm.dao.MedicineTypeDAO;
import com.medicinealarm.dao.PatientDAO;
import com.medicinealarm.dao.RecordsDAO;
import com.medicinealarm.model.Medicine;
import com.medicinealarm.model.MedicineType;
import com.medicinealarm.model.Patient;
import com.medicinealarm.model.Records;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/*")
public class WebPageController {

	private static final Logger logger = LoggerFactory
			.getLogger(WebPageController.class);

	private static ApplicationContext context;
	private String mPatientId;

	@RequestMapping(value = "/main", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView home(@RequestParam("patientNumber") String patientId) {
		logger.info("Medicine Alarm System: Main");

		ModelAndView main = new ModelAndView("main");

		mPatientId = patientId;
		
		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");;

		Patient patient = new Patient();
		patient = patientDAO.findPatientById(Integer.parseInt(mPatientId));
		if(patient==null) {
			String errMsg = "No patient in the system.";
			main.addObject("errMsg", errMsg);
		}
		
		return main;
	}
	
	@RequestMapping(value = "/login_admin", method = RequestMethod.POST) 
	public @ResponseBody 
	ModelAndView loginAdmin() {
		logger.info("Medicine Alarm System: Admin Login");

		ModelAndView loginAdmin = new ModelAndView("login_admin");
		
		return loginAdmin;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView login() {
		logger.info("Medicine Alarm System: Login");

		ModelAndView login = new ModelAndView("login");

		return login;
	}

	@RequestMapping(value = "/logoff", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView logoff() {
		logger.info("Medicine Alarm System: Logoff");

		ModelAndView logoff = new ModelAndView("logoff");

		return logoff;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView register(Model model) {
		logger.info("Medicine Alarm System: Register");

		model.addAttribute("registerForm", new Patient());

		ModelAndView register = new ModelAndView("register");

		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");

		int nextId = patientDAO.findNextId();
		register.addObject("next_id", nextId);

		return register;
	}

	@RequestMapping(value = "/register_success", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView resgisterSuccess(Model model,
			@ModelAttribute("registerForm") Patient patient) {
		logger.info("Medicine Alarm System: Add");

		ModelAndView login = new ModelAndView("login");

		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");

		patientDAO.insert(patient);

		return login;
	}

	@RequestMapping(value = "/information", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView information(Model model) {
		logger.info("Medicine Alarm System: Information");

		model.addAttribute("informationForm", new Patient());

		ModelAndView information = new ModelAndView("information");

		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");
		MedicineDAO medicineDAO = (MedicineDAO) context.getBean("medicineDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context
				.getBean("medicineTypeDAO");

		Patient patient = new Patient();
		patient = patientDAO.findPatientById(Integer.parseInt(mPatientId));

		List<Medicine> medicineList = medicineDAO
				.findMedicineListByPatientId(patient.getId());

		ArrayList<Integer> medicineIdList = new ArrayList<Integer>();
		ArrayList<Integer> typeIdList = new ArrayList<Integer>();
		ArrayList<String> timeList = new ArrayList<String>();
		for (int i = 0; i < medicineList.size(); i++) {
			medicineIdList.add(medicineList.get(i).getId());
		}
		for (int i = 0; i < medicineList.size(); i++) {
			typeIdList.add(medicineList.get(i).getType_id());
			timeList.add(changeTimeFormat(medicineList.get(i).getTime_to_take()
					.toString()));
		}

		ArrayList<String> typeList = new ArrayList<String>();
		for (int i = 0; i < typeIdList.size(); i++) {
			typeList.add(medicineTypeDAO
					.findMedicineTypeById(typeIdList.get(i)).getType());
		}

		information.addObject("id", patient.getId());
		information.addObject("name", patient.getName());
		information.addObject("age", patient.getAge());
		information.addObject("address_street", patient.getAddress_street());
		information.addObject("address_city", patient.getAddress_city());
		information.addObject("address_state", patient.getAddress_state());
		information.addObject("zip", patient.getZip());
		information.addObject("phone", patient.getPhone());
		information.addObject("points", patient.getPoints());
		information.addObject("typeIdList", typeIdList);
		information.addObject("typeList", typeList);
		information.addObject("timeList", timeList);
		information.addObject("medicineIdList", medicineIdList);

		return information;
	}

	@RequestMapping(value = "/information_update", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView informationUpdate(Model model,
			@ModelAttribute("information") Patient patient) {
		logger.info("Medicine Alarm System: Information Update");

		model.addAttribute("informationForm", new Patient());

		ModelAndView information = new ModelAndView("information");

		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");
		MedicineDAO medicineDAO = (MedicineDAO) context.getBean("medicineDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context
				.getBean("medicineTypeDAO");

		patientDAO.update(patient);

		Patient updatedPatient = new Patient();
		updatedPatient = patientDAO.findPatientById(Integer
				.parseInt(mPatientId));
		List<Medicine> medicineList = medicineDAO
				.findMedicineListByPatientId(updatedPatient.getId());

		ArrayList<Integer> medicineIdList = new ArrayList<Integer>();
		ArrayList<Integer> typeIdList = new ArrayList<Integer>();
		ArrayList<String> timeList = new ArrayList<String>();
		for (int i = 0; i < medicineList.size(); i++) {
			medicineIdList.add(medicineList.get(i).getId());
		}
		for (int i = 0; i < medicineList.size(); i++) {
			typeIdList.add(medicineList.get(i).getType_id());
			timeList.add(changeTimeFormat(medicineList.get(i).getTime_to_take()
					.toString()));
		}

		ArrayList<String> typeList = new ArrayList<String>();
		for (int i = 0; i < typeIdList.size(); i++) {
			typeList.add(medicineTypeDAO
					.findMedicineTypeById(typeIdList.get(i)).getType());
		}

		information.addObject("id", patient.getId());
		information.addObject("name", patient.getName());
		information.addObject("age", patient.getAge());
		information.addObject("address_street", patient.getAddress_street());
		information.addObject("address_city", patient.getAddress_city());
		information.addObject("address_state", patient.getAddress_state());
		information.addObject("zip", patient.getZip());
		information.addObject("phone", patient.getPhone());
		information.addObject("points", patient.getPoints());
		information.addObject("typeIdList", typeIdList);
		information.addObject("typeList", typeList);
		information.addObject("timeList", timeList);
		information.addObject("medicineIdList", medicineIdList);

		return information;
	}
	
	@RequestMapping(value = "/information_delete", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView informationDelete(Model model,
			@ModelAttribute("information") Patient patient) {
		ModelAndView login = new ModelAndView("login");
		
		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");
		
		patientDAO.delete(patient.getId());
		
		return login;
	}

	@RequestMapping(value = "/update_medicine", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView updateMedicine(Model model,
			@RequestParam("medicine") String medicineId) {
		logger.info("Medicine Alarm System: Update Medicine");

		ModelAndView update = new ModelAndView("update_medicine");

		context = new ClassPathXmlApplicationContext("Module.xml");
		MedicineDAO medicineDAO = (MedicineDAO) context.getBean("medicineDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context
				.getBean("medicineTypeDAO");

		Medicine medicine = medicineDAO.findMedicineById(Integer
				.parseInt(medicineId));
		MedicineType medicineType = medicineTypeDAO
				.findMedicineTypeById(medicine.getType_id());

		List<String> medicineTypeList = new ArrayList<String>();
		medicineTypeList = medicineTypeDAO.getMedicineTypeList();

		String timeToTake = changeTimeFormat(medicine.getTime_to_take()
				.toString());
		StringTokenizer token = new StringTokenizer(timeToTake);
		String tokenedTime = token.nextElement().toString();
		StringTokenizer token2 = new StringTokenizer(tokenedTime, ":");

		String hour = token2.nextElement().toString();
		String minute = token2.nextElement().toString();
		String ampm = token.nextElement().toString();

		update.addObject("medicineTypeList", medicineTypeList);
		update.addObject("id", medicine.getId());
		update.addObject("patient_id", medicine.getPatient_id());
		update.addObject("type_id", medicine.getType_id());
		update.addObject("type", medicineType.getType());
		update.addObject("hour", hour);
		update.addObject("minute", minute);
		update.addObject("ampm", ampm);

		return update;
	}
	
	@RequestMapping(value = "/delete_medicine", method = RequestMethod.POST)
	public @ResponseBody ModelAndView deleteMedicine(Model model,
			@RequestParam("medicine") String medicineId) {
		logger.info("Medicine Alarm System: Delete Medicine");
		
		ModelAndView information = new ModelAndView("information");
		
		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");
		MedicineDAO medicineDAO = (MedicineDAO) context.getBean("medicineDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context
				.getBean("medicineTypeDAO");
		
		medicineDAO.deleteMedicine(Integer.parseInt(medicineId));
		
		Patient patient = new Patient();
		patient = patientDAO.findPatientById(Integer.parseInt(mPatientId));

		List<Medicine> medicineList = medicineDAO
				.findMedicineListByPatientId(patient.getId());

		ArrayList<Integer> medicineIdList = new ArrayList<Integer>();
		ArrayList<Integer> typeIdList = new ArrayList<Integer>();
		ArrayList<String> timeList = new ArrayList<String>();
		for (int i = 0; i < medicineList.size(); i++) {
			medicineIdList.add(medicineList.get(i).getId());
		}
		for (int i = 0; i < medicineList.size(); i++) {
			typeIdList.add(medicineList.get(i).getType_id());
			timeList.add(changeTimeFormat(medicineList.get(i).getTime_to_take()
					.toString()));
		}

		ArrayList<String> typeList = new ArrayList<String>();
		for (int i = 0; i < typeIdList.size(); i++) {
			typeList.add(medicineTypeDAO
					.findMedicineTypeById(typeIdList.get(i)).getType());
		}

		information.addObject("id", patient.getId());
		information.addObject("name", patient.getName());
		information.addObject("age", patient.getAge());
		information.addObject("address_street", patient.getAddress_street());
		information.addObject("address_city", patient.getAddress_city());
		information.addObject("address_state", patient.getAddress_state());
		information.addObject("zip", patient.getZip());
		information.addObject("phone", patient.getPhone());
		information.addObject("points", patient.getPoints());
		information.addObject("typeIdList", typeIdList);
		information.addObject("typeList", typeList);
		information.addObject("timeList", timeList);
		information.addObject("medicineIdList", medicineIdList);
		
		return information;
	}

	@RequestMapping(value = "/update_success", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView updateSuccess(Model model,
			@RequestParam("medicineId") String medicineId,
			@RequestParam("patient_id") String patientId,
			@RequestParam("medicineType") String typeName,
			@RequestParam("hour") String hour,
			@RequestParam("minute") String minute,
			@RequestParam("ampm") String ampm) {

		logger.info("Medicine Alarm System: Information");
		
		model.addAttribute("informationForm", new Patient());

		ModelAndView information = new ModelAndView("information");

		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");
		MedicineDAO medicineDAO = (MedicineDAO) context.getBean("medicineDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context
				.getBean("medicineTypeDAO");
		
		// update medicine info
		int medicine_id = Integer.parseInt(medicineId);
		int patient_id = Integer.parseInt(patientId);
		int typeId = -1;

		typeId = medicineTypeDAO.findIdByTypeName(typeName);
		
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		int i_hour = Integer.parseInt(hour);
		if(i_hour == 12 && ampm.equalsIgnoreCase("AM")) {
			i_hour = 0;
		} else if (ampm.equalsIgnoreCase("PM") && i_hour != 12) {
			i_hour += 12;
		}
		String time = Integer.toString(i_hour) + ":" + minute + ":" + "00";

		Time time_to_take = null;
		try {
			time_to_take = new Time(timeFormat.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Medicine medicine = new Medicine(medicine_id, patient_id, typeId, time_to_take);
		
		medicineDAO.updateMedicine(medicine);
		
		// show updated all info about the patient
		Patient patient = new Patient();
		patient = patientDAO.findPatientById(Integer.parseInt(mPatientId));
		List<Medicine> medicineList = medicineDAO
				.findMedicineListByPatientId(patient.getId());

		ArrayList<Integer> medicineIdList = new ArrayList<Integer>();
		ArrayList<Integer> typeIdList = new ArrayList<Integer>();
		ArrayList<String> timeList = new ArrayList<String>();
		for (int i = 0; i < medicineList.size(); i++) {
			medicineIdList.add(medicineList.get(i).getId());
		}
		for (int i = 0; i < medicineList.size(); i++) {
			typeIdList.add(medicineList.get(i).getType_id());
			timeList.add(changeTimeFormat(medicineList.get(i).getTime_to_take()
					.toString()));
		}

		ArrayList<String> typeList = new ArrayList<String>();
		for (int i = 0; i < typeIdList.size(); i++) {
			typeList.add(medicineTypeDAO
					.findMedicineTypeById(typeIdList.get(i)).getType());
		}

		information.addObject("id", patient.getId());
		information.addObject("name", patient.getName());
		information.addObject("age", patient.getAge());
		information.addObject("address_street", patient.getAddress_street());
		information.addObject("address_city", patient.getAddress_city());
		information.addObject("address_state", patient.getAddress_state());
		information.addObject("zip", patient.getZip());
		information.addObject("phone", patient.getPhone());
		information.addObject("points", patient.getPoints());
		information.addObject("typeIdList", typeIdList);
		information.addObject("typeList", typeList);
		information.addObject("timeList", timeList);
		information.addObject("medicineIdList", medicineIdList);

		return information;
		
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView add(Model model) {
		logger.info("Medicine Alarm System: Add");

		// model.addAttribute("addForm", new Medicine());

		ModelAndView add = new ModelAndView("add");

		context = new ClassPathXmlApplicationContext("Module.xml");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context
				.getBean("medicineTypeDAO");

		List<String> medicineTypeList = new ArrayList<String>();
		medicineTypeList = medicineTypeDAO.getMedicineTypeList();

		add.addObject("medicineTypeList", medicineTypeList);

		return add;
	}

	@RequestMapping(value = "/add_success", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView addSuccess(@RequestParam("patient_id") String patientId,
			@RequestParam("medicineType") String typeName,
			@RequestParam("type") String typeInput,
			@RequestParam("hour") String hour,
			@RequestParam("minute") String minute,
			@RequestParam("ampm") String ampm) {
		ModelAndView add = new ModelAndView("add");

		context = new ClassPathXmlApplicationContext("Module.xml");
		MedicineDAO medicineDAO = (MedicineDAO) context.getBean("medicineDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context
				.getBean("medicineTypeDAO");
		int patient_id = Integer.parseInt(patientId);
		int typeId = -1;
		if (!typeInput.isEmpty()) {
			typeId = medicineTypeDAO.findIdByTypeName(typeInput);
			if(typeId == -1) {
				medicineTypeDAO.insert(typeInput);
				typeId = medicineTypeDAO.findIdByTypeName(typeInput);
			} else {
				typeId = medicineTypeDAO.findIdByTypeName(typeInput);
			}
		} else {
			typeId = medicineTypeDAO.findIdByTypeName(typeName);
		}
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		int i_hour = Integer.parseInt(hour);
		if(i_hour == 12 && ampm.equalsIgnoreCase("AM")) {
			i_hour = 0;
		} else if (ampm.equalsIgnoreCase("PM") && i_hour != 12) {
			i_hour += 12;
		}
		String time = Integer.toString(i_hour) + ":" + minute + ":" + "00";

		Time time_to_take = null;
		try {
			time_to_take = new Time(timeFormat.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Medicine medicine = new Medicine(0, patient_id, typeId, time_to_take);
		medicineDAO.insert(medicine);
		
		List<String> medicineTypeList = new ArrayList<String>();
		medicineTypeList = medicineTypeDAO.getMedicineTypeList();

		add.addObject("medicineTypeList", medicineTypeList);

		return add;
	}

	@RequestMapping(value = "/records_date", method = RequestMethod.GET)
	public @ResponseBody 
	ModelAndView recordsDate() {
		logger.info("Medicine Alarm System: Records Date");
		
		ModelAndView recordsDate = new ModelAndView("records_date");
		
		context = new ClassPathXmlApplicationContext("Module.xml");
		RecordsDAO recordsDAO = (RecordsDAO) context.getBean("recordsDAO");
		
		List<Records> recordList = new ArrayList<Records>();
		recordList = recordsDAO.findRecordsByPatientId(Integer
				.parseInt(mPatientId));
		
		int i,j=0;
		List<String> dateList = new ArrayList<String>();
		for(i = 0; i < recordList.size(); i = j) {
			String date = recordList.get(i).getRecord_date();
			dateList.add(date);
			for(j = i+1; j < recordList.size(); j++) {
				if(!date.equals(recordList.get(j).getRecord_date())) {
					break;
				}
			}
		}
		
		recordsDate.addObject("dateList", dateList);
		
		return recordsDate;
	}
	
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView records(@RequestParam("page_number") String pageNumber, 
			@RequestParam("block_number") String blockNumber,
			@RequestParam("date") String record_date) {
		logger.info("Medicine Alarm System: Records");

		context = new ClassPathXmlApplicationContext("Module.xml");
		RecordsDAO recordsDAO = (RecordsDAO) context.getBean("recordsDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context
				.getBean("medicineTypeDAO");
		
		int rowsPerPage = 10; 
		int pagesPerBlock = 10;
		int numOfRecords = recordsDAO.getTotalNumberOfRecordOfPatientId(Integer
				.parseInt(mPatientId), record_date);

		double dNumOfPages = ((double)numOfRecords/(double)rowsPerPage); 
		int numOfPages = (dNumOfPages > ((int)dNumOfPages)) ? ((int)dNumOfPages)+1 : (int)dNumOfPages;
		double dNumOfBlocks = ((double)numOfPages/(double)pagesPerBlock);
		int numOfBlocks = (dNumOfBlocks > ((int)dNumOfBlocks)) ? ((int)dNumOfBlocks)+1 : (int)dNumOfBlocks;
		
		List<Records> records = new ArrayList<Records>();
		records = recordsDAO.findRecordsByPatientIdWithPage(Integer
				.parseInt(mPatientId), Integer.parseInt(pageNumber), record_date);

		List<String> typeList = new ArrayList<String>();
		List<String> timeList = new ArrayList<String>();
		List<String> recordList = new ArrayList<String>();
		List<String> dateList = new ArrayList<String>();

		for (int i = 0; i < records.size(); i++) {
			MedicineType medicineType = new MedicineType();
			medicineType = medicineTypeDAO.findMedicineTypeById(records.get(i)
					.getType_id());
			typeList.add(medicineType.getType());
			timeList.add(changeTimeFormat(records.get(i).getTime_to_take().toString()));
			recordList.add(records.get(i).getRecords());
			dateList.add(records.get(i).getRecord_date());
		}

		ModelAndView record = new ModelAndView("records");

		record.addObject("numOfPages", numOfPages);
		record.addObject("numOfBlocks", numOfBlocks);
		record.addObject("currentBlock", blockNumber);
		record.addObject("typeList", typeList);
		record.addObject("timeList", timeList);
		record.addObject("recordList", recordList);
		record.addObject("dateList", dateList);
		record.addObject("date", record_date);

		return record;
	}

	private String changeTimeFormat(String time) {
		DateFormat oldFormat = new SimpleDateFormat("HH:mm:ss");
		DateFormat newFormat = new SimpleDateFormat("hh:mm a");

		try {
			Date date = oldFormat.parse(time);
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			time = newFormat.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return time;
	}
}
