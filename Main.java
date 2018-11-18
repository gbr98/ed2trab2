import java.io.BufferedWriter;
import java.io.FileWriter;

class Main {
  public static void main(String[] args){
    test();
    insertion(new int[]{1000, 5000, 10000, 50000});
  }

  public static void insertion(int[] values){

    for(int n : values){

      BinSearchTree<Deputies>[] trees = new BinSearchTree[5];

      int[] time = new int[trees.length];
      int[] comp = new int[trees.length];
      int[] copy = new int[trees.length];

      for(int i = 0; i < 5; i++){

        trees[0] = new RnBTree<>();
        trees[1] = new AVLTree<>();
        trees[2] = new SplayTree<>();
        trees[3] = new BTree<>();
        trees[4] = new MyTree<>();

        Deputies[] dep = Loader.loadRandomList("deputies_dataset.csv", n);
        System.out.println();

        for(int j = 0; j < trees.length; j++){
          time[j] += fillTree(trees[j], dep);
          comp[j] += trees[j].getComp();
          copy[j] += trees[j].getCopy();
        }

        /*BinSearchTree<Deputies> rnb = new RnBTree<>();
        time[0] += fillTree(rnb, dep);
        comp[0] += rnb.getComp();
        copy[0] += rnb.getCopy();

        BinSearchTree<Deputies> avl = new AVLTree<>();
        time[1] += fillTree(avl, dep);
        comp[1] += avl.getComp();
        copy[1] += avl.getCopy();

        BinSearchTree<Deputies> st = new SplayTree<>();
        time[2] += fillTree(st, dep);
        comp[2] += st.getComp();
        copy[2] += st.getCopy();

        BinSearchTree<Deputies> bt = new BTree<>();
        time[3] += fillTree(bt, dep);
        comp[3] += bt.getComp();
        copy[3] += bt.getCopy();

        BinSearchTree<Deputies> mt = new MyTree<>();
        time[4] += fillTree(mt, dep);
        comp[4] += mt.getComp();
        copy[4] += mt.getCopy();*/
      }

      for(int i = 0; i < trees.length; i++){
        printToFile("resultsIns.txt", "N="+n+" TREE"+(i+1)+" TIME "+time[i]+"\n");
        printToFile("resultsIns.txt", "N="+n+" TREE"+(i+1)+" COMP "+comp[i]+"\n");
        printToFile("resultsIns.txt", "N="+n+" TREE"+(i+1)+" COPY "+copy[i]+"\n\n");
      }
    }
  }

  /**
  * Return the time (in milliseconds) taken to fill the tree.
  */
  private static long fillTree(BinSearchTree<Deputies> tree, Deputies[] dep){
    long startTime = System.currentTimeMillis();
    for(Deputies d : dep){
      tree.add(d.getID(), d);
    }
    long stopTime = System.currentTimeMillis();
    return stopTime - startTime;
  }

  public static void search(int[] values, BinSearchTree<Deputies>[] trees){
    for(int n : values){
      for(BinSearchTree<Deputies> tree : trees){
        //TODO
      }
    }
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
  }
}
