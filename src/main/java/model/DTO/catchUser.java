package model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class catchUser {
   @Pattern(regexp = "[0-9a-zA-Z]{1,15}", message = "帳號只能是數字與英文且少於15個字")
   String account;

   @Pattern(regexp = "[0-9a-zA-Z]{1,15}" , message = "密碼只能是數字與英文且少於15個字")
   String password;
}
