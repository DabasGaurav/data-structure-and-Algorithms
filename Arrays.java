1. // Pretty simple. Just find max and min and return max-min.
    public static int spanOfArray(int[] a, int n){
        int max = a[0];
        int min = a[0];
        for(int i=0;i<n;i++){
            max = Math.max(a[i],max);
            min = Math.min(a[i],min);
        }
        return max- min;
    }

2. // since array is unsorted, can't use binary search, thus linear search.
    public static int findElement(int[] a, int n, int k){
        for(int i=0;i<n;i++){
            if(a[i]==k)
                return i;
        }
        return -1;
    }

3. // For any pattern question, see the rows and cols, here rows = highest number in array, col = total elements in array. A spot will have star if the jth element, it is greater than or equal to i.
	public static void barChart(int[] a, int n){
        int max = a[0];
        for(int i=0;i<n;i++){
            max = Math.max(a[i],max);
        }
        for(int j=max; j>=1;j--) {
            for (int i = 0; i < n; i++) {
                if (a[i] >= j) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
        }
    }

4. // simply iterate right to left and keep a carry. At the end if carry is 1, append a 1 in starting, otherwise we already have the answer.
	public static void sumOfTwoArrays(int[] a, int[] b){
        int na = a.length;
        int nb = b.length;
        int[] c = new int[Math.max(na,nb)+1];
        int i=na-1,j=nb-1,k=c.length-1;

        int carry=0;
        while(i>=0 && j>=0){
            int sum = a[i]+b[j]+carry;
            c[k] = (sum)%10;
            carry = sum/10;
            k--;
            i--;
            j--;
        }
        while(i>=0){
            int sum = a[i]+carry;
            c[k] = (sum)%10;
            carry = sum/10;
            k--;
            i--;
        }
        while(j>=0){
            int sum = b[j]+carry;
            c[k] = (sum)%10;
            carry = sum/10;
            k--;
            j--;
        }
        if(carry==1){
            c[k]=1;
        }
    }

5. // simple way, other way is to use collections.reverse
	public static void reverseArray(int a[], int si, int ei){
        int i = si;
        int j = ei;
        while(i<j){
            int temp = a[i];
            a[i]=a[j];
            a[j]=temp;
            i++;
            j--;
        }
    }

6. // not intuitive, you kind of have to realise that this solution works. 
	public static void rotateArray(int a[], int k){
        reverseArray(a,0,k);
        reverseArray(a,k+1,a.length-1);
        reverseArray(a,0,a.length-1);
    }

7. // Binary search is important. this is simple binary search. next 2 questions are even more useful
	public static int binarySearch(int[] a, int n, int k){
        int i = 0;
        int j = n-1;
        while(i<=j){
            int mid = (i+j)/2;
            if(a[mid]==k){
                return mid;
            }
            else if(a[mid]>k){
                j=mid-1;
            }
            else{
                i=mid+1;
            }
        }
        return -1;
    }

8. // Here we need to find just greater or just smaller value than the given number, if it is not found. So here, after we are done searching for a number, we're always at the condition where i>j. thus we break outta loop. so before this, i=j, here mid is also i/j, thus if this curr number is smaller than k, then i will go forward n cause the loop to break, otherwise j will move back and cause to break. So since j<i , j will be at floor and i will be at ceil at ending.
	public static void ceilAndFloor(int[] a, int n, int k){
        int i = 0;
        int j = n-1;
        int ceil;
        int floor;
        while(i<=j){
            int mid = (i+j)/2;
			if(a[mid]==k){
				ceil = a[mid];
				floor = a[mid];
				return;
			}
            else if(a[mid]>k){
                j=mid-1;
            }
            else{
                i=mid+1;
            }
        }
        floor = a[j];
        ceil = a[i];
    }

9. // for first occurence it is done. similar can be done for last. So basically at every point we find the given number ( at mid ) we store it in possible first occ of that number because its possible that our right pointer will go mid -1 now and mid was the first occurrence.
	public static void firstAndLastOcc(int[] a, int n, int k){
        int i = 0;
        int j = n-1;
        int first;

        while(i<=j){
            int mid = (i+j)/2;
            if(a[mid]==k){
                first = mid;
                j=mid-1;
            }
            else if(a[mid]>k){
                j=mid-1;
            }
            else{
                i=mid+1;
            }
        }
    }


-------------------------------- TWO POINTER TECHNIQUE -----------------------------------

	
10. // Reverse vowels. simple approach
	public String reverseVowels(String s) {
        int i=0;
        int j=s.length()-1;
        char[] a = s.toCharArray();
        HashSet<Character> set = new HashSet();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');
        set.add('A');
        set.add('E');
        set.add('I');
        set.add('O');
        set.add('U');
        while(i<j){
            while(i<j && !set.contains(s.charAt(i))){
                i++;
            }
            while(i<j && !set.contains(s.charAt(j))){
                j--;
            }
            char temp = a[i];
            a[i]=a[j];
            a[j]=temp;

            i++;
            j--;
        }
        return String.valueOf(a);
    }

11. // a word is palindrome if 1st char = last char and so on.
	public boolean isPalindrome(String s) {
        s = s.toLowerCase();

        int i = 0;
        int j = s.length()-1;
        while(i<j){
            if((s.charAt(i)<'a' || s.charAt(i)>'z' )&& (s.charAt(i)<'0' || s.charAt(i)>'9' )){
                i++;
            }
            else if((s.charAt(j)<'a' || s.charAt(j)>'z' )&& (s.charAt(j)<'0' || s.charAt(j)>'9' )){
                j--;
            }
            else if(s.charAt(i)!=s.charAt(j) ){
                System.out.println(s.charAt(i)+" "+s.charAt(j));
                return false;
            }
            else{
                i++;
                j--;
            }
        }
        return true;
    }

12. // apply same logic as above. but whenever it fails, give it one chance, remove ith char/jth char one by one and check if now its a palindrome.
	public boolean validPalindrome(String s) {
        int i = 0;
        int j = s.length()-1;

        while(i<j){
            if(s.charAt(i)!=s.charAt(j)){
                return isPalindrome(s.substring(0,i)+s.substring(i+1))||check(s.substring(0,j)+s.substring(j+1));
            }
            i++;
            j--;
        }
        return true;
    }

13. // every word of smaller string must be present in the longer string and that too in the same order. 
	public boolean isSubsequence(String a, String b) {
        int i=0,j=0;
        while(i<a.length() && j<b.length()){
            if(a.charAt(i)!=b.charAt(j)){
                j++;
            }
            else{
                i++;
                j++;
            }
        }
        return i==a.length();
    }

14. // faulty keyboard
	public static boolean longPressed(String s1, String s2){
        int i1=0;
        int i2=0;
        while(i1<s1.length() && i2<s2.length()){
            if(s1.charAt(i1)==s2.charAt(i2)){
                i1++;
                i2++;
            }
            else if(i1!=0){
                if(s1.charAt(i1-1)==s2.charAt(i2)){
                    i2++;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        while(i2<s2.length()){
            if(s1.charAt(i1-1)==s2.charAt(i2)){
                i2++;
            }
            else{
                return false;
            }
        }
        return i1==s1.length() && i2==s2.length();
    }

15. // fairly simple
	public int matchPlayersAndTrainers(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        int n1 = a.length;
        int n2 = b.length;
        int i = 0;
        int j = 0;
        int c = 0;
        while(i<n1 && j<n2){
            if(a[i]<=b[j]){
                i++;
                j++;
                c++;
            }
            else{
                j++;
            }
        }
        return c;
    }

16. // max no. is either at first or last. since for squares we only need absolute
	public int[] sortedSquares(int[] a) {
        int n = a.length;
        int[] ans = new int[n];
        int i = 0;
        int j = n-1;
        int k = n-1;
        while(i<=j){
            if(Math.abs(a[i])>Math.abs(a[j])){
                ans[k]=a[i]*a[i];
                i++;
            }
            else{
                ans[k]=a[j]*a[j];
                j--;
            }
            k--;
        }
        return ans;
    }

17. // area = length x breadth. thus try to maximise both 
	public int maxArea(int[] a) {
        int i =0;
        int j = a.length-1;
        int ans = 0;
        while(i<j){
            ans = Math.max(ans,Math.min(a[i],a[j])*(j-i));
            if(a[i]<a[j]){
                i++;
            }
            else{
                j--;
            }
        }
        return ans;
    }

18. // remove whenever you find similar 
	public int minimumLength(String s) {
        int i=0;
        int n=s.length();
        int j=n-1;
        while(i<j){
            if(s.charAt(i)==s.charAt(j)){
                i++;
                j--;
                while(i<=j && s.charAt(i)==s.charAt(i-1)){ i++; }
                while(i<=j && s.charAt(j)==s.charAt(j+1)){ j--; }
                if(i>j){
                    return 0;
                }
            }
            else{
                break;
            }
        }
        return i<=j?j-i+1:0;
    }

19. // if we encounter a no. > right, then we start over.  If we encounter a no. < left then we only append it to our previous numbers subarrays. otherwise we append it as well as make a new subarray starting from it.
	public int numSubarrayBoundedMax(int[] a, int l, int r) {
        int n = a.length;
        int prev = 0;
        int count = 0;
        int si = 0;
        int ei = 0;
        while(ei<n){
            if(a[ei]>r){
                prev = 0;

                si=ei+1;
            }
            else{
                if(a[ei]>=l){
                    prev = ei-si+1;
                    count+=prev;
                }
                else{
                    count+=prev;
                }
            }
            // System.out.println(count);
            ei++;
        }
        return count;
    }

20. // put in heap the first from all. then keep on popping and pushing the popped array's next and keeping track of max in the PQ right now. compare answer with max - pq.top (min) everytime.
	public int[] smallestRange(List<List<Integer>> a) {
        int k = a.size();
        int[] ans = new int[2];
        ans[1]=50000000;
        PriorityQueue<Pair<Integer,Pair<Integer,Integer>>> pq= new PriorityQueue<Pair<Integer,Pair<Integer,Integer>>>((a1, b1)->(a1.getKey()-b1.getKey()));
        int max = 0;
        for(int i=0;i<k;i++){
            max = Math.max(a.get(i).get(0),max);
            pq.add(new Pair(a.get(i).get(0),new Pair(0,i)));
        }
        while(pq.size()==k){
            Pair p = pq.poll();
            int val = (int) p.getKey();
            int idx = (int) ((Pair)p.getValue()).getKey();
            int l = (int) ((Pair)p.getValue()).getValue();

            if(ans[1]-ans[0]>max-val){
                ans[0]=val;
                ans[1]=max;
            }

            if(a.get(l).size()-1>idx){
                pq.add(new Pair(a.get(l).get(idx+1),new Pair(idx+1,l)));
                max=Math.max(max,a.get(l).get(idx+1));
            }
        }
        return ans;
    }


------------------------------------ MERGE INTERVAL BASED --------------------------------------------

	
21. // Sort in the basis of start time. add the first pair in answer. now for every next pair, check if it can be merged. if merged then ok. if not then add to answer, and now check for its further intervals with the newly merged. After merging start will be of the left interval since already sorted and end will be the larger of both since theres no sorting for it.
	public int[][] merge(int[][] a) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
        Arrays.sort(a,(int[] a1, int[] b1)->(a1[0]-b1[0]));
        int cs = a[0][0];
        int ce = a[0][1];
        for(int i=1;i<a.length;i++){
            if(a[i][0]<=ce){
                ce = Math.max(ce,a[i][1]);
            }
            else{
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(cs);
                temp.add(ce);
                ans.add(temp);
                cs = a[i][0];
                ce = a[i][1];
            }
        }
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(cs);
        temp.add(ce);
        ans.add(temp);
        int[][] ret = new int[ans.size()][2];
        for(int i=0;i<ans.size();i++){
            ret[i][0]=ans.get(i).get(0);
            ret[i][1]=ans.get(i).get(1);
        }
        return ret;
    }

22. // sort all arrivals and departures. Now iterater based on time. whenever an arrival comes, add noPlat++, and for every departure do --. Max at any time is the answer
	public int findPlatform(int a[], int d[], int n)
    {
        Arrays.sort(a);
        Arrays.sort(d);
        int i=0;
        int j=0;
        int curr = 0;
        int ans = 0;
        while(i<n){
            if(a[i]<=d[j]){
                curr++;
                i++;
            }
            else{
                j++;
                curr--;
            }
            ans = Math.max(ans,curr);
        }
        return ans;
    }

23. // same as above. only if noOfPlatform is 1 then return true else false
	
24. // same as above

25. // sort both by starting time. now for each pair, start time is max of them and end is min of them for the interval. Incase they are not intersecting this calculated start time will be > end time thus we'll know they are exclusive. otherwise add teh intersection as calculated. then increment the one who's end time is lower.
	public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> res = new ArrayList();
        for (int i = 0, j = 0; i < A.length && j < B.length; ) {
            int start = Math.max(A[i][0], B[j][0]);
            int end = Math.min(A[i][1], B[j][1]);
            if (start <= end)
                res.add(new int[]{start, end});
            if (A[i][1] < B[j][1])
                ++i;
            else
                ++j;
        }
        return res.toArray(new int[0][]);
    }

26. // kinda like range addition problem.
	public boolean carPooling(int[][] a, int c) {
         int[] b = new int[1001];
        for(int i=0;i<a.length;i++){
            b[a[i][1]]+=a[i][0];
            b[a[i][2]]+=-a[i][0];
        }
        int sum =0;
        for(int i=0;i<1001;i++){
            sum+=b[i];
            if(sum>c){
                return false;
            }
        }
        return true;
    }


--------------------------------- PREFIX ARRAY TECHNIQUES ------------------------------------
	
27. // water that any element can store above it equal to the min of its left largest height and right largest height minus the height of its own. So calculate left and right largest prefix arrays at once.
	public int trap(int[] a) {
        int n = a.length;
        int[] left = new int[n];
        int[] right = new int[n];
        left[0]=a[0];
        right[n-1]=a[n-1];
        for(int i=1;i<n;i++){
            left[i]=Math.max(left[i-1],a[i]);
        }
        for(int i=n-2;i>=0;i--){
            right[i]=Math.max(right[i+1],a[i]);
        }
        int ans = 0;
        for(int i=0;i<n;i++){
            ans+=(Math.min(left[i],right[i])-a[i]);
        }
        return ans;
    }

28. // make a prefix product array for right side of every element and calculate prefix product from left as we travel and work
	public int[] productExceptSelf(int[] a) {
        int n = a.length;
        int[] ans = new int[n];
        ans[0]=a[0];
        for(int i=1;i<n;i++){
            ans[i]=ans[i-1]*a[i];
        }
        int asf = 1;
        for(int i=n-1;i>0;i--){
            ans[i]=ans[i-1]*asf;
            asf*=a[i];
        }
        ans[0]=asf;
        return ans;
    }

29. // we calculate the odd sum and even sum first. now we iterate and keep on subtracting from the sum the elements that we have iterated thus we'll at every point of time have right odd and even sum. and we're gonna calculate the left odd and even sum as we travel. now just know thart for any element removal the right even will become odd and vica versa.
	 public int waysToMakeFair(int[] A) {
        int res = 0, n = A.length, left[] = new int[2], right[] = new int[2];
        for (int i = 0; i < n; i++)
            right[i%2] += A[i]; 
        for (int i = 0; i < n; i++) {
            right[i%2] -= A[i];
            if (left[0]+right[1] == left[1]+right[0]) res++;
            left[i%2] += A[i];
        }
        return res;
    }

30. // the max product subarray will be either from start or end or both. also considering any 0 will be fresh a start only. 
	public int maxProduct(int[] a) {
        int ans = Integer.MIN_VALUE;
        int c = 1;
        for(int i=0;i<a.length;i++){
            c=c*a[i];
            ans = Math.max(c,ans);
            if(a[i]==0){
                c=1;
            }
        }
        c=1;
        for(int i=a.length-1;i>=0;i--){
            c=c*a[i];
            ans = Math.max(c,ans);
            if(a[i]==0){
                c=1;
            }
        }
        return ans;
    }


----------------------------------- PARTITIONING ARRAY TECHNIQUES -----------------------------------


31. // to find left part of shortest unsorted subarray iterate from right and find the last elemnt that has a smaller element to the right of it. Similarly do for right part and return the subarray.
	public int findUnsortedSubarray(int[] a) {
        int n = a.length;
        int lm = a[0];
        int rm = a[n-1];
        int ri = -1;
        int li = 0;

        for(int i=n-2;i>=0;i--){
            rm = Math.min(rm,a[i]);
            if(rm<a[i]){
                li = i;
            }
        }
        for(int i=1;i<n;i++){
            lm = Math.max(a[i],lm);
            if(lm>a[i]){
                ri = i;
            }
        }
        return ri-li+1;
    }

32. // we store last occurence of every alphabet in hashmap and iterate. keep a variable to store the index where its possible to partition. now iterate and keep on updating this variable with the max hashmap value of current alphabets. At any point if we get that current index is equal to variable then we know we can partition here for sure as all the alphabets we incurred till now do not have their any occurrence past current index.
	public List<Integer> partitionLabels(String s) {
        List<Integer> l = new ArrayList();
        HashMap<Character, Integer> map = new HashMap();
        int n = s.length();
        for(int i=0;i<s.length();i++){
            map.put(s.charAt(i), i);
        }
        int lb = -1;
        int ub = map.get(s.charAt(0));
        for(int i=1;i<n;i++){
            if(i>ub){
                l.add( ub - lb);
                lb = ub;
                ub = map.get(s.charAt(i));
            }
            else{
                ub = Math.max(map.get(s.charAt(i)),ub);
            }
        }
        l.add(ub-lb);
        return l;
    }


33. // explanation in next question. generic.
	public int maxChunksToSorted(int[] a) {
        int n = a.length;
        int max = 0;
        int ans = 0;
        for(int i=0;i<n;i++){
            max = Math.max(max, a[i]);
            if(max == i){
                ans++;
            }
        }
        return ans;
    }


34. // we can make a chunk when we find that the elements till now have all the greater elements to the right. that means the largest element till now should be smaller than the minimum element of the right subarray. 
	public int maxChunksToSorted2(int[] a) {
        int n = a.length;
        int[] left = new int[n];
        left[n-1]=a[n-1];
        for(int i=n-2;i>=0;i--){
            left[i]=Math.min(left[i+1],a[i]);
        }
        int ans = 0;
        int max = 0;
        for(int i=0;i<n-1;i++){
            max = Math.max(max,a[i]);
            if(max<=left[i+1]){
                ans++;
            }
        }
        ans++;
        return ans;
    }


35. // to find the first chunk basically. We can mark every element as possible answer and update whenever we find that it cant be the answer. 
	public int partitionDisjoint(int[] a) {
        int mx = a[0];
        int nmx = a[0];
        int n = a.length;
        int idx = 0;
        for(int i=0;i<n;i++){
            mx = Math.max(mx,a[i]);
            if(a[i]<nmx){
                nmx = mx;
                idx = i;
            }
        }
        return idx+1;
    }


------------------------------------- MOORE'S VOTING ALGORITHM ---------------------------------------


36. // we make pairs of elements that cancel each other. the algo says that if there's an element that occurs>n/2 times then it must be left out after this pairing is done. thus we check the left out element if its the answer or not
	public int majorityElement(int[] a) {
        int n = a.length;
        int c = 0;
        int ans = 0;

        for(int i=0;i<n;i++){
            if(c==0){
                ans = a[i];
                c++;
            }
            else{
                if(a[i]==ans){
                    c++;
                }
                else{
                    c--;
                }
            }
        }
        c=0;
        for(int i=0;i<n;i++){
            if(a[i]==ans){
                c++;
            }
        }
        if(c>n/2){
            return ans;
        }
        return -1;
    }

37. // now we make triplets instead of pairs. rest is same
	    public List<Integer> majorityElement2(int[] a) {
        int n = a.length;
        List<Integer> l = new ArrayList();
        int c1 = 0;
        int ans1 = 0;
        int c2 = 0;
        int ans2 = 0;
        for(int i=0;i<n;i++){
            if(a[i]==ans1){
                c1++;
            }
            else if(a[i]==ans2){
                c2++;
            }
            else{
                if(c1==0){
                    ans1=a[i];
                    c1++;
                }
                else if(c2==0){
                    ans2=a[i];
                    c2++;
                }
                else{
                    c1--;
                    c2--;
                }
            }
        }
        c1=0, c2=0;
        for(int i=0;i<n;i++){
            if(a[i]==ans1){
                c1++;
            }
            else if(a[i]==ans2){
                c2++;
            }
        }
        if(c1>n/3){
            l.add(ans1);
        }
        if(c2>n/3){
            l.add(ans2);
        }
        return l;
    }


-------------------------------------- K SUM/ DIFFERENCE -------------------------------------------

	
38. // simple two pointer technique in sorted array
	public int[] twoSum(int[] a, int k) {
        int n = a.length;
        int i = 0;
        int j = n-1;
        int[] ans = new int[2];
        while(i<j){
            if(a[i]+a[j]==k){
                ans[0]=i+1;
                ans[1]=j+1;
                return ans;
            }
            else if(a[i]+a[j]<k){
                i++;
            }
            else{
                j--;
            }
        }
        return null;
    }

39. // try to pair, if weight > capacity, then the heavier person must go alone. otherwise pair them and send.
	public int numRescueBoats(int[] a, int k) {
        Arrays.sort(a);
        int n = a.length;
        int i = 0;
        int j= n-1;
        int count=0;
        while(i<j){
            if(a[i]+a[j]<=k){
                count++;
                i++;
                j--;
            }
            else{
                j--;
                count++;
            }
        }

        return i==j?count+1:count;
    }

40. // for k sum, iterate over array, and for each element send rest of array for k-1 sum recursively. and base case is 2 sum only.
	public List<List<Integer>> fourSum(int[] a, int target) {
        Arrays.sort(a);
        return xx(a,0,a.length-1,target,4);
    }

    public List<List<Integer>> xx(int[] a, int i, int j, long k, int n){
        List<List<Integer>> ans = new ArrayList();
        if(n==2){
            while(i<j){
                if(a[i]+a[j]==k){
                    ans.add(new ArrayList<>(Arrays.asList(a[i],a[j])));
                    i++;
                    j--;
                    while(i<j && a[i]==a[i-1]){
                        i++;
                    }
                }
                else if(a[i]+a[j]>k){
                    j--;
                }
                else{
                    i++;
                }
            }
        }
        else{
            for(int i1=i;i1<=a.length-n;i1++){
                if(i1!=i && a[i1-1]==a[i1]){
                    continue;
                }
                List<List<Integer>> ans1 = xx(a,i1+1,a.length-1,k-a[i1],n-1);
                for(int j1=0;j1<ans1.size();j1++){
                    ans1.get(j1).add(0,a[i1]);
                    ans.add(ans1.get(j1));
                }
            }   
        }
        return ans;
    }


-------------------------------------- SORTING BY PARITY ---------------------------------------


41. // create 3 ptrs , 0ptr which will indicate the ending of subarray in start with all 0s, 2ptr which denotes the start of sorted subarray in the end with all 2s and lastly, currPtr, the one using which we are iterating and working. if currPtr 0, then swap with 0ptr, if 2, then with 2ptr, otherwise just skip.
	public static void sort012(int a[], int n)
    {
        int i0 = 0;
        int i2 = n-1;
        int i=0;
        while(i<=i2){
            if(a[i]==0){
                int temp = a[i0];
                a[i0]=a[i];
                a[i]=temp;
                i0++;
                i++;
            }
            else if(a[i]==2){
                int temp = a[i2];
                a[i2]=a[i];
                a[i]=temp;
                i2--;
            }
            else{
                i++;
            }
        }
    }


---------------------------------------- MARKING IN ARRAY --------------------------------------------

42. // store flag if we have 1 or not. now make all nos except 0-n as 1. now iterate and for each element and mark that no's corresponding index as negative. Now finally iterate and find first positivve number and that index is the answer.
	public int firstMissingPositive(int[] a) {
        int n = a.length;
        boolean flag = false;
        for(int i=0;i<n;i++){
            if(a[i]==1){
                flag = true;
            }
            if(a[i]<=0 || a[i]>n){
                a[i]=1;
            }
        }
        if(!flag){ return 1; }
        for(int i=0;i<n;i++){
            if(a[i]>0){
                if(a[a[i]-1]>0)
                a[a[i]-1]*=-1;
            }
            else{
                if(a[-a[i]-1]>0){
                    a[-a[i]-1]*=-1;
                }
            }
        }
        
        for(int i=0;i<n;i++){
            if(a[i]>0){
                return i+1;
            }
        }
        
        return n+1;
    }
	
43. // for first duplicate, the same above logic, just see if already negative, then its already occurred so return it.
	

------------------------------------------- SIEVE ------------------------------------------------
	
44. // make boolean array till n. Now iterate till root(n) and for every no. that has value false, mark all its multiples in the array as true. Now all the false ones are prime and true ones are composite.
	public int countPrimes(int n) {
        boolean[] check = new boolean[n+1];
        for(int i=2;i*i<=n;i++){
            if(check[i]==false){
                for(int j=i*2;j<=n;j+=i){
                    check[j]=true;
                }
            }
        }
        int count=0;
        for(int i=2;i<n;i++){
            if(check[i]==false)
            count++;
        }
        return count;
    }
	
	
	
---------------------------------------- MISCELLANEOUS --------------------------------------------
	
	
45. // First take +i jumps till we reach => the given number. After that, if >, then see the difference bw curr n given number. if even, you should have taken -diff/2 jump instead of diff/2. this would have taken you diff steps back. Thus you'd reach given number in that case. incase diff is odd, then we have to take 1 or 2 steps till the new diff is even and again the same logic.
	public static int minJumps(int x) {
        int ans = 0;
        int n =1;
        int i = 0;
        while(true){
            if(i==x){
                return ans;
            }
            else{
                if(i>x){
                    int diff = i-x;
                    if(diff%2==0){
                        return ans;
                    }
                    else{
                        if((n+i-x)%2==0){
                            return ans+1;
                        }
                        else{
                            return ans+2;
                        }
                    }
                }
            }
            ans++;
            i+=n;
            n++;
        }
    }

46. // Need to modify the first least significant digit that can be increased. Iterate from right to left till the sequence is increasing. when first dip is found, thats where the increment is to be done. swap this dip number to just greater to the right number. then sort the right part.
	public int nextGreaterElement(int num) {
        char[] s = Integer.toString(num).toCharArray();
        int n = s.length;
        int i = n-1;
        
        while(i>0 && s[i]<=s[i-1]){
                i--;
        }
        i--;
        int idx = i+1;
        if(i>=0){
            
            for(int j=i+1;j<n;j++){
                if(s[j]>s[i] && s[j]<s[idx]){
                    idx = j;
                }
            }

            char temp = s[idx];
            s[idx]=s[i];
            s[i]=temp;

            Arrays.sort(s,i+1,n);
        }
        else{
            return -1;
        }
        int ans = -1;
        try{
            ans = Integer.parseInt(new String(s));
        }
        catch(Exception e){
            return -1;
        }
        return ans;
    }

47. // find 1st 2nd 3rd +ve no and 1st 2nd -ve number. 
	public int maximumProduct(int[] nums) {
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE, min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n > max2) {
                max3 = max2;
                max2 = n;
            } else if (n > max3) {
                max3 = n;
            }

            if (n < min1) {
                min2 = min1;
                min1 = n;
            } else if (n < min2) {
                min2 = n;
            }
        }
        return Math.max(max1*max2*max3, max1*min1*min2);
    }

48. // sort based on postion. Now iterate right to left 
	public int carFleet(int target, int[] pos, int[] speed) {
        int N = pos.length, res = 0;
        double[][] cars = new double[N][2];
        for (int i = 0; i < N; ++i)
            cars[i] = new double[] {pos[i], (double)(target - pos[i]) / speed[i]};
        Arrays.sort(cars, (a, b) -> Double.compare(a[0], b[0]));
        double cur = 0;
        for (int i = N - 1; i >= 0 ; --i) {
            if (cars[i][1] > cur) {
                cur = cars[i][1];
                res++;
            }
        }
        return res;
    }


