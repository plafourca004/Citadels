package com.montaury.citadels;

import com.montaury.citadels.character.Character;
import com.montaury.citadels.character.RandomCharacterSelector;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.district.District;
import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.player.ComputerController;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.round.action.DestroyDistrictAction;
import io.vavr.Tuple;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;

import java.util.Collections;
import java.util.Scanner;

public class Citadels {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Quel est votre nom ? ");
        String playerName = scanner.next();
        System.out.println("Quel est votre age ? ");
        int playerAge = scanner.nextInt();
        Board board = new Board();
        Player joueurHumain = new Player(playerName, playerAge, new City(board), new HumanController());
        joueurHumain.human = true;
        List<Player> listeDesJoueurs = List.of(joueurHumain);
        System.out.println("Saisir le nombre de joueurs total (entre 2 et 8): ");
        int nombreDeJoueurs;
        do {
            nombreDeJoueurs = scanner.nextInt();
        } while (nombreDeJoueurs < 2 || nombreDeJoueurs > 8);
        for (int compteurDeJoueur = 0; compteurDeJoueur < nombreDeJoueurs; compteurDeJoueur += 1) {
            Player nouveauJoueur = new Player("Computer " + compteurDeJoueur, 35, new City(board), new ComputerController());
            nouveauJoueur.computer = true;
            listeDesJoueurs = listeDesJoueurs.append(nouveauJoueur);
        }
        CardPile pioche = new CardPile(Card.all().toList().shuffle());
        listeDesJoueurs.forEach(player -> {
            player.add(2);
            player.add(pioche.draw(2));
        });
        Player joueurCouronne = listeDesJoueurs.maxBy(Player::age).get();

