_All ideas approved by Joe._

# s18-ahitt1-efu2-phahn1--ckim34
Team Name: Dream Team (!)
Group Members:
  - Anina Hitt (ahitt1)
    - Strengths: javascript. jquery. backend logic. html/css. ui/ux.
    - Weaknesses: AI. networking.
  - Claire Kim (ckim34)
    - Strengths: visual design and structure, css/html website design
    - Weaknesses: program logic and modeling, AI
  - Ellen Fu (efu2)
    - Strengths: visual design, full stack development, algorithms
    - Weaknesses: low-level programming, newest web technologies, networking
  - Peter Hahn (phahn1)
    - Strengths: AI and related data structures/techniques, modeling and logic abstraction
    - Weaknesses: super high-level programming, networking


# Project R.A.D.A.R. (Really Awesome Design And Radars)
## Summary
Our team hopes to create a real-time, top-down multiplayer game that tests a player’s ability to strategically gather and consume resources in a king of the hill conflict against other players. As players collect resources, they will be better able to survive the rounds of the game and fight against other players. Although players may choose to remain isolated during the beginning phases of the game, it becomes much more difficult for players to avoid interacting with each other through the middle and late stages of the game.
Although the objective of the game is to be the last one standing in the arena, the secondary objective of the game is to find the resource jackpot by strategically placing Radars around the map. Although the resource jackpot does not guarantee a victory, it makes it significantly easier to do so, and neglecting to search for it will usually end in defeat.
This is our first choice project, and one we have all agreed upon, so if the project is not approved, we would like ideas to make it fit the requirements of the assignment.
Defining the Problem
As with any game, there are two problems that our team needs to address, that are related: Primarily, we want to create a game that is enjoyable to our target audience. To do this, we must balance our design to allow for a healthy learning curve, quick and interesting game mechanics, and friendly user interfaces. From a more technical standpoint, we must smooth the communication between multiple web client to our server, so that actions that a client wishes to perform are communicated quickly and effectively, and converted into game logic. Our Artificial Intelligent Players must be able to make smart decisions, likely using alpha-beta or A* searches, as well as possibly hidden markov models. The AI must be smart, without making it so smart that it detracts from the game.
The second concern is constructing a system that is free from exploitation from players. We must design the game in such a way that players cannot “gamify” the game to the point where they are avoiding the goals that we hope for them, including interacting with other players, making strategic decisions, and looking for goal states with their Radars.

## Game rules specifications
  - Each game is capped at 4 players.
  - Game ends when a radar reveals the goal and a player reaches the goal.
  - Players have a minimap on their screen showing a non-detailed map of the entire map.
  - Arena will shrink gradually (not necessarily with the goal as the center) so that the goal is still on screen and reach its minimum size at 5 minutes into the game.
  - Players cannot join once a game has started.
  - Game ends with a display message of the winner's username and has a button to return to the home page.

### Players
  - Move using the WASD keys OR the arrow keys and change the forward direction with their mouse.
  - Hold down space bar to attack.
  - F key to pick up items - items of the same type will be automatically swapped
  - R key to put down radar (if sufficient materials)
  - If killed, respawned at random location after 10 seconds with no items

### Radars
  - Can be put down at exactly player's position with the R key once sufficient materials are collected.
  - Show all items and players in the radar's radius as the food material dictates (either simple points or detailed item types).
  - Show the goal if the goal is within the radar's detection radius.
  - Stay there the entire game and updates everyone's minimaps throughout the game.

### Items
Mostly appear at the beginning of the game and are distributed unevenly as in different types of items appear in clusters in different locations. Players pick up items by pressing the F key.

#### Materials
For creating radars to search for the goal. Players need 3 different materials to craft one radar: One stick item, one metal, and one food. Players can only hold ONE of each type of material at once.

Food has 3 types, which can each do one of the following:
  - Carrot: Reveal what types of things are in the radius at the time of creation. I.e. shows position of players, items in the radar radius at time of reveal but doesn't update.
  - Lollipop: Make a radar with a larger radius.
  - Starbucks Frap: Basic (smallest radius)

#### Weapons
For combating other players to take each other's materials. Players press the space key to attack in the direction their icon is facing using their current weapon. Players can hold ONE weapon at once and can drop/pick up others. Players start off with just fists which deal low damage.

Ranged weapons: bow/arrow, boomerang.
Close-combat weapons: fists (default), sword.

