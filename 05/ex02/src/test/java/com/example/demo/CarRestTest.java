package com.example.demo;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CarController.class)
public class CarRestTest {
    @Autowired
    MockMvc testServlet;
    @MockBean
    CarManagerService carManagerService;
    @Autowired
    CarController carController;


    @Test
    void getCreatedCar() throws Exception {

        Car carro1 = new Car("sample text 1", "sample text 2");
        carro1.setCarId(125L);
        System.out.println(JsonUtil.toJson(carro1));
        when(carManagerService.save(Mockito.any()) ).thenReturn( carro1);
        testServlet.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(carro1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker").value("sample text 1"));
        verify(carManagerService,times(1)).save(Mockito.any());
    }

    @Test
    void getCarID() throws Exception {
        Car carro1= new Car();
        carro1.setMaker("toyota");
        when(carManagerService.getCarDetails(Mockito.any())).thenReturn(Optional.of(carro1));
        testServlet.perform(get("/api/cars/10000")).andExpect(jsonPath("$.maker").value("toyota"));
    }
}
