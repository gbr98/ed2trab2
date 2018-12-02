import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;

class Main {
  public static void main(String[] args){
    test();
    allSteps(new int[]{1000, 5000, 10000, 50000, 100000, 500000});
  }

  public static void allSteps(int[] values){

    for(int n : values){

      SearchTree<Deputies>[] trees = new SearchTree[5];
      SearchTree<Deputies>[] treesBk = new SearchTree[5];

      int[][] time = new int[trees.length][5]; //5 steps
      int[][] comp = new int[trees.length][5];
      int[][] copy = new int[trees.length][5];

      for(int i = 0; i < 5; i++){

        trees[0] = treesBk[0] = new RnBTree<>();
        trees[1] = treesBk[1] = new AVLTree<>();
        trees[2] = treesBk[2] = new SplayTree<>();
        trees[3] = treesBk[3] = new BTree<>();
        trees[4] = treesBk[4] = new MyTree<>();

        Deputies[] dep = Loader.loadRandomList("deputies_dataset.csv", n);
        System.out.println();

        for(int j = 0; j < trees.length; j++){
          //insertion
          time[j][0] += fillTree(trees[j], dep);
          comp[j][0] += trees[j].getComp();
          copy[j][0] += trees[j].getCopy();

          //search (1)
          int[] searchSet = new int[n];
          Random r = new Random();
          for(int k = 0; k < n/2; k++){
            searchSet[k] = dep[k].getID();
          }
          for(int k = n/2; k < n; k++){
            searchSet[k] = r.nextInt(Loader.getLastNOLines());
          }
          trees[j].resetComp();
          trees[j].resetCopy();
          time[j][1] += searchAll(trees[j], searchSet);
          comp[j][1] += trees[j].getComp();
          copy[j][1] += trees[j].getCopy();

          //search (2)
          searchSet = new int[n];
          r = new Random();
          for(int k = 0; k < n*3/10.0; k++){
            searchSet[k] = dep[k].getID();
          }
          for(int k = (int) Math.floor(n*3/10.0); k < n*6/10.0; k++){
            searchSet[k] = dep[k-((int)Math.floor(n*3/10.0))].getID();
          }
          for(int k = (int) Math.floor(n*6/10.0); k < n; k++){
            searchSet[k] = r.nextInt(Loader.getLastNOLines());
          }
          Arrays.sort(searchSet);
          trees[j].resetComp();
          trees[j].resetCopy();
          time[j][2] += searchAll(trees[j], searchSet);
          comp[j][2] += trees[j].getComp();
          copy[j][2] += trees[j].getCopy();

          //remotion (1)
          int[] remSet = new int[n];
          for(int k = 0; k < n/2; k++){
            remSet[k] = dep[k].getID();
          }
          for(int k = n/2; k < n; k++){
            remSet[k] = r.nextInt(Loader.getLastNOLines());
          }
          trees[j].resetComp();
          trees[j].resetCopy();
          time[j][3] += removeAll(trees[j], remSet);
          comp[j][3] += trees[j].getComp();
          copy[j][3] += trees[j].getCopy();

          //remoton (2) >> Tree is not empty, using backup
          fillTree(treesBk[j], dep);
          remSet = new int[n];
          for(int k = 0; k < n*3/10.0; k++){
            remSet[k] = dep[k].getID();
          }
          for(int k = (int) Math.floor(n*3/10.0); k < n*6/10.0; k++){
            remSet[k] = dep[k-((int)Math.floor(n*3/10.0))].getID();
          }
          for(int k = (int) Math.floor(n*6/10.0); k < n; k++){
            remSet[k] = r.nextInt(Loader.getLastNOLines());
          }
          Arrays.sort(remSet);
          treesBk[j].resetComp();
          treesBk[j].resetCopy();
          time[j][4] += removeAll(treesBk[j], remSet);
          comp[j][4] += treesBk[j].getComp();
          copy[j][4] += treesBk[j].getCopy();
        }
      }

      for(int i = 0; i < trees.length; i++){
        printToFile("resultsIns.txt", "N="+n+" TREE"+(i+1)+" TIME "+time[i][0]/5+"\n");
        printToFile("resultsIns.txt", "N="+n+" TREE"+(i+1)+" COMP "+comp[i][0]/5+"\n");
        printToFile("resultsIns.txt", "N="+n+" TREE"+(i+1)+" COPY "+copy[i][0]/5+"\n\n");

        printToFile("resultsSea.txt", "N="+n+" TREE"+(i+1)+" TIME "+time[i][1]/5+"\n");
        printToFile("resultsSea.txt", "N="+n+" TREE"+(i+1)+" COMP "+comp[i][1]/5+"\n");
        printToFile("resultsSea.txt", "N="+n+" TREE"+(i+1)+" COPY "+copy[i][1]/5+"\n\n");

        printToFile("resultsSea2.txt", "N="+n+" TREE"+(i+1)+" TIME "+time[i][2]/5+"\n");
        printToFile("resultsSea2.txt", "N="+n+" TREE"+(i+1)+" COMP "+comp[i][2]/5+"\n");
        printToFile("resultsSea2.txt", "N="+n+" TREE"+(i+1)+" COPY "+copy[i][2]/5+"\n\n");

        printToFile("resultsRem.txt", "N="+n+" TREE"+(i+1)+" TIME "+time[i][3]/5+"\n");
        printToFile("resultsRem.txt", "N="+n+" TREE"+(i+1)+" COMP "+comp[i][3]/5+"\n");
        printToFile("resultsRem.txt", "N="+n+" TREE"+(i+1)+" COPY "+copy[i][3]/5+"\n\n");

        printToFile("resultsRem2.txt", "N="+n+" TREE"+(i+1)+" TIME "+time[i][4]/5+"\n");
        printToFile("resultsRem2.txt", "N="+n+" TREE"+(i+1)+" COMP "+comp[i][4]/5+"\n");
        printToFile("resultsRem2.txt", "N="+n+" TREE"+(i+1)+" COPY "+copy[i][4]/5+"\n\n");
      }
    }
  }

