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
    
    //Weighting stats
    int overall_weight = 0;
    int number_linkedWeight = 0;
    
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
    
    public void calc_desires(int own_shortest_path, int own_offset,
                             int number_linked)
    {
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
        
        if (own_shortest_path == 0)
        {
            //fuck this im not throwing shit jeff
        }          
        if (playerNum == 1)
        {
            
        }
        overall_weight = own_shortest_path + 2*own_offset + number_linkedWeight;
        
        

        
        
    }
    
    public Integer calc_bet(int own_shortest_path, int own_offset,
                            int number_linked, Boolean winning_move)
    {
        // Determine if winning move
        if(winning_move)
        {
            gamemode = GameMode.end;
        }
        
        
        if(gamemode == GameMode.middle)
        {
            calc_desires(own_shortest_path, own_offset, number_linked);
            
            //Most often to happen

        }
        else if(gamemode == GameMode.start)
        {
            current_bet = 15;
        }
        else
        {
            current_bet = own_credits;
        }
        
        
        
        return current_bet;
    }
    
    public void end_round_calc()
    {
        opp_creditsMax = 98 - (current_bet + 1);
    }

}
