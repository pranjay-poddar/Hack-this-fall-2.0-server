package com.swasthify.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swasthify.DTOs.CumulativeServices;
import com.swasthify.DTOs.HospitalsDto;
import com.swasthify.Entities.Patients;
import com.swasthify.Service.PatientService;

@RestController
@RequestMapping("/api/v2")
@CrossOrigin
public class PatientController {
	@Autowired
	private PatientService patientSer;
	//register patient
	@PostMapping("/register-patient")
	public Patients regPatient(@RequestBody Patients patient) throws Exception {
		String emailId = patient.getEmailId();
		if(emailId != null && !" ".equals(emailId)) {
			Patients obj = patientSer.fetchByEmailId(emailId);
			if(obj != null) {
				throw new Exception("Patient with email id already exists");
			}
		}
		String pass = patient.getPass();
		String conPass = patient.getConPass();
		if(pass.compareTo(conPass) != 0) {
			throw new Exception("Password and confirm Password are not same");
		}
		return patientSer.registerUser(patient);
	}
	//sign in patient
	@PostMapping("/login-patient")
	public Patients loginPatient(@RequestBody Patients patient) throws Exception {
		String emailId = patient.getEmailId();
		String pass = patient.getPass();
		if(emailId != null && !" ".equals(emailId) && pass != null) {
			Patients obj = patientSer.fetchByEmailId(emailId);
			if(obj == null) {
				throw new Exception("User with email id doesn't exist");
			}
			String p = obj.getPass();
			if(p.compareTo(pass) != 0) {
				throw new Exception("Password Incorrect");
			}
		}
		return patientSer.fetchByEmailId(emailId);
	}
	@GetMapping("/all-services")
	public CumulativeServices totServices() {
		return patientSer.totServices();
	}
	@GetMapping("/all-services/{city}")
	public CumulativeServices totServicesByCity(@PathVariable String city) {
		return patientSer.totServicesByCity(city);
	}
	@GetMapping("/details/{city}")
	public List<HospitalsDto> getAllHospitalsByCity(@PathVariable String city){
		return patientSer.getAllHospitals(city);
	}
	@GetMapping("all-hospitals/{city}")
	public List<HospitalsDto> getALLHospitals(@PathVariable String city){
		return patientSer.getHospitals(city);
	}
	//get details of patient
	@GetMapping("patient-details/{id}")
	public ResponseEntity<Patients> getDetailsOfPatient(@PathVariable Long id) {
		return patientSer.getDetailsOfPatient(id);
	}

}
