package com.nagarro.javaMiniAssignment2.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.nagarro.javaMiniAssignment2.entity.User;
import com.nagarro.javaMiniAssignment2.exception.UserCustomException;
import com.nagarro.javaMiniAssignment2.factory.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static com.nagarro.javaMiniAssignment2.utility.constants.Constants.TO_BE_VERIFIED;
import static com.nagarro.javaMiniAssignment2.utility.constants.Constants.VERIFIED;
import static com.nagarro.javaMiniAssignment2.utility.constants.JsonNodeUtils.*;


@Component
@Slf4j
public class UserApiProcessor {


    private final WebClient randomUserWebClient;
    private final WebClient nationalizeWebClient;
    private final WebClient genderizeWebClient;




    public UserApiProcessor(@Qualifier("randomUserWebClient") WebClient randomUserWebClient,
                       @Qualifier("nationalizeWebClient") WebClient nationalizeWebClient,
                       @Qualifier("genderizeWebClient") WebClient genderizeWebClient
                            ) {
        this.randomUserWebClient = randomUserWebClient;
        this.nationalizeWebClient = nationalizeWebClient;
        this.genderizeWebClient = genderizeWebClient;

    }


    CompletableFuture<List<User>> fetchAndSaveRandomUsers() {
        return CompletableFuture.supplyAsync(() -> {
            String api1Response = randomUserWebClient.get()
                    .uri("/api/")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("API 1 Response: " + api1Response);
            List<User> users = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode api1Data;
            try {
                api1Data = objectMapper.readValue(api1Response, JsonNode.class);
                if (api1Data.has("results") && api1Data.get("results").isArray()) {
                    ArrayNode resultsArray = (ArrayNode) api1Data.get("results");
                    for (JsonNode resultNode : resultsArray) {
                        User user = new User();
                        user.setName(getFullName(resultNode));  // Use full name
                        user.setFirstName(getFirstName(resultNode));
                        user.setGender(getGender(resultNode));
                        user.setDob(getDob(resultNode));
                        user.setAge(getAge(resultNode));
                        user.setNationality(getNationality(resultNode));
                        System.out.println("Age extracted from API: " + getAge(resultNode));
                        users.add(user);
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
            return users;
        });
    }

    CompletableFuture<Boolean> checkNationalityAsync(User user) {
        return CompletableFuture.supplyAsync(() -> {
            String api2Response = nationalizeWebClient.get()
                    .uri("/?name=" + user.getFirstName())  // Use full name
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("API 2 Response: " + api2Response);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode api2Data;
            try {
                api2Data = objectMapper.readValue(api2Response, JsonNode.class);
                JsonNode node = api2Data.get("country");
                for (JsonNode countryNode : node) {
                    String countryId = countryNode.get("country_id").asText();
                    System.out.println("printing the nationality check result : "+countryId.equalsIgnoreCase(user.getNationality()));
                    if (countryId.equalsIgnoreCase(user.getNationality())) {
                        return true;
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
            return false;
        });
    }

    CompletableFuture<Boolean> checkGenderAsync(User user) {
        return CompletableFuture.supplyAsync(() -> {
            String api3Response = genderizeWebClient.get()
                    .uri("/?name=" + user.getFirstName())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("API 3 Response: " + api3Response);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode api3Data;
            try {
                api3Data = objectMapper.readValue(api3Response, JsonNode.class);
                JsonNode genderNode = api3Data.get("gender");

                if (genderNode != null && !genderNode.isNull()) {
                    String gender = genderNode.asText();
                    System.out.println("printing the gender check result : " + gender.equals(user.getGender()));
                    return gender.equals(user.getGender());
                } else {

                    return false;
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }






    void validateParametre(String sortType, String sortOrder, int limit, int offset) throws UserCustomException {
        ValidatorFactory.getValidator(sortType).validateAlphabet(sortType);
        ValidatorFactory.getValidator(sortOrder).validateAlphabet(sortOrder);
        ValidatorFactory.getValidator(limit).validateNumericLimit(limit);
        ValidatorFactory.getValidator(offset).validateNumericOffset(offset);
    }
}
