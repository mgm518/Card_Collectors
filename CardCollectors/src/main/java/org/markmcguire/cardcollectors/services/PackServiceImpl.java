package org.markmcguire.cardcollectors.services;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.repositories.PackRepository;
import org.markmcguire.cardcollectors.repositories.PackTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PackServiceImpl implements PackService {

  private final CardService cardService;
  private final PackTypeRepository packTypeRepository;
  private final PackRepository packRepository;

  @Autowired
  public PackServiceImpl(CardService cardService, PackTypeRepository packTypeRepository,
      PackRepository packRepository) {
    this.cardService = cardService;
    this.packTypeRepository = packTypeRepository;
    this.packRepository = packRepository;
  }

  /**
   * @param packType
   * @return
   */
  @Override
  public PackType createPackType(PackType packType) {
    return packTypeRepository.save(
        PackType.builder().name(packType.getName()).description(packType.getDescription())
            .size(packType.getSize()).bonus(packType.getBonus())
            .pool(Optional.ofNullable(packType.getPool()).orElse(cardService.getAllCards()))
            .build());
  }

  /**
   * @param name
   * @return
   */
  @Override
  public PackType getPackType(String name) {
    return packTypeRepository.findByName(name);
  }

  /**
   * @param id
   * @return
   */
  @Override
  public Pack getPack(Long id) {
    return packRepository.findById(id).orElse(null);
  }

  /**
   * @param pack
   * @return
   */
  @Override
  public List<Card> openPack(Pack pack) {
    // TODO implement return cardService.openCardPack(pack.getType());
    return cardService.openCardPack(pack.getType()).stream()
        .map(cardType -> Card.builder().type(cardType).build())
        .toList();
  }

  @Transactional
  public List<PackType> initializePackDb() {
    packTypeRepository.deleteAll();
    packTypeRepository.save(PackType.builder().name("Standard").description("A standard card pack")
        .pool(cardService.getAllCards()).build());
    packTypeRepository.save(PackType.builder().name("Limited")
        .description("A limited card pack.  There's a here probability of pulling an SSR card")
        .pool(cardService.getAllCards()).bonus(5).build());
    return packTypeRepository.findAll();
  }

  /*
  Below are service methods that are exposed via REST API.
  Their purpose is to either simulate functionality or initialize the DB.
  They are not intended to be used by the regular @Controller
   */

  /**
   * @return List of every {@link CardType} in the database.
   */
  public List<PackType> getAllPacks() {
    return packTypeRepository.findAll();
  }
  /**
   * Simulates opening a {@link PackType} based on the pack's name
   * @param name The name of the pack to find.
   * @return The list {@link CardType} drawn from {@link CardService#openCardPack(PackType)}.  Will be an empty List if {@code name} is invalid.
   */
  public List<CardType> openPack(String name) {
    return Optional.ofNullable(getPackType(name))
        .map(cardService::openCardPack)
        .orElse(List.of());
  }
}
