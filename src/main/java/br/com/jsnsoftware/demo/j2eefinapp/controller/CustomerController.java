package br.com.jsnsoftware.demo.j2eefinapp.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.jsnsoftware.demo.j2eefinapp.entity.Customer;
import br.com.jsnsoftware.demo.j2eefinapp.service.CustomerService;


@Stateless
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @EJB
    private CustomerService customerService;


    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "REST OK";
    }

    @GET
    @Path("/findAll")
    public Response findAll() {

        List<Customer> list = customerService.findAll();

        return Response.ok(list).build();
    }
    

    @GET
    @Path("/find/{id}")
    public Response findId(@PathParam("id") Long id) {

        Customer customer = customerService.findById(id);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(customer).build();
    }

    @POST
    @Path("/create")
    public Response createCustomer(Customer customer) {

        if (customer.getEmail() == null || customer.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Customer newCustomer = customerService.create(customer);

        return Response
                .status(Response.Status.CREATED)
                .entity(newCustomer)
                .build();
    }

    @PUT
    @Path("/update/{id}")
    public Response update(@PathParam("id") Long id, Customer customer) {

      
        if (id == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Customer updateCustomer = customerService.update(id, customer);

        return Response.ok(updateCustomer).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {

        boolean deleted = customerService.delete(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }
}