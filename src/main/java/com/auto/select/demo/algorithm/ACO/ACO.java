package com.auto.select.demo.algorithm.ACO;



import com.auto.select.demo.algorithm.util.Pro;

import java.util.ArrayList;

public class ACO {
    public  static String path = "G:\\AutoSelectFiles\\Mk01.fjs";

    public static  int MAX_GEN = 100;//迭代次数
    public static  int auntNum = 100;//蚂蚁数量
    public static  double alpha = 10.0;//信息素启发因子
    public static  double beta = 1.5;//期望启发因子
    public static  double seg = 0.5;
    public static  double pheMax = 3.5;//信息素最大值
    public static  double pheMin = 1.0;//信息素最小值
    public static int Q = 50;//信息素增强系数

    public static  int inf = Integer.MAX_VALUE;
    public static  int machineMax = 8;//最大机器数
    public static  int processNum = 27;//工序数
    public static  int workpieceNum = 8;//工件数

    public static  int[] a = new int[]{ 0, 3, 7, 10, 13, 17, 20, 23, 27 };//每个工件对应的工序
    public static  int[] b = new int[]{ 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 8, 8 };//每道工序对应的哪个工件

    public static  int[][] time = new int[][]{
            {5, 3, 5, 3, 3, 2147483, 10, 9},
            {10, 2147483, 5, 8, 3, 9, 9, 6},
            {2147483, 10, 2147483, 5, 6, 2, 4, 5},
            {5, 7, 3, 9, 8, 2147483, 9, 2147483},
            {2147483, 8, 5, 2, 6, 7, 10, 9},
            {2147483, 10, 2147483, 5, 6, 4, 1, 7},
            {10, 8, 9, 6, 4, 7, 2147483, 2147483},
            {10, 2147483, 2147483, 7, 6, 5, 2, 4},
            {2147483, 10, 6, 4, 8, 9, 10, 2147483},
            {1, 4, 5, 6, 2147483, 10, 2147483, 7},
            {3, 1, 6, 5, 9, 7, 8, 4},
            {12, 11, 7, 8, 10, 5, 6, 9},
            {4, 6, 2, 10, 3, 9, 5, 7},
            {3, 6, 7, 8, 9, 2147483, 10, 2147483},
            {10, 2147483, 7, 4, 9, 8, 6, 2147483},
            {2147483, 9, 8, 7, 4, 2, 7, 2147483},
            {11, 9, 2147483, 6, 7, 5, 3, 6},
            {6, 7, 1, 4, 6, 9, 2147483, 10},
            {11, 2147483, 9, 9, 9, 7, 6, 4},
            {10, 5, 9, 10, 11, 2147483, 10, 2147483},
            {5, 4, 2, 6, 7, 2147483, 10, 2147483},
            {2147483, 9, 2147483, 9, 11, 9, 10, 5},
            {2147483, 8, 9, 3, 8, 6, 2147483, 10},
            {2, 8, 5, 9, 2147483, 4, 2147483, 10},
            {7, 4, 7, 8, 9, 2147483, 10, 2147483},
            {9, 9, 2147483, 8, 5, 6, 7, 1},
            {9, 2147483, 3, 7, 1, 5, 8, 2147483}
    };
    public static double[][] pheromone = new double[processNum][processNum];//信息素

    public static void main(String[] args) {
        ACO m = new ACO();

        /***读取文件为工件数
        ，工序数，机器数，加工时间，
        数组a，数组b赋值*/
        //System.out.println(path);

        FileRead f = new FileRead();
        f.Read(path);
        workpieceNum = f.getWorkpieceNum();
        machineMax = f.getMachineNum();
        processNum = f.ProcessNum;
        time = f.getTime();
        pheromone = new double[processNum][processNum];
        ArrayList<Pro> p = f.getPronum();

        a = new int[workpieceNum+1];
        b = new int[processNum];
        a[workpieceNum] = processNum;
        for(int i = 0;i<workpieceNum;i++){
            a[i] = p.get(i).start;
        }

        int k = 0;
        for(int i = 0;i<workpieceNum;i++){
            for (int j = 0;j<p.get(i).num;j++){
                b[k] = i+1;
                k++;
            }
        }




        /**打印出
        这些
        上述参数*/
        /*System.out.println("机器数:"+machineMax);
        System.out.println("工件数:"+workpieceNum);
        System.out.println("工序数:"+processNum);
        System.out.print("a:");
        for(int i = 0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();
        System.out.print("b:");
        for(int i = 0;i<b.length;i++){
            System.out.print(b[i]+" ");
        }
        System.out.println();
        for(int i = 0;i<processNum;i++){
            for(int j = 0;j<machineMax;j++){
                System.out.print(time[i][j]+" ");
            }
            System.out.println();
        }*/




        /*Generation a = new ACO();
        a.go();
        a.print();*/




        /**开始
         * 迭代*/
        int best = inf;//所有迭代中的最短时间
        int index = 0;//记录哪一次迭代产生最优解
        Generation[] generations = new Generation[MAX_GEN];
        for (int i = 0;i<MAX_GEN;i++){
            generations[i] = m.new Generation();
            generations[i].go();

            //System.out.println("第"+(i+1)+"代运算全局最优:"+acas[i].best_time);

            if(generations[i].best_time < best){
                best = generations[i].best_time;
                index = i;
            }
        }
        System.out.println();
        System.out.println(MAX_GEN+"次迭代后，在第"+(index+1)+"次迭代产生全局最优:"+best);
        generations[index].print();
    }

