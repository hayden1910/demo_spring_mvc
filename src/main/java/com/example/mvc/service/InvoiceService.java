package com.example.mvc.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.mvc.dao.IInvoiceDao;
import com.example.mvc.dataitem.InvoiceItem;

@Service
public class InvoiceService {

	@Autowired
	private IInvoiceDao invoiceDao;

	private static final String INVOICE_NUMBER_PREFIX = "INV-";

	public String generateInvoiceNumber() {
		long count = invoiceDao.countInvoiceItems();
		return INVOICE_NUMBER_PREFIX + String.format("%06d", count + 1);
	}

	public Page<InvoiceItem> selectInvoiceList(Pageable pageable) {
		long total = invoiceDao.countInvoiceItems();
		long offset = pageable.getOffset();
		int limit = pageable.getPageSize();
		List<InvoiceItem> content = invoiceDao.selectInvoiceList(offset, limit);
		return new PageImpl<>(content, pageable, total);
	}

	public InvoiceItem selectInvoiceById(Long id) {
		InvoiceItem item = invoiceDao.selectInvoiceById(id);
		isExist(id, item);
		return item;
	}

	public InvoiceItem selectInvoiceByIdWithLines(Long id) {
		InvoiceItem item = invoiceDao.selectInvoiceByIdWithLines(id);
		isExist(id, item);
		return item;
	}

	public void insertInvoice(InvoiceItem item) {
		invoiceDao.insertInvoice(item);
	}

	public void updateInvoice(Long id, InvoiceItem item) {
		InvoiceItem existing = invoiceDao.selectInvoiceById(id);
		isExist(id, existing);
		invoiceDao.updateInvoice(id, item);
	}

	public void deleteInvoice(Long id) {
		InvoiceItem existing = invoiceDao.selectInvoiceById(id);
		isExist(id, existing);
		invoiceDao.deleteInvoice(id);
	}

	public BigDecimal calculateLineAmount(BigDecimal price, int qty) {
		return invoiceDao.calculateLineAmount(price, qty);
	}

	private void isExist(Long id, InvoiceItem item) {
		if (id == null || item == null || item.getInvoiceId() == null) {
	        throw new NoSuchElementException("No invoice found with id: " + id);
		}
	}

}
