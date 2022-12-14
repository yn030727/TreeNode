import java.util.*;

public class text2 {
    public static void main(String[] args) {
        //读取文件，保存数组中字符出现的频率为权值.第一步：统计文件中字符的频率
        //第二步：创建哈夫曼树
        //第三步：遍历哈夫曼树
        //第四步：对文件进行编码

        String s = "aaabbbeeedacfwwwwddd";
        //String s="sfawefawfsfwfsafgasf";
        //String s="";
        System.out.println("before code:" + s);

        Object[] encodeRes = new Huffman().encode(s);
        String encodeStr = (String) encodeRes[0];
        Map<Character, String> encodeMap = (Map<Character, String>) encodeRes[1];

        System.out.println("code tab:");
        for (Map.Entry<Character, String> e : encodeMap.entrySet()) {
            System.out.println(e.getKey() + ":" + e.getValue());
        }
        System.out.println("after code:" + encodeStr);
    }
}
class Huffman {
    //内部类 二叉树节点
    private class TreeNode {
        public TreeNode() { }
        public TreeNode(Character ch, int val, int freq, TreeNode left, TreeNode right) {
            this.ch = ch;
            this.val = val;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }//目的是为了给这个内部类赋值！！！！！！！！！！！！！！！！
        Character ch;
        int val;
        int freq;
        TreeNode left;
        TreeNode right;
    }


    //编码方法，返回Object[]，大小为2,Objec[0]为编码后的字符串，Object[1]为编码对应的码表
    public Object[] encode(String s){
        Object[]res= new Object[2];
        //object是所有类的父类，何一个类时候如果没有明确的继承一个父类的话，那么它就是Object的子类；
        Map<Character,String> encodeMap = new HashMap<Character, String>();
        TreeNode tree = constructTree(s);
        findPath(tree, encodeMap, new StringBuilder());
        findPath(tree, encodeMap, new StringBuilder());
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();i++){
            String tmp = encodeMap.get(s.charAt(i));
            sb.append(tmp);
        }
        res[0]=sb.toString();
        res[1] = encodeMap;
        return res;

    }

    /*
     * 根据字符串建立二叉树
     * @param s：要编码的源字符串
     */
    private TreeNode constructTree(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        //计算每个字母的词频，放到Map中；
        //1.先把s进行分散成char，然后遍历用containsKey方法看是否有，有就放且加一，无则放设置为一
        //Character就是char类型类
        Map<Character, Integer> dataMap = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (dataMap.containsKey(c)) {
                int count = dataMap.get(c);
                dataMap.put(c, count + 1);
            } else {
                dataMap.put(c, 1);
            }
        }
        //遍历dataMap,初始化二叉树节点，并将所有初始化后的节点放到nodeList中，并进行排序
        LinkedList<TreeNode> nodeList = new LinkedList<TreeNode>();
        for (Map.Entry<Character, Integer> entry : dataMap.entrySet()) {
            Character ch = entry.getKey();
            int freq = entry.getValue();
            int val = 0;
            TreeNode tmp = new TreeNode(ch,val,freq,null,null);
            nodeList.add(tmp);
        }
        //对存放节点的链表进行排序，方便后续进行组合
        Collections.sort(nodeList, new Comparator<TreeNode>() {
            public int compare(TreeNode t1, TreeNode t2) {
                return t1.freq-t2.freq;
            }
        });
        /*
         * 定义：Comparator是外部比较器，用于比较来对象与对象之间的，
         * 两个对象进行比较，多用于集合排序，而Comparable可以认为是一个内比较器，
         * 根据对象某一属性进行排序的
         * */
        //size==1,代表字符串只包含一种类型的字母
        if(nodeList.size()==1){
            TreeNode t = nodeList.get(0);
            return new TreeNode(null,0,nodeList.get(0).freq,t,null);
        }

        //利用排序好的节点建立二叉树，root为初始化根节点
        TreeNode root = null;
        while(nodeList.size()>0){
            //因为nodeList在前面已经排好序，所以直接取出前两个节点，他们的和肯定为最小
            TreeNode t1 = nodeList.removeFirst();
            TreeNode t2 = nodeList.removeFirst();
            //左子树的val赋值为0，右子树的val赋值为1
            t1.val = 0;
            t2.val = 1;
            //将取出的两个节点进行合并
            if(nodeList.size()==0){
                //此时代表所有节点合并完毕，返回结果
                root = new TreeNode(null,0,t1.freq+t2.freq,t1,t2);
            }else {
                //此时代表还有可以合并的节点
                TreeNode tmp = new TreeNode(null,0,t1.freq+t2.freq,t1,t2);

                //t1、t2合并后，需要将得到的新节点加入到原链表中，继续与其他节点合并，
                //此时需要保证原链表的有序性，需要进行排序
                if(tmp.freq>nodeList.getLast().freq){
                    nodeList.addLast(tmp);
                }else {
                    for(int i=0;i<nodeList.size();i++){
                        int tmpFreq = tmp.freq;
                        if(tmpFreq<= nodeList.get(i).freq){
                            nodeList.add(i,tmp);
                            break;
                        }
                    }
                }
            }
        }
        //返回建立好的二叉树根节点
        return root;
    }

    //对已经建立好的二叉树进行遍历，得到每个字符的编码
    private void findPath(TreeNode root, Map<Character,String> res, StringBuilder path) {
        if (root.left == null && root.right == null) {
            path.append(root.val);
            res.put(root.ch,path.substring(1));
            path.deleteCharAt(path.length() - 1);
            return;
        }
        path.append(root.val);
        if (root.left != null) findPath(root.left, res, path);
        if (root.right != null) findPath(root.right, res, path);
        path.deleteCharAt(path.length() - 1);
    }

    //对字符串进行解码，解码时需要编码码表
    public String decode(String encodeStr,Map<Character,String> encodeMap){
        StringBuilder decodeStr = new StringBuilder();
        while(encodeStr.length()>0){
            for(Map.Entry<Character,String> e: encodeMap.entrySet()){
                String charEncodeStr = e.getValue();
                if(encodeStr.startsWith(charEncodeStr)){
                    decodeStr.append(e.getKey());
                    encodeStr = encodeStr.substring(charEncodeStr.length());
                    break;
                }
            }
        }
        return decodeStr.toString();
    }

}


