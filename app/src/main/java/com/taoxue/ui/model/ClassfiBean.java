package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2018/1/31.
 */

public class ClassfiBean extends BaseModel {
   String fclassify;
   List<String>classify;

   public String getFclassify() {
      return fclassify;
   }

   public void setFclassify(String fclassify) {
      this.fclassify = fclassify;
   }

   public List<String> getClassify() {
      return classify;
   }

   public void setClassify(List<String> classify) {
      this.classify = classify;
   }

   @Override
   public String toString() {
      return "ClassfiBean{" +
              "fclassify='" + fclassify + '\'' +
              ", classify=" + classify +
              '}';
   }
}
