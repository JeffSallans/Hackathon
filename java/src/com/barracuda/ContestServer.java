/**
 * programming contest dufus
 */
package com.barracuda;

import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.barracuda.Bidding.GameMode;

import java.lang.Math;
import java.util.*;
import java.io.*;

public class ContestServer {
    
    Bidding bet = new Bidding();
    int player_num;
    GameMode gamemode;
    
    public int[][] ref_board = new int[7][7];
    public int[][] place_board = new int[7][7];
    
    //occupancy lists
    List<Integer> own_occ = new ArrayList<>();
    List<Integer> opp_occ = new ArrayList<>();

   
    private static Log log = LogFactory.getLog(ContestServer.class);

    public Boolean ping() {
        log.info("ping");
        return true;
    }

    public int init_game(Map state) {
        log.info("init_game");
        log.info(state.toString());
        
        System.out.print("init player_num: " + player_num);

        return 0;
    }

    public Integer get_bid(List<Integer> offer, Map state) {
        log.info("get_bid");
        log.info(offer.toString());
        
        get_vars(offer, state);
        
        System.out.print("playernum: " + player_num);
        
        get_begin_var(state, (int) state.get("idx"));
        
        
        return 4;
        //return bet.calc_bet();
    }

    public Integer make_choice(List<Integer> offer, Map state) {
        log.info("make_choice");
        get_vars(offer, state);
        
        return offer.get(0);
    }

    public int move_result(Map result) {
        log.info("move_result");
        log.info(result.toString());
        
        return 0;
    }

    public int game_result(Map result) {
        log.info("game_result");
        log.info(result.toString());
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int port = 9995;
        WebServer webServer = new WebServer(port);
        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.setVoidMethodEnabled(true);

        // class key = null for no prefix on method names
        phm.addHandler(null, ContestServer.class);
        xmlRpcServer.setHandlerMapping(phm);

        log.info("starting XML-RPC server at /RPC2 on port " + port);
        webServer.start();
        
    }
    
    public void debug_table(int[][] b) {
     
        String output = "" + '\n';
        for (int[] row : b) {
            for (int cell : row) {
                output += cell + ", ";
            }
            output += '\n';
        }
        System.out.print(output);
    }
    
    //EFFECT: Returns 7x7 board
    public void get_board(Map state, String type, int x, int y) {
                
        Object[] all = (Object[]) state.get(type);
        for( int i = 0; i < x; i++) {
            Object[] row = (Object[]) all[i];
            for( int j = 0; j < y; j++) {
                ref_board[i][j] = (int) row[j];
            }
        }
    }
    
    //Called at the beginning of each round
    public void get_begin_var(Map state, int playernum) 
    {
        if (own_occ.size() > 0)
        {
            gamemode = GameMode.middle;
        }
        bet.set_things(gamemode, playernum ,(int) state.get("turn"), (int) state.get("credits"));    
    }
    
    public void get_vars(List<Integer> offer, Map state) {
        
        player_num = (int) state.get("idx");
        gamemode = GameMode.start;

        get_board(state, "board", 7, 7);
        
        //zero out own
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                place_board[i][j] = -1;
            }
        }
        
        //Get
        Element temp_elt;
        int j;
        Object[] all = (Object[]) state.get("owned_squares");
        for (int i = 0; i < 2; i++) {
            Object[] row = (Object[]) all[i];
            j = 0;
            while (row != null && row[j] != null ) {
                temp_elt = new Element(i, (int)row[j], ref_board);
                place_board[temp_elt.x][temp_elt.y] = i;
                j++;
            }
        }
        
        debug_table(place_board);
        
    }
}
