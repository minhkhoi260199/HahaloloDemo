package com.example.demo01.repositories;

import com.example.demo01.entities.K150;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class K150RepoImpl implements K150Repo {
   @Autowired
   private MongoTemplate mongoTemplate;

   @Override
   public List<K150> findAll(String lang) {
      try {
         MongoCollection<Document> collection = this.mongoTemplate.getCollection("k150");
         List<Bson> pipeline = Arrays.asList(new Document("$match", new Document("$and", Arrays.asList(new Document("fk151.lang", lang), new Document("dl145", new Document()
                 .append("$exists", false)
                 .append("$eq", new BsonNull()))))), new Document("$project", new Document()
                 .append("id", 1)
                 .append("kv151", 1)
                 .append("pk100", 1)
                 .append("fk151", new Document("$filter", new Document()
                         .append("input", "$fk151")
                         .append("as", "fk151")
                         .append("cond", new Document("$eq", Arrays.asList("$$fk151.lang", lang)))))));
         List<Document> result = StreamSupport.stream(collection.aggregate(pipeline).spliterator(), false)
                 .collect(Collectors.toList());
         System.out.println(result);

         Gson gson = new Gson();
         String jsonStr = gson.toJson(result, new TypeToken<ArrayList<K150>>() {}.getType());
         return gson.fromJson(jsonStr, new TypeToken<ArrayList<K150>>() {}.getType());

      } catch (MongoException e) {
         e.printStackTrace();
         throw e;
      }
   }

   @Override
   public K150 find(String id, String lang) {
      try {
         MongoCollection<Document> collection = this.mongoTemplate.getCollection("k150");
         List<Bson> pipeline = Arrays.asList(new Document("$match", new Document("$and", Arrays.asList(new Document("_id", new ObjectId(id)), new Document("fk151.lang", lang), new Document("dl145", new Document()
                 .append("$exists", false)
                 .append("$eq", new BsonNull()))))), new Document("$project", new Document()
                 .append("id", 1)
                 .append("kv151", 1)
                 .append("pk100", 1)
                 .append("fk151", new Document("$filter", new Document()
                         .append("input", "$fk151")
                         .append("as", "fk151")
                         .append("cond", new Document("$eq", Arrays.asList("$$fk151.lang", lang)))))));
         List<Document> result = StreamSupport.stream(collection.aggregate(pipeline).spliterator(), false)
                 .collect(Collectors.toList());
         System.out.println(result.get(0));
         Gson gson = new Gson();
         String jsonStr = gson.toJson(result.get(0), Document.class);
         return gson.fromJson(jsonStr, K150.class);
      } catch (MongoException e) {
         e.printStackTrace();
         throw e;
      }
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
      try {
         k150.setDl145(new Date());
         return mongoTemplate.save(k150);
      } catch (MongoException e) {
         e.printStackTrace();
         throw e;
      }
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
