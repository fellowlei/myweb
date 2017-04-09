package com.mark.chp2.service;

import com.alibaba.fastjson.JSON;
import com.mark.chp2.helper.DatabaseHelper;
import com.mark.chp2.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/8.
 */
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public static void main(String[] args) {
        List<Customer> list = new CustomerService().getCustomerList();
        String json = JSON.toJSONString(list);
        System.out.println(json);

    }

    public List<Customer> getCustomerList() {
        String sql = "select * from customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql, null);
    }

    public List<Customer> getCustomerList(String keyword) {
        //TODO
        return null;
    }


    public Customer getCustomer(long id) {
        String sql = "select * from customer where id=" + id;
        return DatabaseHelper.queryEntity(Customer.class, sql, null);
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    public boolean updateCusomter(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
