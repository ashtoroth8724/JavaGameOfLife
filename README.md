
# Game Of Life
The Game of Life (an example of a cellular automaton) is played on an infinite two-dimensional rectangular grid of cells. Each cell can be either alive or dead. The status of each cell changes each turn of the game (also called a generation) depending on the statuses of that cell's 8 neighbors. Neighbors of a cell are cells that touch that cell, either horizontal, vertical, or diagonal from that cell.

### Reminder of basic game of life rules by 
1. Any living cell with strictly fewer than two living neighbors dies (referred to
as underpopulation or exposure).

2. Any living cell with strictly more than three living neighbors dies (referred to
as overpopulation or overcrowding).

3. Any dead cell with exactly three living neighbors will come to life. (Referred to as
spreading or growth)
With the implied additional rules:

4. Any living cell with two or three living neighbors continues to live, unchanged.

5. Any dead cell who doesn’t have exactly 3 living neighbors stays dead, unchanged.



## Features

- Classic Game of Life Rules: Implements the traditional rules of Conway's Game of Life.
- Customizable Rulesets: Load and apply custom rulesets from JSON files.
- Agent Behavior: Simulate agents with specific behaviors based on their surroundings.
- Interactive Grid: Click on cells to change their states with two modes: cell state change and agent placement.
- Looping Borders: Option to enable or disable looping borders for the grid. (Opposit sides are connected)
- Adjustable Simulation Speed: Change the speed of the simulation using a slider.
- Random Cell Generation: Generate a random initial state for the grid, the density slider allows to adjust the probability for a cell to become alive.
- Save and Load States: Save/Load current state of the simulation.
- Pause and Resume.
- Visual Representation: Display the grid with different colors representing different cell states.



## Run Locally

Clone the project

```bash
  git clone https://gitarero.ecam.fr/guillaume.bonabau/OOP_F1_Project.git
```

Go to the project directory

```bash
  cd OOP_F1_Project
```

Install dependencies

```bash
  TODO How to install the json-simple-1.1.1.jar 
```

Start the program

```bash
  TODO ???? => javac .\src\Main.java
```


## Basic How to

- Load Rules
By default, Coneways rules are loaded. You can load other set of rules by clicking the "Load Rule" button.

- Create Field
Create the fild either by generating a random field, or by clicking yourself on cells (Don't forget to Toggle click).

- Toggle Border
There is 2 different iterations methodes.

1. Closed
The sides of the gride will count as dead.

2. Loop
The gride technicaly has no sides. The left handside is connected to the right one, the top to the bottom.
All 4 corners cells are nearby cells of each others.

- 

### Rule Sets

1. Basic John Conway's Rule
The basic rule set described above
world advised: conwaySingleShotCannon.csv backRake.csv glider.csv gliderTrain.csv gosperGlidergun.csv r_pento.csv

2. Blob Rule
A modification of john conway's game of life with much more birth and survive value, very good to play around with sheeps if you want them to starve a bit but not too much

3. Grass for sheeps Rule
give many grass for sheeps to eat from, very good to play with hunting wolves

4. Hash Life Rule
game of life with more birthing values

5. Gas Rule
Uses a Highest neighbour instead of a count near. gas excite (explode) when one of his neighbour is excited, and takes 5 turns to return to an excitable state.
Inspired by this video from Steve Mould [Bizarre travelling flame discovery](https://youtu.be/SqhXQUzVMlQ?t=418)
world advised: randomFive.csv interestingGas.csv densityStyle.csv


## Color Reference

| Color             | HEX                                                                |   RGB |
| ----------------- | ------------------------------------------------------------------ |------|
| Dead | ![#25341F](https://via.placeholder.com/10/25341F?text=+) #25341F | [37,52,31] |
| Alived | ![#a7ed8b](https://via.placeholder.com/10/A7ED8B?text=+) #f8f8f8 | [167,237,139] |


## FAQ

#### Question 1

Answer 1

#### Question 2

Answer 2


## Acknowledgements

 - [OOP Project](https://gitarero.ecam.fr/francois.neron/OOP_H03_test_Project)
 - [Francois NERON](https://gitarero.ecam.fr/francois.neron)

## Appendix

To download the json library:
https://code.google.com/archive/p/json-simple/downloads

Workflow :
When starting a task "TODO" edit it to "TODO-INPROGRESS"

When task is done edit it to "TODO-COMPLETE"

If there is an error in the code edit it to "TODO-ERROR"


Link Canva Whiteboard: 
https://www.canva.com/design/DAGCBGF5b4c/4cNmhoS6lSC8Once9r_Tlg/edit?utm_content=DAGCBGF5b4c&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton
## Authors

- [@guillaume.bonabau](https://gitarero.ecam.fr/guillaume.bonabau)
- [@g.le-chartier](https://gitarero.ecam.fr/g.le-chartier)
- [@balthazar.squinabol](https://gitarero.ecam.fr/balthazar.squinabol)
