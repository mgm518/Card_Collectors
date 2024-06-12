package org.markmcguire.cardcollectors.services;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.repositories.PackTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PackServiceImpl implements PackService {

  private final CardService cardService;

  private final PackTypeRepository packTypeRepository;

  @Autowired
  public PackServiceImpl(CardService cardService, PackTypeRepository packTypeRepository) {
    this.cardService = cardService;
    this.packTypeRepository = packTypeRepository;
  }

  /**
   * @return
   */
  @Override
  public List<PackType> getAllPacks() {
    return List.of();
  }

  /**
   * @param packType
   * @return
   */
  @Override
  public PackType createPackType(PackType packType) {
    return packTypeRepository.save(PackType.builder()
        .name(packType.getName()).description(packType.getDescription())
        .size(packType.getSize()).bonus(packType.getBonus())
        .pool(Optional.ofNullable(packType.getPool())
            .orElse(cardService.getAllCards())).build());
  }

  @Transactional
  public List<PackType> initializePackDb() {
    packTypeRepository.deleteAll();
    packTypeRepository.save(PackType.builder()
        .name("Standard")
        .description("A standard card pack")
        .pool(cardService.getAllCards())
        .build());
    packTypeRepository.save(PackType.builder()
        .name("Limited")
        .description("A limited card pack.  There's a here probability of pulling an SSR card")
        .pool(cardService.getAllCards()).bonus(5)
        .build());
    return packTypeRepository.findAll();
  }

  /**
   * @param name
   * @return
   */
  @Override
  public PackType getPackType(String name) {
    return packTypeRepository.findByName(name);
  }

  public List<CardType> openPack(String name) {
    return Optional.ofNullable(getPackType(name))
        .map(cardService::openCardPack)
        .orElse(List.of());
  }
}
