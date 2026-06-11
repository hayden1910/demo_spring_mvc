package com.example.mvc.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mvc.dataitem.InvoiceLineItem;

@Mapper
public interface IInvoiceLineDao {
	
	long countInvoiceLineItems();
	
	List<InvoiceLineItem> selectInvoiceLineList(@Param("offset") long offset, @Param("limit") int limit);

	InvoiceLineItem selectInvoiceLineById(@Param("invoiceLineId") Long id);

	List<InvoiceLineItem> selectInvoiceLinesByInvoiceId(@Param("invoiceId") Long invoiceId);

	void insertInvoiceLine(InvoiceLineItem invoice);

	void insertInvoiceLineWithProc(InvoiceLineItem invoice);

	void updateInvoiceLine(@Param("invoiceLineId") Long id, @Param("invoiceLine") InvoiceLineItem item);

	void deleteInvoiceLine(@Param("invoiceLineId") Long id);

	BigDecimal calculateLineAmount(@Param("price") BigDecimal price, @Param("qty") int qty);

}
