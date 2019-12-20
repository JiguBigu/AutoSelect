package com.auto.select.demo.algorithm.new_ga;

public class Main {

    public static void main(String[] args) throws Exception
    {
        //改python路径
        GA ga=new GA("G:\\Brandimarte_Data\\Text\\mk02.txt");
        ga.Run();
        System.out.println(ga.findBest());
        System.out.println(ga.getPC());
    }


}
