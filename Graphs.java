
---------------------------------------- DFS RELATED --------------------------------------------

  
1. // DFS. need vis in arguments. thing to be noted is if we need to mark it unvisited while backtracking depending on the question. (if we need all possible paths or not)
  public static void DFS(ArrayList<Integer>[] graph, int src, boolean[] vis){
        for(int i=0;i<graph[src].size();i++){
            if(vis[graph[src].get(i)]==false){
                vis[graph[src].get(i)]=true;
                DFS(graph,graph[src].get(i),vis);
            }
        }
    }

2. // for ispath we just need any path till the dest. any will do. no need of all.
      public static boolean isPath(ArrayList<Integer>[] graph, int src, int dest, boolean[] vis){
        if(src == dest){
            return true;
        }
        for(int i=0;i<graph[src].size();i++){
            if(vis[graph[src].get(i)]==false){
                vis[graph[src].get(i)]=true;
                if(isPath(graph,graph[src].get(i),dest,vis)){
                    return true;
                }
            }
        }
        return false;
    }

3. // for all paths, we need to mark unvisited while backtracking
      public static void printAllPaths(ArrayList<Integer>[] graph, int src, int dest, boolean[] vis, String asf){
        if(src==dest){
            System.out.println(asf);
        }
        for(int i=0;i<graph[src].size();i++){
            if(vis[graph[src].get(i)]==false){
                vis[graph[src].get(i)]=true;
                printAllPaths(graph,graph[src].get(i),dest,vis,asf+" "+graph[src].get(i));
                vis[graph[src].get(i)]=false;
            }
        }
    }

4. // for iterative DFS, just apply bfs with stack instead of queue. useful incase where the call stack goes out of memory, so this one is in heap.    
  public static void IterativeDFS(ArrayList<Integer>[] graph, int src){
        Stack<Integer> st = new Stack<>();
        st.push(src);
        boolean[] vis = new boolean[graph.length];
        Arrays.fill(vis,false);
        while(st.size()>0){
            Integer curr = st.pop();
            if(!vis[curr]){
                vis[curr]=true;
                for(Integer i : graph[curr]){
                    if(!vis[i]){
                        st.push(i);
                    }
                }
            }
        }
    }

5. // hamiltonian path is a path that doesn't repeat any edge/vtx and visits all the vertex. Path is when it ends on the start point with the former conditions. To find, we need to iterate over all the poss paths ( thus  ) and if at any point we discover that we have covered all the vtces in a path, we print it. to keep track of this, we can keep a count (here the length of ans array) and check and equate to graph's size. the length of DFS is the number of vtces in that path. And for every path we find, we check if its a cycle or not.
      public static void hamiltonianPathAndCycles(ArrayList<Integer>[] graph, int src, final int source, boolean[] vis, ArrayList<Integer> asf){
        if(asf.size()==vis.length){
            System.out.println(asf.toString()+".");
            if(graph[src].contains(source)){
                System.out.println(asf.toString()+"*");
            }
        }
        for(int i=0;i<graph[src].size();i++){
            if(vis[graph[src].get(i)]==false){
                vis[graph[src].get(i)]=true;
                asf.add(graph[src].get(i));
                hamiltonianPathAndCycles(graph,graph[src].get(i),source,vis,asf);
                asf.remove(graph[src].get(i));
                vis[graph[src].get(i)]=false;
            }
        }
    }

6. // Perfect friends is connected components application. We apply dfs for each component (whenever we find a node unexplored). Now for each dfs we find one component. For perfect friends, we get components one by one and multiply the component count to current answer. think why
  public static int perfectFriends(ArrayList<Integer>[] graph){
        boolean[] vis = new boolean[graph.length];
        int ans = 1;
        for(int i=0;i<graph.length;i++){
            if(vis[i]==false){
                vis[i]=true;
                ans *= getConnectedComponents(graph,i,vis).size();
                System.out.println(ans);
            }
        }
        return ans;
    }

    public static ArrayList<Integer> getConnectedComponents(ArrayList<Integer>[] graph, int src, boolean[] vis){
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(src);
        for(int i=0;i<graph[src].size();i++){
            if(vis[graph[src].get(i)]==false){
                vis[graph[src].get(i)]=true;
                ans.addAll(getConnectedComponents(graph,graph[src].get(i),vis));
            }
        }
        return ans;
    }

