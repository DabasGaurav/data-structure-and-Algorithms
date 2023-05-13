-------------------------------- CONSTRUCTION -------------------------------------

1. // for generic tree, we need a head of type head to traverse n everything, thus no new class. A node will have the data of that node and all its children. since no. of children can be variable thus arraylist. Now to construct from preOrder, we use -1 to mark that no more children for this particular node. So we have a stack and we push, we add the pushed element to children of the element just below in stack. if stack empty then its root obv. now ehen -1 is encountered, that means pop the element on top as its children are done. and go back to its parent which will be below it only in stack.
  
  static class Gnode{
        int data;
        ArrayList<Gnode> children;
        Gnode(int data){
            this.data = data;
            children = new ArrayList<>();
        }
    }
  
  public static Gnode constructGtree(ArrayList<Integer> a){
        int n = a.size();
        Gnode root = null;
        for(int i=0;i<n;i++){
            Stack<Gnode> st = new Stack<>();
            Gnode temp = new Gnode(a.get(i));
            if(st.empty()){
                root = temp;
                st.push(temp);
            }
            else{
                if(a.get(i)!=-1){
                    st.peek().children.add(temp);
                    st.push(temp);
                }
                else{
                    st.pop();
                }
            }
        }
        return root;
    }


2. // for binary tree we just need a data variable, left ptr, right ptr. Construction is similar, except -1 means no node 'here', since we know each element has 2 children (could be null, in that case we give -1). So we take another flag in stack with the element which determines how many children have we joined till now. if 0, then 0, 1 then 1, 2 then pop it since both done.
  static class Bnode{
        int data;
        Bnode left;
        Bnode right;
        Bnode(int data){
            this.data = data;
            left = null;
            right = null;
        }
    }

    public static Bnode constructBtree(ArrayList<Integer> a){
        int n = a.size();
        Bnode root = null;
        for(int i=0;i<n;i++){
            Stack<Pair<Bnode, Integer>> st = new Stack<>();
            Bnode temp = new Bnode(a.get(i));
            if(st.empty()){
                root = temp;
                st.push(new Pair(new Bnode(a.get(i)),0));
            }
            else{
                if(a.get(i)!=-1){
                    Pair p = st.peek();
                    if(st.peek().getValue()==0){
                        st.peek().getKey().left = temp;
                        st.pop();
                        st.push(new Pair(p.getKey(),(Integer)p.getValue()+1));

                    }
                    else if(st.peek().getValue()==1){
                        st.peek().getKey().right = temp;
                        st.pop();
                    }
                    st.push(new Pair(new Bnode(a.get(i)),0));
                }
                else{
                    Pair p = st.peek();
                    st.pop();
                    if((Integer)p.getValue()!=1) {
                        st.push(new Pair(p.getKey(), (Integer) p.getValue() + 1));
                    }
                }
            }
        }
        return root;
    }

3. // To Serialize, simply make a string of preOrder. exactly like we got the input in above question. there should be no ambiguity. To deserialize, split the string by any deliminator. Then we get the same arraylist as we had while construcction.
  
  public static void serializee(Bnode root, ArrayList<Integer> ans){
        if(root==null){
            ans.add(null);
            return;
        }
        ans.add(root.data);
        serializee(root.left,ans);
        serializee(root.right,ans);
    }

    public static void deSerialize(String s){
        String[] s1 = s.split(",");
        ArrayList<Integer> a = new ArrayList<>();
        for(String str: s1){
            if(str!=null) {
                a.add(Integer.parseInt(str));
            }
            else{
                a.add(null);
            }
        }
        constructBtree(a);
    }

