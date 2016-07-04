package com.electrocucaracha.apps.cdp.services;

import java.util.List;

public interface BaseService<E, D> {

	void setDao(D dao);

	long create(E entity) throws Exception;

	List<E> retrieve();

	void update(E entity) throws Exception;

	void delete(E entity) throws Exception;

	E get(long id) throws Exception;

}
