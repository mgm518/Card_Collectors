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
   * Creates a {@link PackType} and saves it to the repository.  Should the {@code packType} not
   * have a pool of {@link CardType} already set, then it will be set to the list of
   * {@code CardTypes} already in the database.
   *
   * @param packType {@link PackType} being created.
   * @return the resulting {@link PackType} stored in the database.
   */
  @Override
  public PackType createPackType(PackType packType) {
    return packTypeRepository.save(
        PackType.builder().name(packType.getName()).description(packType.getDescription())
            .size(packType.getSize()).bonus(packType.getBonus())
            .pool(Optional.ofNullable(packType.getPool()).orElse(cardService.getAllCards()))
            .build());
  }

  @Override
  public PackType getPackType(String name) {
    return packTypeRepository.findByName(name);
  }

  @Override
  public Pack getPack(Long id) {
    return packRepository.findById(id).orElse(null);
  }

  /**
   * Takes the {@code pack} and calls {@link CardService#openCardPack(PackType)} to generate the
   * random list of {@link CardType}.  They are converted to {@link Card} so the
   * {@link org.markmcguire.cardcollectors.models.Player} can store them.
   *
   * @param pack The selected Pack being opened
   * @return The resulting Cards from the gacha generation.
   */
  @Override
  public List<Card> openPack(Pack pack) {
    return cardService.openCardPack(pack.getType()).stream()
        .map(cardType -> Card.builder().type(cardType).build())
        .toList();
  }

  /*
  Below are service methods that are exposed via REST API.
  Their purpose is to either simulate functionality or initialize the DB.
  They are not intended to be used by the regular @Controller
   */

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

  /**
   * @return List of every {@link CardType} in the database.
   */
  public List<PackType> getAllPacks() {
    return packTypeRepository.findAll();
  }

  /**
   * Simulates opening a {@link PackType} based on the pack's name
   *
   * @param name The name of the pack to find.
   * @return The list {@link CardType} drawn from {@link CardService#openCardPack(PackType)}.  Will
   * be an empty List if {@code name} is invalid.
   */
  public List<CardType> openPack(String name) {
    return Optional.ofNullable(getPackType(name))
        .map(cardService::openCardPack)
        .orElse(List.of());
  }
}
