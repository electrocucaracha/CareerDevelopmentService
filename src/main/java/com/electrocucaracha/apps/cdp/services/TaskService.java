package com.electrocucaracha.apps.cdp.services;

import com.electrocucaracha.apps.cdp.dao.TaskDao;
import com.electrocucaracha.apps.cdp.entities.TaskEntity;

public interface TaskService extends BaseService<TaskEntity, TaskDao> {

	final static String DAO_IDENTIFIER = "taskService";

}
