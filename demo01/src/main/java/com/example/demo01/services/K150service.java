package com.example.demo01.services;

import com.example.demo01.entities.K150;
import com.example.demo01.repositories.K150RepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class K150service {
   @Autowired
   private K150RepoImpl k150Repo;

   public K150 save(K150 k150) {
      return k150Repo.save(k150);
   }

   public List<K150> findAll(String lang) {
      return k150Repo.findAll(lang);
   }

   public K150 findById(String id, String lang) {
      return k150Repo.find(id, lang);
   }

   public K150 queryByID(String id) {
      return k150Repo.queryById(id);
   }

   public K150 delete(K150 k150) {
      return k150Repo.delete(k150);
   }
}
