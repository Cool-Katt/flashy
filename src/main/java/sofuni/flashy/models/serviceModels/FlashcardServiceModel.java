package sofuni.flashy.models.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlashcardServiceModel
{
    private String title;
    private String description;
    private String imageURL;
}
