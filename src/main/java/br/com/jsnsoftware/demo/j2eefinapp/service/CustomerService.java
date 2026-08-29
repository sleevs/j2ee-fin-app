package br.com.jsnsoftware.demo.j2eefinapp.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.jsnsoftware.demo.j2eefinapp.entity.Customer;
import br.com.jsnsoftware.demo.j2eefinapp.exception.CustomerNotFoundException;
import br.com.jsnsoftware.demo.j2eefinapp.exception.CustomerServiceException;
import br.com.jsnsoftware.demo.j2eefinapp.repository.CustomerRepository;

@Stateless
public class CustomerService {
    


    @EJB
    private CustomerRepository customerRepository;


     public List<Customer> findAll() {
       List<Customer> customers = customerRepository.findAll();

        if (customers == null || customers.isEmpty()) {
            throw new CustomerNotFoundException(
                "No customers found"
            );
        }

        return customers;
    }

    public Customer findById(Long id) {
        Customer customer = customerRepository.findById(id);

        if(customer == null){
            throw new CustomerNotFoundException( "Customer not Found " + id);
        }
        return customer;
    }

    public Customer create(Customer customer) {
       
         if (customer == null) {
            throw new CustomerNotFoundException(
                "Customer cannot be null"
            );
        }

        return customerRepository.create(customer);
    }

    public Customer update(Long id, Customer data) {

        Customer customer = customerRepository.findById(id);

        if (customer == null) {
            throw new CustomerNotFoundException(
                "Customer not found: " + id
            );
        }

        return customerRepository.update(id, data);
    }

    public boolean delete(Long id) {

       Customer customer = customerRepository.findById(id);

        if (customer == null) {
            throw new CustomerNotFoundException(
                "Customer not found: " + id
            );
        }

        return customerRepository.delete(id);
    }
}
