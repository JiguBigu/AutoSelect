package com.auto.select.demo.algorithm.PSO;

public class PSOThread extends Thread{
    Panicle best;
    public PSOThread()
    {
        best=new Panicle();
    }

    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "PSOThread{" +
                "best=" + best +
                '}';
    }

    @Override
    public void run()
    {
        Panicle best_panicle;
        FileRead fileRead=new FileRead();
        fileRead.Read(filePath);
        Panicle[]panicles=new Panicle[80];
        int min=0,mintime=Integer.MAX_VALUE;
        for (int i = 0; i < panicles.length; i++) {
            panicles[i]=new Panicle(fileRead.ProcessNum,fileRead.MachineNum,fileRead.WorkpieceNum,fileRead.Pronum);
            panicles[i].Init(fileRead.Time);
            if(panicles[i].getTime()<mintime){
                min=i;
            }
        }
        best_panicle=panicles[min].clone();
        //System.out.println("时间："+best_panicle.getTime());
        for (int o = 0; o < 400; o++) {
            //System.out.println(o+" <> "+best_panicle.getTime());
            for (int j = 0; j < panicles.length; j++)
            {
                panicles[j].UpdataV(best_panicle.getTime());
                panicles[j].UpdataX(fileRead.Time,best_panicle);
                panicles[j].CalTime(fileRead.Time);
                panicles[j].Updata_pbest();
            }

            //排序
            Panicle panicle;
            int i,j;
            for(i=0;i<panicles.length-1;i++)
            {
                //查找最小值
                min=i;
                for(j=i+1;j<panicles.length;j++) {
                    if (panicles[min].getTime() > panicles[j].getTime()) {
                        min = j;
                    }
                }

                //交换
                if(min!=i)
                {
                    panicle=panicles[min];
                    panicles[min]=panicles[i];
                    panicles[i]=panicle;
                }
            }

            if(panicles[0].getTime()<best_panicle.getTime()){
                best_panicle=panicles[0].clone();
            }
            best_panicle.LocalSearch(fileRead.Time);
            for (int k = (int)(panicles.length*0.3); k <(int) (panicles.length*0.5); k++) {
                panicles[k].Init(fileRead.Time);
            }
            for (int k = (int)(panicles.length*0.5); k <(int) (panicles.length*0.8); k++)
            {
                int index=((int)Math.random()*1000)%(int) (panicles.length*0.3);
                panicles[k]=panicles[k].clone();
            }
            for (int k = (int)(panicles.length*0.8); k <panicles.length; k++){
                panicles[k]=best_panicle.clone();
            }

        }

        best=best_panicle;

    }

    public Panicle getBest() {
        return best;
    }
}
