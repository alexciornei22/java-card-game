  First, 2 objects of type Player are instantiated (one for each of the players) with their corresponding sets of decks.
  
  For each of the game inputs a new Game instance is created and the hero is set for each of the players, then 
for every action a Command object is retrieved and is executed (each action object implements the Command interface).
  
  Each command receives an output object, on which it inserts its errors/outputs to be printed on the JSON file.
  
  Each card type corresponds to a class which inherits from the base Card class.
  
  The cards that have special abilities have a specialability field which contains an ability object that inherits from
an ability interface, which could be a normal special ability or a row special ability, depending on each case.
