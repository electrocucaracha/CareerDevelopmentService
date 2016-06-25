package com.electrocucaracha.apps.cdp.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.electrocucaracha.apps.cdp.dao.CategoryDao;
import com.electrocucaracha.apps.cdp.entities.CategoryEntity;
import com.electrocucaracha.apps.cdp.errorhandling.InvalidInputException;
import com.electrocucaracha.apps.cdp.services.CategoryService;
import com.electrocucaracha.apps.cdp.services.CategoryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest{

	CategoryService service;

	private static final long TEST_ID = 1L;
	private static final String TEST_TITLE = "Test title";
	private static final String TEST_DEFINITION = "Test definition";
	private static final double TEST_BUDGET = 50.0;
	private static final double TEST_TIME = 10.0;
	
	@Mock
	CategoryDao categoryDao;

	@Before
	public void setUp() throws Exception {
		service = new CategoryServiceImpl();
		service.setDao(categoryDao);
	}

	@Test
	public void createCategorySucessful() throws InvalidInputException {
		when(categoryDao.getCategoryByTitle(TEST_TITLE)).thenReturn(null); 
		when(categoryDao.create(any(CategoryEntity.class))).thenReturn(TEST_ID);

		CategoryEntity entity = new CategoryEntity();
		entity.setTitle(TEST_TITLE);
		entity.setDefinition(TEST_DEFINITION);
		entity.setBudget(TEST_BUDGET);
		entity.setTime(TEST_TIME);
		
		long category_id = service.create(entity);
		
		verify(categoryDao).getCategoryByTitle(TEST_TITLE);
		verify(categoryDao, times(1)).getCategoryByTitle(TEST_TITLE);
		verify(categoryDao, atLeastOnce()).getCategoryByTitle(TEST_TITLE);
		
		assertTrue(category_id == TEST_ID);
	}
}
