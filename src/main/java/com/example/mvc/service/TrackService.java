package com.example.mvc.service;

import com.example.mvc.dataitem.TrackItem;

import com.example.mvc.dao.ITrackDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TrackService {

	@Autowired
	private ITrackDao trackDao;

	public Page<TrackItem> selectTrackList(Pageable pageable) {
		long total = trackDao.countTrackItems();
		long offset = pageable.getOffset();
		int limit = pageable.getPageSize();
		List<TrackItem> content = trackDao.selectTrackList(offset, limit);
		return new PageImpl<>(content, pageable, total);
	}

	public TrackItem selectTrackById(Long id) {
		TrackItem track = trackDao.selectTrackById(id);
		if (track == null) {
			throw new NoSuchElementException("Track not found with id: " + id);
		}
		return track;
	}

	public void insertTrack(TrackItem track) {
		trackDao.insertTrack(track);
	}

	public void updateTrack(Long id, TrackItem track) {
		if (trackDao.selectTrackById(id) == null) {
			throw new NoSuchElementException("Track not found with id: " + id);
		}
		trackDao.updateTrack(id, track);
	}

	public void deleteTrack(Long id) {
		trackDao.deleteTrack(id);
	}

}
