package com.ehens86.customerdemo.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ehens86.customerdemo.dao.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {
    @Query("#{#n1ql.selectEntity} WHERE type = 'customer'")
    List<Customer> allCustomers();
    
    @Query("#{#n1ql.selectEntity} WHERE type = 'customer' "
    		+ "and DATE_DIFF_STR(DATE_ADD_STR(dob, DATE_DIFF_STR(CLOCK_UTC(), dob, 'year'), 'year'), CLOCK_UTC(), 'day') >= 0 "
    		+ "and DATE_DIFF_STR(DATE_ADD_STR(dob, DATE_DIFF_STR(CLOCK_UTC(), dob, 'year'), 'year'), CLOCK_UTC(), 'day') < $dayCutoff")
    List<Customer> customersWithUpcomingBirthdays(@Param("dayCutoff") Integer dayCutoff);
}