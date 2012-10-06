package com.barracuda;

public class Target extends Element {

    public int offset = 0;

    public int s_path_to_goal1 = 0;
    public int s_path_to_goal2 = 0;
    
    public int s_path_to_root = 0;
    
    public int min()
    {
        if(s_path_to_goal1 < s_path_to_goal2)
        	return s_path_to_goal1 + s_path_to_root;
        return s_path_to_goal2 + s_path_to_root;
    }
    
    public Target(int in_owner, int in_value, int[][] board) {
        super(in_owner, in_value, board);
        // TODO Auto-generated constructor stub
    }
    
}
