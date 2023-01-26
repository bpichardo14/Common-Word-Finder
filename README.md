# Description
Hey! Thanks for checking out my project. This project's goal was to use three of the data structures I developed this semester in my Data Structures in JAVA class to create a program that finds the n most common words in a document. These structures included a binary search tree, Adelson-Velskii and Landis tree, and a hash map. BSTMap, AVLMap, and MyHashMap implemented the MyMap interface. I could create these data structures with proper object-oriented programming and refer to them from a MyMap reference. Though all of these classes implement the same interface, their methods are implemented differently, leading to various execution times on the user's computer.  

# Guiding questions
 - What data structure do you expect to have the best (fastest) performance?
  
 - Which one do you expect to be the slowest?
  
 - Do the results of timing your program’s execution match yourexpectations?

# Initial predictions

I anticipated that the hashmap will exhibit the highest level of performance. This is due to the fact that, in terms of time complexity, the average case of insertion and retrieval is characterized by constant time. This is not the case with both binary search tree (BST) and AVL tree. It is well-established that, for large inputs, the BST can run in O(h) time, where h represents the height of the tree. This is a result of the significant imbalance that arises during the insertion process. In contrast, the time complexity of insertion and retrieval in an AVL tree is Theta(logN), which makes it faster than a BST tree but slower than a hashmap.

# Analysis of observations

As predicted, the running time for the hashmap was found to be inferior to that of both the AVL and BST. However, the discrepancy was not as significant as expected. One potential explanation for this outcome is that, regardless of the data structure employed, an array is utilized to sort the map, resulting in similar run times. Additionally, it is worth noting that when a word is inserted into the BST or AVL, the words are to a degree sorted, which may mitigate the degree of imbalance and resulting in run times similar to those of the AVL and BST.

# Usage
If you intend to utilize this project in your own learning of data structures, it is recommended that you implement your own versions of the binary search tree (BST), AVL tree, and hashmap utilizing generics. Once your code has been compiled, you may execute the program using the following command:

java CommonWordFinder <filename> <desired data structure: bst, avl, hash> <word limit>
