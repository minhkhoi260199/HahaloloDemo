package com.example.demo01.repositories;

import com.example.demo01.entities.K100;
import com.example.demo01.entities.K150;

import java.util.List;

public interface K150Repo {
    public K150 find(String id);
    public List<K150> findAll();
    K150 queryById(String id);
    public K150 save(K150 k150);
    public K150 delete(String id);
}
