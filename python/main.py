# -*- coding: utf-8 -*-
"""
Created on Sat Dec  7 19:48:43 2019

@author: Z
"""


import geatpy as ea # import geatpy
from MyProblem import MyProblem # 导入自定义问题接口
import numpy as np
import random
import sys

np.set_printoptions(suppress=True)


def mysort(nums,index):
    for i in range(nums.shape[0] - 1):  # 这个循环负责设置冒泡排序进行的次数
        for j in range(nums.shape[0] - i - 1):  # j为列表下标
            if nums[j][index] > nums[j + 1][index]:
                t=np.copy(nums[j])
                nums[j]=nums[j+1]
                nums[j+1]=t
    return nums

def delrepeat(nums,index):
    list=nums.tolist()
    list1=[]
    for element in list :
        if(element not in list1):
            flag=1
            for i in range(len(list1)):
                if(element[index]==list1[i][index]):
                    flag=0
            if(flag==1):
                list1.append(element)
    return np.array(list1)


#接收参数
#nmac=int(sys.argv[1])
#njob=int(sys.argv[2])

nmac=10
njob=20



"""================================实例化问题对象==========================="""
problem = MyProblem(njob,nmac)     # 生成问题对象
"""==================================种群设置==============================="""
Encoding = 'RI'           # 编码方式
NIND = 100                 # 种群规模
Field = ea.crtfld(Encoding, problem.varTypes, problem.ranges, problem.borders) # 创建区域描述器
population = ea.Population(Encoding, Field, NIND) # 实例化种群对象（此时种群还没被初始化，仅仅是完成种群对象的实例化）
"""=================================算法参数设置============================"""
myAlgorithm = ea.moea_NSGA2_templet(problem, population) # 实例化一个算法模板对象
myAlgorithm.MAXGEN = 50  # 最大进化代数
myAlgorithm.drawing = 0
"""============================调用算法模板进行种群进化======================"""
NDSet = myAlgorithm.run() # 执行算法模板，得到帕累托最优解集NDSet
NDSet.save()              # 把结果保存到文件中
# 输出
#print('用时：%s 秒'%(myAlgorithm.passTime))//
#print('非支配个体数：%s 个'%(NDSet.sizes))
#print('单位时间找到帕累托前沿点个数：%s 个'%(int(NDSet.sizes // myAlgorithm.passTime)))
#print(type(NDSet))
#print(NDSet.ObjV)
#print(NDSet.Chrom)
arr=np.hstack((NDSet.ObjV,NDSet.Chrom))
#print('数据:\n')
#print(arr)
#print('数据:\n')
#print(mysort(arr,1))
s=mysort(arr,1)
m=np.hstack((np.zeros((s.shape[0],1)),s))
np.set_printoptions(precision=4)
#print(m)
m[0][0]=1000.0
m=delrepeat(m,1)
m=delrepeat(m,2)
for i in range(m.shape[0]-1):
    m[i+1][0]=(m[i+1][2]-m[i][2])/(m[i+1][1]-m[i][1])
s=mysort(m,0)
#print(s)
index=random.randint(0,9)
print(''+str(s[index][1])+' '+str(s[index][2])+' '+str(s[index][3])+' '+str(s[index][4])+' '+str(s[index][5]))