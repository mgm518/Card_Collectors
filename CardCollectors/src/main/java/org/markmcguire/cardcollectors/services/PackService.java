package org.markmcguire.cardcollectors.services;

import java.util.List;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Pack;

public interface PackService {

  List<Pack> getAllPacks();

  Pack createPack(Pack pack);

  Pack getPack(String name);

  List<Card> openPack(Pack pack);


}
