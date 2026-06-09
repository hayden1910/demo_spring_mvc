package com.example.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvc.dataitem.InvoiceItem;
import com.example.mvc.service.InvoiceService;
import com.example.mvc.util.PageUtil;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

	@Autowired
	private InvoiceService service;

	@GetMapping
	public Page<InvoiceItem> selectInvoiceList(
			@RequestParam(defaultValue = PageUtil.DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = PageUtil.DEFAULT_SIZE) int size) throws Exception {
		return service.selectInvoiceList(PageUtil.buildPageable(page, size));
	}

	@GetMapping("/{id}")
	public InvoiceItem selectInvoiceById(@PathVariable Long id) {
		return service.selectInvoiceById(id);
	}

	@PostMapping
	public void insertInvoice(@RequestBody InvoiceItem item) {
		service.insertInvoice(item);
	}

	@PutMapping("/{id}")
	public void updateInvoice(@PathVariable Long id, @RequestBody InvoiceItem item) {
		service.updateInvoice(id, item);
	}

	@DeleteMapping("/{id}")
	public void deleteInvoice(@PathVariable Long id) {
		service.deleteInvoice(id);
	}

}
