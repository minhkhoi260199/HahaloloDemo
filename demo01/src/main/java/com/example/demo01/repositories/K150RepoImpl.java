package com.example.demo01.repositories;

import com.example.demo01.entities.K150;
import com.mongodb.MongoException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class K150RepoImpl implements K150Repo {
   @Autowired
   private MongoTemplate mongoTemplate;

   @Override
   public K150 find(String id, String lang) {
      return null;
   }

   @Override
   public List<K150> findAll(String lang) {
      return null;
   }

   @Override
   public K150 queryById(String id) {
      try {
         return mongoTemplate.findById(id, K150.class);
      } catch (MongoException e) {
         e.printStackTrace();
         throw e;
      }
   }

   @Override
   public K150 delete(K150 k150) {
      return null;
   }

   @Override
   public K150 save(K150 k150Param) {
      try {
         if (k150Param.get_id() == null) {
            //create k150
            k150Param.set_id(new ObjectId());
            k150Param.getFk151().get(0).set_id(new ObjectId());
            return mongoTemplate.insert(k150Param);
         } else {
            //update k150
            K150 k150 = mongoTemplate.findById(k150Param.get_id(), K150.class);
            if (k150Param.getFk151().get(0).get_id() == null) {
               //add fk151
               k150Param.getFk151().get(0).set_id(new ObjectId());
               k150.getFk151().add(k150Param.getFk151().get(0));
            } else {
               //update fk151
               for (int i = 0; i < k150.getFk151().size(); i++) {
                  if (k150.getFk151().get(i).get_id().equals(k150Param.getFk151().get(0).get_id())) {
                     k150.getFk151().set(i, k150Param.getFk151().get(0));
                  }
               }
            }
            return mongoTemplate.save(k150);
         }
      } catch (MongoException e) {
         e.printStackTrace();
         throw e;
      }
   }
}
