package br.qxd.jh.registry.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.qxd.jh.registry.entity.HoursRecord;

public interface HoursRecordRepository extends CrudRepository<HoursRecord, Long>{

	@Query(value="SELECT r.id, r.worked_hours, r.date, r.user_id" + 
			" FROM HOURS_RECORD r, user u" + 
			" WHERE r.user_id = ?1", nativeQuery=true)
	public Collection<HoursRecord> findRecordsByUserId(Long id);
}
