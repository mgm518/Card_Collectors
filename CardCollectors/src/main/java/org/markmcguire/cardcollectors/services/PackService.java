package org.markmcguire.cardcollectors.services;

import java.util.List;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.models.PackType;

public interface PackService {

  PackType createPackType(PackType packType);

  PackType getPackType(String name);

  Pack getPack(Long id);

  List<Card> openPack(Pack pack);

}
