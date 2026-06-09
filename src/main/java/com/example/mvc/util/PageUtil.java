package com.example.mvc.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

	public static final String DEFAULT_PAGE = "0";
	public static final String DEFAULT_SIZE = "20";

	public static Pageable buildPageable(int page, int size) {
		return PageRequest.of(page, size);
	}
}
