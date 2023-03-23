package com.exam.demo.crud.repository;

import com.exam.demo.crud.model.DataModel;
import com.exam.demo.crud.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class DataJson implements DataJsonRepository {
    @Override
    public DataModel getUser(Long id) {
        List<DataModel> listData = getDataJson();
        AtomicReference<DataModel> responseData = new AtomicReference<>(new DataModel());
        listData.forEach(item -> {
            if (item.getId() == id) {
                responseData.set(item);
            }
        });
        return responseData.get();
    }

    @Override
    public ResponseModel saveUser(DataModel user) {
        ResponseModel responseModel = new ResponseModel();
        try {
            saveDataJson(user);
            responseModel.setCode(200L);
            responseModel.setDescription("Se almacenaron correctamente los datos");
            responseModel.setData(user);
        } catch (ParseException e) {
            log.error("Error al guardar los datos");
            responseModel.setCode(100L);
            responseModel.setDescription("No se almacenaron correctamente los datos");
            responseModel.setData(user);
        }
        return responseModel;
    }

    @Override
    public ResponseModel deleteUser(Long id) {
        ResponseModel responseModel = new ResponseModel();
        try {
            List<DataModel> list = getDataJson();
            JSONObject js = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            list = list.stream()
                    .sorted(Comparator.comparingLong(DataModel::getId))
                    .collect(Collectors.toList());
            list.forEach(item -> {
                log.info("Json {}", item.toString());
                try {
                    if (!(item.getId() == id)) {
                        JSONObject json = (JSONObject) new JSONParser().parse(item.toString());
                        jsonArray.add(json);
                    }
                } catch (ParseException e) {
                    log.error("Error al guardar los datos", e);
                }
            });
            js.put("users", jsonArray);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(DataJson.class.getClassLoader().getResource("JSONFile.json").getPath()))) {
                bw.write(js.toJSONString());
                System.out.println("Fichero creado");
            } catch (IOException ex) {
                log.error("Err al almacenar los datos", ex);
            }
            responseModel.setCode(200L);
            responseModel.setDescription("Se almacenaron correctamente los datos");
            responseModel.setData(null);
        } catch (Exception e) {
            log.error("Error al eliminar los datos");
            responseModel.setCode(100L);
            responseModel.setDescription("No se eliminaron los datos");
            responseModel.setData(null);
        }
        return responseModel;
    }

    @Override
    public ResponseModel editUser(DataModel user) {
        ResponseModel responseModel = new ResponseModel();
        try {
            List<DataModel> list = getDataJson();
            JSONObject js = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            list = list.stream()
                    .sorted(Comparator.comparingLong(DataModel::getId))
                    .collect(Collectors.toList());
            list.forEach(item -> {
                log.info("Json {}", item.toString());
                try {
                    if ((item.getId() == user.getId())) {
                        JSONObject json = (JSONObject) new JSONParser().parse(user.toString());
                        jsonArray.add(json);
                    }else{
                        JSONObject json = (JSONObject) new JSONParser().parse(item.toString());
                        jsonArray.add(json);
                    }
                } catch (ParseException e) {
                    log.error("Error al guardar los datos", e);
                }
            });
            js.put("users", jsonArray);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(DataJson.class.getClassLoader().getResource("JSONFile.json").getPath()))) {
                bw.write(js.toJSONString());
                System.out.println("Fichero creado");
            } catch (IOException ex) {
                log.error("Error al almacenar los datos", ex);
            }
            responseModel.setCode(200L);
            responseModel.setDescription("Se almacenaron correctamente los datos");
            responseModel.setData(null);
        } catch (Exception e) {
            log.error("Error al modificar los datos");
            responseModel.setCode(100L);
            responseModel.setDescription("No se modificaron los datos");
            responseModel.setData(null);
        }
        return responseModel;
    }

    private List<DataModel> getDataJson() {
        List<DataModel> listData = new ArrayList<>();
        try {
            log.info("Archivo de data {}", DataJson.class.getClassLoader().getResource("JSONFile.json").getPath());
            Object ob = new JSONParser().parse(new FileReader(DataJson.class.getClassLoader().getResource("JSONFile.json").getPath()));
            JSONObject js = (JSONObject) ob;
            JSONArray jsonArray = (JSONArray) js.get("users");
            jsonArray.forEach(item -> {
                DataModel dataModel = new DataModel();
                dataModel.setId(Long.parseLong(((JSONObject) item).get("id").toString()));
                dataModel.setName(((JSONObject) item).get("name").toString());
                dataModel.setLastName(((JSONObject) item).get("lastName").toString());
                dataModel.setAddress(((JSONObject) item).get("address").toString());
                dataModel.setTelephone(((JSONObject) item).get("telephone").toString());
                listData.add(dataModel);
            });
        } catch (IOException | ParseException e) {
            log.error("Error al consultar los datos", e);
        }

        return listData;
    }

    private void saveDataJson(DataModel model) throws ParseException {
        List<DataModel> list = getDataJson();
        JSONObject js = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        list = list.stream()
                .sorted(Comparator.comparingLong(DataModel::getId))
                .collect(Collectors.toList());
        AtomicReference<Long> idNext = new AtomicReference<>(0L);
        list.forEach(item -> {
            log.info("Json {}", item.toString());
            try {
                JSONObject json = (JSONObject) new JSONParser().parse(item.toString());
                jsonArray.add(json);
            } catch (ParseException e) {
                log.error("Error al guardar los datos", e);
            }
            idNext.set(item.getId() + 1);
        });
        model.setId(idNext.get());
        jsonArray.add(new JSONParser().parse(model.toString()));
        js.put("users", jsonArray);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DataJson.class.getClassLoader().getResource("JSONFile.json").getPath()))) {
            bw.write(js.toJSONString());
            System.out.println("Fichero creado");
        } catch (IOException ex) {
            log.error("Erros al almacenar los datos", ex);
        }
    }
}
