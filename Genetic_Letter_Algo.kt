import kotlin.random.Random

const val TARGET = "HELLO WORLD"
const val POP_SIZE = 500
const val MUTATION_RATE = 0.02

data class Individual(val genes: String, val fitness: Int)

fun randomString(length: Int) = (1..length)
    .map { ('A'..'Z') + ' ' }
    .map { it.random() }
    .joinToString("")

fun fitness(genes: String) = genes.zip(TARGET).count { it.first == it.second }

fun mutate(genes: String): String {
    return genes.map { if (Random.nextDouble() < MUTATION_RATE) randomString(1)[0] else it }
        .joinToString("")
}

fun crossover(parent1: String, parent2: String): String {
    val point = Random.nextInt(parent1.length)
    return parent1.substring(0, point) + parent2.substring(point)
}

fun evolve() {
    var population = List(POP_SIZE) { Individual(randomString(TARGET.length), 0) }
    var generation = 0

    while (true) {
        population = population.map { it.copy(fitness = fitness(it.genes)) }
        population = population.sortedByDescending { it.fitness }

        if (population.first().fitness == TARGET.length) {
            println("Generation $generation: ${population.first().genes}")
            break
        }

        val nextGen = mutableListOf<Individual>()
        for (i in 0 until POP_SIZE / 2) {
            val parent1 = population[i].genes
            val parent2 = population[i + 1].genes
            val child1 = mutate(crossover(parent1, parent2))
            val child2 = mutate(crossover(parent2, parent1))
            nextGen.add(Individual(child1, 0))
            nextGen.add(Individual(child2, 0))
        }

        population = nextGen
        generation++

        if (generation % 10 == 0) {
            println("Generation $generation: ${population.first().genes}")
        }
    }
}

fun main() {
    evolve()
}
