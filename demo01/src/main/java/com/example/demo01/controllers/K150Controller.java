package com.example.demo01.controllers;

import com.example.demo01.entities.K150;
import com.example.demo01.services.K100service;
import com.example.demo01.services.K150service;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
         if ( k150Param.getKv151() == null
              || k150Param.getFk151() == null
              || k150Param.getFk151().get(0).getKv152() == null
              || k150Param.getFk151().get(0).getLang() == null
              || k150Param.getPk100() == null
              || k150Param.getKv151().isEmpty()
              || k150Param.getFk151().get(0).getKv152().isEmpty()
              || k150Param.getFk151().get(0).getLang().isEmpty()
              || k150Param.getPk100().isEmpty()) {
            return new ResponseEntity<>("Errol: 6001 - Param null", HttpStatus.BAD_REQUEST);
         }
         String langParam = k150Param.getFk151().get(0).getLang();
         String pk100Param = k150Param.getPk100();
         if (k150Param.getFk151().size() != 1) {
            return new ResponseEntity<>("Errol: 6001 - Param invalid", HttpStatus.BAD_REQUEST);
         }
         if(k100service.queryByID(pk100Param) == null) {
            return new ResponseEntity<>("Errol: 2002 - GCate Not Exist", HttpStatus.BAD_REQUEST);
         }
         if (k150Param.get_id() == null) {
            //create
            k150Param.set_id(new ObjectId());
            k150Param.getFk151().get(0).set_id(new ObjectId());
            K150 res = k150service.save(k150Param);
            return new ResponseEntity<>(res, HttpStatus.OK);
         } else {
            //update
            K150 k150 = k150service.queryByID(k150Param.get_id().toString());
            if (k150 == null) {
               return new ResponseEntity<>("Errol: 2002 - Not Exist", HttpStatus.BAD_REQUEST);
            }
            if (k150Param.getFk151().get(0).get_id() == null) {
               //case add lang: check existed lang
               for (K150.Fk151 fk151 : k150.getFk151()) {
                  if (fk151.getLang().equals(langParam)) {
                     return new ResponseEntity<>("Errol: Language has Existed", HttpStatus.BAD_REQUEST);
                  }
               }
               k150Param.getFk151().get(0).set_id(new ObjectId());
               k150.getFk151().add(k150Param.getFk151().get(0));
            } else {
               //case update lang
               boolean flag = false;
               int i = 0;
               for (K150.Fk151 fk151 : k150.getFk151()) {
                  if (fk151.get_id().equals(k150Param.getFk151().get(0).get_id()) && fk151.getLang().equals(langParam)) {
                     k150.getFk151().set(i, k150Param.getFk151().get(0));
                     flag = true;
                     break;
                  }
                  i++;
               }
               if (!flag) {
                  return new ResponseEntity<>("Errol: 2002 - Not Exist", HttpStatus.BAD_REQUEST);
               }
            }
            K150 res = k150service.save(k150);
            System.out.println(res.toString());
            return new ResponseEntity<>(res, HttpStatus.OK);
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
   }

   @RequestMapping(value = "syzFodCatsInf/v1", method = RequestMethod.GET)
   public ResponseEntity<?> getListAll(@RequestParam String lang) {
      try {
         if (lang.isEmpty()) {
            return new ResponseEntity<>("Errol: 6001 - Param null", HttpStatus.BAD_REQUEST);
         } else {
            List<K150> categories = k150service.findAll(lang);
            if (categories.size() == 0) {
               return new ResponseEntity<>("Errol: 2004 - No content", HttpStatus.BAD_REQUEST);
            } else {
               return new ResponseEntity<>(categories, HttpStatus.OK);
            }
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
   }

   @RequestMapping(value = "syzFodCatInf/v1", method = RequestMethod.GET)
   public ResponseEntity<?> getById(@RequestParam String id, String lang) {
      try {
         if (id.isEmpty() || lang.isEmpty()) {
            return new ResponseEntity<>("Errol: 6001 - Param null", HttpStatus.BAD_REQUEST);
         } else {
            K150 K150 = k150service.queryByID(id);
            if (K150 == null) {
               return new ResponseEntity<>("Errol: 2002 - Not Exist", HttpStatus.BAD_REQUEST);
            } else {
               K150 cate = k150service.findById(id, lang);
               return new ResponseEntity<>(cate, HttpStatus.OK);
            }
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
   }

   @RequestMapping(value = "syzFodCatDel/v1", method = RequestMethod.GET)
   public ResponseEntity<?> delById(@RequestParam String id) {
      try {
         if (id.isEmpty()) {
            return new ResponseEntity<>("Errol: 6001 - Param null", HttpStatus.BAD_REQUEST);
         } else {
            K150 K150 = k150service.queryByID(id);
            if (K150 == null) {
               return new ResponseEntity<>("Errol: 2002 - Not Exist", HttpStatus.BAD_REQUEST);
            } else {
               K150 cate = k150service.delete(K150);
               return new ResponseEntity<>(cate, HttpStatus.OK);
            }
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
   }
}
