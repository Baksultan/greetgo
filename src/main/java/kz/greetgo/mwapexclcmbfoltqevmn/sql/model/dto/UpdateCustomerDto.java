package kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerDto {

    private String fullName;
    private int birthYear;
    private String phoneNumber;
    private String secondPhoneNumber;


}