## Frontend specifications
The front end needs to render the game while also communicating with the backend to see how the state of the world has changed. However in general, we want to make sure that the frontend runs smoother than the bottleneck of networking. As a result, it will have to predict where it should render objects by having a lighter representation of the game engine stored internally in each client. 
It can do this by getting game constants from the server at the beginning of each connection. These constants will be passed as a JSON format, containing any information about game data, such as weapon ammo capacity, player speeds, player maximum health, etc. The frontend can then render the game live, and update everything necessary once it gets the results from the backend, approximately 30 times per second. 

### Start screen
Users start at the start screen at our home page URL. Users will have the option to name themselves (they are assigned a random user ID by default). Users have two choices: create a game or join a game. There is an option to display game rules.

### "Create a game" page
  - Provide a URL for others to join this game.
  - Name the game room.
  - Select whether room is private (needs URL) or public (seen in "join a game screen")
  - A start button to start the game.
  - Players are filled with AI players until human players join.

### "Join a game" page
Players see a list of currently open games. The list's functionalities are to display each game room's:
  - Current number of players/max number of players
  - Room name
  - Join button
  - A "join random room" button at the top.

### A game room's page
Looks like "create a game" page except players cannot change game name and don't have a "begin game" button.

### Game screen:
  - Option to display game rules.
  - Overlaid elements
    - A health meter
    - An inventory bar
    - Weapon icon
    - Minimap
    - Update messages
    - Timer (show how much time is left in the game)
  - Player icon: kept at center of the screen.
  - Item (material/weapon/obstacles) icons (interactable)
    - Able to tell because of some moving element (blinking border, glow in background, etc.)
  - Background (cannot interact)

### Extra features (if we have time)
  - Choose a theme of game (type of special material, default is food)
  - Change number of players in the game
  - Choose player icon
  - Players can choose the keyboard configuration (WASD or arrow keys for movement)

## Artificial Intelligence specifications
The AI player will simulate a human player in that it only has knowledge of items and players within its viewable region (if it were a human player. This will be called the immediate radius from here on) and any radar's revealed elements. The AI goes into defensive (prioritize health), offensive (prioritize dealing damage), and gathering (prioritize getting items) mode depending on AI's current state (health, materials, weapon) and knowledge of surroundings.

### Data provided by backend
Uses backend's representations of item and player locations within the AI player's visible range. If a player is in the AI's visible range, the player's hit box (attack range) and hurt box (the range at which the player can be dealt damage to) are queried. The minimap updates are updated at every tick (whether goal position is revealed, items and players within the radar range).

### Main algorithms
  - Predict encountered player's movements
  - Avoid obstacles and calculate shortest distance route (either to items, or to/away from other players)

### Behavior modes
#### Defensive mode
If AI's health is below 2 times any encountered player's attack damage, it will prioritize fleeing from other players.

Cases:
  - One encountered player: predict the player's next position and go in the same direction as that to run away.
  - Several encountered players: choose a direction by:
    1. Calculating the 180 degree range of directions (x and y range) for each player, with the player's predicted direction at the right angle position (so it's a 180 degree fan with the predicted direction as the angle bisector)
    2. Choose a random direction in the overlaps of all other players' 180 degree fans. If there are no overlaps (e.g. a perfect surrounding attack by other players), randomly choose angle not directly opposite to any of the player's angles and reevaluate at the next update.

#### Offensive mode
When AI encounters another player and health is > 2 times any encountered players' attack damage, and AI's hit range is larger than target player's hit range

Steps until the AI kills the target or switches to a different mode:
  1. Choose the closest encountered player whose hit range is smaller than AI's hit range as the target.
  2. Move towards the target's current position to get within hit range
  3. Predict the other player's movements to aim and attack at the optimal direction to hit the player
  4. If successfully killed the target, collect all target's better-quality items
  5. Choose another target if the mode hasn't changed yet

#### Gathering mode
No other players are in the visible range.

Cases:
  - AI currently has insufficient materials to make a radar
    - Get all the best-quality items in the visible range
    - If no items are in the visible range, choose a random direction and continue in that direction until encountering items or other players (and change modes accordingly)
  - AI has sufficient materials to make a radar
    - If any better-quality items are in the visible range, pick them up
    - Otherwise, make a radar and decide its placement
      - If no radars have been placed yet, place it right where the AI is
      - If one radar has been placed, place it somewhere such that the radar radii don't overlap
      - If multiple radars have been placed, go towards the midpoint of existing radars' centers and place the radar there

#### Reach goal mode
Goal has been revealed! Go straight towards the goal in modified defensive mode: Instead of fleeing the opposite direction the encountered player is coming from, flee in the direction closest to the goal but still avoids the encountered players' hit box.

