package sofuni.flashy.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sofuni.flashy.models.bindingModels.FlashcardBindingModel;
import sofuni.flashy.models.serviceModels.FlashcardServiceModel;
import sofuni.flashy.services.FlashcardService;
import sofuni.flashy.services.PlayerService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/flashcard")
public class FlashcardController
{
    private final FlashcardService flashcardService;
    private final ModelMapper modelMapper;

    public FlashcardController(FlashcardService flashcardService, ModelMapper modelMapper)
    {
        this.flashcardService = flashcardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String showAddCard(Model model)
    {
        model.addAttribute("formData", new FlashcardBindingModel());
        return "new";
    }

    @PostMapping("/add")
    private String addCard(@Valid @ModelAttribute("formData") FlashcardBindingModel flashcardBindingModel,
                           BindingResult bindingResult, Principal principal)
    {
        if (bindingResult.hasErrors())
        {
            return "redirect:/add";
        }
        FlashcardServiceModel flashcardServiceModel = this.modelMapper.map(flashcardBindingModel, FlashcardServiceModel.class);
        if (this.flashcardService.addFlashcard(flashcardServiceModel, principal) != null)
        {
            return "index";
        } else
        {
            return "new";
        }
    }

    @GetMapping("/list")
    private String listAll(Model model)
    {
        model.addAttribute("formDataAll", this.flashcardService.listAllCards());
        return "list";
    }

    @GetMapping("/list-player")
    private String listPlayerCards(Model model, Principal principal)
    {
        List<FlashcardBindingModel> list = this.flashcardService.listPlayerCards(principal).stream()
                .map(l -> this.modelMapper.map(l, FlashcardBindingModel.class)).collect(Collectors.toList());
        model.addAttribute("formDataAll", list.get((int) (Math.random()*list.size())));
        return "list";
    }
}
