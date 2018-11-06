class RnBTree<T> extends BinSearchTree<T> {

  @Override
  public void add(int key, T x){
    RnBTreeNode<T> chosen = (RnBTreeNode<T>) this.getRoot();
    RnBTreeNode<T> side = chosen;
    RnBTreeNode<T> newNode;

    if(this.getRoot() == null){
      this.setRoot(new RnBTreeNode<T>(key, x, null));
      ((RnBTreeNode<T>)this.getRoot()).recolorize();
      return;
    }

    do {
      chosen = side;
      side = (RnBTreeNode<T>)(key < chosen.getKey() ? chosen.getLeftChild() : chosen.getRightChild());
    } while(side != null);

    newNode = new RnBTreeNode<>(key, x, chosen);

    if(key < chosen.getKey()){
      chosen.setLeftChild(newNode);
    } else {
      chosen.setRightChild(newNode);
    }

    //rotate and recolorize [OK?]
    rotate(newNode);
    if(((RnBTreeNode<T>)this.getRoot()).getColor() == 1){ //root is always a black node
      ((RnBTreeNode<T>)this.getRoot()).recolorize();
    }
    //...
  }

  @Override
  public T search(int key){
    return null;
  }

  @Override
  public T remove(int key){
    return null;
  }

  /**
  * Recursive function to balance the tree
  */
  public void rotate(RnBTreeNode<T> node){
    RnBTreeNode<T> parent = (RnBTreeNode<T>) node.getParent();
    if(parent == null) return;
    RnBTreeNode<T> gparent = (RnBTreeNode<T>) parent.getParent();
    if(gparent == null) return;

    boolean isNodeRightChild = (parent.getRightChild() == node);
    boolean isParentRightChild = (gparent.getRightChild() == parent);

    if(node.getColor() != 1 || parent.getColor() != 1) return; //nothing to do

    //second case (first) : pull down blackness
    RnBTreeNode<T> gparentlc = (RnBTreeNode<T>)gparent.getLeftChild();
    RnBTreeNode<T> gparentrc = (RnBTreeNode<T>)gparent.getRightChild();
    int lccolor = (gparentlc == null ? 0 : gparentlc.getColor());
    int rccolor = (gparentrc == null ? 0 : gparentrc.getColor());
    if((isParentRightChild && lccolor == 1) || (!isParentRightChild && rccolor == 1)){
      gparent.recolorize();
      gparentrc.recolorize();
      gparentlc.recolorize();
      rotate(gparent);
      return; //?
    }

    //first case (second): simple rotation
    if(isNodeRightChild && isParentRightChild){
      rotateLeft(node);
      parent.recolorize();
      gparent.recolorize();
      rotate(parent); //recursion
      return;
    } else if(!isNodeRightChild && !isParentRightChild){
      rotateRight(node);
      parent.recolorize();
      gparent.recolorize();
      rotate(parent); //recursion
      return;
    }

    //third case: double rotate
    if(isNodeRightChild){
      //rotate LR
      rotateLR(node);
      node.recolorize();
      gparent.recolorize();
      rotate(node);
      return;
    } else {
      //rotate RL
      rotateRL(node);
      node.recolorize();
      gparent.recolorize();
      rotate(node);
      return;
    }
  }

  @Override
  public void printTree(){
    printTreeR((RnBTreeNode<T>)this.getRoot());
    System.out.println();
  }

  private void printTreeR(RnBTreeNode<T> root){
    int color = (root == null ? 0 : root.getColor());
    if(color == 0){
      String text = (root == null ? "NIL" : " "+root.getKey()+" ");
      System.out.print("\033[1;37;40m"+text+"\033[0m");
    } else {
      System.out.print("\033[1;37;41m "+root.getKey()+" \033[0m");
    }
    System.out.print(" ");
    if(root != null){
      printTreeR((RnBTreeNode<T>)root.getLeftChild());
      printTreeR((RnBTreeNode<T>)root.getRightChild());
    }
  }
}