4. // To construct from Pre and In, the first element of pre is the head of current subtree/tree. Find it in Inorder. Now in Inorder, elements to left are in left subtree, and ||ly for right. We use same number of elements in left subtree acc to InOrder in PreOrder after the first element (root) to determine left subtree. and call recursively.
  public static Bnode BTfromPreAndIn(List<Integer> pre, int sp, int ep, List<Integer> in, int si, int ei){
        if(sp==ep){
            return new Bnode(pre.get(sp));
        }
        if(sp>ep){
            return null;
        }
        Bnode root = new Bnode(pre.get(sp));
        int idx = in.indexOf(pre.get(sp));
        int len = idx-si;

        root.left = BTfromPreAndIn(pre,sp+1,sp+len, in, si,idx-1);
        root.right = BTfromPreAndIn(pre,sp+len+1, ep, in, idx+1,ei);
        return root;
    }

5. // incase of post, last item is the root, rest everthing remains same.
  public static Bnode BTfromPostAndIn(List<Integer> post, int sp, int ep, List<Integer> in, int si, int ei){
        if(sp==ep){
            return new Bnode(post.get(sp));
        }
        if(sp>ep){
            return null;
        }
        Bnode root = new Bnode(post.get(ep));
        int idx = in.indexOf(post.get(sp));
        int len = idx-si;

        root.left = BTfromPreAndIn(post,sp,sp+len-1, in, si,idx-1);
        root.right = BTfromPreAndIn(post,sp+len, ep-1, in, idx+1,ei);
        return root;
    }

6. // The first element of levelOrder gives the root. Now for inorder we can divide easily like before. For levelorder, we'll have to pick manually all the elements in left n right subtree by matching with the inorder subtree elements by using a hashset since order isn't same.
   public static Bnode BTfromLevelAndIn(List<Integer> in, int si, int ei, List<Integer> levelOrder){
        if(si==ei){
            return new Bnode(in.get(si));
        }
        if(si>ei){
            return null;
        }
        Bnode root = new Bnode(levelOrder.get(0));
        int idx = in.indexOf(root.data);
        
        HashSet<Integer> set = new HashSet<>();
        for(int i=si;i<idx;i++){
            set.add(in.get(i));
        }
        List<Integer> levelLeft = new ArrayList<>();
        List<Integer> levelRight = new ArrayList<>();
        for(int i=1;i<levelOrder.size();i++){
            if(set.contains(levelRight.get(i))){
                levelLeft.add(levelRight.get(i));
            }
            else{
                levelRight.add(levelRight.get(i));
            }
        }
        root.left = BTfromLevelAndIn(in, si, idx-1, levelLeft);
        root.right = BTfromLevelAndIn(in, idx+1,ei,levelRight);
        return root;
    }

7. // Adding in BST is simple, just go left/right based on the curr and node to insert. When found null, then insert. Removing is little tricky. When we remove a leaf node then no problem. Also when we remove anode with one child then also no problem as we can replace the node by node's child and BST property will be retained. incase of both children, either swap the removed node with the right sibtree's leftmost node or vica versa to maintain BST property.
  public static void addNodeToBST(Bnode root, Bnode node){
        if(root==null)
            return;
        if(root.data<node.data){
            if(root.right!=null){
                addNodeToBST(root.right,node);
            }
            else{
                root.right = node;
            }
        }
        else{
            if(root.left!=null){
                addNodeToBST(root.left,node);
            }
            else{
                root.left = node;
            }
        }
    }

    public static Bnode removeNodeFromBST(Bnode root, Bnode node){
        if(root == null){
            return null;
        }
        else{
            if(root.data<node.data){
                return removeNodeFromBST(root.right,node);
            }
            else if(root.data>node.data){
                return removeNodeFromBST(root.left,node);
            }
            else{
                if(root.left == null && root.right == null){
                    return null;
                }
                else if(root.left == null){
                    return root.right;
                }
                else if(root.right == null){
                    return root.left;
                }
                else{
                    Bnode temp = root.right;
                    while(temp.left!=null){
                        temp=temp.left;
                    }
                    node.data = temp.data;
                    removeNodeFromBST(root.right,temp);
                    return node;
                }
            }
        }
    }


