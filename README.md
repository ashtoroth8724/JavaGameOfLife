
# Game Of Life
The Game of Life (an example of a cellular automaton) is played on an infinite two-dimensional rectangular grid of cells. Each cell can be either alive or dead. The status of each cell changes each turn of the game (also called a generation) depending on the statuses of that cell's 8 neighbors. Neighbors of a cell are cells that touch that cell, either horizontal, vertical, or diagonal from that cell.

### Reminder of basic game of life rules
    1. Any living cell with strictly fewer than two living neighbors dies (referred to
    as underpopulation or exposure).

    2. Any living cell with strictly more than three living neighbors dies (referred to
    as overpopulation or overcrowding).

    3. Any dead cell with exactly three living neighbors  will come to life. (Referred to as
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

Start the program

a. Compile the Java source files :

```bash
javac -cp lib/json-simple-1.1.1.jar src/Main.java
```

b. Run the compiled Java program, including the jar file in the classpath:

```bash
java -cp .:lib/json-simple-1.1.1.jar src/Main
```
## Basic How to

- Load Rules
By default, Coneways rules are loaded. You can load other set of rules by clicking the "Load Rule" button.

- Create Field
Create the fild either by generating a random field, or by clicking yourself on cells.

- Toggle Border
There is 2 different iterations methodes.

    1. Closed
        The sides of the gride will count as dead.

    2. Loop
        The gride technicaly has no sides. The left handside is connected to the right one, the top to the bottom.
        All 4 corners cells are nearby cells of each others.

- Add agents
By clicking on toggle click, or by importing an agent file, you are able to place on the grid specific agents like a sheep or a wolf.

## Color Reference

### Coneway Rule

| Color             | HEX                                                                |   RGB |
| ----------------- | ------------------------------------------------------------------ |------|
| Dead | ![#25341F](https://via.placeholder.com/10/25341F?text=+) #25341F | [37,52,31] |
| Alived | ![#a7ed8b](https://via.placeholder.com/10/A7ED8B?text=+) #f8f8f8 | [167,237,139] |

### Blob Rule

| Color             | HEX                                                                |   RGB |
| ----------------- | ------------------------------------------------------------------ |------|
| Dead | ![#615e3a](https://via.placeholder.com/10/615e3a?text=+) #615e3a | [97,94,58] |
| Alived | ![#dcff42](https://via.placeholder.com/10/dcff42?text=+) #dcff42 | [255,255,66] |

### Gaz Rule

| Color             | HEX                                                                |   RGB |
| ----------------- | ------------------------------------------------------------------ |------|
| Dead | ![#000000](https://via.placeholder.com/10/000000?text=+) #000000 | [0,0,0] |
| Excited state 1 | ![#333333](https://via.placeholder.com/10/333333?text=+) #333333 | [51,51,51] |
| Excited state 2 | ![#666666](https://via.placeholder.com/10/666666?text=+) #666666 | [102,102,102] |
| Excited state 3 | ![#999999](https://via.placeholder.com/10/999999?text=+) #999999 | [153,153,153] |
| Excited state 4 | ![#cccccc](https://via.placeholder.com/10/cccccc?text=+) #cccccc | [204,204,204] |
| Excited state 5 | ![#ffffff](https://via.placeholder.com/10/ffffff?text=+) #ffffff | [255,255,255] |


## FAQ

#### How to make my own rules ?

To create a rule, you have to know how many cell states you will have. For each cell state, you have to add a cell onject in the json file, like the following one :

```json
{"cell": {
    "value" :   0, 
    "color" : [97, 94, 58],
    "conditionCountNear" : [1,3,5,8],
    "conditionHighestNear" : [],
    "ifValue" : 1,
    "elseValue" : 0
}}
```
- value: The state value of the cell.
- color: An array representing the RGB color of the cell.
- conditionCountNear: An array specifying the counts of neighboring cells that satisfy the condition for this state to change.
- conditionHighestNear: An array specifying conditions based on the highest value of neighboring cells.
- ifValue: The state the cell changes to if conditions are met.
- elseValue: The state the cell changes to if conditions are not met.

#### What are agents ?

Agents are specific objects on top of the grid with a specific behaviour. In this project, 2 agents are implemented :
- Sheep : Sheeps have a hunger, and have to eat grass (Living cells) for them to survive. They can reproduce and act dummy, by moving  in a random way.
- Wolf : Wolfs also have a hunger, but they have to eat a ship to survive. They can reproduce, and act as dummy if no sheeps are in their radar. If a ship is in its detection radius, the wolf focus the ship and walk towards him.

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
