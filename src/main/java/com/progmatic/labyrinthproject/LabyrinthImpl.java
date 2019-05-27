package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private CellType[][] labyrinth;
    private int width = -1;
    private int height = -1;
    private Coordinate playerPosition;

    public LabyrinthImpl() {
        playerPosition = new Coordinate(0, 0);
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine()); // sor
            int height = Integer.parseInt(sc.nextLine()); // oszlop
            setSize(width, height);
            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            labyrinth[ww][hh] = CellType.WALL;
                            break;
                        case 'E':
                            labyrinth[ww][hh] = CellType.END;
                            break;
                        case 'S':
                            labyrinth[ww][hh] = CellType.START;
                            playerPosition = new Coordinate(ww, hh);
                            break;
                        case ' ':
                            labyrinth[ww][hh] = CellType.EMPTY;
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if (c.getCol() < 0 || c.getRow() < 0 || c.getCol() >= width || c.getRow() >= height) {
            throw new CellException(c, "The given coordinate does not exist");
        }
        return labyrinth[c.getCol()][c.getRow()];
    }

    @Override
    public void setSize(int width, int height) {
        if (width < 0 || height < 0) {
            throw new RuntimeException("WTF?");
        }
        this.width = width;
        this.height = height;
        labyrinth = new CellType[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                labyrinth[i][j] = CellType.EMPTY;
            }

        }
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (c.getCol() < 0 || c.getRow() < 0 || c.getCol() >= width || c.getRow() >= height) {
            throw new CellException(c, "The given coordinate does not exist");
        } else {
            labyrinth[c.getCol()][c.getRow()] = type;
            if (type == CellType.START) {
                playerPosition = c;
            }
        }
    }

    @Override
    public Coordinate getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public boolean hasPlayerFinished() {
        return labyrinth[playerPosition.getCol()][playerPosition.getRow()] == CellType.END;
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> result = new ArrayList<>();
        if (playerPosition.getCol() < width && labyrinth[playerPosition.getCol() + 1][playerPosition.getRow()] != CellType.WALL) {
            result.add(Direction.EAST);
        }
        if (playerPosition.getRow() < height && labyrinth[playerPosition.getCol()][playerPosition.getRow() + 1] != CellType.WALL) {
            result.add(Direction.SOUTH);
        }
        if (playerPosition.getCol() > 0 && labyrinth[playerPosition.getCol() - 1][playerPosition.getRow()] != CellType.WALL) {
            result.add(Direction.WEST);
        }
        if (playerPosition.getRow() > 0 && labyrinth[playerPosition.getCol()][playerPosition.getRow() - 1] != CellType.WALL) {
            result.add(Direction.NORTH);

        }
        return result;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        int row = 0;
        int col = 0;

        switch (direction) {            
            case EAST:
                col = +1;
                break;
            case SOUTH:
                row = +1;
                break;                
            case WEST:
                col = -1;
                break;
            case NORTH:
                row = -1;
                break;
        }
        
        int newCol = playerPosition.getCol() + col;
        int newRow = playerPosition.getRow() + row;
        if (newCol >= width || newCol < 0 || newRow >= height || newRow < 0) {
            throw new InvalidMoveException();
        } else if (labyrinth[newCol][newRow]==CellType.WALL){
            throw new InvalidMoveException();
        } else {
            playerPosition = new Coordinate(newCol, newRow);
        }
    }

}
