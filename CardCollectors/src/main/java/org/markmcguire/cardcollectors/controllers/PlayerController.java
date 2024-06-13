package org.markmcguire.cardcollectors.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.markmcguire.cardcollectors.models.Player;
import org.markmcguire.cardcollectors.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlayerController {

  private final PlayerService playerService;

  @Autowired
  public PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @GetMapping("/")
  public String index(Authentication auth) {
    if (auth == null) {
      return "redirect:/login";
    }
    return "redirect:/profile";
  }

  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("player", new Player());
    return "register";
  }

  @PostMapping("/register")
  public String registerUser(@ModelAttribute("player") Player player) {
    player.setCurrency(15000);
    playerService.savePlayer(player);
    return "redirect:/login"; // Redirect to login after registration
  }

  @GetMapping("/login")
  public String showLoginForm() {
    return "login";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/login?logout";
  }

  @RequestMapping("/profile")
  public String showProfile(Model model, Authentication authentication) {
    String email = authentication.getName();
    Player player = playerService.findByEmail(email);
    if(player == null) { return "redirect:/login"; }
    model.addAttribute("player", player);
    return "profile";
  }

  @GetMapping("/{amount}")
  public String addCurrency(@PathVariable Integer amount, Authentication auth) {
    Player player = playerService.findByEmail(auth.getName());
    if(player == null) { return "redirect:/login"; }
    playerService.purchaseCurrency(player, amount);
    return "redirect:/profile";
  }
}
