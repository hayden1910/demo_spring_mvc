package com.example.mvc.base;

import org.springframework.data.domain.Page;

public interface IBaseDao<T, ID, RQ> {
	Page<T> selectList(long offset, int limit);

	T selectById(ID id);

	void insert(RQ item);

	void update(RQ item);

	void delete(ID id);
}
