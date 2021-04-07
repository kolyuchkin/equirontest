package com.kavhome.equirontest.restservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kavhome.equirontest.restservice.controller.DocumentValidationController;
import com.kavhome.equirontest.restservice.pojo.DocPojo;
import com.kavhome.equirontest.restservice.pojo.ProductPojo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:koljuchkin.aleksandr@alphaopen.com" >Aleksandr Kolyuchkin</a>
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DocumentValidationController.class)
public class DocumentValidationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenInputIsInvalid_thenReturnsStatus400() throws Exception {
        DocPojo input = new DocPojo();
        input.setSeller("111111111");
        input.setCustomer("222222222");
        final ProductPojo pojo1 = new ProductPojo();
        pojo1.setName("sdfsdf");
        pojo1.setCode("3333333333333");
        input.setProducts(List.of(pojo1));
        String body = objectMapper.writeValueAsString(input);

        mvc.perform(post("/validate")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isOk());

        input.setProducts(List.of());
        body = objectMapper.writeValueAsString(input);

        mvc.perform(post("/validate")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());
    }
}
