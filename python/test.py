# -*- coding: utf-8 -*-
"""
Created on Sat Dec  7 20:14:11 2019

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

a=0
b=0
a=sys.argv[1]
b=sys.argv[2]
print(int(a)+int(b))
nmac=int(a)
njob=sys.argv[2]