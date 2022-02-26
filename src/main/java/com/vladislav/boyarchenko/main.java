package com.vladislav.boyarchenko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {

    public static void main(String args[]) throws DatesToCronConvertException {
        Converter con = new Converter();
        List <String> date = new ArrayList<>(); 
        date.addAll(Arrays.asList("2022-01-24T19:53:00",
        "2022-01-24T19:54:00",
        "2022-01-24T19:55:00",
        "2022-01-24T19:56:00",
        "2022-01-24T19:57:00",
        "2022-01-24T19:58:00",
        "2022-01-24T19:59:00",
        "2022-01-24T20:00:00",
        "2022-01-24T20:01:00",
        "2022-01-24T20:02:00"
           )
       );
        //   "0 * * * * MON"
       System.out.print(con.convert(date));
       List <String> date2 = new ArrayList<>(); 
       date2.addAll(Arrays.asList("2022-01-25T08:00:00",
	"2022-01-25T08:30:00",
	"2022-01-25T09:00:00",
	"2022-01-25T09:30:00",
	"2022-01-26T08:00:00",
	"2022-01-26T08:30:00",
	"2022-01-26T09:00:00",
	"2022-01-26T09:30:00"
            )
       );
       //      "0 0/30 8-9 * * *"
       System.out.println();
       System.out.print(con.convert(date2));
       System.out.println();
       System.out.println(con.getImplementationInfo());
    }
}
