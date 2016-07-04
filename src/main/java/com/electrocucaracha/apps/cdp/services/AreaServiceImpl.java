package com.electrocucaracha.apps.cdp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.electrocucaracha.apps.cdp.dao.AreaDao;
import com.electrocucaracha.apps.cdp.entities.AreaEntity;
import com.electrocucaracha.apps.cdp.errorhandling.InvalidInputException;

public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;

	public void setDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}

	@Override
	public List<AreaEntity> retrieve() {
		return areaDao.retrieve();
	}

	private void validateInputForCreation(AreaEntity entity) throws InvalidInputException {
		if (entity.getTitle() == null) {
			throw new InvalidInputException("Provided data not sufficient for insertion");
		}
	}

	@Override
	public long create(AreaEntity entity) throws InvalidInputException {
		validateInputForCreation(entity);

		AreaEntity areaByTitle = areaDao.getAreaByTitle(entity.getTitle());
		if (areaByTitle != null) {
			throw new InvalidInputException(
					"Area with title already existing in the database with the id " + areaByTitle.getId());
		}

		return areaDao.create(entity);
	}

	@Override
	public void update(AreaEntity entity) throws AreaNotFoundException {
		if (areaDao.get(entity.getId()) == null) {
			throw new AreaNotFoundException(
					"Area with the id " + entity.getId() + " doesn't exist in the database");
		}
		areaDao.update(entity);
	}

	@Override
	public void delete(AreaEntity entity) throws AreaNotFoundException {
		if (areaDao.get(entity.getId()) == null) {
			throw new AreaNotFoundException(
					"Area with the id " + entity.getId() + " doesn't exist in the database");
		}
		areaDao.delete(entity);
	}

	@Override
	public AreaEntity get(long id) throws InvalidInputException{
		if(id < 0){
			throw new InvalidInputException("There is no area with negative identifier");
		}
		
		return areaDao.get(id);
	}

}
