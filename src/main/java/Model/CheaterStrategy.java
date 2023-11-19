package Model;

import Model.Player;

import java.util.HashMap;

/**
 * This strategy is cheater strategy where the player conquers neighbour countries and
 * on all neighbour countries it doubles its armies on its countries that have enemy neighbors.
 */
public class CheaterStrategy extends PlayStrategy{
    /**
     * Represents object of  RunGameEngine class
     */
    Player d_OtherPlayer;

    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     * @param p_player player that will be cheater player
     * @param p_map map that is going to be loaded.
     */
    public CheaterStrategy(Player p_player, WargameMap p_map) {
        super(p_player, p_map);
    }

    @Override
    public Order createOrder() {

        this.d_Player.setOwnedArmies(0);

        HashMap<String, Country> l_countryList = new HashMap<String, Country>();
        for(String l_s : this.d_Player.getOwnedCountries().keySet()){
            l_countryList.put(l_s, this.d_Player.getOwnedCountries().get(l_s));
        }

        //conquering the enemy neighbor
        for(Country l_countries : l_countryList.values()){
            for(Country l_neighbours : l_countries.getNeighbours().values()){
                if(!this.d_Player.getOwnedCountries().containsKey(l_neighbours.getCountryName().toLowerCase())) {
                    d_OtherPlayer= l_neighbours.getcountryOwnerPlayer();
                    this.d_Player.getOwnedCountries().put(l_neighbours.getCountryName().toLowerCase(),l_neighbours);
                    d_OtherPlayer.getOwnedCountries().remove(l_neighbours.getCountryName().toLowerCase());
                    d_Player.addCard();
                    l_neighbours.setcountryOwnerPlayer(this.d_Player);
                    System.out.println("Cheater has owned neighbour country: "+ l_neighbours.getCountryName() );

                }
            }
        }

        //double the armies that have neighbor enemy country
        for(Country l_newcountries : this.d_Player.getOwnedCountries().values()){
            for(Country l_newneighbours : l_newcountries.getNeighbours().values()){
                if(!(this.d_Player.getOwnedCountries().containsKey(l_newneighbours.getCountryName().toLowerCase()))) {
                    l_newcountries.setNumberOfArmies(l_newcountries.getNumberOfArmies() * 2);
                    System.out.println("Cheater has now doubled army on "+ l_newcountries.getCountryName() + " country");
                }

            }
        }

        return null;
    }

    @Override
    protected Country toAttackFrom() {
        return null;
    }

    @Override
    protected Country toAttack() {
        return null;
    }

    @Override
    protected Country toMoveFrom() {
        return null;
    }


}

