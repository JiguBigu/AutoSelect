# -*- coding: utf-8 -*-
"""
Created on Sat Dec  7 19:49:22 2019

@author: Z
"""

# -*- coding: utf-8 -*-
import numpy as np
import geatpy as ea
import tensorflow as tf
import gc

"""
该案例展示了一个带约束连续决策变量的最小化目标的双目标优化问题。
min f1 = X**2
min f2 = (X - 2)**2
s.t.
X**2 - 2.5 * X + 1.5 >= 0
10 <= Xi <= 10, (i = 1,2,3,...)
"""


import time




def feed(matrix=np.zeros([1,5])):
        '''
        X = np.zeros([matrix.shape[0],9],float)
        for i in range(matrix.shape[0]):
            X[i][0] = matrix[i][0]
            X[i][1] = matrix[i][1]
            X[i][2] = (matrix[i][2]-100.0)/(225.0-100.0)
            X[i][3] = (matrix[i][3]-10.0)/(20.0-10.0)
            X[i][4] = (matrix[i][4]-4.0)/(18.0-4.0)
            X = np.reshape(X,[-1,3,3,1])'''

        X = np.zeros([matrix.shape[0],5],float)
        
        #print('type')
        #print(X)
        #print(matrix)
        
        for i in range(matrix.shape[0]):
            X[i][0] = matrix[i][0]
            X[i][1] = matrix[i][1]
            X[i][2] = (matrix[i][2]-100.0)/(235.0-100.0)
            X[i][3] = (matrix[i][3]-10.0)/(20.0-10.0)
            X[i][4] = (matrix[i][4]-4.0)/(18.0-4.0)
        with tf.compat.v1.Session() as sess:
            sess.run(tf.compat.v1.global_variables_initializer())
        
            saver = tf.compat.v1.train.import_meta_graph('./effect/effect-model-20000001.meta')
            saver.restore(sess, tf.compat.v1.train.latest_checkpoint('./effect'))
            graph = tf.compat.v1.get_default_graph()
            x = graph.get_tensor_by_name("x:0")
            y = graph.get_tensor_by_name("y:0")
            keep_prob = graph.get_tensor_by_name("keep_prob:0")
            effect = sess.run(y,feed_dict={x:X,keep_prob:1.0})
        
            saver = tf.compat.v1.train.import_meta_graph('./time/time-model-25000001.meta')
            saver.restore(sess, tf.compat.v1.train.latest_checkpoint('./time'))
            graph = tf.compat.v1.get_default_graph()
            x = graph.get_tensor_by_name("x:0")
            y = graph.get_tensor_by_name("y:0")
            keep_prob = graph.get_tensor_by_name("keep_prob:0")
            time = sess.run(y,feed_dict={x:X,keep_prob:1.0})
            
            del X
            del x
            del y
            del saver
            del graph
            gc.collect
            
        return effect,time





class MyProblem(ea.Problem): # 继承Problem父类
    def __init__(self, M = 2,job=4,mac=8):
        
        njob=job
        nmac=mac
        
        
        name = 'MyProblem' # 初始化name（函数名称，可以随意设置）
        Dim = 5 # 初始化Dim（决策变量维数）
        maxormins = [-1,1] # 初始化maxormins（目标最小最大化标记列表，1：最小化该目标；-1：最大化该目标）
        varTypes = [0,0,1,1,1] # 初始化varTypes（决策变量的类型，0：实数；1：整数）
        lb = [0.05,0.05,100,njob,nmac]  # 决策变量下界
        
        ub = [1,1,235,njob,nmac]  # 决策变量上界
        
        lbin = [1] * Dim # 决策变量下边界
        ubin = [1] * Dim # 决策变量上边界
        # 调用父类构造方法完成实例化
        ea.Problem.__init__(self, name, M, maxormins, Dim, varTypes, lb, ub, lbin, ubin)
    
    
    
    
    
    
    def aimFunc(self, pop): # 目标函数
        Vars = pop.Phen # 得到决策变量矩阵
        #time_start=time.time()
        f=feed(Vars)
        #time_end=time.time()
        #print('totally cost',time_end-time_start)
        f1 = f[0]
        f2 = f[1]
        
#        # 利用罚函数法处理约束条件
#        exIdx = np.where(Vars**2 - 2.5 * Vars + 1.5 < 0)[0] # 获取不满足约束条件的个体在种群中的下标
#        f1[exIdx] = f1[exIdx] + np.max(f1) - np.min(f1)
#        f2[exIdx] = f2[exIdx] + np.max(f2) - np.min(f2)
        # 利用可行性法则处理约束条件
        #pop.CV = -Vars**2 + 2.5 * Vars - 1.5
        
        pop.ObjV = np.hstack([f1, f2]) # 把求得的目标函数值赋值给种群pop的ObjV
    
    