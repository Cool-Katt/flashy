package sofuni.flashy.models.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerBindingModel
{
    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 3, message = "Password should be more than 3 characters!")
    private String password;

    private String passwordConfirm;
}
