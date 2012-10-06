package com.barracuda;

public class Element {
    
    public int owner;
    public int value;
    
    //x is vertical
    public int x;
    //y is horizontal
    public int y;
    
    public int offset;
    
    //REQUIRES: board is 7x7
    public Element(int in_owner, int in_value, int[][] board) {
        
        owner = in_owner;
        value = in_value;
        
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                if (board[i][j] == value) {
                    //Leave for loop
                    x = i;
                    y = j;
                    break;
                }
            }
        }
    }
    
    //Using a breath-first search to find the shortest path
    public int shortest_path(int dest, int[][] placement_board) {
        
        return 0;
    }
    
    public int longest_link(){
        
        //push row/col number if connected
        
        //find the max and min row/col difference
        
        return 0;
    }

}
