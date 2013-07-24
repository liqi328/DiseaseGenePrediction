package com.liqi.jxl;

/***********************************

*��1

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
//�����excel�ļ���
//�����excel�ļ���������
//excel������ı���
//������д���Excel������,�������ɵ��ļ���tomcat/bin��
//workbook = Workbook.createWorkbook(new File("output.xls")); 
//��ӵ�һ��������
//WritableSheet sheet1 = workbook.createSheet("MySheet1", 1); //����ӵڶ�������
/*
jxl.write.Label label = new jxl.write.Label(0, 2, "A label record"); //put a label in cell A3, Label(column,row)
sheet.addCell(label); 
*/
//Label(�к�,�к� ,���� )
//put the title in row1 
//������ӵĶ�����ȵ����þ�����ͨ���������ο���
//�������
//put the number 3.14159 in cell D5
//��Ӵ�������Formatting�Ķ��� 
//��ˮƽ���뷽ʽָ��Ϊ���� 
//�Ѵ�ֱ���뷽ʽָ��Ϊ���� 
//��Ӵ���������ɫ,��������ɫ Formatting�Ķ��� 
//�иߺ��п� 
//����һ�еĸ߶���Ϊ200 
//����һ�еĿ����Ϊ30 
//��Ӵ���formatting��Number���� 
//3.���Boolean���� 
//4.���DateTime���� 
//��Ӵ���formatting��DateFormat���� 
//�Ͳ���Ԫ��
//sheet.mergeCells(int col1,int row1,int col2,int row2);//���Ͻǵ����½�
//���Ͻǵ����½�
//
//���ñ߿�
//String cmd[]={"notepad","exec.java"}; 
/***********************************

*��2

************************************/
class excel
{
public static void main(String[] args) 
{

String targetfile = "c:/out.xls";//�����excel�ļ���
String worksheet = "List";//�����excel�ļ���������
String[] title = {"ID","NAME","DESCRIB"};//excel������ı���


WritableWorkbook workbook;
try
{
//������д���Excel������,�������ɵ��ļ���tomcat/bin��
//workbook = Workbook.createWorkbook(new File("output.xls")); 
System.out.println("begin");

OutputStream os=new FileOutputStream(targetfile); 
workbook=Workbook.createWorkbook(os); 

WritableSheet sheet = workbook.createSheet(worksheet, 0); //��ӵ�һ��������
//WritableSheet sheet1 = workbook.createSheet("MySheet1", 1); //����ӵڶ�������
/*
jxl.write.Label label = new jxl.write.Label(0, 2, "A label record"); //put a label in cell A3, Label(column,row)
sheet.addCell(label); 
*/

jxl.write.Label label;
for (int i=0; i<title.length; i++)
{
//Label(�к�,�к� ,���� )
label = new jxl.write.Label(i, 0, title[i]); //put the title in row1 
sheet.addCell(label); 
}




//������ӵĶ�����ȵ����þ�����ͨ���������ο���


//�������
jxl.write.Number number = new jxl.write.Number(3, 4, 3.14159); //put the number 3.14159 in cell D5
sheet.addCell(number);

//��Ӵ�������Formatting�Ķ��� 
jxl.write.WritableFont wf = new jxl.write.WritableFont(WritableFont.TIMES,10,WritableFont.BOLD,true); 
jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf); 
jxl.write.Label labelCF = new jxl.write.Label(4,4,"�ı�",wcfF); 
sheet.addCell(labelCF); 

 

//��ˮƽ���뷽ʽָ��Ϊ���� 
wcfF.setAlignment(jxl.format.Alignment.CENTRE); 

//�Ѵ�ֱ���뷽ʽָ��Ϊ���� 
wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 

//��Ӵ���������ɫ,��������ɫ Formatting�Ķ��� 
jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED); 
jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc); 
wcfFC.setBackground(jxl.format.Colour.BLUE);
jxl.write.Label labelCFC = new jxl.write.Label(1,5,"����ɫ",wcfFC); 
sheet.addCell(labelCFC); 

//�иߺ��п� 

//WritableSheet.setRowView(int i,int height); 

//������ָ����i 1�еĸ߶ȣ����磺 

//����һ�еĸ߶���Ϊ200 
sheet.setRowView(0,200); 

//WritableSheet.setColumnView(int i,int width); 

//������ָ����i 1�еĿ�ȣ����磺 

//����һ�еĿ����Ϊ30 
sheet.setColumnView(0,30); 



//��Ӵ���formatting��Number���� 
jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); 
jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); 
jxl.write.Number labelNF = new jxl.write.Number(1,1,3.1415926,wcfN); 
sheet.addCell(labelNF); 

//3.���Boolean���� 
jxl.write.Boolean labelB = new jxl.write.Boolean(0,2,false); 
sheet.addCell(labelB); 

//4.���DateTime���� 
jxl.write.DateTime labelDT = new jxl.write.DateTime(0,3,new java.util.Date()); 
sheet.addCell(labelDT); 

//��Ӵ���formatting��DateFormat���� 
jxl.write.DateFormat df = new jxl.write.DateFormat("ddMMyyyyhh:mm:ss"); 
jxl.write.WritableCellFormat wcfDF = new jxl.write.WritableCellFormat(df); 
jxl.write.DateTime labelDTF = new jxl.write.DateTime(1,3,new java.util.Date(),wcfDF); 
sheet.addCell(labelDTF); 

