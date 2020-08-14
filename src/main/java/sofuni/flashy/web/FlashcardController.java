package sofuni.flashy.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sofuni.flashy.config.IsAdmin;
import sofuni.flashy.config.IsUser;
import sofuni.flashy.models.bindingModels.FlashcardBindingModel;
import sofuni.flashy.models.serviceModels.FlashcardServiceModel;
import sofuni.flashy.services.FlashcardService;

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

    @IsUser
    @GetMapping("/add")
    public String showAddCard(Model model)
    {
        model.addAttribute("formData", new FlashcardBindingModel());
        return "new";
    }

    @IsUser
    @PostMapping("/add")
    public String addCard(@Valid @ModelAttribute("formData") FlashcardBindingModel flashcardBindingModel,
                           BindingResult bindingResult, Principal principal)
    {
        if (bindingResult.hasErrors())
        {
            return "redirect:/add";
        }
        FlashcardServiceModel flashcardServiceModel = this.modelMapper.map(flashcardBindingModel, FlashcardServiceModel.class);
        if (this.flashcardService.addFlashcard(flashcardServiceModel, principal) != null)
        {
            return "redirect:/list";
        } else
        {
            return "new";
        }
    }

    @IsUser
    @GetMapping("/list")
    public String listAll(Model model)
    {
        model.addAttribute("formDataAll", this.flashcardService.listAllCards());
        return "list";
    }

    @IsUser
    @GetMapping("/list-player")
    public String listPlayerCards(Model model, Principal principal)
    {
        List<FlashcardBindingModel> list = this.flashcardService.listPlayerCards(principal).stream()
                .map(l -> this.modelMapper.map(l, FlashcardBindingModel.class)).collect(Collectors.toList());
        if (list.size() > 0)
        {
            model.addAttribute("formDataAll", list.get((int) (Math.random() * list.size())));
        } else
        {
            //todo: tell user to add cards first
        }
        return "redirect:/flashcard/list";
    }

    @IsAdmin
    @DeleteMapping("/delete")
    public String delete(@ModelAttribute(name = "deleteId") String deleteId)
    {
        this.flashcardService.deleteCardByTitle(deleteId);
        return "redirect:/flashcard/list";
    }
}