  /**
  * Return the time (in milliseconds) taken to fill the tree.
  */
  private static long fillTree(SearchTree<Deputies> tree, Deputies[] dep){
    long startTime = System.currentTimeMillis();
    for(Deputies d : dep){
      tree.add(d.getID(), d);
    }
    long stopTime = System.currentTimeMillis();
    return stopTime - startTime;
  }

  /**
  * Return the time (in milliseconds) taken to search for all
  * values in searchSet.
  */
  public static long searchAll(SearchTree<Deputies> tree, int[] searchSet){
    long startTime = System.currentTimeMillis();
    for(int key : searchSet){
      tree.search(key);
    }
    long stopTime = System.currentTimeMillis();
    return stopTime - startTime;
  }

  public static long removeAll(SearchTree<Deputies> tree, int[] remSet){
    long startTime = System.currentTimeMillis();
    for(int key : remSet){
      tree.remove(key);
    }
    long stopTime = System.currentTimeMillis();
    return stopTime - startTime;
  }

  private static void printToFile(String filename, String data){
    BufferedWriter bw = null;
    try {
      bw = new BufferedWriter(new FileWriter(filename, true));
      bw.write(data);
    } catch(Exception e){
      e.printStackTrace();
    } finally {
      try {
        if(bw!=null) bw.close();
      } catch(Exception e){
        e.printStackTrace();
      }
    }
  }

  public static void test(){
    RnBTree<Integer> rnb = new RnBTree<>();
    rnb.add(5, 5);
    rnb.printTree();
    rnb.add(2, 2);
    rnb.printTree();
    rnb.add(6, 6);
    rnb.printTree();
    rnb.add(7, 7);
    rnb.printTree();
    rnb.add(3, 3);
    rnb.printTree();
    rnb.add(8, 8);
    rnb.printTree();
    rnb.add(9, 9);
    rnb.printTree();
    rnb.add(10, 10);
    rnb.printTree();
    rnb.add(11, 11);
    rnb.printTree();
    rnb.add(2, 2);
    rnb.printTree();

    SplayTree<Integer> st = new SplayTree<>();
    st.add(5, 5);
    st.printTree();
    st.add(2, 2);
    st.printTree();
    st.add(6, 6);
    st.printTree();
    st.add(7, 7);
    st.printTree();
    st.add(3, 3);
    st.printTree();
    st.add(8, 8);
    st.printTree();
    st.add(9, 9);
    st.printTree();
    st.add(2, 2);
    st.printTree();
    System.out.println("Searching for 7");
    st.search(7);
    st.printTree();
    st.remove(3);
    st.printTree();
  }
}
