import java.io.IOException;

class UnionFind{
    private int[] father = null;
    private int count;
    public UnionFind(int n){
        father = new int[n];
        for(int i = 0; i < n; i ++){
            father[i] = i;
        }
    }

    private int find(int x){
        if(father[x] == x)
            return x;
        return father[x] = find(father[x]);
    }

    public void connect(int x, int y){
        int root_x = find(x);
        int root_y = find(y);
        if(root_x != root_y){
            father[root_x] = root_y;
            count --;
        }
    }

    public int query(){
        return count;
    }

    public void set_count(int total){
        count = total;
    }
}

public class Solution {
    int n, m;
    public int friendCircles(String[] friends){
        if(friends == null || friends.length == 0 || friends[0] == null || friends[0].length() == 0)
            return 0;
        n = friends.length;
        m = friends[0].length();
        UnionFind uf = new UnionFind(n*m);
        int total = 0;
        for(int i = 0; i < n; i ++){
            for(int j = 0; j < m; j ++){
                if(friends[i].charAt(j) == 'Y'){
                    total ++;  //一个点不能connect,只能计算total这里
                }
            }
        }
        uf.set_count(total);

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        for(int i = 0; i < n; i ++){
            for(int j = 0; j < m; j ++){
                if(friends[i].charAt(j) == 'Y'){
                    for(int k = 0; k < 4; k ++){
                        int nx = i + dx[k];
                        int ny = j + dy[k];
                        if(isBound(friends, nx, ny)){
                            uf.connect(j + m * i, ny + m * nx);
                        }
                    }
                }

            }
        }

        return uf.query();
    }

    private boolean isBound(String[] friends, int x, int y){
        if(x < 0 || x >= n || y < 0 || y >= m || friends[x].charAt(y) == 'N')
            return false;
        return true;
    }

    public static void main(String[] args) throws IOException {
        Solution s  = new Solution();
        //String[] src = {"YYNN", "YYYN", "NYYN", "NNNY"};
        String[] src ={};
        int ret = s.friendCircles(src);
        System.out.println(ret);
    }
}
/******************************************************updated***********************************************************************************************/

import java.io.IOException;

class UnionFind{
    private int[] father = null;
    private int count;
    public UnionFind(int n){
        father = new int[n];
        for(int i = 0; i < n; i ++){
            father[i] = i;
        }
    }

    private int find(int x){
        if(father[x] == x)
            return x;
        return father[x] = find(father[x]);
    }

    public void connect(int x, int y){
        int root_x = find(x);
        int root_y = find(y);
        if(root_x != root_y){
            father[root_x] = root_y;
            count --;
        }
    }

    public int query(){
        return count;
    }

    public void set_count(int total){
        count = total;
    }
}

public class Solution {
    int n, m;
    public int friendCircles(String[] friends){
        if(friends == null || friends.length == 0 || friends[0] == null || friends[0].length() == 0)
            return 0;
        n = friends.length;
        m = friends[0].length();
        UnionFind uf = new UnionFind(n*m);
        int total = 0;
        for(int i = 0; i < n; i ++){
            for(int j = 0; j < m; j ++){
                if(friends[i].charAt(j) == 'Y'){
                    total ++;  
                }
            }

        }
        total /= 2;//有一半是重复计算的，但是在connect里面count没有重复减
        uf.set_count(total);

        for(int i = 0; i < n; i ++){
            for(int j = 0; j < m; j ++){
                if(friends[i].charAt(j) == 'Y'){
                  uf.connect(i, j);//直接连接i,j点 没有island那样上下左右4个方向连通
                }

            }
        }

        return uf.query();
    }

    public static void main(String[] args) throws IOException {
        Solution s  = new Solution();
        String[] src = {"YYNN", "YYYN", "NYYN", "NNNY"};
        int ret = s.friendCircles(src);
        System.out.println(ret);
    }
}

/******************************************************from website***********************************************************************************************/


   
    static int friendCircles(String[] friends) {
         if(friends==null||friends.length==0) return 0;
        int n = friends.length;
        
        unionfind uf = new unionfind(n);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(friends[i].charAt(j)=='Y'&&!uf.isConnected(i,j))
                    uf.union(i,j);
            }
        }
        return uf.getCount();
    }
    
    static class unionfind{
        int[] ids;
        int count;
        
        public unionfind(int num){
            ids = new int[num];
            for(int i=0;i<num;i++)
                ids[i]=i;
            count = num;
        }
        
        public int find(int i){
            return ids[i];
        }
        
        public void union(int i1, int i2){
            int id1 = ids[i1];
            int id2 = ids[i2];
            if(id1!=id2){
                for(int i=0;i<ids.length;i++){
                    if(ids[i]==id2)
                        ids[i]=id1;
                }
                count--;
            }
        }
        
        public boolean isConnected(int i1, int i2){
            return find(i1)==find(i2);
        }
        
        public int getCount(){
            return count;
        }
    }