package org.example

import java.util.Arrays
import java.util.PriorityQueue

fun testDijkstra() {
    val v = 6 // Number of vertices
    val adj = Array(v) { mutableListOf<Edge>() }

    // Graph Representation (Weighted Edges)
    adj[0].add(Edge(1, 4))
    adj[0].add(Edge(2, 1))
    adj[1].add(Edge(3, 1))
    adj[2].add(Edge(1, 2))
    adj[2].add(Edge(3, 5))
    adj[3].add(Edge(4, 3))
    adj[4].add(Edge(5, 2))
    adj[3].add(Edge(5, 1))

    val source = 0
    val expected = intArrayOf(0, 3, 1, 4, 7, 5) // Expected shortest distances
    val result = dijkstraShortestPath(source, v, adj)
    println(result.contentToString())

    // LeetCode-style validation
    check(result.contentEquals(expected)) { "Test failed! Expected: ${expected.joinToString()}, but got: ${result.joinToString()}" }

    // Print success message if no assertion fails
    println("Test passed!")}

// Run the test
fun main() {
    testDijkstra() // Will throw an AssertionError if the test fails
}

data class Edge(val to: Int, val cost: Int)

fun dijkstraShortestPath(s: Int, v: Int, adj: Array<MutableList<Edge>>): IntArray {

    val distance = IntArray(v) { Int.MAX_VALUE }
    val pq = PriorityQueue<Pair<Int, Int>>(Comparator.comparingInt {
        it.second
    })
    val visited = BooleanArray(v)

    distance[s] = 0
    pq.add(Pair(s, 0))

    while (pq.isNotEmpty()) {
        val node = pq.poll()
        visited[node.first] = true
        for (edge in adj[node.first]) {
            if (!visited[edge.to]) {
                val newDistance = distance[node.first] + edge.cost
                if (newDistance < distance[edge.to]) {
                    distance[edge.to] = newDistance
                    pq.add(Pair(edge.to, newDistance))
                }
            }
        }
    }

    return distance

}