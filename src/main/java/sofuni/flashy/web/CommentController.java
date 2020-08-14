package sofuni.flashy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sofuni.flashy.config.IsUser;
import sofuni.flashy.models.bindingModels.FlashcardBindingModel;
import sofuni.flashy.models.serviceModels.CommentServiceModel;
import sofuni.flashy.services.CommentService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/comments")
public class CommentController
{
    private final CommentService commentService;

    public CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }

    @IsUser
    @GetMapping("/list")
    public String listAllComments(Model model)
    {
        model.addAttribute("comments", this.commentService.allComments());
        return "list-comments";
    }

    @IsUser
    @GetMapping("/add")
    public String showCommentAdd(Model model)
    {
        model.addAttribute("comment", new CommentServiceModel());
        return "new-comment";
    }

    @IsUser
    @PostMapping("/add")
    public String addComment(@Valid @ModelAttribute(name = "comment") CommentServiceModel commentServiceModel,
                             BindingResult bindingResult, Principal principal)
    {
        this.commentService.addComment(commentServiceModel.getComment(), principal);
        if (bindingResult.hasErrors())
        {
            return "redirect:/comments/add";
        }

        return "redirect:/comments/list-comments";
    }
}
