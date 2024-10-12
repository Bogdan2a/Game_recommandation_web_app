package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Character;
import model.SavedTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.CharacterRepository;
import repository.SavedTeamRepository;
import java.util.List;

/**
 * Controller class responsible for handling requests related to user teams.
 */
@Controller
@RequestMapping("/api/userteams")
public class SavedTeamController {

    @Autowired
    private SavedTeamRepository savedTeamRepository;

    @Autowired
    private CharacterRepository characterRepository;

    /**
     * Retrieves the saved teams for the logged-in user and renders them on the saved_teams page.
     *
     * @param model The model to be populated with data.
     * @return The name of the saved_teams HTML file.
     * @throws JsonProcessingException If there is an error during JSON processing.
     */
    @GetMapping("/saved_teams.html")
    public String getUserSavedTeams(Model model) throws JsonProcessingException {
        Long loggedUser = SecurityVariable.loggedUserId;
        List<SavedTeam> savedTeams = savedTeamRepository.findAllByUser_id(loggedUser);
        List<Character> characters = characterRepository.findAll();

        // Serialize savedTeams and characters objects to JSON strings
        ObjectMapper objectMapper = new ObjectMapper();
        String savedTeamsJson = objectMapper.writeValueAsString(savedTeams);
        String charactersJson = objectMapper.writeValueAsString(characters);

        model.addAttribute("savedTeams", savedTeamsJson);
        model.addAttribute("characters", charactersJson);

        return "saved_teams";
    }

    /**
     * Saves the current user team to the database.
     *
     * @param selectedCharacterId The ID of the selected character.
     * @return A redirection URL to the saved_teams page.
     */
    @PostMapping("/saveCurrentTeam")
    public String saveCurrentTeam(@RequestBody String selectedCharacterId) {
        SavedTeam savedTeam = new SavedTeam();
        savedTeam.setUser_id(SecurityVariable.loggedUserId);
        savedTeam.setMaindpscharacter_id(SecurityVariable.mainDPSId);
        savedTeam.setSubcharacter1(SecurityVariable.subCharacterId1);
        savedTeam.setSubcharacter2(SecurityVariable.subCharacterId2);
        savedTeam.setSubcharacter3(SecurityVariable.subCharacterId3);
        savedTeamRepository.save(savedTeam);

        return "redirect:/api/userteams/saved_teams.html";
    }

}
