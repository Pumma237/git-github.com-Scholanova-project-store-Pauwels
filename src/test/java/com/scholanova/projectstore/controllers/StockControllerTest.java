package com.scholanova.projectstore.controllers;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.exceptions.StoreNameCannotBeEmptyException;
import com.scholanova.projectstore.exceptions.StoreNotFoundException;
import com.scholanova.projectstore.exceptions.StockNotValidException;
import com.scholanova.projectstore.models.Store;
import com.scholanova.projectstore.models.Stock;
import com.scholanova.projectstore.services.StockService;
import com.scholanova.projectstore.services.StoreService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StockControllerTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate template = new TestRestTemplate();

    @MockBean
    private StockService stockService;

    @Captor
    ArgumentCaptor<Stock> createStockArgumentCaptor;

    @Captor
    ArgumentCaptor<Integer> storeIdArgumentCaptor;

    @Nested
    class Test_createStock {

        @Test
        void givenCorrectBody_whenCalled_createsStock() throws Exception, StockNotValidException {
            // given
            String url = "http://localhost:{port}/stores/1/stocks";

            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("port", String.valueOf(port));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestJson = "{" +
                    "\"name\":\"Flat Nail\"," +
                    "\"type\":\"Nail\"," +
                    "\"value\":100," +
                    "\"storeId\":1" +
                    "}";
            HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);

            Stock createdStock = new Stock(1, "Flat Nail", "Nail", 100, 1);
            when(stockService.create(storeIdArgumentCaptor.capture(),createStockArgumentCaptor.capture())).thenReturn(createdStock);

            // When
            ResponseEntity responseEntity = template.exchange(url,
                    HttpMethod.POST,
                    httpEntity,
                    String.class,
                    urlVariables);

            // Then
            assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
            assertThat(responseEntity.getBody()).isEqualTo(
                    "{" +
                            "\"id\":1," +
                            "\"name\":\"Flat Nail\"," +
                            "\"type\":\"Nail\"," +
                            "\"value\":100," +
                            "\"storeId\":1" +
                            "}"
            );
            Stock stockToCreate = createStockArgumentCaptor.getValue();
            assertThat(stockToCreate.getName()).isEqualTo("Flat Nail");
            assertThat(storeIdArgumentCaptor.getValue()).isEqualTo(1);
        }

        @Test
        void givenEmptyName_whenCalled_createsStore() throws Exception, StockNotValidException {
            // given
            String url = "http://localhost:{port}/stores/1/stocks";

            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("port", String.valueOf(port));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestJson = "{" +
                    "\"name\":\"\"," +
                    "\"type\":\"Nail\"," +
                    "\"value\":100," +
                    "\"storeId\":1" +
                    "}";
            HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);

            Stock createdStock = new Stock(1, "", "Nail", 100, 1);
            when(stockService.create(1, createdStock)).thenThrow(StockNotValidException.class);

            // When
            ResponseEntity responseEntity = template.exchange(url,
                    HttpMethod.POST,
                    httpEntity,
                    String.class,
                    urlVariables);

            // Then
            assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
            assertThat(responseEntity.getBody()).isEqualTo(
                    "{" +
                            "\"msg\":\"Invalid stock\"" +
                            "}"
            );

            verify(stockService).create(1, createdStock);
        }

        @Test
        void givenZeroToValue_whenCalled_createsStore() throws Exception, StockNotValidException {
            // given
            String url = "http://localhost:{port}/stores/1/stocks";

            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("port", String.valueOf(port));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestJson = "{" +
                    "\"name\":\"salut\"," +
                    "\"type\":\"Nail\"," +
                    "\"value\":0," +
                    "\"storeId\":1" +
                    "}";
            HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);

            Stock createdStock = new Stock(1, "salut", "Nail", 0, 1);
            when(stockService.create(1, createdStock)).thenThrow(StockNotValidException.class);

            // When
            ResponseEntity responseEntity = template.exchange(url,
                    HttpMethod.POST,
                    httpEntity,
                    String.class,
                    urlVariables);

            // Then
            assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
            assertThat(responseEntity.getBody()).isEqualTo(
                    "{" +
                            "\"msg\":\"Invalid stock\"" +
                            "}"
            );

            verify(stockService).create(1, createdStock);
        }

        @Test
        void givenBadType_whenCalled_createsStore() throws Exception, StockNotValidException {
            // given
            String url = "http://localhost:{port}/stores/1/stocks";

            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("port", String.valueOf(port));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestJson = "{" +
                    "\"name\":\"salut\"," +
                    "\"type\":\"Fruits\"," +
                    "\"value\":10," +
                    "\"storeId\":1" +
                    "}";
            HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);

            Stock createdStock = new Stock(1, "salut", "Fruits", 10, 1);
            when(stockService.create(1, createdStock)).thenThrow(StockNotValidException.class);

            // When
            ResponseEntity responseEntity = template.exchange(url,
                    HttpMethod.POST,
                    httpEntity,
                    String.class,
                    urlVariables);

            // Then
            assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
            assertThat(responseEntity.getBody()).isEqualTo(
                    "{" +
                            "\"msg\":\"Invalid stock\"" +
                            "}"
            );

            verify(stockService).create(1, createdStock);
        }
    }
}