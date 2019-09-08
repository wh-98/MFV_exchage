package org.hwk.pd.app;
import  java.awt.*; 
import  java.io.*; 
import  java.awt.event.*; 
import  java.util.*; 
import  javax.swing.*; 
import  java.awt.geom.*; 
//ʵ����괥���¼���һϵ�л�ͼ����
class  paintboard  extends  Frame  implements  ActionListener, MouseMotionListener,
MouseListener, ItemListener {
private   static   final   long   serialVersionUID  = 1L;
int   x  = -1,  y  = -1;
int   con  = 1; //  ���ʴ�С
int   Econ  = 5; //  ��Ƥ��С
int   toolFlag  = 0; // toolFlag: ���߱��
// toolFlag ���߶�Ӧ��
//  �� 0-- ���ʣ����� 1-- ��Ƥ������ 2-- �������
//  �� 3-- ֱ�ߣ����� 4-- Բ������ 5-- ���Σ���
Color  c  =  new  Color(0, 0, 0);  //  ������ɫ
BasicStroke  size  =  new  BasicStroke( con , BasicStroke. CAP_BUTT ,
BasicStroke. JOIN_BEVEL ); //  ���ʴ�ϸ
Point  cutflag  =  new  Point(-1, -1,  c , 6,  con ); //  �ضϱ�־
Vector<Point>  paintInfo  =  null ; //  ����Ϣ������
int   n  = 1;
FileInputStream  picIn  =  null ;
FileOutputStream  picOut  =  null ;
ObjectInputStream  VIn  =  null ;
ObjectOutputStream  VOut  =  null ;
// * ������� -- ���ʣ�ֱ�ߣ�Բ�����Σ������ , ��Ƥ����� */
Panel  toolPanel ;
Button  eraser ,  drLine ,  drCircle ,  drRect ;
Button  clear ,  pen ;
Choice  ColChoice ,  SizeChoice ,  EraserChoice ;
Button  colchooser ;
Label  color, size_B ,  size_E ;
//  ���湦��
Button  openPic ,  savePic ;
FileDialog  openPicture ,  savePicture ;
paintboard(String s) {
super (s);
addMouseMotionListener( this );
addMouseListener( this );
paintInfo  =  new  Vector<Point>();
/*  �����߰�ť��ѡ����  */
//  ��ɫѡ��
ColChoice  =  new  Choice();
ColChoice .add( " ��ɫ " );
ColChoice .add( " ��ɫ " );
ColChoice .add( " ��ɫ " );
ColChoice .add( " ��ɫ " );
ColChoice .addItemListener( this );
//  ���ʴ�Сѡ��
SizeChoice  =  new  Choice();
SizeChoice .add( "1" );
SizeChoice .add( "3" );
SizeChoice .add( "5" );
SizeChoice .add( "7" );
SizeChoice .add( "9" );
SizeChoice .addItemListener( this );
//  ��Ƥ��Сѡ��
EraserChoice  =  new  Choice();
EraserChoice .add( "5" );
EraserChoice .add( "9" );
EraserChoice .add( "13" );
EraserChoice .add( "17" );
EraserChoice .addItemListener( this );
toolPanel  =  new  Panel();  //  ������ʾ������
clear  =  new  Button( " ��� " );
eraser  =  new  Button( " ��Ƥ " );
pen  =  new  Button( " ���� " );
drLine  =  new  Button( " ��ֱ�� " );
drCircle  =  new  Button( " ��Բ�� " );
drRect  =  new  Button( " ������ " );
openPic  =  new  Button( " ��ͼ�� " );
savePic  =  new  Button( " ����ͼ�� " );
colchooser  =  new  Button( " ��ʾ��ɫ�� " );
//  ������¼�����
clear .addActionListener( this );
eraser .addActionListener( this );
pen .addActionListener( this );
drLine .addActionListener( this );
drCircle .addActionListener( this );
drRect .addActionListener( this );
openPic .addActionListener( this );
savePic .addActionListener( this );
colchooser .addActionListener( this );
color  =  new  Label( " ������ɫ " , Label. CENTER );
size_B  =  new  Label( " ���ʴ�С " , Label. CENTER );
size_E  =  new  Label( " ��Ƥ��С " , Label. CENTER );
//  ���������
toolPanel .add( openPic );  //  ��ͼ��
toolPanel .add( savePic );  //  ����ͼ��
toolPanel .add( pen );  //  ����
toolPanel .add( drLine );  //  ����
toolPanel .add( drCircle );  //  ��Բ
toolPanel .add( drRect );  //  ������
toolPanel .add( color );
toolPanel .add( ColChoice );  //  ������ɫ
toolPanel .add( size_B );
toolPanel .add( SizeChoice );  //  ���ʴ�С
toolPanel .add( colchooser );  //  ��ɫ��
toolPanel .add( eraser );  //  ��Ƥ
toolPanel .add( size_E );
toolPanel .add( EraserChoice ); //  ��Ƥ��С
toolPanel .add( clear );  //  ���
toolPanel .setBackground(Color. CYAN ); //  ��屳��ɫ
//  ������嵽 APPLET ���
add( toolPanel , BorderLayout. NORTH );
setBounds(60, 60, 1000, 550);
setVisible( true );
validate();
// dialog for save and load
openPicture  =  new  FileDialog( this ,  " ��ͼ�� " , FileDialog. LOAD );
openPicture .setVisible( false );
savePicture  =  new  FileDialog( this ,  " ����ͼ�� " , FileDialog. SAVE );
savePicture .setVisible( false );
openPicture .addWindowListener( new  WindowAdapter() {
  public   void  windowClosing(WindowEvent e) {
    openPicture .setVisible( false );
 }
});
savePicture .addWindowListener( new  WindowAdapter() {
  public   void  windowClosing(WindowEvent e) {
    savePicture .setVisible( false );
 }
});
addWindowListener( new  WindowAdapter() {
  public   void  windowClosing(WindowEvent e) {
   System. exit (0);
 }
});
}
public   void  paint(Graphics g) {
Graphics2D g2d = (Graphics2D) g;
Point p1, p2;
n  =  paintInfo .size();
if  ( toolFlag  == 2)
 g.clearRect(0, 0, getSize(). width , getSize(). height ); //  ���
for  ( int  i = 0; i <  n ; i++) {
 p1 = (Point)  paintInfo .elementAt(i);
 p2 = (Point)  paintInfo .elementAt(i + 1);
  size  =  new  BasicStroke(p1. boarder , BasicStroke. CAP_BUTT ,
     BasicStroke. JOIN_BEVEL );
 g2d.setColor(p1. col );
 g2d.setStroke( size );
  if  (p1. tool  == p2. tool ) {
    switch  (p1. tool ) {
    case  0: //  ����
 Line2D line1 =  new  Line2D.Double(p1. x , p1. y , p2. x , p2. y );
     g2d.draw(line1);
      break ;
    case  1: //  ��Ƥ
     g.clearRect(p1. x , p1. y , p1. boarder , p1. boarder );
      break ;
    case  3: //  ��ֱ��
 Line2D line2 =  new  Line2D.Double(p1. x , p1. y , p2. x , p2. y );
     g2d.draw(line2);
      break ;
    case  4: //  ��Բ
Ellipse2D ellipse =  new  Ellipse2D.Double(p1. x , p1. y ,
       Math. abs (p2. x  - p1. x ), Math. abs (p2. y  - p1. y ));
     g2d.draw(ellipse);
      break ;
    case  5: //  ������
   Rectangle2D rect =  new  Rectangle2D.Double(p1. x , p1. y ,
       Math. abs (p2. x  - p1. x ), Math. abs (p2. y  - p1. y ));
     g2d.draw(rect);
      break ;
    case  6: //  �ضϣ�����
     i = i + 1;
      break ;
    default :
   } // end switch
 } // end if
} // end for
}
public   void  itemStateChanged(ItemEvent e) {
if  (e.getSource() ==  ColChoice ) //  Ԥѡ��ɫ
{
 String name =  ColChoice .getSelectedItem();
  if  (name ==  " ��ɫ " ) {
    c  =  new  Color(0, 0, 0);
 }  else   if  (name ==  " ��ɫ " ) {
    c  =  new  Color(255, 0, 0);
 }  else   if  (name ==  " ��ɫ " ) {
    c  =  new  Color(0, 255, 0);
 }  else   if  (name ==  " ��ɫ " ) {
    c  =  new  Color(0, 0, 255);
 }
}  else   if  (e.getSource() ==  SizeChoice ) //  ���ʴ�С
{
 String selected =  SizeChoice .getSelectedItem();
  if  (selected ==  "1" ) {
    con  = 1;
    size  =  new  BasicStroke( con , BasicStroke. CAP_BUTT ,
       BasicStroke. JOIN_BEVEL );
 }  else   if  (selected ==  "3" ) {
    con  = 3;
    size  =  new  BasicStroke( con , BasicStroke. CAP_BUTT ,
       BasicStroke. JOIN_BEVEL );
 }  else   if  (selected ==  "5" ) {
    con  = 5;
    size  =  new  BasicStroke( con , BasicStroke. CAP_BUTT ,
       BasicStroke. JOIN_BEVEL );
 }  else   if  (selected ==  "7" ) {
    con  = 7;
    size  =  new  BasicStroke( con , BasicStroke. CAP_BUTT ,
       BasicStroke. JOIN_BEVEL );
 }  else   if  (selected ==  "9" ) {
    con  = 9;
    size  =  new  BasicStroke( con , BasicStroke. CAP_BUTT ,
       BasicStroke. JOIN_BEVEL );
 }
}  else   if  (e.getSource() ==  EraserChoice ) //  ��Ƥ��С
{
 String Esize =  EraserChoice .getSelectedItem();
  if  (Esize ==  "5" ) {
    Econ  = 5 * 2;
 }  else   if  (Esize ==  "9" ) {
    Econ  = 9 * 2;
 }  else   if  (Esize ==  "13" ) {
    Econ  = 13 * 2;
 }  else   if  (Esize ==  "17" ) {
    Econ  = 17 * 3;
 }
}
}
public   void  mouseDragged(MouseEvent e) {
Point p1;
switch  ( toolFlag ) {
case  0: //  ����
  x  = ( int ) e.getX();
  y  = ( int ) e.getY();
 p1 =  new  Point( x ,  y ,  c ,  toolFlag ,  con );
  paintInfo .addElement(p1);
 repaint();
  break ;
case  1: //  ��Ƥ
  x  = ( int ) e.getX();
  y  = ( int ) e.getY();
 p1 =  new  Point( x ,  y ,  null ,  toolFlag ,  Econ );
  paintInfo .addElement(p1);
 repaint();
  break ;
default :
}
}
public   void  mouseMoved(MouseEvent e) {
}
public   void  update(Graphics g) {
paint(g);
}
public   void  mousePressed(MouseEvent e) {
Point p2;
switch  ( toolFlag ) {
case  3: //  ֱ��
  x  = ( int ) e.getX();
  y  = ( int ) e.getY();
 p2 =  new  Point( x ,  y ,  c ,  toolFlag ,  con );
  paintInfo .addElement(p2);
  break ;
case  4:  //  Բ
  x  = ( int ) e.getX();
  y  = ( int ) e.getY();
 p2 =  new  Point( x ,  y ,  c ,  toolFlag ,  con );
  paintInfo .addElement(p2);
  break ;
case  5:  //  ����
  x  = ( int ) e.getX();
  y  = ( int ) e.getY();
 p2 =  new  Point( x ,  y ,  c ,  toolFlag ,  con );
  paintInfo .addElement(p2);
  break ;
default :
}
}
public   void  mouseReleased(MouseEvent e) {
Point p3;
switch  ( toolFlag ) {
case  0: //  ����
  paintInfo .addElement( cutflag );
  break ;
case  1:  // ��Ƥ
  paintInfo .addElement( cutflag );
  break ;
case  3: //  ֱ��
  x  = ( int ) e.getX();
  y  = ( int ) e.getY();
 p3 =  new  Point( x ,  y ,  c ,  toolFlag ,  con );
  paintInfo .addElement(p3);
  paintInfo .addElement( cutflag );
 repaint();
  break ;
case  4:  //  Բ
  x  = ( int ) e.getX();
  y  = ( int ) e.getY();
 p3 =  new  Point( x ,  y ,  c ,  toolFlag ,  con );
  paintInfo .addElement(p3);
  paintInfo .addElement( cutflag );
 repaint();
  break ;
case  5:  //  ����
  x  = ( int ) e.getX();
  y  = ( int ) e.getY();
 p3 =  new  Point( x ,  y ,  c ,  toolFlag ,  con );
  paintInfo .addElement(p3);
  paintInfo .addElement( cutflag );
 repaint();
  break ;
default :
}
}
public   void  mouseEntered(MouseEvent e) {
}
public   void  mouseExited(MouseEvent e) {
}
public   void  mouseClicked(MouseEvent e) {
}
public   void  actionPerformed(ActionEvent e) {
if  (e.getSource() ==  pen ) //  ����
{
  toolFlag  = 0;
}
if  (e.getSource() ==  eraser ) //  ��Ƥ
{
  toolFlag  = 1;
}
if  (e.getSource() ==  clear ) //  ���
{
  toolFlag  = 2;
  paintInfo .removeAllElements();
 repaint();
}
if  (e.getSource() ==  drLine ) //  ����
{
  toolFlag  = 3;
}
if  (e.getSource() ==  drCircle ) //  ��Բ
{
  toolFlag  = 4;
}
if  (e.getSource() ==  drRect ) //  ������
{
  toolFlag  = 5;
}
if  (e.getSource() ==  colchooser ) //  ��ɫ��
{
Color newColor = JColorChooser. showDialog ( this ,  " ��ɫ�� " ,  c );
  c  = newColor;
}
if  (e.getSource() ==  openPic ) //  ��ͼ��
{
  openPicture .setVisible( true );
  if  ( openPicture .getFile() !=  null ) {
    int  tempflag;
   tempflag =  toolFlag ;
    toolFlag  = 2;
   repaint();
    try  {
      paintInfo .removeAllElements();
     File filein =  new  File( openPicture .getDirectory(),
          openPicture .getFile());
      picIn  =  new  FileInputStream(filein);
      VIn  =  new  ObjectInputStream( picIn );
      paintInfo  =  (Vector)  VIn .readObject() ;
      VIn .close();
     repaint();
      toolFlag  = tempflag;
   }  catch  (ClassNotFoundException IOe2) {
     repaint();
      toolFlag  = tempflag;
     System. out .println( "can not read object" );
   }  catch  (IOException IOe) {
     repaint();
      toolFlag  = tempflag;
     System. out .println( "can not read file" );
   }
 }
}
if  (e.getSource() ==  savePic ) //  ����ͼ��
{
  savePicture .setVisible( true );
  try  {
   File fileout =  new  File( savePicture .getDirectory(),
        savePicture .getFile());
    picOut  =  new  FileOutputStream(fileout);
    VOut  =  new  ObjectOutputStream( picOut );
    VOut .writeObject( paintInfo );
    VOut .close();
 }
  //  ���ļ�ʧ����ô���쳣
  catch  (IOException IOe) {
   System. out .println( "can not write object" );
 }
}
}
}
