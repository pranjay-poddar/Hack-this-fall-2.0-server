package com.swasthify.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swasthify.DTOs.HospitalsDto;
import com.swasthify.Entities.Hospitals;
import com.swasthify.Service.HospitalsService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class HospitalController {

	@Autowired
	private HospitalsService hospService;
	//sign up hospital
	@PostMapping("/register-hospital")
	public Hospitals registerHospital(@RequestBody Hospitals hosp) throws Exception {
		String email = hosp.getEmailId();
		if(email != null && !" ".equals(email)) {
			Hospitals obj = hospService.fetchByEmailId(email);
			if(obj != null) {
				throw new Exception("Hospital already exists with email id"+email);
			}
		}
		String pass = hosp.getPass();
		String conPass = hosp.getConPass();
		if(pass.compareTo(conPass) != 0) {
			throw new Exception("Confirm Password should be same as Password");
		}
		return hospService.saveHospital(hosp);
	}
	//login hospital
	@PostMapping("/login-hospital")
	public Hospitals loginHospital(@RequestBody Hospitals hosp) throws Exception {
		String email = hosp.getEmailId();
		String pass = hosp.getPass();
		if(email != null && !" ".equals(email)) {
			Hospitals obj = hospService.fetchByEmailId(email);
			if(obj == null) {
				throw new Exception("Hospital doesn't exist");
			}
			String p = obj.getPass();
			if(!p.equals(pass)) {
				throw new Exception("Password is incorrect");
			}
		}
		return hospService.fetchByEmailId(email);
	}
	//add hospital services upon login
	@PutMapping("/add-services/{id}")
	public ResponseEntity<Hospitals> addServices(@PathVariable Long id, @RequestBody Hospitals hosp) {
		return hospService.addServices(id, hosp);
	}
	//get details of hospital by id
	@GetMapping("/details/{id}")
	public ResponseEntity<Hospitals> getDetailsById(@PathVariable Long id) {
		return hospService.getDetails(id);
	}
	//get hospitals dto
	@GetMapping("/hospital-details/{id}")
	public ResponseEntity<HospitalsDto> getHospitalDetailsById(@PathVariable Long id){
		return hospService.getDetailsOfHosp(id);
	}
}
