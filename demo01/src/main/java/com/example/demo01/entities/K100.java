package com.example.demo01.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class K100 implements Serializable {

   @Id
   @JsonProperty("id")
   @JsonSerialize(using = ToStringSerializer.class)
   private ObjectId _id;
   private String kv101;
   private List<Fk101> fk101;
   //Record deleted
   @JsonSerialize(using = ToStringSerializer.class)
   private Date dl145;

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @Document
   public static class Fk101 {
      @JsonProperty("id")
      @JsonSerialize(using = ToStringSerializer.class)
      ObjectId _id;
      String kv102;
      String lang;
   }


}
