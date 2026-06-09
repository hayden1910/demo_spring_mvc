package com.example.mvc.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.mvc.dao.IInvoiceLineDao;
import com.example.mvc.dataitem.InvoiceLineItem;

@Service
public class InvoiceLineService {

	@Autowired
	private IInvoiceLineDao dao;

	public Page<InvoiceLineItem> selectInvoiceLineList(Pageable pageable) {
		long total = dao.countInvoiceLineItems();
		long offset = pageable.getOffset();
		int limit = pageable.getPageSize();
		List<InvoiceLineItem> content = dao.selectInvoiceLineList(offset, limit);
		return new PageImpl<>(content, pageable, total);
	}

	public InvoiceLineItem selectInvoiceLineById(Long id) {
		InvoiceLineItem item = dao.selectInvoiceLineById(id);
		isExist(id, item);
		return item;
	}

	public void insertInvoiceLine(InvoiceLineItem item) {
		dao.insertInvoiceLine(item);
	}

	public void updateInvoiceLine(Long id, InvoiceLineItem item) {
		InvoiceLineItem existing = dao.selectInvoiceLineById(id);
		isExist(id, existing);
		dao.updateInvoiceLine(id, item);
	}

	public void deleteInvoiceLine(Long id) {
		InvoiceLineItem existing = dao.selectInvoiceLineById(id);
		isExist(id, existing);
		dao.deleteInvoiceLine(id);
	}

	private void isExist(Long id, InvoiceLineItem item) {
		if (id == null || item == null || item.getInvoiceLineId() == null) {
			throw new NoSuchElementException("No invoiceLine found with id: " + id);
		}
	}

}
