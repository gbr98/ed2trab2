class RnBTree<T> extends BinSearchTree<T> {

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

    //rotate and recolorize [TODO]

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

  public void recolorize(BinTreeNode<T> node){
    node.setKey((-1)*node.getKey());
  }
}
