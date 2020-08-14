package sofuni.flashy.models.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.joda.time.Instant;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentServiceModel
{
    @NotNull
    @Length(min = 5, message = "You'r comment must be at least 5 characters!")
    private String comment;

    @NotNull
    @FutureOrPresent
    private Instant timestamp;

    @NotNull
    private String leftBy;
}
