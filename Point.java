package org.hwk.pd.app;
import  java.awt.*; 
import  java.io.*; 
//����һ�����ݳ�Ա
class   Point   implements  Serializable {
int   x ,  y ;
Color  col ;
int   tool ;
int   boarder ;
Point( int  x,  int  y, Color col,  int  tool,  int  boarder) {
 this . x  = x;
 this . y  = y;
 this . col  = col;
 this . tool  = tool;
 this . boarder  = boarder;
}
}

