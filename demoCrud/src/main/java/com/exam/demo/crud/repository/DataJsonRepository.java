package com.exam.demo.crud.repository;

import com.exam.demo.crud.model.DataModel;
import com.exam.demo.crud.model.ResponseModel;

public interface DataJsonRepository {
    public DataModel getUser(Long id);
    public ResponseModel saveUser(DataModel user);
    public ResponseModel deleteUser(Long user);
    public ResponseModel editUser(DataModel user);
}