----------------------------------------- TRAVERSALS ------------------------------------------
  
  8. public static void preOrder(Bnode root){
        if(root==null)
            return;
        System.out.println(root.data);
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void InOrder(Bnode root){
        if(root==null)
            return;
        InOrder(root.left);
        System.out.println(root.data);
        InOrder(root.right);
    }

    public static void postOrder(Bnode root){
        if(root==null)
            return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.data);
    }

9. // can keep track of size to print linewise
    public static void levelOrder(Bnode root){
        Queue<Bnode> q = new LinkedList<>();
        q.add(root);
        System.out.println(root.data);
        while(q.size()>0){
            Bnode node = q.poll();
            if(node.left!=null){
                q.add(node.left);
                System.out.println(node.left.data);
            }
            if(node.right!=null){
                q.add(node.right);
                System.out.println(node.right.data);
            }
        }
    }

 10. // Keep a flag to tell whether we need to add from left to right or otherwise. Now iterate like normal LevelOrder except keep a stack instead of queue. This is because we cannot iterate from right to left and add in queue from left to right. think why we won't get the required order. thus we use stack. If we iterate left to right, we add children from left to right in stack. Then when we pop it automatically becomes right to left. There is one improvement in this answer i.e. st stack is not needed actually.
       public static void levelOrderZigZag(Gnode root){
        Stack<Stack> st = new Stack();
        Stack<Gnode> temp = new Stack();

        temp.push(root);
        st.push(temp);

        int flag = 1;

        while(st.size()>0){
            int s = st.size();
            Stack<Gnode> toPush = new Stack();
            while(s-->0){
                Stack<Gnode> popped = st.pop();
                while(popped.size()>0){
                    Gnode node = popped.pop();
                    System.out.print(node.data+" ");
                    if(flag == 1){
                        for(Gnode n: node.children){
                            toPush.push(n);
                        }
                    }
                    else{
                        for(int i=node.children.size()-1;i>=0;i--){
                            toPush.push(node.children.get(i));
                        }
                    }
                }
            }
            if(toPush.size()>0)
                st.push(toPush);
            flag = 1 - flag;
            System.out.println();
        }
    }

11. // For iterative orders, make 3 lists. then iterate using a stack like we constructed a binary tree, when we see that none of the left or right node is added, add to preorder. If left subtree is done and we are starting the right subtree of any node, add it to inorder, and finally before pooping add it to postorder.
  public static void IterativeTraversal(Bnode root){
        ArrayList<Integer> pre = new ArrayList<>();
        ArrayList<Integer> post = new ArrayList<>();
        ArrayList<Integer> in = new ArrayList<>();

        Stack<Pair<Bnode,Integer>> st = new Stack<>();
        st.push(new Pair(root,1));
        pre.add(root.data);

        while(st.size()>0){
            Pair p = st.pop();
            if((Integer)p.getValue()==3){
                post.add(((Bnode)p.getKey()).data);
            }
            else{
                if((Integer) p.getValue()==1){
                    st.push(new Pair(p.getKey(),(Integer)p.getValue()+1));
                    if(((Bnode)p.getKey()).left!=null){
                        st.push(new Pair(((Bnode)p.getKey()).left,1));
                        pre.add(((Bnode)p.getKey()).left.data);
                    }
                }
                else{
                    st.push(new Pair(p.getKey(),(Integer)p.getValue()+1));
                    in.add(((Bnode)p.getKey()).data);
                    if(((Bnode)p.getKey()).right!=null){
                        st.push(new Pair(((Bnode)p.getKey()).right,1));
                        pre.add(((Bnode)p.getKey()).right.data);
                    }
                }
            }
        }
        for(Integer i: pre){
            System.out.print(i+" ");
        }
        System.out.println();
        for(Integer i: in){
            System.out.print(i+" ");
        }
        System.out.println();
        for(Integer i: post){
            System.out.print(i+" ");
        }
        System.out.println();
    }

