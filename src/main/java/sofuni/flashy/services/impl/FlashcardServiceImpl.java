package sofuni.flashy.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sofuni.flashy.config.BingConfig;
import sofuni.flashy.models.entities.FlashcardEntity;
import sofuni.flashy.models.entities.PlayerEntity;
import sofuni.flashy.models.serviceModels.FlashcardServiceModel;
import sofuni.flashy.repositories.FlashcardRepository;
import sofuni.flashy.repositories.PlayerRepository;
import sofuni.flashy.services.FlashcardService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlashcardServiceImpl implements FlashcardService
{
    private final FlashcardRepository flashcardRepository;
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final BingConfig bingConfig;

    public FlashcardServiceImpl(FlashcardRepository flashcardRepository, PlayerRepository playerRepository, ModelMapper modelMapper, BingConfig bingConfig)
    {
        this.flashcardRepository = flashcardRepository;
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.bingConfig = bingConfig;
    }

    @Override
    public FlashcardServiceModel addFlashcard(FlashcardServiceModel flashcardServiceModel, Principal principal)
    {
        FlashcardEntity flashcardEntity = this.modelMapper.map(flashcardServiceModel, FlashcardEntity.class);
        flashcardEntity.setImageUrl(this.bingConfig.findImageURL(flashcardServiceModel.getTitle()));
        this.playerRepository.findByEmail(principal.getName())
                .ifPresent(playerRepositoryByEmail -> playerRepositoryByEmail.getDeck().add(flashcardEntity));
        return this.modelMapper.map(this.flashcardRepository.saveAndFlush(flashcardEntity), FlashcardServiceModel.class);
    }

    @Override
    public List<FlashcardServiceModel> listAllCards()
    {
        return this.flashcardRepository.findAll().stream()
                .map(c -> this.modelMapper.map(c, FlashcardServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<FlashcardServiceModel> listPlayerCards(Principal principal)
    {
        PlayerEntity playerEntity = this.playerRepository.findByEmail(principal.getName()).orElse(null);
        if (playerEntity != null)
        {
            return playerEntity.getDeck().stream()
                    .map(l -> this.modelMapper.map(l, FlashcardServiceModel.class)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteCardByTitle(String title)
    {
        if (this.flashcardRepository.findByTitle(title).isPresent())
        {
            this.flashcardRepository.deleteByTitle(title);
        }
    }
}
