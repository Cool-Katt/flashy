package sofuni.flashy.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sofuni.flashy.models.bindingModels.PlayerBindingModel;
import sofuni.flashy.models.serviceModels.PlayerServiceModel;
import sofuni.flashy.services.PlayerService;

import javax.validation.Valid;

@Controller
public class RegistrationController
{

    private final PlayerService playerService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(PlayerService playerService, ModelMapper modelMapper, PasswordEncoder passwordEncoder)
    {
        this.playerService = playerService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String showRegister(Model model)
    {
        model.addAttribute("formData", new PlayerBindingModel());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("formData") PlayerBindingModel playerBindingModel,
                           BindingResult bindingResult)
    {

        if (bindingResult.hasErrors())
        {
            return "registration";
        }

        if (this.playerService.findPlayer(playerBindingModel.getEmail()) != null)
        {
            bindingResult.rejectValue("email", "error.email", "An account with this email already exists.");
            return "registration";
        }

        if (!playerBindingModel.getPassword().equals(playerBindingModel.getPasswordConfirm()))
        {
            return "registration";
        }

        PlayerServiceModel playerServiceModel = this.modelMapper.map(playerBindingModel, PlayerServiceModel.class);
        playerServiceModel.setPasswordHash(this.passwordEncoder.encode(playerBindingModel.getPassword()));

        this.playerService.registerPlayer(playerServiceModel);

        return "redirect:/login";
    }
}
