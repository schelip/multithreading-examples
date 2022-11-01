static void main(String[] args) {
  def scan = new Scanner(System.in)

  def compare = {linear, parallel ->
    def startTime = System.currentTimeMillis()
    def resultLinear = linear()
    def endTime = System.currentTimeMillis()
    println "\nPROCESSAMENTO LINEAR FINALIZADO"
    if (resultLinear != null && !(resultLinear instanceof Matrix && resultLinear.cols > 10))
      println "Resultado linear: " + resultLinear.toString() + "\n"
    def linearTime = endTime - startTime

    startTime = System.currentTimeMillis()
    def resultParallel = parallel()
    endTime = System.currentTimeMillis()
    println "\nPROCESSAMENTO PARALELO FINALIZADO"
    if (resultParallel != null && !(resultParallel instanceof Matrix && resultParallel.cols > 10))
      println "Resultado paralelo: " + resultParallel.toString() + "\n"
    def parallelTime = endTime - startTime

    def diff = linearTime - parallelTime

    println "Resultados iguais = " + (resultLinear == resultParallel)
    println "Tempo execução linear = " + linearTime + "ms"
    println "Tempo execução paralela = " + parallelTime + "ms"
    println "Diferença de tempo = " + diff + "ms (" + ((diff / linearTime) * 100) + "% de melhora)"
  }

  def matrixOperations = {
    def m1, m2
    do {
      def tam = -1
      do {
        println "Qual tamanho?"
        tam = scan.nextInt()
      } while (tam < 1)

      var max = -1;
      do {
        println "Qual o valor máximo de cada elemento?"
        max = scan.nextInt()
      } while (max < 1)

      m1 = Matrix.fillRandom(tam, max)
      m2 = Matrix.fillRandom(tam, max)

      if (tam <= 10) {
        print "M1:\n" + m1.toString() + "\n"
        print "M2:\n" + m2.toString() + "\n"
      }

      var threads = -1
      do {
        println "Qual o máximo de threads?"
        threads = scan.nextInt()
      } while (max < 1)
      ParallelOperations.MAX_THREADS = threads

      def op = -1
      do {
        println "Qual operação?\n" +
                "(1) Soma\n" +
                "(2) Subtracao\n" +
                "(3) Multiplicacao\n"

        op = scan.nextInt()
        switch (op) {
          case 1:
            compare(
                    () -> LinearOperations.sum(m1, m2),
                    () -> ParallelOperations.sum(m1, m2),
            )
            break
          case 2:
            compare(

                    () -> LinearOperations.subtraction(m1, m2),
                    () -> ParallelOperations.subtraction(m1, m2),
            )
            break
          case 3:
            compare(
                    () -> LinearOperations.multiplication(m1, m2),
                    () -> ParallelOperations.multiplication(m1, m2),
            )
            break
        }
      } while (op < 0 || op > 3)

      println "Deseja executar outra operação com matrizes? (S)"
    } while (scan.next().toUpperCase() == 'S')
  }

  def graphOperations = {
    do {
      Graph graph

      def n = -1
      do {
        println "Quantos nós?"
        n = scan.nextInt()
      } while (n < 2)

      def e = -1d
      do {
        println "Densidade de arestas? (0..1)"
        e = scan.nextDouble()
      } while (e < 0 || e > 1)

      def maxW = -1
      do {
        println "Peso máximo de cada aresta? ( >= 0 )"
        maxW = scan.nextInt()
      } while (maxW < 0)

      var threads = -1
      do {
        println "Qual o máximo de threads?"
        threads = scan.nextInt()
      } while (threads < 1)
      ParallelOperations.MAX_THREADS = threads

      graph = Graph.withRandom(n, e, maxW)
      if (n <= 10) {
        println graph.edges
      }

      assert graph != null
      compare(
              () -> LinearOperations.floydWarshall(graph),
              () -> ParallelOperations.floydWarshall(graph),
      )

      println "Deseja executar outra operação com grafos? (S)"
    } while (scan.next().toUpperCase() == 'S')
  }

  do {
    def opt
    do {
      println "Deseja realizar uma operação com (1) matrizes ou com (2) grafos?"
      opt = scan.nextInt()
    } while (opt != 1 && opt != 2)

    switch (opt) {
      case 1:
        matrixOperations()
        break
      case 2:
        graphOperations()
        break
    }

    println "Deseja executar outra operação? (S)"
  } while (scan.next().toUpperCase() == 'S')
}