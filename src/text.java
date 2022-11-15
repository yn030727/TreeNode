
import java.util.*;
import java.util.function.Function;
public class text {
    public static void main(String[] args) {
        System.out.println("请输入根节点的值：");
        Scanner sc=new Scanner(System.in);
        int data=sc.nextInt();
        //测试用例：1234567(层次遍历形式这里是) 输入:1 2 4 -1 -1 5 -1 -1 3 6 -1 -1 7 -1 -1
        TreeNode root =creat_tree(data);
        //后序遍历输出 输出：4526731
        postorder(root);
        //非递归的前序遍历 输出:[1,2,4,5,3,6,7]
        List<Integer> integerList=preOrder(root);
        System.out.println(integerList);
        //层次遍历  输出：[[1],[2,3],[4,5,6,7]]
        List<List<Integer>> lists=levelOrder(root);
        System.out.println(lists);
    }
    //建立二叉树
    public static TreeNode creat_tree(int data){
        if(data==-1){
            return null;
        }
        TreeNode t=new TreeNode();
        int left=0;
        int right=0;
        t.val=data;
        System.out.println("请输入当前结点的左子树(-1退出递归):");
        Scanner sc1=new Scanner(System.in);
        left=sc1.nextInt();
        t.left=creat_tree(left);
        System.out.println("请输入当前结点的右子树(-1退出递归):");
        Scanner sc2=new Scanner(System.in);
        right=sc2.nextInt();
        t.right=creat_tree(right);
        return t;
    }
    //通过递归对二叉树进行后序遍历
    public static void postorder(TreeNode root){
        if(root==null){
            return ;
        }
        postorder(root.left);
        postorder(root.right);
        System.out.println(root.val);
    }
    //二叉树的非递归遍历算法
    //这里采用栈来先序遍历的非递归算法
    public static List<Integer> preOrder(TreeNode root){
        //存放前序遍历完返回的集合
        List<Integer> ans=new ArrayList<>();
        //创建一个栈
        Stack<TreeNode> stack=new Stack<>();
        //如果为空，就返回当前
        if(root==null)return ans;
        //把当前结点加入到栈中
        stack.push(root);
        //如果栈不为空
        while(!stack.isEmpty()){
            //将栈顶元素出栈
            TreeNode cur = stack.pop();
            //将栈顶元素的值加入到集合当中
            ans.add(cur.val);
            if(cur.right != null){
                stack.push(cur.right);
            }
            if(cur.left != null){
                stack.push(cur.left);
            }
        }
        return ans;
    }
    //二叉树的层次遍历
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> lists=new ArrayList<>();
        Queue<TreeNode> queue=new ArrayDeque<>();
        if(root==null)return lists;
        //将根节点加入到队列当中
        //队列通过offer将值传递进去
        queue.offer(root);
        //退出循环的某个条件
        while(!queue.isEmpty()){
            int len=queue.size();
            //给每一层定义一个存放数据的集合
            List<Integer> list=new ArrayList<>();
            //遍历每一层存好的队列
            for(int i=0;i<len;i++){
                //左节点不为null
                if(queue.peek().left!=null){
                    queue.offer(queue.peek().left);
                }
                //有结点不为null
                if(queue.peek().right!=null){
                    queue.offer(queue.peek().right);
                }
                list.add(queue.poll().val);
            }
            lists.add(list);
        }
        return lists;
    }

}
