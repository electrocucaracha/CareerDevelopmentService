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

import com.electrocucaracha.apps.cdp.dao.CategoryDao;
import com.electrocucaracha.apps.cdp.entities.CategoryEntity;
import com.electrocucaracha.apps.cdp.errorhandling.CategoryNotFoundException;
import com.electrocucaracha.apps.cdp.errorhandling.InvalidInputException;
import com.electrocucaracha.apps.cdp.services.CategoryService;
import com.electrocucaracha.apps.cdp.services.CategoryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

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
	public void createCategorySucessful() throws Exception {
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

	@Test(expected = InvalidInputException.class)
	public void createCategoryWithExistingTitle() throws Exception {
		CategoryEntity entity = new CategoryEntity();
		entity.setTitle(TEST_TITLE);

		when(categoryDao.getCategoryByTitle(TEST_TITLE)).thenReturn(entity);

		service.create(entity);
	}

	@Test(expected = InvalidInputException.class)
	public void createCategoryWithoutTitle() throws Exception {
		CategoryEntity entity = new CategoryEntity();

		service.create(entity);
	}

	@Test
	public void retrieveEmptyList() {
		when(categoryDao.retrieve()).thenReturn(new ArrayList<CategoryEntity>());

		List<CategoryEntity> result = service.retrieve();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test(expected = CategoryNotFoundException.class)
	public void deleteNonExistingCategory() throws Exception {
		when(categoryDao.get(TEST_ID)).thenReturn(null);

		CategoryEntity entity = new CategoryEntity();
		entity.setId(TEST_ID);

		service.delete(entity);
	}

	@Test
	public void deleteCategorySucessful() throws Exception {
		when(categoryDao.get(TEST_ID)).thenReturn(new CategoryEntity());

		CategoryEntity entity = new CategoryEntity();
		entity.setId(TEST_ID);

		service.delete(entity);
	}

	@Test(expected = CategoryNotFoundException.class)
	public void updateNonExistingCategory() throws Exception {
		when(categoryDao.get(TEST_ID)).thenReturn(null);

		CategoryEntity entity = new CategoryEntity();
		entity.setId(TEST_ID);

		service.update(entity);
	}

	@Test
	public void updateCategorySucessful() throws Exception {
		when(categoryDao.get(TEST_ID)).thenReturn(new CategoryEntity());

		CategoryEntity entity = new CategoryEntity();
		entity.setId(TEST_ID);

		service.update(entity);
	}

	@Test(expected = InvalidInputException.class)
	public void getCategoryWithNegativeId() throws Exception {
		service.get(-1L);
	}

	@Test
	public void getCategorySucessful() throws Exception {
		when(categoryDao.get(TEST_ID)).thenReturn(new CategoryEntity());

		assertNotNull(service.get(TEST_ID));
	}
}
