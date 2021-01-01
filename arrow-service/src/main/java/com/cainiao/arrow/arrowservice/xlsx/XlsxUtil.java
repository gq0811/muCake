package com.cainiao.arrow.arrowservice.xlsx;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

        try {
            String fileName = "/Users/huqingnie/Downloads/入账表格/流水/11月杭州云客赞0989明细.xlsx";
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fileName));
            Map<String, List<String>> shopIdMap = new HashMap<String, List<String>>();
            /**
             *  1、选取哪个sheet（从0开始数）
             */
            XSSFSheet xSheet = xwb.getSheetAt(5);
            for (int i = 0; i <= xSheet.getLastRowNum(); i++) {
                if (xSheet.getRow(i) == null) {
                    continue;
                }

                /**
                 *  2、数据源的名称，所在的列号（从0开始数）
                 */
                String currKey = xSheet.getRow(i).getCell(6).toString();
                /**
                 *  3、数据源对应的ID，所在的列号（从0开始数）
                 */
                String currVal = xSheet.getRow(i).getCell(5).toString();
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
            }
            for (int i = 0; i <= xSheet.getLastRowNum(); i++) {  //遍历所有的行
                if (xSheet.getRow(i) == null || xSheet.getRow(i).getCell(0) == null) { //这行为空执行下次循环
                    continue;
                }
                /**
                 * 4、需要匹配的名称，所在的列号（从0开始数）
                 */
                int matchCellNum = 2;
                if(xSheet.getRow(i).getCell(matchCellNum)==null||StringUtils.isEmpty(xSheet.getRow(i).getCell(matchCellNum).toString())){
                    continue;
                }
                String currKey = xSheet.getRow(i).getCell(matchCellNum).toString();
                /**
                 *  5、在哪一列生成这个匹配得到的数据（从0开始数）
                 */
                XSSFCell xCell = xSheet.getRow(i).createCell(3);
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
            throw new RuntimeException(e);
        }

    }
}
