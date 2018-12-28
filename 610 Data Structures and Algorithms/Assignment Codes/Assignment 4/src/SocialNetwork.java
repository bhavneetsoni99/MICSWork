import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.corba.se.impl.orbutil.graph.Node;
import com.sun.corba.se.impl.orbutil.graph.NodeData;
import sun.nio.ch.Net;

import java.util.*;

/**
 * Created by bsoni1 on 11/30/2016.
 */
public class SocialNetwork {

    public static ArrayList<String> Members = new ArrayList<String>();
    public static ArrayList<ArrayList<String>> Network =  new ArrayList<ArrayList<String>>();

    ArrayList<ArrayList<String>> Connections = new ArrayList<ArrayList<String>>();

    public static void main (String[] args) {
        Members.add("Madhu");
        Members.add("Giridhar");
        Members.add("Sachin");
        Members.add("Suman");
        Members.add("Bhavneet");
        Members.add("Preeti");
        Members.add("Maanya");
        Members.add("Sakshi");
        Members.add("Lakshita");
        Members.add("Kanish");
        Members.add("Tammy");

        for (int i = 0; i< Members.size(); i++){
            ArrayList<String> branch = new ArrayList<String>();
            Network.add(branch);
        }
        Network.get(0).add("Giridhar");
        Network.get(1).add("Madhu");
        Network.get(0).add("Sachin");
        Network.get(0).add("Bhavneet");
        Network.get(2).add("Madhu");
        Network.get(2).add("Suman");
        Network.get(2).add("Sakshi");
        Network.get(4).add("Madhu");
        Network.get(4).add("Preeti");
        Network.get(4).add("Kanish");
        Network.get(4).add("Maanya");
        Network.get(6).add("Bhavneet");
        Network.get(6).add("Preeti");
        Network.get(6).add("Lakshita");
        Network.get(8).add("Maanya");
        Network.get(8).add("Kanish");
        Network.get(9).add("Lakshita");
        Network.get(9).add("Bhavneet");
        Network.get(7).add("Sachin");
        Network.get(7).add("Suman");
        Network.get(5).add("Bhavneet");
        Network.get(5).add("Maanya");
        Network.get(3).add("Sachin");
        Network.get(3).add("Sakshi");
        printNetwork();
        startInteraction(); // main logic starts with this function
    }
    //this function interacts with the user to get input for actions to be performed
    public static void startInteraction() {
        System.out.println("********************************");
        System.out.println("----- You can Choose from one of the Following options ----- ");
        System.out.println("You can either ----- exit, addMembers, getMembers, setRelation, PrintNetwork, seePath(x,y) or hopTree(x,1) ---- ");
        System.out.print("------->");
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        // clear the userinput so proper case
        userInput = userInput.trim();
        userInput = userInput.toLowerCase();

        if (userInput.equals("exit")) {
            System.out.println("Have a nice day");
        } else if (userInput.equals("addmembers")) {
            addMembers();
        } else if (userInput.equals("printnetwork")) {
            printNetwork();
        }else if (userInput.equals("setrelation")){
            setRelation();
        }else if(userInput.equals("getmembers")) {
            printVertexes();
        }else if (userInput.substring(0, 7).equals("seepath")){
            //stripping arguments from the user input
            String[] pathBetween = userInput.substring(8).split(",");
            String first = toProperCase(pathBetween[0]);
            String second = toProperCase(pathBetween[1].substring(0, pathBetween[1].length()-1));
            if (!Members.contains(first) || !Members.contains(second)) {
                System.out.println("Member(s) does not exist in the network, try again");
                startInteraction();
            }else if(first.equals(second)){
                System.out.println("**Its the same person**");
                startInteraction();
            } else{
                makeSearchTree();
                //steps.add(first);
                //ArrayList<String> path =
                        seePath(first, second);
                if(found) {

                  //  System.out.println(path);
                }else {
                    System.out.println("!!!!! There is no relation between the two. !!!!!");
                }
                startInteraction();
            }
        } else if (userInput.substring(0, 7).equals("hoptree")) {
            //stripping arguments from the user input
            String[] arguments = userInput.substring(8).split(",");
            String person = toProperCase(arguments[0]);
            try {
                String hopsArg = arguments[1];
                hopsArg = hopsArg.trim();
                hopsArg = hopsArg.replaceAll("\\s","");
                int hops = Integer.valueOf(hopsArg.substring(0, hopsArg.length()-1));
                if(!Members.contains(person)){

                }else{
                    if(hops<0){
                        System.out.println(" Please enter a valid value");
                    }else {
                        hopTree(person, hops);
                        allReadyAdded.clear();
                    }
                }
            }catch (NumberFormatException e){
                System.out.print("incorrect format try again");
            }

        }else{
            System.out.println("Enter a valid input value");
            parents.clear();
            startInteraction();
        }
    }

