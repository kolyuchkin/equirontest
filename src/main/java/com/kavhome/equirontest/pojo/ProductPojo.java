package com.kavhome.equirontest.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author <a href="mailto:koljuchkin.aleksandr@alphaopen.com" >Aleksandr Kolyuchkin</a>
 */
public class ProductPojo {
    @NotBlank(message = "Product name field should not be blank")
    private String name;

    @NotBlank(message = "Product code field should not be blank")
    @Pattern(regexp = "^[0-9]{13}$", message = "Product code field must contains only 13 digits")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