7. // Make a stack and apply dfs. push in stack in post order. This ensures that the edges on the originating side of edge comes first. But we need the opposite to know whether its a string component or not. Now reverse the edges of the graph and pop from stack one by one and apply dfs same again. Now we are logically applying dfs from the problematic size i.e. the ending side of the edge. Now everytime we find an unexplored vtx popping from graph, its a new component. 
  public static void dfs(ArrayList<ArrayList<Integer>> graph, Stack st, boolean[] vis, int src){
        if(!vis[src]){
            vis[src]=true;
            for(int i : graph.get(src)){
                if(!vis[i]) {
                    dfs(graph, st, vis, i);
                }
            }
            st.push(src);
        }
    }
    public int kosaraju(ArrayList<ArrayList<Integer>> graph, int V){
        boolean[] vis = new boolean[V];
        Arrays.fill(vis,false);
        int ans = 0;
        Stack<Integer> st = new Stack<>();
        for(int i=0;i<graph.size();i++){
            if(!vis[i]){
                dfs(graph, st, vis, i);
            }
        }
        ArrayList<ArrayList<Integer>> g = new ArrayList<>();
        for(int i=0;i<graph.size();i++){
            g.add(new ArrayList<>());
        }
        for(int i=0;i<graph.size();i++){
            for(int j : graph.get(i)){
                // System.out.println(i+" "+j);
                g.get(j).add(i);
            }
        }
        // System.out.println(st.toString());
        Arrays.fill(vis,false);
        while(st.size()>0){
            int i = st.pop();
            if(vis[i]==false){
                // System.out.println(i);
                ans++;
                dfs(g, st, vis, i);
            }
            // System.out.println();
        }
        return ans;
    }

  
8. // mother vertex. Here we simply apply dfs in post order as above. the final vtx at the top is the only possible answer thus we apply dfs and confirm if it is. 
  
9. // we apply dfs from the given cell. Now while applying we need a marking to know which all cells have been visited, to not get stuck in infinite loop ofc, we use negation for this. now we iterate, and for each cell we check what we got in return from all 4 sides (we work in post after marking visited). So if we get the same number, or negate of the number then it means its surronded and is not the border, thus negate it again and make it normal. otherwise let it be begative so that we can traverse at the end and make all the negative elements of the new color.
      public static int[][] colorBorder(int[][] a, int row, int col, int color) {
        int origColor = a[row][col];
        dfsMod(a,row,col,origColor);
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                if(a[i][j]==-origColor){
                    a[i][j]=color;
                }
            }
        }
        return a;
    }
    public static boolean dfsMod(int[][] a, int i, int j, int k){
        if(i<0 || j<0 || i==a.length || j==a[i].length || (a[i][j]!=k && a[i][j]!=-k)){
            return false;
        }
        if(a[i][j]==-k){
            return true;
        }
        a[i][j]=-k;
        Boolean ans = true;
        Boolean a1 = dfsMod(a,i+1,j,k);
        Boolean a2 = dfsMod(a,i-1,j,k);
        Boolean a3 = dfsMod(a,i,j+1,k);
        Boolean a4 = dfsMod(a,i,j-1,k);
        ans = a1 && a2 && a3 && a4;
        if(ans == true)
            a[i][j] = k;
        return true;
    }

