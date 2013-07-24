package com.liqi.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Boolean;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.liqi.centrality.model.Centrality2;

public class ExcelWriter {
	/**
     * 输出Excel
     */
    //public static void writeExcel(OutputStream os, String newSheetName)
    public static void writeExcel(WritableWorkbook wwb, String newSheetName)
    {
        try
        {
            /**
             * 只能通过API提供的工厂方法来创建Workbook，而不能使用WritableWorkbook的构造函数，
             * 因为类WritableWorkbook的构造函数为protected类型
             * method(1)直接从目标文件中读取WritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile));
             * method(2)如下实例所示 将WritableWorkbook直接写入到输出流

             */
        	System.out.println("create " + newSheetName);
            //WritableWorkbook wwb = Workbook.createWorkbook(os);
            //创建Excel工作表 指定名称和位置
            int num = wwb.getNumberOfSheets();
            WritableSheet ws = wwb.createSheet(newSheetName, num);

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
            //wwb.write();
            //wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args){
    	 File fileWrite = new File("D:/testWrite.xls");
    	 
         try {
			fileWrite.createNewFile();
			OutputStream os = new FileOutputStream(fileWrite);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
	    	for(int i = 0; i < 3; ++i){
	    		writeExcel(wwb, "network"+ i);
	    	}
	    	wwb.write();
	    	wwb.close();
		} catch (IOException | WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
    }
}
