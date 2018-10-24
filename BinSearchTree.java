class BinSearchTree<T> {
  private BinTreeNode<T> root;
  private int n;

  public BinSearchTree(){
    this.n = 0;
    this.root = null;
  }

  public void add(int key, T x){

  }

  public T search(int key){
    return null;
  }

  public T remove(int key){
    return null;
  }

  public void printTree(){
    printTreeR(this.root);
    System.out.println();
  }

  private void printTreeR(BinTreeNode<T> root){
    if(root != null){
      System.out.print(root.getKey()+" ");
    } else {
      System.out.print("NIL "); return;
    }
    printTreeR(root.getLeftChild());
    printTreeR(root.getRightChild());
  }

  public BinTreeNode<T> getRoot(){
    return this.root;
  }

  public int getN(){
    return this.n;
  }

  public void setRoot(BinTreeNode<T> root){
    this.root = root;
  }

  public void setN(int n){
    this.n = n;
  }
}
