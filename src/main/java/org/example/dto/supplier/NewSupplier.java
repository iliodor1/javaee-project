package org.example.dto.supplier;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class NewSupplier implements Serializable {
    private String companyName;
    private String country;


    @JsonCreator
    public NewSupplier(@JsonProperty("companyName") String companyName, @JsonProperty("country") String country) {
        this.companyName = companyName;
        this.country = country;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
