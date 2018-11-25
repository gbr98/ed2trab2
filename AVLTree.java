class AVLTree<T> extends BinSearchTree<T> 
{
   private BinTreeNode root;
   private BinTreeNode assistant_insert;
   private BinTreeNode assistant_remove;
   private long gasto_id;
   
  public AVLTree()
  {
  }
  //
  public BinTreeNode getroot()
  {
      get root;
  }
  
  public void setroot( BinTreeNode root)
  {
      this.root = root;
  }
  
    public BinTreeNode get_assistant_insert()
  {
     get assistant_insert;
  }
  
  public void set_assistant_insert( BinTreeNode assistant_insert)
  {
     this.assistant_insert = assistant_insert;
  }
  
    public BinTreeNode get_assistant_remove()
  {
    get assistant_remove;
  }
  
  public void set_assistant_remove( BinTreeNode assistant_remove)
  {
     this.root = assistant_remove;
  }
  
     public long get_gasto_id()
  {
    get gasto_id;
  }
  
  public void set_gasto_id( long gasto_id)
  {
    this.gasto_id = gasto_id;
  }
  
  //function to insert
  
  public void insert(long gasto_id) 
  {
        if (root == null)
        {
            BinTreeNode node = new BinTreeNode(gasto_id);
            setroot(node);
        } 
        else 
        {
            assistant_insert = gasto_id;
            insertRecursive(root);
            newBalancingFactor(root);
            root = rebalance(root);
            newBalancingFactor(root);
        }
    }
///CANSEI VOLLTAR AMANHAAQIIIIIIIII
    private BinTreeNode insertRecursive(BinTreeNode node) 
    {
        if (node == null)
        {
            node = BinTreeNode(assistant_insert);
        } 
        else 
        {
            if (assistant_insert.get_gasto_id() < node.()) //VOLTAR AQUI
            {
                super. setLeftChild(insertRecursive(super.getLeftChild()));
            } 
            else 
            {
                super.setRightChild(insertRecursive(super.getRightChild()));
            }
        }
        return node;
    }
    
    
    /* public void newBalancingFactor(BinSearchNode node) 
     {
        if (node != null) 
        {
            node.setIndice(altura(no.getFilhoEsq()) - altura(no.getFilhoDir()));
            arrumaFatorBalanceamento(no.getFilhoDir());
            arrumaFatorBalanceamento(no.getFilhoEsq());
        }
    }
   public NoAVL rebalance(NoAVL no) {
        if (no != null) {
            no.setFilhoDir(rebalanceia(no.getFilhoDir()));
            no.setFilhoEsq(rebalanceia(no.getFilhoEsq()));
            no.setIndice(altura(no.getFilhoEsq()) - altura(no.getFilhoDir()));
            if (no.getIndice() == 2) {
                if (no.getFilhoEsq().getIndice() > 0) {
                    no = rotacaoDireitaDireita(no);
                } else {
                    no = rotacaoEsquerdaDireita(no);
                }
            } else {
                if (no.getIndice() == -2) {
                    if (no.getFilhoDir().getIndice() < 0) {
                        no = rotacaoEsquerdaEsquerda(no);
                    } else {
                        no = rotacaoDireitaEsquerda(no);
                    }
                }
            }
        }
        return no;
    }
  */
  
  

}