12. // morris traversals. 


------------------------------------------ VIEWS -------------------------------------------
  
13. // left view is first element of every level in levelOrder. so can make an arrayList and put directly as well, or hashmap. keep tra k of the level number
  public static void leftView(Bnode root){
        Queue<Bnode> q = new LinkedList<>();
        q.add(root);
        ArrayList<Bnode> ans = new ArrayList<>();
        int level = 0;
        while(q.size()>0){
            int s = q.size();
            level++;
            while(s-->0) {
                Bnode top = q.poll();
                if(ans.size()<level) {
                    ans.add(top);
                }
                if(top.left!=null){
                    q.add(top.left);
                }
                if(top.right!=null){
                    q.add(top.right);
                }
            }
        }
    }

14. // similarly for right view, last element of level order. 
  public static void rightView(Bnode root){
        Queue<Bnode> q = new LinkedList<>();
        q.add(root);
        ArrayList<Bnode> ans = new ArrayList<>();
        int level = 0;
        while(q.size()>0){
            int s = q.size();
            level++;
            while(s-->0) {
                Bnode top = q.poll();
                if(ans.size()<level) {
                    ans.add(top);
                }
                else{
                    ans.set(level-1,top);
                }
                if(top.left!=null){
                    q.add(top.left);
                }
                if(top.right!=null){
                    q.add(top.right);
                }
            }
        }
    }

15. // vertical order level = +1 if right, -1 if left. Clculate width first. Now for vertical order, apply levelOrder and keep track of vertical order for each order and keep on putting in ans array for their corresponding VO level. We could have simply used recursion (preOrder) but the order could have been wrong in that way.
  public static void width(Bnode root, int level, int[] pair){
        if(root==null)
            return;
        pair[0]= Math.min(pair[0],level);
        pair[1] = Math.max(pair[1], level);
        width(root.left,level-1, pair);
        width(root.right, level+1, pair);
    }

    public static void verticalOrder(Bnode root){
        int[] pair = new int[2];
        width(root,0,pair);
        int left = -pair[0];
        int right = pair[1];
        Queue<Pair<Bnode,Integer>> q = new LinkedList<>();
        ArrayList<Integer>[] ans = new ArrayList[left+right+1];
        for(int i=0;i<ans.length;i++){
            ans[i]=new ArrayList<>();
        }
        q.add(new Pair<>(root,left));
        while(q.size()>0){
            Pair p = q.poll();
            Bnode top = (Bnode) p.getKey();
            int level = (Integer) p.getValue();
            ans[level].add(top.data);
            if(top.left!=null){
                q.add(new Pair<>(top.left,level-1));
            }
            if(top.right!=null){
                q.add(new Pair<>(top.right,level+1));
            }
        }
    }

16. // top view is first element of vertical order
  public static void topView(Bnode root){
        int[] pair = new int[2];
        width(root,0,pair);
        int left = -pair[0];
        int right = pair[1];
        Queue<Pair<Bnode,Integer>> q = new LinkedList<>();
        int[] ans = new int[left+right+1];

        q.add(new Pair<>(root,left));
        while(q.size()>0){
            Pair p = q.poll();
            Bnode top = (Bnode) p.getKey();
            int level = (Integer) p.getValue();
            if(ans[level]==0)
            ans[level]=top.data;
            if(top.left!=null){
                q.add(new Pair<>(top.left,level-1));
            }
            if(top.right!=null){
                q.add(new Pair<>(top.right,level+1));
            }
        }
    }

