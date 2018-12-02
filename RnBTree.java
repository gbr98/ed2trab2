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
      addCopy();
      addComp();
      side = (RnBTreeNode<T>)(key < chosen.getKey() ? chosen.getLeftChild() : chosen.getRightChild());
    } while(side != null);

    newNode = new RnBTreeNode<>(key, x, chosen);

    addComp();
    if(key < chosen.getKey()){
      chosen.setLeftChild(newNode);
    } else {
      chosen.setRightChild(newNode);
    }
    addCopy();

    //rotate and recolorize [OK?]
    rotate(newNode);
    if(((RnBTreeNode<T>)this.getRoot()).getColor() == 1){ //root is always a black node
      ((RnBTreeNode<T>)this.getRoot()).recolorize();
    }
    //...
  }

  @Override
  public T remove(int key){
    RnBTreeNode<T> node = (RnBTreeNode<T>) searchNode(key);
    if(node != null){
      RnBTreeNode<T> largest = null;
      if(node.getLeftChild() == null){
        largest = node;
      } else {
        largest = (RnBTreeNode<T>) findLargestInSubtree(node.getLeftChild());
      }

      RnBTreeNode<T> largestCopy = new RnBTreeNode<>(largest.getKey(), largest.getValue(), largest.getParent());
      largestCopy.setColor(largest.getColor());
      largestCopy.setRightChild(largest.getRightChild());
      largestCopy.setLeftChild(largest.getLeftChild());

      largestCopy.setParent(node.getParent());
      if(node.getParent() != null){
        if(node.getParent().getRightChild() == node){
          node.getParent().setRightChild(largestCopy);
        } else {
          node.getParent().setLeftChild(largestCopy);
        }
      } else {
        setRoot(largestCopy);
      }
      if(node.getLeftChild() != null)
        node.getLeftChild().setParent(largestCopy);
      if(node.getRightChild() != null)
        node.getRightChild().setParent(largestCopy);
      largestCopy.setLeftChild(node.getLeftChild());
      largestCopy.setRightChild(node.getRightChild());
      addCopy(8);

      largest.setColor(-1); //-1 == double black
      rotateDoubleBlack(largest);

      return node.getValue();
    } else {
      return null;
    }
  }

  /**
  * Recursive function to balance the tree
  */
  private void rotate(RnBTreeNode<T> node){
    RnBTreeNode<T> parent = (RnBTreeNode<T>) node.getParent();
    if(parent == null) return;
    RnBTreeNode<T> gparent = (RnBTreeNode<T>) parent.getParent();
    if(gparent == null) return;

    boolean isNodeRightChild = (parent.getRightChild() == node);
    boolean isParentRightChild = (gparent.getRightChild() == parent);

    if(node.getColor() != 1 || parent.getColor() != 1) return; //nothing to do
    addComp(2);

    //second case (first) : pull down blackness
    RnBTreeNode<T> gparentlc = (RnBTreeNode<T>)gparent.getLeftChild();
    RnBTreeNode<T> gparentrc = (RnBTreeNode<T>)gparent.getRightChild();
    int lccolor = (gparentlc == null ? 0 : gparentlc.getColor());
    int rccolor = (gparentrc == null ? 0 : gparentrc.getColor());
    addComp(2);
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

  /**
  * Recursive function to remove double-blackness
  */
  private void rotateDoubleBlack(RnBTreeNode<T> largest){
    if(largest.getParent() == null){ //root
      largest.setColor(0);
      return;
    }
    //verify that rightChild != null
    RnBTreeNode<T> leftChild = (RnBTreeNode<T>)largest.getLeftChild();
    int leftChildColorL = (leftChild == null ? 0 : leftChild.getColor());
    if(largest.getColor() == 1 || leftChildColorL == 1){
      //simple case
      if(largest.getParent().getRightChild() == largest){
        largest.getParent().setRightChild(leftChild);
      } else {
        largest.getParent().setLeftChild(leftChild);
      }
      if(leftChild != null){
        leftChild.setParent(largest.getParent());
        leftChild.setColor(0);
      }
    } else {
      //uhg...
      //both largest and leftChild are black => double black
      //in this case, leftChild must be NULL (black height)

      RnBTreeNode<T> sibling = null;
      if(largest.getParent().getRightChild() == largest){
        sibling = (RnBTreeNode<T>)largest.getParent().getLeftChild();
      } else {
        sibling = (RnBTreeNode<T>)largest.getParent().getRightChild();
      }
      int siblingColor = (sibling == null ? 0 : sibling.getColor());
      if(siblingColor == 0){
        //CASE 1: sibling is black and one of its children is red
        if(sibling != null){ // if sibling is NULL, none of its "children" are red
          int rightChildColor = (sibling.getRightChild() == null ? 0 : ((RnBTreeNode<T>)sibling.getRightChild()).getColor());
          int leftChildColor = (sibling.getLeftChild() == null ? 0 : ((RnBTreeNode<T>)sibling.getLeftChild()).getColor());
          if(sibling.getParent().getRightChild() == sibling){
            //sibling is right child
            if(rightChildColor == 1){
              //sibling's rightChild is red
              rotateLeft(sibling.getRightChild()); //double rotate Left
              removeDoubleBlack(largest);
              return;
            } else if(leftChildColor == 1){
              //sibling's leftChild is red
              rotateRL(sibling.getLeftChild()); //rotate Right-Left
              removeDoubleBlack(largest);
              return;
            }
          } else {
            //sibling is left child
            if(rightChildColor == 1){
              //sibling's rightChild is red
              rotateLR(sibling.getRightChild()); //rotate Left-Right
              removeDoubleBlack(largest);
              return;
            } else if(leftChildColor == 1){
              //sibling's leftChild is red
              rotateRight(sibling.getLeftChild()); //double rotate Right
              removeDoubleBlack(largest);
              return;
            }
          }
        }

        //CASE 2: both sibling's children are black
        if(sibling != null)
          sibling.setColor(1); //red
        if(((RnBTreeNode<T>)largest.getParent()).getColor() == 0){
          rotateDoubleBlack((RnBTreeNode<T>)largest.getParent());
          removeDoubleBlack(largest);
          return;
        } else {
          ((RnBTreeNode<T>)largest.getParent()).setColor(0);
          removeDoubleBlack(largest);
          return;
        }
      } else {
        //CASE 3: sibling is red (can't be NULL)
        sibling.recolorize();
        ((RnBTreeNode<T>)sibling.getParent()).recolorize();
        if(sibling.getParent().getRightChild() == sibling){
          simpleRotateLeft(sibling);
        } else {
          simpleRotateRight(sibling);
        }
        rotateDoubleBlack(largest);
        return;
      }
    }
  }

  private void removeDoubleBlack(RnBTreeNode<T> db){
    if(db.getColor() == -1){
      if(db.getParent().getRightChild() == db){
        db.getParent().setRightChild(null);
      } else {
        db.getParent().setLeftChild(null);
      }
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
