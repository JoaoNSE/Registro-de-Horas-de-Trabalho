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
	
	/**
	 * Recebe um ID de usuário como parâmetro e retorna todos os registros de
	 * horas desse usuário.
	 * 
	 * @param user
	 * @return List<HoursRecord>
	 */
	public List<HoursRecord> getRecordsFromUser(Long userId) {
		return (List<HoursRecord>) hoursRepo.findRecordsByUserId(userId);
	}
	
	/**
	 * Adiciona um novo registro de horas no usuário informado.
	 * 
	 * @param user
	 * @param hours
	 * @param date
	 */
	public void insertRecordInUser(Long userId, double hours, Date date) {
		User user = new User();
		user.setId(userId);
		HoursRecord record = new HoursRecord(hours, date, user);
		hoursRepo.save(record);
	}

}
