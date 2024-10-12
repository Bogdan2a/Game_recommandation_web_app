package controller;

import model.*;
import model.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.*;
import service.UserServiceImpl;

import java.util.*;

/**
 * Controller class responsible for handling requests related to characters.
 */
@Controller
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MainDPSTeamRepository mainDPSTeamRepository;

    @Autowired
    private ResonanceRepository resonanceRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private SavedTeamRepository savedTeamRepository;

    /**
     * Retrieves all characters and renders them on the characters page.
     *
     * @param model The model to be populated with data.
     * @return The name of the characters HTML file.
     */
    @GetMapping("/characters.html")
    public String getAllCharacters(Model model) {
        List<Character> characters = characterRepository.findAll();
        model.addAttribute("characters", characters);
        model.addAttribute("elementIcons", getElementIcons());

        return "characters";
    }

    /**
     * Retrieves the icons for different elements.
     *
     * @return A map containing element names as keys and their respective icon URLs as values.
     */
    private Map<String, String> getElementIcons() {
        Map<String, String> elementIcons = new HashMap<>();
        elementIcons.put("Pyro", "https://rerollcdn.com/GENSHIN/Elements/Element_Pyro.png");
        elementIcons.put("Cryo", "https://rerollcdn.com/GENSHIN/Elements/Element_Cryo.png");
        elementIcons.put("Electro", "https://rerollcdn.com/GENSHIN/Elements/Element_Electro.png");
        elementIcons.put("Hydro", "https://rerollcdn.com/GENSHIN/Elements/Element_Hydro.png");
        elementIcons.put("Anemo", "https://rerollcdn.com/GENSHIN/Elements/Element_Anemo.png");
        elementIcons.put("Geo", "https://rerollcdn.com/GENSHIN/Elements/Element_Geo.png");
        elementIcons.put("Dendro", "https://rerollcdn.com/GENSHIN/Elements/Element_Dendro.png");

        return elementIcons;
    }

    /**
     * Retrieves the icons for different weapon types.
     *
     * @return A map containing weapon types as keys and their respective icon URLs as values.
     */
    private Map<String, String> getWeaponIcons() {
        Map<String, String> weaponIcons = new HashMap<>();
        weaponIcons.put("Greatsword", "https://rerollcdn.com/GENSHIN/UI/weapon_claymore.png");
        weaponIcons.put("Sword", "https://rerollcdn.com/GENSHIN/UI/weapon_sword.png");
        weaponIcons.put("Catalyst", "https://rerollcdn.com/GENSHIN/UI/weapon_catalyst.png");
        weaponIcons.put("Polearm", "https://rerollcdn.com/GENSHIN/UI/weapon_polearm.png");
        weaponIcons.put("Bow", "https://rerollcdn.com/GENSHIN/UI/weapon_bow.png");
        weaponIcons.put("Geo", "https://rerollcdn.com/GENSHIN/Elements/Element_Geo.png");
        weaponIcons.put("Dendro", "https://rerollcdn.com/GENSHIN/Elements/Element_Dendro.png");

        return weaponIcons;
    }

    /**
     * Retrieves the icons for different artifact pieces.
     *
     * @return A map containing artifact piece names as keys and their respective icon URLs as values.
     */
    private Map<String, String> getArtifactPieceIcons() {
        Map<String, String> artifactPiece = new HashMap<>();
        artifactPiece.put("Sands", "https://img.game8.co/3289904/c798c39d2657d5f39f6f635ef6f0fb2a.png/show");
        artifactPiece.put("Goblet", "https://img.game8.co/3289902/0d8d707f500e8268f3b032de4903b286.png/show");
        artifactPiece.put("Circlet", "https://img.game8.co/3289906/c9371276ffd85379144d8c034683abc1.png/show");

        return artifactPiece;
    }

    /**
     * Retrieves the icons for different artifact sets based on character data.
     *
     * @return A map containing artifact set names as keys and their respective icon URLs as values.
     */
    private Map<String,String> getArtifactSetIcons(){
        //fetch from database the name of the artifact set from each character and add to a list if it's not already added
        Map<String,String> artifactSets = new HashMap<>();
        List<Character> characters = characterRepository.findAll();
        for(Character character: characters){
            if(!artifactSets.containsKey(character.getArtifact_set())){
                //concatenate to form the link and make all letters lowercase
                String link = "https://rerollcdn.com/GENSHIN/Gear/" + character.getArtifact_set().toLowerCase().replace(" ","_") + ".png";
                artifactSets.put(character.getArtifact_set(),link);
            }
        }
        return artifactSets;
    }

    /**
     * Retrieves the icons for recommended weapons based on character data.
     *
     * @return A map containing recommended weapon names as keys and their respective icon URLs as values.
     */
    private Map<String,String> getRecommendedWeaponIcons(){
        //fetch from database the name of the recommended weapon from each character and add to a list if it's not already added
        Map<String,String> recommendedWeapons = new HashMap<>();
        List<Character> characters = characterRepository.findAll();
        for(Character character: characters){
            if(!recommendedWeapons.containsKey(character.getRecommended_weapon())){
                //concatenate to form the link and make all letters lowercase
                String link = "https://rerollcdn.com/GENSHIN/Weapons/" + character.getRecommended_weapon().replace(" ","_") + ".png";
                recommendedWeapons.put(character.getRecommended_weapon(),link);
            }
        }
        return recommendedWeapons;
    }

    /**
     * Displays a page for selecting the main DPS character.
     *
     * @param model The model to be populated with data.
     * @return The name of the selectMainDPS HTML file.
     */
    @GetMapping("/selectMainDPS.html")
    public String selectMainDPS(Model model) {
        SecurityVariable.mainDPSId = null;
        SecurityVariable.subCharacterId1 = null;
        SecurityVariable.subCharacterId2 = null;
        SecurityVariable.subCharacterId3 = null;

        User loggedUser = userRepository.findById(SecurityVariable.loggedUserId).orElse(null);
        assert loggedUser != null;
        // Refetch user to ensure fresh data
        loggedUser = userRepository.findById(SecurityVariable.loggedUserId).orElse(null);
        String loggedUserOwnedCharacterIds = loggedUser.getOwned_characters_ids();

        // Parse CharacterIds
        List<Long> ownedCharacterIds = parseCharacterIds(loggedUserOwnedCharacterIds);

        // Based on the list of Ids create a list of Character type objects
        List<Character> ownedCharacters = new ArrayList<>();
        for (Long id : ownedCharacterIds) {
            Character character = characterRepository.findById(id).orElse(null);
            if (character != null && character.getRole().equals("MainDPS")) {
                ownedCharacters.add(character);
            }
        }
        model.addAttribute("characters", ownedCharacters);
        return "selectMainDPS";
    }

    /**
     * Confirms the selection of the main DPS character.
     *
     * @param selectedCharacterId The ID of the selected character.
     * @return A redirection URL to the recommended_team page.
     */
    @PostMapping("/confirmMainDPS")
    public String confirmMainDPS(@RequestBody String selectedCharacterId) {
        Long mainDPSId = Long.parseLong(selectedCharacterId);
      //  System.out.println("Selected Main DPS ID: " + selectedCharacterId);
        SecurityVariable.mainDPSId = mainDPSId;
        Character mainDPS = characterRepository.findById(mainDPSId).orElse(null);
       // System.out.println("Main DPS: " + mainDPS.getName());
        return "redirect:/api/characters/recommended_team.html";
    }

    /**
     * Displays a page showing the recommended team composition.
     *
     * @param model The model to be populated with data.
     * @return The name of the recommended_team HTML file.
     */
    @GetMapping("/recommended_team.html")
    public String recommendedTeam(Model model) {
        User loggedUser = userRepository.findById(SecurityVariable.loggedUserId).orElse(null);
        assert loggedUser != null;
        // Refetch user to ensure fresh data
        loggedUser = userRepository.findById(SecurityVariable.loggedUserId).orElse(null);
        assert loggedUser != null;
        String loggedUserOwnedCharacterIds = loggedUser.getOwned_characters_ids();

        // Parse CharacterIds
        List<Long> ownedCharacterIds = parseCharacterIds(loggedUserOwnedCharacterIds);

        // Based on the list of Ids create a list of Character type objects
        List<Character> ownedCharacters = new ArrayList<>();
        for (Long id : ownedCharacterIds) {
            Character character = characterRepository.findById(id).orElse(null);
            if (character != null) {
                ownedCharacters.add(character);
            }
        }
        List<Character> recommendedTeam = new ArrayList<>();
        Long mainDPSId = SecurityVariable.mainDPSId;
        Character mainDPS = characterRepository.findById(mainDPSId).orElse(null);
        recommendedTeam.add(mainDPS);
        boolean sub1 = false;
        boolean sub2 = false;
        boolean sub3 = false;
        // fetch from the recommended_teams database the team of the mainDPS each character separately
        Long subCharacter1Id = mainDPSTeamRepository.findSubCharacter1IdByMainDPSId(mainDPSId);
        Long subCharacter2Id = mainDPSTeamRepository.findSubCharacter2IdByMainDPSId(mainDPSId);
        Long subCharacter3Id = mainDPSTeamRepository.findSubCharacter3IdByMainDPSId(mainDPSId);
        Character subCharacter1 = characterRepository.findById(subCharacter1Id).orElse(null);
        Character subCharacter2 = characterRepository.findById(subCharacter2Id).orElse(null);
        Character subCharacter3 = characterRepository.findById(subCharacter3Id).orElse(null);
        assert subCharacter1 != null;
        String element1 = subCharacter1.getElement();
        assert subCharacter2 != null;
        String element2 = subCharacter2.getElement();
        assert subCharacter3 != null;
        String element3 = subCharacter3.getElement();
        // best case all characters recommended are also owned
        // if sucCharacter fetched from db is in ownedCharacters add to recommended team
        if (ownedCharacters.contains(subCharacter1)) {
            recommendedTeam.add(subCharacter1);
            sub1 = true;
        }
        if (ownedCharacters.contains(subCharacter2)) {
            recommendedTeam.add(subCharacter2);
            sub2 = true;
        }
        if (ownedCharacters.contains(subCharacter3)) {
            recommendedTeam.add(subCharacter3);
            sub3 = true;
        }
        if (recommendedTeam.size() == 4) {
            model.addAttribute("characters", recommendedTeam);
        }
        // if not all best characters are owned try for characters with the same element to substitute
        else {
            for (Character character : ownedCharacters) {
                if (character.getElement().equals(element1) && !sub1 && !recommendedTeam.contains(character)) {
                    recommendedTeam.add(character);
                    sub1 = true;
                }
                if (character.getElement().equals(element2) && !sub2 && !recommendedTeam.contains(character)) {
                    recommendedTeam.add(character);
                    sub2 = true;
                }
                if (character.getElement().equals(element3) && !sub3 && !recommendedTeam.contains(character)) {
                    recommendedTeam.add(character);
                    sub3 = true;
                }
            }
            if (recommendedTeam.size() == 4) {
                model.addAttribute("characters", recommendedTeam);

            } // even worse case when we don't find even with at least the same element try searching for any sub-dps/support
            else if (recommendedTeam.size() < 4) {
                for (Character character : ownedCharacters) {
                    if (recommendedTeam.size()!=4 && !recommendedTeam.contains(character) && (character.getRole().equals("SubDPS") || character.getRole().equals("Support"))) {
                        recommendedTeam.add(character);
                    }
                }
                if (recommendedTeam.size() == 4) {
                    model.addAttribute("characters", recommendedTeam);
                } else if (recommendedTeam.size() < 4) {
                    // if we still don't have 4 characters add randomly from owned characters
                    for (Character character : ownedCharacters) {
                        if (recommendedTeam.size()!=4 && !recommendedTeam.contains(character)) {
                            recommendedTeam.add(character);
                        }
                    }
                    if (recommendedTeam.size() == 4) {
                        model.addAttribute("characters", recommendedTeam);
                    }
                }
            }
        }


        List<Resonance> allResonances = resonanceRepository.findAll();
        List<Resonance> activeResonances = new ArrayList<>();
        List<Reaction> allReactions = reactionRepository.findAll();
        List<Reaction> possibleReactions = new ArrayList<>();
        // for each character in the recommended team fetch the element
        String finalTeamElement1 = recommendedTeam.get(0).getElement();
        String finalTeamElement2 = recommendedTeam.get(1).getElement();
        String finalTeamElement3 = recommendedTeam.get(2).getElement();
        String finalTeamElement4 = recommendedTeam.get(3).getElement();
        // if 2 or more elements are the same add the resonance with the name of that element
        // check 2 by 2 if the elements are the same and add the resonance with the name of that element
        //for example if finalTeamElement1 and finalTeamElement2 are both Pyro add the resonance with the name Pyro
        if(finalTeamElement1.equals(finalTeamElement2)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement1));
        }
        if(finalTeamElement1.equals(finalTeamElement3)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement1));
        }
        if(finalTeamElement1.equals(finalTeamElement4)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement1));
        }
        if(finalTeamElement2.equals(finalTeamElement3)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement2));
        }
        if(finalTeamElement2.equals(finalTeamElement4)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement2));
        }
        if(finalTeamElement3.equals(finalTeamElement4)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement3));
        }
        if(activeResonances.isEmpty()){
            activeResonances.add(resonanceRepository.findByName("Universal"));
        }
        //remove duplicates from the list
        activeResonances = new ArrayList<>(new HashSet<>(activeResonances));
        // make a list of the 4 elements
        List<String> elements = new ArrayList<>();
        elements.add(finalTeamElement1);
        elements.add(finalTeamElement2);
        elements.add(finalTeamElement3);
        elements.add(finalTeamElement4);
        // for each element in the list check if it has a reaction with the other elements
        // check for presence of Pyro in the list
        if(elements.contains("Pyro")){
            for(String element: elements){
                if(element.equals("Hydro")){
                    possibleReactions.add(reactionRepository.findByName("Vaporize"));
                }
                if(element.equals("Cryo")){
                    possibleReactions.add(reactionRepository.findByName("Melt"));
                }
                if(element.equals("Electro")){
                    possibleReactions.add(reactionRepository.findByName("Overloaded"));
                }
                if(element.equals("Dendro")){
                    possibleReactions.add(reactionRepository.findByName("Burning"));
                }
                if(element.equals("Geo")){
                    possibleReactions.add(reactionRepository.findByName("Crystallize"));
                }
                if(element.equals("Anemo")){
                    possibleReactions.add(reactionRepository.findByName("Swirl"));
                }
            }
        }
        if(elements.contains("Hydro")){
            for(String element: elements){
                if(element.equals("Cryo")){
                    possibleReactions.add(reactionRepository.findByName("Frozen"));
                    possibleReactions.add(reactionRepository.findByName("Shattered"));
                }
                if(element.equals("Electro")){
                    possibleReactions.add(reactionRepository.findByName("Electro-Charged"));
                }
                if(element.equals("Dendro")){
                    possibleReactions.add(reactionRepository.findByName("Bloom"));
                }
                if(element.equals("Geo")){
                    possibleReactions.add(reactionRepository.findByName("Crystallize"));
                }
                if(element.equals("Anemo")){
                    possibleReactions.add(reactionRepository.findByName("Swirl"));
                }
            }
        }
        if(elements.contains("Cryo")){
            for(String element: elements){

                if(element.equals("Electro")){
                    possibleReactions.add(reactionRepository.findByName("Superconduct"));
                }
                if(element.equals("Geo")){
                    possibleReactions.add(reactionRepository.findByName("Crystallize"));
                }
                if(element.equals("Anemo")){
                    possibleReactions.add(reactionRepository.findByName("Swirl"));
                }
            }
        }
        if(elements.contains("Electro")){
            for(String element: elements){
                if(element.equals("Dendro")){
                    possibleReactions.add(reactionRepository.findByName("Quicken"));
                    possibleReactions.add(reactionRepository.findByName("Aggravate"));
                    possibleReactions.add(reactionRepository.findByName("Spread"));
                }
                if(element.equals("Geo")){
                    possibleReactions.add(reactionRepository.findByName("Crystallize"));
                }
                if(element.equals("Anemo")){
                    possibleReactions.add(reactionRepository.findByName("Swirl"));
                }
            }
        }
        if(elements.contains("Electro") && elements.contains("Hydro") && elements.contains("Dendro")){
            possibleReactions.add(reactionRepository.findByName("Hyperbloom"));
        }
        if(elements.contains("Pyro") && elements.contains("Hydro") && elements.contains("Dendro")){
            possibleReactions.add(reactionRepository.findByName("Burgeon"));
        }


        //remove duplicates from the list
        possibleReactions = new ArrayList<>(new HashSet<>(possibleReactions));

        model.addAttribute("elementIcons", getElementIcons());
        model.addAttribute("weaponIcons", getWeaponIcons());
        model.addAttribute("artifactPieceIcons", getArtifactPieceIcons());
        model.addAttribute("artifactSetIcons", getArtifactSetIcons());
        model.addAttribute("recommendedWeaponIcons", getRecommendedWeaponIcons());
        model.addAttribute("resonances", activeResonances);
        model.addAttribute("reactions", possibleReactions);

        SecurityVariable.subCharacterId1 = recommendedTeam.get(1).getCharacterId();
        SecurityVariable.subCharacterId2 = recommendedTeam.get(2).getCharacterId();
        SecurityVariable.subCharacterId3 = recommendedTeam.get(3).getCharacterId();

        return "recommended_team";
    }

    /**
     * Processes the selected characters for the team.
     *
     * @param selectedCharacterIds The IDs of the selected characters.
     * @return A redirection URL to the selectMainDPS page.
     */
    @PostMapping("/processSelectedCharacters")
    public String processSelectedCharacters(@RequestBody String selectedCharacterIds) {
        // Logging the received character IDs
        System.out.println("Selected Character IDs: " + selectedCharacterIds);

        // Remove quotes from the selectedCharacterIds
        selectedCharacterIds = selectedCharacterIds.replace("\"", "").replace("'", "");

        // Fetch the user
        User user = userRepository.findById(SecurityVariable.loggedUserId).orElse(null);

        if (user != null) {
            System.out.println("User: " + user.getUsername());

            // Set the owned characters IDs
            user.setOwned_characters_ids(selectedCharacterIds);

            // Save the user to update the database
            userRepository.save(user);

            // Refresh user object after save
            user = userRepository.findById(SecurityVariable.loggedUserId).orElse(null);
            return "redirect:/api/characters/selectMainDPS.html";
        } else {
            System.out.println("User not found!");
            return "redirect:/api/characters/selectMainDPS.html";
        }
    }

    /**
     * Displays a page showing the details of a saved team.
     *
     * @param teamId The ID of the team to display.
     * @param model  The model to be populated with data.
     * @return The name of the show_team HTML file.
     */
    @GetMapping("/show_team.html")
    public String showTeam(@RequestParam Long teamId, Model model) {
        // Retrieve the team using the teamId
        Optional<SavedTeam> savedTeamOpt = savedTeamRepository.findById(teamId);
        if (!savedTeamOpt.isPresent()) {
            // Handle the case where the team is not found
            return "error";
        }

        SavedTeam savedTeam = savedTeamOpt.get();

        // Retrieve the characters for the team
        Character mainDps = characterRepository.findById(savedTeam.getMaindpscharacter_id()).orElse(null);
        Character sub1 = characterRepository.findById(savedTeam.getSubcharacter1()).orElse(null);
        Character sub2 = characterRepository.findById(savedTeam.getSubcharacter2()).orElse(null);
        Character sub3 = characterRepository.findById(savedTeam.getSubcharacter3()).orElse(null);

        List<Character> team = new ArrayList<>();
        team.add(mainDps);
        team.add(sub1);
        team.add(sub2);
        team.add(sub3);

        List<Resonance> allResonances = resonanceRepository.findAll();
        List<Resonance> activeResonances = new ArrayList<>();
        List<Reaction> allReactions = reactionRepository.findAll();
        List<Reaction> possibleReactions = new ArrayList<>();
        // for each character in the recommended team fetch the element
        String finalTeamElement1 = team.get(0).getElement();
        String finalTeamElement2 = team.get(1).getElement();
        String finalTeamElement3 = team.get(2).getElement();
        String finalTeamElement4 = team.get(3).getElement();
        // if 2 or more elements are the same add the resonance with the name of that element
        // check 2 by 2 if the elements are the same and add the resonance with the name of that element
        //for example if finalTeamElement1 and finalTeamElement2 are both Pyro add the resonance with the name Pyro
        if(finalTeamElement1.equals(finalTeamElement2)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement1));
        }
        if(finalTeamElement1.equals(finalTeamElement3)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement1));
        }
        if(finalTeamElement1.equals(finalTeamElement4)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement1));
        }
        if(finalTeamElement2.equals(finalTeamElement3)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement2));
        }
        if(finalTeamElement2.equals(finalTeamElement4)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement2));
        }
        if(finalTeamElement3.equals(finalTeamElement4)){
            activeResonances.add(resonanceRepository.findByName(finalTeamElement3));
        }
        if(activeResonances.isEmpty()){
            activeResonances.add(resonanceRepository.findByName("Universal"));
        }
        //remove duplicates from the list
        activeResonances = new ArrayList<>(new HashSet<>(activeResonances));
        // make a list of the 4 elements
        List<String> elements = new ArrayList<>();
        elements.add(finalTeamElement1);
        elements.add(finalTeamElement2);
        elements.add(finalTeamElement3);
        elements.add(finalTeamElement4);
        // for each element in the list check if it has a reaction with the other elements
        // check for presence of Pyro in the list
        if(elements.contains("Pyro")){
            for(String element: elements){
                if(element.equals("Hydro")){
                    possibleReactions.add(reactionRepository.findByName("Vaporize"));
                }
                if(element.equals("Cryo")){
                    possibleReactions.add(reactionRepository.findByName("Melt"));
                }
                if(element.equals("Electro")){
                    possibleReactions.add(reactionRepository.findByName("Overloaded"));
                }
                if(element.equals("Dendro")){
                    possibleReactions.add(reactionRepository.findByName("Burning"));
                }
                if(element.equals("Geo")){
                    possibleReactions.add(reactionRepository.findByName("Crystallize"));
                }
                if(element.equals("Anemo")){
                    possibleReactions.add(reactionRepository.findByName("Swirl"));
                }
            }
        }
        if(elements.contains("Hydro")){
            for(String element: elements){
                if(element.equals("Cryo")){
                    possibleReactions.add(reactionRepository.findByName("Frozen"));
                    possibleReactions.add(reactionRepository.findByName("Shattered"));
                }
                if(element.equals("Electro")){
                    possibleReactions.add(reactionRepository.findByName("Electro-Charged"));
                }
                if(element.equals("Dendro")){
                    possibleReactions.add(reactionRepository.findByName("Bloom"));
                }
                if(element.equals("Geo")){
                    possibleReactions.add(reactionRepository.findByName("Crystallize"));
                }
                if(element.equals("Anemo")){
                    possibleReactions.add(reactionRepository.findByName("Swirl"));
                }
            }
        }
        if(elements.contains("Cryo")){
            for(String element: elements){

                if(element.equals("Electro")){
                    possibleReactions.add(reactionRepository.findByName("Superconduct"));
                }
                if(element.equals("Geo")){
                    possibleReactions.add(reactionRepository.findByName("Crystallize"));
                }
                if(element.equals("Anemo")){
                    possibleReactions.add(reactionRepository.findByName("Swirl"));
                }
            }
        }
        if(elements.contains("Electro")){
            for(String element: elements){
                if(element.equals("Dendro")){
                    possibleReactions.add(reactionRepository.findByName("Quicken"));
                    possibleReactions.add(reactionRepository.findByName("Aggravate"));
                    possibleReactions.add(reactionRepository.findByName("Spread"));
                }
                if(element.equals("Geo")){
                    possibleReactions.add(reactionRepository.findByName("Crystallize"));
                }
                if(element.equals("Anemo")){
                    possibleReactions.add(reactionRepository.findByName("Swirl"));
                }
            }
        }
        if(elements.contains("Electro") && elements.contains("Hydro") && elements.contains("Dendro")){
            possibleReactions.add(reactionRepository.findByName("Hyperbloom"));
        }
        if(elements.contains("Pyro") && elements.contains("Hydro") && elements.contains("Dendro")){
            possibleReactions.add(reactionRepository.findByName("Burgeon"));
        }


        //remove duplicates from the list
        possibleReactions = new ArrayList<>(new HashSet<>(possibleReactions));

        model.addAttribute("characters", team);
        model.addAttribute("elementIcons", getElementIcons());
        model.addAttribute("weaponIcons", getWeaponIcons());
        model.addAttribute("artifactPieceIcons", getArtifactPieceIcons());
        model.addAttribute("artifactSetIcons", getArtifactSetIcons());
        model.addAttribute("recommendedWeaponIcons", getRecommendedWeaponIcons());
        model.addAttribute("resonances", activeResonances);
        model.addAttribute("reactions", possibleReactions);


        return "show_team"; // This should be the name of the Thymeleaf template to display the team details
    }

    /**
     * Parses a comma-separated string of character IDs into a list of Long.
     *
     * @param characterIds The comma-separated string of character IDs.
     * @return A list of Long containing the parsed character IDs.
     */
    public static List<Long> parseCharacterIds(String characterIds) {
        List<Long> characterIdList = new ArrayList<>();

        if (characterIds == null || characterIds.isEmpty()) {
            return characterIdList;  // Return an empty list if the input is null or empty
        }

        // Split the string by comma
        String[] ids = characterIds.split(",");

        // Process each id
        for (String id : ids) {
            try {
                // Trim any surrounding whitespace and parse to Long
                Long characterId = Long.parseLong(id.trim());
                characterIdList.add(characterId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid character ID: " + id);
                // Handle or log the invalid number format if necessary
            }
        }

        return characterIdList;
    }

}
