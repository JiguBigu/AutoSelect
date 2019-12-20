package com.auto.select.demo.algorithm.new_ga;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Python {
    public double pc;
    public double pm;
    int POPSIZE;
    double time;
    double effect;

    int nmac;
    int njob;

    public Python(int nmac, int njob) {
        this.nmac = nmac;
        this.njob = njob;
    }

    void Run() throws Exception
    {
        String[]str1=new String[4];

        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath() ;

        String python = "D:\\Anaconda3\\envs\\CNN\\python";//改成自己的python路径

        String py_dir = courseFile+"\\python";
        String py_path = courseFile+"\\python\\main.py";
        System.out.println(py_path);
        str1[0]=python;
        str1[1]=py_path;

        str1[2]="10";
        str1[3]="20";

        String[] cmdArr = str1;
        String line="";
        String str="";
        Process process = null;
        try {

            process = Runtime.getRuntime().exec(cmdArr,null, new File(py_dir));


            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));


            while( (line = br.readLine()) != null){
                str=line;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] strs=str.split(" ");
        effect=Double.valueOf(strs[0]);
        time=Double.valueOf(strs[1]);
        pc=Double.valueOf(strs[2]);
        pm=Double.valueOf(strs[3]);
        double p=Double.valueOf(strs[4]);
        POPSIZE=(int)p;
        try {
            System.out.println("waitFor():"+process.waitFor());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
