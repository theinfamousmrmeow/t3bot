package com.t3;

import java.util.ArrayList;
import java.util.Collections;

public class game {

    static int generation = 0;
    static int maxGenerations = 1000;
    static int breedingPairs = 2;
    static int populationCapacity = 10;
    static ArrayList<Player> population = new ArrayList<>(populationCapacity);//Our current population of players.  We maintain 10 at a time.
    static ArrayList<Player> lastPopulation = new ArrayList<>(populationCapacity);

    /*
    DESIGN -
    Take a hard-coded robot Tic Tac Toe player, and put it up against a Genetic Algorithm equipped player.
    Have them player 100 games.
    ROBO-Player:  Can be toggled to go first or second.  Always plays in a certain sequence of moves, skipping a move and going to the next one if the current move is not valid.
    ANIMAL-Player:  Uses its current moveset to make a move.  If the move is not valid, skips to the next move.  If none of its moves are valid, makes a random move.

    BREEDING:  After each game, breed a certain number of players to form the next generation.

    EXPECTED RESULT:
    After 100 generations, the Best Animal Player should be able to destroy the robo-player.

     */
    public static void start(int geneticPlayerNumber, Player opponentPlayer){

        int chaosWins = 0;
        int geneticWins =0;

        for (int i = 0; i < populationCapacity; i++) {
            population.add(new Player());
        }
        GENERATIONS: while(generation<maxGenerations){
            nextGeneration(false);
            int generationWins = 0;
            int generationLosses = 0;
            int opponentTurn = (geneticPlayerNumber+1)%2;
            System.out.println("Generation: "+generation);
            //Play a game for each player in the population.
            POPULATION: for (int i = 0; i < population.size(); i++) {
                Board currentBoard = new Board();
                Player[] players = new Player[2];

                players[opponentTurn] = new Player();
                players[geneticPlayerNumber] = population.get(i);

                if (players[0].chromosone.size()<9){
                    System.out.println("DEVIANT DETECTED");
                    break GENERATIONS;
                }

                while (currentBoard.isWon()<0 && currentBoard.moves<9){
                    for (int j = 0; j < players.length; j++) {
                        for (int k = 0; k < players[j].chromosone.size(); k++) {
                            if (currentBoard.tryMove(players[j].chromosone.get(k),j)){
                                k=1000;
                            }
                        }
                    }
                }
                System.out.println("Moves total: "+currentBoard.moves);
                System.out.println("Winner: "+currentBoard.isWon());
                //We lost
                if (currentBoard.isWon()==1){
                    chaosWins++;
                    //High scores are bad.
                    players[geneticPlayerNumber].fitnessScore=100;
                    generationLosses++;
                }
                //We won!
                else if (currentBoard.isWon()==0) {
                    geneticWins++;
                    //A score of 5 means you won the game in 3 moves LIKE A FREAKING G
                    players[geneticPlayerNumber].fitnessScore=currentBoard.moves;
                    generationWins++;
                }
                //If it ended in a tie.
                else {
                    players[geneticPlayerNumber].fitnessScore=10;
                }
                //End of the game.  Evaluate Fitness of current player.
                currentBoard.printBoard();
            }
            System.out.println("Generation Win Rate: "+generationWins+"/"+populationCapacity);
            System.out.println("Generation Loss Rate: "+generationLosses+"/"+populationCapacity);

        }

        System.out.println("Chaotic Player Wins: "+chaosWins);
        System.out.println("Genetic Player Wins: "+geneticWins);
        int totalMatches = (maxGenerations*populationCapacity);
        float nonLossPercent = (float)(totalMatches-chaosWins)/(float)totalMatches;
        System.out.println("Percent not lost: "+nonLossPercent+"%");//Completely random player can "not lose" 71% of the time if it goes first.
        System.out.println("Optimal Strategy: ");
        population.get(0).printChromosones();
    }

    public static void nextGeneration(boolean addMutants){
        //lastPopulation = (ArrayList<Player>)java.util.List.copyOf(population);
        Collections.sort(population);
        //We are assuming that the Current population is ranked in order of fitness, so we just take the top Players and breed them
        for(int i=0;i<breedingPairs;i++){
            population.remove(population.size()-1);
            //population.add(0,new Player(population.get(1),population.get(2),1));
            //TODO:  This doesn't exactly work, because moves can get lost using this method, meaning I will have less than 9 chromosones.
            population.add(0,new Player(population.get(0),population.get(1),(int)(population.get(i).chromosone.size()*(Math.random()))));
        }
        //If we have a loser, kill him and replace with a Mutant.
        if (addMutants && population.get(population.size()-1).fitnessScore>=100 && generation < maxGenerations/2) {
            population.remove(population.size() - 1);
            population.add(new Player());
        }
        generation++;
    }
}
