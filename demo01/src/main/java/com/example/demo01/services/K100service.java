package com.example.demo01.services;

import com.example.demo01.entities.K100;
import com.example.demo01.repositories.K100RepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class K100service {
   @Autowired
   private K100RepoImpl k100Repo;

   public K100 save(K100 k100) {
      return k100Repo.save(k100);
   }

   public List<K100> findAll(String lang) {
      return k100Repo.findAll(lang);
   }

   public K100 findById(String id, String lang) {
      return k100Repo.find(id, lang);
   }

   public K100 queryByID(String id) {
      return k100Repo.queryById(id);
   }

   public K100 delete(K100 k100) {
      return k100Repo.delete(k100);
   }
}
