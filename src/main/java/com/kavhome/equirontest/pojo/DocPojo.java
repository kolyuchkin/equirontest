package com.kavhome.equirontest.pojo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author <a href="mailto:koljuchkin.aleksandr@alphaopen.com" >Aleksandr Kolyuchkin</a>
 */
public class DocPojo {
    @NotBlank(message = "Seller field should not be blank")
    @Pattern(regexp = "^[0-9]{9}$", message = "Seller field must contains only 9 digits")
    private String seller;

    @NotBlank(message = "Customer field should not be blank")
    @Pattern(regexp = "^[0-9]{9}$", message = "Customer field must contains only 9 digits")
    private String customer;

    @NotEmpty(message = "Products list field mast not be empty")
    @Valid
    private List<ProductPojo> products;

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<ProductPojo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductPojo> products) {
        this.products = products;
    }
}
