package com.example.homework;
import com.example.homework.data.WeatherData;
import com.example.homework.service.OpenWeatherMapClient;
import com.example.homework.service.WrongCoordinatesException;
import lombok.SneakyThrows;
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
class OpenWeatherClientTest {



    @Mock(lenient=true)
    Logger logger;
    @Mock(lenient=true)
    RestTemplate template;

    @InjectMocks
    OpenWeatherMapClient client;



    @Test
    void outofBoundsTest() throws WrongCoordinatesException {
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(-200.0,0.0);});
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(0.0,500.0);});
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(200.0,000.0);});
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(0.0,-500.0);});
    }

    @SneakyThrows
    @Test void coordinateDesyncTest(){
        when(template.getForEntity(Mockito.anyString(),Mockito.any())).thenReturn(ResponseEntity.ok("{\n" + "  \"coord\": {\"lat\":50,\n" + "  \"lon\":50},\n" + "  \"list\":[\n" + "    {\n" + "      \"dt\":1605182400,\n" + "      \"main\":{\n" + "        \"aqi\":1\n" + "      },\n" + "      \"components\":{\n" + "        \"co\":201.94053649902344,\n" + "        \"no\":0.01877197064459324,\n" + "        \"no2\":0.7711350917816162,\n" + "        \"o3\":68.66455078125,\n" + "        \"so2\":0.6407499313354492,\n" + "        \"pm2_5\":0.5,\n" + "        \"pm10\":0.540438711643219,\n" + "        \"nh3\":0.12369127571582794\n" + "      }\n" + "    }\n" + "  ]\n" + "} "));
        Assertions.assertThrows(WrongCoordinatesException.class,()->{client.getData(49.0,49.0);});
    }

    @Test void objectConverterTest() throws WrongCoordinatesException {
        when(template.getForEntity(Mockito.anyString(),Mockito.any())).thenReturn(ResponseEntity.ok("{\n" + "  \"coord\":{\"lat\": 50,\n" + " \"lon\":   50},\n" + "  \n" + "  \"list\":[\n" + "    {\n" + "      \"dt\":1605182400,\n" + "      \"main\":{\n" + "        \"aqi\":1\n" + "      },\n" + "      \"components\":{\n" + "        \"co\":201.94053649902344,\n" + "        \"no\":0.01877197064459324,\n" + "        \"no2\":0.7711350917816162,\n" + "        \"o3\":68.66455078125,\n" + "        \"so2\":0.6407499313354492,\n" + "        \"pm2_5\":0.5,\n" + "        \"pm10\":0.540438711643219,\n" + "        \"nh3\":0.12369127571582794\n" + "      }\n" + "    }\n" + "  ]\n" + "} "));
        WeatherData data = new WeatherData();
        data.setLon(50);
        data.setLat(50);
        data.setPm25(0.5);
        data.setPm10(0.540438711643219);
        data.setAqi(1);
        data.setCo(201.94053649902344);
        data.setO3(68.66455078125);
        Assertions.assertEquals(data,client.getData(50,50));
    }
}
