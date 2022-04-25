package com.example.demo01.repositories;

import com.example.demo01.entities.K100;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.BsonNull;
import org.bson.conversions.Bson;
import org.bson.Document;
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
public class K100RepoImpl implements K100Repo{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public K100 save(K100 k100Param){
        try{
            if(k100Param.get_id() == null){
                //create k100
                k100Param.set_id(new ObjectId());
                k100Param.getFk101().get(0).set_id(new ObjectId());
                return mongoTemplate.insert(k100Param);
            } else {
                //update k100
                K100 k100 = mongoTemplate.findById(k100Param.get_id(), K100.class);
                if(k100Param.getFk101().get(0).get_id() == null){
                    //add fk101
                    k100Param.getFk101().get(0).set_id(new ObjectId());
                    k100.getFk101().add(k100Param.getFk101().get(0));
                } else {
                    //update fk101
                    for (int i=0 ; i < k100.getFk101().size(); i++){
                        if(k100.getFk101().get(i).get_id().equals(k100Param.getFk101().get(0).get_id())){
                            k100.getFk101().set(i, k100Param.getFk101().get(0));
                        }
                    }
                }
                return mongoTemplate.save(k100);
            }
        } catch (MongoException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public List<K100> findAll(String lang){
        try{
            MongoCollection<Document> collection = this.mongoTemplate.getCollection("k100");
            List<Bson> pipeline = Arrays.asList(new Document("$match", new Document("$and", Arrays.asList(new Document("fk101.lang", lang), new Document("dl145", new Document()
                    .append("$exists", false)
                    .append("$eq", new BsonNull()))))),new Document("$project", new Document()
                                                        .append("id", 1)
                                                        .append("kv101",1)
                                                        .append("fk101", new Document("$filter", new Document()
                                                            .append("input", "$fk101")
                                                            .append("as", "fk101")
                                                            .append("cond", new Document("$eq", Arrays.asList("$$fk101.lang", lang)))))));
            List<Document> result = StreamSupport.stream(collection.aggregate(pipeline).spliterator(), false)
                            .collect(Collectors.toList());
            System.out.println(result);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(result, new TypeToken<ArrayList<K100>>(){}.getType());
            return gson.fromJson(jsonStr, new TypeToken<ArrayList<K100>>(){}.getType());

        } catch (MongoException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public K100 find(String id, String lang) {
        try{
            MongoCollection<Document> collection = this.mongoTemplate.getCollection("k100");
            List<Bson> pipeline = Arrays.asList(new Document("$match", new Document("$and", Arrays.asList(new Document("_id", new ObjectId(id)), new Document("fk101.lang", lang), new Document("dl145", new Document()
                    .append("$exists", false)
                    .append("$eq", new BsonNull()))))),new Document("$project", new Document()
                                                        .append("id",1)
                                                        .append("kv101",1)
                                                        .append("fk101", new Document("$filter", new Document()
                                                                .append("input", "$fk101")
                                                                .append("as", "fk101")
                                                                .append("cond", new Document("$eq", Arrays.asList("$$fk101.lang", lang)))))));
            List<Document> result = StreamSupport.stream(collection.aggregate(pipeline).spliterator(), false)
                    .collect(Collectors.toList());
            System.out.println(result.get(0));
            Gson gson = new Gson();
            String jsonStr = gson.toJson(result.get(0), Document.class);
            return gson.fromJson(jsonStr, K100.class);
        } catch (MongoException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public K100 delete(K100 k100Param) {
        try {
            k100Param.setDl145(new Date());
            return mongoTemplate.save(k100Param);
        } catch (MongoException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public K100 queryById(String id) {
        try {
            return mongoTemplate.findById(id, K100.class);
        } catch (MongoException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
