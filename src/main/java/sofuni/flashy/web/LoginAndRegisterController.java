package sofuni.flashy.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import sofuni.flashy.models.bindingModels.PlayerBindingModel;
import sofuni.flashy.models.serviceModels.PlayerServiceModel;
import sofuni.flashy.services.PlayerService;

import javax.validation.Valid;

@Controller
public class LoginAndRegisterController
{
    private final PlayerService playerService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginAndRegisterController(PlayerService playerService, ModelMapper modelMapper, PasswordEncoder passwordEncoder)
    {
        this.playerService = playerService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLogin(Model model)
    {
        model.addAttribute("formData", new PlayerBindingModel());
        return "login";
    }

    @PostMapping("/login")
    private String login(@Valid @ModelAttribute("formData") PlayerBindingModel playerBindingModel,
                         BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "redirect:/login";
        }

        PlayerServiceModel playerServiceModel = this.modelMapper.map(playerBindingModel, PlayerServiceModel.class);
        if (this.playerService.loginPlayer(playerServiceModel) != null)
        {
            return "index";
        } else
        {
            return "login";
        }
    }

    @PostMapping("/login-error")
    public ModelAndView onLoginError(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.
                    SPRING_SECURITY_FORM_USERNAME_KEY) String email
    )
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", "bad.credentials");
        modelAndView.addObject("username", email);
        modelAndView.setViewName("login");

        return modelAndView;
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
