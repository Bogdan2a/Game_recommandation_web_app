package controller;

import model.Resonance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.ResonanceRepository;
import java.util.List;

/**
 * Controller class responsible for handling requests related to resonances.
 */
@Controller
@RequestMapping("/api/resonances")
public class ResonanceController {

    @Autowired
    private ResonanceRepository resonanceRepository;

}
