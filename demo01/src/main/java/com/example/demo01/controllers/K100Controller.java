package com.example.demo01.controllers;

import com.example.demo01.entities.K100;
import com.example.demo01.services.K100service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class K100Controller {
    @Autowired
    private K100service k100service;

    @RequestMapping(value = "syzFodGroupCatCreatUdp/v1", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody K100 k100Param){
        try {
            if(k100Param.getFk101() == null || k100Param.getFk101().size() == 0){
                return new ResponseEntity<>("Errol: 6001 - Param null",HttpStatus.BAD_REQUEST);
            }
            String kv101Param = k100Param.getKv101();
            String kv102Param = k100Param.getFk101().get(0).getKv102();
            String langParam = k100Param.getFk101().get(0).getLang();
            if(kv101Param.isEmpty() || kv102Param.isEmpty() || langParam.isEmpty()){
                return new ResponseEntity<>("Errol: 6001 - Param null",HttpStatus.BAD_REQUEST);
            } else {
                if(k100Param.get_id()==null){
                    //create
                    K100 res = k100service.save(k100Param);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                }else{
                    //update
                    K100 k100 = k100service.queryByID(k100Param.get_id().toString());
                    if(k100==null){
                        return new ResponseEntity<>("Errol: 2002 - Not Exist",HttpStatus.BAD_REQUEST);
                    } else {
                        if(k100Param.getFk101().get(0).get_id() == null){
                            //case add lang: check existed lang
                            for (K100.Fk101 fk101 : k100.getFk101()) {
                                if(fk101.getLang().equals(langParam)){
                                    return new ResponseEntity<>("Errol: Language has Existed",HttpStatus.BAD_REQUEST);
                                }
                            }
                        } else {
                            //case update lang
                            boolean flag = false;
                            for (K100.Fk101 fk101 : k100.getFk101()) {
                                if(fk101.get_id().equals(k100Param.getFk101().get(0).get_id()) && fk101.getLang().equals(langParam)){
                                    flag = true;
                                    break;
                                }
                            }
                            if(!flag){
                                return new ResponseEntity<>("Errol: 2002 - Not Exist",HttpStatus.BAD_REQUEST);
                            }
                        }
                        K100 res = k100service.save(k100Param);
                        System.out.println(res.toString());
                        return new ResponseEntity<>(res, HttpStatus.OK);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "syzFodGroupCatsInf/v1", method = RequestMethod.GET)
    public ResponseEntity<?> getListAll(@RequestParam String lang){
        try {
            if(lang.isEmpty()){
                return new ResponseEntity<>("Errol: 6001 - Param null",HttpStatus.BAD_REQUEST);
            } else {
                List<K100> categories = k100service.findAll(lang);
                if(categories.size() == 0){
                    return new ResponseEntity<>("Errol: 2004 - No content",HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>(categories, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "syzFodGroupCatInf/v1", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@RequestParam String id, String lang){
        try {
            if(id.isEmpty() || lang.isEmpty()){
                return new ResponseEntity<>("Errol: 6001 - Param null",HttpStatus.BAD_REQUEST);
            } else {
                K100 k100 = k100service.queryByID(id);
                if(k100 == null){
                    return new ResponseEntity<>("Errol: 2002 - Not Exist",HttpStatus.BAD_REQUEST);
                } else {
                    K100 cate = k100service.findById(id, lang);
                    return new ResponseEntity<>(cate, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "syzFodGroupCatDel/v1", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@RequestParam String id){
        try {
            if(id.isEmpty()){
                return new ResponseEntity<>("Errol: 6001 - Param null",HttpStatus.BAD_REQUEST);
            } else {
                K100 k100 = k100service.queryByID(id);
                if(k100 == null){
                    return new ResponseEntity<>("Errol: 2002 - Not Exist",HttpStatus.BAD_REQUEST);
                } else {
                    K100 cate = k100service.delete(k100);
                    return new ResponseEntity<>(cate, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
