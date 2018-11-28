interface SearchTree<T> {
  public void add(int key, T x);
  public T search(int key);
  public T remove(int key);
  public long getComp();
  public long getCopy();
  public void resetComp();
  public void resetCopy();
}
