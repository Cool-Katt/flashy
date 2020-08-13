package sofuni.flashy.models.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlashcardBindingModel
{
    @NotNull
    @Length(min = 3, message = "Title should be more between 3 and 25 characters!")
    private String title;

    @NotNull
    @Length(min = 10, message = "Description should be more than 10 characters!")
    private String description;

    private String imageURL;
}
