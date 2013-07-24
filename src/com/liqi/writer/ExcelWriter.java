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
     * ���Excel
     */
    //public static void writeExcel(OutputStream os, String newSheetName)
    public static void writeExcel(WritableWorkbook wwb, String newSheetName)
    {
        try
        {
            /**
             * ֻ��ͨ��API�ṩ�Ĺ�������������Workbook��������ʹ��WritableWorkbook�Ĺ��캯����
             * ��Ϊ��WritableWorkbook�Ĺ��캯��Ϊprotected����
             * method(1)ֱ�Ӵ�Ŀ���ļ��ж�ȡWritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile));
             * method(2)����ʵ����ʾ ��WritableWorkbookֱ��д�뵽�����

             */
        	System.out.println("create " + newSheetName);
            //WritableWorkbook wwb = Workbook.createWorkbook(os);
            //����Excel������ ָ�����ƺ�λ��
            int num = wwb.getNumberOfSheets();
            WritableSheet ws = wwb.createSheet(newSheetName, num);

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
