class RnBTree<T> extends BinSearchTree<T> {

  @Override
  public void add(int key, T x){
    RnBTreeNode<T> chosen = this.getRoot();
    RnBTreeNode<T> side = chosen;
    RnBTreeNode<T> newNode;

    if(this.getRoot() == null){
      this.setRoot(new RnBTreeNode<T>(key, x, null));
      return;
    }

    do {
      chosen = side;
      side = (key < chosen.getKey() ? chosen.getLeftChild() : chosen.getRightChild());
    } while(side != null);

    newNode = new RnBTreeNode<>(key, x, chosen);

    if(key < chosen.getKey()){
      chosen.setLeftChild(newNode);
    } else {
      chosen.setRightChild(newNode);
    }

    //rotate and recolorize [OK?]
    rotate(newNode);
    if(root.getColor() == 1){ //root is always a black node
      root.recolorize();
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
    RnBTreeNode<T> parent = node.getParent();
    RnBTreeNode<T> gparent = parent.getParent();

    boolean isNodeRightChild = (parent.getRightChild() == node);
    boolean isParentRightChild = (gparent.getRightChild() == parent);

    if(node.getColor() != 1 || parent.getColor() != 1) return; //nothing to do

    //first case : simple rotation
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

    //second case : pull down blackness
    if((isParentRightChild && gparent.getLeftChild().getColor() == 1) || (!isParentRightChild && gparent.getRightChild().getColor() == 1)){
      gparent.recolorize();
      gparent.getRightChild().recolorize();
      gparent.getLeftChild().recolorize();
      rotate(gparent);
      return; //?
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
}
