class RnBTreeNode<T> extends BinTreeNode<T> {

  private int color; //0 - black | 1 - red

  public RnBTreeNode(int key, T value, BinTreeNode<T> parent){
    super(key, value, parent);
    this.color = 1;
  }

  public void recolorize(){
    this.color = (this.color == 0 ? 1 : 0);
  }

  public int getColor(){
    return this.color;
  }
}
