package com.barracuda;

import java.util.*;

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
    //EFFECT: returns -1 if invalid path
    //REQUIRE: placement_board empty spots are -1
    public int shortest_path(int dest, int[][] placement_board, int[][] ref_board) {
        
        //Check if dest is value
        if (dest == value) {
            return 0;
        }
        
        Element temp_val;
        Vector< Element > queue = new Vector< Element >();
        
        //add value to the first position in the queue
        queue.add(new Element(-1, value, ref_board));
        placement_board[x][y] = 1;
        
        do {
            //pop queue element
            temp_val = queue.remove(0);
            
            //Check if dest
            if (temp_val.value == dest) {
                break;
            }
            
            //Check and enqueue valid adjacent
            
            //north
            if (temp_val.x > 0 && placement_board[temp_val.x - 1][temp_val.y] == -1) {
                
                //Mark as visited with current distance
                placement_board[temp_val.x - 1][temp_val.y] = placement_board[temp_val.x][temp_val.y] + 1;
                queue.add(new Element(-1, ref_board[temp_val.x - 1][temp_val.y], ref_board));
            }
            //south
            else if (temp_val.x < 6 && placement_board[temp_val.x + 1][temp_val.y] == -1) {
                
                //Mark as visited with current distance
                placement_board[temp_val.x + 1][temp_val.y] = placement_board[temp_val.x][temp_val.y] + 1;
                queue.add(new Element(-1, ref_board[temp_val.x + 1][temp_val.y], ref_board));
            }
            //east
            else if (temp_val.y < 6 && placement_board[temp_val.x][temp_val.y + 1] == -1) {
                
                //Mark as visited with current distance
                placement_board[temp_val.x][temp_val.y + 1] = placement_board[temp_val.x][temp_val.y] + 1;
                queue.add(new Element(-1, ref_board[temp_val.x][temp_val.y + 1], ref_board));
            }
            //west
            else if (temp_val.y > 0 && placement_board[temp_val.x][temp_val.y - 1] == -1) {
                
                //Mark as visited with current distance
                placement_board[temp_val.x][temp_val.y - 1] = placement_board[temp_val.x][temp_val.y] + 1;
                queue.add(new Element(-1, ref_board[temp_val.x][temp_val.y - 1], ref_board));
            }
            //ne
            else if (temp_val.x > 0 && temp_val.y < 6 && placement_board[temp_val.x - 1][temp_val.y + 1] == -1) {
                
                //Mark as visited with current distance
                placement_board[temp_val.x - 1][temp_val.y + 1] = placement_board[temp_val.x][temp_val.y] + 1;
                queue.add(new Element(-1, ref_board[temp_val.x - 1][temp_val.y + 1], ref_board));
            }
            //nw
            else if (temp_val.x > 0 && temp_val.y > 0 && placement_board[temp_val.x - 1][temp_val.y - 1] == -1) {
                
                //Mark as visited with current distance
                placement_board[temp_val.x - 1][temp_val.y - 1] = placement_board[temp_val.x][temp_val.y] + 1;
                queue.add(new Element(-1, ref_board[temp_val.x - 1][temp_val.y - 1], ref_board));
            }
            //se
            else if (temp_val.x < 6 && temp_val.y < 6 && placement_board[temp_val.x + 1][temp_val.y + 1] == -1) {
                
                //Mark as visited with current distance
                placement_board[temp_val.x + 1][temp_val.y + 1] = placement_board[temp_val.x][temp_val.y] + 1;
                queue.add(new Element(-1, ref_board[temp_val.x + 1][temp_val.y + 1], ref_board));
            }
            //sw
            else if (temp_val.x < 6 && temp_val.y > 0 && placement_board[temp_val.x + 1][temp_val.y - 1] == -1) {
                
                //Mark as visited with current distance
                placement_board[temp_val.x + 1][temp_val.y - 1] = placement_board[temp_val.x][temp_val.y] + 1;
                queue.add(new Element(-1, ref_board[temp_val.x + 1][temp_val.y - 1], ref_board));
            }
        }
        while(!queue.isEmpty());
        //While the queue is not empty or found the dest
        
        
        //Check if found
        if (temp_val.value == dest) {
            return placement_board[temp_val.x][temp_val.y];
        }
        else {
            return -1;
        }
    }
    
    //Using a breath-first search to find the shortest path
    //EFFECT: returns -1 if invalid path will move through owned blocks and not count it as a length
    //REQUIRE: placement_board empty spots are -1
    //          placement_board self owned spots are 0
    public int ghost_shortest_path(int dest, int[][] placement_board, int[][] ref_board) {
        
        //Check if dest is value
        if (dest == value) {
            return 0;
        }
        
        //Re-assign values in placement_board
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                
                //Check if self owned re assigned to -2
                if (placement_board[i][j] == 0) {
                    placement_board[i][j] = -2;
                }
            }
        }
        
        Element temp_val;
        Vector< Element > queue = new Vector< Element >();
        
        //add value to the first position in the queue
        queue.add(new Element(-1, value, ref_board));
        placement_board[x][y] = 1;
        
        do {
            //pop queue element
            temp_val = queue.remove(0);
            
            //Check if dest
            if (temp_val.value == dest) {
                break;
            }
            
            //Check and enqueue valid adjacent
            
            //north
            if (temp_val.x > 0 && placement_board[temp_val.x - 1][temp_val.y] <= -1) {
                
                //Mark as visited with current distance
                if (placement_board[temp_val.x - 1][temp_val.y] == -1) {
                    placement_board[temp_val.x - 1][temp_val.y] = placement_board[temp_val.x][temp_val.y] + 1;
                    queue.add(new Element(-1, ref_board[temp_val.x - 1][temp_val.y], ref_board));
                }
                //placement_board is -2
                else {
                    //self owned spots are not counted with location
                    placement_board[temp_val.x - 1][temp_val.y] = placement_board[temp_val.x][temp_val.y];
                    //add to front of queue
                    queue.add(0, new Element(-1, ref_board[temp_val.x - 1][temp_val.y], ref_board));
                }
            }
            //south
            else if (temp_val.x < 6 && placement_board[temp_val.x + 1][temp_val.y] <= -1) {
                
                //Mark as visited with current distance
                if (placement_board[temp_val.x + 1][temp_val.y] == -1) {
                    placement_board[temp_val.x + 1][temp_val.y] = placement_board[temp_val.x][temp_val.y] + 1;
                    queue.add(new Element(-1, ref_board[temp_val.x + 1][temp_val.y], ref_board));
                }
                //placement_board is -2
                else {
                    placement_board[temp_val.x + 1][temp_val.y] = placement_board[temp_val.x][temp_val.y];
                    queue.add(0, new Element(-1, ref_board[temp_val.x + 1][temp_val.y], ref_board));
                }
            }
            //east
            else if (temp_val.y < 6 && placement_board[temp_val.x][temp_val.y + 1] <= -1) {
                
                //Mark as visited with current distance
                if (placement_board[temp_val.x][temp_val.y + 1] == -1) {
                    placement_board[temp_val.x][temp_val.y + 1] = placement_board[temp_val.x][temp_val.y] + 1;
                    queue.add(new Element(-1, ref_board[temp_val.x][temp_val.y + 1], ref_board));
                }
                //placement_board is -2
                else {
                    placement_board[temp_val.x][temp_val.y + 1] = placement_board[temp_val.x][temp_val.y];
                    queue.add(0, new Element(-1, ref_board[temp_val.x][temp_val.y + 1], ref_board));
                }
            }
            //west
            else if (temp_val.y > 0 && placement_board[temp_val.x][temp_val.y - 1] <= -1) {
                
                //Mark as visited with current distance
                if (placement_board[temp_val.x][temp_val.y - 1] == -1) {
                    placement_board[temp_val.x][temp_val.y - 1] = placement_board[temp_val.x][temp_val.y] + 1;
                    queue.add(new Element(-1, ref_board[temp_val.x][temp_val.y - 1], ref_board));
                }
                else {
                    placement_board[temp_val.x][temp_val.y - 1] = placement_board[temp_val.x][temp_val.y];
                    queue.add(0, new Element(-1, ref_board[temp_val.x][temp_val.y - 1], ref_board));
                }
            }
            //ne
            else if (temp_val.x > 0 && temp_val.y < 6 && placement_board[temp_val.x - 1][temp_val.y + 1] <= -1) {
                
                //Mark as visited with current distance
                if (placement_board[temp_val.x - 1][temp_val.y + 1] == -1) {
                    placement_board[temp_val.x - 1][temp_val.y + 1] = placement_board[temp_val.x][temp_val.y] + 1;
                    queue.add(new Element(-1, ref_board[temp_val.x - 1][temp_val.y + 1], ref_board));
                }
                else {
                    placement_board[temp_val.x - 1][temp_val.y + 1] = placement_board[temp_val.x][temp_val.y];
                    queue.add(0, new Element(-1, ref_board[temp_val.x - 1][temp_val.y + 1], ref_board));
                }
            }
            //nw
            else if (temp_val.x > 0 && temp_val.y > 0 && placement_board[temp_val.x - 1][temp_val.y - 1] <= -1) {
                
                //Mark as visited with current distance
                if (placement_board[temp_val.x - 1][temp_val.y - 1] == -1) {
                    placement_board[temp_val.x - 1][temp_val.y - 1] = placement_board[temp_val.x][temp_val.y] + 1;
                    queue.add(new Element(-1, ref_board[temp_val.x - 1][temp_val.y - 1], ref_board));
                }
                else {
                    placement_board[temp_val.x - 1][temp_val.y - 1] = placement_board[temp_val.x][temp_val.y];
                    queue.add(0, new Element(-1, ref_board[temp_val.x - 1][temp_val.y - 1], ref_board));
                }
            }
            //se
            else if (temp_val.x < 6 && temp_val.y < 6 && placement_board[temp_val.x + 1][temp_val.y + 1] <= -1) {
                
                //Mark as visited with current distance
                if (placement_board[temp_val.x + 1][temp_val.y + 1] == -1) {
                    placement_board[temp_val.x + 1][temp_val.y + 1] = placement_board[temp_val.x][temp_val.y] + 1;
                    queue.add(new Element(-1, ref_board[temp_val.x + 1][temp_val.y + 1], ref_board));
                }
                else {
                    placement_board[temp_val.x + 1][temp_val.y + 1] = placement_board[temp_val.x][temp_val.y];
                    queue.add(0, new Element(-1, ref_board[temp_val.x + 1][temp_val.y + 1], ref_board));
                }
            }
            //sw
            else if (temp_val.x < 6 && temp_val.y > 0 && placement_board[temp_val.x + 1][temp_val.y - 1] <= -1) {
                
                //Mark as visited with current distance
                if (placement_board[temp_val.x + 1][temp_val.y - 1] == -1) {
                    placement_board[temp_val.x + 1][temp_val.y - 1] = placement_board[temp_val.x][temp_val.y] + 1;
                    queue.add(new Element(-1, ref_board[temp_val.x + 1][temp_val.y - 1], ref_board));
                }
                else {
                    placement_board[temp_val.x + 1][temp_val.y - 1] = placement_board[temp_val.x][temp_val.y];
                    queue.add(0, new Element(-1, ref_board[temp_val.x + 1][temp_val.y - 1], ref_board));
                }
            }
        }
        while(!queue.isEmpty());
        //While the queue is not empty or found the dest
        
        
        //Check if found
        if (temp_val.value == dest) {
            return placement_board[temp_val.x][temp_val.y];
        }
        else {
            return -1;
        }
    }
    
    //This gets the total number of elements linked
    //NOT the stretch of a link
    public int longest_link(int[][] ref_board, int[][] placement_board){
        
        //one for the element calling this function
        int result = 1;
        
        int x_min = -1;
        int x_max = 1;
        int y_min = -1;
        int y_max = 1;
        
        Element temp_val;
        Vector< Element > queue = new Vector< Element >();
        
        //add value to the first position in the queue
        queue.add(new Element(-1, value, ref_board));
        
        while(!queue.isEmpty()) {
        
            temp_val = queue.remove(0);
            
            x_min = -1;
            x_max = 1;
            y_min = -1;
            y_max = 1;
            
            //Check bounds
            if (temp_val.x == 0) {
                x_min = 0;
            }
            
            if (temp_val.x == 6) {
                x_max = 0;
            }
            
            if (temp_val.y == 0) {
                y_min = 0;
            }
       
            if (temp_val.y == 6) {
                y_max = 0;
            }
            
            for(int i = x_min; i <= x_max; i++) {
                for( int j = y_min; j <= y_max; j++) {
                    if (i == 0 && j == 0) {
                        //This is value post
                    }
                    else if (placement_board[temp_val.x + i][temp_val.y + j] == 0) {
                        
                        //Mark as empty b/c we counted it
                        placement_board[temp_val.x + i][temp_val.y + j] = -1;
                        result++;
                    }
                }
            }
            
        }
        
        return result;
    }
    
    //Returns true if dest is connected to Element
    public boolean is_connected(int dest, int[][] ref_board) {
    
        int x_min = -1;
        int x_max = 1;
        int y_min = -1;
        int y_max = 1;
        
        //Check bounds
        if (x == 0) {
            x_min = 0;
        }
        
        if (x == 6) {
            x_max = 0;
        }
        
        if (y == 0) {
            y_min = 0;
        }
   
        if (y == 6) {
            y_max = 0;
        }
        
        for(int i = x_min; i <= x_max; i++) {
            for( int j = y_min; j <= y_max; j++) {
                if (i == 0 && j == 0) {
                    //This is value post
                }
                else if (dest == ref_board[x + i][y + j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //REQUIRES: your pieces are 0 on the placement_board
    public int num_connected(int[][] placement_board, int[][] ref_board) {
        
        int result = 0;
        
        int x_min = -1;
        int x_max = 1;
        int y_min = -1;
        int y_max = 1;
        
        //Check bounds
        if (x == 0) {
            x_min = 0;
        }
        
        if (x == 6) {
            x_max = 0;
        }
        
        if (y == 0) {
            y_min = 0;
        }
   
        if (y == 6) {
            y_max = 0;
        }
        
        for(int i = x_min; i <= x_max; i++) {
            for( int j = y_min; j <= y_max; j++) {
                if (i == 0 && j == 0) {
                    //This is value post
                }
                else if (placement_board[x + i][y + j] == 0) {
                    result++;
                }
            }
        }
        return result;
    }
    
    //REQUIRE: dest is in ref_board
    public int offset(int dest, int[][] ref_board, boolean horizontal_offset) {
        
        Element dest_elt = new Element(-1, dest, ref_board);
        
        if (horizontal_offset) {
            return Math.abs(dest_elt.y - y);
        }
        else {
            return Math.abs(dest_elt.x - x);
        }
    }

}
