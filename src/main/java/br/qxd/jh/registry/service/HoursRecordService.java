package br.qxd.jh.registry.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.qxd.jh.registry.model.HoursRecord;
import br.qxd.jh.registry.model.User;
import br.qxd.jh.registry.repository.HoursRecordRepository;

@Service
public class HoursRecordService {
	
	@Autowired
	private HoursRecordRepository hoursRepo;
	
	public List<HoursRecord> getRecordsFromUser(User user) {
		return (List<HoursRecord>) hoursRepo.findRecordsByUserId(user.getId());
	}
	
	public void insertRecordInUser(User user, double hours, Date date) {
		HoursRecord record = new HoursRecord(hours, date, user);
		hoursRepo.save(record);
	}

}