    class Generation{

        public int[][] ganTe_ACA;
        int indexOfGen = 0;//记录第几只蚂蚁最优


        //public static int[][] m_machine = new int[processNum][machineMax];//记录工序在不同机器加工时间
        int best_time = inf;//每一代的最优
        public Aunt[] m_aunt = new Aunt[auntNum];

        public void go(){

            for (int i = 0; i < auntNum; i++)
            {
                m_aunt[i] = new Aunt(a,b);
            }

            init();

            for (int i = 0; i < auntNum; i++)
            {
                m_aunt[i].search();
                //System.out.println("第"+i+"个蚂蚁"+m_aunt[i].best);

                if (m_aunt[i].best < best_time) {
                    indexOfGen = i;
                    best_time = m_aunt[i].best;
                }
            }
            update();
            ganTe_ACA = m_aunt[indexOfGen].ganTe;
        }

        public void print(){
            System.out.println("第"+(indexOfGen+1)+"只蚂蚁全局最优"+best_time);
            System.out.println();

       /* for(int i = 0;i<Generation.machineMax;i++){
            System.out.print("第"+(i+1)+"台机器:");
            for(int j = 0;j<Generation.processNum;j++){
                if(m_aunt[index].ganTe[i][j] != -1) {
                    System.out.print(m_aunt[index].ganTe[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();*/

            //打印每台机器的工作顺序
            for(int i = 0;i<machineMax;i++){
                System.out.print("第"+(i+1)+"台机器:");
                for(int j = 0;j<processNum;j++){
                    if(ganTe_ACA[i][j] != -1) {
                        int t = time[ganTe_ACA[i][j]][i];
                        int workPiece = b[ganTe_ACA[i][j]];
                        int node = ganTe_ACA[i][j] - a[workPiece-1]+1;
                        int endTime = m_aunt[indexOfGen].nodeEndTime[ganTe_ACA[i][j]];
                        System.out.print("("+workPiece+","+node+","+ t +","+endTime+")"+" ");
                    }
                }
                System.out.println();
            }
        }

        public void init(){
            for (int i = 0; i < processNum; i++) {
                for (int j = 0; j < processNum; j++) {
                    pheromone[i][j] = pheMax;
                }
            }

        /*for (int i = 0; i < processNum; i++) {
            for (int j = 0; j < machineMax; j++) {
                m_machine[i][j] = time[i][j];
            }
        }*/
        }

        /*????????????????*/
        //更新信息素
        void update() {
            for (int i = 0; i < processNum-1; i++) {
                //更新从工序x到工序y的信息素
                int x = m_aunt[indexOfGen].pathOfAnt.get(i).intValue();
                int y = m_aunt[indexOfGen].pathOfAnt.get(i+1).intValue();
                //System.out.println(x+"->"+y);

                pheromone[x][y] = (1 - seg)*pheromone[x][y] + Q/best_time; //更新最优路径的信息素

                if (pheromone[x][y] < 1.0) pheromone[x][y] = pheMin;
                else if (pheromone[x][y]>3.5) pheromone[x][y] = pheMax;
            }
        }
        /*????????????????*/
    }

    class Aunt{

        int[] Accessory = new int[workpieceNum+1];// 记录每个工件对应的工序号
        int[] procedure = new int[processNum]; //可选择的工件号{111,2222,33333,44444}

        ArrayList<Integer> pathOfAnt = new ArrayList<Integer>();//每个蚂蚁选择工序的顺序

        int[][] ganTe = new int[machineMax][processNum];//记录每个机器上的加工顺序
        int[] nodeEndTime = new int[processNum];//记录每个工序结束的时间

        int[] machine_time = new int[machineMax]; //记录机器时间
        int[] workpiece_time = new int[workpieceNum];

        int[] cur_Accessory = new int[workpieceNum];

        int[] tabu = new int[processNum];
        int curNode;//当前工序

        int best;

        public Aunt(int[] a,int[] b){
            best = 0;
            for (int i = 0; i <= workpieceNum; i++) {
                Accessory[i] = a[i];
            }
            for (int j = 0; j < workpieceNum; j++) {
                cur_Accessory[j] = a[j];
                workpiece_time[j] = 0;
            }
            for (int j = 0; j < processNum; j++) {
                procedure[j] = b[j];
                tabu[j] = 0;
            }
            for (int i = 0; i < machineMax; i++) {
                machine_time[i] = 0;
            }
            for(int i = 0;i<machineMax;i++){
                for(int j = 0;j<processNum;j++){
                    ganTe[i][j] = -1;
                }
            }
        }

