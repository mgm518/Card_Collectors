package org.markmcguire.cardcollectors.controllers;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.models.Player;
import org.markmcguire.cardcollectors.services.PackService;
import org.markmcguire.cardcollectors.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/addPack/{name}")
  public String purchaseSelectedPack(@PathVariable String name, Authentication auth) {
    Player player = playerService.findByEmail(auth.getName());
    PackType packType = packService.getPackType(name);
    log.debug("Attempting to add pack {} to player {}", packType, player);
    if (player.getCurrency() >= packType.getCost()) {
      playerService.purchasePack(player, packType);
    } // else provide an error saying player can't purchase; Or disable the buttons on front-end
    return "redirect:/profile";
  }

  @GetMapping("/openPack/{id}")
  public String openSelectedPack(@PathVariable Long id, Authentication auth) {
    Player player = playerService.findByEmail(auth.getName());
    Optional.ofNullable(packService.getPack(id))
        .ifPresent(pack -> {
          List<Card> cards = packService.openPack(pack);
          log.debug("Updating the player {} to add cardList {} and remove pack {}",
              player, cards, pack);
          playerService.addCardsToPlayerCollection(player, pack, cards);
        });
    return "redirect:/profile";
  }
}