17. // bottom view is last element of vertical order
  public static void bottomView(Bnode root){
        int[] pair = new int[2];
        width(root,0,pair);
        int left = -pair[0];
        int right = pair[1];
        Queue<Pair<Bnode,Integer>> q = new LinkedList<>();
        int[] ans = new int[left+right+1];

        q.add(new Pair<>(root,left));
        while(q.size()>0){
            Pair p = q.poll();
            Bnode top = (Bnode) p.getKey();
            int level = (Integer) p.getValue();
            ans[level]=top.data;
            if(top.left!=null){
                q.add(new Pair<>(top.left,level-1));
            }
            if(top.right!=null){
                q.add(new Pair<>(top.right,level+1));
            }
        }
    }

18. // For diagonal view, put root in queue. Now keep popping on iterating to right and keep on adding left children of popped if they exist. then repeat till queue is empty and print according to queue size as we do in linewise levelOrder.
  public static void diagonalOrder(Bnode root){
        Queue<Bnode> q = new LinkedList<>();
        q.add((root));
        while(q.size()>0){
            int s = q.size();
            while(s-->0){
                Bnode temp = q.poll();
                while(temp!=null){
                    if(temp.left!=null){
                        q.add(temp.left);
                    }
                    System.out.println(temp.data+" ");
                    temp = temp.right;
                }
            }
            System.out.println();
        }
    }


------------------------------------------ BASIC RECURSIVE QUESTIONS -----------------------------------------
  
19. // basic size, max value, height
      public static int sizee(Gnode root){
        if(root==null){
            return 0;
        }
        int ans = 1;
        for(Gnode n: root.children){
            ans += sizee(n);
        }
        return ans;
    }

    public static int maximum(Gnode root){
        if(root==null){
            return 0;
        }
        int ans = root.data;
        for(Gnode n: root.children){
            ans = Math.max(ans,maximum(n));
        }
        return ans;
    }

    public static int height(Gnode root){
        if(root==null){
            return 0;
        }
        int ans = 1;
        for(Gnode n: root.children){
            ans = Math.max(ans,height(n));
        }
        return ans+1;
    }

20. // pred and succ
      public static Bnode pred = null;
    public static Bnode succ = null;
    public static void predAndSucc(Bnode root, Bnode check){
        if(root==null){
            return;
        }
        if(check.data==root.data){
            succ = check;
        }
        if(succ == null){
            pred = root;
        }
        if(succ == check){
            succ = root;
        }
        predAndSucc(root.left, check);
        predAndSucc(root.right,check);
    }

21. // keep track of max subtree and your tree's sum
      public static Pair<Integer,Integer> maxSubtree(Bnode root){
        if(root == null){
            return new Pair<>(0,0);
        }
        Pair a = maxSubtree(root.left);
        Pair b = maxSubtree(root.right);
        int sum = (Integer)a.getValue()+(Integer) b.getValue()+root.data;
        return new Pair<>(Math.max((int) a.getKey(),Math.max((int)b.getKey(),sum)),sum);
    }

