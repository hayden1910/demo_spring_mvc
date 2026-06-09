package com.example.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvc.dataitem.InvoiceLineItem;
import com.example.mvc.service.InvoiceLineService;
import com.example.mvc.util.PageUtil;

@RestController
@RequestMapping("/api/invoice-lines")
public class InvoiceLineController {

	@Autowired
	private InvoiceLineService service;

	@GetMapping
	public Page<InvoiceLineItem> selectInvoiceLineList(
			@RequestParam(defaultValue = PageUtil.DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = PageUtil.DEFAULT_SIZE) int size) throws Exception {
		return service.selectInvoiceLineList(PageUtil.buildPageable(page, size));
	}

	@GetMapping("/{id}")
	public InvoiceLineItem selectInvoiceLineById(@PathVariable Long id) throws Exception {
		return service.selectInvoiceLineById(id);
	}

	@PostMapping
	public void insertInvoiceLine(@RequestBody InvoiceLineItem item) throws Exception {
		service.insertInvoiceLine(item);
	}

	@PutMapping("/{id}")
	public void updateInvoiceLine(@PathVariable Long id, @RequestBody InvoiceLineItem item) throws Exception {
		service.updateInvoiceLine(id, item);
	}

	@DeleteMapping("/{id}")
	public void deleteInvoiceLine(@PathVariable Long id) {
		service.deleteInvoiceLine(id);
	}

}
