package com.t3;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is our Environment.
 */
public class Board {
    int[][] spaces = new int[3][3];
    int winner;
    int moves;

    Board(){
        cleanBoard();
    }

    public void cleanBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                spaces[i][j]=-1;
            }
        }
    }

    public boolean playerHas(int x, int y, int player){
        return (player == spaces[x][y]);
    }

    public boolean spacesFree(){
        return (spaces.toString().contains("-1"));
    }

    public boolean isFull(){
        return (!spacesFree());
    }

    public boolean tryMove(Move move, int player){
        return tryMove(move.x,move.y,player);
    }

    public boolean tryMove(int x, int y, int player){
        if (spaces[x][y]==-1){
            spaces[x][y]=player;
            moves++;
            return true;
        }
        return false;
    }

    public int isWon(){
        //Checks to see if this board has been won by one player or another, and returns which player, 0 if a draw, -1 if not over yet.
        //Check the 8 possible win conditions.
        for (int player = 0; player < 2; player++) {
            //Columns;
            for (int x = 0; x < 3; x++) {
                if (playerHas(x,0, player) && playerHas(x,1,player) && playerHas(x,2,player)){return player;}
            }
            //Rows;
            for (int y = 0; y < 3; y++) {
                if (playerHas(0,y, player) && playerHas(1,y,player) && playerHas(2,y,player)){return player;}
            }
            //Diags
            if (playerHas(0,0, player) && playerHas(1,1,player) && playerHas(2,2,player)){
                return player;
            }
            if (playerHas(2,0, player) && playerHas(1,1,player) && playerHas(0,2,player)){
                return player;
            }
        }
        if (isFull()) return -1;//Stalemate
        return 0;//Not over!
    }

    public void printBoard(){
        System.out.println(Arrays.deepToString(spaces).replace("]","\n").replace("[["," ").replace("[","").replace(",","").replace("-1","."));
    }

}
