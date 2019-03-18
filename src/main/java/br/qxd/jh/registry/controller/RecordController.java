package br.qxd.jh.registry.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.qxd.jh.registry.dto.HoursRecordDTO;
import br.qxd.jh.registry.entity.HoursRecord;
import br.qxd.jh.registry.entity.User;
import br.qxd.jh.registry.service.HoursRecordService;

@RequestMapping("/record")
@RestController
public class RecordController {
	
	@Autowired
	private HoursRecordService hoursService;
	
	@GetMapping("/{id}")
	public List<HoursRecord> getRecords(@PathVariable Long id) {
		User user = new User();
		user.setId(id);
		return hoursService.getRecordsFromUser(user);
	}
	
	@PostMapping("/save")
	public void insertRecord(@RequestBody HoursRecordDTO record) {
		hoursService.insertRecordInUser(record.getUser(), record.getWorkedHours(), record.getDate());
	}

}