10. // for number of enclaves we apply dfs to all the 1s on the border. thus converting all the connected components for thos to 0 and we count the remaining 1s that are enclaves.
      public static int numEnclaves(int[][] a) {
        int n = a.length;
        int m = a[0].length;
        int ans = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i==0 || j==0 || i==n-1 || j==m-1){
                    if(a[i][j]==1)
                        dfsModified(a,i,j);
                }
            }
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(a[i][j]>0)
                    ans++;
            }
        }
        return ans;
    }

    public static void dfsModified(int[][] a, int i, int j){
        if(i<0 || j<0 || i>=a.length || j>=a[0].length || a[i][j]==0){
            return ;
        }
        a[i][j]=0;
        dfsModified(a,i+1,j);
        dfsModified(a,i-1,j);
        dfsModified(a,i,j+1);
        dfsModified(a,i,j-1);
    }

11. // to count distinct islands, we iterate from top left on all the islands and make a string from the island iteration. this string keeps track of l,r,u,d and returns as well. finally for every component/island, we put it in hashset.
      static int countDistinctIslands(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        HashSet<ArrayList<Character>> set = new HashSet<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    ArrayList<Character> a = new ArrayList<>();
                    dfs(grid, i, j, a);
                    set.add(a);
                }
            }
        }
        return set.size();
    }

    static void dfs(int[][] grid, int i, int j, ArrayList<Character> a){
        if(i<0 || j<0 || i>=grid.length || j>=grid[i].length || grid[i][j]==0){
            a.add('b');
            return;
        }
        grid[i][j]=0;
        a.add('u');
        dfs(grid, i-1, j, a);
        a.add('d');
        dfs(grid, i+1, j, a);
        a.add('l');
        dfs(grid, i, j-1, a);
        a.add('r');
        dfs(grid, i, j+1, a);
    }

---------------------------------------------- BFS RELATED -----------------------------------------------------
  
  
  
12. // simple bfs, level order. vis array still needed. marking vis while adding or while removing depends on the question. 
      public static void BFS(ArrayList<Integer>[] graph, int src){
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        boolean[] vis = new boolean[graph.length];
        vis[src]=true;
        while(q.size()>0){
            int s = q.size();
            while(s-->0){
                int curr = q.poll();
                System.out.print(curr+" ");
                for(int i : graph[curr]){
                    if(vis[i]==false){
                        vis[i]=true;
                        q.add(i);
                    }
                }
            }
            System.out.println();
        }
    }
    
13. // we mark visited while removing. 
      public static boolean isCyclic(ArrayList<Integer>[] graph, int src){
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        boolean[] vis = new boolean[graph.length];
        while(q.size()>0){
            int s = q.size();
            while(s-->0){
                int curr = q.poll();
                if(vis[curr]==true){
                    return true;
                }
                vis[curr]=true;
                for(int i : graph[curr]){
                    if(vis[i]==false){
                        q.add(i);
                    }
                }
            }
        }
        return false;
    }

14. // bipartite is if we color vtces in just 2 colors and every adjacent color is different. Basically no odd length cycles. So in continuation to above cycle detection, now we detect cycle and also check that colour that we already filled in a vtx is same as we were going to fill from other part of the cycle thus its an even 
      public static boolean isBipartite(ArrayList<Integer>[] graph, int src){
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        int[] vis = new int[graph.length];
        int x = 1;
        while(q.size()>0){
            int s = q.size();
            while(s-->0){
                int curr = q.poll();
                if(vis[curr]>0){
                    if(vis[curr]!=x){
                        return false;
                    }
                    continue;
                }
                vis[curr]=x;
                for(int i : graph[curr]){
                    if(vis[i]==0){
                        q.add(i);
                    }
                    else{
                        if(vis[i]==x){
                            return false;
                        }
                    }
                }
            }
            x=x==1?2:1;
        }
        return true;
    }

  15. // add all the virus cells to queue. Now apply bfs normally and keep track of radius/level/time. finally check if any cell is left, thus return -1;
    public int spreadVirus(int[][] a) {
        Queue<Pair> q = new LinkedList<>();
        for(int i = 0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                if(a[i][j]==2){
                    q.add(new Pair(i,j));
                }
            }
        }
        int time=-1;
        while(q.size()>0){
            int s = q.size();
            while(s-->0){
                Pair p = q.poll();
                int i =  p.getKey();
                int j =  p.getValue();
                if(i>0 && a[i-1][j]==1){
                    a[i-1][j]=2;
                    q.add(new Pair(i-1,j));
                }
                if(j>0 && a[i][j-1]==1){
                    a[i][j-1]=2;
                    q.add(new Pair(i,j-1));
                }
                if(j<a[i].length-1 && a[i][j+1]==1){
                    a[i][j+1]=2;
                    q.add(new Pair(i,j+1));
                }
                if(i<a.length-1 && a[i+1][j]==1){
                    a[i+1][j]=2;
                    q.add(new Pair(i+1,j));
                }
            }
            time++;
        }

        for(int i = 0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                if(a[i][j]==1){
                    return -1;
                }
            }
        }
        return time;
    }

