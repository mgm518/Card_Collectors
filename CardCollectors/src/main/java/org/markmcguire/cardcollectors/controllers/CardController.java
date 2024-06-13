package org.markmcguire.cardcollectors.controllers;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.Player;
import org.markmcguire.cardcollectors.services.CardService;
import org.markmcguire.cardcollectors.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("cards")
public class CardController {

  private final PlayerService playerService;
  private final CardService cardService;

  @Autowired
  public CardController(PlayerService playerService, CardService cardService) {
    this.playerService = playerService;
    this.cardService = cardService;
  }

  @GetMapping("discard/{id}")
  public String discardSelectedCard(@PathVariable long id, Authentication auth) {
    Player player = playerService.findByEmail(auth.getName());
    Optional.ofNullable(cardService.getCard(id))
        .ifPresent(card -> {
          log.debug("Attempting to discard selected card {} from player {}", card, player);
          playerService.discardCard(player, card);
        });
    return "redirect:/profile";
  }
}
