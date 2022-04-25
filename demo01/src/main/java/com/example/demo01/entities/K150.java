package com.example.demo01.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class K150 {
   @Id
   @JsonProperty("id")
   @JsonSerialize(using = ToStringSerializer.class)
   private ObjectId id;
   private String kv151;
   private List<Fk151> fk151 = new ArrayList<>();
   private String pk100;
   //Record deleted
   private Date dl145;

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @Document
   public static class Fk151 {
      @JsonProperty("id")
      @JsonSerialize(using = ToStringSerializer.class)
      ObjectId _id;
      private String kv152;
      private String lang;
   }
}
