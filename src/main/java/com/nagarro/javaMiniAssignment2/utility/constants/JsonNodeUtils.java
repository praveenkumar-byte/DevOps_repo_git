package com.nagarro.javaMiniAssignment2.utility.constants;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonNodeUtils {

    public static String getFullName(JsonNode resultNode) {
        String firstName = resultNode.path("name").path("first").asText();
        String lastName = resultNode.path("name").path("last").asText();
        return firstName + " " + lastName;
    }
    public static String getFirstName(JsonNode resultNode) {
        String firstName = resultNode.path("name").path("first").asText();
        return firstName ;
    }
    public static int getAge(JsonNode resultNode) {
        return resultNode.path("dob").path("age").asInt();
    }

//    public static int getAge(JsonNode resultNode) {
//        return resultNode.path("dob").path("age").asInt();
//    }
    public static String getDob(JsonNode resultNode) {
        return resultNode.path("dob").path("date").asText();
    }

    public static String getGender(JsonNode resultNode) {
        JsonNode genderNode = resultNode.path("gender");
        return genderNode.isNull() ? null : genderNode.asText();
    }
    public static String getNationality(JsonNode resultNode) {
        JsonNode nationalityNode = resultNode.path("nat");
        return nationalityNode.isNull() ? null : nationalityNode.asText();
    }

}
