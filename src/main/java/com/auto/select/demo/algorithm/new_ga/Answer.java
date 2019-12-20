package com.auto.select.demo.algorithm.new_ga;

public class Answer {
    int [] P;
    int [] M;
    int time;

    public Answer(int pnum) {
        P=new int[pnum];
        M=new int[pnum];
    }

    public Answer clone()
    {
        Answer answer=new Answer(P.length);
        answer.P=P.clone();
        answer.M=M.clone();
        answer.time=time;

        return answer;
    }

    public int[] getP() {
        return P;
    }

    public int[] getM() {
        return M;
    }

    public int getTime() {
        return time;
    }
}
