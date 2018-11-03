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

  /**
  * Basic Left-Right rotation for binary search tree
  * @params node: the x node
  */
  public void rotateLR(BinTreeNode<T> node){
    BinTreeNode<T> parent = node.getParent();
    BinTreeNode<T> gparent = parent.getParent();
    BinTreeNode<T> ggparent = gparent.getParent();

    //first step
    if(ggparent != null){
      if(ggparent.getLeftChild() == gparent){
        ggparent.setLeftChild(node);
      } else {
        ggparent.setRightChild(node);
      }
    }
    node.setParent(ggparent);

    //second step
    gparent.setLeftChild(node.getRightChild());
    node.getRightChild().setParent(gparent);
    parent.setRightChild(node.getLeftChild());
    node.getLeftChild().setParent(parent);

    //third step
    node.setLeftChild(parent);
    parent.setParent(node);
    node.setRightChild(gparent);
    gparent.setParent(node);
  }

  /**
  * Basic Right-Left rotation for binary search tree
  * @params node: the x node
  */
  public void rotateRL(BinTreeNode<T> node){
    BinTreeNode<T> parent = node.getParent();
    BinTreeNode<T> gparent = parent.getParent();
    BinTreeNode<T> ggparent = gparent.getParent();

    //first step
    if(ggparent != null){
      if(ggparent.getLeftChild() == gparent){
        ggparent.setLeftChild(node);
      } else {
        ggparent.setRightChild(node);
      }
    }
    node.setParent(ggparent);

    //second step
    gparent.setRightChild(node.getLeftChild());
    node.getLeftChild().setParent(gparent);
    parent.setLeftChild(node.getRightChild());
    node.getRightChild().setParent(parent);

    //third step
    node.setLeftChild(gparent);
    gparent.setParent(node);
    node.setRightChild(parent);
    parent.setParent(node);
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
