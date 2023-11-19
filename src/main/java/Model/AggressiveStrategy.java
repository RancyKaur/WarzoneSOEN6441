package Model;

import java.util.Random;

/**
 * This strategy  is aggressive form that focuses on centralization of forces in one of its countries
 * and then attacks the defenders' country.
 * Player using this strategy deploys on its strongest country, then always attack with its strongest country,
 * then moves its armies in order to maximize aggregation of forces in one country.
 */
public class AggressiveStrategy extends PlayStrategy{
    private int d_OrderVal,d_MaxArmies;
    Country d_StrongestCountry,d_DefendingCountry,d_MoveFromCountry,d_InitialCountry;
    boolean d_IsTest;
    public int d_TestReinforceArmies;

    /**
     * default Constructor for PlayerStrategy
     *
     * @param p_player player object
     * @param p_map    map object
     */
    public AggressiveStrategy(Player p_player, WargameMap p_map) {
        super(p_player, p_map);
        d_StrongestCountry = null;
        d_DefendingCountry = null;
        d_InitialCountry = null;
        d_MaxArmies = 0;
        d_IsTest = false;
    }


    /**
     * Finds country with highest number of armies owned by player
     * strongest CountryDetails object
     */
    private void findStrongestCountryDetails()
    {
        int l_maxArmies = 0, l_numArmies;
        for(Country l_countryDetails : this.d_Player.getOwnedCountries().values()) {
            l_numArmies = l_countryDetails.getNumberOfArmies();
            if( l_numArmies > l_maxArmies) {
                l_maxArmies = l_numArmies;
                d_StrongestCountry = l_countryDetails;
            }
        }

        if(l_maxArmies == 0)
            d_StrongestCountry = findInitialCountry();
    }

    /**
     *toAttack function implements attacks to the
     * neighboring country from strongest country
     * @return country to attack
     */
    protected Country toAttack()
    {
        if(d_StrongestCountry!=null) {
            for (Country l_neighborCountry : d_StrongestCountry.getNeighbours().values()) {
                if (!this.d_Player.getOwnedCountries().containsKey(l_neighborCountry.getCountryName())) {
                    d_DefendingCountry = l_neighborCountry;
                    return d_DefendingCountry;
                }
            }
        }
        //if no neighbors found to attack
        return null;
    }

    /**
     * This function finds the strongest country from where the attack
     * takes place.
     * @return country to attack from
     */
    protected Country toAttackFrom()
    {
        findStrongestCountryDetails();
        return d_StrongestCountry;
    }

    /**
     * This function moves the armies to the strongest country
     *to centralize the forces.
     * @return source country for advance
     */
    protected Country toMoveFrom()
    {
        int l_maxArmies =0;
        findStrongestCountryDetails();

        if(d_StrongestCountry!=null) {
            for (Country l_neighborCountry : d_StrongestCountry.getNeighbours().values()) {
                if (this.d_Player.getOwnedCountries().containsKey(l_neighborCountry.getCountryName())) {
                    int l_currentCountryArmies = l_neighborCountry.getNumberOfArmies();
                    if (l_currentCountryArmies >= l_maxArmies) {
                        l_maxArmies = l_currentCountryArmies;
                        d_MoveFromCountry = l_neighborCountry;
                        d_MaxArmies = l_maxArmies;
                    }
                }
            }
        }
        if(l_maxArmies == 0)
        {
            d_MoveFromCountry = setInitialMoveFromCountry();
            return d_MoveFromCountry;
        }
        else
            return d_MoveFromCountry;
    }

    /**
     *  This function  sets  the randomOrderValue which can be 0,1,2
     *  These values 0,1,2 will be used for creating orders
     */
    public void setRandomOrderValue()
    {
        Random l_random =new Random();
        d_OrderVal = l_random.nextInt(3);
    }

    /**
     * Sets the random value for tests
     * @param p_random input random value
     */
    public void setTestOrderValue(int p_random)
    {
        d_OrderVal = p_random;
    }

    /**
     * Sets the d_IsTest value to true in testcases
     * @param p_isTest input boolean value
     */
    public void isTest(boolean p_isTest)
    {
        d_IsTest = p_isTest;
    }

    /**
     * Finds the initial country where armies can be deployed.
     * @return Initial random country
     */
    private Country findInitialCountry() {
        for(Country l_country : this.d_Player.getOwnedCountries().values())
        {
            for (Country l_neighborCountry : l_country.getNeighbours().values()) {
                if(!this.d_Player.getOwnedCountries().containsKey(l_neighborCountry.getCountryName())){
                    d_InitialCountry = l_country;
                    return d_InitialCountry;
                }
            }

        }
        return d_InitialCountry;
    }

    /**
     * sets the initial country where armies can be moved to strongest country.
     * @return Initial random country neighbor to strongest country
     */
    private Country setInitialMoveFromCountry() {
        int l_maxArmies = 0;
        for(Country l_country : this.d_Player.getOwnedCountries().values())
        {
            for (Country l_neighborCountry : l_country.getNeighbours().values()) {
                if(this.d_Player.getOwnedCountries().containsKey(l_neighborCountry.getCountryName())){
                    d_MoveFromCountry = l_neighborCountry;
                    d_MoveFromCountry.setNumberOfArmies(2);
                    d_MoveFromCountry = l_country;
                    l_maxArmies = d_MoveFromCountry.getNumberOfArmies();
                    d_MaxArmies =l_maxArmies;
                    return d_MoveFromCountry;
                }
            }

        }
        return null;
    }

