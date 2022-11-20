import java.util.*;

public class text2 {
    public static void main(String[] args) {
        //��ȡ�ļ��������������ַ����ֵ�Ƶ��ΪȨֵ.��һ����ͳ���ļ����ַ���Ƶ��
        //�ڶ�����������������
        //��������������������
        //���Ĳ������ļ����б���

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
    //�ڲ��� �������ڵ�
    private class TreeNode {
        public TreeNode() { }
        public TreeNode(Character ch, int val, int freq, TreeNode left, TreeNode right) {
            this.ch = ch;
            this.val = val;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }//Ŀ����Ϊ�˸�����ڲ��ำֵ��������������������������������
        Character ch;
        int val;
        int freq;
        TreeNode left;
        TreeNode right;
    }


    //���뷽��������Object[]����СΪ2,Objec[0]Ϊ�������ַ�����Object[1]Ϊ�����Ӧ�����
    public Object[] encode(String s){
        Object[]res= new Object[2];
        //object��������ĸ��࣬��һ����ʱ�����û����ȷ�ļ̳�һ������Ļ�����ô������Object�����ࣻ
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
     * �����ַ�������������
     * @param s��Ҫ�����Դ�ַ���
     */
    private TreeNode constructTree(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        //����ÿ����ĸ�Ĵ�Ƶ���ŵ�Map�У�
        //1.�Ȱ�s���з�ɢ��char��Ȼ�������containsKey�������Ƿ��У��оͷ��Ҽ�һ�����������Ϊһ
        //Character����char������
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
        //����dataMap,��ʼ���������ڵ㣬�������г�ʼ����Ľڵ�ŵ�nodeList�У�����������
        LinkedList<TreeNode> nodeList = new LinkedList<TreeNode>();
        for (Map.Entry<Character, Integer> entry : dataMap.entrySet()) {
            Character ch = entry.getKey();
            int freq = entry.getValue();
            int val = 0;
            TreeNode tmp = new TreeNode(ch,val,freq,null,null);
            nodeList.add(tmp);
        }
        //�Դ�Žڵ������������򣬷�������������
        Collections.sort(nodeList, new Comparator<TreeNode>() {
            public int compare(TreeNode t1, TreeNode t2) {
                return t1.freq-t2.freq;
            }
        });
        /*
         * ���壺Comparator���ⲿ�Ƚ��������ڱȽ������������֮��ģ�
         * ����������бȽϣ������ڼ������򣬶�Comparable������Ϊ��һ���ڱȽ�����
         * ���ݶ���ĳһ���Խ��������
         * */
        //size==1,�����ַ���ֻ����һ�����͵���ĸ
        if(nodeList.size()==1){
            TreeNode t = nodeList.get(0);
            return new TreeNode(null,0,nodeList.get(0).freq,t,null);
        }

        //��������õĽڵ㽨����������rootΪ��ʼ�����ڵ�
        TreeNode root = null;
        while(nodeList.size()>0){
            //��ΪnodeList��ǰ���Ѿ��ź�������ֱ��ȡ��ǰ�����ڵ㣬���ǵĺͿ϶�Ϊ��С
            TreeNode t1 = nodeList.removeFirst();
            TreeNode t2 = nodeList.removeFirst();
            //��������val��ֵΪ0����������val��ֵΪ1
            t1.val = 0;
            t2.val = 1;
            //��ȡ���������ڵ���кϲ�
            if(nodeList.size()==0){
                //��ʱ�������нڵ�ϲ���ϣ����ؽ��
                root = new TreeNode(null,0,t1.freq+t2.freq,t1,t2);
            }else {
                //��ʱ�����п��Ժϲ��Ľڵ�
                TreeNode tmp = new TreeNode(null,0,t1.freq+t2.freq,t1,t2);

                //t1��t2�ϲ�����Ҫ���õ����½ڵ���뵽ԭ�����У������������ڵ�ϲ���
                //��ʱ��Ҫ��֤ԭ����������ԣ���Ҫ��������
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
        //���ؽ����õĶ��������ڵ�
        return root;
    }

    //���Ѿ������õĶ��������б������õ�ÿ���ַ��ı���
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

    //���ַ������н��룬����ʱ��Ҫ�������
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