## Game Logic
tl;dr The game engine does three things. Requests events from the central architecture since the last time it did, process the events, and moves the game ahead a state given those events occurred, and post to the central architecture the new state of the game. In addition, the game must export its internal representation of statistics in the game to pass along to the clients (once each) so that the front-end can predictably render. As long as this input output specification is met, then the rest of the program will run without fail.
The rest of this document is written to demonstrate the underlying workings of the program. Although these specs will be the ones used for the final project, changing these specifications is alright because everything in this class will be handled internally.

### Classes
  - Game Logic
    - Interface `Tickable`
    - Interface `Game`
    - Class `RadarGame implements Game, Tickable`
    - Class `Chunk`
  - Boxing Information
    - Interface `Hitted`
    - Interface `Hurted`
    - Interface` Collisioned`
    - Interface `Box`
    - Class `Rectangle implements Box`
    - Class `Circle implements Box`
  - Entity Information
    - Abstract Class `Entity`
    - Class `Player extends Entity implements Hitted, Hurted, Collisioned`
    - Abstract class `Item extends Entity`
    - Class `Radar extends Item`

### Game Loop
Game runs at 30 ticks per second and 1 frame per tick. This means for every game event, there will be an associated set of items to be rendered. Each tick will involve all tickable entities to call their void tick() method. This updates the state of the entity, by taking to account its position, its velocity, and what each player inputted. More information on this in the next section.

#### Ticks
On every tick, there are a few things that need to be done. First, all character positions must be updated, based on the inputs from the clients. Inputs will be given asynchronously, but everything is done synchronously internally on ticks. Second, all game updates will happen. This is a much easier task, as all game representations are internal. Primarily, the game will request from the architecture, the set of events that occurred since the last tick. After this, the game processes the next step of the game, taking into account the events. After everything is done here, the game will post the entities to the architect, completing the tick method. During this process, the front end is responsible for maintaining the state of the game, using prediction methods. 
This process will likely happen on 2 or more separate threads to speed things up. The exact specification for this is currently unknown.

#### Events
There are limited inputs a player can give that result in a game change. We must account for this for all players, and pass along the meaningful ones to the game. Here, the game will take the id of the player, and the associated event and perform the necessary action. For example, player 5 (id:555) presses 'w' on the keyboard and left mouse clicks while facing 44 degrees from east. There are three inputs here for a single player, and the game engine (name the tick method) must be in charge of handling this.
 The Event framework should be as follows: There exists a SetMultiMap from PlayerIds to Events that the central framework contains. It is requested by the game at every tick, and then processed.

#### Information 
The game must pass on information of all entities to each client to render on their page. However, there will be notation of what each player "knows". All information outside of a certain range will not be processed. The information present will be slightly larger than the player's field of view, perhaps 20% larger, to allow items to be rendered without fear of losing them. To solve this problem, we use a concept called chunks. Chunks divide the map into smaller components, and so when passing along information, only chunks near the item really need to be affected, as long as we maintain the invariant that a change in item state in one corner of the map cannot affect the state of another corner of the map. 

### Hitbox Detection
The problem here is that there are many components on the screen, many of which are dynamic. Storing these items in a KDTree for hit detection may not be the best idea, but linear passes also probably will not be a good plan. The solution to this plan is chunks. The world can be divided into chunks, where each chunk is a little over the maximum range any individual can have (in terms of sight and weaponry). This is much more space efficient than having an array representing each pixel, while still dividing up the labor of range of checking for hit detection. The easiest way to act is to render in a 3 by 3 chunk area around the player. Given that the chunks are of appropriate size, this guarantees that you will not need more space.
 There are three types of boxes that need to be considered: Hitboxes, hurtboxes, and collision boxes. Some items can be interacted with, but fall into none of these categories, and simply must be positioned. Hitboxes: Entities that can hit things have hitboxes. Consider projectiles or danger zones. Hurtboxes: Entities that are affected by hitboxes have hurtboxes. Consider players or breakable items. When a hurtbox interacts with a hitbox, some event occurs, usually the entity taking damage. Collision Boxes: Entities have collision boxes. Most of the time, these do not affect the entity, until it interacts with another entity that is marked solid.
 There will be 2 classes of boxes, Circles and Rectangles. This is to simplify things for all of us. Rectangles will only be used for boxes that are non rotatable, also to make things easier for us. These two shapes were chosen because of the ease of determining whether a point is within bounds of them, which is really nice. Our design will prefer circles over rectangles, but will have support for rectangles to make certain things more appropriate
Here is the diagram for class hierarchy for the boxes:
  - Box
    - Rectangle
    - CircleBox Collisioned // interface Hitboxed  // interface Hurtboxed // interface
 For example, a Player implements Collisioned and Hurtboxed, while having a box that represents its hurtbox and a box that represents its collision box

