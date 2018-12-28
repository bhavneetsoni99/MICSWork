This program is written in Java on JDK 8.0.

Program is an effort to interactively prompt the user to run some methods, following methods can be called by the user. For the ease of use effort has been made to make the commands caseinsesitve (eg, BhavneetS == Bhavneet)

1. addMembers - this function will interactivelty add members to the social network, and will also add empty adjacency lists. Function can add multiple memebrs at a tome. Funciton will make sure the names are stored in proper format.  First alphabets in capitals.

2. getMembers  - this function will print all the members (Vertexes) of the social network

3. setRelation - this method will promp the user to add relation between multiple users. If a member being added to the network in not present in the vertex list, it will be automatically added. Relations are set two way e.g if you add Abc as a member of Xyz, it will set Xyz as a memebr of Abc also.

4. PrintNetwork - this funciton will print the network, vertex and its adjaceny list

5. seePath(x,y) - this function will show the shortest path to the between x and y

6. hopTree(x,1) - this function will show all the members at the number of hops from the member x

7. Exit - this will close the user interaction

For saving the trouble of entring the data again and again I have hardcoded a sample network. User can add to this network if they wish to.