package com.vladislav.boyarchenko;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Converter implements DatesToCronConverter{
    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    
  
    
    @Override
     public String convert(List<String> dates) throws DatesToCronConvertException{
    
         StringBuilder builder = new StringBuilder();
        List<String> month = new ArrayList<>();
        List<String> day = new ArrayList<>();
        List<String> hour = new ArrayList<>();
        List<String> min = new ArrayList<>();
        List<String> sec = new ArrayList<>();
        
        dates.stream().map(d -> {
            month.add(d.substring(5, 7));
            return d;
        }).map(d -> {
            day.add(d.substring(8, 10));
            return d;
        }).map(d -> {
            hour.add(d.substring(11, 13));
            return d;
        }).map(d -> {
            min.add(d.substring(14, 16));
            return d;
        }).forEachOrdered(d -> {
            sec.add(d.substring(17, 19));
        });

        parseSec(sec, builder);
        parseMin(min, builder);
        parseHour(hour, builder);
        parseDay(day, builder);
        parseMonth(month, builder);
        parseWeekDay(day, month, dates, builder);

        return builder.toString();
    }
     public void parseSec(List<String> sec, StringBuilder builder){
         boolean consistent = true;
         boolean range = false;
         if(Integer.parseInt(sec.get(0)) > Integer.parseInt(sec.get(1))) Integer.parseInt(sec.get(1)+60);
         int difference0 = Integer.parseInt(sec.get(0))-Integer.parseInt(sec.get(1));
         for (int i = 0, j = 1; j < sec.size(); i++, j++){
             int firstTime = Integer.parseInt(sec.get(i));
             int nextTime = Integer.parseInt(sec.get(j));
             if(firstTime>nextTime)nextTime += 60;
             int difference = nextTime - firstTime;
             if(difference0 !=difference && difference0 !=Math.abs(30))consistent = false;
             if(sec.size()<60 && Integer.parseInt(sec.get(0)) == Integer.parseInt(sec.get(i))) range=true;
     } 
         if(difference0 == 0 && consistent){
             builder.append(Integer.parseInt(sec.get(0))).append(" ");
         } else if(range && consistent){
             builder.append(Integer.parseInt(sec.get(0))).
                    append("-").append(Integer.parseInt(sec.get(sec.size()-1))).append(" ");
           } else if(consistent) {
             builder.append("*/").append(Math.abs(difference0)).append(" ");
         } else {
               builder.append("* ");
         }
      }
    public void parseMin(List<String> min, StringBuilder builder){
         boolean consistent = true;
         boolean range = false;
         if((Integer.parseInt(min.get(0)) | Integer.parseInt(min.get(1))) !=30 
                 && Integer.parseInt(min.get(0)) > Integer.parseInt(min.get(1))) Integer.parseInt(min.get(1)+60);
         int difference0 = Integer.parseInt(min.get(1))-Integer.parseInt(min.get(0));
         for (int i = 1, j = 2; j < min.size(); i++, j++){
             int firstTime = Integer.parseInt(min.get(i));
             int nextTime = Integer.parseInt(min.get(j));
             if(firstTime>nextTime)nextTime += 60;
             int difference = nextTime - firstTime;
             if(Math.abs(difference0) !=Math.abs(difference))consistent = false;
             if(min.size()<60 && Integer.parseInt(min.get(0)) == Integer.parseInt(min.get(j)) && (difference ==Math.abs(1) 
                     || difference ==Math.abs(0))) range=true;
     }
          if(difference0 == 0 && consistent){
             builder.append(Integer.parseInt(min.get(0))).append(" ");
          } else if(range && consistent){
             builder.append(Integer.parseInt(min.get(0))).
                    append("-").append(Integer.parseInt(min.get(min.size()-1))).append(" ");
           } else if(consistent && Math.abs(difference0) !=1) {
             builder.append("*/").append(Math.abs(difference0)).append(" ");
         } else {
               builder.append("* ");
         }
      } 
    public void parseHour(List<String> hour, StringBuilder builder){
         boolean consistent = true;
         boolean range = false;
         int sum1 =0;
         int sum2 =0;
         if(Integer.parseInt(hour.get(0)) > Integer.parseInt(hour.get(1))) Integer.parseInt(hour.get(1)+24);
         int difference0 = Integer.parseInt(hour.get(0))-Integer.parseInt(hour.get(1));
         for (int i = 0, j = 1; j < hour.size(); i++, j++){
             int firstTime = Integer.parseInt(hour.get(i));
             int nextTime = Integer.parseInt(hour.get(j));
             if(firstTime>nextTime)nextTime += 24;
             int difference = nextTime - firstTime;
             if(difference ==0) sum1 +=1;
             if(difference ==1) sum2 +=1;
             if(sum1==sum2) range=true;
             if(difference0 !=difference && difference0 !=Math.abs(12))consistent = false;
             if(hour.size()<24 && (Integer.parseInt(hour.get(i)) == Integer.parseInt(hour.get(j)) 
                    & difference==1)) range=true;
       
     } 
         if(difference0 == 0 && consistent && !range){
             builder.append(Integer.parseInt(hour.get(0))).append(" ");
           } else if(range){
             builder.append(Integer.parseInt(hour.get(0))).
                    append("-").append(Integer.parseInt(hour.get(hour.size()-1))).append(" ");
         }  else if(consistent) {
             builder.append("*/").append(Math.abs(difference0)).append(" ");
         } else {
               builder.append("* ");
         }
      }
    public void parseDay(List<String> day, StringBuilder builder){
         boolean consistent = true;
         boolean range = false;
         if(Integer.parseInt(day.get(0)) > Integer.parseInt(day.get(1))) Integer.parseInt(day.get(1)+31);
         int difference0 = Integer.parseInt(day.get(0))-Integer.parseInt(day.get(1));
         for (int i = 0, j = 1; j < day.size(); i++, j++){
             int firstTime = Integer.parseInt(day.get(i));
             int nextTime = Integer.parseInt(day.get(j));
             if(firstTime>nextTime)nextTime += 31;
             int difference = nextTime - firstTime;
             if(difference0 !=difference && difference0 !=Math.abs(15))consistent = false;
             if(day.size()<31 && Integer.parseInt(day.get(0)) == Integer.parseInt(day.get(i))) range=true;
     }
         if(difference0 == 0 && consistent ){
             builder.append("* ");
           } else if(range && consistent){
             builder.append(Integer.parseInt(day.get(0))).
                    append("-").append(Integer.parseInt(day.get(day.size()-1))).append(" ");
         }  else if(consistent) {
             builder.append("*/").append(Math.abs(difference0)).append(" ");
         } else {
               builder.append("* ");
         }
      }
    public void parseMonth(List<String> month, StringBuilder builder){
         boolean consistent = true;
         boolean range = false;
         if(Integer.parseInt(month.get(0)) > Integer.parseInt(month.get(1))) Integer.parseInt(month.get(1)+12);
         int difference0 = Integer.parseInt(month.get(0))-Integer.parseInt(month.get(1));
         for (int i = 0, j = 1; j < month.size(); i++, j++){
             int firstTime = Integer.parseInt(month.get(i));
             int nextTime = Integer.parseInt(month.get(j));
             if(firstTime>nextTime)nextTime += 12;
             int difference = nextTime - firstTime;
             if(difference0 !=difference && difference0 !=Math.abs(6))consistent = false;
             if(month.size()<12 && Integer.parseInt(month.get(0)) == Integer.parseInt(month.get(i))) range=true;
     } 
         if(difference0 == 0 && consistent){
             builder.append("* ");
         }else if(range && consistent){
             builder.append(Integer.parseInt(month.get(0))).
                    append("-").append(Integer.parseInt(month.get(month.size()-1))).append(" ");
          } else if(consistent) {
             builder.append("*/").append(Math.abs(difference0)).append(" ");
         } else {
               builder.append("* ");
         }
      }
    public void parseWeekDay(List<String> day, List<String> month, List<String> dates, StringBuilder builder){
        boolean equals = true;

        for (int i = 0, j = 1; j < day.size(); i++, j++) {
            if (Integer.parseInt(day.get(i))!= Integer.parseInt(day.get(j)) || Integer.parseInt(month.get(i)) != Integer.parseInt(month.get(j))) {
                equals = false;
                break;
            }
        }

        if (equals) {
            LocalDateTime date = LocalDateTime.parse(dates.get(0));
            String dayWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            builder.append(dayWeek.toUpperCase());
        } else builder.append("*");
    }
    @Override
    public String getImplementationInfo(){
    return "Боярченко Владислав Денисович " + getClass().getSimpleName() + " " + getClass().getPackage().getName()
                + " " + " https://github.com/he4to3/DateCron";
}

}