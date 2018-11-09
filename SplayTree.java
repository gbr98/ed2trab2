class SplayTree<T> extends BinSearchTree<T> {

  @Override
  public void add(int key, T x){
    BinTreeNode<T> chosen = this.getRoot();
    BinTreeNode<T> side = chosen;
    BinTreeNode<T> newNode;

    if(this.getRoot() == null){
      this.setRoot(new BinTreeNode<T>(key, x, null));
      return;
    }

    do {
      chosen = side;
      side = (key < chosen.getKey() ? chosen.getLeftChild() : chosen.getRightChild());
    } while(side != null);

    newNode = new BinTreeNode<>(key, x, chosen);

    if(key < chosen.getKey()){
      chosen.setLeftChild(newNode);
    } else {
      chosen.setRightChild(newNode);
    }

    //rotate
    rotate(newNode);
    //...
  }

  private void rotate(BinTreeNode<T> node){

    BinTreeNode<T> parent = node.getParent();
    if(parent == null) return;
    BinTreeNode<T> gparent = parent.getParent();

    if(gparent == null){
      if(parent.getRightChild() == node){
        simpleRotateLeft(node);
      } else {
        simpleRotateRight(node);
      }
      rotate(node);
      return;
    } else {
      boolean isNodeRightChild = (parent.getRightChild() == node);
      boolean isParentRightChild = (gparent.getRightChild() == parent);
      if(isNodeRightChild && isParentRightChild){
        rotateLeft(node);
        simpleRotateLeft(node);
      } else if(!isNodeRightChild && !isParentRightChild){
        rotateRight(node);
        simpleRotateRight(node);
      } else if(isNodeRightChild && !isParentRightChild){
        rotateLR(node);
      } else if(!isNodeRightChild && isParentRightChild){
        rotateRL(node);
      }
      rotate(node);
      return;
    }
  }

  @Override
  public T search(int key){
    BinTreeNode<T> node = searchNode(key);
    rotate(node);
    return node.getValue();
  }
}
