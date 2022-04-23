package com.example.demo01.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class K150 {
    @Id
    private ObjectId id;
    private String kv151;
    private List<Fk151> fk151 = new ArrayList<>();
    private String pk100;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Document
    public static class Fk151 {
        @Id
        private String kv152;
        private String lang;
    }
}
