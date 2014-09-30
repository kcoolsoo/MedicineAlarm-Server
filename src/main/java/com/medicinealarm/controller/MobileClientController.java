package com.medicinealarm.controller;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medicinealarm.dao.MedicineDAO;
import com.medicinealarm.dao.MedicineTypeDAO;
import com.medicinealarm.dao.PatientDAO;
import com.medicinealarm.dao.RecordsDAO;
import com.medicinealarm.model.Medicine;
import com.medicinealarm.model.MedicineType;
import com.medicinealarm.model.MedicineTypeList;
import com.medicinealarm.model.Message;
import com.medicinealarm.model.Patient;
import com.medicinealarm.model.PatientInfo;
import com.medicinealarm.model.Records;

@Controller
@RequestMapping("/*")
public class MobileClientController {

	private static final Logger logger = LoggerFactory.getLogger(MobileClientController.class);

	private static ApplicationContext context;
	
	@RequestMapping(value = "/mobile_login", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Message getMessage() {
		logger.info("Accessing protected resource");
		return new Message(100, "Conrats!", "Accessed!");
	}
		
	@RequestMapping(value = "/mobile_main", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody PatientInfo getPatientInfo(ModelMap model) {
		logger.info("Getting Patient Infromation");
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getUsername();
		
		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");
		MedicineDAO medicineDAO = (MedicineDAO) context.getBean("medicineDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context.getBean("medicineTypeDAO");
		
		Patient patient = patientDAO.findPatientById(Integer.parseInt(id));
		List<Medicine> medicineList = medicineDAO.findMedicineListByPatientId(patient.getId());
		
		ArrayList<Integer> typeIdList = new ArrayList<Integer>();
		ArrayList<Time> timeList = new ArrayList<Time>();
		for(int i = 0; i < medicineList.size(); i++) {
			typeIdList.add(medicineList.get(i).getType_id());
			timeList.add(medicineList.get(i).getTime_to_take());
		}
		
		ArrayList<String> typeList = new ArrayList<String>();
		for(int i = 0; i < typeIdList.size(); i++) {
			typeList.add(medicineTypeDAO.findMedicineTypeById(typeIdList.get(i)).getType());
		}
				
		PatientInfo patientInfo = new PatientInfo();
		patientInfo.setId(patient.getId());
		patientInfo.setName(patient.getName());
		patientInfo.setPoints(patient.getPoints());
		patientInfo.setTypeList(typeList);
		patientInfo.setTimeList(timeList);
		
		return patientInfo;
	}
	
	@RequestMapping(value = "/records", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody void Points(@RequestParam("points") String points, 
									@RequestParam("record") String record, 
									@RequestParam("typeId") String typeId,
									@RequestParam("time") String time) {
		logger.info("Get Reward Points and Record.");

		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getUsername();
		
		context = new ClassPathXmlApplicationContext("Module.xml");
		PatientDAO patientDAO = (PatientDAO) context.getBean("patientDAO");
		RecordsDAO recordsDAO = (RecordsDAO) context.getBean("recordsDAO");
		MedicineDAO medicineDAO = (MedicineDAO) context.getBean("medicineDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context.getBean("medicineTypeDAO");
		
		List<Integer> listType = new ArrayList<Integer>();
		listType = medicineDAO.findMedicineListByTime(Integer.parseInt(id), time);
		
		MedicineTypeList medicineTypeList = new MedicineTypeList();
		List<MedicineType> listMedicineType = new ArrayList<MedicineType>();
		for(int i = 0; i < listType.size(); i++){
			MedicineType medicineType = new MedicineType();
			medicineType = medicineTypeDAO.findMedicineTypeById(listType.get(i));
			listMedicineType.add(medicineType);		
		}
		medicineTypeList.setMedicineList(listMedicineType);		
		
		List<String> typeIdList = new ArrayList<String>();
		for(int i = 0; i < medicineTypeList.getMedicineList().size(); i++) {
			MedicineType medicineType = new MedicineType();
			medicineType = medicineTypeList.getMedicineList().get(i);
			typeIdList.add(Integer.toString(medicineType.getId()));
		}
		
		patientDAO.updatePoints(Integer.parseInt(id), Integer.parseInt(points));
		
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Time time_to_take = null;
		try {
			time_to_take = new Time(timeFormat.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < typeIdList.size(); i++) {
			Records records = new Records(0, Integer.parseInt(id), Integer.parseInt(typeIdList.get(i)), time_to_take, record, null);
			recordsDAO.insert(records);
		}
		
	}
	
	@RequestMapping(value = "/medicinetype", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MedicineTypeList getMedicineType(@RequestParam("time") String time) {
		logger.info("Get Medicine Types");

		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getUsername();
		
		context = new ClassPathXmlApplicationContext("Module.xml");
		MedicineDAO medicineDAO = (MedicineDAO) context.getBean("medicineDAO");
		MedicineTypeDAO medicineTypeDAO = (MedicineTypeDAO) context.getBean("medicineTypeDAO");
		
		List<Integer> listType = new ArrayList<Integer>();
		listType = medicineDAO.findMedicineListByTime(Integer.parseInt(id), time);
		
		MedicineTypeList medicineTypeList = new MedicineTypeList();
		List<MedicineType> listMedicineType = new ArrayList<MedicineType>();
		for(int i = 0; i < listType.size(); i++){
			MedicineType medicineType = new MedicineType();
			medicineType = medicineTypeDAO.findMedicineTypeById(listType.get(i));
			listMedicineType.add(medicineType);		
		}
		medicineTypeList.setMedicineList(listMedicineType);
			
		return medicineTypeList;
	}
	
}
