class MyTree<T> extends AVLTree  
{
    /**
    * Recursive function to balance the tree
    */
    @Override
    public BinTreeNode rebalance(BinTreeNode<T> node) {
        if (node != null) {
            node.setRightChild((rebalance(node.getRightChild())));
            node.setLeftChild((rebalance(node.getLeftChild())));

            if (newBalancingFactor(node) == 3) {
                if (newBalancingFactor(node.getLeftChild()) > 0) { 
                    super.rotateRight(node);
                } else {
                    super.rotateLR(node);
                }
            } else if (newBalancingFactor(node) == -3) {
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
}
