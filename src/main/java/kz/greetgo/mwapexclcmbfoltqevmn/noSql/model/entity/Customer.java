package kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Customer {

    @Id
    private String id;

    private String fullName;

    private int birthYear;

    @Indexed(unique = true)
    private String phoneNumber;

    @Indexed(unique = true)
    private String secondPhoneNumber;

    private LocalDateTime creationDate;
}
