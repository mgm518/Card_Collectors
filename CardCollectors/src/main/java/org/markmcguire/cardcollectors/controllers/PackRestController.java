package org.markmcguire.cardcollectors.controllers;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.services.PackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/packs")
public class PackRestController {

  @Autowired
  private PackServiceImpl packService;

  @GetMapping("/all")
  public List<Pack> getAllPacks() {
    log.debug("Fetching list of all packs");
    return packService.getAllPacks();
  }

  @PostMapping("/create")
  public Pack createCard(@RequestBody Pack pack) {
    log.debug("Creating Pack: {}", pack);
    return packService.createPack(pack);
  }

  @PostMapping("/initiate")
  public List<Pack> initiatePackDb() {
    log.debug("Initializing Card Database");
    return packService.initializePackDb();
  }

  @PostMapping("/open/{name}")
  public List<Card> openPack(@PathVariable String name) {
    log.debug("Opening Card Pack");
    return packService.openPack(name);
  }
}