//�Ͳ���Ԫ��
//sheet.mergeCells(int col1,int row1,int col2,int row2);//���Ͻǵ����½�
sheet.mergeCells(4,5,8,10);//���Ͻǵ����½�
wfc = new jxl.write.WritableFont(WritableFont.ARIAL,40,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.GREEN); 
jxl.write.WritableCellFormat wchB = new jxl.write.WritableCellFormat(wfc); 
wchB.setAlignment(jxl.format.Alignment.CENTRE);
labelCFC = new jxl.write.Label(4,5,"��Ԫ�ϲ�",wchB); 
sheet.addCell(labelCFC); //

//���ñ߿�
jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat(); 
wcsB.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THICK);
labelCFC = new jxl.write.Label(0,6,"�߿�����",wcsB); 
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

*��2

************************************/


public class ExcelHandler
{
    public ExcelHandler()
    {
    }

    /**
     * ��ȡExcel
     */
    public static void readExcel(String filePath)
    {
        try
        {
            InputStream is = new FileInputStream(filePath);
            Workbook rwb = Workbook.getWorkbook(is);
            //Sheet st = rwb.getSheet("0")���������ַ�����ȡsheet��,1Ϊ���֣���Ϊ�±꣬��0��ʼ
            Sheet st = rwb.getSheet("original");
            Cell c00 = st.getCell(0,0);
            //ͨ�õĻ�ȡcellֵ�ķ�ʽ,�����ַ���
            String strc00 = c00.getContents();
            //���cell��������ֵ�ķ�ʽ
            if(c00.getType() == CellType.LABEL)
            {
                LabelCell labelc00 = (LabelCell)c00;
                strc00 = labelc00.getString();
            }
            //���
            System.out.println(strc00);
            //�ر�
            rwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ���Excel
     */
    public static void writeExcel(OutputStream os)
    {
        try
        {
            /**
             * ֻ��ͨ��API�ṩ�Ĺ�������������Workbook��������ʹ��WritableWorkbook�Ĺ��캯����
             * ��Ϊ��WritableWorkbook�Ĺ��캯��Ϊprotected����
             * method(1)ֱ�Ӵ�Ŀ���ļ��ж�ȡWritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile));
             * method(2)����ʵ����ʾ ��WritableWorkbookֱ��д�뵽�����

             */
            WritableWorkbook wwb = Workbook.createWorkbook(os);
            //����Excel������ ָ�����ƺ�λ��
            WritableSheet ws = wwb.createSheet("Test Sheet 1",0);

            //**************�����������������*****************

            //1.���Label����
            Label label = new Label(0,0,"this is a label test");
            ws.addCell(label);

            //��Ӵ�������Formatting����
            WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            Label labelcf = new Label(2,0,"this is a label test",wcf);
            ws.addCell(labelcf);

            //��Ӵ���������ɫ��Formatting����
            WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,
                    UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED);
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            Label labelCF = new Label(1,0,"This is a Label Cell",wcfFC);
            ws.addCell(labelCF);

            //2.���Number����
            Number labelN = new Number(0,1,3.1415926);
            ws.addCell(labelN);

            //��Ӵ���formatting��Number����
            NumberFormat nf = new NumberFormat("#.##");
            WritableCellFormat wcfN = new WritableCellFormat(nf);
            Number labelNF = new jxl.write.Number(1,1,3.1415926,wcfN);
            ws.addCell(labelNF);

            //3.���Boolean����
            Boolean labelB = new jxl.write.Boolean(0,2,false);
            ws.addCell(labelB);

            //4.���DateTime����
            jxl.write.DateTime labelDT = new jxl.write.DateTime(0,3,new java.util.Date());
            ws.addCell(labelDT);

            //��Ӵ���formatting��DateFormat����
            DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");
            WritableCellFormat wcfDF = new WritableCellFormat(df);
            DateTime labelDTF = new DateTime(1,3,new java.util.Date(),wcfDF);
            ws.addCell(labelDTF);


            //���ͼƬ����,jxlֻ֧��png��ʽͼƬ
            File image = new File("D:/2.png");
            WritableImage wimage = new WritableImage(0,1,2,2,image);//0,1�ֱ����x,y.2,2�����͸�ռ�ĵ�Ԫ����
            ws.addImage(wimage);
            //д�빤����
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ������,�����޸�,����file1Ϊ��copy����file2Ϊ�޸ĺ󴴽��Ķ���
     * ����Ԫ��ԭ�еĸ�ʽ�������ǲ���ȥ���ģ����ǻ��ǿ��Խ��µĵ�Ԫ�����μ���ȥ��
     * ��ʹ��Ԫ��������Բ�ͬ����ʽ����
     */
    public static void modifyExcel(File file1,File file2)
    {
        try
        {
            Workbook rwb = Workbook.getWorkbook(file1);
            WritableWorkbook wwb = Workbook.createWorkbook(file2,rwb);//copy
            WritableSheet ws = wwb.getSheet(0);
            WritableCell wc = ws.getWritableCell(0,0);
            //�жϵ�Ԫ�������,������Ӧ��ת��
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


    //����
    public static void main(String[] args)
    {
        try
        {
            //��Excel
            //ExcelHandle.readExcel("f:/testRead.xls");
            //���Excel
            File fileWrite = new File("D:/testWrite.xls");
            fileWrite.createNewFile();
            OutputStream os = new FileOutputStream(fileWrite);
            ExcelHandler.writeExcel(os);
            //�޸�Excel
            //ExcelHandle.modifyExcel(new File(""),new File(""));
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
    }
}


