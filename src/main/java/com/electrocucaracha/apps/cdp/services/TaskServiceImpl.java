package com.electrocucaracha.apps.cdp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.electrocucaracha.apps.cdp.dao.TaskDao;
import com.electrocucaracha.apps.cdp.entities.TaskEntity;
import com.electrocucaracha.apps.cdp.errorhandling.InvalidInputException;
import com.electrocucaracha.apps.cdp.errorhandling.TaskNotFoundException;

public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;

	public void setDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	private void validateInputForCreation(TaskEntity entity) throws InvalidInputException {
		if (entity.getTitle() == null) {
			throw new InvalidInputException("Provided data not sufficient for insertion");
		}
	}

	@Override
	public long create(TaskEntity entity) throws InvalidInputException {
		validateInputForCreation(entity);

		TaskEntity taskByTitle = taskDao.getTaskByTitle(entity.getTitle());
		if (taskByTitle != null) {
			throw new InvalidInputException(
					"Task with title already existing in the database with the id " + taskByTitle.getId());
		}

		return taskDao.create(entity);
	}

	@Override
	public List<TaskEntity> retrieve() {
		return taskDao.retrieve();
	}

	@Override
	public void update(TaskEntity entity) throws TaskNotFoundException {
		if (taskDao.get(entity.getId()) == null) {
			throw new TaskNotFoundException("Task with the id " + entity.getId() + " doesn't exist in the database");
		}
		taskDao.update(entity);
	}

	@Override
	public void delete(TaskEntity entity) throws TaskNotFoundException {
		if (taskDao.get(entity.getId()) == null) {
			throw new TaskNotFoundException("Task with the id " + entity.getId() + " doesn't exist in the database");
		}
		taskDao.delete(entity);

	}

	@Override
	public TaskEntity get(long id) throws InvalidInputException {
		if (id < 0) {
			throw new InvalidInputException("There is no task with negative identifier");
		}

		return taskDao.get(id);
	}
}
