class Matrix {
    int rows
    int cols
    int[][] raw

    Matrix(int tam) {
        this(tam, tam)
    }

    Matrix(int rows, int cols) {
        this.rows = rows
        this.cols = cols
        this.raw = new int[rows][cols]
    }

    Matrix(int[][] content) {
        assert content.each {it.length == content[0].length } // Matriz deve ser retangular
        this.raw = content
        this.rows = content.length
        this.cols = content[0].length
    }

    static fillRandom(int tam, int max) {
        return fillRandom(tam, tam, max)
    }

    static fillRandom(int rows, int cols, int max) {
        def m = new Matrix(rows, cols)
        Random rnd = new Random()
        for (i in 0..<rows) {
            for (j in 0..<cols) {
                m.raw[i][j] = rnd.nextInt(max)
            }
        }
        return m
    }

    @Override
    String toString() {
        StringBuffer bfr = new StringBuffer("\n")
        for (row in this.raw) {
            bfr.append("|")
            for (col in row) {
                bfr.append(" ${col} |")
            }
            bfr.append("\n")
        }
        return bfr.toString()
    }

    @Override
    boolean equals(Object obj) {
        assert obj instanceof Matrix
        assert this.rows == obj.rows && this.cols == obj.cols
        for (i in 0..<this.rows)
            for (j in 0..<this.cols)
                if (this.raw[i][j] != obj.raw[i][j]) return false
        return true
    }
}