    @Override
    public Order createOrder() {

        Country l_attackingCountry,l_defendingCountry,l_moveFromCountry;
        l_attackingCountry = toAttackFrom(); //strongest country
        l_defendingCountry = toAttack();     //neighbor country not belonging to player
        l_moveFromCountry  = toMoveFrom();   //neighbor of strongest country with max armies

        System .out.println("Countries owned by Aggressive player "+d_Player.getOwnedCountries().size());
        System .out.println("Armies owned by Aggressive player "+d_Player.getOwnedArmies());
        //TODO
        if (d_Player.getOwnedCountries().size() == 0){
            //TODO
        }

        Random l_random = new Random();

        if(!d_IsTest)
            setRandomOrderValue();

        switch(d_OrderVal) {
            case 0:
                if(d_Player.getOwnedArmies() == 0)
                {
                    System.out.println("Reinforcement armies are all used by player ");
                    break;
                }
                int l_reinforceArmies = d_Player.getOwnedArmies();
                System.out.println(" Aggressive reinforce armies owned by player"+d_Player.getOwnedArmies());
                d_TestReinforceArmies = l_reinforceArmies;
                if(d_Player.getOwnedArmies() == 1)
                    l_reinforceArmies = 1;
                if (l_reinforceArmies != 0) {
                    if(d_StrongestCountry!=null) {
                        System.out.println("Armies deployed on country :" + d_StrongestCountry.getCountryName() + " " + l_reinforceArmies);
                        d_Player.setOwnedArmies(d_Player.getOwnedArmies() - l_reinforceArmies);
                        return new Deploy(d_Player, d_StrongestCountry.getCountryName(), l_reinforceArmies);
                    }else{
                        findInitialCountry();
                        if(d_InitialCountry!=null) {
                            d_StrongestCountry = d_InitialCountry;
                            System.out.println("Armies deployed on country :" + d_InitialCountry.getCountryName() + " " + l_reinforceArmies);
                            d_Player.setOwnedArmies(d_Player.getOwnedArmies() - l_reinforceArmies);
                            return new Deploy(d_Player, d_InitialCountry.getCountryName(), l_reinforceArmies);
                        }
                    }
                }
                else
                    System.out.println("Invalid value for deploying reinforcement armies : "+l_reinforceArmies);
                return null;

            case 1:
                //create attack Order
                if(l_attackingCountry ==null) {
                    //then the attacking country or strongest country will be null if doesnt own any countries
                    if (d_Player.getOwnedCountries() == null){
                        System.out.println("The countries owned by player is null ");
                        //TODO armies can be set to zero in such a case
                    }
                    return null;
                }
                else{
                    if (l_attackingCountry.getNumberOfArmies() == 0) {
                        System.out.println("The number of armies in strongest country is 0 ,deploy before advance");
                        return null;
                    }
                    if (l_defendingCountry != null) {

                        //check if a player has Bomb card.
                        //If yes randomly it can be used otherwise attack order takes place
                        if (d_Player.checkCardExists("Bomb")) {
                            Random l_randomCard = new Random();
                            int l_value = l_randomCard.nextInt(2);
                            if (l_value == 0) {
                                d_Player.removeCard("Bomb");
                                return new Bomb(d_Player, l_defendingCountry.getcountryOwnerPlayer(), l_defendingCountry.getCountryName());
                            } else
                                break;
                        }
                        int l_randomVal;
                        if (l_attackingCountry.getNumberOfArmies() > 0)
                            l_randomVal = l_random.nextInt(l_attackingCountry.getNumberOfArmies());
                        else
                            return null;
                        if (l_randomVal != 0)
                            return new Advance(d_Player, l_attackingCountry.getCountryName(), l_defendingCountry.getCountryName(), l_randomVal, d_DefendingCountry.getcountryOwnerPlayer());
                        else
                            return null;
                    } else
                        System.out.println("Neighbor does not exist for this country :" + l_attackingCountry.getCountryName());
                    return null;
                }

            case 2:
                //move maximum armies from one country to strongest country
                if(l_moveFromCountry!=null) {
                    //check if a player has Airlift card.
                    //If yes randomly it can be used otherwise advance order takes place
                    if(d_Player.checkCardExists("Airlift")) {
                        Random l_randomCard = new Random();
                        int l_value = l_randomCard.nextInt(2);
                        if (l_value == 0) {
                            d_Player.removeCard("Airlift");
                            return new Airlift(d_Player, l_moveFromCountry.getCountryName(), l_attackingCountry.getCountryName(), d_MaxArmies);
                        } else
                            break;
                    }
                    return new Advance(d_Player, l_moveFromCountry.getCountryName(), l_attackingCountry.getCountryName(), d_MaxArmies-1, l_attackingCountry.getcountryOwnerPlayer());
                } else
                    return null;


        }
        return null;
    }

}

