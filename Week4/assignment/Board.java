    import edu.princeton.cs.algs4.StdRandom;
    import java.util.ArrayDeque;
    import java.util.ArrayList;
    import java.util.Queue;

    /**
     * Created by mac on 2017/11/9.
     */
    public class Board {
    //    private int[] arr;
        private int mDimension;
        private int[][] mBlocks;
        private int rowa,cola;
        private int rowb,colb;
        public Board(int[][] blocks) {
            mBlocks = blocks;
            mDimension = blocks.length;
    //        arr = new int[mDimension * mDimension];
    //        for (int i = 0; i < dimension() * dimension(); i++) {
    //            int row = i / dimension();
    //            int col = i % dimension();
    //            arr[i] = blocks[row][col];
    //        }
            do {
                rowa = StdRandom.uniform(dimension());
                cola = StdRandom.uniform(dimension());
                rowb = StdRandom.uniform(dimension());
                colb = StdRandom.uniform(dimension());
            } while (mBlocks[rowa][cola] == mBlocks[rowb][colb] || mBlocks[rowa][cola] == 0 ||  mBlocks[rowb][colb]== 0);
        }

        public int dimension() {
            return mDimension;
        }
        public int hamming(){
            int hamming = 0;
            for(int i =0; i < dimension(); i++){
                for (int j = 0; j < dimension(); j++)
                if(mBlocks[i][j] !=0 && mBlocks[i][j]!=(i*dimension()+j+1)){
                    hamming++;
                }
            }
            return hamming;

        }
        public int manhattan() {
            int manhattan = 0;
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++){
                    if (mBlocks[i][j] != 0) {
                        manhattan += Math.abs(i - (mBlocks[i][j] - 1) / mDimension) + Math.abs(j - (mBlocks[i][j] - 1) % mDimension);
                    }
                }
            }
            return manhattan;
        }

        public boolean isGoal() {
            int count = 0;
            for (int i = 0; i < dimension(); i++) {
                for(int j = 0; j < dimension(); j++) {
                    count++;
                    if(count != dimension()*dimension()) {
                        if (mBlocks[i][j] != (i*dimension() + j + 1)) return false;
                    }else{
                        if(mBlocks[i][j] != 0) return false;
                    }
                }
            }
            return true;
        }

        public Board twin() {
//            boolean change = false;
            int[][] newBlocks = new int[mDimension][mDimension];
            for (int i = 0; i < mDimension; i++) {
                for (int j = 0; j < mDimension; j++){
                    int row = i;
                    int col = j;
                    if (row == rowa && col == cola || row == rowb && col == colb) {
//                        row = rowb;
//                        col = colb;
//                        change = true;
                        newBlocks[rowa][cola] = mBlocks[rowb][colb];
                        newBlocks[rowb][colb] = mBlocks[rowa][cola];
                        continue;
                    }
                    newBlocks[i][j] = mBlocks[row][col];
                }
            }

            return new Board(newBlocks);
        }

        public boolean equals(Object y) {
            if(y instanceof Board){
                Board comp = (Board) y;
                if (comp.dimension() != dimension()) return false;
                for (int i = 0; i < mDimension; i++) {
                    for (int j = 0; j < mDimension; j++) {
                        if (comp.mBlocks[i][j] != mBlocks[i][j]) return false;
                    }
                }
                return true;
            }
            return false;
        }

        public Iterable<Board> neighbors() {
            Queue<Board> neighbors = new ArrayDeque<>();

            for (int i = 0; i < mDimension; i++) {
                for (int j = 0; j < mDimension; j++) {
                    if (mBlocks[i][j] == 0) {
                        if (i - 1 >= 0) {
                            int[][] clone = new int[mDimension][mDimension];
                            for (int k = 0; k < mDimension; k++) {
                                System.arraycopy(mBlocks[k],0,clone[k],0,mDimension);
                            }
                            clone[i][j] = clone[i - 1][j];
                            clone[i - 1][j] = 0;
                            neighbors.add(new Board(clone));
                        }
                        if (i + 1 < mDimension) {
                            int[][] clone = new int[mDimension][mDimension];
                            for (int k = 0; k < mDimension; k++) {
                                    System.arraycopy(mBlocks[k],0,clone[k],0,mDimension);
                            }
                            clone[i][j] = clone[i + 1][j];
                            clone[i + 1][j] = 0;
                            neighbors.add(new Board(clone));
                        }
                        if (j - 1 >= 0) {
                            int[][] clone = new int[mDimension][mDimension];
                            for (int k = 0; k < mDimension; k++) {
                                System.arraycopy(mBlocks[k],0,clone[k],0,mDimension);
                            }
                            clone[i][j] = clone[i][j - 1];
                            clone[i][j - 1] = 0;
                            neighbors.add(new Board(clone));
                        }
                        if (j + 1 < mDimension) {
                            int[][] clone = new int[mDimension][mDimension];
                            for (int k = 0; k < mDimension; k++) {
                                System.arraycopy(mBlocks[k],0,clone[k],0,mDimension);
                            }
                            clone[i][j] = clone[i][j + 1];
                            clone[i][j + 1] = 0;
                            neighbors.add(new Board(clone));
                        }
                        return neighbors;
                    }
                }
            }
            return neighbors;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(mDimension + "\n");
            for (int i = 0; i < mDimension; i++) {
                for (int j = 0; j < mDimension; j++) {
                    s.append(String.format("%2d ", mBlocks[i][j]));
                }
                s.append("\n");
            }
            return s.toString();
        }
    }

