package com.electrocucaracha.apps.cdp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.electrocucaracha.apps.cdp.dao.CategoryDao;
import com.electrocucaracha.apps.cdp.entities.CategoryEntity;
import com.electrocucaracha.apps.cdp.errorhandling.CategoryNotFoundException;
import com.electrocucaracha.apps.cdp.errorhandling.InvalidInputException;

public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	public void setDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public List<CategoryEntity> retrieve() {
		return categoryDao.retrieve();
	}

	private void validateInputForCreation(CategoryEntity entity) throws InvalidInputException {
		if (entity.getTitle() == null) {
			throw new InvalidInputException("Provided data not sufficient for insertion");
		}
	}

	@Override
	public long create(CategoryEntity entity) throws InvalidInputException {
		validateInputForCreation(entity);

		CategoryEntity categoryByTitle = categoryDao.getCategoryByTitle(entity.getTitle());
		if (categoryByTitle != null) {
			throw new InvalidInputException(
					"Category with title already existing in the database with the id " + categoryByTitle.getId());
		}

		return categoryDao.create(entity);
	}

	@Override
	public void update(CategoryEntity entity) throws CategoryNotFoundException {
		if (categoryDao.get(entity.getId()) == null) {
			throw new CategoryNotFoundException(
					"Category with the id " + entity.getId() + " doesn't exist in the database");
		}
		categoryDao.update(entity);
	}

	@Override
	public void delete(CategoryEntity entity) throws CategoryNotFoundException {
		if (categoryDao.get(entity.getId()) == null) {
			throw new CategoryNotFoundException(
					"Category with the id " + entity.getId() + " doesn't exist in the database");
		}
		categoryDao.delete(entity);
	}

	@Override
	public CategoryEntity get(long id) throws InvalidInputException{
		if(id < 0){
			throw new InvalidInputException("There is no category with negative identifier");
		}
		
		return categoryDao.get(id);
	}

}
