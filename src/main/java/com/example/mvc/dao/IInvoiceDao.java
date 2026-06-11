package com.example.mvc.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mvc.dataitem.InvoiceItem;

@Mapper
public interface IInvoiceDao {

	long countInvoiceItems();

	List<InvoiceItem> selectInvoiceList(@Param("offset") long offset, @Param("limit") int limit);

	InvoiceItem selectInvoiceById(@Param("invoiceId") Long id);

	InvoiceItem selectInvoiceByIdWithLines(@Param("invoiceId") Long id);

	void insertInvoice(InvoiceItem invoice);

	void updateInvoice(@Param("invoiceId") Long id, @Param("invoice") InvoiceItem invoice);

	void deleteInvoice(@Param("invoiceId") Long id);

	BigDecimal calculateLineAmount(@Param("price") BigDecimal price, @Param("qty") int qty);

}
