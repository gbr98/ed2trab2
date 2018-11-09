class Main {
  public static void main(String[] args){
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
