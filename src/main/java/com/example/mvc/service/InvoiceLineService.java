package com.example.mvc.service;

import java.math.BigDecimal;
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

	public List<InvoiceLineItem> selectInvoiceLinesByInvoiceId(Long invoiceId) {
		return dao.selectInvoiceLinesByInvoiceId(invoiceId);
	}

	public void insertInvoiceLine(InvoiceLineItem item) {
		dao.insertInvoiceLine(item);
	}

	public void insertInvoiceLineWithProc(InvoiceLineItem item) {
		dao.insertInvoiceLineWithProc(item);
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

	public BigDecimal calculateLineAmount(BigDecimal price, int qty) {
		return dao.calculateLineAmount(price, qty);
	}

	private void isExist(Long id, InvoiceLineItem item) {
		if (id == null || item == null || item.getInvoiceLineId() == null) {
			throw new NoSuchElementException("No invoiceLine found with id: " + id);
		}
	}

}
