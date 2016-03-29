package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by komal on 9/3/16.
 */
public class BrandInfo {

      private String person_type;
      @SerializedName("category_one")
      private String category;

      public String getPerson_type() {
            return person_type;
        }
      public void setPerson_type(String person_type) {
            this.person_type = person_type;
        }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
