package com.t3;

import java.util.ArrayList;
import java.util.Collections;

public class Player implements Comparable<Player> {
    int fitnessScore=0;
    ArrayList<Move> chromosone = new ArrayList<>(9);
    //Move[] chromosone = new Move[9];//There are only 9 possible moves.
    //Chromosone design is a little weird, because its actually a sequential list of moves.
    //Player takes the next available move out of his chromosone.

    //No-args creates a CHAOS player, with random move set.
    Player(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                chromosone.add(new Move(i,j));
            }
        }
        //Randomize the moveset!
        Collections.shuffle(chromosone);
    }

    Player(Player father, Player mother, int crossover){
        chromosone = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            if (i<crossover){
                chromosone.add(i,father.chromosone.get(i));
                //chromosone[i] = father.chromosone[i];
            }
            else {
                chromosone.add(i,mother.chromosone.get(i));
                //chromosone[i] = mother.chromosone[i];
            }
        }
    }

    public void printChromosones(){
        Board example = new Board();
        int i = 1;
        for (Move m:chromosone) {
            example.spaces[m.x][m.y]=i;
            i++;
        }
        example.printBoard();
    }


    @Override
    /**
     * Returns how much better or worse I am than the compared Player.
     */
    public int compareTo(Player o) {
        return fitnessScore-o.fitnessScore;
    }

}
