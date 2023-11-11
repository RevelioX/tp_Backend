package TP.AlquileresMicroServicio.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

@Service
public class ConvertidorService {

    private final RestTemplate restTemplate;
    private static final List<String> MONEDAS_ACEPTADAS = Arrays.asList("EUR", "CLP", "BRL", "COP", "PEN", "GBP");
    public ConvertidorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double convertirMoneda(double monto, String monedaDestino) {

        if (!MONEDAS_ACEPTADAS.contains(monedaDestino)) {
            return monto;
        }
        // Construir el cuerpo de la solicitud en formato JSON
        String requestBody = "{\"moneda_destino\":\"" + monedaDestino + "\",\"importe\":" + monto + "}";


        // Configurar la entidad de la solicitud con el cuerpo
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

        // Realizar la solicitud POST a la API y obtener la respuesta
        String apiUrl = "http://34.82.105.125:8080/convertir";
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                JsonNode.class
        );

        // Obtener el cuerpo de la respuesta
        JsonNode responseBody = responseEntity.getBody();

        // Obtener los valores específicos
        double importe;
        if (responseBody != null) {
             importe = responseBody.get("importe").asDouble();
        }else {
            importe = monto;
        }

        return importe;
    }
}
