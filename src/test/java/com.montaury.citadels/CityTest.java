package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import io.vavr.collection.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vavr.collection.Set;

import static com.montaury.citadels.district.Card.*;
import static org.assertj.core.api.Assertions.assertThat;

class CityTest {
    public Board board;
    public City city;

    @BeforeEach
    public void setUp(){
        board = new Board();
        city = new City(board);
    }

    @Test
    public void it_should_return_3_for_cost_of_manor_card(){
        Possession possession = new Possession(0, null);

        city.buildDistrict(MANOR_1);

        assertThat(city.score(possession)).isEqualTo(3);
    }

    @Test
    public void it_should_return_11_for_all_district_type(){
        Possession possession = new Possession(0, null);

        city.buildDistrict(MANOR_1); //score += 3
        city.buildDistrict(WATCHTOWER_1); //score +=1
        city.buildDistrict(TAVERN_1); //score += 1
        city.buildDistrict(TEMPLE_1); //score +=1
        city.buildDistrict(HAUNTED_CITY); //score +=2

        assertThat(city.score(possession)).isEqualTo(11);
    }

    @Test
    public void it_should_return_4_for_the_first_player(){
        Possession possession = new Possession(0, null);

        board.mark(city);

        assertThat(city.score(possession)).isEqualTo(4);
    }

    @Test
    public void it_should_return_8_for_dragon_gate(){
        Possession possession = new Possession(0, null);

        city.buildDistrict(DRAGON_GATE);

        assertThat(city.score(possession)).isEqualTo(8);
    }

    @Test
    public void it_should_return_8_for_university(){
        Possession possession = new Possession(0, null);

        city.buildDistrict(UNIVERSITY);

        assertThat(city.score(possession)).isEqualTo(8);
    }

    @Test
    public void it_should_return_6_for_treasury_and_1_gold(){
        Possession possession = new Possession(1, null);

        city.buildDistrict(TREASURY);

        assertThat(city.score(possession)).isEqualTo(6);
    }

    @Test
    public void it_should_return_6_for_map_room_and_1_card(){
        Set<Card> jeuEnMain = HashSet.empty();
        jeuEnMain = jeuEnMain.add(MANOR_1);

        Possession possession = new Possession(0,jeuEnMain);

        city.buildDistrict(MAP_ROOM); //score += 5

        assertThat(city.score(possession)).isEqualTo(6);
    }

    @Test
    public void it_should_return_9_for_complete_city_and_7_district(){
        Possession possession = new Possession(0, null);

        board.mark(new City(new Board()));

        city.buildDistrict(TEMPLE_1);
        city.buildDistrict(TEMPLE_2);
        city.buildDistrict(TEMPLE_3);
        city.buildDistrict(TAVERN_1);
        city.buildDistrict(TAVERN_2);
        city.buildDistrict(TAVERN_3);
        city.buildDistrict(TAVERN_4);

        assertThat(city.score(possession)).isEqualTo(9);
    }

    @Test
    public void it_sould_return_11_for_haunted_city_and_4_other_districts(){
        Possession possession = new Possession(0, null);

        city.buildDistrict(TEMPLE_1);//score += 1
        city.buildDistrict(TAVERN_3);//score += 1
        city.buildDistrict(WATCHTOWER_1);//score += 1
        city.buildDistrict(KEEP_1);//score += 3
        city.buildDistrict(HAUNTED_CITY);//score += 2

        assertThat(city.score(possession)).isEqualTo(11);
    }
}
