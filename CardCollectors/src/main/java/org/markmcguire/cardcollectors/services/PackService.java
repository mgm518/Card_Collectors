package org.markmcguire.cardcollectors.services;

import java.util.List;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.PackType;

public interface PackService {

  List<PackType> getAllPacks();

  PackType createPackType(PackType packType);

  PackType getPackType(String name);

//  List<CardType> openPack(PackType packType);


}
