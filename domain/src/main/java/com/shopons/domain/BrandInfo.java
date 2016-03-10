package com.shopons.domain;

/**
 * Created by komal on 9/3/16.
 */
public class BrandInfo {

        private String person_type;

        public BrandInfo(String person_type)
        {
            this.person_type=person_type;
        }

        public String getPerson_type() {
            return person_type;
        }

        public void setPerson_type(String person_type) {
            this.person_type = person_type;
        }

}
