# Guess-the-Animal

### Learning Outcomes

- tree data structure: how to search it, and traverse all its nodes. 
- how to use serialization to store knowledge from a tree data structure.
- how to use resource bundles for program internalization and adding support for different languages

### About

In this project, a simple interactive game was created where the computer will try to guess the animal that the user 
has in mind with the help of yes or no questions. During the game, the computer will extend its knowledge base by 
learning new facts about animals and using this information in the next game.

The program starts (if it was being launched for the first time) by asking the user what their favorite animal is. This animal name will be the root node in our knowledge tree for now.  
After that, the user will be greeted with a menu that has these 6 items:
- Play the guessing game
- List of all animals: lists all the animals that the user has provided to the program so far
- Search for an animal: The user provides an animal name (with no article) and, if it exists in the knowledge tree, prints facts about it
- Calculate statistics: gives information about what the current root node is, the total number of nodes, animals, and statements, the height of the tree, and the minimum and average animal's depth
- Print the knowledge tree: prints the knowledge tree where the right node represents answering yes to the question and the left node represents answering no.
- Exit: exits the program and saves the information from the knowledge tree through serialization in a file "animals.db"

This program also includes support for both the English and Esperanto languages through the use of resource bundles. 
The default language is English, but the user can specify the language to be Esperanto instead by using the "user.language" 
key of the JVM. For example, to specify the Esperanto language, one can run the following command: 
        
    java -Duser.language=eo Main

In the IntelliJ development environment (if IntelliJ was used as the IDE to run this program), the user can specify this key 
in the "Run / Debug Configurations"  in the "VM Options" field.

#### Objectives of the guessing game

The program prompts the user to think of an animal and then tries to guess what it is. If the computer fails,
the program should ask the user what this animal is and what statement can help distinguish it from another one.
This knowledge is supposed to be stored in a form of a binary tree and saved through serialization for another run of the program.

When the computer starts the game, it will ask questions starting from the top, that is, the root node. 
If the computer makes a wrong guess, it should ask the user two questions: first, what animal the user had in mind, 
and second, what statement can help the computer distinguish the animal it guessed (old) from the animal that the person 
actually thought of (new). The program should clarify whether that fact is correct for the new animal. 
After that, the name of the "old" animal in the tree is replaced with the new statement, and two new leaves are added 
to this node: one with the "old" animal and another with the "new" animal.

At the end of each session, the program should ask the user if they want to keep playing or quit the game.

# General Info

To learn more about this project, please visit [HyperSkill Website - Guess the Animal (Java)](https://hyperskill.org/projects/132).

This project's difficulty has been labelled as __Hard__ where this is how
HyperSkill describes each of its four available difficulty levels:

- __Easy Projects__ - if you're just starting
- __Medium Projects__ - to build upon the basics
- __Hard Projects__ - to practice all the basic concepts and learn new ones
- __Challenging Projects__ - to perfect your knowledge with challenging tasks

This repository contains

    animals package
        - Contains the contacts.Main java class that contains the main method to run the project
        - Contains the animals.TestInput java class that is used to check for proper user input
        - Contains the animals.SerializationUtils java class that allows for serialization of tree information
        - Contains the animals.Node java class that includes the implementation of the Node class of BinaryTree
        - Contains the animals.BinaryTree java class that includes the implementation of the BinaryTree class that stores Node object as its root
        - Contains the BinaryTreePrinter java class that includes the implementation to print the BinaryTree on to the console window
    
    main.resources Resource Bundles
        - Contains the messages.properties (for English) and messages_eo.properties (for Esperanto) 'messages' resource bundles
        - Contains the patterns.properties (for English) and patterns_eo.properties (for Esperanto) 'patterns' resource bundles

Project was built using java 21

# How to Run

Download the animals and main.resources repositories to your machine. Create a new project in IntelliJ IDEA, then move the downloaded 
repositories to the src folder and run the animals.Main java class.
After a single run of the program, a file "animals.db" will be saved to the same working directory. If a new fresh start of the program 
is needed, then "animals.db" needs to be deleted.
