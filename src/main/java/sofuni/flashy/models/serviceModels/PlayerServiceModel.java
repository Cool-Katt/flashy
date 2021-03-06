package sofuni.flashy.models.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerServiceModel
{
    private String email;
    private String passwordHash;
}
