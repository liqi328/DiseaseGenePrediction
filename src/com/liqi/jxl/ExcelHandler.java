package com.liqi.jxl;

/***********************************

*例1

************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Boolean;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
//输出的excel文件名
//输出的excel文件工作表名
//excel工作表的标题
//创建可写入的Excel工作薄,运行生成的文件在tomcat/bin下
//workbook = Workbook.createWorkbook(new File("output.xls")); 
//添加第一个工作表
//WritableSheet sheet1 = workbook.createSheet("MySheet1", 1); //可添加第二个工作
/*
jxl.write.Label label = new jxl.write.Label(0, 2, "A label record"); //put a label in cell A3, Label(column,row)
sheet.addCell(label); 
*/
//Label(列号,行号 ,内容 )
//put the title in row1 
//下列添加的对字体等的设置均调试通过，可作参考用
//添加数字
//put the number 3.14159 in cell D5
//添加带有字型Formatting的对象 
//把水平对齐方式指定为居中 
//把垂直对齐方式指定为居中 
//添加带有字体颜色,带背景颜色 Formatting的对象 
//行高和列宽 
//将第一行的高度设为200 
//将第一列的宽度设为30 
//添加带有formatting的Number对象 
//3.添加Boolean对象 
//4.添加DateTime对象 
//添加带有formatting的DateFormat对象 
//和并单元格
//sheet.mergeCells(int col1,int row1,int col2,int row2);//左上角到右下角
//左上角到右下角
//
//设置边框
//String cmd[]={"notepad","exec.java"}; 
/***********************************

*例2

************************************/
class excel
{
public static void main(String[] args) 
{

String targetfile = "c:/out.xls";//输出的excel文件名
String worksheet = "List";//输出的excel文件工作表名
String[] title = {"ID","NAME","DESCRIB"};//excel工作表的标题


WritableWorkbook workbook;
try
{
//创建可写入的Excel工作薄,运行生成的文件在tomcat/bin下
//workbook = Workbook.createWorkbook(new File("output.xls")); 
System.out.println("begin");

OutputStream os=new FileOutputStream(targetfile); 
workbook=Workbook.createWorkbook(os); 

WritableSheet sheet = workbook.createSheet(worksheet, 0); //添加第一个工作表
//WritableSheet sheet1 = workbook.createSheet("MySheet1", 1); //可添加第二个工作
/*
jxl.write.Label label = new jxl.write.Label(0, 2, "A label record"); //put a label in cell A3, Label(column,row)
sheet.addCell(label); 
*/

jxl.write.Label label;
for (int i=0; i<title.length; i++)
{
//Label(列号,行号 ,内容 )
label = new jxl.write.Label(i, 0, title[i]); //put the title in row1 
sheet.addCell(label); 
}




//下列添加的对字体等的设置均调试通过，可作参考用


//添加数字
jxl.write.Number number = new jxl.write.Number(3, 4, 3.14159); //put the number 3.14159 in cell D5
sheet.addCell(number);

//添加带有字型Formatting的对象 
jxl.write.WritableFont wf = new jxl.write.WritableFont(WritableFont.TIMES,10,WritableFont.BOLD,true); 
jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf); 
jxl.write.Label labelCF = new jxl.write.Label(4,4,"文本",wcfF); 
sheet.addCell(labelCF); 

 

//把水平对齐方式指定为居中 
wcfF.setAlignment(jxl.format.Alignment.CENTRE); 

//把垂直对齐方式指定为居中 
wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 

//添加带有字体颜色,带背景颜色 Formatting的对象 
jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED); 
jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc); 
wcfFC.setBackground(jxl.format.Colour.BLUE);
jxl.write.Label labelCFC = new jxl.write.Label(1,5,"带颜色",wcfFC); 
sheet.addCell(labelCFC); 

//行高和列宽 

//WritableSheet.setRowView(int i,int height); 

//作用是指定第i 1行的高度，比如： 

//将第一行的高度设为200 
sheet.setRowView(0,200); 

//WritableSheet.setColumnView(int i,int width); 

//作用是指定第i 1列的宽度，比如： 

//将第一列的宽度设为30 
sheet.setColumnView(0,30); 



//添加带有formatting的Number对象 
jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); 
jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); 
jxl.write.Number labelNF = new jxl.write.Number(1,1,3.1415926,wcfN); 
sheet.addCell(labelNF); 

//3.添加Boolean对象 
jxl.write.Boolean labelB = new jxl.write.Boolean(0,2,false); 
sheet.addCell(labelB); 

//4.添加DateTime对象 
jxl.write.DateTime labelDT = new jxl.write.DateTime(0,3,new java.util.Date()); 
sheet.addCell(labelDT); 

//添加带有formatting的DateFormat对象 
jxl.write.DateFormat df = new jxl.write.DateFormat("ddMMyyyyhh:mm:ss"); 
jxl.write.WritableCellFormat wcfDF = new jxl.write.WritableCellFormat(df); 
jxl.write.DateTime labelDTF = new jxl.write.DateTime(1,3,new java.util.Date(),wcfDF); 
sheet.addCell(labelDTF); 

//和并单元格
//sheet.mergeCells(int col1,int row1,int col2,int row2);//左上角到右下角
sheet.mergeCells(4,5,8,10);//左上角到右下角
wfc = new jxl.write.WritableFont(WritableFont.ARIAL,40,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.GREEN); 
jxl.write.WritableCellFormat wchB = new jxl.write.WritableCellFormat(wfc); 
wchB.setAlignment(jxl.format.Alignment.CENTRE);
labelCFC = new jxl.write.Label(4,5,"单元合并",wchB); 
sheet.addCell(labelCFC); //

