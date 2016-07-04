package com.electrocucaracha.apps.cdp.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.electrocucaracha.apps.cdp.dao.AreaDao;
import com.electrocucaracha.apps.cdp.entities.AreaEntity;
import com.electrocucaracha.apps.cdp.errorhandling.InvalidInputException;

@RunWith(MockitoJUnitRunner.class)
public class AreaServiceImplTest {

	AreaService service;

	private static final long TEST_ID = 1L;
	private static final String TEST_TITLE = "Test title";

	@Mock
	AreaDao areaDao;

	@Before
	public void setUp() throws Exception {
		service = new AreaServiceImpl();
		service.setDao(areaDao);
	}

	@Test
	public void createAreaSucessful() throws Exception {
		when(areaDao.getAreaByTitle(TEST_TITLE)).thenReturn(null);
		when(areaDao.create(any(AreaEntity.class))).thenReturn(TEST_ID);

		AreaEntity entity = new AreaEntity();
		entity.setTitle(TEST_TITLE);

		long area_id = service.create(entity);

		verify(areaDao).getAreaByTitle(TEST_TITLE);
		verify(areaDao, times(1)).getAreaByTitle(TEST_TITLE);
		verify(areaDao, atLeastOnce()).getAreaByTitle(TEST_TITLE);

		assertTrue(area_id == TEST_ID);
	}

	@Test(expected = InvalidInputException.class)
	public void createAreaWithExistingTitle() throws Exception {
		AreaEntity entity = new AreaEntity();
		entity.setTitle(TEST_TITLE);

		when(areaDao.getAreaByTitle(TEST_TITLE)).thenReturn(entity);

		service.create(entity);
	}

	@Test(expected = InvalidInputException.class)
	public void createAreaWithoutTitle() throws Exception {
		AreaEntity entity = new AreaEntity();

		service.create(entity);
	}

	@Test
	public void retrieveEmptyList() {
		when(areaDao.retrieve()).thenReturn(new ArrayList<AreaEntity>());

		List<AreaEntity> result = service.retrieve();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test(expected = AreaNotFoundException.class)
	public void deleteNonExistingArea() throws Exception {
		when(areaDao.get(TEST_ID)).thenReturn(null);

		AreaEntity entity = new AreaEntity();
		entity.setId(TEST_ID);

		service.delete(entity);
	}

	@Test
	public void deleteAreaSucessful() throws Exception {
		when(areaDao.get(TEST_ID)).thenReturn(new AreaEntity());

		AreaEntity entity = new AreaEntity();
		entity.setId(TEST_ID);

		service.delete(entity);
	}

	@Test(expected = AreaNotFoundException.class)
	public void updateNonExistingArea() throws Exception {
		when(areaDao.get(TEST_ID)).thenReturn(null);

		AreaEntity entity = new AreaEntity();
		entity.setId(TEST_ID);

		service.update(entity);
	}

	@Test
	public void updateAreaSucessful() throws Exception {
		when(areaDao.get(TEST_ID)).thenReturn(new AreaEntity());

		AreaEntity entity = new AreaEntity();
		entity.setId(TEST_ID);

		service.update(entity);
	}

	@Test(expected = InvalidInputException.class)
	public void getAreaWithNegativeId() throws Exception {
		service.get(-1L);
	}

	@Test
	public void getAreaSucessful() throws Exception {
		when(areaDao.get(TEST_ID)).thenReturn(new AreaEntity());

		assertNotNull(service.get(TEST_ID));
	}
}
