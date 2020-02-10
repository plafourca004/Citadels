package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.ComputerController;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import org.assertj.core.internal.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.montaury.citadels.district.Card.*;
import static org.assertj.core.api.Assertions.assertThat;

class CityTest {
    @Test
    public void it_should_return_3_for_cost_of_manor_card(){
        Board board = new Board();
        Player player = new Player("aName", 12, new City(board), new HumanController());

        player.add(3);

        player.buildDistrict(MANOR_1);

        assertThat(player.score()).isEqualTo(3);
    }

    @Test
    public void it_should_return_11_for_all_district_type(){
        Board board = new Board();
        Player player = new Player("aName", 12, new City(board), new HumanController());

        player.add(30);

        player.buildDistrict(MANOR_1); //score += 3
        player.buildDistrict(WATCHTOWER_1); //score +=1
        player.buildDistrict(TAVERN_1); //score += 1
        player.buildDistrict(TEMPLE_1); //score +=1
        player.buildDistrict(HAUNTED_CITY); //score +=2

        assertThat(player.score()).isEqualTo(11);
    }

    @Test
    public void it_should_return_4_for_the_first_player(){
        Board board = new Board();
        Player player = new Player("aName", 12, new City(board), new HumanController());

        board.mark(player.city());

        assertThat(player.score()).isEqualTo(4);
    }

    @Test
    public void it_should_return_8_for_dragon_gate(){
        Board board = new Board();
        Player player = new Player("aName", 12, new City(board), new HumanController());

        player.add(6);

        player.buildDistrict(DRAGON_GATE);

        assertThat(player.score()).isEqualTo(8);
    }

    @Test
    public void it_should_return_8_for_university(){
        Board board = new Board();
        Player player = new Player("aName", 12, new City(board), new HumanController());

        player.add(6);

        player.buildDistrict(UNIVERSITY);

        assertThat(player.score()).isEqualTo(8);
    }

    @Test
    public void it_should_return_7_for_treasury(){
        Board board = new Board();
        Player player = new Player("aName", 12, new City(board), new HumanController());

        player.add(7);

        player.buildDistrict(TREASURY);

        assertThat(player.score()).isEqualTo(7);
    }

    @Test
    public void it_should_return_7_for_map_room(){
        Board board = new Board();
        Player player = new Player("aName", 12, new City(board), new HumanController());

        player.add(6);

        player.addCardInHand(MAP_ROOM);
        player.addCardInHand(TEMPLE_1);
        player.addCardInHand(TEMPLE_2);

        player.buildDistrict(MAP_ROOM); //score += 5

        assertThat(player.score()).isEqualTo(7);
    }

    @Test
    public void it_should_return_10_for_complete_city_and_8_district(){
        Board board = new Board();
        Player player = new Player("aName", 12, new City(board), new HumanController());

        Board boardWinner = new Board();
        Player playerWinner = new Player("aWinnerName", 12, new City(boardWinner), new HumanController());

        board.mark(playerWinner.city());

        player.add(50);

        player.buildDistrict(TEMPLE_1);
        player.buildDistrict(TEMPLE_2);
        player.buildDistrict(TEMPLE_3);
        player.buildDistrict(TAVERN_1);
        player.buildDistrict(TAVERN_2);
        player.buildDistrict(TAVERN_3);
        player.buildDistrict(TAVERN_4);
        player.buildDistrict(TAVERN_5);

        assertThat(player.score()).isEqualTo(10);
    }
}