        List<Group> roundAssociations;
        do {
            java.util.List<Player> listeJoueursConvertie = listeDesJoueurs.asJavaMutable();
            Collections.rotate(listeJoueursConvertie, -listeDesJoueurs.indexOf(joueurCouronne)); //Trier par age
            List<Player> joueursDanslOrdre = List.ofAll(listeJoueursConvertie);
            RandomCharacterSelector selecteurDePersonnagesAleatoires = new RandomCharacterSelector();
            List<Character> personnagesDisponibles = List.of(Character.ASSASSIN, Character.THIEF, Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD);

           /* List<Character> personnagesEncoreDisponibles = personnagesDisponibles;
            List<Character> personnagesPlusDisponibles = List.empty();
            Character personnageChoisi = selecteurDePersonnagesAleatoires.among(personnagesEncoreDisponibles);
            personnagesPlusDisponibles = personnagesPlusDisponibles.append(personnageChoisi);
            personnagesEncoreDisponibles = personnagesEncoreDisponibles.remove(personnageChoisi);


            Character carteCacheAuCentre = personnagesPlusDisponibles.head();
            personnagesDisponibles = personnagesDisponibles.remove(carteCacheAuCentre);*/

            Character personnageCacheAuCentre = selecteurDePersonnagesAleatoires.among(personnagesDisponibles);
            personnagesDisponibles = personnagesDisponibles.remove(personnageCacheAuCentre);



            List<Character> personnagesEncoreDisponibles = personnagesDisponibles.remove(Character.KING);
            List<Character> PersonnagesDejaChoisis = List.empty();

            for (int i = 0; i < 7 - joueursDanslOrdre.size() - 1; i++) { //Permet de choisir aléatoirement les cartes au centre retournées
                Character personnageChoisi = selecteurDePersonnagesAleatoires.among(personnagesEncoreDisponibles);
                PersonnagesDejaChoisis = PersonnagesDejaChoisis.append(personnageChoisi);
                personnagesEncoreDisponibles = personnagesEncoreDisponibles.remove(personnageChoisi);
            }
            List<Character> personnagesVisiblesAuCentre = PersonnagesDejaChoisis;
            personnagesDisponibles = personnagesDisponibles.removeAll(personnagesVisiblesAuCentre);

            List<Group> couplesJoueurPerso = List.empty();

            if (joueursDanslOrdre.size() == 8)
            {
                personnagesDisponibles.append(personnageCacheAuCentre);
            }

            for (Player joueurCourant : joueursDanslOrdre) {
                System.out.println(joueurCourant.name() + " doit choisir un personnage");


                Character personnageChoisi = joueurCourant.controller.selectOwnCharacter(personnagesDisponibles, personnagesVisiblesAuCentre); //choisir un perso
                personnagesDisponibles = personnagesDisponibles.remove(personnageChoisi);
                couplesJoueurPerso = couplesJoueurPerso.append(new Group(joueurCourant, personnageChoisi));
            }
            //List<Group> associations = associations;
            GameRoundAssociations groupeCoupleJoueurPerso = new GameRoundAssociations(couplesJoueurPerso);

            for (int indicePersonnageCourant = 0; indicePersonnageCourant < 8; indicePersonnageCourant++) {
                for (int indiceJoueurCourant = 0; indiceJoueurCourant < couplesJoueurPerso.size(); indiceJoueurCourant++) {
                    Group joueurCourant = couplesJoueurPerso.get(indiceJoueurCourant);
                    if (indicePersonnageCourant + 1 == joueurCourant.character.number()) {
                        if (!couplesJoueurPerso.get(indiceJoueurCourant).isMurdered()) {
                            couplesJoueurPerso.get(indiceJoueurCourant).thief().peek(thief -> thief.steal(joueurCourant.player())); //Se faire voler sa thune si c'est toi qui a été volé

                            List<District> districts = joueurCourant.player().city().districts();
                            Set<Action> actionsDisponibles = HashSet.of(Action.DRAW_2_CARDS_KEEP_1, Action.RECEIVE_2_COINS);
                            for (District districtCourant : districts) {
                                if (districtCourant == District.OBSERVATORY) {
                                    actionsDisponibles = actionsDisponibles.replace(Action.DRAW_2_CARDS_KEEP_1, Action.DRAW_3_CARDS_KEEP_1);
                                }
                            }
                            // keep only actions that player can realize
                            List<Action> actionsPossibles = List.empty();
                            for (Action action : actionsDisponibles) {
                                if ((action == Action.DRAW_2_CARDS_KEEP_1) && (pioche.canDraw(2))) { //flagModif
                                    actionsPossibles = actionsPossibles.append(Action.DRAW_2_CARDS_KEEP_1);
                                } else if (action == Action.DRAW_3_CARDS_KEEP_1 && pioche.canDraw(3)) { //flagModif
                                    actionsPossibles = actionsPossibles.append(Action.DRAW_3_CARDS_KEEP_1);
                                } else {
                                    actionsPossibles = actionsPossibles.append(action);
                               }
                            }
                            Action typeDAction = joueurCourant.player().controller.selectActionAmong(actionsPossibles.toList());
                            // execute selected action
                            if (typeDAction == Action.DRAW_2_CARDS_KEEP_1) {
                                Set<Card> cartesPiochees = pioche.draw(2);
                                if (!joueurCourant.player().city().has(District.LIBRARY)) {
                                    Card keptCard = joueurCourant.player().controller.selectAmong(cartesPiochees);
                                    pioche.discard(cartesPiochees.remove(keptCard).toList());
                                    cartesPiochees = HashSet.of(keptCard);
                                }
                                joueurCourant.player().add(cartesPiochees);
                            } else if (typeDAction == Action.RECEIVE_2_COINS) {
                                joueurCourant.player().add(2);
                            } else if (typeDAction == Action.DRAW_3_CARDS_KEEP_1) {
                                Set<Card> cardsDrawn = pioche.draw(3);
                                if (!joueurCourant.player().city().has(District.LIBRARY)) {
                                    Card keptCard = joueurCourant.player().controller.selectAmong(cardsDrawn);
                                    pioche.discard(cardsDrawn.remove(keptCard).toList());
                                    cardsDrawn = HashSet.of(keptCard);
                                }
                                joueurCourant.player().add(cardsDrawn);
                            }
                            actionExecuted(joueurCourant, typeDAction, couplesJoueurPerso);

                            // receive powers from the character            flagModif
                            List<Action> powers = (List<Action>) joueurCourant.character().getPowers();

                            List<Action> extraActions = List.empty();
                            for (District d : joueurCourant.player().city().districts()) {
                                if (d == District.SMITHY) {
                                    extraActions = extraActions.append(Action.DRAW_3_CARDS_KEEP_1);
                                }
                                if (d == District.LABORATORY) {
                                    extraActions = extraActions.append(Action.DISCARD_CARD_FOR_2_COINS);
                                }
                            }
                            Set<Action> availableActions11 = Group.OPTIONAL_ACTIONS
                                    .addAll(powers)
                                    .addAll(extraActions);
                            Action actionType11;
                            do {
                                Set<Action> availableActions1 = availableActions11;
                                // keep only actions that player can realize
                                List<Action> possibleActions2 = List.empty();
                                for (Action action : availableActions1) {
                                    if (action == Action.BUILD_DISTRICT) {
                                        if (!joueurCourant.player().buildableDistrictsInHand().isEmpty())
                                            possibleActions2 = possibleActions2.append(Action.BUILD_DISTRICT);
                                    } else if (action == Action.DESTROY_DISTRICT && DestroyDistrictAction.districtsDestructibleBy(groupeCoupleJoueurPerso, joueurCourant.player()).exists(districtsByPlayer -> !districtsByPlayer._2().isEmpty())) {

                                        possibleActions2 = possibleActions2.append(Action.DESTROY_DISTRICT);
                                    } else if (action == Action.DISCARD_CARD_FOR_2_COINS && !joueurCourant.player().cards().isEmpty()) {

                                        possibleActions2 = possibleActions2.append(Action.DISCARD_CARD_FOR_2_COINS);
                                    } else if (action == Action.DRAW_3_CARDS_KEEP_1 && pioche.canDraw(3) && joueurCourant.player().canAfford(2)) {

                                        possibleActions2 = possibleActions2.append(Action.DRAW_3_CARDS_FOR_2_COINS);
                                    } else if (action == Action.EXCHANGE_CARDS_WITH_PILE && !joueurCourant.player().cards().isEmpty() && pioche.canDraw(1)) {

                                        possibleActions2 = possibleActions2.append(Action.EXCHANGE_CARDS_WITH_PILE);
                                    } else if (action == Action.PICK_2_CARDS && pioche.canDraw(2)) {
                                        possibleActions2 = possibleActions2.append(Action.PICK_2_CARDS);
                                    } else
                                        possibleActions2 = possibleActions2.append(action);
                                }
                                Action actionType1 = joueurCourant.player().controller.selectActionAmong(possibleActions2.toList());
                                // execute selected action
                                if (actionType1 == Action.BUILD_DISTRICT) {
                                    Card card = joueurCourant.player().controller.selectAmong(joueurCourant.player().buildableDistrictsInHand());
                                    joueurCourant.player().buildDistrict(card);
                                } else if (actionType1 == Action.DISCARD_CARD_FOR_2_COINS) {
                                    Player player = joueurCourant.player();
                                    Card card = player.controller.selectAmong(player.cards());
                                    player.cards = player.cards().remove(card);
                                    pioche.discard(card);
                                    player.add(2);
                                } else if (actionType1 == Action.DRAW_3_CARDS_FOR_2_COINS) {
                                    joueurCourant.player().add(pioche.draw(3));
                                    joueurCourant.player().pay(2);
                                } else if (actionType1 == Action.EXCHANGE_CARDS_WITH_PILE) {
                                    Set<Card> cardsToSwap = joueurCourant.player().controller.selectManyAmong(joueurCourant.player().cards());
                                    joueurCourant.player().cards = joueurCourant.player().cards().removeAll(cardsToSwap);
                                    joueurCourant.player().add(pioche.swapWith(cardsToSwap.toList()));
                                } else if (actionType1 == Action.EXCHANGE_CARDS_WITH_OTHER_PLAYERS) {
                                    Player playerToSwapWith = joueurCourant.player().controller.selectPlayerAmong(groupeCoupleJoueurPerso.associations.map(Group::player).remove(joueurCourant.player()));
                                    joueurCourant.player().exchangeHandWith(playerToSwapWith);
                                } else if (actionType1 == Action.KILL) {
                                    Character characterToMurder = joueurCourant.player().controller.selectAmong(List.of(Character.THIEF, Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD));
                                    groupeCoupleJoueurPerso.associationToCharacter(characterToMurder).peek(Group::murder);
                                } else if (actionType1 == Action.PICK_2_CARDS) {
                                    joueurCourant.player().add(pioche.draw(2));
                                } else if (actionType1 == Action.RECEIVE_2_COINS) {
                                    joueurCourant.player().add(2);
                                } else if (actionType1 == Action.RECEIVE_1_COIN) {
                                    joueurCourant.player().add(1);
                                } else if (actionType1 == Action.RECEIVE_INCOME) {
                                    DistrictType type = null;


                                    //type = group.character.associatedDistrictType();

                                    if (joueurCourant.character == Character.BISHOP) {
                                        type = DistrictType.RELIGIOUS;
                                    } else if (joueurCourant.character == Character.WARLORD) {
                                        type = DistrictType.MILITARY;
                                    } else if (joueurCourant.character == Character.KING) {
                                        type = DistrictType.NOBLE;
                                    } else if (joueurCourant.character == Character.MERCHANT) {
                                        type = DistrictType.TRADE;
                                    }
                                    if (type != null) {
                                        for (District d : joueurCourant.player().city().districts()) {
                                            if (d.districtType() == type) {
                                                joueurCourant.player().add(1);
                                            }
                                            if (d == District.MAGIC_SCHOOL) {
                                                joueurCourant.player().add(1);
                                            }
                                        }
                                    }
                                } else if (actionType1 == Action.DESTROY_DISTRICT) {
                                    //flemme
                                } else if (actionType1 == Action.ROB) {
                                    Character character = joueurCourant.player().controller.selectAmong(List.of(Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD)
                                            .removeAll(groupeCoupleJoueurPerso.associations.find(Group::isMurdered).map(Group::character)));
                                    groupeCoupleJoueurPerso.associationToCharacter(character).peek(association -> association.stolenBy(joueurCourant.player()));
                                }
                                actionExecuted(joueurCourant, actionType1, couplesJoueurPerso);
                                actionType11 = actionType1;
                                availableActions11 = availableActions11.remove(actionType11);
                            }
                            while (!availableActions11.isEmpty() && actionType11 != Action.END_ROUND);
                        }
                    }
                }
            }
            roundAssociations = couplesJoueurPerso;
            joueurCouronne = roundAssociations.find(a -> a.character == Character.KING).map(Group::player).getOrElse(joueurCouronne);
        } while (!listeDesJoueurs.map(Player::city).exists(City::isComplete));

