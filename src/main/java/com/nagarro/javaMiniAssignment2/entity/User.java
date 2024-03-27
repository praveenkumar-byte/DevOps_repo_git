package com.nagarro.javaMiniAssignment2.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String name;
    @Transient
    @JsonIgnore
    private String firstName;

    private String gender;
    private String dob;
    private int age;
    private String nationality;
    private String verificationStatus;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;


}
