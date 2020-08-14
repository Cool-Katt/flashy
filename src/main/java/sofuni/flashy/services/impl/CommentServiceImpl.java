package sofuni.flashy.services.impl;

import org.joda.time.Instant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sofuni.flashy.models.entities.CommentEntity;
import sofuni.flashy.models.serviceModels.CommentServiceModel;
import sofuni.flashy.repositories.CommentRepository;
import sofuni.flashy.services.CommentService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService
{
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper)
    {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CommentServiceModel> allComments()
    {
        return this.commentRepository.findAll().stream()
                .map(c -> this.modelMapper.map(c, CommentServiceModel.class)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CommentServiceModel addComment(String comment, Principal principal)
    {
        CommentEntity newComment = new CommentEntity();
        newComment.setComment(comment);
        newComment.setTimestamp(Instant.now());
        newComment.setLeftBy(principal.getName().replaceAll("@\\w+", "@****"));
        return this.modelMapper.map(this.commentRepository.saveAndFlush(newComment), CommentServiceModel.class);
    }
}
