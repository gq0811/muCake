package com.cainiao.arrow.arrowservice.xlsx;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XlsxUtil {



    public static void main(String[] args) {


        Map<String, List<String>> map = new HashMap<>();
        List<String> workNo = new ArrayList<>();

        try {

            String fileName = "/Users/huqingnie/Downloads/映射文件.xlsx";  //修改d盘的aaa.xlsx文件
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fileName));
            Map<String, List<String>> shopIdMap = new HashMap<String, List<String>>();
            String fillStr = "";    //存储aaa文件里的数据
            String[] fillSplit = null;
            XSSFSheet xSheet = xwb.getSheetAt(2);  //获取excel表的第一个sheet
            for (int i = 0; i <= xSheet.getLastRowNum(); i++) {  //遍历所有的行
                if (xSheet.getRow(i) == null) { //这行为空执行下次循环
                    continue;
                }
                String currKey = xSheet.getRow(i).getCell(3).toString();
                String currVal = xSheet.getRow(i).getCell(2).toString();
                if(!shopIdMap.containsKey(currKey)){
                    List<String> list= new ArrayList<>();
                    list.add(currVal);
                    shopIdMap.put(currKey,list);
                }else{
                    shopIdMap.get(currKey).add(currVal);
                }
                if(shopIdMap.get(currKey).size()>1){
                    System.out.println(currKey);
                }
//                for (int j = 0; j <= xSheet.getRow(i).getPhysicalNumberOfCells(); j++) {  //遍历当前行的所有列
//                    if (xSheet.getRow(i).getCell(j) == null) {//为空执行下次循环
//                         System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
//                        continue;
//                    }
//                    fillStr = (xSheet.getRow(i)).getCell(j).toString();//获取当前单元格的数据
//                    fillSplit = fillStr.split("_");//切割，本人的数据是以"_"为分隔符的这个可以根据自己情况改变
////                        XSSFRow xRow = xSheet.createRow(i);
////                        XSSFCell xCell = xRow.createCell(j);
//                    XSSFCell xCell = xSheet.getRow(i).getCell(j); //获取单元格对象，这块不能向上边那两句代码那么写，不能用createXXX，用的话会只把第一列的数据改掉
//                    //xCell.setCellValue(fields.get(fillSplit[0].trim()) == null ? fillStr : fields.get(fillSplit[0].trim()));//修改数据，看数据是否和字段集合中的数据匹配，不匹配使用元数据
//                     System.out.println(JSON.toJSONString(xCell));
//                }
            }
            for (int i = 0; i <= xSheet.getLastRowNum(); i++) {  //遍历所有的行
                if (xSheet.getRow(i) == null || xSheet.getRow(i).getCell(0) == null) { //这行为空执行下次循环
                    continue;
                }
                String currKey = xSheet.getRow(i).getCell(0).toString();
                XSSFCell xCell = xSheet.getRow(i).createCell(1);
                if(shopIdMap.get(currKey)==null){
                    continue;
                }
                xCell.setCellValue(shopIdMap.get(currKey).stream().map(String::valueOf).collect(Collectors.joining(",")));
                if(i<5){
                    System.out.println(xCell.toString());
                }
            }
            FileOutputStream out = new FileOutputStream(fileName);
            xwb.write(out);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
