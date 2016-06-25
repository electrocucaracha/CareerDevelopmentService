package com.electrocucaracha.apps.cdp.services;

import com.electrocucaracha.apps.cdp.dao.CategoryDao;
import com.electrocucaracha.apps.cdp.entities.CategoryEntity;

public interface CategoryService extends BaseService<CategoryEntity, CategoryDao> {

	final static String DAO_IDENTIFIER = "categoryService";

}
