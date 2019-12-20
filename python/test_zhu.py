# -*- coding: utf-8 -*-
"""
Created on Wed Oct 16 22:28:55 2019

@author: DELL
"""

import tensorflow as tf
import numpy as np
import sys

#顺序： pc pm 种群规模popsize 迭代次数G 工件数 机器数

X = np.zeros([200,9])


for i in range(200):
    X[i][0] = sys.argv[i*6+1]#pc
    X[i][1] = sys.argv[i*6+2]#pm
    X[i][2] = sys.argv[i*6+3]#种群规模
    X[i][3] = sys.argv[i*6+4]#迭代次数
    X[i][4] = sys.argv[i*6+5]#工件数
    X[i][5] = sys.argv[i*6+6]#机器数

X = np.reshape(X,[-1,3,3,1])

with tf.compat.v1.Session() as sess:
    sess.run(tf.compat.v1.global_variables_initializer())
    
    saver = tf.compat.v1.train.import_meta_graph('./time_and_effect_model/my-model.meta')
    saver.restore(sess, tf.compat.v1.train.latest_checkpoint('./time_and_effect_model'))
    graph = tf.compat.v1.get_default_graph()
    x = graph.get_tensor_by_name("x:0")
    y = graph.get_tensor_by_name("y:0")

    YY = sess.run(y,feed_dict={x:X})
for i in range(YY.shape[0]):
    print(YY[i][0])
    print(YY[i][1])