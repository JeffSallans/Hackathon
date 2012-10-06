package com.barracuda;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;


public class Bidding 
{
    int playerNum;
    int round_counter;
    int own_credits;
    int opp_creditsMax = 98;
    int own_desire;
    int opp_desire;
    int current_bet;
    List<Integer> norm_range = new ArrayList<>();
    
    //Random
    int funRand = 0;
    
    //Weighting stats
    int overall_weight = 0;
    int number_linkedWeight = 0;
    int desire = -1;
    int index = -1;
    int opp_shortest_pathWeight = 0;
    int own_shortest_pathWeight = 0;
    
    enum GameMode
    {
        start,
        middle,
        end;
    }
    GameMode gamemode;
    
    public Boolean can_bet(int own_credits, int current_bet)
    {
        if (current_bet < own_credits)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
     
    public void set_things(GameMode gamestate,int playerNumber, int rounds, int credits)
    {
        // sets values availble from other function
        this.playerNum = playerNumber;
        this.gamemode = gamestate;
        this.round_counter = rounds;
        this.own_credits = credits;
        
        // initializes the normal range of numbers
        for (int i = 0; i < 7; i++)
        {
            if (i + 7 < 13)
            {
                norm_range.add(i + 7);
            }
            else
            {
                norm_range.add(i + 7 + 3);
            }
        }
    }
    
    public Integer calc_index_start(int own_shortest_path, int own_offset,
                             int number_linked, int opp_shortest_path)
    {
        // handling number of elements linked together
        if (number_linked < 4)
        {
            number_linkedWeight = 5;
        }
        else if (number_linked == 4)
        {
            number_linkedWeight = 4;
        }
        else if (number_linked == 5)
        {
            number_linkedWeight = 2;
        }
        else if (number_linked == 6)
        {
            number_linkedWeight = 1;            
        }
        
        // handling own shortest path
        if (own_shortest_path == 0)
        {
            // shouldn't happen
        }
        else
            own_shortest_pathWeight = own_shortest_path;
        
        // handling opp shortest path
        if (opp_shortest_path == 0)
            opp_shortest_pathWeight = 1;
        else
            opp_shortest_pathWeight = opp_shortest_path;

        
        // computing overall weight
        overall_weight = own_shortest_path + 2*opp_shortest_pathWeight + 
                         2*(own_offset + 1) + number_linkedWeight;
        
        
        if (own_shortest_path == 1 && own_offset == 0)
        {
            return 9;
        }
        if (overall_weight < 9)
            return 8;
        else if (overall_weight < 13)
            return 7;
        else if (overall_weight < 16)
            return 6;
        else if (overall_weight < 19)
            return 5;
        else if (overall_weight < 22)
            return 4;
        else if (overall_weight < 25)
            return 3;
        else if (overall_weight < 28)
            return 2;
        else if (overall_weight < 30)
            return 1;
        else
            return 0;

        
    }
    
    public Integer calc_bet(int own_shortest_path, int own_offset,
                            int number_linked, int opp_shortest_path,
                            Boolean winning_move)
    {
        // Determine if winning move
        if(winning_move)
        {
            gamemode = GameMode.end;
        }
        
        if(gamemode == GameMode.middle)
        {
            if (Math.random() < .5)
                funRand = (int) (2 * Math.random());   
            else
                funRand = -(int) (2 * Math.random());
            desire = calc_index_start(own_shortest_path, own_offset, number_linked, opp_shortest_path);
            //Most often to happen
            
            if (desire == 9)
            {
                current_bet = norm_range.get(norm_range.size() - 1);
            }
            else if (desire == 8)
            {
                current_bet = norm_range.get(norm_range.size() - 2) + funRand;    
            }
            else if (desire == 7)
            {
                current_bet = norm_range.get(norm_range.size() - 4) + funRand;
            }
            else if (desire == 6)
            {
                current_bet = norm_range.get(norm_range.size() - 5) + funRand;                
            }
            else if (desire == 5)
            {
                current_bet = norm_range.get(norm_range.size() - 6) + funRand;               
            }
            else if (desire == 4)
            {
                current_bet = norm_range.get(norm_range.size() - 7) + funRand;              
            }
            else if (desire == 3)
            {
                current_bet = norm_range.get(norm_range.size() - 8) + funRand;               
            }
            else if (desire == 2)
            {
                current_bet = norm_range.get(norm_range.size() - 9) + funRand;               
            }
            else if (desire == 1)
            {
                current_bet = norm_range.get(0) + funRand;    
            }
            else if (desire == 0)
            {
                current_bet = 0;
            }
        }
        else if(gamemode == GameMode.start)
        {
            current_bet = 15;
        }
        else if (gamemode == GameMode.end)
        {
            current_bet = own_credits;
        }
        
        if(can_bet(current_bet, own_credits))
        {
            return current_bet;
        }
        else
        {
            //well fuck
            return own_credits;
        }
    }
    
    public void end_round_calc()
    {
        opp_creditsMax = 98 - (current_bet + 1);
    }

}
