package com.example.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mvc.dataitem.GenreItem;

@Mapper
public interface IGenreDao {
	List<GenreItem> getAllGenre();

	GenreItem getGenreById(@Param("id") Long id);

	void createGenre(GenreItem genre);

	void updateGenre(@Param("id") Long id, @Param("genre") GenreItem genre);

	void deleteGenre(@Param("id") Long id);

	List<GenreItem> searchGenre(@Param("keyword") String keyword);
}
