import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by mac on 2017/11/9.
 */

public class Solver {
    private class SearchNode {
        int moves;
        Board board;
        SearchNode preNd;

        public SearchNode(Board inital) {
            moves = 0;
            preNd = null;
            board = inital;
        }
    }

    private class PQcomparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int priorityO1 = o1.board.manhattan() + o1.moves;
            int priorityO2 = o2.board.manhattan() + o2.moves;
            if (priorityO1 < priorityO2) return -1;
            else if (priorityO1 > priorityO2) return 1;
            else return 0;
        }
    }
    private SearchNode solver;
    public Solver(Board initial) {
        PQcomparator pQcomparator = new PQcomparator();
        SearchNode node = new SearchNode(initial);
        SearchNode twinNode = new SearchNode(initial.twin());
        MinPQ<SearchNode> PQ = new MinPQ<>(pQcomparator);
        MinPQ<SearchNode> twinPQ = new MinPQ<>(pQcomparator);
        twinPQ.insert(twinNode);
        PQ.insert(node);

        SearchNode minSearchNode = PQ.delMin();
        SearchNode twinMinSearchNode = twinPQ.delMin();

        while (!minSearchNode.board.isGoal() && !twinMinSearchNode.board.isGoal()) {
            for (Board s : minSearchNode.board.neighbors()) {
                if (minSearchNode.preNd == null || !s.equals(minSearchNode.preNd.board)) {
                    SearchNode newNode = new SearchNode(s);
                    newNode.moves = minSearchNode.moves + 1;
                    newNode.preNd = minSearchNode;
                    PQ.insert(newNode);
                }
            }

            for (Board s : twinMinSearchNode.board.neighbors()) {
                if (twinMinSearchNode.preNd == null || !s.equals(twinMinSearchNode.preNd.board)) {
                    SearchNode newNode = new SearchNode(s);
                    newNode.moves = twinMinSearchNode.moves + 1;
                    newNode.preNd = twinMinSearchNode;
                    twinPQ.insert(newNode);
                }
            }
            if(PQ.size()==0||twinPQ.size()==0) break;
            minSearchNode = PQ.delMin();
            twinMinSearchNode = twinPQ.delMin();
        }

        if(minSearchNode.board.isGoal()) solver = minSearchNode;
        else solver = null;
    }

    public boolean isSolvable() {
        return solver!=null;
    }

    public int moves() {
       return solver.moves;
    }

    public Iterable<Board> solution() {
        if(solver == null) return null;
        Stack<Board> solution = new Stack<>();
        while (solver!=null){
            solution.push(solver.board);
            solver = solver.preNd;
        }
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
