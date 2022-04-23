package com.example.demo01.repositories;

import com.example.demo01.entities.K150;

import java.util.List;

public interface K150Repo {
    public K150 find(String id);
    public List<K150> findAll();
    public K150 insert(K150 k150);
    public K150 delete(String id);
}
