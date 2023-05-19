-------------------------------- BASIC RECURSION ---------------------------------------

1. // basic printing, factorial, power
  public static void printInc(int n){
        if (n==0){
            return;
        }
        System.out.println(n);
        printInc(n-1);
    }

    public static void printDec(int n){
        if (n==0){
            return;
        }
        printDec(n-1);
        System.out.println(n);
    }

    public static void printIncDec(int n){
        if (n==0){
            return;
        }
        System.out.println(n);
        printIncDec(n-1);
        System.out.println(n);
    }

    public static void printZigZag(int n){
        if (n==0){
            return;
        }
        System.out.println("Pre"+n);
        printZigZag(n-1);
        System.out.println("In"+n);
        printZigZag(n-1);
        System.out.println("Post"+n);
    }

    public static int factorial(int n){
        if (n==0){
            return 1;
        }
        return n*factorial(n-1);
    }

    public static int power(int x, int n){
        if (n==0){
            return 1;
        }
        return x*power(x,n-1);
    }

2. // logarithmic power calculation.
  public static int powerLog(int x, int n){
        if (n==0){
            return 1;
        }
        int ans = power(x,n/2);
        if(n%2==0){
            return ans*ans;
        }
        else{
            return ans*ans*x;
        }
    }

3. // fibonacci. the optimized way is to calculate matrix to the power n-1. 
  public static int fib(int n){
        if(n<=1){
            return n;
        }
        return fib(n-1)+fib(n-2);
    }

    public static int fibLog(int n){
        int[][] F = new int[][]{{1,1},{0,1}};
        PowerMatrixLogComplexity(F,n-1);
        return F[0][0];
    }


4. // recursion in arrays. we need an idx
  public static void display(int[] a, int idx){
        if(idx == a.length){
            return;
        }
        System.out.println(a[idx]);
        display(a,idx+1);
    }

    public static int retMax(int[] a, int idx){
        if(idx == a.length){
            return 0;
        }
        return Math.max(a[idx],retMax(a,idx+1));
    }

    public static int firstOcc(int[] a, int idx, int x){
        if(idx == a.length){
            return -1;
        }
        if(a[idx]==x){
            return idx;
        }
        return firstOcc(a,idx,x);
    }

    public static int lastOcc(int[] a, int idx, int x){
        if(idx == a.length){
            return -1;
        }

        int ans = lastOcc(a,idx,x);
        if(ans !=-1)
            return ans;
        if(a[idx]==x){
            return idx;
        }
        return -1;
    }

    public static void allIdx(int[] a, int idx, int x, String ans){
        if(idx == a.length){
            System.out.println(ans);
        }
        if(a[idx]==x){
            allIdx(a,idx,x,ans+a[idx]+" ");
        }
        else{
            allIdx(a,idx,x,ans);
        }
    }

5. // subseqces of a string
    public static void getSubSeq(String s, int idx, String asf){
        if(idx == s.length()){
            System.out.println(asf);
            return;
        }
        getSubSeq(s, idx+1, asf+s.charAt(idx));
        getSubSeq(s, idx+1, asf);
    }

6. // TOH - we first move n-1 tiles to helper using faith in recursion since its a smaller problem and then we manually move the last tile to the dest and then again using recursion we move the n-1 tiles from helper to dest. base case is when only 1 tile is there, move it. or if no tile is there, return.
      public static void toh(int a, int b, int c, int n){
        if(n==1){
            System.out.println("move tile "+n+" from "+a+" to "+b);
            return;
        }
        toh(a,c,b,n-1);
        System.out.println("move tile "+n+" from "+a+" to "+b);
        toh(c,b,a,n-1);
    }

7. // to get gcd, we use euclid's theorem. 
  private int gcd(int a, int b) {
		  if (b == 0) return a;
		  return gcd(b, a % b);
    }

8. // not using return type ( building while going up )
    static String[] keypad = new String[]{"  ","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz","."};
    public static void keypadComb(String s, int idx, String asf){
        if(idx==s.length()){
            System.out.println(asf);
            return;
        }
        String temp = keypad[Integer.parseInt(s.charAt(idx)+"")];
        for(int i=0;i<temp.length();i++){
            keypadComb(s, idx+1, asf+temp.charAt(i));
        }
    }

9. // k steps allowed.
  public static int climbingStairs(int n, int k){
        if(n<0){
            return 0;
        }
        if(n==0){
            return 1;
        }
        int ans = 0;
        for(int i=1;i<=k;i++){
            ans += climbingStairs(n-i,k);
        }
        return ans;
    }

10. // building while returning. 
      public static ArrayList<String> mazeJump(int n, int m, int i, int j){
        if(i==n-1 && j==m-1){
            ArrayList<String> a = new ArrayList<>();
            a.add(" ");
            return a;
        }
        ArrayList<String> ans = new ArrayList<>();
        for(int k = 1; k<n-i; k++){
            ArrayList<String> temp = mazeJump(n,m,i+k,j);
            for(String s : temp){
                ans.add("v"+k+s);
            }
        }
        for(int k = 1; k<m-j; k++){
            ArrayList<String> temp = mazeJump(n,m,i,j+k);
            for(String s : temp){
                ans.add("h"+k+s);
            }
        }
        return ans;
    }

11. // building on the way back/down
   public static ArrayList<String> decode (String s, int idx){
        if(idx == s.length()){
            ArrayList<String> a = new ArrayList<>();
            a.add(" ");
            return a;
        }
        ArrayList<String> ans = new ArrayList<>();
        if(idx != s.length()-1 &&  Integer.parseInt(s.substring(idx,idx+2))<27){
            ArrayList<String> temp = decode(s, idx+2);
            for(String s1 : temp){
                ans.add((char)(Integer.parseInt(s.substring(idx,idx+2))+'A'-1)+""+s1);
            }
        }
        ArrayList<String> temp = decode(s, idx+1);
        for(String s1 : temp){
            ans.add(""+(char)(Integer.parseInt(s.substring(idx,idx+1))+'A'-1)+""+s1);
        }
        return ans;
    }

12. // rat in maze
      public static int ratInMaze(int[][] a, int i, int j){
        if(i<0 || j<0 || i>=a.length || j>=a[0].length || a[i][j]==-1){
            return 0;
        }
        if(i==a.length-1 && j==a[i].length-1){
            return 1;
        }

        a[i][j]=-1;
        int ans = ratInMaze(a,i+1,j)+ratInMaze(a,i,j+1)+ratInMaze(a,i-1,j)+ratInMaze(a,i,j-1);
        a[i][j]=-1;

        return ans;
    }

13. // include exclude tratergy
      public static ArrayList<String> subsetSum(int[] a, int i, int t){
        if(t==0){
            ArrayList<String> aa = new ArrayList<>();
            aa.add("");
            return aa;
        }
        if(i==a.length || t<0){
            return new ArrayList<String>();
        }
        ArrayList<String> ans = new ArrayList<>();

        ArrayList<String> a2 = subsetSum(a,i+1,t-a[i]);
        for(String s : a2){
            ans.add(a[i]+s);
        }
        ArrayList<String> a1 = subsetSum(a,i+1,t);
        for(String s : a1){
            ans.add(s);
        }
        return ans;
    }

14. // 





























