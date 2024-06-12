package org.markmcguire.cardcollectors.controllers;

import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.Player;
import org.markmcguire.cardcollectors.services.PackService;
import org.markmcguire.cardcollectors.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("packs")
public class PackController {

  private final PlayerService playerService;

  private final PackService packService;

  @Autowired
  public PackController(PlayerService playerService, PackService packService) {

    this.playerService = playerService;
    this.packService = packService;
  }

  @GetMapping("/")
  public String index(Model model, Authentication auth) {
    String email = auth.getName();
    Player player = playerService.findByEmail(email);
    model.addAttribute("player", player);
    model.addAttribute("packs", packService.getAllPacks());
    return "purchase";
  }
}
