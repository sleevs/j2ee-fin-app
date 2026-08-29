package br.com.jsnsoftware.demo.j2eefinapp.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.jsnsoftware.demo.j2eefinapp.entity.Customer;

@Stateless
public class CustomerRepository{

    @PersistenceContext(unitName = "FinAppPersistenceUnit")
    private EntityManager em;

     public List<Customer> findAll() {
        return em.createQuery(
                "SELECT c FROM Customer c",
                Customer.class
        ).getResultList();
    }

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public Customer create(Customer customer) {
        em.persist(customer);
        return customer;
    }

    public Customer update(Long id, Customer data) {

        Customer customer = em.find(Customer.class, id);

        if (customer == null) {
            return null;
        }

        customer.setName(data.getName());
        customer.setEmail(data.getEmail());
        customer.setPhone(data.getPhone());

        return customer;
    }

    public boolean delete(Long id) {

        Customer customer = em.find(Customer.class, id);

        if (customer == null) {
            return false;
        }

        em.remove(customer);

        return true;
    }



}