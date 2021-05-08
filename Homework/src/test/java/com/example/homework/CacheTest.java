package com.example.homework;

import com.example.homework.data.WeatherData;
import com.example.homework.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CacheTest {
    @Spy
    Clock clock;
    @Mock(lenient=true)
    Logger logger;
    @Mock(lenient=true)
    OpenWeatherMapClient mainClient;
    @Mock(lenient=true)
    WeatherBitClient secondaryClient;
    @InjectMocks
    CachedInfoProvider cachedInfoProvider;

    @Test
    @Order(1)
    void getFromMainApiTest() throws WrongCoordinatesException {
        WeatherData data = new WeatherData();
        data.setAqi(299);
        when(mainClient.getData(Mockito.anyDouble(),Mockito.anyDouble())).thenReturn(data);
        long mainApiRequests = cachedInfoProvider.getMainApiRequests();
        long mainApiRequestSuccess = cachedInfoProvider.getMainApiRequestsSuccess();
        Assertions.assertEquals(data,cachedInfoProvider.getData(0,0));
        Assertions.assertEquals(mainApiRequests+1,cachedInfoProvider.getMainApiRequests());
        Assertions.assertEquals(mainApiRequestSuccess+1,cachedInfoProvider.getMainApiRequestsSuccess());
    }
    @Test
    @Order(2)
    void backupApiTest() throws WrongCoordinatesException {
        WeatherData data = new WeatherData();
        data.setAqi(299);

        when(mainClient.getData(Mockito.anyDouble(),Mockito.anyDouble())).thenThrow(WrongCoordinatesException.class);
        when(secondaryClient.getData(Mockito.anyDouble(),Mockito.anyDouble())).thenReturn(data);

        long mainApiRequests = cachedInfoProvider.getMainApiRequests();
        long mainApiRequestSuccess = cachedInfoProvider.getMainApiRequestsSuccess();

        long backupApiRequests = cachedInfoProvider.getBackupApiRequests();
        long backupApiRequestSuccess = cachedInfoProvider.getBackupApiRequestsSuccess();

        Assertions.assertEquals(data,cachedInfoProvider.getData(1,1));
        Assertions.assertEquals(mainApiRequests+1,cachedInfoProvider.getMainApiRequests());
        Assertions.assertEquals(mainApiRequestSuccess,cachedInfoProvider.getMainApiRequestsSuccess());
        Assertions.assertEquals(backupApiRequests+1,cachedInfoProvider.getBackupApiRequests());
        Assertions.assertEquals(backupApiRequestSuccess+1,cachedInfoProvider.getBackupApiRequestsSuccess());
        
    }
    @Test
    @Order(3)
    void cachedContentTest() throws WrongCoordinatesException {
        WeatherData data = new WeatherData();
        data.setAqi(299);
        when(mainClient.getData(Mockito.anyDouble(),Mockito.anyDouble())).thenReturn(data);

        long cached = cachedInfoProvider.getCacheUsages();
        long mainApiRequests = cachedInfoProvider.getMainApiRequests();

        cachedInfoProvider.getData(3,3);
        cachedInfoProvider.getData(3,3);
        cachedInfoProvider.getData(3,3);
        cachedInfoProvider.getData(3,3);
        //it gets requested once and cached the next 3
        Assertions.assertEquals(cached+3,cachedInfoProvider.getCacheUsages());
        Assertions.assertEquals(mainApiRequests+1,cachedInfoProvider.getMainApiRequests());
    }
    @Test
    @Order(4)
    void cacheTimeoutTest() throws WrongCoordinatesException {
        WeatherData data = new WeatherData();
        data.setAqi(299);
        when(mainClient.getData(Mockito.anyDouble(),Mockito.anyDouble())).thenReturn(data);
        long cached = cachedInfoProvider.getCacheUsages();
        long mainApiRequests = cachedInfoProvider.getMainApiRequests();

        cachedInfoProvider.getData(3,3);
        cachedInfoProvider.getData(3,3);

        Assertions.assertEquals(cached+1,cachedInfoProvider.getCacheUsages());
        Assertions.assertEquals(mainApiRequests+1,cachedInfoProvider.getMainApiRequests());

        doReturn(System.currentTimeMillis()+60000).when(clock).currentTimeMillis();

        //cache timed out
        cachedInfoProvider.getData(3,3);
        Assertions.assertEquals(cached+1,cachedInfoProvider.getCacheUsages());
        Assertions.assertEquals(mainApiRequests+2,cachedInfoProvider.getMainApiRequests());

    }
}
