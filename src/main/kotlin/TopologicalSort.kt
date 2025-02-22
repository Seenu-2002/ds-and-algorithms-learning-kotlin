package org.example

import java.util.LinkedList

fun main() {
    fun testCase(v: Int, adj: Array<IntArray>) {
        val sortedOrder = topologicalSortBfs(v, adj)
        println("Topological Sort: $sortedOrder")
        println("Verification: ${verifyTopologicalSort(v, adj, sortedOrder)}\n")
    }

    // Example 1
    val adj1 = arrayOf(
        intArrayOf(1, 2), // 0 → 1, 2
        intArrayOf(3),    // 1 → 3
        intArrayOf(3),    // 2 → 3
        intArrayOf()      // 3 → []
    )
    testCase(4, adj1)
    // Expected output: Valid topological order and verification should return true

    // Example 2
    val adj2 = arrayOf(
        intArrayOf(1),    // 0 → 1
        intArrayOf(2),    // 1 → 2
        intArrayOf(3),    // 2 → 3
        intArrayOf(4),    // 3 → 4
        intArrayOf()      // 4 → []
    )
    testCase(5, adj2)

    // Example 3: Complex DAG
    val adj3 = arrayOf(
        intArrayOf(1, 2), // 0 → 1, 2
        intArrayOf(3, 4), // 1 → 3, 4
        intArrayOf(4),    // 2 → 4
        intArrayOf(5),    // 3 → 5
        intArrayOf(5),    // 4 → 5
        intArrayOf()      // 5 → []
    )
    testCase(6, adj3)
}



fun topologicalSort(v: Int, adj: Array<IntArray>): List<Int> {
    val visited = BooleanArray(v)
    val stack = LinkedList<Int>()

    for (i in 0 until v) {
        if (!visited[i]) {
            dfs(i, stack, visited, adj)
        }
    }

    return stack
}

fun dfs(i: Int, stack: LinkedList<Int>, visited: BooleanArray, adj: Array<IntArray>) {
    visited[i] = true
    for (child in adj[i]) {
        if (!visited[child]) {
            dfs(child, stack, visited, adj)
        }
    }
    stack.push(i)
}


fun verifyTopologicalSort(v: Int, adj: Array<IntArray>, order: List<Int>): Boolean {
    if (order.size != v) return false // Must contain all vertices

    // Map each node to its position in the order
    val position = IntArray(v)
    for (i in order.indices) {
        position[order[i]] = i
    }

    // Check if each edge u → v follows the correct order
    for (u in adj.indices) {
        for (v in adj[u]) {
            if (position[u] > position[v]) {
                return false // u should come before v, but it's not
            }
        }
    }
    return true
}

fun topologicalSortBfs(v: Int, adj: Array<IntArray>): List<Int> {

    val indegree = Array(v) { 0 }
    for (i in 0 until v) {
        adj[i].forEach {
            indegree[it]++
        }
    }

    val queue = LinkedList<Int>()
    val result = mutableListOf<Int>()
    for (i in 0 until v) {
        if (indegree[i] == 0) {
            queue.add(i)
        }
    }

    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        result.add(node)

        for (i in adj[node]) {
            indegree[i]--
            if (indegree[i] == 0) {
                queue.add(i)
            }
        }
    }

    return result

}
