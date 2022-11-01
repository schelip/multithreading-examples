class ParallelOperations {
    public static int MAX_THREADS = 8

    static distribute(int tasks) {
        def dist = []
        int each = (tasks / MAX_THREADS) as Integer
        def over = tasks % MAX_THREADS
        for (i in 1..<MAX_THREADS+1) {
            dist << each + (over > i-1 ? 1 : 0)
        }
        return dist
    }

    static Matrix sum(Matrix m1, Matrix m2) {
        def result = new Matrix(m1.rows, m1.cols)

        List<Thread> threads = []
        int[] tasks = distribute(m1.rows)
        def rowsAssigned = 0

        for (t in 0..<MAX_THREADS) {
            int rows = tasks[t]
            if (rows > 0) {
                def start = rowsAssigned
                def end = rowsAssigned + rows
                def thread = Thread.start {
                    for (i in start..<end) {
                        println "Processando linha " + i
                        for (j in 0..<m1.cols)
                            result.raw[i][j] = m1.raw[i][j] + m2.raw[i][j]
                    }
                }
                threads << thread
            }
            rowsAssigned += rows
        }
        threads*.join()

        return result
    }

    static Matrix subtraction(Matrix m1, Matrix m2) {
        def result = new Matrix(m1.rows, m1.cols)

        List<Thread> threads = []
        int[] tasks = distribute(m1.rows)
        def rowsAssigned = 0

        for (t in 0..<MAX_THREADS) {
            int rows = tasks[t]
            if (rows > 0) {
                def start = rowsAssigned
                def end = rowsAssigned + rows
                def thread = Thread.start {
                    for (i in start..<end) {
                        println "Processando linha " + i
                        for (j in 0..<m1.cols)
                            result.raw[i][j] = m1.raw[i][j] - m2.raw[i][j]
                    }
                }
                threads << thread
            }
            rowsAssigned += rows
        }
        threads*.join()

        return result
    }

    static Matrix multiplication(Matrix m1, Matrix m2) {
        assert m1.cols == m2.rows

        def result = new Matrix(m1.cols, m2.rows)

        List<Thread> threads = []
        int[] tasks = distribute(m1.rows)
        def rowsAssigned = 0

        for (t in 0..<MAX_THREADS) {
            int rows = tasks[t]
            if (rows > 0) {
                def start = rowsAssigned
                def end = rowsAssigned + rows
                def thread = Thread.start {
                    for (i in start..<end) {
                        println "Processando linha " + i
                        for (j in 0..<m2.cols)
                            for (k in 0..<m1.cols)
                                result.raw[i][j] += m1.raw[i][k] * m2.raw[k][j]
                    }
                }
                threads << thread
            }
            rowsAssigned += rows
        }
        threads*.join()

        return result
    }

    static floydWarshall(Graph graph) {
        def dist = new Matrix(graph.edges.raw)

        List<Thread> threads = []
        int[] tasks = distribute(graph.nodes)
        def rowsAssigned = 0

        for (t in 0..<MAX_THREADS) {
            int rows = tasks[t]
            if (rows > 0) {
                def start = rowsAssigned
                def end = rowsAssigned + rows
                def thread = Thread.start {
                    for (k in start..<end) {
                        for (i in 0..<graph.nodes) {
                            for (j in start..<end) {
                                println "Comparando (" + i + "-" + j + ") com (" + i + "-" + k + "-" + j + ")"
                                if (dist.raw[i][k] != Integer.MAX_VALUE &&
                                        dist.raw[k][j] != Integer.MAX_VALUE &&
                                        dist.raw[i][j] > dist.raw[i][k] + dist.raw[k][j]
                                ) {
                                    dist.raw[i][j] = dist.raw[i][k] + dist.raw[k][j]
                                }
                            }
                        }
                    }
                }
                threads << thread
            }
            rowsAssigned += rows
        }
        threads*.join()
        return dist
    }
}
