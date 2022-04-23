package com.example.demo01.repositories;

import com.example.demo01.entities.K100;

import java.util.List;

public interface K100Repo {
    K100 find(String id, String lang);
    K100 queryById(String id);
    List<K100> findAll(String lang);
    K100 save(K100 k100);
    K100 delete(K100 k100);
}
