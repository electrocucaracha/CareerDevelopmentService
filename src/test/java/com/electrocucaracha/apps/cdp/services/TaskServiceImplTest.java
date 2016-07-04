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

import com.electrocucaracha.apps.cdp.dao.TaskDao;
import com.electrocucaracha.apps.cdp.entities.TaskEntity;
import com.electrocucaracha.apps.cdp.errorhandling.InvalidInputException;
import com.electrocucaracha.apps.cdp.errorhandling.TaskNotFoundException;
import com.electrocucaracha.apps.cdp.services.TaskService;
import com.electrocucaracha.apps.cdp.services.TaskServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

	TaskService service;

	private static final long TEST_ID = 1L;
	private static final String TEST_TITLE = "Test title";
	private static final String TEST_RESOURCE = "Test resource";
	private static final double TEST_PROGRESS = 50.0;
	private static final double TEST_PRICE = 10.0;
	private static final String TEST_COMMENTS = "Test comments";

	@Mock
	TaskDao taskDao;

	@Before
	public void setUp() throws Exception {
		service = new TaskServiceImpl();
		service.setDao(taskDao);
	}

	@Test
	public void createTaskSucessful() throws Exception {
		when(taskDao.getTaskByTitle(TEST_TITLE)).thenReturn(null);
		when(taskDao.create(any(TaskEntity.class))).thenReturn(TEST_ID);

		TaskEntity entity = new TaskEntity();
		entity.setTitle(TEST_TITLE);
		entity.setResource(TEST_RESOURCE);
		entity.setProgress(TEST_PROGRESS);
		entity.setPrice(TEST_PRICE);
		entity.setComments(TEST_COMMENTS);

		long task_id = service.create(entity);

		verify(taskDao).getTaskByTitle(TEST_TITLE);
		verify(taskDao, times(1)).getTaskByTitle(TEST_TITLE);
		verify(taskDao, atLeastOnce()).getTaskByTitle(TEST_TITLE);

		assertTrue(task_id == TEST_ID);
	}

	@Test(expected = InvalidInputException.class)
	public void createTaskWithExistingTitle() throws Exception {
		TaskEntity entity = new TaskEntity();
		entity.setTitle(TEST_TITLE);

		when(taskDao.getTaskByTitle(TEST_TITLE)).thenReturn(entity);

		service.create(entity);
	}

	@Test(expected = InvalidInputException.class)
	public void createTaskWithoutTitle() throws Exception {
		TaskEntity entity = new TaskEntity();

		service.create(entity);
	}

	@Test
	public void retrieveEmptyList() {
		when(taskDao.retrieve()).thenReturn(new ArrayList<TaskEntity>());

		List<TaskEntity> result = service.retrieve();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test(expected = TaskNotFoundException.class)
	public void deleteNonExistingTask() throws Exception {
		when(taskDao.get(TEST_ID)).thenReturn(null);

		TaskEntity entity = new TaskEntity();
		entity.setId(TEST_ID);

		service.delete(entity);
	}

	@Test
	public void deleteTaskSucessful() throws Exception {
		when(taskDao.get(TEST_ID)).thenReturn(new TaskEntity());

		TaskEntity entity = new TaskEntity();
		entity.setId(TEST_ID);

		service.delete(entity);
	}

	@Test(expected = TaskNotFoundException.class)
	public void updateNonExistingTask() throws Exception {
		when(taskDao.get(TEST_ID)).thenReturn(null);

		TaskEntity entity = new TaskEntity();
		entity.setId(TEST_ID);

		service.update(entity);
	}

	@Test
	public void updateTaskSucessful() throws Exception {
		when(taskDao.get(TEST_ID)).thenReturn(new TaskEntity());

		TaskEntity entity = new TaskEntity();
		entity.setId(TEST_ID);

		service.update(entity);
	}

	@Test(expected = InvalidInputException.class)
	public void getTaskWithNegativeId() throws Exception {
		service.get(-1L);
	}

	@Test
	public void getTaskSucessful() throws Exception {
		when(taskDao.get(TEST_ID)).thenReturn(new TaskEntity());

		assertNotNull(service.get(TEST_ID));
	}
}
