package sofuni.flashy.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import sofuni.flashy.config.BingConfig;
import sofuni.flashy.models.entities.CommentEntity;
import sofuni.flashy.models.entities.FlashcardEntity;
import sofuni.flashy.models.serviceModels.FlashcardServiceModel;
import sofuni.flashy.repositories.FlashcardRepository;
import sofuni.flashy.repositories.PlayerRepository;
import sofuni.flashy.services.FlashcardService;
import sofuni.flashy.services.impl.FlashcardServiceImpl;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlashcardServiceTest
{
    private FlashcardService flashcardServiceTest;

    @Mock
    FlashcardRepository flashcardRepositoryMock;
    PlayerRepository playerRepositoryMock;
    Principal principalMock = mock(Principal.class);

    @BeforeEach
    public void setup()
    {
        flashcardServiceTest = new FlashcardServiceImpl(flashcardRepositoryMock, playerRepositoryMock, new ModelMapper(), new BingConfig());
    }

    @Test
    public void testAddCard()
    {
        FlashcardServiceModel flash = new FlashcardServiceModel();
        flash.setTitle("sample");
        flash.setDescription("testing this");

        when(principalMock.getName()).thenReturn("tester");
        when(flashcardRepositoryMock.saveAndFlush(any(FlashcardEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(playerRepositoryMock.findByEmail(any())).thenReturn(null);

        FlashcardServiceModel model = flashcardServiceTest.addFlashcard(flash, principalMock);

        Assertions.assertEquals(flash.getTitle(), model.getTitle());
        Assertions.assertNotEquals("", model.getImageURL());
    }
}
