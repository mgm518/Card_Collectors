package org.markmcguire.cardcollectors.controllers;

import org.markmcguire.cardcollectors.models.Player;
import org.markmcguire.cardcollectors.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("player")
public class PlayerController {

  private final PlayerService playerService;

  @Autowired
  public PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("player", new Player());
    return "register";
  }

  @PostMapping("/register")
  public String registerUser(@ModelAttribute("player") Player player) {
    playerService.savePlayer(player);
    return "redirect:/players/login"; // Redirect to login after registration
  }

  @GetMapping("/login")
  public String showLoginForm() {
    return "login";
  }

  @GetMapping("/profile")
  public String showProfile(Model model, Authentication authentication) {
    String email = authentication.getName();
    Player player = playerService.findByEmail(email);
    model.addAttribute("player", player);
    return "profile";
  }
}
