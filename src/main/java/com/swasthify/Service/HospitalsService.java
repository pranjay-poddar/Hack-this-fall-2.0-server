package com.swasthify.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swasthify.DTOs.HospitalsDto;
import com.swasthify.Entities.Hospitals;
import com.swasthify.Exception.ResourceNotFound;
import com.swasthify.Repository.HospitalRepo;

@Service
public class HospitalsService {
	@Autowired
	private HospitalRepo hospitalRepo;
	//fetch by email id
	public Hospitals fetchByEmailId(String email) {
		return hospitalRepo.findByemailId(email);
	}
	//save hospital data
	public Hospitals saveHospital(Hospitals hospital) {
		return hospitalRepo.save(hospital);
	}
	//add services of hospitals
	public ResponseEntity<Hospitals> addServices(Long id ,Hospitals hosp) {
		Hospitals obj = hospitalRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Hospital with given email not found"+hosp.getEmailId()));
		obj.setIcuBeds(hosp.getIcuBeds());
		obj.setIsolationBeds(hosp.getIsolationBeds());
		obj.setOxygenCylinders(hosp.getOxygenCylinders());
		obj.setVaccine1(hosp.getVaccine1());
		obj.setVaccine2(hosp.getVaccine2());
		obj.setCity(hosp.getCity());
		obj.setState(hosp.getState());
		Hospitals updatedEntry = hospitalRepo.save(obj);
		return ResponseEntity.ok(updatedEntry);
	}
	//get details by id
	public ResponseEntity<Hospitals> getDetails(Long id) {
		Hospitals obj = hospitalRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Hospital not found"));
		return ResponseEntity.ok(obj);
	}
//	//get hospitals
//	public ResponseEntity<Hospitals> getDetailsOfHospitals(Long id){
//		Hospitals obj = hospitalRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Problem while finding dashboard"));
//		return ResponseEntity.ok(obj);
//	}
	public ResponseEntity<HospitalsDto> getDetailsOfHosp(Long id) {
		// TODO Auto-generated method stub
		HospitalsDto obj = hospitalRepo.findByHospitalId(id);
		return ResponseEntity.ok(obj);
	}
}
