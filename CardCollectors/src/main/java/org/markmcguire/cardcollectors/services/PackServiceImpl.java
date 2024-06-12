package org.markmcguire.cardcollectors.services;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.repositories.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PackServiceImpl implements PackService {

  private final CardService cardService;

  private final PackRepository packRepository;

  @Autowired
  public PackServiceImpl(CardService cardService, PackRepository packRepository) {
    this.cardService = cardService;
    this.packRepository = packRepository;
  }

  /**
   * @return
   */
  @Override
  public List<Pack> getAllPacks() {
    return List.of();
  }

  /**
   * @param pack
   * @return
   */
  @Override
  public Pack createPack(Pack pack) {
    return packRepository.save(Pack.builder()
        .name(pack.getName()).description(pack.getDescription())
        .size(pack.getSize()).bonus(pack.getBonus())
        .pool(Optional.ofNullable(pack.getPool())
            .orElse(cardService.getAllCards())).build());
  }

  @Transactional
  public List<Pack> initializePackDb() {
    packRepository.deleteAll();
    packRepository.save(Pack.builder()
        .name("Standard")
        .description("A standard card pack")
        .pool(cardService.getAllCards())
        .build());
    packRepository.save(Pack.builder()
        .name("Limited")
        .description("A limited card pack.  There's a here probability of pulling an SSR card")
        .pool(cardService.getAllCards()).bonus(5)
        .build());
    return packRepository.findAll();
  }

  /**
   * @param name
   * @return
   */
  @Override
  public Pack getPack(String name) {
    return packRepository.findByName(name);
  }

  public List<Card> openPack(String name) {
    return Optional.ofNullable(packRepository.findByName(name))
        .map(cardService::openCardPack)
        .orElse(List.of());
  }

  /**
   * @return
   */
  @Override
  public List<Card> openPack(Pack pack) {
    return cardService.openCardPack(pack);
  }
}
