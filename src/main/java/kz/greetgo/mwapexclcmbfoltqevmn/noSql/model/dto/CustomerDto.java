package kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private String fullName;
    private int birthYear;
    private String phoneNumber;
    private String secondPhoneNumber;
    private LocalDateTime creationDate;
}
