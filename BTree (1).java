package arvore;

class BTree {


    private Node root; //raiz
    private int order; //ordem
    private int elements; //quantidade de elementos

    //Construtor

    public BTree(int n) {
        this.root = new Node(n);
        this.order = n;
        elements = 0;
    }

    //Getters e Setters dos atributos
    public int getElements() {
        return elements;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public Node getRoot() {
        return root;
    }

    //Metodo de Inserção
    //parametros: k - chave a ser inserida
    public void insert(int k) {

        if (search(root, k) == null) {
            if (root.getN() == 0) {
                root.getKey().set(0, k);
                root.setN(root.getN() + 1);
            } else {
                Node r = root;

                if (r.getN() == order - 1) {
                    Node s = new Node(order);
                    root = s;
                    s.setLeaf(false);
                    s.setN(0);
                    s.getChildren().set(0, r);
                    split(s, 0, r);
                    insertNotFull(s, k);

                } else {
                    insertNotFull(r, k);
                }
            }
            elements++;
        }
    }


    public void split(Node x, int i, Node y) {
        int t = (int) Math.floor((order - 1) / 2);

        Node z = new Node(order);
        z.setLeaf(y.isLeaf());
        z.setN(t);


        for (int j = 0; j < t; j++) {
            if ((order - 1) % 2 == 0) {
                z.getKey().set(j, y.getKey().get(j + t));
            } else {
                z.getKey().set(j, y.getKey().get(j + t + 1));
            }
            y.setN(y.getN() - 1);
        }


        if (!y.isLeaf()) {
            for (int j = 0; j < t + 1; j++) {
                if ((order - 1) % 2 == 0) {
                    z.getChildren().set(j, y.getChildren().get(j + t));
                } else {
                    z.getChildren().set(j, y.getChildren().get(j + t + 1));
                }

            }
        }

        y.setN(t);


        for (int j = x.getN(); j > i; j--) {
            x.getChildren().set(j + 1, x.getChildren().get(j));
        }

        x.getChildren().set(i + 1, z);


        for (int j = x.getN(); j > i; j--) {
            x.getKey().set(j, x.getKey().get(j - 1));
        }


        if ((order - 1) % 2 == 0) {
            x.getKey().set(i, y.getKey().get(t - 1));
            y.setN(y.getN() - 1);

        } else {
            x.getKey().set(i, y.getKey().get(t));
        }


        x.setN(x.getN() + 1);

    }


    public void insertNotFull(Node x, int k) {
        int i = x.getN() - 1;

        if (x.isLeaf()) {

            while (i >= 0 && k < x.getKey().get(i)) {
                x.getKey().set(i + 1, x.getKey().get(i));
                i--;
            }
            i++;
            x.getKey().set(i, k);
            x.setN(x.getN() + 1);

        } else {
            while ((i >= 0 && k < x.getKey().get(i))) {
                i--;
            }
            i++;

            if ((x.getChildren().get(i)).getN() == order - 1) {
                split(x, i, x.getChildren().get(i));
                if (k > x.getKey().get(i)) {
                    i++;
                }
            }

            insertNotFull(x.getChildren().get(i), k);
        }

    }


    public Node search(Node X, int k) {
        int i = 1;

        while ((i <= X.getN()) && (k > X.getKey().get(i - 1))) {
            i++;
        }

        if ((i <= X.getN()) && (k == X.getKey().get(i - 1))) {
            return X;
        }

        if (X.isLeaf()) {
            return null;
        } else {
            return (search(X.getChildren().get(i - 1), k));
        }
    }


    public void remove(int k) {
        if (search(this.root, k) != null) {
            Node N = search(this.root, k);
            int i = 1;

            while (N.getKey().get(i - 1) < k) {
                i++;
            }

            if (N.isLeaf()) {
                for (int j = i + 1; j <= N.getN(); j++) {
                    N.getKey().set(j - 2, N.getKey().get(j - 1));
                }
                N.setN(N.getN() - 1);
                if (N != this.root) {
                    balanceLeaf(N);
                }
            } else {
                Node S = ancestor(this.root, k);
                int y = S.getKey().get(S.getN() - 1);
                S.setN(S.getN() - 1);
                N.getKey().set(i - 1, y);
                balanceLeaf(S);
            }
            elements--;
        }
    }

    private void balanceLeaf(Node F) {
        if (F.getN() < Math.floor((order - 1) / 2)) {
            Node P = getFather(root, F);//P é o pai de F
            int j = 1;

            while (P.getChildren().get(j - 1) != F) {
                j++;
            }

            if (j == 1 || (P.getChildren().get(j - 2)).getN() == Math.floor((order - 1) / 2)) {
                if (j == P.getN() + 1 || (P.getChildren().get(j).getN() == Math.floor((order - 1) / 2))) {
                    Diminui_Altura(F);
                } else {
                    balanceRightLeft(P, j - 1, P.getChildren().get(j), F);
                }
            } else {
                balanceLeftRight(P, j - 2, P.getChildren().get(j - 2), F);
            }
        }
    }


    private void Diminui_Altura(Node X) {
        int j;
        Node P = new Node(order);

        if (X == this.root) {
            if (X.getN() == 0) {
                this.root = X.getChildren().get(0);
                X.getChildren().set(0, null);
            }
        } else {
            int t = (int) Math.floor((order - 1) / 2);
            if (X.getN() < t) {
                P = getFather(root, X);
                j = 1;

                while (P.getChildren().get(j - 1) != X) {
                    j++;
                }

                if (j > 1) {
                    merge(getFather(root, X), j - 1);
                } else {
                    merge(getFather(root, X), j);
                }
                Diminui_Altura(getFather(root, X));
            }
        }
    }

    private void balanceLeftRight(Node P, int e, Node Esq, Node Dir) {
        for (int i = 0; i < Dir.getN(); i++) {
            Dir.getKey().set(i + 1, Dir.getKey().get(i));
        }

        if (!Dir.isLeaf()) {
            for (int i = 0; i > Dir.getN(); i++) {
                Dir.getChildren().set(i + 1, Dir.getChildren().get(i));
            }
        }
        Dir.setN(Dir.getN() + 1);
        Dir.getKey().set(0, P.getKey().get(e));
        P.getKey().set(e, Esq.getKey().get(Esq.getN() - 1));
        Dir.getChildren().set(0, Esq.getChildren().get(Esq.getN()));
        Esq.setN(Esq.getN() - 1);

    }

    private void balanceRightLeft(Node P, int e, Node Dir, Node Esq) {

        Esq.setN(Esq.getN() + 1);
        Esq.getKey().set(Esq.getN() - 1, P.getKey().get(e));
        P.getKey().set(e, Dir.getKey().get(0));
        Esq.getChildren().set(Esq.getN(), Dir.getChildren().get(0));

        for (int j = 1; j < Dir.getN(); j++) {
            Dir.getKey().set(j - 1, Dir.getKey().get(j));
        }

        if (!Dir.isLeaf()) {
            for (int i = 1; i < Dir.getN()+1 ; i++) {
                Dir.getChildren().set(i - 1, Dir.getChildren().get(i));
            }
        }

        Dir.setN(Dir.getN() - 1);

    }

    private void merge(Node X, int i) {
        Node Y = X.getChildren().get(i - 1);
        Node Z = X.getChildren().get(i);

        int k = Y.getN();
        Y.getKey().set(k, X.getKey().get(i - 1));

        for (int j = 1; j <= Z.getN(); j++) {
            Y.getKey().set(j + k, Z.getKey().get(j - 1));
        }

        if (!Z.isLeaf()) {
            for (int j = 1; j <= Z.getN(); j++) {
                Y.getChildren().set(j + k, Z.getChildren().get(j - 1));
            }
        }

        Y.setN(Y.getN() + Z.getN() + 1);

        X.getChildren().set(i, null);

        for (int j = i; j <= X.getN() - 1; j++) {//ainda nao
            X.getKey().set(j - 1, X.getKey().get(j));
            X.getChildren().set(j, X.getChildren().get(j + 1));
        }

        X.setN(X.getN() - 1);
    }

    private Node ancestor(Node N, int k) {
        int i = 1;
        while (i <= N.getN() && N.getKey().get(i - 1) < k) {
            i++;
        }
        if (N.isLeaf()) {
            return N;
        } else {
            return ancestor(N.getChildren().get(i - 1), k);
        }
    }

    private Node getFather(Node T, Node N) {
        if (this.root == N) {
            return null;
        }
        for (int j = 0; j <= T.getN(); j++) {
            if (T.getChildren().get(j) == N) {
                return T;
            }
            if (!T.getChildren().get(j).isLeaf()) {
                Node X = getFather(T.getChildren().get(j), N);
                if (X != null) {
                    return X;
                }
            }
        }
        return null;
    }

    public void clear(Node N, int ordem) {

        for (int i = 0; i < N.getN() + 1; i++) {
            if (!N.isLeaf()) {
                clear(N.getChildren().get(i), ordem);
            }
            N.getChildren().set(i, null);
            N.setN(0);
        }

        if (N == this.root) {
            this.root = new Node(ordem);
        }
        elements = 0;
    }


}
