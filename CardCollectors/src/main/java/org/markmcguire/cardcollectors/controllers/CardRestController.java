package org.markmcguire.cardcollectors.controllers;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Rarity;
import org.markmcguire.cardcollectors.services.CardServiceImpl;
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

  private final CardServiceImpl cardService;

  @Autowired
  public CardRestController(CardServiceImpl cardService) {
    this.cardService = cardService;
  }

  @GetMapping("/all")
  public Set<Card> getAllCards() {
    log.debug("Fetching list of all cards");
    return cardService.getAllCards();
  }

  @PostMapping("/create")
  public Card createCard(@RequestBody Card card) {
    log.debug("Creating card: {}", card);
    return cardService.createCard(card);
  }

  @PostMapping("/createTestCard")
  public Card createTestCard() {
    Card test = Card.builder().name("Tester").rarity(Rarity.SSR).build();
    log.debug("Generated Test Card: {}", test);
    return cardService.createCard(test);
  }

  @PostMapping("/initiate")
  public List<Card> createCard() {
    log.debug("Initializing Card Database");
    return cardService.initializeCardDb();
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
