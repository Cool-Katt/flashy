package sofuni.flashy.service;

import org.joda.time.Instant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import sofuni.flashy.models.entities.CommentEntity;
import sofuni.flashy.models.serviceModels.CommentServiceModel;
import sofuni.flashy.repositories.CommentRepository;
import sofuni.flashy.services.CommentService;
import sofuni.flashy.services.impl.CommentServiceImpl;

import java.security.Principal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest
{
    private CommentService commentServiceTest;

    @Mock
    CommentRepository commentRepositoryMock;
    Principal principalMock = mock(Principal.class);


    @BeforeEach
    public void setup()
    {
        commentServiceTest = new CommentServiceImpl(commentRepositoryMock, new ModelMapper());
    }

    @Test
    public void testAllComments()
    {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setTimestamp(Instant.now());
        commentEntity.setComment("test");
        commentEntity.setLeftBy("tester");

        when(commentRepositoryMock.findAll()).thenReturn(List.of(commentEntity));

        List<CommentServiceModel> comments = commentServiceTest.allComments();

        Assertions.assertEquals(1, comments.size());
        Assertions.assertEquals(commentEntity.getComment(), comments.get(0).getComment());
        Assertions.assertEquals(commentEntity.getLeftBy(), comments.get(0).getLeftBy());
    }

    @Test
    public void testAddingComment()
    {
        when(principalMock.getName()).thenReturn("tester");
        when(commentRepositoryMock.saveAndFlush(any(CommentEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        CommentServiceModel commentModel = commentServiceTest.addComment("hello", principalMock);

        Assertions.assertEquals("hello", commentModel.getComment());
        Assertions.assertEquals("tester", commentModel.getLeftBy());
    }
}
