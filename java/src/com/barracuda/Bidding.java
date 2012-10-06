package com.barracuda;

public class Bidding 
{
    int round_counter;
    int own_credits;
    int opp_creditsMax;
    int own_desire;
    int opp_desire;
    
    public void set_things(int rounds, int credits)
    {
        this.round_counter = rounds;
        this.own_credits = credits;
    }

}
