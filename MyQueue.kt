import java.util.*


class Queue {
    var first: Node? = null
    var last: Node? = null

    val isEmpty: Boolean
        get() = first == null

    constructor(data: String) {
        first = Node(data)
        last = first
    }

    constructor() {
        first = null
        last = first
    }

    inner class Node(var data: String) {
        var next: Node? = null

        init {
            next = null
        }
    }

    fun Enqueue(d: String) {
        if (this.isEmpty) {
            last = Node(d)
            first = last
        } else {
            last!!.next = Node(d)
            last = last!!.next
        }
    }

    fun Dequeue(): String {
        val d = first!!.data
        first = first!!.next
        return d
    }

}

class Stack {

    var first: Node? = null

    val isEmpty: Boolean
        get() = first == null

    constructor(data: String) {
        first = Node(data)
    }

    constructor() {
        first = null
    }

    inner class Node(var data: String) {
        var next: Node? = null

        init {
            next = null
        }
    }

    fun Push(d: String) {
        val p = Node(d)
        p.next = first
        first = p
    }

    fun Pop(): String {
        val d = first!!.data
        first = first!!.next
        return d
    }

    fun Top(): String {
        return first!!.data
    }
}

object MyQueue {
    internal fun ShuntingYard(prefix: String): String {
        val st = StringTokenizer(prefix, " ")
        val s = Stack()
        val q = Queue()
        while (st.hasMoreTokens()) {
            val t = st.nextToken()
            if (t == "(") {
                s.Push(t)
            } else if (t == ")") {
                while (s.Top() != "(") {
                    q.Enqueue(s.Pop())
                }
                s.Pop()
            } else if (t == "+" || t == "-" || t == "*" || t == "/") {
                while (!s.isEmpty && OperationPriority(s.Top()) > OperationPriority(t)) {
                    q.Enqueue(s.Pop())
                }
                s.Push(t)
            } else {
                q.Enqueue(t)
            }
        }

        while (!s.isEmpty) {
            q.Enqueue(s.Pop())
        }

        var postfix = q.Dequeue()

        while (!q.isEmpty) {
            postfix += " " + q.Dequeue()
        }

        return postfix
    }

    internal fun OperationPriority(operation: String): Int {
        if (operation == "+" || operation == "-")
            return 0
        else if (operation == "*" || operation == "/") return 1
        return -1
    }

    internal fun computePostfix(postfix: String): Int {
        val st = StringTokenizer(postfix, " ")
        val stack = Stack()
        while (st.hasMoreTokens()) {
            val s = st.nextToken()
            if (s == "+") {
                val b = Integer.parseInt(stack.Pop())
                val a = Integer.parseInt(stack.Pop())
                stack.Push(Integer.toString(a + b))
            } else if (s == "-") {
                val b = Integer.parseInt(stack.Pop())
                val a = Integer.parseInt(stack.Pop())
                stack.Push(Integer.toString(a - b))
            } else if (s == "*") {
                val b = Integer.parseInt(stack.Pop())
                val a = Integer.parseInt(stack.Pop())
                stack.Push(Integer.toString(a * b))
            } else if (s == "/") {
                val b = Integer.parseInt(stack.Pop())
                val a = Integer.parseInt(stack.Pop())
                stack.Push(Integer.toString(a / b))
            } else {
                stack.Push(s)
                //System.out.println(stack.Top());
            }
        }
        return Integer.parseInt(stack.Pop())
    }

    fun main(args: Array<String>) {
        val sc = Scanner(System.`in`)
        val prefix = sc.nextLine()
        val postfix = ShuntingYard(prefix)
        println(postfix)

        // 3 * ( 9 - 7 ) - 6 / ( 5 - 4 )
        println(computePostfix(postfix))

    }

}

