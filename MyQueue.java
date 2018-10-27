import java.util.*;

class Queue{
    public Node first, last;

    public Queue(String data){
        first = new Node(data);
        last = first;
    }
    public Queue(){
        first = null;
        last = first;
    }

    class Node{
        String data;
        Node next;
        public Node(String data){
            this.data = data;
            next = null;
        }
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void Enqueue(String d){
        if(this.isEmpty()) first = last = new Node(d);
        else{
            last.next = new Node(d);
            last = last.next;
        }
    }

    public String Dequeue(){
        String d = first.data;
        first = first.next;
        return d;
    }

}

class Stack{

    public Node first;

    public Stack(String data){
        first = new Node(data);
    }
    public Stack(){
        first = null;
    }

    class Node{
        String data;
        Node next;

        public Node(String data){
            this.data = data;
            next = null;
        }
    }

    void Push(String d){
        Node p = new Node(d);
        p.next = first;
        first = p;
    }

    String Pop(){
        String d = first.data;
        first = first.next;
        return d;
    }

    String Top(){
        return first.data;
    }

    boolean isEmpty(){
        return first == null;
    }
}

public class MyQueue {
    static String ShuntingYard(String prefix){
        StringTokenizer st = new StringTokenizer(prefix, " ");
        Stack s = new Stack();
        Queue q = new Queue();
        while(st.hasMoreTokens()) {
            String t = st.nextToken();
            if(t.equals("(")){
                s.Push(t);
            }
            else if(t.equals(")")){
                while(!s.Top().equals("(")) {
                    q.Enqueue(s.Pop());
                }
                s.Pop();
            }
            else if(t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/")){
                while(!s.isEmpty() && OperationPriority(s.Top()) > OperationPriority(t)){
                    q.Enqueue(s.Pop());
                }
                s.Push(t);
            }
            else{
                q.Enqueue(t);
            }
        }

        while(!s.isEmpty()){
            q.Enqueue(s.Pop());
        }

        String postfix = q.Dequeue();

        while(!q.isEmpty()){
            postfix += " " + q.Dequeue();
        }

        return postfix;
    }

    static int OperationPriority(String operation){
        if(operation.equals("+") || operation.equals("-")) return 0;
        else if(operation.equals("*") || operation.equals("/")) return 1;
        return -1;
    }

    static int computePostfix(String postfix){
        StringTokenizer st = new StringTokenizer(postfix, " ");
        Stack stack = new Stack();
        while(st.hasMoreTokens()){
            String s = st.nextToken();
            if(s.equals("+")){
                int b = Integer.parseInt(stack.Pop());
                int a = Integer.parseInt(stack.Pop());
                stack.Push(Integer.toString(a+b));
            }
            else if(s.equals("-")){
                int b = Integer.parseInt(stack.Pop());
                int a = Integer.parseInt(stack.Pop());
                stack.Push(Integer.toString(a-b));
            }
            else if(s.equals("*")){
                int b = Integer.parseInt(stack.Pop());
                int a = Integer.parseInt(stack.Pop());
                stack.Push(Integer.toString(a*b));
            }
            else if(s.equals("/")){
                int b = Integer.parseInt(stack.Pop());
                int a = Integer.parseInt(stack.Pop());
                stack.Push(Integer.toString(a/b));
            }
            else{
                stack.Push(s);
                System.out.println(stack.Top());
            }
        }
        return Integer.parseInt(stack.Pop());
    }

    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        String prefix = sc.nextLine();
        String postfix = ShuntingYard(prefix);
        System.out.println(postfix);

        // 3 * ( 9 - 7 ) - 6 / ( 5 - 4 )
        System.out.println(computePostfix(postfix));

    }

}
