class BinTreeNode<T> {
  private int key;
  private T value;
  private BinTreeNode<T> parent;
  private BinTreeNode<T> rightChild;
  private BinTreeNode<T> leftChild;

  public BinTreeNode(int key, T value, BinTreeNode<T> parent){
    this.key = key;
    this.value = value;
    this.parent = parent;
    this.rightChild = null;
    this.leftChild = null;
  }

  public int getKey(){
    return this.key;
  }

  public T getValue(){
    return this.value;
  }

  public void setRightChild(BinTreeNode<T> rightChild){
    this.rightChild = rightChild;
  }

  public void setLeftChild(BinTreeNode<T> leftChild){
    this.leftChild = leftChild;
  }

  public void setParent(BinTreeNode<T> parent){
    this.parent = parent;
  }

  public void setKey(int key){
    this.key = key;
  }

  public void setValue(T value){
    this.value = value;
  }

  public BinTreeNode<T> getRightChild(){
    return this.rightChild;
  }

  public BinTreeNode<T> getLeftChild(){
    return this.leftChild;
  }

  public BinTreeNode<T> getParent(){
    return this.parent;
  }
}
