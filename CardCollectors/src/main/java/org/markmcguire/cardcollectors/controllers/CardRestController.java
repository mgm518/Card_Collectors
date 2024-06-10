package org.markmcguire.cardcollectors.controllers;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Rarity;
import org.markmcguire.cardcollectors.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/cards")
public class CardRestController {

  private final CardService cardService;

  @Autowired
  public CardRestController(CardService cardService) {
    this.cardService = cardService;
  }

  @GetMapping("/all")
  public List<Card> getAllCards() {
    log.debug("Fetching list of all cards");
    return cardService.getAllCards();
  }

  @PostMapping("/create")
  public Card createCard(@RequestBody Card card) {
    log.debug("Creating card: {}", card);
    return cardService.createCard(card);
  }

  @PostMapping("/createtest")
  public Card createCard() {
    Card test = Card.builder().name("Tester").rarity(Rarity.SSR).build();
    log.debug("Generated Test Card: {}", test);
    return cardService.createCard(test);
  }

  @GetMapping("/draw")
  public Card drawStandardCard() {
    log.debug("Standard Gacha Pull");
    return cardService.gachaStandardPull();
  }

  @GetMapping("/drawLimited")
  public Card drawLimitedCard() {
    log.debug("Limited Gacha Pull");
    return cardService.gachaLimitedPull();
  }
}
