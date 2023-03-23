package com.exam.demo.crud.service;

import com.exam.demo.crud.model.DataModel;
import com.exam.demo.crud.model.ResponseModel;
import com.exam.demo.crud.repository.DataJsonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrudService {
    @Autowired
    DataJsonRepository repository;

    public ResponseModel getUser(Long id) {
        ResponseModel response = new ResponseModel();
        DataModel model = repository.getUser(id);
        if (model.getId() == id) {
            response.setCode(200L);
            response.setData(model);
            response.setDescription("Se regresan datos solicitados");
        } else {
            response.setCode(100L);
            response.setData(null);
            response.setDescription("No se encontraron los datos solicitados");
        }
        return response;
    }

    public ResponseModel deleteUser(Long id) {
        return repository.deleteUser(id);
    }

    public ResponseModel saveUser(DataModel user) {
        return repository.saveUser(user);

    }
    public ResponseModel updateUser(DataModel user) {
        return repository.editUser(user);

    }
}
