package controller;

import model.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.ReactionRepository;
import java.util.List;

/**
 * Controller class responsible for handling requests related to reactions.
 */
@Controller
@RequestMapping("/api/reactions")
public class ReactionController {

    @Autowired
    private ReactionRepository reactionRepository;

}