16. // for max distance from 0, we put all the 1s in queue and apply bfs. keep track of level for each cell, and at the end, the last 0 is the answer.
  public int maxDistance(int[][] a) {
        int n = a.length;
        int m = a[0].length;
        Queue<Pair<Integer,Pair<Integer,Integer>>> q = new LinkedList();
       int level = 0;

       for(int i=0;i<n;i++){
           for(int j=0;j<m;j++){
               if(a[i][j]==1){
                   q.add(new Pair(0,new Pair(i,j)));
               }
           }
       }

       while(q.size()>0){
           Pair p = q.poll();
           int val = (Integer) p.getKey();
           Pair p1 = (Pair) p.getValue();
           int i = (Integer) p1.getKey();
           int j = (Integer) p1.getValue();

           level = Math.max(level,val);
           if(i!=0){
               if(a[i-1][j]==0){
                   a[i-1][j]=1;
                 q.add(new Pair(val+1,new Pair(i-1,j)));
               }
           }
           if(j!=0){
               if(a[i][j-1]==0){
                   a[i][j-1]=1;
                 q.add(new Pair(val+1,new Pair(i,j-1)));
               }
           }
           if(i!=n-1){
               if(a[i+1][j]==0){
                   a[i+1][j]=1;
                 q.add(new Pair(val+1,new Pair(i+1,j)));
               }
           }
           if(j!=m-1){
               if(a[i][j+1]==0){
                   a[i][j+1]=1;
                 q.add(new Pair(val+1,new Pair(i,j+1)));
               }
           }
       }

        return level==0?-1:level;
    }

