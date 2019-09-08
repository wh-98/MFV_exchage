package org.hwk.pd.app;
import  java.awt.*; 
import  java.io.*; 
//定义一组数据成员
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

