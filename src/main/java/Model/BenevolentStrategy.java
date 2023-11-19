package Model;

import java.util.Collection;
import java.util.Random;

/**
 * This deploys its armies on weaker countries of the player that has lesser armies
 * and advances armies to reinforce its weak countries
 * In this strategy player never attacks
 */

public class BenevolentStrategy extends PlayStrategy{

    Country d_SourceCountry,d_WeakCountry;
    Random random = new Random();

    /**
     * default Constructor for PlayerStrategy
     *
     * @param p_player player object
     * @param p_map    map object
     */
    public BenevolentStrategy(Player p_player, WargameMap p_map) {
        super(p_player, p_map);
        d_SourceCountry = null;
        d_WeakCountry = null;
    }

    /**
     * To find the country with least number of armies of the player
     * @return The country with least armies
     */
    private Country findWeakestCountryDetails() {
        Collection<Country> l_countries=d_Player.getOwnedCountries().values();
        int l_minArmies = 100;
        for(Country l_countryDetails : l_countries) {
            int l_numArmies = l_countryDetails.getNumberOfArmies();
            if( l_numArmies < l_minArmies) {
                l_minArmies = l_numArmies;
                d_WeakCountry = l_countryDetails;
            }
        }

        if (d_WeakCountry == null) {
            d_WeakCountry = d_Player.getOwnedCountries().get(0);
        }
        return d_WeakCountry;
    }

    /**
     * This function returns the source country from which
     * the armies need to be moved
     * @return the country from which armies advance
     */
    @Override
    protected Country toMoveFrom() {
        //d_SourceCountry=null;

        Object[] values = d_Player.getOwnedCountries().values().toArray();
        int totalC = values.length - 1;
        if(d_Player.getOwnedCountries().size() != 0){
            Object randomValue = values[random.nextInt(totalC + 1)];
            d_SourceCountry = (Country) randomValue;
        }

        return d_SourceCountry;
    }

    /**
     * toAdvance function implements advance to the
     * neighboring country from random owned country
     * @return country to advance
     */
    protected Country toAdvance()
    {
        if(d_SourceCountry!=null) {
            findWeakestCountryDetails();
            for (Country l_neighborCountry : d_SourceCountry.getNeighbours().values()) {
                if (this.d_Player.getOwnedCountries().containsKey(l_neighborCountry.getCountryName()) && (l_neighborCountry == d_WeakCountry)) {
                    return d_WeakCountry;
                }
            }
        } else{
            return null;
        }

        //if no neighbors found to advance
        return null;
    }

    /**
     * Creates a Random Order as per Strategy
     * @return random Order
     */
    @Override
    public Order createOrder() {

        Country l_sourceCountry, l_advanceCountry;
        l_sourceCountry = toMoveFrom();
        l_advanceCountry = toAdvance();

        int l_rndOrder = random.nextInt(2);
        int rnd_num_of_armies_pool = d_Player.getOwnedArmies();
        Country l_cD = findWeakestCountryDetails();

        switch(l_rndOrder) {
            case 0:
                if (l_cD != null) {
                    //deploy on weak country
                    d_Player.setOwnedArmies(0);
                    return new Deploy(d_Player, l_cD.getCountryName(), rnd_num_of_armies_pool);
                } else {
                    System.out.println("Cannot be deployed on weak country");
                }
                break;
            case 1:
                //create advance Order
                if (l_sourceCountry != null) {
                    if (l_sourceCountry.getNumberOfArmies() == 0) {
                        System.out.println("The number of armies in strongest country is 0 ,deploy before advance");
                        return null;
                    }

                    if (l_advanceCountry != null) {
                        int l_randomVal;
                        if (l_sourceCountry.getNumberOfArmies() > 0)
                            l_randomVal = random.nextInt(l_sourceCountry.getNumberOfArmies());
                        else
                            return null;

                        if (l_randomVal != 0)
                            return new Advance(d_Player, l_sourceCountry.getCountryName(), l_advanceCountry.getCountryName(), l_randomVal, l_advanceCountry.getcountryOwnerPlayer());
                        else
                            System.out.println("Neighbor does not exist for this country" + l_sourceCountry.getCountryName());
                        break;
                    }
                } else {
                    return null;
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

}

