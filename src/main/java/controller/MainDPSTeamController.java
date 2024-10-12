package controller;

import model.MainDPSTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.MainDPSTeamRepository;
import java.util.List;

/**
 * Controller class responsible for handling requests related to mainDPS teams.
 */
@Controller
@RequestMapping("/api/maindpsteams")
public class MainDPSTeamController {

    @Autowired
    private MainDPSTeamRepository mainDPSTeamRepository;

}
