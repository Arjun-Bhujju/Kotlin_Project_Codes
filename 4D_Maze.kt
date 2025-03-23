import kotlin.random.Random

data class Cell(val x: Int, val y: Int, val z: Int, val w: Int)

val directions = listOf(
    Cell(1, 0, 0, 0), Cell(-1, 0, 0, 0), // X-axis
    Cell(0, 1, 0, 0), Cell(0, -1, 0, 0), // Y-axis
    Cell(0, 0, 1, 0), Cell(0, 0, -1, 0), // Z-axis
    Cell(0, 0, 0, 1), Cell(0, 0, 0, -1)  // W-axis
)

class Maze4D(val size: Int) {
    private val maze = Array(size) { Array(size) { Array(size) { BooleanArray(size) } } }

    fun generate(x: Int = 0, y: Int = 0, z: Int = 0, w: Int = 0) {
        maze[x][y][z][w] = true
        directions.shuffled().forEach { (dx, dy, dz, dw) ->
            val nx = x + dx
            val ny = y + dy
            val nz = z + dz
            val nw = w + dw
            if (nx in 0 until size && ny in 0 until size && nz in 0 until size && nw in 0 until size && !maze[nx][ny][nz][nw]) {
                generate(nx, ny, nz, nw)
            }
        }
    }

    fun solve(start: Cell, end: Cell): Boolean {
        val visited = mutableSetOf<Cell>()
        val stack = ArrayDeque<Cell>()
        stack.push(start)

        while (stack.isNotEmpty()) {
            val current = stack.pop()
            if (current == end) return true
            if (current in visited) continue
            visited.add(current)

            directions.forEach { (dx, dy, dz, dw) ->
                val next = Cell(current.x + dx, current.y + dy, current.z + dz, current.w + dw)
                if (next.x in 0 until size && next.y in 0 until size && next.z in 0 until size && next.w in 0 until size && maze[next.x][next.y][next.z][next.w]) {
                    stack.push(next)
                }
            }
        }
        return false
    }

    fun display() {
        for (w in 0 until size) {
            for (z in 0 until size) {
                println("4D Layer: W=$w, Z=$z")
                for (y in 0 until size) {
                    for (x in 0 until size) {
                        print(if (maze[x][y][z][w]) "⬜" else "⬛")
                    }
                    println()
                }
                println()
            }
        }
    }
}

fun main() {
    val maze = Maze4D(4)
    maze.generate()
    maze.display()
    println("Maze solvable? " + maze.solve(Cell(0, 0, 0, 0), Cell(3, 3, 3, 3)))
}
