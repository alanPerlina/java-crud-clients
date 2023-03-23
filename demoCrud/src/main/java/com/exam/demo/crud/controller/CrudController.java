package com.exam.demo.crud.controller;

import com.exam.demo.crud.client.ServiceClient;
import com.exam.demo.crud.model.DataModel;
import com.exam.demo.crud.model.ResponseModel;
import com.exam.demo.crud.service.CrudService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("exam/crud")
public class CrudController {

    @Autowired
    ServiceClient serviceClient;
    @Autowired
    CrudService crudService;
    @GetMapping("/users")
    @CircuitBreaker(name = "circuitBreakerService", fallbackMethod = "fallBackToServiceClient")
    public Object getUserService() {
        return serviceClient.callApi();
    }
    @GetMapping("/user/{id}")
    public Object getUserService(@PathVariable Long id) {
        return crudService.getUser(id);
    }
    @PostMapping("/user")
    public Object getUserService(@RequestBody DataModel user) {
        return crudService.saveUser(user);
    }
    @PutMapping("/user")
    public Object updateUserService(@RequestBody DataModel user) {
        return crudService.updateUser(user);
    }
    @DeleteMapping("/user/{id}")
    public Object deleteUserService(@PathVariable Long id) {
        return crudService.deleteUser(id);
    }

    public Object fallBackToServiceClient(Exception e) {
        ResponseModel response = new ResponseModel();
        response.setCode(100L);
        response.setDescription("No se pudo recuperar datos, favor de validar cuando la conexión se restablezca");
        return response;
    }
}
