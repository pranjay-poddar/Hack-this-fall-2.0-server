package com.swasthify.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swasthify.DTOs.CumulativeServices;
import com.swasthify.DTOs.HospitalsDto;
import com.swasthify.Entities.Patients;
import com.swasthify.Exception.ResourceNotFound;
import com.swasthify.Repository.HospitalRepo;
import com.swasthify.Repository.PatientRepo;

@Service
public class PatientService {
	@Autowired
	private PatientRepo patientRep;
	@Autowired
	private HospitalRepo hosRepo;
	//register user
	public Patients registerUser(Patients patient) {
		return patientRep.save(patient);
	}
	public Patients fetchByEmailId(String emailId) {
		return patientRep.findByEmailId(emailId);
	}
	public CumulativeServices totServices() {
		List<CumulativeServices> allSer = new ArrayList<>();
		allSer = hosRepo.getAllServices();
		CumulativeServices obj = new CumulativeServices();
		for(CumulativeServices o : allSer) {
			int icuBeds = o.getIcuBeds();
			int isolationBeds = o.getIsolationBeds();
			int oxygenCyl = o.getOxygenCylinders();
			int vac1 = o.getVaccine1();
			int vac2 = o.getVaccine2();
			obj.setIcuBeds(obj.getIcuBeds()+icuBeds);
			obj.setIsolationBeds(obj.getIsolationBeds() + isolationBeds);
			obj.setOxygenCylinders(obj.getOxygenCylinders() + oxygenCyl);
			obj.setVaccine1(obj.getVaccine1() + vac1);
			obj.setVaccine2(obj.getVaccine2() + vac2);
		}
		return obj;
	}
	public List<HospitalsDto> getAllHospitals(String city){
		return hosRepo.fetchAllHospitals(city);
	}
	public CumulativeServices totServicesByCity(String city) {
		List<CumulativeServices> allSer = new ArrayList<>();
		allSer = hosRepo.getAllServicesByCity(city);
		CumulativeServices obj = new CumulativeServices();
		for(CumulativeServices o : allSer) {
			int icuBeds = o.getIcuBeds();
			int isolationBeds = o.getIsolationBeds();
			int oxygenCyl = o.getOxygenCylinders();
			int vac1 = o.getVaccine1();
			int vac2 = o.getVaccine2();
			obj.setIcuBeds(obj.getIcuBeds()+icuBeds);
			obj.setIsolationBeds(obj.getIsolationBeds() + isolationBeds);
			obj.setOxygenCylinders(obj.getOxygenCylinders() + oxygenCyl);
			obj.setVaccine1(obj.getVaccine1() + vac1);
			obj.setVaccine2(obj.getVaccine2() + vac2);
		}
		return obj;
	}
	public List<HospitalsDto> getHospitals(String city) {
		return hosRepo.getAllHospitalsByService(city);
	}
	public ResponseEntity<Patients> getDetailsOfPatient(Long id) {
		// TODO Auto-generated method stub
		Patients obj = patientRep.findById(id).orElseThrow(() -> new ResourceNotFound("Patient Not found"));
		return ResponseEntity.ok(obj);
	}
	
}