    // Following function will add new members to the members list formated properly
    public static void addMembers () {
        System.out.println("Enter the name, if more than 1 enter seperated by (,) ");
        Scanner input = new Scanner(System.in);
        String enteredData = input.nextLine();
        String[] members = enteredData.split(",");
        for (int i = 0; i < members.length; i++) {
            checkAndAdd(members[i]);
        }
        System.out.println("Members added to the network");
        System.out.println("********************************");
        System.out.println("Updated network is as below");
        printVertexes();
        System.out.println();

        startInteraction();
    }
    //prints all the members in the graph
    public static void printVertexes() {
        int n = Members.size();
        for (int i = 0; i < n; ++i) {
            System.out.println(Members.get(i));
        }
        startInteraction();
    }
    //Following function prints the complete network
    //prints Node----->connections
    public static void printNetwork(){
        int len = Network.size();
        System.out.println(len);
        for(int i = 0; i <len; i++){
            System.out.print(Members.get(i)+ "  ----->  ");
            System.out.print(Network.get(i));
            System.out.println();

        }
        startInteraction();
    }
    //Following function strips the white spaces and return full name in proper case
    //Caps first character of first and last name
    public static String toProperCase(String tempName){
        tempName = tempName.trim();
        String [] fullname = tempName.split(" ");
        String FullName = new String();
        int l = fullname.length;
        if (l>1){
            for(int i = 0; i <l-1; i++ ) {
                if (fullname[i] == " ") {

                } else {
                    String fname = new String();
                     try {
                        fname = fullname[i].substring(0, 1).toUpperCase() + fullname[i].substring(1).toLowerCase() + " ";
                     }catch (StringIndexOutOfBoundsException e){

                     }

                    FullName += fname;
                }
            }
            FullName += fullname[l-1].substring(0, 1).toUpperCase() + fullname[l-1].substring(1).toLowerCase();
        }else {
            FullName = tempName.substring(0, 1).toUpperCase() + tempName.substring(1).toLowerCase();
        }
        return FullName;
    }
    public static void checkAndAdd(String name){
        String memberToBeAdded = toProperCase(name);
        if(Members.indexOf(memberToBeAdded) > 0){
            //dont do any thing if it already exist in the Members list
        }else {
            Members.add(memberToBeAdded);
            ArrayList<String> branch = new ArrayList<String>();
            Network.add(branch);
        }
    }
    public static void setRelation() {//, String relation){
        System.out.println("Set the relation ships, first enter the member u want to enter to");
        System.out.println("Member you want to add to ----> ");
        Scanner input = new Scanner(System.in);
        String memberEntered = input.next();
        memberEntered = toProperCase(memberEntered);
            int indexNumber = Members.indexOf(memberEntered);
            if (indexNumber < 0){
                System.out.println("*******Enter a valid member name*******");
                setRelation();
            }else {
                System.out.println("Members is at  -  " + indexNumber);
                System.out.println("Enter the members directly connected, if more than 1 enter seperated by (,) ");
                Scanner inputLinks = new Scanner(System.in);
                String membersToBeAdded = inputLinks.nextLine();
                String[] addMembersToBranch = membersToBeAdded.split(",");
                for (int i = 0; i < addMembersToBranch.length; i++) {
                    String addMemberToBranch = toProperCase(addMembersToBranch[i]);
                    checkAndAdd(addMembersToBranch[i]);
                    if(Network.get(indexNumber).indexOf(addMemberToBranch)< 0) {
                        Network.get(indexNumber).add(addMemberToBranch);
                        Network.get(Members.indexOf(addMemberToBranch)).add(memberEntered);
                    }
                }
                printNetwork();
                startInteraction();
            }



    }

