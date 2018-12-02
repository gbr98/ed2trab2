class AVLTree<T> extends BinSearchTree<T>
{

  private BinTreeNode root;
    private int key;

    public AVLTree() {
    }

    public BinTreeNode getroot() {
        return root;
    }

    public void setroot(BinTreeNode root) {
        this.root = root;
    }

    public long getkey() {
        return key;
    }

    public void setkey(int gasto_id) {
        key = gasto_id;
    }

    /**
    * Function to insert in the tree
    */
    public void add(int key, T value, BinTreeNode<T> parent) {
        if (root == null) {
            BinTreeNode node = new BinTreeNode(key, value, null);
            setroot(node);
        } else {
            this.key = key;
            addRecursive(root, value, parent);
            newBalancingFactor(root);
            root = rebalance(root);
            newBalancingFactor(root);
        }
    }
    
    /**
     * Recursive function to insert in the tree
     */
    private BinTreeNode addRecursive(BinTreeNode node, T value, BinTreeNode<T> parent) {
        if (node == null) {
            node = new BinTreeNode(key, value, parent);
        } else if (getkey() < root.getKey()) //Valor menor que a raiz
        {
            node.setLeftChild(addRecursive(node.getLeftChild(), value, parent));
        } else {
            node.setRightChild(addRecursive(node.getRightChild(), value, parent));
        }
        return node;
    }

    /**
    * Function to calculate the height of the tree
    */
    public int height(BinTreeNode node) {
        int hl, hr;
        if (node == null) {
            return 0;
        } else {
            hl = height(node.getLeftChild());
            hr = height(node.getRightChild());
            if (hl > hr) {
                return hl + 1;
            } else {
                return hr + 1;
            }
        }
    }

     /**
     * Function to calculate the balancing factor of the tree
     */
    public int newBalancingFactor(BinTreeNode node) {
        int BF = 0;
        if (node != null) {
            BF = (height(node.getLeftChild()) - height(node.getRightChild()));
        }
        return BF;
    }

    /**
    * Recursive function to balance the tree
    */
    public BinTreeNode rebalance(BinTreeNode<T> node) {
        if (node != null) {
            node.setRightChild((rebalance(node.getRightChild())));
            node.setLeftChild((rebalance(node.getLeftChild())));

            if (newBalancingFactor(node) == 2) {
                if (newBalancingFactor(node.getLeftChild()) > 0) { 
                    super.rotateRight(node);
                } else {
                    super.rotateLR(node);
                }
            } else if (newBalancingFactor(node) == -2) {
                if (newBalancingFactor(node.getRightChild()) < 0) 
                {
                    super.rotateLeft(node);
                } else {
                    super.rotateRL(node);
                }
            }
        }
        return node;
    }

     /**
     * Function to find the min value of the tree
     */
    public BinTreeNode findMin(BinTreeNode node) {
        if (node == null) {
            return null;
        } else if (node.getLeftChild() == null) {
            return node;
        } else {
            return findMin(node.getLeftChild());
        }
    }

     /**
     * Function to find the max value of the tree
     */
    public BinTreeNode findMax(BinTreeNode node) {
        if (node == null) {
            return null;
        } else if (node.getRightChild() == null) {
            return node;
        } else {
            return findMax(node.getRightChild());
        }
    }
    
     /**
     * Function to remove an element from the tree
     */
    public BinTreeNode remove(int key, BinTreeNode<T> node) {
        BinTreeNode assistant_remove;

        if (node == null) {
            return null;
        } 
        
        // Search
        else if (key < node.getKey()) {
            node.setLeftChild(remove(key, node.getLeftChild()));
        } else if (key > node.getKey()) {
            node.setRightChild(remove(key, node.getRightChild()));
        }
        
        // 2 children
        else if ((node.getLeftChild() != null) && (node.getRightChild() != null)) {
            assistant_remove = findMin(node.getRightChild());
            node.setKey(assistant_remove.getKey());
            node.setRightChild(remove(node.getKey(), node.getRightChild()));
        } 
        // 1 child or 0 
        else {
            assistant_remove = node;
            if (node.getLeftChild() == null) {
                node = node.getRightChild();
            } else if (node.getRightChild() == null) {
                node = node.getLeftChild();
            }
            assistant_remove = null;
        }

        if (node == null) {
            return node;
        }

        newBalancingFactor(node);
        rebalance(node);

        return node;
    }
    
     /**
     * Function to search a tree value
     */

    public BinTreeNode<T> AuxSearch(int key) {
        return super.searchNode(key);
    }

}