//设置边框
jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat(); 
wcsB.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THICK);
labelCFC = new jxl.write.Label(0,6,"边框设置",wcsB); 
sheet.addCell(labelCFC); 
workbook.write(); 
workbook.close();
}catch(Exception e) 
{ 
e.printStackTrace(); 
} 
System.out.println("end");
Runtime r=Runtime.getRuntime(); 
Process p=null; 
//String cmd[]={"notepad","exec.java"}; 
String cmd[]={"C:\\Program Files\\Microsoft Office\\Office\\EXCEL.EXE","out.xls"}; 
try{ 
p=r.exec(cmd); 
} 
catch(Exception e){ 
System.out.println("error executing: "+cmd[0]); 
}


}
}

/***********************************

*例2

************************************/


public class ExcelHandler
{
    public ExcelHandler()
    {
    }

    /**
     * 读取Excel
     */
    public static void readExcel(String filePath)
    {
        try
        {
            InputStream is = new FileInputStream(filePath);
            Workbook rwb = Workbook.getWorkbook(is);
            //Sheet st = rwb.getSheet("0")这里有两种方法获取sheet表,1为名字，而为下标，从0开始
            Sheet st = rwb.getSheet("original");
            Cell c00 = st.getCell(0,0);
            //通用的获取cell值的方式,返回字符串
            String strc00 = c00.getContents();
            //获得cell具体类型值的方式
            if(c00.getType() == CellType.LABEL)
            {
                LabelCell labelc00 = (LabelCell)c00;
                strc00 = labelc00.getString();
            }
            //输出
            System.out.println(strc00);
            //关闭
            rwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 输出Excel
     */
    public static void writeExcel(OutputStream os)
    {
        try
        {
            /**
             * 只能通过API提供的工厂方法来创建Workbook，而不能使用WritableWorkbook的构造函数，
             * 因为类WritableWorkbook的构造函数为protected类型
             * method(1)直接从目标文件中读取WritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile));
             * method(2)如下实例所示 将WritableWorkbook直接写入到输出流

             */
            WritableWorkbook wwb = Workbook.createWorkbook(os);
            //创建Excel工作表 指定名称和位置
            WritableSheet ws = wwb.createSheet("Test Sheet 1",0);

            //**************往工作表中添加数据*****************

            //1.添加Label对象
            Label label = new Label(0,0,"this is a label test");
            ws.addCell(label);

            //添加带有字型Formatting对象
            WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            Label labelcf = new Label(2,0,"this is a label test",wcf);
            ws.addCell(labelcf);

            //添加带有字体颜色的Formatting对象
            WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,
                    UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED);
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            Label labelCF = new Label(1,0,"This is a Label Cell",wcfFC);
            ws.addCell(labelCF);

            //2.添加Number对象
            Number labelN = new Number(0,1,3.1415926);
            ws.addCell(labelN);

            //添加带有formatting的Number对象
            NumberFormat nf = new NumberFormat("#.##");
            WritableCellFormat wcfN = new WritableCellFormat(nf);
            Number labelNF = new jxl.write.Number(1,1,3.1415926,wcfN);
            ws.addCell(labelNF);

            //3.添加Boolean对象
            Boolean labelB = new jxl.write.Boolean(0,2,false);
            ws.addCell(labelB);

            //4.添加DateTime对象
            jxl.write.DateTime labelDT = new jxl.write.DateTime(0,3,new java.util.Date());
            ws.addCell(labelDT);

            //添加带有formatting的DateFormat对象
            DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");
            WritableCellFormat wcfDF = new WritableCellFormat(df);
            DateTime labelDTF = new DateTime(1,3,new java.util.Date(),wcfDF);
            ws.addCell(labelDTF);


            //添加图片对象,jxl只支持png格式图片
            File image = new File("D:/2.png");
            WritableImage wimage = new WritableImage(0,1,2,2,image);//0,1分别代表x,y.2,2代表宽和高占的单元格数
            ws.addImage(wimage);
            //写入工作表
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝后,进行修改,其中file1为被copy对象，file2为修改后创建的对象
     * 尽单元格原有的格式化修饰是不能去掉的，我们还是可以将新的单元格修饰加上去，
     * 以使单元格的内容以不同的形式表现
     */
    public static void modifyExcel(File file1,File file2)
    {
        try
        {
            Workbook rwb = Workbook.getWorkbook(file1);
            WritableWorkbook wwb = Workbook.createWorkbook(file2,rwb);//copy
            WritableSheet ws = wwb.getSheet(0);
            WritableCell wc = ws.getWritableCell(0,0);
            //判断单元格的类型,做出相应的转换
            if(wc.getType() == CellType.LABEL)
            {
                Label label = (Label)wc;
                label.setString("The value has been modified");
            }
            wwb.write();
            wwb.close();
            rwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    //测试
    public static void main(String[] args)
    {
        try
        {
            //读Excel
            //ExcelHandle.readExcel("f:/testRead.xls");
            //输出Excel
            File fileWrite = new File("D:/testWrite.xls");
            fileWrite.createNewFile();
            OutputStream os = new FileOutputStream(fileWrite);
            ExcelHandler.writeExcel(os);
            //修改Excel
            //ExcelHandle.modifyExcel(new File(""),new File(""));
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
    }
}


