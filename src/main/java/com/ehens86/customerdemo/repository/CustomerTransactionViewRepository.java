package com.ehens86.customerdemo.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.repository.CrudRepository;

import com.couchbase.client.java.view.ViewQuery;
import com.ehens86.customerdemo.dao.CustomerTransactionView;
import com.ehens86.customerdemo.dao.Transaction;

public interface CustomerTransactionViewRepository extends CrudRepository<Transaction, String> {
	
	@View(viewName="customer_totals", reduce = true)
	List<CustomerTransactionView> findAllStats();
}
