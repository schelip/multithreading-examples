class LinearOperations {
    static Matrix sum(Matrix m1, Matrix m2) {
        def result = new Matrix(m1.rows, m1.cols)

        for (i in 0..<m1.rows) {
            println "Processando linha " + i
            for (j in 0..<m1.cols)
                result.raw[i][j] = m1.raw[i][j] + m2.raw[i][j]
        }

        return result
    }

    static Matrix subtraction(Matrix m1, Matrix m2) {
        def result = new Matrix(m1.rows, m1.cols)

        for (i in 0..<m1.rows) {
            println "Processando linha " + i
            for (j in 0..<m1.cols)
                result.raw[i][j] = m1.raw[i][j] - m2.raw[i][j]
        }

        return result
    }

    static Matrix multiplication(Matrix m1, Matrix m2) {
        assert m1.cols == m2.rows

        def result = new Matrix(m1.cols, m2.rows)

        for (i in 0..<m1.rows) {
            println "Processando linha " + i
            for (j in 0..<m2.cols)
                for (k in 0..<m1.cols)
                    result.raw[i][j] += m1.raw[i][k] * m2.raw[k][j]
        }

        return result
    }

    static floydWarshall(Graph graph) {
        def dist = new Matrix(graph.edges.raw)

        for (k in 0..<graph.nodes)
            for (i in 0..<graph.nodes)
                for (j in 0..<graph.nodes) {
                    println "Comparando (" + i + "-" + j + ") com ("+ i + "-" + k + "-" + j + ")"
                    if (dist.raw[i][k] != Integer.MAX_VALUE &&
                            dist.raw[k][j] != Integer.MAX_VALUE &&
                            dist.raw[i][j] > dist.raw[i][k] + dist.raw[k][j]
                    ) {
                        dist.raw[i][j] = dist.raw[i][k] + dist.raw[k][j]
                    }
                }

        return dist
    }
}