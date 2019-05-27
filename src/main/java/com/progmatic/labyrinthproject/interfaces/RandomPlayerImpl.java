package com.progmatic.labyrinthproject.interfaces;


import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.util.List;
import java.util.Random;


public class RandomPlayerImpl implements Player {

    public RandomPlayerImpl() {
    }

    @Override
    public Direction nextMove(Labyrinth l) {
        Random r = new Random();
        List<Direction> possibleMoves = l.possibleMoves();
        return possibleMoves.get(r.nextInt(possibleMoves.size()));
    }
    
}