17. // for bus routes, we need both hashmaps. one that tells each bus has what all stops and for each stop how many buses pass throught them. now here we apply modified bfs. in start we add all the stops that buses can reach who pass throught that stop and similarly for each stop that we pop from queue, we see all buses that pass through it and push all stops that aren't vis that those buses can go to. this is described as one level. Return the current level whenever the dest stop is found/popped.
   public int numBusesToDestination(int[][] r, int src, int d) {
        int n = r.length;
        HashMap<Integer,ArrayList<Integer>> map = new HashMap();
        boolean[] vis = new boolean[n];
        HashSet<Integer> set = new HashSet();
        for(int i=0;i<n;i++){
            for(int j=0;j<r[i].length;j++){
                if(map.containsKey(r[i][j])){
                    map.get(r[i][j]).add(i);
                }
                else{
                    map.put(r[i][j],new ArrayList());
                    map.get(r[i][j]).add(i);
                }
            }
        }

        Queue<Integer> q = new LinkedList();
        q.add(src);
        int level=-1;
        while(q.size()>0){
            level++;
            int s = q.size();
            for(int i=0;i<s;i++){
                int k = q.remove();
                if(k==d){
                    return level;
                }
                ArrayList<Integer> a = map.get(k);
                for(int j=0;j<a.size();j++){
                    int bus = a.get(j);
                    if(vis[bus]==false){
                        vis[bus]=true;
                        for(int i1=0;i1<r[bus].length;i1++){
                            if(!set.contains(r[bus][i1])){
                                set.add(r[bus][i1]);
                                q.add(r[bus][i1]);
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }
  
18. // 
  public int slidingPuzzle(int[][] board) {
        String target = "123450";
        String start = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                start += board[i][j];
            }
        }
        HashSet<String> visited = new HashSet<>();
        int[][] dirs = new int[][] { { 1, 3 }, { 0, 2, 4 },
                { 1, 5 }, { 0, 4 }, { 1, 3, 5 }, { 2, 4 } };
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        int res = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                if (cur.equals(target)) {
                    return res;
                }
                int zero = cur.indexOf('0');
                // swap if possible
                for (int dir : dirs[zero]) {
                    String next = swap(cur, zero, dir);
                    if (visited.contains(next)) {
                        continue;
                    }
                    visited.add(next);
                    queue.offer(next);

                }
            }
            res++;
        }
        return -1;
    }

    private String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }

19. // For 0-1 bfs, put reverse edges to all the edges but with weight 1. now apply bfs (no need for shortest path djikstra as the weight = 1). the shortest wt path will equal the shortest edge path. thus apply bfs and find answer.
  
20. // for MST, we apply prims, which is exactly like bfs. Only difference is that it uses PQ instead of Q. and it pushes with individual weight os each edge and not current total weight of graph. on each removal from queue, we check if the vtx popped is not already visited, if not vis then mark vis and add wt to curr MST. this is because for each vtx we will reach to it in the min weight first and if already marked vis then this wt is definitely higher. 
  static int spanningTree(int V, int E, int a[][]){
        ArrayList<ArrayList<Pair>> g= new ArrayList<ArrayList<Pair>>(V);
        for(int i=0;i<V;i++){
            g.add(new ArrayList<Pair>());
        }
        for(int i=0;i<a.length;i++){
            int v1 = a[i][0];
            int v2 = a[i][1];
            int w = a[i][2];
            g.get(v1).add(new Pair(v2,w));
            g.get(v2).add(new Pair(v1,w));
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>((a1,b1)->(a1.getValue()-b1.getValue()));

        pq.add(new Pair(0,0));
        int ans = 0;
        boolean[] vis = new boolean[V];
        while(pq.size()>0){
            Pair p = pq.poll();
            int v = p.getKey();
            int w = p.getValue();
            if(!vis[v]){
                vis[v]=true;
                ans+=w;
                for(Pair pair : g.get(v)){
                    if(!vis[pair.getKey()]){
                        pq.add(pair);
                    }
                }
            }
        }
        return ans;
    }
  
21. // similar is Djikstra but here we put the total running weight insted of individual since we want to reach the dest with min path travelled and we dont care about the MST.
    static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> g1, int S)
    {
        ArrayList<ArrayList<Pair>> g= new ArrayList<ArrayList<Pair>>(V);
        for(int i=0;i<V;i++){
            g.add(new ArrayList<Pair>());
        }
        for(int i=0;i<g1.size();i++){
            for(int j=0;j<g1.get(i).size();j++){
                int v1 = i;
                int v2 = g1.get(i).get(j).get(0);
                int w = g1.get(i).get(j).get(1);
                g.get(v1).add(new Pair(v2,w));
                g.get(v2).add(new Pair(v1,w));
            }
        }
        int[] ans = new int[V];
        PriorityQueue<Pair> pq = new PriorityQueue<>((a1,b1)->(a1.getValue()-b1.getValue()));
        pq.add(new Pair(S,0));
        while(pq.size()>0){
            Pair p = pq.poll();
            int v = p.getKey();
            int w = p.getValue();
            if(ans[v]==0){
                ans[v]=w;
                for(Pair pair : g.get(v)){
                    if(ans[pair.getKey()]==0 && pair.getKey()!=S){
                        pq.add(new Pair(pair.getKey(), pair.getValue()+w));
                    }
                }
            }
        }
        return ans;
    }
  
  
  
---------------------------------------------- TOPOLOGICAL SORT -----------------------------------------------------

    
22. // simply push the vtces in post order. This makes sure that the originating end is on top. then print in st order only.
  public static void topo(ArrayList<Integer>[] g, int src, Stack<Integer> s, boolean[] vis){
        for(int i : g[src]){
            if(vis[i]==false){
                vis[i]=true;
                topo(g,i,s,vis);
            }
        }
        s.push(src);
    }
  
23. // mark indegree of each vtx. then for all the vtces with indegree 0, push in queue. Start applying bfs and keep decreasing indegree of the nbrs bt one as we incur them, once for any nbr, the indeg becomes 0, push it in too (since all its dependencies are over) and add to topo sort 
  public static boolean kahnAlgo(ArrayList<Integer>[] g){
        int[] in = new int[g.length];
        for(int i=0;i<g.length;i++){
            for(int j : g[i]){
                in[j]++;
            }
        }
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<in.length;i++){
            if(in[i]==0){
                in[i]--;
                q.add(i);
            }
        }
        while(q.size()>0){
            int curr = q.poll();
            System.out.print(curr+" ");
            for(int i : g[curr]){
                in[i]--;
                if(in[i]==0){
                    q.add(i);
                }
            }
        }
        for(int i=0;i<in.length;i++){
            if(in[i]!=0){
                return false;
            }
        }
        return true;
    }

