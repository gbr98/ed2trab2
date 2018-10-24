class Main {
  public static void main(String[] args){
    RnBTree<Integer> rnb = new RnBTree<>();
    rnb.add(5, 5);
    rnb.add(2, 2);
    rnb.add(6, 6);
    rnb.add(7, 7);
    rnb.add(3, 3);
    rnb.printTree();
  }
}