    private static boolean found = false;

    private static  ArrayList<String>  temp = new ArrayList<String>();//
    private  static ArrayList<String> allreadyCheckedPath = new ArrayList<String>();

    private static ArrayList<parentChild> step = new ArrayList<parentChild>();

    private static ArrayList<String> black = new ArrayList<String>();
    private static ArrayList<ArrayList<String>> path = new ArrayList<ArrayList<String>>();

    //Sort of a combination of depth first and breadth first search algorithm

    public static void seePath(String first, String second) {
        /* logic behind this function is that we look in the direct child for the first member
            we add the first to a search path list that we are maintaining called as parents
            if we find the second as the direct child we end the function and print path (Parents)
            else we call this function recurs
         */
        System.out.println("starting from  "+  first + " looking for " + second);
        int firstLocation = Members.indexOf(first);
        allreadyCheckedPath.add(first);
        //parents is a list to hold the search point we are at
        parents.add(first); //add the search node to the parents list
        searchTree.get(firstLocation).color = "grey";
        String thisNodeParent = searchTree.get(firstLocation).parent;
        ArrayList<String> thisNode = searchTree.get(firstLocation).child;
        thisNode.removeAll(allreadyCheckedPath);

        System.out.println("child are  - "+ thisNode);
        if (thisNode.isEmpty()) {
            searchTree.get(firstLocation).color = "Black";
            parents.remove(first);
        } else {
            if (thisNode.contains(second)) {
                found = true;
                parents.add(second);
                printPath();
            } else {
                allreadyCheckedPath.addAll(thisNode);
                int interator = thisNode.size();
                for (int i = 0; i < interator; i++) {
                    if (!found) {
                        seePath(thisNode.get(i), second); // keep calling it recursively till you find in the
                        if(!found){
                            //if the destination was not found  in this step remove the parent node from the search path
                            parents.remove(thisNode.get(i));
                        }
                    }
                }
            }
        }
    }
    private static ArrayList<String> parents=  new ArrayList<String>();
    private static void printPath(){
        System.out.print(parents);
        for(int i = 0; i <parents.size(); i++){
             System.out.print(parents.get(i) +" -->");
        }
        parents.clear();
    }

    public static class parentChild{
        String parent;
        ArrayList<String> child;
        String color;
        public parentChild(String p, ArrayList<String> c) {
            parent = p;
            child = c;
            color = "white";
        }
    }
    private static ArrayList<parentChild> searchTree = new ArrayList<parentChild>();
        private static void makeSearchTree(){
            for (int i = 0; i <Members.size(); i++){
                parentChild parent =  new parentChild(Members.get(i), Network.get(i));
                searchTree.add(parent);
            }
        }
    private static ArrayList<ArrayList<String>> tree = new ArrayList<ArrayList<String>>();
    private static int count = 0;
    private static ArrayList<String> allReadyAdded = new ArrayList<String>();
    public static void hopTree(String person, int hops) {
        if (hops == 0) {
            System.out.println(person);
        } else {
            ArrayList<String> nextLevel = new ArrayList<String>();
            nextLevel.add(person);
            tree.add(nextLevel);

            allReadyAdded.add(person);
            while (count< hops) {
                //keep gettting next levels till the desired hops value

                getLevel(tree.get(count), hops); //get unique members on next level
                count++;
            }
            for (int i = 0 ; i < tree.size() ; i++){
                System.out.print("Members at Level " + i + " are - ");
                System.out.println(tree.get(i));
            }
        }
    }
    public static void getLevel (ArrayList<String> level, int hops){
        int levelSize = level.size();
        if(levelSize>0) {
            ArrayList<String> nextLevel = new ArrayList<String>();
                // to make sure we have unique members added only removing already checked memebers

            for (int i = 0; i < levelSize; i++) {
                int memPos = Members.indexOf(level.get(i));
                ArrayList<String> temp = Network.get(memPos);
                temp.removeAll(allReadyAdded);
                nextLevel.addAll(temp);
                allReadyAdded.addAll(temp);
            }
            if (nextLevel.isEmpty()){
                System.out.println("Tree does not have these many levels");
            }else {
                tree.add(nextLevel);
            }
        }

    }
}
