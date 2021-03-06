package com.montaury.citadels;

import com.montaury.citadels.actions.ActionType;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.character.RandomCharacterSelector;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.ComputerController;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.round.action.DestroyDistrict;
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



        jouer(listeDesJoueurs,  joueurCouronne, pioche);
        // classe les joueurs par leur score
        // si ex-aequo, le premier est celui qui n'est pas assassiné
        // si pas d'assassiné, le gagnant est le joueur ayant eu le personnage avec le numéro d'ordre le plus petit au dernier tour

    }

    public static void jouer(List<Player> listeDesJoueurs, Player joueurCouronne, CardPile pioche)
    {
        List<Group> roundAssociations;
        do {
            java.util.List<Player> listeJoueursConvertie = listeDesJoueurs.asJavaMutable();
            Collections.rotate(listeJoueursConvertie, -listeDesJoueurs.indexOf(joueurCouronne)); //Trier par age
            List<Player> joueursDanslOrdre = List.ofAll(listeJoueursConvertie);
            RandomCharacterSelector selecteurDePersonnagesAleatoires = new RandomCharacterSelector();
            List<Character> personnagesDisponibles = List.of(Character.ASSASSIN, Character.THIEF, Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD);

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

            jouerUnTour( couplesJoueurPerso,  pioche, groupeCoupleJoueurPerso );

            roundAssociations = couplesJoueurPerso;
            joueurCouronne = roundAssociations.find(a -> a.character == Character.KING).map(Group::player).getOrElse(joueurCouronne);
        } while (!listeDesJoueurs.map(Player::city).exists(City::isComplete));

        afficherResultats(roundAssociations);

    }

    public static void jouerUnTour(List<Group> couplesJoueurPerso,  CardPile pioche, GameRoundAssociations groupeCoupleJoueurPerso )
    {
        for (int indicePersonnageCourant = 0; indicePersonnageCourant < 8; indicePersonnageCourant++) {
            for (int indiceJoueurCourant = 0; indiceJoueurCourant < couplesJoueurPerso.size(); indiceJoueurCourant++) {
                Group joueurCourant = couplesJoueurPerso.get(indiceJoueurCourant);
                if (indicePersonnageCourant + 1 == joueurCourant.character.number()) {
                    if (!couplesJoueurPerso.get(indiceJoueurCourant).isMurdered()) {
                        couplesJoueurPerso.get(indiceJoueurCourant).thief().peek(thief -> thief.steal(joueurCourant.player())); //Se faire voler sa thune si c'est toi qui a été volé

                        List<District> districts = joueurCourant.player().city().districts();
                        Set<ActionType> actionsDisponibles = HashSet.of(ActionType.DRAW_2_CARDS_KEEP_1, ActionType.RECEIVE_2_COINS);
                        for (District districtCourant : districts) {
                            if (districtCourant == District.OBSERVATORY) {
                                actionsDisponibles = actionsDisponibles.replace(ActionType.DRAW_2_CARDS_KEEP_1, ActionType.DRAW_3_CARDS_KEEP_1);
                            }
                        }
                        // keep only actions that player can realize
                        List<ActionType> actionsPossibles = List.empty();
                        for (ActionType action : actionsDisponibles) {
                            if ((action == ActionType.DRAW_2_CARDS_KEEP_1) && (pioche.canDraw(2))) { //flagModif
                                actionsPossibles = actionsPossibles.append(ActionType.DRAW_2_CARDS_KEEP_1);
                            } else if (action == ActionType.DRAW_3_CARDS_KEEP_1 && pioche.canDraw(3)) { //flagModif
                                actionsPossibles = actionsPossibles.append(ActionType.DRAW_3_CARDS_KEEP_1);
                            } else {
                                actionsPossibles = actionsPossibles.append(action);
                            }
                        }
                        ActionType typeDAction = joueurCourant.player().controller.selectActionAmong(actionsPossibles.toList());
                        // execute selected action
                        if (typeDAction == ActionType.DRAW_2_CARDS_KEEP_1) { // attention, sorte de doublons de code ////////
                            Set<Card> cartesPiochees = pioche.draw(2);
                            if (!joueurCourant.player().city().has(District.LIBRARY)) {
                                Card keptCard = joueurCourant.player().controller.selectAmong(cartesPiochees);
                                pioche.discard(cartesPiochees.remove(keptCard).toList());
                                cartesPiochees = HashSet.of(keptCard);
                            }
                            joueurCourant.player().add(cartesPiochees);
                        } else if (typeDAction == ActionType.RECEIVE_2_COINS) {
                            joueurCourant.player().add(2);
                        } else if (typeDAction == ActionType.DRAW_3_CARDS_KEEP_1) {
                            Set<Card> cartesPiochees = pioche.draw(3);
                            if (!joueurCourant.player().city().has(District.LIBRARY)) {
                                Card keptCard = joueurCourant.player().controller.selectAmong(cartesPiochees);
                                pioche.discard(cartesPiochees.remove(keptCard).toList());
                                cartesPiochees = HashSet.of(keptCard);
                            }
                            joueurCourant.player().add(cartesPiochees);
                        }
                        actionExecuted(joueurCourant, typeDAction, couplesJoueurPerso);

                        // receive powers from the character            flagModif
                        List<ActionType> powers = (List<ActionType>) joueurCourant.character().getPowers();

                        List<ActionType> actionsBonus = List.empty();
                        for (District d : joueurCourant.player().city().districts()) {

                            actionsBonus = actionsBonus.append(d.getActionBonus());
                                /*if (d == District.SMITHY) {
                                    actionsBonus = actionsBonus.append(ActionType.DRAW_3_CARDS_KEEP_1);
                                }
                                if (d == District.LABORATORY) {
                                    actionsBonus = actionsBonus.append(ActionType.DISCARD_CARD_FOR_2_COINS);
                                }*/
                        }
                        Set<ActionType> actionsPossibles1 = Group.OPTIONAL_ACTIONS
                                .addAll(powers)
                                .addAll(actionsBonus);
                        ActionType actionType11;
                        do {
                            Set<ActionType> availableActionsCopie = actionsPossibles1;
                            // keep only actions that player can realize
                            List<ActionType> possibleActions2 = List.empty();
                            for (ActionType action : availableActionsCopie){
                                System.out.println(action.getDescription());
                                action.canExecute( joueurCourant,  possibleActions2, groupeCoupleJoueurPerso,  pioche);
                                //System.out.println("BALISE - 1 dépassée");
                            }
                            //System.out.println("BALISE 0 dépassée");
                            ActionType typeDAction1 = joueurCourant.player().controller.selectActionAmong(possibleActions2.toList());
                            typeDAction1.execute( joueurCourant,  couplesJoueurPerso, groupeCoupleJoueurPerso, pioche);
                            //System.out.println("BALISE 1 dépassée");
                            actionExecuted(joueurCourant, typeDAction1, couplesJoueurPerso);
                            actionType11 = typeDAction1;
                            actionsPossibles1 = actionsPossibles1.remove(actionType11);
                            //System.out.println("BALISE 2 dépassée");
                        }
                        while (!actionsPossibles1.isEmpty() && actionType11 != ActionType.END_ROUND);
                    }
                }
            }
        }
    }

    public static void afficherResultats(List<Group> roundAssociations)
    {
        System.out.println("Classement: " + roundAssociations.sortBy(a -> Tuple.of(a.player().score(), !a.isMurdered(), a.character))
                .reverse()
                .map(Group::player));
    }

    public static void actionExecuted(Group association, ActionType actionType, List<Group> associations) {
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
