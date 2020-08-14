package sofuni.flashy.services;

import sofuni.flashy.models.serviceModels.FlashcardServiceModel;

import java.security.Principal;
import java.util.List;

public interface FlashcardService
{
    FlashcardServiceModel addFlashcard(FlashcardServiceModel flashcardServiceModel, Principal principal);

    List<FlashcardServiceModel> listAllCards();

    List<FlashcardServiceModel> listPlayerCards(Principal principal);

    void deleteCardByTitle(String title);
}
