package twentytwentytwo.day7

import readInput

class TreeNode(var value: Int, private var name: String) {
    var parent: TreeNode? = null

    var children: MutableMap<String, TreeNode> = mutableMapOf()

    fun addChild(node: TreeNode): TreeNode? {
        if (!children.containsKey(node.name)) {
            children[node.name] = node
            node.parent = this
            return null
        }
        return node
    }

    fun totalValue(): Int {
        return children.values.sumOf { it.totalValue() } + this.value
    }

    override fun toString(): String {
        var s = "$value"
        if (children.isNotEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val root = TreeNode(0, "/")
        var currNode = root

        for (i in input.indices) {
            val line = input[i]
            val parts = line.split(" ")
            if (parts[0] == "$") {
                if (parts[1] == "cd") {
                    currNode = when (parts[2]) {
                        ".." -> currNode.parent!!
                        "/" -> root
                        else -> if (currNode.children.containsKey(parts[2])) {
                            currNode.children[parts[2]]!!
                        } else {
                            currNode.addChild(TreeNode(0, parts[2]))!!
                        }
                    }
                }
            } else {
                if (parts[0] == "dir") {
                    currNode.addChild(TreeNode(0, parts[1]))
                } else {
                    val size = parts[0].toInt()
                    val fileName = parts[1]
                    currNode.addChild(TreeNode(size, fileName))
                }
            }
        }

        var sum = 0
        val stack = ArrayDeque<TreeNode>()
        stack.addFirst(root)
        while (!stack.isEmpty()) {
            val curr = stack.removeFirst()

            curr.value = curr.totalValue()

            curr.children.values.forEach {
                stack.addFirst(it)
            }
            if (curr.value <= 100000 && curr.children.isNotEmpty()) {
                sum += curr.value
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val root = TreeNode(0, "/")
        var currNode = root

        for (i in input.indices) {
            val line = input[i]
            val parts = line.split(" ")
            if (parts[0] == "$") {
                if (parts[1] == "cd") {
                    currNode = when (parts[2]) {
                        ".." -> currNode.parent!!
                        "/" -> root
                        else -> if (currNode.children.containsKey(parts[2])) {
                            currNode.children[parts[2]]!!
                        } else {
                            currNode.addChild(TreeNode(0, parts[2]))!!
                        }
                    }
                }
            } else {
                if (parts[0] == "dir") {
                    currNode.addChild(TreeNode(0, parts[1]))
                } else {
                    val size = parts[0].toInt()
                    val fileName = parts[1]
                    currNode.addChild(TreeNode(size, fileName))
                }
            }
        }

        val sizeNeeded = 30000000 - (70000000 - root.totalValue())

        var min = Int.MAX_VALUE
        val stack = ArrayDeque<TreeNode>()
        stack.addFirst(root)
        while (!stack.isEmpty()) {
            val curr = stack.removeFirst()

            curr.value = curr.totalValue()

            curr.children.values.forEach {
                stack.addFirst(it)
            }
            if (curr.value in (sizeNeeded + 1)..min && curr.children.isNotEmpty()) {
                min = curr.value
            }
        }

        return min
    }

    val input = readInput("day7", "Day07_input")
    println(part1(input))
    println(part2(input))
}

