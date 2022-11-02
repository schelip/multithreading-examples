class Graph {
    int nodes
    Matrix edges

    Graph(int n) {
        nodes = n
        edges = new Matrix(n)
    }

    Graph(Matrix e) {
        assert e.cols == e.rows
        nodes = e.cols
        edges = e
    }

    // Retorna grafo com um número de arestas baseado em uma densidade (probabilidade) pe, cada uma com peso aleatório
    // de no máximo maxW
    static withRandom(int n, double pe, int maxW) {
        assert 0 <= pe && pe <= 1
        assert maxW >= 0
        def m = new Matrix(n)
        Random rnd = new Random()
        for (i in 0..<n)
            for (j in 0..<n)
                if (i == j)
                    m.raw[i][j] = 0
                else
                    m.raw[i][j] = Math.random() > pe ?
                        rnd.nextInt(maxW) :
                        Integer.MAX_VALUE
        return new Graph(m)
    }
}
