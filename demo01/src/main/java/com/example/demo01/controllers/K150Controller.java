package com.example.demo01.controllers;

import com.example.demo01.entities.K150;
import com.example.demo01.services.K100service;
import com.example.demo01.services.K150service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class K150Controller {
   @Autowired
   private K100service k100service;
   @Autowired
   private K150service k150service;

   @RequestMapping(value = "syzFodCatCreatUdp/v1", method = RequestMethod.POST)
   public ResponseEntity<?> save(@RequestBody K150 k150Param) {
      try {
         if (k150Param.getFk151().size() != 1) {
            return new ResponseEntity<>("Errol: 6001 - Param invalid", HttpStatus.BAD_REQUEST);
         }
         String kv151Param = k150Param.getKv151();
         String kv102Param = k150Param.getFk151().get(0).getKv152();
         String langParam = k150Param.getFk151().get(0).getLang();
         String pk100Param = k150Param.getPk100();
         if ( kv151Param == null
              || kv102Param == null
              || langParam == null
              || pk100Param == null
              || kv151Param.isEmpty()
              || kv102Param.isEmpty()
              || langParam.isEmpty()
              || pk100Param.isEmpty()) {
            return new ResponseEntity<>("Errol: 6001 - Param null", HttpStatus.BAD_REQUEST);
         } else {
            if(k100service.queryByID(pk100Param) == null) {
               return new ResponseEntity<>("Errol: 2002 - GC Not Exist", HttpStatus.BAD_REQUEST);
            }
            if (k150Param.get_id() == null) {
               //create
               K150 res = k150service.save(k150Param);
               return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
               //update
               K150 K150 = k150service.queryByID(k150Param.get_id().toString());
               if (K150 == null) {
                  return new ResponseEntity<>("Errol: 2002 - Not Exist", HttpStatus.BAD_REQUEST);
               } else {
                  if (k150Param.getFk151().get(0).get_id() == null) {
                     //case add lang: check existed lang
                     for (K150.Fk151 fk151 : K150.getFk151()) {
                        if (fk151.getLang().equals(langParam)) {
                           return new ResponseEntity<>("Errol: Language has Existed", HttpStatus.BAD_REQUEST);
                        }
                     }
                  } else {
                     //case update lang
                     boolean flag = false;
                     for (K150.Fk151 fk151 : K150.getFk151()) {
                        if (fk151.get_id().equals(k150Param.getFk151().get(0).get_id()) && fk151.getLang().equals(langParam)) {
                           flag = true;
                           break;
                        }
                     }
                     if (!flag) {
                        return new ResponseEntity<>("Errol: 2002 - Not Exist", HttpStatus.BAD_REQUEST);
                     }
                  }
                  K150 res = k150service.save(k150Param);
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

}
