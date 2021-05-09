package com.example.homework;

import com.example.homework.data.WeatherData;

import com.example.homework.service.WeatherBitClient;
import com.example.homework.service.WrongCoordinatesException;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherBitClientTest {

    @Mock(lenient=true)
    Logger logger;
    @Mock(lenient=true)
    RestTemplate template;

    @InjectMocks
    WeatherBitClient client;


    @Test
    void outofBoundsTest(){
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(-200.0,0.0);});
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(0.0,500.0);});
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(200.0,000.0);});
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(0.0,-500.0);});
    }

    @SneakyThrows
    @Test void coordinateDesyncTest(){
        when(template.getForEntity(Mockito.anyString(),Mockito.any())).thenReturn(ResponseEntity.ok("          {  \n" + "             \"lat\":50,\n" + "             \"lon\":50,\n" +"             \"timezone\":\"America\\/New_York\",\n" + "             \"city_name\":\"Raleigh\",\n" + "             \"country_code\":\"US\",\n" + "             \"state_code\":\"NC\",\n" + "             \"data\":[  \n" + "                {  \n" + "                   \"aqi\":47,\n" + "                   \"o3\":101.2,\n" + "                   \"so2\":3.8594,\n" + "                   \"no2\":5.42472,\n" + "                   \"co\":251.9,\n" + "                   \"pm10\":16,\n" + "                   \"pm25\":11,\n" + "                   \"pollen_level_tree\": 4,\n" + "                   \"pollen_level_grass\": 2,\n" + "                   \"pollen_level_weed\": 2,\n" + "                   \"mold_level\": 0,\n" + "                   \"predominant_pollen_type\": \"Trees\"\n" + "                }\n" + "             ]\n" + "          }"));
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(49.0,49.0);});
    }

    @Test void objectConverterTest() throws WrongCoordinatesException, JSONException {
        when(template.getForEntity(Mockito.anyString(),Mockito.any())).thenReturn(ResponseEntity.ok("          {  \n" + "             \"lat\":50,\n" + "             \"lon\":50,\n" +"             \"timezone\":\"America\\/New_York\",\n" + "             \"city_name\":\"Raleigh\",\n" + "             \"country_code\":\"US\",\n" + "             \"state_code\":\"NC\",\n" + "             \"data\":[  \n" + "                {  \n" + "                   \"aqi\":47,\n" + "                   \"o3\":101.2,\n" + "                   \"so2\":3.8594,\n" + "                   \"no2\":5.42472,\n" + "                   \"co\":251.9,\n" + "                   \"pm10\":16,\n" + "                   \"pm25\":11,\n" + "                   \"pollen_level_tree\": 4,\n" + "                   \"pollen_level_grass\": 2,\n" + "                   \"pollen_level_weed\": 2,\n" + "                   \"mold_level\": 0,\n" + "                   \"predominant_pollen_type\": \"Trees\"\n" + "                }\n" + "             ]\n" + "          }"));
        WeatherData data = new WeatherData();
        data.setLon(50);
        data.setLat(50);
        data.setPm25(11);
        data.setPm10(16);
        data.setAqi(47);
        data.setCo(251.9);
        data.setO3(101.2);
        Assertions.assertEquals(data,client.getData(50,50));
    }
}
