import java.util.Stack;

public class minStack {
    private final Stack<Integer> stack;
    private final Stack<Integer> adjuvantStack;

    public minStack() {
        stack= new Stack<>();
        adjuvantStack= new Stack<>();
    }

    public void push(int val) {
        stack.push(val);
        if(!adjuvantStack.isEmpty()){
            if(val<adjuvantStack.peek()) adjuvantStack.push(val);
            else adjuvantStack.push(adjuvantStack.peek());
        }else adjuvantStack.push(val);
    }

    public void pop() {
        //两个一起pop
        stack.pop();
        adjuvantStack.pop();
        String a;
    }

    public int top() {
        int num=stack.peek();
        return num;
    }
    public int getMin() {
        int min=adjuvantStack.peek();
        return min;
    }
    //peek查看栈顶部的对象，但不移除它
    //pop 移除栈顶部的对象，并作为次函数的值返回该对象
    //push 把元素压入栈顶部
    //search 返回对象在栈中的位置，以1为基数
}
