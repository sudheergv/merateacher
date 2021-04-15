package com.merateacher.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merateacher.exception.ResourceNotFoundException;
import com.merateacher.model.Trainer;
import com.merateacher.repository.TrainerRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class TrainerController {
	@Autowired
	private TrainerRepository trainerRepository;

	@GetMapping("/trainers")
	public List<Trainer> getAllEmployees() {
		return trainerRepository.findAll();
	}

	@GetMapping("/trainers/{id}")
	public ResponseEntity<Trainer> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Trainer employee = trainerRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("/trainers")
	public Trainer createEmployee(@Valid @RequestBody Trainer employee) {
		return trainerRepository.save(employee);
	}

	@PutMapping("/trainers/{id}")
	public ResponseEntity<Trainer> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Trainer employeeDetails) throws ResourceNotFoundException {
		Trainer employee = trainerRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Trainer updatedEmployee = trainerRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/trainers/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Trainer employee = trainerRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		trainerRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
