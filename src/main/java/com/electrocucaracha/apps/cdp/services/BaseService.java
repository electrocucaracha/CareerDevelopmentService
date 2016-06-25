package com.electrocucaracha.apps.cdp.services;

import java.util.List;

import com.electrocucaracha.apps.cdp.errorhandling.InvalidInputException;

public interface BaseService<E, D> {

	D getDao();

	void setDao(D dao);

	long create(E entity) throws InvalidInputException;

	List<E> retrieve();

	void update(E entity);

	void delete(E entity);

	E get(long id);

}