        public void A_init(){
            int r = (int)Math.random()*workpieceNum;//选择第一个加工的工件
            int min_time = inf;
            int min_index = -1;//加工时间最短的机器
            int t = Accessory[r];//第一个加工的工序
            pathOfAnt.add(new Integer(t));
            for(int i = 0;i<machineMax;i++){
                if(time[t][i] != 2147483){
                    if(time[t][i] < min_time){
                        min_time = time[t][i];
                        min_index = i;
                    }
                }
            }

            curNode = t;
            cur_Accessory[r]++;
            if(min_index != -1) {
                machine_time[min_index] += time[t][min_index];
                workpiece_time[r] += time[t][min_index];
                ganTe[min_index][0] = curNode;
                nodeEndTime[curNode] = time[curNode][min_index];
            }
            tabu[curNode] = 1;
        }

        public int choose_p(int[] machine){
            int select = -1;
            double[] p = new double[workpieceNum];

            double total_p = 0.0;
            for (int i = 0; i < workpieceNum; i++) {
                if (machine[i] == -1) p[i] = 0; //如果工件已经完工，则被选中的概率为0
                else {
                    int pro_num = cur_Accessory[i]; //得到工序号
                    int t_wait = (machine_time[machine[i]] > workpiece_time[i])?machine_time[machine[i]]:workpiece_time[i];

                    double temp = 0.0;
                    temp = 1 / ((t_wait + time[cur_Accessory[i]][machine[i]])*1.0);

                    p[i] = Math.pow(pheromone[curNode][pro_num], alpha) * Math.pow(temp, beta); //公式计算概率

                    total_p += p[i];
                }
            }

            //轮盘赌
            double temp_p = 0.0;

            if (total_p > 0.0) //总的信息素值大于0
            {
                temp_p = Math.random()*total_p; //取一个随机数
                for (int i = 0; i < workpieceNum; i++) {
                    if (machine[i] != -1) {
                        temp_p = temp_p - p[i]; //转动轮盘
                        if (temp_p < 0.0) {
                            select = i;
                            break;
                        }
                    }
                }
            }
            if (select == -1) //随机选择一个未完成加工的工件
            {
                do {
                    int i = (int)Math.random() * workpieceNum;
                    if (machine[i] != -1)
                    {
                        return i;
                    }
                } while (true);
            }
            return select;
        }

        //搜索
        public void move() {
            //machine_time[min_index] += m_machine[min_index][curNode];

            int[] machine = new int[workpieceNum]; //保存每个工件可选择的最优机器

            /*=============为未加工完成的工件选择机器=================*/
            for (int i = 0; i < workpieceNum; i++)
            {
                if (cur_Accessory[i] < Accessory[i + 1]) //工件未完成加成
                {
                    int min_index = 0;
                    int min_time = inf;

                    for (int j = 0; j < machineMax; j++)
                    {
                        if (time[cur_Accessory[i]][j] != 0)
                        {
                            int t = (machine_time[j]>workpiece_time[i])?machine_time[j]:workpiece_time[i];//当前机器完工时间，工件当前所加工工序完工时间，较大者
                            t += time[cur_Accessory[i]][j]; //加上当前工件在当前机器的加工时间
                            if (t < min_time)
                            {
                                min_index = j;
                                min_time = t;
                            }
                        }

                    }
                    machine[i] = min_index;
                }
                else //工件完成加成，此工件不用选择机器
                {
                    machine[i] = -1;
                }

            }

            /*=================选择下一个要加工的工件=================*/
            int next_acc = choose_p(machine); //选工件


            int min_m = machine[next_acc]; //加工当前工件的机器

            /*??????*/
            int t = (machine_time[min_m]>workpiece_time[next_acc])?machine_time[min_m]:workpiece_time[next_acc];
            t += time[cur_Accessory[next_acc]][min_m];

            machine_time[min_m] = t;
            workpiece_time[next_acc] = t;
            /*???????*/


            int cur_pro = cur_Accessory[next_acc];
            pathOfAnt.add(new Integer(cur_pro));
            cur_Accessory[next_acc]++;

            tabu[cur_pro] = 1; //当前工序加入禁忌表
            curNode = cur_pro;

            int i = 0;
            while (ganTe[min_m][i] != -1){
                i++;
            }
            ganTe[min_m][i] = curNode;
            nodeEndTime[curNode] = t;
        }

        public void search() {
            A_init();

            for (int i = 1; i < processNum; i++)
            {
                move();
            }


            for (int i = 0; i < workpieceNum; i++)
            {
                if (workpiece_time[i] > best)
                    best = workpiece_time[i];
            }
        }
    }
}


