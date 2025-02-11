package org.adaschool.Weather;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WeatherReportServiceTest {

    // Constants for test data
    private static final double TEST_LATITUDE = 37.8267;
    private static final double TEST_LONGITUDE = -122.4233;
    private static final double TEST_TEMPERATURE = 0.0;
    private static final int TEST_HUMIDITY = 70;
    private static final String API_KEY = "4e622c43a69685f1bcdd9b22ba89bc43";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherReportService weatherReportService;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWeatherReport_SuccessfulResponse_ReturnsCorrectWeatherReport() {
        // Arrange
        WeatherApiResponse mockResponse = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(TEST_TEMPERATURE);
        main.setHumidity(TEST_HUMIDITY);
        mockResponse.setMain(main);

        String expectedUrl = BASE_URL + "?lat=" + TEST_LATITUDE + "&lon=" + TEST_LONGITUDE + "&appid=" + API_KEY;
        when(restTemplate.getForObject(eq(expectedUrl), eq(WeatherApiResponse.class)))
                .thenReturn(mockResponse);

        // Act
        WeatherReport result = weatherReportService.getWeatherReport(TEST_LATITUDE, TEST_LONGITUDE);

        // Assert
        assertNotNull(result, "Weather report should not be null");
        assertEquals(TEST_TEMPERATURE, result.getTemperature(),
                "Temperature should match the mock response");
        assertEquals(TEST_HUMIDITY, result.getHumidity(),
                "Humidity should match the mock response");
    }
}