### Game Design and Details
To allow ourselves creativity throughout the process of the game design, our framework will be created to be extensible. Although the inner specifics of of our game may be subject to change, we have outlined the components we would like to implement. Changing these will be simple because of our design.
  - Items:
    Items extend Entity, so that they can be found in constant time. Items
    will have their entity form, until a player picks it up, in which case
    it will be converted to a internal class representation.
  - Radars:
    Radars are a type of item that can be placed in a given location, that
    has different functionality depending on the type of radar. Each radar
    can be upgraded to have different functionality, - radius (increased radius) - identification (type of object) - regular

### Game Class extends Component implements Tickable
#### Game(Architect architect)
The Game constructor takes in an Architect, that connects it to the central framework. The Architect has access to all the Components, and the game in turn has access to the Architect which allows them to communicate, mainly through events.

  - `void run()`: The main while loop of the game, which will call the tick method 60 times
    per second. During the tick method, all game entities are updated in the
    most appropriate manner
  - `SetMultiMap<Integer, Event> requestEvents()`: Returns a Map of ids to a set of events from the central architecture, which
    represent the set of events that occurred for each player within the last
    tick.
  - `void handleEvents(SetMultiMap<Integer, Event>)`: Given the output of requestEvents(), this method updates the state of all
    of the entities that have the integer id.
  - `void postChangeState()`: This method will be used to call the architecture's post method that allows the game engine to post the changes made since the last time this method was called.
  - `JSONObject statistics()`: This method is needed once per client, so that the front end will have a representation of the different statistics of the game so that it can render predictably without necessarily needing to be connected to the backend game logic

#### Entity abstract class Entity(int id)
The only attribute of an entity is that it has a unique id. The id is stored in the abstract class. Upon comparing equality, only the id is used

```
// Sort of self explanatory, but the player class is a representation of a
// player in the game.
Player class extends Entity implements Hitted, Hurted// abstract class for player 
double direction, x, y; List<Item> inventory; 

void changeDirection(double direction) // These 5 commands deal with the changes 
void moveForward() // In the player's location state, which will be most relevant 
void moveBackward() // To the front end for rendering. void strafeLeft() void strafeRight()
void pickUp() // queries for the nearest item to this player, and adds it to the inventory if possible 
void dropItem(String id) // Drops the item of the given id
void mainAction() // performs the main, generally considered left-click action void secondaryAction() // probably will be the secondary action, if relevant void hand() // tempory name, but changes the mainAction item. Something something switching positions in the array
```

## Networking
The Networking portion of the project deals with connecting the many moving pieces of the project, allowing for extensible and abstracted design. Each component in the backend that is connected will extend the Component class. 
The two challenges the Networking aspect of this project needs to address is keeping in sync different components, some of which are synchronous and some that are not, as well as keeping track of grouped data. For example, if we were to have a chat room, we weed need to associate with a specific game with a specific set of players. 

### Classes
  - GameSession extends Component
    - establishes a new socket/thread for each new game session, and associates it with an id.
  - Player
    - establishes a new socket/thread for each new client, and associates it with an id.
    - We need to distinguish between GamePlayer and Player. A GamePlayer is the internal game representation of a player, while a Player is the abstraction of player in regards to networking. Naming is still up in the air, but essentially, a Player persists beyond the game, and is associated with an AI or a Client
    - [AI will be treated as a player]
  - SparkHandlers
    - `runSparkServer()`: all the routes/templateviewroutes/posts/gets for the home page and game pages
    - A post request from an active game should pass in the game session’s id, as well as the ids of all the entities associated with events (for example, Player 1: “move left”). The information will be parsed into a format readable by the backend, and get sent as a setMultiMap from Players to Events, in order for the backend model of the game to be updated properly. 
    - should also use a method to send information packets down to the frontend through all the client’s sockets. information packets will probably consist of a JSON object mapping playerIDs to a list of events. 

### Components
Many components are currently not in our spec, but the main component we will be implementing is the Game Component. The Networking portion of this project will deal with taking events from the front-end and converting it to events that can be passed onto each component (in our case, the Game) in our backend. See the Game section for specifics on the inputs and outputs, at a high level, we send in events associated with ids, and receives a representation of the game in its postState(SetMultiMap<Chunk, Entity>) method. 

### Handlers
To easy abstraction, we will have handlers that correspond to each relevant component. If we have multiple games running at the same time, it may get messy to handle everything in a single class. As a result, we can pass in these arguments into a handler that will take the events from the network, and communicate it to the correct instance of the component. 
