package com.science_kit.science_kit_bot.rust;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;

import java.util.Map;

@Service
public class RustChemistryClient {

    private final RestClient restClient;

    public RustChemistryClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://127.0.0.1:8081")
                .build();
    }

    public RustClientDto.MolarMassResponse calculateMolarMass(String formula, Map<String, Double> atomicMasses) {
        RustClientDto.MolarMassRequest request = new RustClientDto.MolarMassRequest(formula, atomicMasses);

        return restClient.post()
                .uri("/api/chemistry/molar-mass")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(RustClientDto.MolarMassResponse.class);
    }
}