22. // just reverse the children for each node
      public static Bnode mirror(Bnode root){
        if(root == null)
            return null;
        Bnode left = mirror(root.left);
        Bnode right = mirror(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

23. // left of one should be equal to right of second. and same architecture.
      public static boolean isMirror(Bnode root1, Bnode root2){
        if(root1==null && root2 == null)
            return true;
        if(root1 == null || root2 == null)
            return false;
        return isMirror(root1.left,root2.right) && isMirror(root1.right,root2.left);
    }

24. // symmetric if its mirror of its own
      public static boolean isSymmetric(Bnode root){
        return isMirror(root.left,root.right);
    }

25. // if its a leaf node, return null. return yourelf if not null. do work in pre and not post.
      public static int removeLeaves(Gnode root){
        if(root==null)
            return 0;
        int s = root.children.size();
        for(int i=root.children.size()-1;i>=0;i--) {
            int ans = removeLeaves(root.children.get(i));
            if(ans == 1 ){
                root.children.remove(i);
            }
        }
        if(s==0){
            return 1;
        }
        return 2;
    }

26. // recursively linearize all children. and return their tails. now for current, for each tail, put its next to be the next child. and finally make children to only contain the first child. remove all others as they would have been joined to tails and linearized.
      public static Gnode linearize(Gnode root){
        if(root.children.size()==0)
            return root;
        Gnode tail = null;
        for(int i=0;i<root.children.size();i++){
            Gnode temp = linearize(root.children.get(i));
            if(i!=0){
                tail.children.add(root.children.get(i));
            }
            tail = temp;
        }
        ArrayList<Gnode> temp = new ArrayList<>();
        temp.add(root.children.get(0));
        root.children = temp;
        return tail;
    }

27. // leaves return that i dont have camera and need to be watched. rest return based on their children. if camera needed by any, then put camera, else same. The root needs to be handled separately if we want to put camera there or not.
      static int cameras = 0;
    public static int camera(Bnode root){
        if(root == null){
            return 1;
        }
        int left = camera(root.left);
        int right = camera(root.right);

        if(left ==0 || right ==0){
            cameras++;
            return 2;
        }
        if(left == 2 || right == 2){
            return 1;
        }
        return 0;
    }

28. // for each node, return the included best rob and excluded best rob. now for current include is curr + exc left + exc right and include is inc right + inc left / exc right + inc left / inc right + exc left
      public static Pair<Integer,Integer> rob(Bnode root){
        if(root==null)
            return new Pair(0,0);

        Pair left = rob(root.left);
        Pair right = rob(root.right);

        int li = (Integer)left.getKey();
        int le = (Integer)left.getValue();
        int ri = (Integer)right.getKey();
        int re = (Integer)right.getValue();

        return new Pair(root.data+le+re,Math.max(li+Math.max(ri,re),Math.max(ri+Math.max(li,le),re+le)));
    }

29. // each will return longest path considering curr to be left and right. now for parent, it'll take right from left node and left from right node. final ans will be in static variable.
      static int finalans = 0;
    public static Pair<Integer,Integer> longestZigZag(Bnode root){
        if(root == null){
            return new Pair<>(-1,-1);
        }
        Pair<Integer,Integer> left = longestZigZag(root.left);
        Pair<Integer,Integer> right = longestZigZag(root.right);
        finalans = Math.max(Math.max((Integer) left.getValue()+1,(Integer) right.getKey()+1),ans);
        return new Pair<>((Integer) left.getValue()+1,(Integer) right.getKey()+1);
    }

30. // 2 ways to solve these problems. one is heapmover, basically keep storing in an array that is there in the arguments constantly. other is to return that array by making it again and again, this looks more costlier.
      public static void singleChild(Bnode root, ArrayList<Bnode> a){
        if(root==null)
            return;
        if(root.left == null || root.right == null){
            a.add(root);
        }
        singleChild(root.left,a);
        singleChild(root.right,a);
    }

    public static ArrayList<Bnode> singleChild1(Bnode root){
        if(root==null)
            return null;
        ArrayList<Bnode> a = new ArrayList<>();
        if(root.left == null || root.right == null){
            a.add(root);
        }
        a.addAll(singleChild1(root.left));
        a.addAll(singleChild1(root.right));
        return a;
    }

31. // we keep a static variable sum. Now we R N L order because right calculates the sum of all larger and stores in sum variable. then we put that in the current node's data, add current node's value to sum and then iterate towards left. 
      static int sum=0;
    public static int replaceSumOfLarger(Bnode root){
        if(root==null)
            return 0;
        replaceSumOfLarger(root.right);
        sum+=root.data;
        root.data = sum;
        replaceSumOfLarger(root.left);
        return root.data;
    }

32. // simple.
      public static Bnode LCAofBST(Bnode root, int n1, int n2){
        if(root == null){
            return null;
        }
        if(n1>root.data && n2> root.data){
            return LCAofBST(root.right,n1,n2);
        }
        else if(n1<root.data && n2<root.data){
            return LCAofBST(root.left,n1,n2);
        }
        else {
            return root;
        }
    }

33. // simple
      public static void printInRange(Bnode root, int n1, int n2){
        if(root == null){
            return;
        }
        if(root.data>n1 && root.data<n2){
            System.out.println(root.data);
            printInRange(root.left,n1,n2);
            printInRange(root.right,n1,n2);
        }
        else{
            if(root.data>n2){
                printInRange(root.left,n1,n2);
            }
            else if(root.data<n1){
                printInRange(root.right,n1,n2);
            }
        }
    }

34. // call for all children, only one can have the ans, if any list returned is non empty, add yourself to it and return back. 
      public static ArrayList<Bnode> node2root(Bnode root, Bnode node){
       if(root == null)
           return null;
       if(node==root){
           ArrayList<Bnode> a = new ArrayList<>();
           a.add(root);
           return a;
       }
       ArrayList<Bnode> a = node2root(root.left,node);
       if(a!=null){
           a.add(root);
           return a;
       }
       a = node2root(root.right,node);
        if(a!=null){
            a.add(root);
            return a;
        }
        return null;
    }

35. // considering that LCA always exists. if you're the given nodes, return yourself. For others, call left right to find first. now based on returned results, return root, if left and right both are non null as its the LCA else return whichever is non null.
      public Bnode lowestCommonAncestor(Bnode root, Bnode p, Bnode q) {
        if (root == null || root == p || root == q) return root;
        Bnode left = lowestCommonAncestor(root.left, p, q);
        Bnode right = lowestCommonAncestor(root.right, p, q);
        return left == null ? right : right == null ? left : root;
    }

36. // 
      public static int ans = 0;
    public static int disBetweenNodes(Bnode root, int n1, int n2){
        if(root==null)
            return 0;
        int left = disBetweenNodes(root.left, n1, n2);
        int right = disBetweenNodes(root.right, n1,n2);

        if(root.data == n1 || root.data == n2){
            if(left!=0 || right!=0){
                ans = Math.max(left,right);
                return 0;
            }
            return 1;
        }
        if(left>0 && right>0){
            ans = left + right;
        }
        return left>0 ? left+1: (right>0 ? right+1 : 0);
    }
  
37. // return heights, store dia in global variable
      static int dia = 0;
    public static int diameter(Bnode root){
        if(root==null)
            return 0;
        int left = diameter(root.left);
        int right = diameter(root.right);
        dia = Math.max(left+right, dia);
        return Math.max(left,right)+1;
    }
  
38. // block node is to return. rest is simple
      public static ArrayList<Bnode> KLevelDown(Bnode root, int k, Bnode block){
        if(root == null || root == block){
            return new ArrayList<>();
        }

        if(k==0){
            ArrayList<Bnode> a = new ArrayList<>();
            a.add(root);
            return a;
        }
        ArrayList left = KLevelDown(root.left,k-1, block);
        ArrayList right = KLevelDown(root.right,k-1,block);
        left.addAll(right);
        return left;
    }

    public static ArrayList<Bnode> KDistAway(Bnode root, Bnode node, int k){
        ArrayList<Bnode> n2r = node2root(root,node);
        ArrayList<Bnode> ans = new ArrayList<>();
        for(int i=0;i<n2r.size();i++){
            Bnode n = n2r.get(i);
            ArrayList temp = KLevelDown(n, k--, i==0?null:n2r.get(i-1));
            ans.addAll(temp);
            if(k==-1){
                return ans;
            }
        }
        return ans;
    }

39. // simple
      public static void leftClone(Bnode root){
        if(root==null){
            return;
        }
        Bnode n = new Bnode(root.data);
        n.left = root.left;
        root.left = n;
        leftClone(root.left.left);
        leftClone(root.right);
    }

    public static void transBackFromLeftClonedTree(Bnode root){
        if(root==null)
            return;
        root.left = root.left.left;
        transBackFromLeftClonedTree(root.left);
        transBackFromLeftClonedTree(root.right);
    }













