package com.auto.select.demo.algorithm.ABC;


import com.auto.select.demo.algorithm.util.Pro;

import java.util.ArrayList;

public class ABC {
    /**
     * 保留比
     */
    private final static double r = 0.5;
    private final static int POPSIZE = 100;
    private final static int Food_Num = POPSIZE / 2;
    private final static int LIMIT = 100;
    private final static int maxCycle = 2500;
    private int[][] Time;
    private Food[] food;
    private int bestNum;
    private int bestTime;


    ABC(int p, int m, int w, ArrayList<Pro> pronum, int[][] time) {
        food = new Food[Food_Num];
        Time = time;
        for (int i = 0; i < Food_Num; i++){
            food[i] = new Food(p, m, w, pronum);
        }
    }

    public void Init() {
        int min_time = Integer.MAX_VALUE;
        for (int i = 0; i < Food_Num; i++) {
            food[i].Init(Time);
            food[i].CalTime(Time);
            if (food[i].getTime() < min_time) {
                bestNum = i;
                min_time = food[i].getTime();
            }
        }
        bestTime = food[bestNum].getTime();
    }

    public void EmployedBees() {
        order();

        //先进
        for (int i = 0; i < Food_Num / 2; i++) {
            Food temp = food[i].clone();
            food[i].Swap();
            food[i].Insert();
            food[i].CalTime(Time);
            if (temp.getTime() < food[i].getTime()){
                food[i] = temp;
            }
        }

        //后进
        for (int i = Food_Num / 2; i < Food_Num; i++) {
            Food temp = food[i].clone();
            Cross(food[i], food[bestNum].clone());
            food[i].Reserse();
            food[i].CalTime(Time);
            if (temp.getTime() < food[i].getTime()){
                food[i] = temp;
            }
        }
    }

    public void OnlookerBees() {
        //锦标赛选择一个食物源
        int i, j, k, min;
        do {
            i = (int) (Math.random() * 1000) % Food_Num;
            j = (int) (Math.random() * 1000) % Food_Num;
            k = (int) (Math.random() * 1000) % Food_Num;
        } while (i != j && i != k && j != k);
        min = food[i].getTime() < food[j].getTime() ? i : j;
        min = food[k].getTime() < food[min].getTime() ? k : min;

        //随机选一个个体交叉
        do {
            i = (int) (Math.random() * 1000) % Food_Num;
        } while (i != min);
        Food temp_i = food[i].clone();
        Food temp_min = food[min].clone();
        Cross(temp_i, temp_min);
        temp_i.CalTime(Time);
        temp_min.CalTime(Time);
        if (temp_i.getTime() < food[i].getTime()){
            food[i] = temp_i;
        }
        if (temp_min.getTime() < food[min].getTime()){
            food[min] = temp_min;
        }

        FindBest();
    }


    /**
     * POX方法进行交叉
     * @param food1
     * @param food2
     */
    public void Cross(Food food1, Food food2) {

        int WorkpieceNum = food1.getWorkpieceNum();
        int ProcessNum = food1.getM().length;

        //工件全集，1、0属于不同工件集
        int[] Hash = new int[WorkpieceNum];
        for (int g = 0; g < WorkpieceNum; g++){
            Hash[g] = 0;
        }
        for (int p = 0; p < WorkpieceNum / 2 + 1; p++){
            //划分工序集
            Hash[(int) (Math.random() * 1000) % WorkpieceNum] = 1;
        }



        int[] C1 = new int[ProcessNum];
        int[] C2 = new int[ProcessNum];
        for (int j = 0; j < ProcessNum; j++) {
            if (Hash[food1.getP()[j] - 1] != 0){
                C1[j] = food1.getP()[j];
            } else{
                C1[j] = 0;
            }

            if (Hash[food2.getP()[j] - 1] != 0){
                C2[j] = food2.getP()[j];
            } else{
                C2[j] = 0;
            }
        }

        int index;
        index = 0;
        for (int k = 0; k < ProcessNum; k++) {
            if (C1[k] == 0) {
                for (int j = index; j < ProcessNum; j++) {
                    if (Hash[food2.getP()[j] - 1] == 0) {
                        index = j + 1;
                        C1[k] = food2.getP()[j];
                        break;
                    }
                }
            }
        }

        index = 0;
        for (int k = 0; k < ProcessNum; k++) {
            if (C2[k] == 0) {
                for (int j = index; j < ProcessNum; j++) {
                    if (Hash[food1.getP()[j] - 1] == 0) {
                        index = j + 1;
                        C2[k] = food1.getP()[j];
                        break;
                    }
                }
            }
        }

        food1.setP(C1);
        food2.setP(C2);


    }

    public void New() {
        int num = 0;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < Food_Num; i++) {
            if (food[i].getTime() == bestNum){
                num++;
            }
            list.add(i);
        }

        num = (int) (r * num);
        for (int i = 0; i < num; i++) {
            int t = (int) (Math.random() * 1000) % list.size();
            int s = list.get(t);
            food[s] = new Food(food[s].getP().length, food[s].getMachineNum(), food[s].getWorkpieceNum(), food[s].getPronum());
            list.remove(t);
        }
    }

    public void FindBest() {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < Food_Num; i++) {
            if (food[i].getTime() < min) {
                min = food[i].getTime();
                bestNum = i;
            }
        }
        bestTime = food[bestNum].getTime();
    }

    public void order() {
        int i, j;
        Food temp;
        for (i = 1; i < Food_Num; i++) {
            temp = food[i];
            j = i - 1;
            while (j > -1 && temp.getTime() < food[j].getTime()) {
                food[j + 1] = food[j];
                j--;
            }
            food[j + 1] = temp;
        }
        bestNum = 0;
        bestTime = food[bestNum].getTime();
    }

    public int getBestTime() {
        return bestTime;
    }

    public Food[] getFood() {
        return food;
    }

    public int getBestNum() {
        return bestNum;
    }
}
