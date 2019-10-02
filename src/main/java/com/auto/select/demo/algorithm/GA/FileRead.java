package com.auto.select.demo.algorithm.GA;

import com.auto.select.demo.algorithm.util.Mtime;
import com.auto.select.demo.algorithm.util.Pro;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRead {
    public ArrayList<Pro>Pronum;
    int [][] Time;
    ArrayList<ArrayList<Mtime>>ProTime;
    int MachineNum;
    int WorkpieceNum;
    int ProcessNum;

    FileRead(){
        Pronum=new ArrayList<Pro>();
        ProTime=new ArrayList<ArrayList<Mtime>>();
    }



    public void Read(String path)
    {


        int p_num,m_num;
        String[] data;
        try{
            Pattern pat = Pattern.compile("[0-9]{1,}");
            ArrayList<Integer>arrayList=new ArrayList<Integer>();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            line=br.readLine();

            arrayList.clear();
            Matcher matcher = pat.matcher(line);
            while (matcher.find()) {
                String temp = line.substring(matcher.start(),matcher.end());
                arrayList.add(Integer.valueOf(temp));
                //System.out.println(temp);
            }

            p_num=arrayList.get(0);
            m_num=arrayList.get(1);
            MachineNum=m_num;
            WorkpieceNum=p_num;
            ProcessNum=0;

            for(int i=0;i<p_num;i++)
            {
                int ptr=0;
                line=br.readLine();

                arrayList.clear();
                matcher = pat.matcher(line);
                while (matcher.find()) {
                    String temp = line.substring(matcher.start(),matcher.end());
                    arrayList.add(Integer.valueOf(temp));
                    //System.out.println(temp);
                }

                //System.out.println(arrayList.size());
                //System.out.println(arrayList);

                Pro pro=new Pro();
                pro.start = ProcessNum;
                int p=arrayList.get(ptr++);
                ProcessNum += p;
                pro.num = p;
                Pronum.add(pro);
                for (int j = 0; j < p; j++)
                {
                    int m=arrayList.get(ptr++);
                    ArrayList<Mtime>protime=new ArrayList<Mtime>();
                    for (int k = 0; k < m; k++)
                    {
                        Mtime a=new Mtime();
                        a.m=arrayList.get(ptr++);
                        a.time=arrayList.get(ptr++);
                        protime.add(a);
                    }
                    ProTime.add(protime);
                }
            }

            Time=new int[ProcessNum][MachineNum];
            for(int i=0;i<ProcessNum;i++)
                for(int j=0;j<MachineNum;j++)
                    Time[i][j]=Integer.MAX_VALUE/1000;

            for (int i = 0; i < ProcessNum; i++)
            {
                for (int j = 0; j < ProTime.get(i).size(); j++)
                {
                    Time[i][ProTime.get(i).get(j).m-1]=ProTime.get(i).get(j).time;
                }
            }

            /*for(int i=0;i<Time.length;i++)
            {
                for(int j=0;j<Time[i].length;j++)
                {
                    System.out.print(Time[i][j]+" ");
                }
                System.out.println();
            }*/


        }catch (Exception e)
        {
            System.out.println("FileRead类-Read方法:"+e);
        }


    }


    public ArrayList<Pro> getPronum() {
        return Pronum;
    }

    public int[][] getTime() {
        return Time;
    }

    public ArrayList<ArrayList<Mtime>> getProTime() {
        return ProTime;
    }

    public int getMachineNum() {
        return MachineNum;
    }

    public int getWorkpieceNum() {
        return WorkpieceNum;
    }
}