        // classe les joueurs par leur score
        // si ex-aequo, le premier est celui qui n'est pas assassiné
        // si pas d'assassiné, le gagnant est le joueur ayant eu le personnage avec le numéro d'ordre le plus petit au dernier tour
        System.out.println("Classement: " + roundAssociations.sortBy(a -> Tuple.of(a.player().score(), !a.isMurdered(), a.character))
                .reverse()
                .map(Group::player));
    }

    public static void actionExecuted(Group association, Action actionType, List<Group> associations) {
        System.out.println("Player " + association.player().name() + " executed action " + actionType.getDescription());
        associations.map(Group::player)
                .forEach(Citadels::displayStatus);
    }

    private static void displayStatus(Player player) {
        System.out.println("  Player " + player.name() + ":");
        System.out.println("    Gold coins: " + player.gold());
        System.out.println("    City: " + textCity(player));
        System.out.println("    Hand size: " + player.cards().size());
        if (player.controller instanceof HumanController) {
            System.out.println("    Hand: " + textHand(player));
        }
        System.out.println();
    }

    private static String textCity(Player player) {
        List<District> districts = player.city().districts();
        return districts.isEmpty() ? "Empty" : districts.map(Citadels::textDistrict).mkString(", ");
    }

    private static String textDistrict(District district) {
        return district.name() + "(" + district.districtType().name() + ", " + district.cost() + ")";
    }

    private static String textHand(Player player) {
        Set<Card> cards = player.cards();
        return cards.isEmpty() ? "Empty" : cards.map(Citadels::textCard).mkString(", ");
    }

    private static String textCard(Card card) {
        return textDistrict(card.district());
    }
}
