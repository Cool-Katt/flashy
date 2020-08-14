package sofuni.flashy.services;

import sofuni.flashy.models.serviceModels.CommentServiceModel;

import java.security.Principal;
import java.util.List;

public interface CommentService
{
    List<CommentServiceModel> allComments();

    CommentServiceModel addComment(String comment, Principal principal);
}
