
public class Answer {
//    ArrayList<ArrayList<Integer>> maze = new ArrayList<ArrayList<Integer>>();
//        maze.add([0, 0, 0, 0, 0, 0],[1, 1, 1, 1, 1, 0],[0, 0, 0, 0, 0, 0],[0, 1, 1, 1, 1, 1], [0, 1, 1, 1, 1, 1], [0, 0, 0, 0, 0, 0]);

    //{{0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 0}}
    public static void main(){
        int [][] maze = {{0, 1, 1, 0}, {0, 0, 0, 1},{1, 1, 0, 0},{1, 1, 1, 0}};
        answer(maze);
    }

    private static int count = 0;
    public static int answer(int[][] maze) {
        int len = maze.length;
        System.out.print(len);
        // Your code goes here.
        return count;
    }
}