24. // for bellman ford / -ve weight cycle detection, we start with making path array. and put path of src vtx = 0 ( since no -ve cycle we assume ) and then we start iterating on all edges v-1 times and each time we check if path[u]+wt of edge < path[v], if so, then reduce.
  public static void main(String[] args) throws NumberFormatException, IOException {
	     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String[] st = br.readLine().split(" ");
	    
	    int n = Integer.parseInt(st[0]);
	    int m = Integer.parseInt(st[1]);
	    
	    int [][]arr = new int[m][3];
	    
	    for(int i = 0; i < m; i++){
	        st = br.readLine().split(" ");
	        arr[i][0] = Integer.parseInt(st[0]) - 1;
	        arr[i][1] = Integer.parseInt(st[1]) - 1;
	        arr[i][2] = Integer.parseInt(st[2]);
			}
			
			int[] path = new int[n];
			Arrays.fill(path,Integer.MAX_VALUE);
			path[0] = 0;
			
			for(int i = 0; i < n - 1; i++){
			    for(int j = 0; j < m; j++){
			        int u = arr[j][0];
			        int v = arr[j][1];
			        int wt = arr[j][2];
			        
			        if(path[u] == Integer.MAX_VALUE){
			            continue;
			        }
			        
			        if(path[u] + wt < path[v]){
			            path[v] = path[u] + wt;
			        }
			    }
			}
			
			for(int i = 1; i < n; i++){
			    if(path[i] != Integer.MAX_VALUE){
			        System.out.print(path[i] + " ");
			    } else {
			        System.out.println("not reachable");
			    }
			}
			
			System.out.println();
}


--------------------------------------------- DSU -------------------------------------------------

  
24. // find and union. compressions are to make the tree more broad than deep.
  public static boolean union(int x, int y){
      int lx = find(x);
      int ly = find(y);
      
      if(lx != ly){
          if(rank[lx] > rank[ly]){
              parent[ly] = lx;
          } else if(rank[lx] < rank[ly]){
              parent[lx] = ly;
          } else {
              parent[lx] = ly;
              rank[ly]++;
          }
          return true;
      } else {
          return false;
      }
  }
  
  public static int find(int x){
      if(parent[x] == x){
          return x;
      }
      int temp = find(parent[x]);
      parent[x] = temp;
      return temp;
  }
  
  
  
  
  
  
  
  
  
  

